package com.junglee.gameserver.message;

public class BroadcastMessage extends Message{
	
	public BroadcastMessage(String str){
		msgStr = str;
	}
	
	public String msgStr;
}
