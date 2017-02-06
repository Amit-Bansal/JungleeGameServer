package com.junglee.dbservice.model;

import javax.persistence.Column;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PLAYER")
public class Player {

	@Id
	@Column(name = "ID", nullable = false)
	private int id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "AGE", nullable = false)
	private long age;
	
	@Column(name = "GAMES_PLAYED", nullable = true)
	private long gamesPlayed;
	
	@Column(name = "LAST_LOGIN_TIME", nullable = true)
	private Date lastLoginTime;
	
	@Column(name = "LAST_LOGOUT_TIME", nullable = true)
	private Date lastLogoutTime;
	
	public Player() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public long getGamesPlayed() {
		return gamesPlayed;
	}

	public void incrementGamesPlayed() {
		++gamesPlayed;
	}
	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date date) {
		this.lastLoginTime = date;
	}
	
	public Date getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(Date date) {
		this.lastLogoutTime = date;
	}
}
