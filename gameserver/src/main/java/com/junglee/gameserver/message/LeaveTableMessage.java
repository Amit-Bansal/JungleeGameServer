package com.junglee.gameserver.message;

import com.junglee.gameserver.table.Table;


public class LeaveTableMessage  extends Message {

	public String serialize() {
		return null;		
	}

	public Table getTable(){
		return table;
	}
	
	Table table;
}
