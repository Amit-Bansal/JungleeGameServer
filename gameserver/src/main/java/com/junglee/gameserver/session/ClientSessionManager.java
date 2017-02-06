package com.junglee.gameserver.session;

import java.util.HashMap;
import com.junglee.networkservice.ClientConnection;

public class ClientSessionManager {
	
	public Boolean createSession(ClientConnection connection){
		return true;
	}
	
	public ClientSession getSession(){
		return null;
	}
	
	
	private HashMap<Integer, ClientSession> clientSession;
}
