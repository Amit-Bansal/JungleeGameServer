package com.junglee.gameserver.player;

import java.util.Date;

import org.json.JSONObject;

public class Player{

	public Player() {
	}

	public void import_data(JSONObject data){
		
	}
	
	public JSONObject export_data(){
		return null;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public long getGamesPlayed() {
		return gamesPlayed;
	}

	public void incrementGamesPlayed() {
		++gamesPlayed;
	}
	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date date) {
		this.lastLoginTime = date;
	}
	
	public Date getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(Date date) {
		this.lastLogoutTime = date;
	}
	
	
	private String id;
	private String name;
	private long age;
	private long gamesPlayed;
	private Date lastLoginTime;
	private Date lastLogoutTime;
}