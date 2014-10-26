package wsChat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebSocketChatConfigurator extends ServerEndpointConfig.Configurator{
	
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response){
		HttpSession session = (HttpSession) request.getHttpSession();
		ArrayList<String> invalidFlag = new ArrayList<String>();
		invalidFlag.add("invalid");
		if (session == null){
			response.getHeaders().put("invalid",invalidFlag);
		
		}
		else {
			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null){
				response.getHeaders().put("invalid", invalidFlag);
			}
			else {
				sec.getUserProperties().put("userId", userId);
				sec.getUserProperties().put("sc",session.getServletContext());
			}
		}
	}
	

}
