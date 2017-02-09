package com.junglee.gameserver.session;
import java.util.HashMap;

import com.junglee.gameserver.app.AppContext;
import com.junglee.networkservice.*;
import com.junglee.networkservice.server.WebSocketClientConnection;

public class ConnectionHandler extends ConnectionInterface{
			
	@Override
	public void handleConnection(ConnectionContext context){
		ClientConnection connection = new WebSocketClientConnection(context);
		ClientSession client = ClientSessionManager.getInstance().createSession(connection);
	}
	
	public void register(){
		ConnectionManager manager = (ConnectionManager)AppContext.getBean("ConnectionManager");
		manager.registerHandler(this);
	}
	
}
