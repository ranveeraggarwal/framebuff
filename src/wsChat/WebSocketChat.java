package wsChat;

import java.io.IOException;
import java.util.Date;

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
import common.CommonSQL;



@ServerEndpoint(
		value = "/chat/{videoId}",
		configurator = WebSocketChatConfigurator.class
)
public class WebSocketChat {

	@OnOpen
	public void open(Session session, @PathParam("videoId") String videoId) throws IOException {
		Integer videoIdInt = null;
		try{
			videoIdInt = Integer.parseInt(videoId);
		} catch (Exception e){
			session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "video Id not found"));
			return;
		}
		session.getUserProperties().put("videoId", videoIdInt);
		Integer userId = (Integer) session.getUserProperties().get("userId");
		if (userId == null) closeWithReason(session, null);
		
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
		
		Integer chatId = CommonSQL.updateChatToDb(chat);
		chat.setChatId(chatId);
		
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
	public void closeWithReason(Session session, CloseReason reason){
		System.out.println("session is closed due to "+reason.getReasonPhrase());
	}
	
	@OnError
	public void error(Session session, Throwable thr){
		System.out.println("Error got it's way");
		thr.printStackTrace();
	}
}
