package wsChat;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;



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
		String room = (String) session.getUserProperties().get("room");
		try {
			for (Session s : session.getOpenSessions()) {
				if (s.isOpen()
						&& room.equals(s.getUserProperties().get("room"))) {
					s.getBasicRemote().sendText(message);
				}
			}
		} catch (IOException  e) {
			System.out.println(e.getMessage());
		}
	}
}
