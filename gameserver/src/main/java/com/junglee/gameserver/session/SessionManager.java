package com.junglee.gameserver.session;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.junglee.eventdispatcher.*;
import com.junglee.gameserver.message.Message;
import com.junglee.gameserver.task.TaskScheduler;
import com.junglee.networkservice.*;

@Component
public class SessionManager implements EventListener{
	
	@Autowired
	EventDispatcher eventDispatcher;
	
	@Autowired
	@Qualifier("schedulerService")
	TaskScheduler taskScheduler;
	
	@Autowired
	private ConnectionManager manager;
	
	private HashMap<ClientConnection, ClientSession> clientSessionMap;
		
		
	public ClientSession getSession(ClientConnection connection){
		return clientSessionMap.get(connection);
	}
	
	public void handleMessage(ClientConnection connection, String msgStr){
		Message msg = Message.deserialize(msgStr);
		ClientSession session = clientSessionMap.get(connection);
		session.setCurrentMsg(msg);
		taskScheduler.processTask(session);
	}
	
	@Override
	public void executeEvent(Event event) {
		ClientConnection connection = (ClientConnection)event.getSource();
		if (event.getType() == EventType.ClientConnected){
			ClientSession session = new ClientSession(connection);
			clientSessionMap.put(connection, session);
		}
		else if (event.getType() == EventType.ClientDisconnected){
			ClientSession session = clientSessionMap.get(connection);
			session.handleDisconnected();
		}
		else if (event.getType() == EventType.MessageReceived){
			handleMessage(connection, event.getMessage());
		}
	}
}
