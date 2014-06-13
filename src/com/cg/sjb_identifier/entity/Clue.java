package com.cg.sjb_identifier.entity;

import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Clue {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    int id;
	
	/*more instructions for different difficulty levels: (easy), (medium), (hard)*/
	@Persistent
	ArrayList<String> instructions;
	@Persistent
	ArrayList<Double> latlongCoordinates;
	
	public Clue(ArrayList<String> theInstructions, ArrayList<Double> theCoordinates) {
		this.instructions = new ArrayList<String>();
		for (String i: theInstructions)
			this.instructions.add(i);
		
		this.latlongCoordinates = new ArrayList<Double>();
		for (Double i: theCoordinates)
			this.latlongCoordinates.add(i);
	}
	
	public Clue() {}
	
	public ArrayList<String> getInstructions() {
		return this.instructions;
	}
	
	public ArrayList<Double> getCoordinates() {
		return this.latlongCoordinates;
	}
}