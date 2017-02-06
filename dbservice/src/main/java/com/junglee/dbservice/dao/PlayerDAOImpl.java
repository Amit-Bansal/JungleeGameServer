package com.junglee.dbservice.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.junglee.dbservice.model.Player;

@Repository("playerDAO")
public class PlayerDAOImpl implements PlayerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void persistPlayer(Player player) {
		sessionFactory.getCurrentSession().persist(player);
	}

	@Override
	public Player findPlayerById(String id) {
		return (Player) sessionFactory.getCurrentSession().get(Player.class, id);
	}

	@Override
	public void updatePlayer(Player player) {
		sessionFactory.getCurrentSession().update(player);

	}
	@Override
	public void deletePlayer(Player player) {
		sessionFactory.getCurrentSession().delete(player);

	}

}