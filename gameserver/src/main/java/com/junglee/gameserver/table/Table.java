package com.junglee.gameserver.table;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import com.junglee.dbservice.model.GameModel;
import com.junglee.dbservice.model.PlayerModel;
import com.junglee.gameserver.player.*;
import com.junglee.gameserver.game.GameManager;
import com.junglee.gameserver.message.BroadcastMessage;
import com.junglee.gameserver.session.ClientSession;


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
	
	public void joinPlayer(PlayerModel player) {
		if (currentState == TableState.WAITING_FOR_PLAYERS) {
			playerList.add(player);
			if (playerList.size() >= MinPlayersLimit) {
				SetGameStartFiveSecondTimer();
			}
		}
	}
	
	public void removePlayer(PlayerModel player) {
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
		for (PlayerModel player : playerList) {
			//send message through client connection
			BroadcastMessage msg = new BroadcastMessage(msgStr);
			ClientSession session = playerManager.getSession(player);
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
		setGame(gameManager.startGame(this));
	}
	
	public GameModel getGame() {
		return game;
	}

	public void setGame(GameModel game) {
		this.game = game;
	}
	
	public List<PlayerModel> getPlayerList() {
		return playerList;
	}

	private int TableID;
	private TableState currentState;
	private List<PlayerModel> playerList;
	private boolean gameStartFiveSecondTimer;
	private Timer timer;
	private GameModel game;
}
