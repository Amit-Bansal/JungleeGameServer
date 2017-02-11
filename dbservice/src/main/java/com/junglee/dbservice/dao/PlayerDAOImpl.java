package com.junglee.dbservice.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.junglee.dbservice.model.PlayerModel;

@Repository("playerDAO")
public class PlayerDAOImpl implements PlayerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void persistPlayer(PlayerModel player) {
		sessionFactory.getCurrentSession().persist(player);
	}

	@Override
	public PlayerModel findPlayerById(String id) {
		return (PlayerModel) sessionFactory.getCurrentSession().get(PlayerModel.class, id);
	}

	@Override
	public void updatePlayer(PlayerModel player) {
		sessionFactory.getCurrentSession().update(player);

	}
	@Override
	public void deletePlayer(PlayerModel player) {
		sessionFactory.getCurrentSession().delete(player);

	}

}