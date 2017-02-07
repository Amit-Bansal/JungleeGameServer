package com.junglee.gameserver.game;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.junglee.dbservice.model.*;
import com.junglee.gameserver.table.TableState;
import com.junglee.gameserver.table.TableManager;

public class GameManager {
	
	private static GameManager instance = null;
	private static Object mutex = new Object();
	private GameManager() {}
	
	public static GameManager getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null) {
					instance = new GameManager();
				}
			}
		}
		return instance;
	}
	
	public void startGame() {
		Game game = new Game();
		games.put(game.getGameId(), game);
		game.startGame();
	}
	
	public void endGame(Game game) {
		TableManager.updateTable(game.getTable(), TableState.WAITING_FOR_PLAYERS);
		for (Player player:game.getPlayerList())
		    player.incrementGamesPlayed();
		games.remove(game.getGameId());
	}
	
	private static HashMap<Integer, Game> games;
}
