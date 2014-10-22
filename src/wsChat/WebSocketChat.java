package wsChat;

import java.io.IOException;
import java.util.Date;

import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import models.Chat;

import com.fasterxml.jackson.databind.ObjectMapper;



@ServerEndpoint(
		value = "/chat/{room}"
)
public class WebSocketChat {

	@OnOpen
	public void open(Session session, @PathParam("room") String room){
		session.getUserProperties().put("room", room);
	}
	
	@OnMessage
	public void message(Session session, String message){
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
	
	
	@OnError
	public void error(Session session, Throwable thr){
		
	}
}
