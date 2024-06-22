package org.example.utility;


import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exceptions.minusVibe;
import org.example.main.Request;
import org.example.main.Response;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerMessaging {
    private static final Logger log = LogManager.getLogger(ServerMessaging.class);

    private ServerMessaging() {
    }


    public static Response nioRead(SocketChannel clientChannel) throws IOException, minusVibe {
        ByteBuffer buf = ByteBuffer.allocate(clientChannel.socket().getReceiveBufferSize());
        int readed= clientChannel.read(buf);
        if (readed > 0) {
            buf.flip();
            String s = new String(ByteBuffer.allocate(readed).put(buf.array(),0,readed).array());

            return ObjectConverter.deserialize( s, new TypeReference<>() {});

        } else throw new minusVibe();
    }
    public static void nioSend(SocketChannel clientChannel,String message) throws IOException {
        Request resp = new Request();
        resp.addMessage(message);
        message =ObjectConverter.toJson(resp);
        ByteBuffer buf = ByteBuffer.allocate(message.getBytes().length).put(message.getBytes());
        buf = buf.flip();
        while (buf.hasRemaining()){
            clientChannel.write(buf);
        }
        System.out.println("sended " + message);
    }
    public static void nioSend(SocketChannel clientChannel, Request resp) throws IOException {
        String message = ObjectConverter.toJson(resp);
        ByteBuffer buf = ByteBuffer.allocate(message.getBytes().length).put(message.getBytes());
        buf = buf.flip();
        while (buf.hasRemaining()){
            clientChannel.write(buf);
        }
        System.out.println("sended " + message);
    }
}
