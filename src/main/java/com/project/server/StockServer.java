package com.project.server;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class StockServer extends WebSocketServer {

    private static final int PORT = 8080; // Change if needed
    private static final Set<WebSocket> clients = new CopyOnWriteArraySet<>();

    public StockServer() {
        super(new InetSocketAddress(PORT));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        clients.add(conn);
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        clients.remove(conn);
        System.out.println("Connection closed: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received: " + message);
        broadcast(message); // Send message to all clients
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("Error: " + ex.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("Stock Server started on port " + PORT);
    }

    public static void main(String[] args) {
        StockServer server = new StockServer();
        server.start();
        System.out.println("Stock Market Tracker Server is running...");
    }
}
