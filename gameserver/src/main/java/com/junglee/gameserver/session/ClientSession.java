package com.junglee.gameserver.session;

import java.util.List;

import com.junglee.dbservice.model.Player;
import com.junglee.gameserver.game.Game;

public class ClientSession {
	enum Status
	{
		NOT_CONNECTED, CONNECTING, CONNECTED, CLOSED
	}
	
	public ClientSession(){
		status = Status.NOT_CONNECTED;
	}
	
	private Status status;
	private Player player;
	private ConnectionHandler handler;
	private List<Game> games;
}
