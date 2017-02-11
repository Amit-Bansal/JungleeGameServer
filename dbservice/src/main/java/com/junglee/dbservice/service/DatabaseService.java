package com.junglee.dbservice.service;

import org.json.*;



public interface DatabaseService {

	void persistPlayer(JSONObject player);
	JSONObject findPlayerById(String id);
	void updatePlayer(JSONObject player);
	void deletePlayer(JSONObject player);
	
	
	void persistGame(JSONObject game);
	JSONObject findGameById(String id);
	void updateGame(JSONObject game);
	void deleteGame(JSONObject game);
}
