package com.junglee.dbservice.service;

import org.springframework.stereotype.Service;

import com.junglee.dbservice.model.GameModel;
import com.junglee.dbservice.model.PlayerModel;


@Service("databaseServiceNoSQLImpl")
public class DatabaseServiceNoSQLImpl implements DatabaseService{

	@Override
	public void persistPlayer(PlayerModel player) {	
	}
	@Override
	public void updatePlayer(PlayerModel player) {	
	}
	@Override
	public PlayerModel findPlayerById(String id) {
		return null;
	}
	@Override
	public void deletePlayer(PlayerModel player) {	
	}
	
	
	
	@Override
	public void persistGame(GameModel game){}
	 
	@Override
	public GameModel findGameById(String id){
		return null;
	}
	
	@Override
	public void updateGame(GameModel game){}
	
	@Override
	public void deleteGame(GameModel game){}
	
}
