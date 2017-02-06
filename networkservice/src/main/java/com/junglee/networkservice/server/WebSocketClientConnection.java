package com.junglee.networkservice.server;

import java.net.Socket;

import com.junglee.networkservice.ClientConnection;
import com.junglee.networkservice.ConnectionContext;

public class WebSocketClientConnection implements ClientConnection{
	
	public WebSocketClientConnection(ConnectionContext context){
		connectionContext = context;
		//other DS initializations
	}
	
	@Override
	public void sendMessage() {
		System.out.println("Inside sendMessage");
	}
	
	@Override
	public void receiveMessage() {
		System.out.println("Inside receiveMessage");
	}
	
  
	private ConnectionContext connectionContext;
}
