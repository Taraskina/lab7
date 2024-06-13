package org.example.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exceptions.MessageWasNotRedSuccessful;
import org.example.main.Request;
import org.example.main.Response;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;


public class ServerMessaging {
    private static final Logger log = LogManager.getLogger(ServerMessaging.class);

    private ServerMessaging(){};

    public static Request nioRead(DatagramChannel clientChannel) throws IOException, MessageWasNotRedSuccessful {
        ByteBuffer buf = ByteBuffer.allocate(clientChannel.socket().getReceiveBufferSize());
        int readed = clientChannel.read(buf);
        if (readed > 0) {
            buf.flip();
            String msg = new String(buf.array());
            log.info("readed {} , after seserialization {}", msg, ObjectConverter.toJson(ObjectConverter.deserialize(msg, new TypeReference<>() {
            })));
            return ObjectConverter.deserialize(msg, new TypeReference<>() {
            });
        } else throw new MessageWasNotRedSuccessful();
    }

    public static void nioSend(SocketChannel clientChannel, String message) throws IOException {
        Response resp = new Response();
        resp.addMessage(message);
        message = ObjectConverter.toJson(resp);
        log.info("sended {}", message);
        ByteBuffer buf = ByteBuffer.allocate(message.getBytes().length).put(message.getBytes());

        buf = buf.flip();
        while (buf.hasRemaining()) {
            clientChannel.write(buf);
        }
    }

    public static void nioSend(DatagramChannel clientChannel, Response resp) throws IOException {
        String message = ObjectConverter.toJson(resp);
        log.info("sended {}", message);
        ByteBuffer buf = ByteBuffer.allocate(message.getBytes().length);

        buf.put(message.getBytes());
        buf = buf.flip();
        while (buf.hasRemaining()) {
            clientChannel.write(buf);
        }
    }

}
