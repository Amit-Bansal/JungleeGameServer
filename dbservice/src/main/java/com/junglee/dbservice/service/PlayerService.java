package com.junglee.dbservice.service;

import com.junglee.dbservice.model.Player;

public interface PlayerService {

	void persistPlayer(Player player);

	Player findPlayerById(String id);

	void updatePlayer(Player player);

	void deletePlayer(Player player);
}
