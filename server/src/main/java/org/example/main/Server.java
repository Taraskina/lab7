package org.example.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exceptions.MessageWasNotRedSuccessful;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

import static java.nio.channels.SelectionKey.OP_READ;
import static org.example.utility.AccessingAllClassesInPackage.getAllClasses;
import static org.example.utility.ServerMessaging.nioRead;
import static org.example.utility.ServerMessaging.nioSend;

public class Server {
    static final LinkedBlockingDeque<Request> requests = new LinkedBlockingDeque<Request>(100);
    private static final Logger log = LogManager.getLogger(Server.class);
    public static HashMap<String, Method> nameToHandleMap = new HashMap<>();
    private static boolean flag = true;
    private Selector selector;
    private DatagramChannel datagramChannel;
    private Client client;

    public Server(int port) {
        try {
            log.info("Программа запущена");
            this.datagramChannel = DatagramChannel.open();
            this.datagramChannel.bind(new InetSocketAddress(port));
            this.datagramChannel.configureBlocking(false);
            this.selector = Selector.open();
            this.datagramChannel.register(selector, OP_READ);
            log.info("Сервер настроен");
        } catch (IOException e) {
            log.error("Сервер не настроен", e);
            throw new RuntimeException(e);
        }
    }

    private void setCommands() {
        this.client.firstMessageFromClient = false;
        StringBuilder sb = new StringBuilder();
        getAllClasses("commands").stream().filter(w -> Arrays.stream(w.getFields()).anyMatch(x -> (x.getName().equals("commandType"))) && Arrays.stream(w.getFields()).anyMatch(y -> y.getName().equals("name"))).forEach(w -> {
            try {
                nameToHandleMap.put(String.valueOf(w.getField("name").get(w.getConstructor().newInstance())), w.getMethod("staticFactory", String[].class, String.class));
                sb.append(w.getField("name").get(w.getConstructor().newInstance())).append(",").append(w.getField("commandType").get(w.getConstructor().newInstance()).toString()).append(";");
            } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InstantiationException |
                     InvocationTargetException ignored) {
            }
        });

        try {
            nioSend(this.getClientChannel(), sb.toString());
        } catch (IOException e) {
        }
    }

    public void run() throws IOException {

        while (true) {//true
            this.getSelector().select();
            log.info("мощность итератора по селектору = {}", getSelector().selectedKeys().size());
            Iterator<SelectionKey> keysIterator = this.getSelector().selectedKeys().iterator();
            try {
                while (keysIterator.hasNext()) {
                    this.handleKey(keysIterator.next());
                    keysIterator.remove();
                }
            } catch (SocketException | ClosedChannelException e) {
                client.socket.close();
            } catch (Exception e) {
                log.error("ошибка,сервер чуть не лег", e);
            }
        }

    }

    private void handleKey(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            this.handleAcception();
        }else if (key.isReadable()) {
            this.handleRead();
        }else if (key.isWritable()) {
            this.handleWrite();
        }
    }

    private void handleAcception() throws IOException{
        log.info("ключ оказался доступным");
        this.setClientChannel(this.getServerDatagramChannel().socket());
        this.client.socket.configureBlocking(false);
        this.getClientChannel().register(this.getSelector(), OP_READ);
        log.info("Зарегали на селектор с read");
    }
    private void handleRead(){
        Request request = null;
        log.info("ключ оказался читаемым");
        try {
            request = nioRead(this.getClientChannel());
        } catch (IOException | MessageWasNotRedSuccessful e) {
            log.error("непрочитали(", e);
        }
        if (request != null) {
            requests.add(request);
            try {
                this.getClientChannel().register(this.getSelector(), SelectionKey.OP_WRITE, OP_READ);
            } catch (ClosedChannelException ignored) {}
        }
    }

    private void handleWrite() throws  IOException{
        log.info("ключ оказался писаемым");
        Request request = requests.poll();
        if (request != null) {
            if (request.getMessages().get(0).equals("commands") && client.firstMessageFromClient) {
                setCommands();
            } else {
                try {
                    request.command = request.command.revalidate(request.getMessages().get(0));

                    nioSend(this.getClientChannel(), request.getCommandToEx().calling(request.command.getArgs(), request.getCommandToEx().getValue()));

                } catch (InvocationTargetException | IllegalAccessException e) {
                    log.info("Ошибка: неверно реализована команда",e);
                } catch (IOException e) {
                    log.info("неудачно отправили сообщение", e);
                    flag = false;
                }
            }
        } else {
            log.info("null");
        }
        if (flag) {
            this.getClientChannel().register(this.getSelector(), OP_READ);
        }
        flag = true;
    }

    public DatagramChannel getServerDatagramChannel() {
        return datagramChannel;
    }

    public void setServerSocketChannel(DatagramChannel serverDatagramChannel) {
        this.datagramChannel = serverDatagramChannel;
    }

    public DatagramChannel getClientChannel() {
        return this.client.getSocket();
    }

    public void setClientChannel(DatagramSocket clientChannel) {
        this.client = new Client(clientChannel.getChannel());
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }
}
