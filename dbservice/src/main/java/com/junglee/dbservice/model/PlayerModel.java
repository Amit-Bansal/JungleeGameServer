package com.junglee.dbservice.model;

import org.json.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "PLAYER")
public class PlayerModel extends Base{

	@Id
	@Column(name = "PLAYER_ID", nullable = false)
	private String id;

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
	
	public PlayerModel() {
	}


	public void import_data(JSONObject data){
		
	}
	
	public JSONObject export_data(){
		return null;
	}

	


}
