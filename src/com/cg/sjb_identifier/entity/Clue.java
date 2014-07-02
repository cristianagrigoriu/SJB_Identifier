package com.cg.sjb_identifier.entity;

import java.util.ArrayList;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true")
//@EmbeddedOnly
public class Clue {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	/*more instructions for different difficulty levels: (easy), (medium), (hard)*/
	@Persistent
	ArrayList<String> instructions;
	
	@Persistent
	ArrayList<Double> latlongCoordinates;
	
	@Persistent
	boolean isFound;
	
	public Clue(ArrayList<String> theInstructions, ArrayList<Double> theCoordinates) {
		this.instructions = new ArrayList<String>();
		for (String i: theInstructions)
			this.instructions.add(i);
		
		this.latlongCoordinates = new ArrayList<Double>();
		for (Double i: theCoordinates)
			this.latlongCoordinates.add(i);
		
		isFound = false;
	}
	
	public Clue() {
		isFound = false;
	}
	
	public void setClueFound() {
		this.isFound = true;
	}
	
	public boolean getIsFoundClue() {
		return this.isFound;
	}
	
	public ArrayList<String> getInstructions() {
		return this.instructions;
	}
	
	public ArrayList<Double> getCoordinates() {
		return this.latlongCoordinates;
	}
	
	/*public void setKey(Key newKey) {
		this.key = newKey;
	}
	
	public Key getKey() {
		return this.key;
	}*/
}
