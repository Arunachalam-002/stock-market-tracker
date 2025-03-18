package com.project.server;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class StockServer extends WebSocketServer {

    private static final int PORT = 8080;
    private static final Set<WebSocket> clients = new CopyOnWriteArraySet<>();
    private static final Random random = new Random();

    public StockServer() {
        super(new InetSocketAddress(PORT));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        clients.add(conn);
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
        conn.send("Welcome! Live stock prices will start updating.");

        // Start sending stock prices to the client every 2 seconds
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                double stockPrice = 1000 + (random.nextDouble() * 200); // Simulated stock price
                broadcast("Stock Price: $" + String.format("%.2f", stockPrice));
            }
        }, 0, 2000); // Send update every 2 seconds
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        clients.remove(conn);
        System.out.println("Connection closed: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received from client: " + message);
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
