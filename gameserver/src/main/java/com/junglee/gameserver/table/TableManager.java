package com.junglee.gameserver.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.junglee.dbservice.model.PlayerModel;
import com.junglee.gameserver.event.Event;
import com.junglee.gameserver.event.EventListener;
import com.junglee.gameserver.session.ClientSession;

@Component
public class TableManager implements EventListener{
	
	public TableManager() {
		playerTableMap = new HashMap<PlayerModel, Table>();
		currentTables = new ArrayList<Table>();
;	}

	
	public static final int MaxPlayersLimit = 5;
	public static final int MinPlayersLimit = 3;
	
	public  void joinPlayerToTable(PlayerModel player) {
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
		
	public void leavePlayerFromTable(PlayerModel player) {
		Table table = playerTableMap.get(player);
		removePlayerFromTable(player, table);
		//BroadCast message to all players of current table
		String broadcastMsg = "Player with ID " + player.getId() + "and Name " + player.getName() + "Left";
		table.sendMessageToPlayers(broadcastMsg);
	}
	
	public void handlePlayerLogout(PlayerModel player) {
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
		PlayerModel player = session.getPlayer();
		if(playerTableMap.containsKey(player)){
			Table table = playerTableMap.get(player);
			removePlayerFromTable(player, table);
			//BroadCast message to all players of current table
			String broadcastMsg = "Player with ID " + player.getId() + "and Name " + player.getName() + "Disconnected";
			table.sendMessageToPlayers(broadcastMsg);
		}
	}
	
	private void removePlayerFromTable(PlayerModel player, Table table){
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
	
	private void addPlayerToTable(Table table, PlayerModel player) {
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
	
	private HashMap<PlayerModel, Table> playerTableMap;
	private  List<Table> currentTables;
}
