package com.cg.sjb_identifier.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class UserTreasureHunt {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	TreasureHunt currentTreasureHunt;
	@Persistent
	boolean isCompleted;
	
	public UserTreasureHunt(TreasureHunt newTH) {
		this.currentTreasureHunt = newTH;
		this.isCompleted = false;
	}
	
	public void setCompleted() {
		this.isCompleted = true;
	}
}
