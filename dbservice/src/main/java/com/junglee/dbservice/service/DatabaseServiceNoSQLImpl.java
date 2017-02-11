package com.junglee.dbservice.service;

import org.springframework.stereotype.Service;

import org.json.*;


@Service("databaseServiceNoSQLImpl")
public class DatabaseServiceNoSQLImpl implements DatabaseService{

	@Override
	public void persistPlayer(JSONObject player) {	
	}
	@Override
	public void updatePlayer(JSONObject player) {	
	}
	@Override
	public JSONObject findPlayerById(String id) {
		return null;
	}
	@Override
	public void deletePlayer(JSONObject player) {	
	}
	
	
	
	@Override
	public void persistGame(JSONObject game){}
	 
	@Override
	public JSONObject findGameById(String id){
		JSONObject obj = null;
		return obj;
	}
	
	@Override
	public void updateGame(JSONObject game){}
	
	@Override
	public void deleteGame(JSONObject game){}
	
}
