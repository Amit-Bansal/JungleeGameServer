package com.junglee.gameserver.session;


import com.junglee.gameserver.player.*;
import com.junglee.gameserver.application.App;
import com.junglee.gameserver.message.*;
import com.junglee.gameserver.task.Task;
import com.junglee.networkservice.ClientConnection;


public class ClientSession extends Task {
	enum Status
	{
		NOT_CONNECTED, CONNECTING, CONNECTED, AUTHENTICATED, CLOSED
	}
	
	public ClientSession(ClientConnection connection){
		handler = connection;
		setStatus(Status.CONNECTED);
		player = null;
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
			case USER_SIGNUP:{
				SignupMessage msg = (SignupMessage)currentMsg;
				SignupResponse response = new SignupResponse();
				if(App.getInstance().getPlayerManager().signup(msg.id, msg.name, msg.password))
					response.msg = "Account created successfully";
				else
					response.msg = "Could not signup";
				sendMessage(response);
				break;
			}
			case USER_LOGIN:{
				LoginMessage msg = (LoginMessage)currentMsg;
				player = App.getInstance().getPlayerManager().loginPlayer(msg.id, msg.password, this);
				LoginResponse response = new LoginResponse();
				if (player == null){
					response.code = 401;
					response.msg = "Not Authorised";
				}
				response.clientName = player.getName();
				response.code = 200;
				sendMessage(response);
				status = Status.AUTHENTICATED;
				break;
			}
			case USER_LOGOUT:{
				App.getInstance().getTableManager().handlePlayerLogout(player);
				App.getInstance().getPlayerManager().logoutPlayer(player);
				status = Status.CONNECTED;
				player = null;
				break;
			}
			case JOIN_TABLE:{
				//JoinTableMessage msg = (JoinTableMessage)currentMsg;
				App.getInstance().getTableManager().joinPlayerToTable(player);
				
				break;
			}
			case LEAVE_TABLE:{
				//LeaveTableMessage msg = (LeaveTableMessage)currentMsg;
				App.getInstance().getTableManager().leavePlayerFromTable(player);
				break;
			}
			default:
				break;
		}		
	}
	
	public void handleClose(){
		status = Status.NOT_CONNECTED;
		player = null;
	}
	
	public void handleDisconnected(){
		status = Status.NOT_CONNECTED;
		player = null;
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
