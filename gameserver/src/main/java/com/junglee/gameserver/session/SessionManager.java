package com.junglee.gameserver.session;

import java.util.HashMap;

import com.junglee.gameserver.app.AppContext;
import com.junglee.gameserver.message.Message;
import com.junglee.gameserver.task.ExecutorScheduler;
import com.junglee.networkservice.*;


public class SessionManager extends ClientInterface{
	

	public SessionManager() {}
		
	public void handleMessage(String msgStr){
		Message msg = Message.deserialize(msgStr);
		Integer sessionId = msg.getSessionId();
		ClientSession session = clientSession.get(sessionId);
		session.setCurrentMsg(msg);
		ExecutorScheduler.getInstance().processTask(session);
	}
	
	public ClientSession createSession(ClientConnection connection){
		
		ClientSession session = new ClientSession(connection);
		clientSession.put(session.getSessionId(), session);
		return session;
	}
	
	public ClientSession getSession(int sessionId){
		
		return clientSession.get(sessionId);
	}
	
	public void register(){
		ClientConnection manager = (ClientConnection)AppContext.getBean("ClientConnection");
		manager.registerHandler(this);
	}
	
	private HashMap<Integer, ClientSession> clientSession;
}
