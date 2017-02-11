package com.junglee.gameserver.game;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.junglee.dbservice.service.DatabaseService;
import com.junglee.gameserver.application.App;
import com.junglee.gameserver.event.Event;
import com.junglee.gameserver.event.EventListener;
import com.junglee.gameserver.player.Player;
import com.junglee.gameserver.table.Table;

@Component
public class GameManager implements EventListener{
	
	@Autowired
	DatabaseService databaseService;
	
	public GameManager() {
	}

	
	public Game startGame(Table table) {
		Game game = new Game();		
		gamesTableMap.put(game, table);
		game.startGame();
		
		JSONObject gameObj = game.export_data();
		App.getInstance().getDbService().persistGame(gameObj);
		return game;
	}
	
	public void endGame(Game game) {
		Table table = gamesTableMap.get(game);
		for (Player player:table.getPlayerList())
		    player.incrementGamesPlayed();
		
		JSONObject gameObj = game.export_data();
		databaseService.updateGame(gameObj);
		gamesTableMap.remove(game);
	}
	
	@Override
	public void executeEvent(Event event) {
		
	}
	
	private static HashMap<Game, Table> gamesTableMap;
}
