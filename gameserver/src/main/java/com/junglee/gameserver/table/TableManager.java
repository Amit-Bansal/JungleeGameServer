package com.junglee.gameserver.table;

import java.util.List;

import com.junglee.dbservice.model.Player;
import com.junglee.gameserver.app.App;
import com.junglee.gameserver.message.JoinTableMessage;
import com.junglee.gameserver.message.LeaveTableMessage;


public class TableManager {
	
	public TableManager() {}

	
	public static final int MaxPlayersLimit = 5;
	public static final int MinPlayersLimit = 3;
	
	public  void handlePlayerJoinTableMessage(JoinTableMessage msg) {
		Table table = getWaitingTable();
		if (table == null || table.getPlayerCount() >= MaxPlayersLimit) {
			table = createTable();
		}
		Player player= App.getInstance().getSessionManager().getSession(msg.getSessionId()).getPlayer();
		addPlayerToTable(table, player);
	}
	
	public void handlePlayerLeaveTableMessage(LeaveTableMessage msg) {
		
		Player player= App.getInstance().getSessionManager().getSession(msg.getSessionId()).getPlayer();
		msg.getTable().removePlayer(player);
		if (msg.getTable().getPlayerCount() == 0) {
			destroyTable(msg.getTable());
		}
	}
	
	private Table createTable() {
		Table table = new Table();
		currentTables.add(table);
		return table;
	}
	
	private void destroyTable(Table table) {
		currentTables.remove(table);
	}
	
	public void updateTable(Table table, TableState state) {
		table.setCurrentState(state);
	}
	
	private void addPlayerToTable(Table table, Player player) {
		table.joinPlayer(player);
	}
	
	private Table getWaitingTable() {
		for (Table table : currentTables) {
			if (table.getCurrentState() == TableState.WAITING_FOR_PLAYERS)
				return table;
			else 
				return null;
		}
		return null;
	}
	
	private  List<Table> currentTables;
}
