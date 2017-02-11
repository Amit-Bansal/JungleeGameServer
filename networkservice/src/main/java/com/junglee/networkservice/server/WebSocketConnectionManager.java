package com.junglee.networkservice.server;


import java.util.ArrayList;

import java.util.List;

import com.junglee.networkservice.*;


public class WebSocketConnectionManager implements ConnectionManager {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleConnection(String data) {
		ConnectionContext context = new ConnectionContext(data);
		ClientConnection connection = new WebSocketClientConnection(context);
		
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
	
	private List<ConnectionInterface> handlers = new ArrayList<ConnectionInterface>();

}
