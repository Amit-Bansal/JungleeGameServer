package com.junglee.gameserver.player;

import java.util.HashMap;

import com.junglee.dbservice.model.Player;

public class PlayerManager {
	public void loadPlayerDetails(){}
	public void createNewPlayer(){}
	public void sendPlayerDetailsToClient(){}
	
	public void updatePlayerConnectionTime(){}
	public void updatePlayerDisconnectionTime(){}
	
	public void TriggerPlayerDisconnectedEventToServices(){}
	
	public void PlayerRequestToJoinGame(){}
	
	public static Player getPlayer(int playerId){
		return playerMap.get(playerId);
	}
	
	private static HashMap<Integer, Player> playerMap;
}
