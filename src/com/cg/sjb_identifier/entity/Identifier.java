package com.cg.sjb_identifier.entity;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Identifier {
	/*@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String uniqueId;*/
	
	@Persistent
	private String uniqueId;
	
	@PrimaryKey
    @Persistent
    private Key key;
	
	@Persistent
	private String name;
	@Persistent
	private List<Key> treasureHunts;
	
	public Identifier() {
		treasureHunts = new ArrayList<Key>();
	}
	
	public Identifier(String id) {
		super();
		Key key = KeyFactory.createKey(Identifier.class.getSimpleName(), id);
		this.key = key;
		this.uniqueId = id;
		treasureHunts = new ArrayList<Key>();
	}

	public Identifier(String id, String name) {
		super();
		Key key = KeyFactory.createKey(Identifier.class.getSimpleName(), id);
		this.key = key;
		this.uniqueId = id;
		this.name = name;
		treasureHunts = new ArrayList<Key>();
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
	
	public void setKey(Key key) {
        this.key = key;
    }
	
	public List<Key> getTreasureHunts() {
		return this.treasureHunts;
	}
	
	public void setTreasureHunts(List<Key> ths) {
		this.treasureHunts = ths;
	}
	
	public void addTreasureHunt(Key th) {
		this.treasureHunts.add(th);
	}
}
