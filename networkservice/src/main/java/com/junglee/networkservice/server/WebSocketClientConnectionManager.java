package com.junglee.networkservice.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.junglee.networkservice.*;


public class WebSocketClientConnectionManager implements ConnectionManager {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleConnection() {
		// TODO Auto-generated method stub                   
	}
	
	
	@Override
	public void registerHandler(ClientInterface handler) {
		handlers.add(handler);
	}
	
	private List<ClientInterface> handlers = new ArrayList<ClientInterface>();

}
