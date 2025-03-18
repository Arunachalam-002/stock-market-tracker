package com.project.server;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class StockWebSocketServer extends WebSocketServer {
    private static final Set<WebSocket> connections = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public StockWebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connections.add(conn);
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connections.remove(conn);
        System.out.println("Connection closed: " + conn.getRemoteSocketAddress() + " Reason: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message: " + message);
        conn.send("Message received: " + message); // Echo message back
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("Error occurred: " + ex.getMessage());
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket Server started successfully!");
    }

    public void broadcastStockUpdate(String stockData) {
        for (WebSocket conn : connections) {
            conn.send(stockData);
        }
    }

    public static void main(String[] args) {
        int port = 8080; // Change port if needed
        StockWebSocketServer server = new StockWebSocketServer(port);
        server.start();
        System.out.println("WebSocket server started on port: " + port);
    }
}