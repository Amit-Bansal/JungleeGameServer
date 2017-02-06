package com.junglee.gameserver.message;

import com.junglee.gameserver.table.Table;


public class LeaveTableMessage  implements Message {

	public void serialize() {
		// TODO Auto-generated method stub
		
	}

	public Message deserialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Table getTable(){
		return table;
	}
	public int playerId;
	Table table;
}
