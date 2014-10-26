package wsChat;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import models.Chat;

import org.skife.jdbi.v2.DBI;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonSQL;



@ServerEndpoint(
		value = "/chat/{videoId}",
		configurator = WebSocketChatConfigurator.class
)
public class WebSocketChat {

	@OnOpen
	public void open(Session session, @PathParam("videoId") String videoId) {
		Integer videoIdInt = Integer.parseInt(videoId);
		session.getUserProperties().put("videoId", videoIdInt);
		Integer userId = (Integer) session.getUserProperties().get("userId");
		if (userId == null) close(session);
		
	}
	
	@OnMessage
	public void message(final Session session, String message) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		Chat chat = null;
		try {
			System.out.println(message);
			chat = mapper.readValue(message, Chat.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Integer videoId = (Integer) session.getUserProperties().get("videoId");
		chat.setVideoId(videoId);
		chat.setChatDate(new Date());
		chat.setUserId((Integer) session.getUserProperties().get("userId"));
		chat.setParentId(-1);
		final Chat temp = new Chat(chat);
		new Thread(new Runnable() {
			
			@Override
			public void run() {	
				ServletContext sc = (ServletContext) session.getUserProperties().get("sc");	
				DBI dbi = (DBI) sc.getAttribute("dbi");
				System.out.println(temp);
				CommonSQL.updateChatToDb(temp, dbi);
			}
		}).start();
		
		try {
			for (Session s : session.getOpenSessions()) {
				if (s.isOpen()
						&& videoId.equals(s.getUserProperties().get("videoId"))) {
					s.getBasicRemote().sendText(mapper.writeValueAsString(chat));
				}
			}
		} catch (IOException  e) {
			System.out.println(e.getMessage());
		}
	}
	
	@OnClose
	public void close(Session session){
		System.out.println("Session closed");
	}
	
	@OnError
	public void error(Session session, Throwable thr){
		System.out.println("Error got it's way");
		thr.printStackTrace();
	}
}
