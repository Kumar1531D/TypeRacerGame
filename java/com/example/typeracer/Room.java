package com.example.typeracer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private final String roomId;
    private final Map<ClientSession, Boolean> clients = Collections.synchronizedMap(new HashMap<>());
    private final Map<ClientSession, Boolean> finishedClients = Collections.synchronizedMap(new HashMap<>());
    private final String textContent;
    private boolean gameStarted = false;

    Room(String roomId, String textContent) {
        this.roomId = roomId;
        this.textContent = textContent;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void addClient(ClientSession session) {
        if (gameStarted) {
            sendMessageToClient(session, "ROOM_IN_GAME");
            return;
        }
        clients.put(session, false);
        finishedClients.put(session, false);
        sendMessageToClient(session, "TEXT:" + textContent);
    }

    public void removeClient(ClientSession session) {
        clients.remove(session);
        finishedClients.remove(session);
    }

    public void setClientReady(ClientSession session) {
        clients.put(session, true);
    }

    public boolean checkAllClientsReady() {
        for (boolean b : clients.values()) {
            if (!b) {
                return false;
            }
        }
        startGame();
        return true;
    }

    public void setClientFinished(ClientSession session) {
        finishedClients.put(session, true);
    }

    public boolean areAllClientsFinished() {
        for (Boolean finished : finishedClients.values()) {
            if (!finished) {
                return false;
            }
        }
        return true;
    }

    public void startGame() {
        gameStarted = true;
    }

    public void resetGame() {
        gameStarted = false;
        for (ClientSession session : clients.keySet()) {
            clients.put(session, false);
            finishedClients.put(session, false);
        }
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void broadcastProgress(String sessionId, String progress) {
        broadcastMessage("PROGRESS:" + progress);
    }

    public void broadcastMessage(String message) {
        synchronized (clients) {
            for (ClientSession client : clients.keySet()) {
                sendMessageToClient(client, message);
            }
        }
    }

    private void sendMessageToClient(ClientSession session, String message) {
        try {
            session.getOutput().write(message.getBytes(StandardCharsets.UTF_8));
            session.getOutput().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//import java.io.IOException;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
////public class Room {
////    private final String roomId;
////    private final Map<WebSocketServer.ClientHandler, Boolean> clients = Collections.synchronizedMap(new HashMap<>());
////    private final Map<WebSocketServer.ClientHandler, Boolean> finishedClients = Collections.synchronizedMap(new HashMap<>());
////    private final String textContent;
////    private boolean gameStarted = false;
////
////    public Room(String roomId, String textContent) {
////        this.roomId = roomId;
////        this.textContent = textContent;
////    }
////
////    public String getRoomId() {
////        return roomId;
////    }
////
////    public String getTextContent() {
////        return textContent;
////    }
////
////    public void addClient(WebSocketServer.ClientHandler client) throws IOException {
////        if (gameStarted) {
////            client.sendMessage("ROOM_IN_GAME");
////            return;
////        }
////        clients.put(client, false);
////        finishedClients.put(client, false);
////        client.sendMessage("TEXT:" + textContent);
////    }
////
////    public void removeClient(WebSocketServer.ClientHandler client) {
////        clients.remove(client);
////        finishedClients.remove(client);
////    }
////
////    public void setClientReady(WebSocketServer.ClientHandler client) {
////        clients.put(client, true);
////    }
////
////    public boolean checkAllClientsReady() {
////        for (boolean b : clients.values()) {
////            if (!b) {
////                return false;
////            }
////        }
////        startGame();
////        return true;
////    }
////
////    public void setClientFinished(WebSocketServer.ClientHandler client) {
////        finishedClients.put(client, true);
////    }
////
////    public boolean areAllClientsFinished() {
////        for (Boolean finished : finishedClients.values()) {
////            if (!finished) {
////                return false;
////            }
////        }
////        return true;
////    }
////
////    public void startGame() {
////        gameStarted = true;
////    }
////
////    public void resetGame() {
////        gameStarted = false;
////        for (WebSocketServer.ClientHandler client : clients.keySet()) {
////            clients.put(client, false);
////            finishedClients.put(client, false);
////            
////        }
////    }
////
////    public boolean isGameStarted() {
////        return gameStarted;
////    }
////
////    public void broadcastProgress(String progress) throws IOException {
////        broadcastMessage("PROGRESS:" + progress);
////    }
////
////    public void broadcastMessage(String message) throws IOException {
////        synchronized (clients) {
////            for (WebSocketServer.ClientHandler client : clients.keySet()) {
////                if (!client.socket.isClosed()) {
////                    client.sendMessage(message);
////                }
////            }
////        }
////    }
////}
//
//
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import jakarta.websocket.Session;
//
//public class Room {
//	
//	private final String RoomId;
//	private final Map<Session,Boolean> clients = Collections.synchronizedMap(new HashMap<>());
//	private final Map<Session,Boolean> finishedClients = Collections.synchronizedMap(new HashMap<>());
//	private final String textContent;
//	private boolean gameStarted = false;
//	private final int MAX_CLIENTS = 4;
//	
//	Room(String rId,String textContent){
//		this.RoomId = rId;
//		this.textContent = textContent;
//	}
//	
//	public String getRoomId() {
//		return RoomId;
//	}
//	
//	public String getTextContent() {
//		return textContent;
//	}
//	
//	public void addClient(Session session) throws IOException {
//		if(gameStarted) {
//			session.getBasicRemote().sendText("ROOM_IN_GAME");
//			return;
//		}
//		if (clients.size() >= MAX_CLIENTS) {
//            session.getBasicRemote().sendText("ROOM_FULL");
//            return;
//        }
//		clients.put(session,false);
//		finishedClients.put(session, false);
//		session.getBasicRemote().sendText("TEXT:" + textContent);
//	}
//	
//	public void removeClient(Session session) {
//		clients.remove(session);
//		finishedClients.remove(session);
//	}
//	
//	public void setClientReady(Session session) {
//		clients.put(session, true);
//	}
//	
//	public void broadcastProgress(String sessionId, String progressMessage) throws IOException {
//        broadcastMessage("PROGRESS:" + sessionId + ":" + progressMessage);
//    }
//	
//	public boolean checkAllClientsReady() {
//		for(boolean b: clients.values()) {
//			if(!b) {
//				return false;
//			}
//		}	
//		startGame();
//		return true;
//	}
//	public void setClientFinished(Session session) {
//        finishedClients.put(session, true);
//    }
//
//    public boolean areAllClientsFinished() {
//        for (Boolean finished : finishedClients.values()) {
//            if (!finished) {
//                return false;
//            }
//        }
//        
//        return true;
//    }
//	
//	public void startGame() {
//		gameStarted = true;
//	}
//	
//	public void resetGame() {
//		gameStarted = false;
//		for(Session session : clients.keySet()) {
//			clients.put(session, false);
//			finishedClients.put(session, false);
//		}
//	}
//	
//	public boolean isGameStarted() {
//		return gameStarted;
//	}
//	
//	public void broadcastProgress(String progress) throws IOException {
//        broadcastMessage("PROGRESS:" + progress);
//    }
//	
//	public void broadcastMessage(String message) throws IOException {
//        synchronized (clients) {
//            for (Session client : clients.keySet()) {
//                if (client.isOpen()) {
//                    client.getBasicRemote().sendText(message);
//                }
//            }
//        }
//    }
//
//}
