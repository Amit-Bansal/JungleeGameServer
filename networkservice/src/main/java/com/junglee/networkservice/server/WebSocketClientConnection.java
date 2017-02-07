package com.junglee.networkservice.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.junglee.networkservice.ClientConnection;
import com.junglee.networkservice.ClientInterface;
import com.junglee.networkservice.ConnectionContext;
import com.junglee.networkservice.ConnectionInterface;

public class WebSocketClientConnection implements ClientConnection{
	
	public WebSocketClientConnection(ConnectionContext context){
		connectionContext = context;
		//other DS initializations
	}
	
	@Override
	public void sendMessage(String msg) {
		connectionContext.sendMessage(msg);
		System.out.println("Inside sendMessage");
	}
	
	@Override
	public void receiveMessage(){
		String msg = connectionContext.readMessage();
		for (ClientInterface handler : handlers){ 
			handler.handleMessage(msg);
		}
	}
	

	private ConnectionContext connectionContext;
		
	@Override
	public void registerHandler(ClientInterface handler) {
		handlers.add(handler);
	}
	
	private List<ClientInterface> handlers = new ArrayList<ClientInterface>();
}
