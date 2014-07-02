package com.cg.sjb_identifier.entity;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable="true")
public class TreasureHunt {	
	@Persistent
	//@PrimaryKey
	//@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	String uniqueId;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	String name;
	
	@Persistent
	List<Clue> clues = new ArrayList<Clue>();
	
	@Persistent
	boolean isCompleted;
	
	public TreasureHunt() {
		//this.clues = null;
		isCompleted = false;
	}
	
	public TreasureHunt(String id) {
		super();
		this.uniqueId = id;
		//this.clues = null;
		isCompleted = false;
	}

	public TreasureHunt(String id, String name) {
		super();
		this.uniqueId = id;
		this.name = name;
		//this.clues = null;
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
	
	public List<Clue> getAllClues() {
		//this.clues = new ArrayList<Clue>();
		//System.out.println(this.clues.size());
		if (this.clues.isEmpty())
		//if (this.clues == null)
			this.clues = new ArrayList<Clue>();
		return this.clues;
	}
	
	public TreasureHunt addClueTo(Clue newClue) {
		if (this.clues.isEmpty()) {
			this.clues = new ArrayList<Clue>();
		}
		this.clues.add(newClue);
		return this;
	}
	
	public void setCluesNull() {
		this.clues = null;
	}
	
	public boolean isTHCompleted() {
		  return this.isCompleted;
	}
	
	public void setCompleted() {
		this.isCompleted = true;
	}
}
