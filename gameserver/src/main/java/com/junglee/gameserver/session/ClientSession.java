package com.junglee.gameserver.session;


import com.junglee.dbservice.model.Player;
import com.junglee.gameserver.app.App;
import com.junglee.gameserver.message.JoinTableMessage;
import com.junglee.gameserver.message.Message;
import com.junglee.gameserver.task.Task;
import com.junglee.networkservice.ClientConnection;


public class ClientSession extends Task {
	enum Status
	{
		NOT_CONNECTED, CONNECTING, CONNECTED, CLOSED
	}
	
	public ClientSession(ClientConnection connection){
		handler = connection;
		setStatus(Status.NOT_CONNECTED);
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
			case USER_SIGNUP:
				break;
			case JOIN_TABLE:
				JoinTableMessage joinMSg = (JoinTableMessage)currentMsg;
				App.getInstance().getTableManager().handlePlayerJoinTableMessage(joinMSg);
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
	
	public void sendMessage(Message msg){
		String msgStr = msg.serialize();
		handler.sendMessage(msgStr);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	private Status status;
	private ClientConnection handler;
	private int sessionId;
	private Message currentMsg;
	private Player player;
}
