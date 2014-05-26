package com.cg.sjb_identifier.entity;

public class Identifier {
	String uniqueId;
	String name;
	
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
