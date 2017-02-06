package com.junglee.dbservice.dao;

import com.junglee.dbservice.model.Player;

public interface PlayerDAO {
	
		  void persistPlayer(Player player);
		  
		  Player findPlayerById(String id);
		  
		  void updatePlayer(Player player);
		  
		  void deletePlayer(Player player);
		  
}
