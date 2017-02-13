package com.junglee.networkservice.server;


import java.io.IOException;

import com.junglee.networkservice.ClientConnection;


import javax.websocket.Session;

public class WebSocketClientConnection implements ClientConnection{
	
	public WebSocketClientConnection(Session session){
		this.session = session;
	}
	
	@Override
	public void sendMessage(String msg) {
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private Session session;

}
