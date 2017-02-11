package com.junglee.dbservice.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "GAME")
public class GameModel extends Base{

	@Id
	@Column(name = "GAME_ID", nullable = false)
	private String id;
	
	@Column(name = "NAME", nullable = false)
	private String name;
		
	@Column(name = "START_TIME", nullable = true)
	private Date startTime;
	
	@Column(name = "END_TIME", nullable = true)
	private Date endTime;
	
	@OneToOne
	@JoinColumn(name = "WINNER", nullable = true)
	private PlayerModel winner;
	
	
}
