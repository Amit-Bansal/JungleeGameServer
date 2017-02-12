package com.junglee.networkservice.server;


import java.io.IOException;

import com.junglee.networkservice.ClientConnection;
import com.junglee.networkservice.ClientEventDispatcher;
import com.junglee.networkservice.ConnectionContext;

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
	
	@Override
	public void receiveMessage(String msg){
		ClientEventDispatcher.getInstance().handleMessage(this, msg);
	}
	
	@Override
	public void handleConnectionClose(){
		ClientEventDispatcher.getInstance().handleConnectionClose(this);
	}
	
	private Session session;

}
