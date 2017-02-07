package com.junglee.gameserver.game;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.junglee.dbservice.model.*;
import com.junglee.gameserver.table.Table;
import com.junglee.gameserver.table.TableManager;
import com.junglee.gameserver.table.TableState;

public class Game {
	
	public void startGame() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
		    @Override
		    public void run() {
		    	gameTimeout();
		    }
		}, 0, 60000);
	}
	
	public void gameTimeout() {
		timer.cancel();
		GameManager.getInstance().endGame(this);
	}
	
	public List<Player> getPlayerList() {
		return playerList;
	}
	
	public Table getTable() {
		return table;
	}
	
	
	public int getGameId() {
		return gameId;
	}


	private List<Player> playerList;
	private Table table;
	private Timer timer;
	private int gameId;
}
