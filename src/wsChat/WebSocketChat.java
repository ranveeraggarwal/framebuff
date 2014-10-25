package wsChat;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import models.Chat;

import com.fasterxml.jackson.databind.ObjectMapper;



@ServerEndpoint(
		value = "/chat/{room}",
		configurator = WebSocketChatConfigurator.class
)
public class WebSocketChat {
	private static Map<String, Queue<Session>> queue = new HashMap<String, Queue<Session>>();

	@OnOpen
	public void open(Session session, @PathParam("room") String room) throws IOException{
		session.getUserProperties().put("room", room);
		Integer userId = (Integer) session.getUserProperties().get("userId");
		System.out.println(userId);
		if (userId == null) close(session);
		
	}
	
	@OnMessage
	public void message(Session session, String message) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		Chat chat = null;
		try {
			chat = mapper.readValue(message, Chat.class);
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		
		String room = (String) session.getUserProperties().get("room");
		chat.setChatroom(room);
		chat.setDate(new Date());
		try {
			for (Session s : session.getOpenSessions()) {
				if (s.isOpen()
						&& room.equals(s.getUserProperties().get("room"))) {
					s.getBasicRemote().sendText(mapper.writeValueAsString(chat));
				}
			}
		} catch (IOException  e) {
			System.out.println(e.getMessage());
		}
	}
	
	@OnClose
	public void close(Session session) throws IOException{
		System.out.println("Session closed");
		System.out.println(session);
		session.close();
	}
	
	@OnError
	public void error(Session session, Throwable thr){
		
	}
}
