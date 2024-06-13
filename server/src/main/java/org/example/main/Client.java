package org.example.main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

public class Client {

    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    boolean firstMessageFromClient = true;

    public Client(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public Client(DatagramChannel clientChannel) {
    }

    public boolean isFirstMessageFromClient() {
        return firstMessageFromClient;
    }

    public void setFirstMessageFromClient(boolean firstMessageFromClient) {
        this.firstMessageFromClient = firstMessageFromClient;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void sendMessage(String message) throws Exception {
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }

    public String receiveMessage() throws Exception {
        byte[] buffer = new byte[1024]; // Размер буфера нужно подбирать в зависимости от ожидаемого размера сообщений
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }
}


