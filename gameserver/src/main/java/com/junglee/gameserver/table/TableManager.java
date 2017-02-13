package com.junglee.gameserver.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.junglee.dbservice.model.PlayerModel;
import com.junglee.eventdispatcher.*;
import com.junglee.gameserver.player.PlayerManager;
import com.junglee.networkservice.ClientConnection;

@Component
public class TableManager implements EventListener{
	
	@Autowired
	PlayerManager playerManager;
	
	public TableManager() {
		playerTableMap = new HashMap<ClientConnection, Table>();
		currentTables = new ArrayList<Table>();
;	}

	
	public static final int MaxPlayersLimit = 5;
	public static final int MinPlayersLimit = 3;
	
	public  void joinPlayerToTable(ClientConnection connection) {
		Table table = getWaitingTable();
		if (table == null || table.getPlayerCount() >= MaxPlayersLimit) {
			table = createTable();
		}
		playerTableMap.put(connection, table);
		addPlayerToTable(table, connection);
		
		PlayerModel player = playerManager.getPlayer(connection);
		//BroadCast message to all players of current table
		String broadcastMsg = "Player with ID " + player.getId() + "and Name " + player.getName() + "joined";
		table.sendMessageToPlayers(broadcastMsg);
	}
		
	public void leavePlayerFromTable(ClientConnection connection) {
		Table table = playerTableMap.get(connection);
		removePlayerFromTable(connection, table);
		//BroadCast message to all players of current table
		PlayerModel player = playerManager.getPlayer(connection);
		String broadcastMsg = "Player with ID " + player.getId() + "and Name " + player.getName() + "Left";
		table.sendMessageToPlayers(broadcastMsg);
	}
	
	public void handlePlayerLogout(ClientConnection connection) {
		if(playerTableMap.containsKey(connection)){
			Table table = playerTableMap.get(connection);
			removePlayerFromTable(connection, table);
			//BroadCast message to all players of current table
			PlayerModel player = playerManager.getPlayer(connection);
			String broadcastMsg = "Player with ID " + player.getId() + "and Name " + player.getName() + "Disconnected";
			table.sendMessageToPlayers(broadcastMsg);
		}
	}
	
	@Override
	public void executeEvent(Event event) {
		ClientConnection connection = (ClientConnection)event.getSource();
        if (event.getType() == EventType.ClientDisconnected){
        	if(playerTableMap.containsKey(connection)){
        		Table table = playerTableMap.get(connection);
    			removePlayerFromTable(connection, table);
    			//BroadCast message to all players of current table
    			PlayerModel player = playerManager.getPlayer(connection);
    			String broadcastMsg = "Player with ID " + player.getId() + "and Name " + player.getName() + "Disconnected";
    			table.sendMessageToPlayers(broadcastMsg);
        	}

		}
	}
	
	
	private void removePlayerFromTable(ClientConnection connection, Table table){
		table.removePlayer(connection);
		if (table.getPlayerCount() == 0) {
			destroyTable(table);
		}
		playerTableMap.remove(connection);
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
	
	private void addPlayerToTable(Table table, ClientConnection player) {
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
	
	private HashMap<ClientConnection, Table> playerTableMap;
	private  List<Table> currentTables;
}
