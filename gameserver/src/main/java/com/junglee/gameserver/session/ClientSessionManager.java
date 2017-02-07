package com.junglee.gameserver.session;

import java.util.HashMap;

import com.junglee.gameserver.app.AppContext;
import com.junglee.gameserver.game.GameManager;
import com.junglee.gameserver.message.Message;
import com.junglee.gameserver.task.ExecutorScheduler;
import com.junglee.networkservice.*;


public class ClientSessionManager extends ClientInterface{
	
	private static ClientSessionManager instance = null;
	private static Object mutex = new Object();
	private ClientSessionManager() {}
	
	public static ClientSessionManager getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null) {
					instance = new ClientSessionManager();
				}
			}
		}
		return instance;
	}
	
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
	
	public ClientSession getSession(){
		return null;
	}
	
	public void register(){
		ClientConnection manager = (ClientConnection)AppContext.getBean("ClientConnection");
		manager.registerHandler(this);
	}
	
	private HashMap<Integer, ClientSession> clientSession;
}
