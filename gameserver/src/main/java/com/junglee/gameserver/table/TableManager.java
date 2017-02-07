package com.junglee.gameserver.table;

import java.util.List;

import com.junglee.dbservice.model.Player;
import com.junglee.gameserver.message.JoinTableMessage;
import com.junglee.gameserver.message.LeaveTableMessage;
import com.junglee.gameserver.player.PlayerManager;

public class TableManager {
	
	private static TableManager instance = null;
	private static Object mutex = new Object();
	private TableManager() {}
	
	public static TableManager getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null) {
					instance = new TableManager();
				}
			}
		}
		return instance;
	}
	
	public static final int MaxPlayersLimit = 5;
	public static final int MinPlayersLimit = 3;
	
	public static void handlePlayerJoinTableMessage(JoinTableMessage msg) {
		Table table = getWaitingTable();
		if (table == null || table.getPlayerCount() >= MaxPlayersLimit) {
			table = createTable();
		}
		Player player= PlayerManager.getInstance().getPlayer(msg.playerId);
		addPlayerToTable(table, player);
	}
	
	public static void handlePlayerLeaveTableMessage(LeaveTableMessage msg) {
		
		Player player= PlayerManager.getInstance().getPlayer(msg.playerId);
		msg.getTable().removePlayer(player);
		if (msg.getTable().getPlayerCount() == 0) {
			destroyTable(msg.getTable());
		}
	}
	
	private static Table createTable() {
		Table table = new Table();
		currentTables.add(table);
		return table;
	}
	
	private static void destroyTable(Table table) {
		currentTables.remove(table);
	}
	
	public static void updateTable(Table table, TableState state) {
		table.setCurrentState(state);
	}
	
	private static void addPlayerToTable(Table table, Player player) {
		table.joinPlayer(player);
	}
	
	private static Table getWaitingTable() {
		for (Table table : currentTables) {
			if (table.getCurrentState() == TableState.WAITING_FOR_PLAYERS)
				return table;
			else 
				return null;
		}
		return null;
	}
	
	private static List<Table> currentTables;
}
