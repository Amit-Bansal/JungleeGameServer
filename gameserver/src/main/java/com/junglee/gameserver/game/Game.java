package com.junglee.gameserver.game;

import java.util.List;

import com.junglee.dbservice.model.*;
import com.junglee.gameserver.table.Table;

public class Game {
	public List<Player> getPlayerList() {
		return playerList;
	}
	public Table getTable() {
		return table;
	}
	private List<Player> playerList;
	private Table table;
}
