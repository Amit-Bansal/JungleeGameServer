package com.junglee.gameserver.session;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.junglee.dbservice.model.PlayerModel;
import com.junglee.gameserver.player.*;
import com.junglee.gameserver.message.*;
import com.junglee.gameserver.table.TableManager;
import com.junglee.gameserver.task.Task;
import com.junglee.networkservice.ClientConnection;


public class ClientSession extends Task {
	
	@Autowired
	PlayerManager playerManager;
	
	@Autowired
	TableManager tableManager;
	
	private Status status;
	private ClientConnection connection;
	private Message currentMsg;
	
	enum Status
	{
		NOT_CONNECTED, CONNECTING, CONNECTED, AUTHENTICATED, CLOSED
	}
	
	public ClientSession(ClientConnection clientCconnection){
		connection = clientCconnection;
		setStatus(Status.CONNECTED);
	}
		
	@Override
	public void process() {
		switch(currentMsg.getType()){
			case USER_SIGNUP:{
				SignupMessage msg = (SignupMessage)currentMsg;
				SignupResponse response = new SignupResponse();
				if (playerManager.signup(msg.id, msg.name, msg.password))
					response.msg = "Account created successfully";
				else
					response.msg = "Could not signup";
				sendMessage(response);
				break;
			}
			case USER_LOGIN:{
				LoginMessage msg = (LoginMessage)currentMsg;
				PlayerModel player = playerManager.loginPlayer(msg.id, msg.password, connection);
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
				tableManager.handlePlayerLogout(connection);
				playerManager.logoutPlayer(connection);
				status = Status.CONNECTED;
				break;
			}
			case JOIN_TABLE:{
				//JoinTableMessage msg = (JoinTableMessage)currentMsg;
				tableManager.joinPlayerToTable(connection);
				
				break;
			}
			case LEAVE_TABLE:{
				//LeaveTableMessage msg = (LeaveTableMessage)currentMsg;
				tableManager.leavePlayerFromTable(connection);
				break;
			}
			default:
				break;
		}		
	}
	
	
	public void handleDisconnected(){
		status = Status.NOT_CONNECTED;
	}
	
	public Message getCurrentMsg() {
		return currentMsg;
	}

	public void setCurrentMsg(Message currentMsg) {
		this.currentMsg = currentMsg;
	}
	
	public void sendMessage(Message msg){
		String msgStr = msg.serialize();
		connection.sendMessage(msgStr);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
