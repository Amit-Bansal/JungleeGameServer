package com.junglee.gameserver.table;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import com.junglee.dbservice.model.GameModel;

import com.junglee.gameserver.player.*;
import com.junglee.gameserver.game.GameManager;
import com.junglee.gameserver.message.BroadcastMessage;

import com.junglee.networkservice.ClientConnection;


public class Table {
	
	@Autowired
	GameManager gameManager;
	
	@Autowired
	PlayerManager playerManager;
	
	public Table() {
		setCurrentState(TableState.WAITING_FOR_PLAYERS);
		gameStartFiveSecondTimer = false;
		setGame(null);
	}
	
	public static final int MaxPlayersLimit = 5;
	public static final int MinPlayersLimit = 3;
	
	public void joinPlayer(ClientConnection player) {
		if (currentState == TableState.WAITING_FOR_PLAYERS) {
			playerList.add(player);
			if (playerList.size() >= MinPlayersLimit) {
				SetGameStartFiveSecondTimer();
			}
		}
	}
	
	public void removePlayer(ClientConnection player) {
		playerList.remove(player);
		if (playerList.size() == 0) {
			gameManager.endGame(game);
			setGame(null);
			return;
		}
		else if (playerList.size() < MinPlayersLimit) {
			currentState = TableState.WAITING_FOR_PLAYERS;
			StopGameStartFiveSecondTimer();
		}
	}
	

	public void sendMessageToPlayers(String msgStr) {
		for (ClientConnection connection : playerList) {
			//send message through client connection
			BroadcastMessage msg = new BroadcastMessage(msgStr);
			String message = msg.serialize();
			connection.sendMessage(message);
		}
	}
	
	public void incrementPlayersGamesCount(){
		for (ClientConnection connection:playerList){
			playerManager.incrementPlayerGameCount(connection);
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
		setGame(gameManager.startGame(this));
	}
	
	public GameModel getGame() {
		return game;
	}

	public void setGame(GameModel game) {
		this.game = game;
	}
	
	public List<ClientConnection> getPlayerList() {
		return playerList;
	}

	private int TableID;
	private TableState currentState;
	private List<ClientConnection> playerList;
	private boolean gameStartFiveSecondTimer;
	private Timer timer;
	private GameModel game;
}
