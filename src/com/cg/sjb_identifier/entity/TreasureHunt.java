package com.cg.sjb_identifier.entity;

import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class TreasureHunt {
	/*@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	String uniqueId;*/
	
	@Persistent
	String uniqueId;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	String name;
	@Persistent
	ArrayList<Clue> clues;
	boolean isCompleted;
	
	public TreasureHunt() {
		this.clues = new ArrayList<Clue>();
		isCompleted = false;
	}
	
	public TreasureHunt(String id) {
		super();
		this.uniqueId = id;
		this.clues = new ArrayList<Clue>();
		isCompleted = false;
	}

	public TreasureHunt(String id, String name) {
		super();
		this.uniqueId = id;
		this.name = name;
		this.clues = new ArrayList<Clue>();
		isCompleted = false;
	}
	
	public String getUniqueId() {
		return this.uniqueId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Key getKey() {
		return this.key;
	}
	
	public void setKey(Key newKey) {
		this.key = newKey;
	}
	
	public ArrayList<Clue> getAllClues() {
		return this.clues;
	}
	
	public TreasureHunt addClueTo(String id, Clue newClue) {
		if (this.clues == null) {
			this.clues = new ArrayList<Clue>();
		}
		this.clues.add(newClue);
		return this;
	}
}