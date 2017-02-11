package com.junglee.dbservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.junglee.dbservice.dao.PlayerDAO;
import com.junglee.dbservice.model.PlayerModel;

import org.json.*;

@Service("databaseServiceMySQLImpl")
public class DatabaseServiceMySQLImpl implements DatabaseService{

	@Autowired
	PlayerDAO playerDAO;
		
	@Override
	@Transactional
	public void persistPlayer(JSONObject playerJson) {
		PlayerModel player = new PlayerModel();
		player.import_data(playerJson);
		playerDAO.persistPlayer(player);
	}

	@Override
	@Transactional
	public void updatePlayer(JSONObject playerObj) {
		PlayerModel player = null;
		try {
			player = playerDAO.findPlayerById(playerObj.getString("id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playerDAO.updatePlayer(player);
	}
	
	@Override
	@Transactional
	public JSONObject findPlayerById(String id) {
		PlayerModel player = playerDAO.findPlayerById(id);
		return player.export_data();
	}

	@Override
	@Transactional
	public void deletePlayer(JSONObject playerObj) {
		PlayerModel player = null;
		try {
			player = playerDAO.findPlayerById(playerObj.getString("id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playerDAO.deletePlayer(player);
		
	}
	
	@Override
	@Transactional
	public void persistGame(JSONObject game){}
	 
	@Override
	@Transactional
	public JSONObject findGameById(String id){
		JSONObject obj = null;
		return obj;
	}
	
	@Override
	@Transactional
	public void updateGame(JSONObject game){}
	
	@Override
	@Transactional
	public void deleteGame(JSONObject game){}

}
