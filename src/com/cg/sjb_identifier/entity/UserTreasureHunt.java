package com.cg.sjb_identifier.entity;

public class UserTreasureHunt {
	TreasureHunt currentTreasureHunt;
	boolean isCompleted;
	
	public UserTreasureHunt(TreasureHunt newTH) {
		this.currentTreasureHunt = newTH;
		this.isCompleted = false;
	}
	
	public void setCompleted() {
		this.isCompleted = true;
	}
}
