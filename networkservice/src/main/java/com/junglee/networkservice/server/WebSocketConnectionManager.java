package com.junglee.networkservice.server;


import java.io.IOException;
import java.util.HashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;


import com.junglee.eventdispatcher.*;
import com.junglee.networkservice.ClientConnection;
import com.junglee.networkservice.ConnectionManager;

@ServerEndpoint("/gameServer")
public class WebSocketConnectionManager implements ConnectionManager {
	
	@Autowired
	EventDispatcher eventDispatcher;

	private HashMap<String, ClientConnection> sessionMap = new HashMap<String, ClientConnection>();

	@OnOpen
	public void onOpen(Session session) {
		ClientConnection connection = new WebSocketClientConnection(session);
		handleConnection(connection);
	}
	
	@OnMessage
	public void onMessage(String msg, Session session) throws IOException {
		ClientConnection clientConnection = sessionMap.get(session.getId());
		handleMessage(clientConnection, msg);
	}

	@OnClose
	public void onClose(CloseReason reason, Session session) {
		ClientConnection clientConnection = sessionMap.get(session.getId());
		handleDisconnected(clientConnection);
	}
	
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleConnection(ClientConnection connection) {
		Event event = new Event(connection, EventType.ClientConnected);
		eventDispatcher.submit(event);
	}
	
	@Override
	public void handleDisconnected(ClientConnection clientConnection) {
		Event event = new Event(clientConnection, EventType.ClientDisconnected);
		eventDispatcher.submit(event);
	}

	@Override
	public void handleMessage(ClientConnection connection, String message) {
		Event event = new Event(connection, EventType.MessageReceived);
		event.setMessage(message);
		eventDispatcher.submit(event);
	}
}
