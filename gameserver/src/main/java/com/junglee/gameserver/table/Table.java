package com.junglee.gameserver.table;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.junglee.gameserver.player.*;
import com.junglee.gameserver.application.App;
import com.junglee.gameserver.game.Game;
import com.junglee.gameserver.message.BroadcastMessage;
import com.junglee.gameserver.session.ClientSession;


public class Table {
	public Table() {
		setCurrentState(TableState.WAITING_FOR_PLAYERS);
		gameStartFiveSecondTimer = false;
		setGame(null);
	}
	
	public static final int MaxPlayersLimit = 5;
	public static final int MinPlayersLimit = 3;
	
	public void joinPlayer(Player player) {
		if (currentState == TableState.WAITING_FOR_PLAYERS) {
			playerList.add(player);
			if (playerList.size() >= MinPlayersLimit) {
				SetGameStartFiveSecondTimer();
			}
		}
	}
	
	public void removePlayer(Player player) {
		playerList.remove(player);
		if (playerList.size() == 0) {
			App.getInstance().getGameManager().endGame(game);
			setGame(null);
			return;
		}
		else if (playerList.size() < MinPlayersLimit) {
			currentState = TableState.WAITING_FOR_PLAYERS;
			StopGameStartFiveSecondTimer();
		}
	}
	

	public void sendMessageToPlayers(String msgStr) {
		for (Player player : playerList) {
			//send message through client connection
			BroadcastMessage msg = new BroadcastMessage(msgStr);
			ClientSession session =App.getInstance().getPlayerManager().getSession(player);
			msg.setSessionId(session.getSessionId());
			session.sendMessage(msg);
		}
	}
	
	public void SetGameStartFiveSecondTimer() {
		sendMessageToPlayers("Game starting in 5s");
		if (gameStartFiveSecondTimer == false) {
			gameStartFiveSecondTimer = true;
			timer = new Timer();
			timer.schedule(new TimerTask() {
			    @Override
			    public void run() {
			    	startGame();
			    }
			}, 0, 5000);
		}
	}
	
	public void StopGameStartFiveSecondTimer() {
		gameStartFiveSecondTimer = false;
		timer.cancel();
	}
	public int getTableID() {
		return TableID;
	}
	public void setTableID(int tableID) {
		TableID = tableID;
	}
	public TableState getCurrentState() {
		return currentState;
	}
	public void setCurrentState(TableState currentState) {
		this.currentState = currentState;
	}
	
	public int getPlayerCount() {
		return playerList.size();
	}
	
	public void startGame() {
		currentState = TableState.IN_GAME;
		setGame(App.getInstance().getGameManager().startGame(this));
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public List<Player> getPlayerList() {
		return playerList;
	}

	private int TableID;
	private TableState currentState;
	private List<Player> playerList;
	private boolean gameStartFiveSecondTimer;
	private Timer timer;
	private Game game;
}
