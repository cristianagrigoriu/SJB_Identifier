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
	@Persistent
	private String uniqueId;
	
	@PrimaryKey
    @Persistent
    private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private List<Key> treasureHuntKeys;
	
	@Persistent
	private List<TreasureHunt> treasureHunts;
	
	public Identifier() {
		treasureHuntKeys = new ArrayList<Key>();
		treasureHunts = new ArrayList<TreasureHunt>();
	}
	
	public Identifier(String id) {
		super();
		Key key = KeyFactory.createKey(Identifier.class.getSimpleName(), id);
		this.key = key;
		this.uniqueId = id;
		treasureHuntKeys = new ArrayList<Key>();
		treasureHunts = new ArrayList<TreasureHunt>();
	}

	public Identifier(String id, String name) {
		super();
		Key key = KeyFactory.createKey(Identifier.class.getSimpleName(), id);
		this.key = key;
		this.uniqueId = id;
		this.name = name;
		treasureHuntKeys = new ArrayList<Key>();
		treasureHunts = new ArrayList<TreasureHunt>();
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
	
	public List<Key> getTreasureHuntKeys() {
		return this.treasureHuntKeys;
	}
	
	public void setTreasureHuntKeys(List<Key> ths) {
		this.treasureHuntKeys = ths;
	}
	
	public List<TreasureHunt> getTreasureHunts() {
		return this.treasureHunts;
	}
	
	public void setTreasureHuns(List<TreasureHunt> ths) {
		this.treasureHunts = ths;
	}
	
	public void addTreasureHunt(TreasureHunt th) {
		this.treasureHuntKeys.add(th.getKey());
		this.treasureHunts.add(th);
	}
	
	public void deleteTreasureHunt(TreasureHunt th) {
		if (this.treasureHuntKeys != null)
			for (Key k : this.treasureHuntKeys)
				if (k.equals(th.getKey()))
					this.treasureHuntKeys.remove(k);
		
		if (this.treasureHunts != null)
			for (TreasureHunt t : this.treasureHunts)
				if (t.equals(th))
					this.treasureHunts.remove(t);		
	}
	
	public boolean hasTreasureHunt(TreasureHunt th) {
		if (this.treasureHunts != null)
			for (TreasureHunt t : this.treasureHunts)
				if (th.equals(t))
					return true;
		return false;
	}
}
