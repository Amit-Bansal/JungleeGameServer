package com.junglee.gameserver.game;


import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.junglee.gameserver.application.App;

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
		
		App.getInstance().getGameManager().endGame(this);
	}
	
		
	public int getGameId() {
		return gameId;
	}
	
	public void import_data(JSONObject data){
		
	}
	
	public JSONObject export_data(){
		return null;
	}

	private Timer timer;
	private int gameId;
}
