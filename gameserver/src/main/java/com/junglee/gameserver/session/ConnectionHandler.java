package com.junglee.gameserver.session;
import java.util.HashMap;

import com.junglee.gameserver.app.AppContext;

import com.junglee.networkservice.*;

public class ConnectionHandler extends ClientInterface{
			
	@Override
	public void handleConnection(ConnectionContext context){
		
	}
	
	public void register(){
		ConnectionManager manager = (ConnectionManager)AppContext.getBean("ConnectionManager");
		manager.registerHandler(this);
	}
	
	//private HashMap<ClientConnection, ClientSession> sessionMap;

}
