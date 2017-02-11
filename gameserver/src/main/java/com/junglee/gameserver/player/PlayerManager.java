package com.junglee.gameserver.player;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

import com.junglee.dbservice.service.DatabaseService;
import com.junglee.gameserver.application.App;
import com.junglee.gameserver.event.Event;
import com.junglee.gameserver.event.EventListener;
import com.junglee.gameserver.session.ClientSession;

import java.sql.Timestamp;

public class PlayerManager implements EventListener{
		
	public PlayerManager() {
	}
	
	public boolean signup(String id, String name, String password){
		Player player = new Player();
		player.setId(id);
		player.setName(name);
		JSONObject playerObj = player.export_data();
		App.getInstance().getDbService().persistPlayer(playerObj);
		return true;
	}
			
	private Player loadPlayer(String id){
		Player player = new Player();
		JSONObject playerObj = App.getInstance().getDbService().findPlayerById(id);
		player.import_data(playerObj);
		return player;
	}
	
	public Player loginPlayer(String id, String password, ClientSession session){
		//verify player credentials - to do
		Player player = loadPlayer(id);
		if (player != null){
			playerMap.put(player, session);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			player.setLastLoginTime(timestamp);
		}
		return player;

	}
	
	public void logoutPlayer(Player player){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		player.setLastLogoutTime(timestamp);
		JSONObject playerObj = player.export_data();
		App.getInstance().getDbService().updatePlayer(playerObj);
		playerMap.remove(player);
	}
	
	public ClientSession getSession(Player player){
		return playerMap.get(player);
	}
	
	private static HashMap<Player, ClientSession> playerMap;

	@Override
	public void executeEvent(Event event) {
		
		ClientSession session = (ClientSession)event.getSource();
		Player player = session.getPlayer();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		player.setLastLogoutTime(timestamp);
		JSONObject playerObj = player.export_data();
		App.getInstance().getDbService().updatePlayer(playerObj);
		playerMap.remove(player);
	}
}
