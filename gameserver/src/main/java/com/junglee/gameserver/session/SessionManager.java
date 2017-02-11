package com.junglee.gameserver.session;

import java.util.HashMap;

import com.junglee.gameserver.application.App;
import com.junglee.gameserver.event.Event;
import com.junglee.gameserver.event.EventType;
import com.junglee.gameserver.message.Message;
import com.junglee.networkservice.*;


public class SessionManager extends ClientInterface{
	
	public SessionManager() {}
		

	public void handleMessage(ClientConnection connection, String msgStr){
		Message msg = Message.deserialize(msgStr);
		ClientSession session = clientSession.get(connection);
		session.setCurrentMsg(msg);
		
		App.getInstance().getTaskScheduler().processTask(session);
	}
	
	public void handleConnectionClose(ClientConnection connection){
		ClientSession session = clientSession.get(connection);
		session.handleClose();
		Event connectionCloseEvent = new Event(session, EventType.ClientDisconnected);
		App.getInstance().getEventDispather().submit(connectionCloseEvent);
	}
	
	public ClientSession createSession(ClientConnection connection){
		
		ClientSession session = new ClientSession(connection);
		clientSession.put(connection, session);
		return session;
	}
	
	public ClientSession getSession(int sessionId){
		
		return clientSession.get(sessionId);
	}
	
	public void register(){
		ClientEventDispatcher.getInstance().registerHandler(this);
	}
	
	private HashMap<ClientConnection, ClientSession> clientSession;


}
