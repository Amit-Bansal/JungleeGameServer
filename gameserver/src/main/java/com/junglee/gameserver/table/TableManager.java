package com.junglee.gameserver.table;

import java.util.HashMap;
import java.util.List;

import com.junglee.gameserver.event.Event;
import com.junglee.gameserver.event.EventListener;
import com.junglee.gameserver.player.*;
import com.junglee.gameserver.session.ClientSession;

public class TableManager implements EventListener{
	
	public TableManager() {
	}

	
	public static final int MaxPlayersLimit = 5;
	public static final int MinPlayersLimit = 3;
	
	public  void joinPlayerToTable(Player player) {
		Table table = getWaitingTable();
		if (table == null || table.getPlayerCount() >= MaxPlayersLimit) {
			table = createTable();
		}
		playerTableMap.put(player, table);
		addPlayerToTable(table, player);
		//BroadCast message to all players of current table
		String broadcastMsg = "Player with ID " + player.getId() + "and Name " + player.getName() + "joined";
		table.sendMessageToPlayers(broadcastMsg);
	}
		
	public void leavePlayerFromTable(Player player) {
		Table table = playerTableMap.get(player);
		removePlayerFromTable(player, table);
		//BroadCast message to all players of current table
		String broadcastMsg = "Player with ID " + player.getId() + "and Name " + player.getName() + "Left";
		table.sendMessageToPlayers(broadcastMsg);
	}
	
	public void handlePlayerLogout(Player player) {
		if(playerTableMap.containsKey(player)){
			Table table = playerTableMap.get(player);
			removePlayerFromTable(player, table);
			//BroadCast message to all players of current table
			String broadcastMsg = "Player with ID " + player.getId() + "and Name " + player.getName() + "Disconnected";
			table.sendMessageToPlayers(broadcastMsg);
		}
	}
	
	@Override
	public void executeEvent(Event event) {
		ClientSession session = (ClientSession)event.getSource();
		Player player = session.getPlayer();
		if(playerTableMap.containsKey(player)){
			Table table = playerTableMap.get(player);
			removePlayerFromTable(player, table);
			//BroadCast message to all players of current table
			String broadcastMsg = "Player with ID " + player.getId() + "and Name " + player.getName() + "Disconnected";
			table.sendMessageToPlayers(broadcastMsg);
		}
	}
	
	private void removePlayerFromTable(Player player, Table table){
		table.removePlayer(player);
		if (table.getPlayerCount() == 0) {
			destroyTable(table);
		}
		playerTableMap.remove(player);
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
	
	private HashMap<Player, Table> playerTableMap;
	private  List<Table> currentTables;
}
