package com.example.typeracer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.serv.dao.InsertDAO;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/typeracer/{roomId}")
public class TyperacerEndpoint {
	
	private static final Map<String,Room> rooms = Collections.synchronizedMap(new HashMap<>());
	private static final Map<String, Queue<Session>> waiting_list = new ConcurrentHashMap<>();
	public static final Map<String,String> textForRoom = new HashMap<>();
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);
		
	@OnOpen
	public void onOpen(Session session,@PathParam("roomId") String roomId) throws IOException {
		System.out.println("Open is called");
		String text = textForRoom.getOrDefault(roomId, GenerateText.get());
		Room room = rooms.computeIfAbsent(roomId,id -> new Room(id,text));
		System.out.println("New Client added in "+roomId+" " + session.getId());
		if(room.isGameStarted()) {
			waiting_list.computeIfAbsent(roomId, k -> new LinkedList<>()).add(session);
            session.getBasicRemote().sendText("WAIT");
		}
		else {
		//	room.addClient(session);
		}
	}
	
	@OnClose
    public void onClose(Session session,@PathParam("roomId") String roomId) {
		Room room = rooms.get(roomId);
		if(room!=null)
		//	room.removeClient(session);
        System.out.println("Connection close in room "+roomId+" "+session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session,@PathParam("roomId") String roomId) throws IOException {
    	Room room = rooms.get(roomId);
    	
		if (room != null) {
			if (message.startsWith("READY")) {
//                room.setClientReady(session);
                if (room.checkAllClientsReady()) {
                    room.broadcastMessage("START");
                }
            }
			else if(message.startsWith("RESULT")) {
				textForRoom.put(roomId, GenerateText.get());
//				room.setClientFinished(session);
				room.broadcastMessage(message);
				InsertDAO in = new InsertDAO();
				in.addHistory(GetName.get(message),in.getHistory(GetName.get(message))+"\n"+message);
			
				if(room.areAllClientsFinished() || message.endsWith("time")) {
					System.out.println("Inside button");
					room.resetGame();
					room.broadcastMessage("REMATCH");
					processWaitingList(roomId);
				}
			}
			else if (message.startsWith("PROGRESS:")) {
                room.broadcastProgress(session.getId(),message.substring(9));
            }
			else {
				room.broadcastMessage(session.getId() + " : " + message);
				System.out.println("Message received in Room " + roomId + " : " + message + " from " + session.getId());
			}
		}
    }
    
    public void processWaitingList(String roomId) {
    	Queue<Session> queue = waiting_list.get(roomId);
        Room room = rooms.get(roomId);
        if (queue != null && room != null) { 
            while (!queue.isEmpty()) {
                Session session = queue.poll();
                System.out.println("Yes process is called");
                try {
                    if (session.isOpen()) {
//                        room.addClient(session);
                        session.getBasicRemote().sendText("ENTER");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
    
    private static String extractRoomIdFromRequest(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String data;
        String requestLine = reader.readLine();
        if (requestLine != null) {
            String[] requestParts = requestLine.split(" ");
            if (requestParts.length >= 2) {
                String path = requestParts[1];
                String[] pathParts = path.split("/");
                if (pathParts.length >= 3) {
                    return pathParts[3];  
                }
            }
        }

        return "defaultRoomId"; // Fallback room ID if extraction fails
    }
    
    private static String generateSessionId() {
        return UUID.randomUUID().toString();
    }
    
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8001)) {
            System.out.println("WebSocket server started on port 8001");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("socket is "+socket);
                String roomId = extractRoomIdFromRequest(socket);
                String sessionId = generateSessionId();
                ClientSession session = new ClientSession(sessionId, socket.getOutputStream());
                System.out.println("Room id is : "+roomId);
              threadPool.execute(new WebSocketConnectionHandler(socket,session,roomId));
            }
        }
    }

}

