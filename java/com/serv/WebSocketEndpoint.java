package com.serv;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws")
public class WebSocketEndpoint {

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // Broadcast the received message to all clients
        synchronized (clients) {
            for (Session client : clients) {
                if (!client.equals(session)) {
                    client.getBasicRemote().sendText(message);
                }
            }
        }
    }

    // Method to send message to all clients
    public static void sendMessage(String message) throws IOException {
        synchronized (clients) {
            for (Session client : clients) {
                client.getBasicRemote().sendText(message);
            }
        }
    }
}