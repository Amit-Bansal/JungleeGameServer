package com.junglee.gameserver.player;



import java.sql.Timestamp;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.junglee.dbservice.model.PlayerModel;
import com.junglee.dbservice.service.DatabaseService;
import com.junglee.gameserver.event.Event;
import com.junglee.gameserver.event.EventListener;
import com.junglee.gameserver.session.ClientSession;

@Component
public class PlayerManager implements EventListener{
		
	@Autowired
	DatabaseService databaseService;
	
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
	
	public PlayerModel loginPlayer(String id, String password, ClientSession session){
		//verify player credentials - to do
		PlayerModel player = loadPlayer(id);
		if (player != null){
			synchronized(playerMap)
			{
				playerMap.put(player, session);
			}
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			player.setLastLoginTime(timestamp);
		}
		return player;

	}
	
	public void logoutPlayer(PlayerModel player){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		player.setLastLogoutTime(timestamp);

		databaseService.updatePlayer(player);
		synchronized(playerMap)
		{
			playerMap.remove(player);
		}
	}
	
	public ClientSession getSession(PlayerModel player){
		return playerMap.get(player);
	}
	
	private static HashMap<PlayerModel, ClientSession> playerMap;

	@Override
	public void executeEvent(Event event) {
		
		ClientSession session = (ClientSession)event.getSource();
		PlayerModel player = session.getPlayer();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		player.setLastLogoutTime(timestamp);
		databaseService.updatePlayer(player);
		synchronized(playerMap)
		{
			playerMap.remove(player);
		}
	}
}
