package com.junglee.networkservice.server;


import com.junglee.networkservice.ClientConnection;
import com.junglee.networkservice.ClientEventDispatcher;
import com.junglee.networkservice.ConnectionContext;


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
		ClientEventDispatcher.getInstance().handleMessage(this, msg);
	}
	
	@Override
	public void handleConnectionClose(){
		ClientEventDispatcher.getInstance().handleConnectionClose(this);
	}
	
	private ConnectionContext connectionContext;

}
