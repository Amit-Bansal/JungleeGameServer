package com.junglee.dbservice.service;

import com.junglee.dbservice.model.GameModel;
import com.junglee.dbservice.model.PlayerModel;



public interface DatabaseService {

	void persistPlayer(PlayerModel player);
	PlayerModel findPlayerById(String id);
	void updatePlayer(PlayerModel player);
	void deletePlayer(PlayerModel player);
	
	
	void persistGame(GameModel game);
	GameModel findGameById(String id);
	void updateGame(GameModel game);
	void deleteGame(GameModel game);
}
