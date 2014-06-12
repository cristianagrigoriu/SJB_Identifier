package com.cg.sjb_identifier.entity;

import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Identifier {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String uniqueId;
	@Persistent
	private String name;
	@Persistent
	private ArrayList<UserTreasureHunt> treasureHunts;
	
	public Identifier() {
	}
	
	public Identifier(String id) {
		super();
		this.uniqueId = id;
	}

	public Identifier(String id, String name) {
		super();
		this.uniqueId = id;
		this.name = name;
	}
	
	public String getUniqueId() {
		return this.uniqueId;
	}
	
	public String getName() {
		return this.name;
	}
}
