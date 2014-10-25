package wsChat;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class WebSocketChatConfigurator extends ServerEndpointConfig.Configurator{
	
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response){
		HttpSession session = (HttpSession) request.getHttpSession();
		if (session == null){
		
		}
		else {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId!= null)sec.getUserProperties().put("userId", userId);
		}
	}

}
