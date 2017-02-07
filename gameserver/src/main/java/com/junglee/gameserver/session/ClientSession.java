package com.junglee.gameserver.session;

import java.util.List;

import com.junglee.dbservice.model.Player;
import com.junglee.gameserver.game.Game;
import com.junglee.gameserver.message.Message;
import com.junglee.gameserver.message.MessageType;
import com.junglee.gameserver.task.Task;
import com.junglee.networkservice.ClientConnection;
import com.junglee.networkservice.ConnectionContext;

public class ClientSession extends Task {
	enum Status
	{
		NOT_CONNECTED, CONNECTING, CONNECTED, CLOSED
	}
	
	public ClientSession(ClientConnection connection){
		handler = connection;
		status = Status.NOT_CONNECTED;
	}
	
	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	
	@Override
	public void process() {
		switch(currentMsg.getType()){
			case USER_LOGIN:
				break;
			default:
				break;
		}		
	}
	
	public Message getCurrentMsg() {
		return currentMsg;
	}

	public void setCurrentMsg(Message currentMsg) {
		this.currentMsg = currentMsg;
	}

	private Status status;
	private Player player;
	private ClientConnection handler;
	private List<Game> games;
	private int sessionId;
	private Message currentMsg;
}
