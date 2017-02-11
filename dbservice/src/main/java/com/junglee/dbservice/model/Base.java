package com.junglee.dbservice.model;

import java.util.Date;
import javax.persistence.Column;


public abstract class Base {
	
	@Column(name = "LAST_UPDATED_ON", nullable = true)
	private Date lastUpdatedOn;

	@Column(name = "CREATED_ON", nullable = true)
	private Date createdOn;
}
