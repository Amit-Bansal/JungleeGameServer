package com.junglee.gameserver.player;

import java.util.HashMap;

import com.junglee.dbservice.model.Player;
import com.junglee.gameserver.game.GameManager;

public class PlayerManager {
	
	private static PlayerManager instance = null;
	private static Object mutex = new Object();
	private PlayerManager() {}
	
	public static PlayerManager getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null) {
					instance = new PlayerManager();
				}
			}
		}
		return instance;
	}
	
	public void loadPlayerDetails(){}
	public void createNewPlayer(){}
	public void sendPlayerDetailsToClient(){}
	
	public void updatePlayerConnectionTime(){}
	public void updatePlayerDisconnectionTime(){}
	
	public void TriggerPlayerDisconnectedEventToServices(){}
	
	public void PlayerRequestToJoinGame(){}
	
	public Player getPlayer(int playerId){
		return playerMap.get(playerId);
	}
	
	private static HashMap<Integer, Player> playerMap;
}
