package com.junglee.gameserver.table;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.junglee.dbservice.model.Player;
import com.junglee.gameserver.app.App;


public class Table {
	public Table() {
		setCurrentState(TableState.WAITING_FOR_PLAYERS);
		gameStartFiveSecondTimer = false;
	}
	
	public static final int MaxPlayersLimit = 5;
	public static final int MinPlayersLimit = 3;
	
	public void joinPlayer(Player player) {
		if (currentState == TableState.WAITING_FOR_PLAYERS) {
			sendNewPlayerInfoToExistingPlayersOnTable(player.getId(), player.getName());
			playerList.add(player);
			if (playerList.size() >= MinPlayersLimit) {
				SetGameStartFiveSecondTimer();
			}
		}
	}
	
	public void removePlayer(Player player) {
		playerList.remove(player);
		if (playerList.size() == 0) {
			return;
		}
		else if (playerList.size() < MinPlayersLimit) {
			currentState = TableState.WAITING_FOR_PLAYERS;
			StopGameStartFiveSecondTimer();
		}
		else if (playerList.size() >= MinPlayersLimit) {
			SetGameStartFiveSecondTimer();
		}
		sendPlayerDisconnectedInfoToOtherPlayersOnTable(player.getId());
	}
	
	public void sendNewPlayerInfoToExistingPlayersOnTable(int ID, String name) {
		String msg = "Player with ID " + ID + "and Name " + name + "joined";
		sendMessageToPlayers(msg);
	}
	
	public void sendPlayerDisconnectedInfoToOtherPlayersOnTable(int ID) {
		String msg = "Player with ID " + ID + "disconnected";
		sendMessageToPlayers(msg);
	}
	
	public void sendMessageToPlayers(String msg) {
		for (Player player : playerList) {
			//send message through client connection
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
		App.getInstance().getGameManager().startGame();
	}
	
	private int TableID;
	private TableState currentState;
	private List<Player> playerList;
	private boolean gameStartFiveSecondTimer;
	private Timer timer;
}
