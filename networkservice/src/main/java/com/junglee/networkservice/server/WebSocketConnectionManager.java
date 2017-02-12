package com.junglee.networkservice.server;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.junglee.networkservice.*;

@ServerEndpoint("/gameServer")
public class WebSocketConnectionManager implements ConnectionManager {

	private List<ConnectionInterface> handlers = new ArrayList<ConnectionInterface>();

	@OnOpen
	public void onOpen(Session session) {
		ClientConnection connection = new WebSocketClientConnection(session);
		handleConnection(connection);
	}
	
	@OnMessage
	public void onMessage(String txt, Session session) throws IOException {

	}

	@OnClose
	public void onClose(CloseReason reason, Session session) {
		handleDisconnected();
	}
	
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleConnection(ClientConnection connection) {
		for(ConnectionInterface handler : handlers)   
			handler.handleConnection(connection);
	}
	
	@Override
	public void handleDisconnected() {		
		for(ConnectionInterface handler : handlers)   
			handler.handleDisconnected();
	}
	
	@Override
	public void registerHandler(ConnectionInterface handler) {
		handlers.add(handler);
	}

}
