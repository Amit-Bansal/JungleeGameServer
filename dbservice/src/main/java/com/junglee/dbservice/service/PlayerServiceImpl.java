package com.junglee.dbservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.junglee.dbservice.dao.PlayerDAO;
import com.junglee.dbservice.model.Player;

@Service("playerService")
public class PlayerServiceImpl implements PlayerService{

	@Autowired
	PlayerDAO playerDAO;
	
	@Override
	@Transactional
	public void persistPlayer(Player player) {
		playerDAO.persistPlayer(player);
		
	}

	@Override
	@Transactional
	public void updatePlayer(Player player) {
		playerDAO.updatePlayer(player);
		
	}
	@Override
	@Transactional
	public Player findPlayerById(String id) {
		return playerDAO.findPlayerById(id);
	}

	@Override
	@Transactional
	public void deletePlayer(Player player) {
		playerDAO.deletePlayer(player);
		
	}

}
