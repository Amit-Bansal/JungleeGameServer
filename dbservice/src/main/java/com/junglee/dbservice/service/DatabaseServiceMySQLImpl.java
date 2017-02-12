package com.junglee.dbservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.junglee.dbservice.dao.PlayerDAO;
import com.junglee.dbservice.model.GameModel;
import com.junglee.dbservice.model.PlayerModel;



@Service("databaseServiceMySQLImpl")
public class DatabaseServiceMySQLImpl implements DatabaseService{

	@Autowired
	PlayerDAO playerDAO;
		
	@Override
	@Transactional
	public void persistPlayer(PlayerModel player) {
		playerDAO.persistPlayer(player);
	}

	@Override
	@Transactional
	public void updatePlayer(PlayerModel player) {
		playerDAO.updatePlayer(player);
	}
	
	@Override
	@Transactional
	public PlayerModel findPlayerById(String id) {
		PlayerModel player = playerDAO.findPlayerById(id);
		return player;
	}

	@Override
	@Transactional
	public void deletePlayer(PlayerModel player) {
		playerDAO.deletePlayer(player);
		
	}
	
	@Override
	@Transactional
	public void persistGame(GameModel game){}
	 
	@Override
	@Transactional
	public GameModel findGameById(String id){
		return null;
	}
	
	@Override
	@Transactional
	public void updateGame(GameModel game){}
	
	@Override
	@Transactional
	public void deleteGame(GameModel game){}

}
