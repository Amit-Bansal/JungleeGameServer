package com.junglee.gameserver.session;


import java.util.List;

import com.junglee.gameserver.application.App;
import com.junglee.networkservice.*;


public class ConnectionHandler extends ConnectionInterface{
			
	public ConnectionHandler(Object beanObject){
		manager = (ConnectionManager)beanObject;
		manager.registerHandler(this);
	}
	
	public void startServer(){
		manager.start();
	}
	
	@Override
	public void handleConnection(ClientConnection connection){
		ClientSession session = App.getInstance().getSessionManager().createSession(connection);
		clientSessionList.add(session);
	}
	
	
	@Override
	public void handleDisconnected() {
		for(ClientSession session:clientSessionList){
			session.handleDisconnected();
		}
		clientSessionList.clear();
	}
	
	private List<ClientSession> clientSessionList;
	private ConnectionManager manager;
}
