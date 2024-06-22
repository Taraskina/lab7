package org.example.main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

public class Client {

    protected DatagramChannel socket;
    private InetAddress address;
    private int port;
    boolean firstMessageFromClient = true;


    public Client(DatagramChannel clientChannel) {clientChannel = socket;
    }

    public boolean isFirstMessageFromClient() {
        return firstMessageFromClient;
    }

    public void setFirstMessageFromClient(boolean firstMessageFromClient) {
        this.firstMessageFromClient = firstMessageFromClient;
    }

    public DatagramChannel getSocket() {
        return socket;
    }

    public void setSocket(DatagramChannel socket) {
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

}


