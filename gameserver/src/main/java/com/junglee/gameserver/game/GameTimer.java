package com.junglee.gameserver.game;


import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import com.junglee.dbservice.model.GameModel;

public class GameTimer {

	@Autowired
	GameManager gameManager;
	
	public GameTimer(GameModel game){
		this.game = game;
	}
	
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
		
		gameManager.endGame(game);
	}
	
	public GameModel getGameId() {
		return game;
	}
	
	private Timer timer;
	private GameModel game;
}
