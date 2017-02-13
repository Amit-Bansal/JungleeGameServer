package com.junglee.gameserver.player;



import java.sql.Timestamp;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.junglee.dbservice.model.PlayerModel;
import com.junglee.dbservice.service.DatabaseService;
import com.junglee.eventdispatcher.*;
import com.junglee.networkservice.ClientConnection;

@Component
public class PlayerManager implements EventListener{
		
	@Autowired
	DatabaseService databaseService;
	
	private static HashMap<ClientConnection, PlayerModel> playerMap;
	
	public boolean signup(String id, String name, String password){
		PlayerModel player = new PlayerModel();
		player.setId(id);
		player.setName(name);
		databaseService.persistPlayer(player);
		return true;
	}
			
	private PlayerModel loadPlayer(String id){
		PlayerModel player =  databaseService.findPlayerById(id);
		return player;
	}
	
	public PlayerModel loginPlayer(String id, String password, ClientConnection connection){
		//verify player credentials - to do
		PlayerModel player = loadPlayer(id);
		if (player != null){
			synchronized(playerMap)
			{
				playerMap.put(connection, player);
			}
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			player.setLastLoginTime(timestamp);
		}
		return player;

	}
	
	public void logoutPlayer(ClientConnection connection){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		PlayerModel player = playerMap.get(connection);
		player.setLastLogoutTime(timestamp);

		databaseService.updatePlayer(player);
		synchronized(playerMap)
		{
			playerMap.remove(connection);
		}
	}
	
	public PlayerModel getPlayer(ClientConnection connection){
		return playerMap.get(connection);
	}
	
	public void incrementPlayerGameCount(ClientConnection connection){
		PlayerModel player = getPlayer(connection);
		player.incrementGamesPlayed();
	}
	
	@Override
	public void executeEvent(Event event) {
		ClientConnection connection = (ClientConnection)event.getSource();
	    if (event.getType() == EventType.ClientDisconnected){
	    	if(playerMap.containsKey(connection)){    		
				PlayerModel player = playerMap.get(connection);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				player.setLastLogoutTime(timestamp);
				databaseService.updatePlayer(player);
				synchronized(playerMap)
				{
					playerMap.remove(connection);
				}
	    	}
		}
	}
}
