package com.junglee.gameserver.game;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.junglee.dbservice.model.GameModel;
import com.junglee.dbservice.service.DatabaseService;
import com.junglee.eventdispatcher.*;

import com.junglee.gameserver.table.Table;

@Component
public class GameManager implements EventListener{
		
	@Autowired
	EventDispatcher eventDispatcher;
	
	@Autowired
	DatabaseService databaseService;
	
	public GameManager() {
		gamesTableMap = new HashMap<GameModel, Table>();
	}
	

	public GameModel startGame(Table table) {
		GameModel game = new GameModel();
		GameTimer timer = new GameTimer(game);
		
		synchronized(gamesTableMap)
		{
			gamesTableMap.put(game, table);
		}

		databaseService.persistGame(game);
		
		timer.startGame();
		return game;
	}
	
	public void endGame(GameModel game) {
		
		Table table;
		synchronized(gamesTableMap)
		{
			table = gamesTableMap.get(game);
		}

		table.incrementPlayersGamesCount();
		
		databaseService.updateGame(game);
		
		synchronized(gamesTableMap)
		{
			gamesTableMap.remove(game);
		}
	}
	
	@Override
	public void executeEvent(Event event) {
		
	}
	
	private static HashMap<GameModel, Table> gamesTableMap;
}
