package com.junglee.gameserver.game;

import java.util.Timer;
import java.util.TimerTask;

import com.junglee.dbservice.model.*;
import com.junglee.gameserver.table.TableState;
import com.junglee.gameserver.table.TableManager;

public class GameManager {
	
	public void startGame() {
		game = new Game();
		timer = new Timer();
		timer.schedule(new TimerTask() {
		    @Override
		    public void run() {
		    	endGame();
		    }
		}, 0, 60000);
	}
	public void endGame() {
		timer.cancel();
		TableManager.updateTable(game.getTable(), TableState.WAITING_FOR_PLAYERS);
		for (Player player:game.getPlayerList())
		    player.incrementGamesPlayed();
	}
	
	
	private Timer timer;
	private Game game;
}
