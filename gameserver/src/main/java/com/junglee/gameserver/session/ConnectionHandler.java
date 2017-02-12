package com.junglee.gameserver.session;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.junglee.networkservice.*;

@Component
public class ConnectionHandler extends ConnectionInterface{
	
	@Autowired
	SessionManager sessionManager;
	
	private List<ClientSession> clientSessionList;
	
	@Autowired
	private ConnectionManager manager;
	
	public void startServer(){
		manager.registerHandler(this);
		manager.start();
	}
	
	@Override
	public void handleConnection(ClientConnection connection){
		ClientSession session = sessionManager.createSession(connection);
		clientSessionList.add(session);
	}
	
	
	@Override
	public void handleDisconnected() {
		for(ClientSession session:clientSessionList){
			session.handleDisconnected();
		}
		clientSessionList.clear();
	}
	
	
}
