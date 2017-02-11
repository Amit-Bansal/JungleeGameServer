package com.junglee.dbservice.dao;

import com.junglee.dbservice.model.PlayerModel;

public interface PlayerDAO {
	
		  void persistPlayer(PlayerModel player);
		  
		  PlayerModel findPlayerById(String id);
		  
		  void updatePlayer(PlayerModel player);
		  
		  void deletePlayer(PlayerModel player);
		  
}
