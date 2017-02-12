package com.junglee.gameserver.session;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.junglee.gameserver.event.Event;
import com.junglee.gameserver.event.EventDispatcher;
import com.junglee.gameserver.event.EventType;
import com.junglee.gameserver.message.Message;
import com.junglee.gameserver.task.TaskScheduler;
import com.junglee.networkservice.*;

@Component
public class SessionManager extends ClientInterface{
	
	@Autowired
	EventDispatcher eventDispatcher;
	
	@Autowired
	@Qualifier("schedulerService")
	TaskScheduler taskScheduler;
		

	public void handleMessage(ClientConnection connection, String msgStr){
		Message msg = Message.deserialize(msgStr);
		ClientSession session = clientSession.get(connection);
		session.setCurrentMsg(msg);
		
		taskScheduler.processTask(session);
	}
	
	public void handleConnectionClose(ClientConnection connection){
		ClientSession session = clientSession.get(connection);
		session.handleClose();
		Event connectionCloseEvent = new Event(session, EventType.ClientDisconnected);
		eventDispatcher.submit(connectionCloseEvent);
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
