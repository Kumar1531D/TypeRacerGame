package com.example.typeracer;

import java.io.IOException;
import jakarta.websocket.Session;

public class ClientHandler implements Runnable {
    private final Session session;
    private final Room room;
    private boolean isReady;
    private boolean isFinished;
    private Thread thread;

    public ClientHandler(Session session, Room room) {
        this.session = session;
        this.room = room;
        this.isReady = false;
        this.isFinished = false;
    }

    public Session getSession() {
        return session;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Here, you can handle any per-client logic or periodic checks.
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }
}
