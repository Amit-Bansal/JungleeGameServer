package com.junglee.networkservice.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
		for(ConnectionInterface handler : handlers)   
			handler.handleConnection(context);
	}
	
	
	@Override
	public void registerHandler(ConnectionInterface handler) {
		handlers.add(handler);
	}
	
	private List<ConnectionInterface> handlers = new ArrayList<ConnectionInterface>();

}
