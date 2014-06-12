package com.cg.sjb_identifier.service;

import com.cg.sjb_identifier.entity.*;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.persistence.EntityExistsException;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.cg.sjb_identifier.entity.Identifier;

@Api(name="identifierapi",version="v1", description="An API to manage the ids for the Treasure Hunts")
public class IdentifierAPI {

	public static List<Identifier> ids = new ArrayList<Identifier>();
	PersistenceManager pm = PMF.get().getPersistenceManager();;

	@ApiMethod(name="addIdentifier")
	public Identifier addId(@Named("id") String id, @Named("name") String name) throws NotFoundException {		
		//Check if already exists
		Identifier newId = new Identifier(id, name);
		
		List<Identifier> results = getAllIdsFromDatastore();
		if (!results.isEmpty()) {
			for (Identifier i : results) {
				if (i.getUniqueId().equals(id))
					//throw new EntityExistsException("ID Record already exists.");
			    	return newId;
			    }
		} 
		
		pm.makePersistent(newId);
			
		return newId;
	}

	@ApiMethod(name="removeIdentifier")
	public void removeId(@Named("id") String id) throws NotFoundException {
		List<Identifier> results = getAllIdsFromDatastore();
		
		if (!results.isEmpty()) {
			for (Identifier i : results) {
				if (i.getUniqueId().equals(id))
					pm.deletePersistent(i);
			    }
		} else {
			throw new NotFoundException("ID Record does not exist");
		}
	}

	@ApiMethod(name="list")
	public List<Identifier> getQuotes() throws NotFoundException {	
		List<Identifier> results = getAllIdsFromDatastore();
		
		if (!results.isEmpty()) {
			for (Identifier p : results) {
		    	System.out.println(p.getUniqueId() + " " + p.getName());
		    }
		} else {
			throw new NotFoundException("ID Record does not exist");
		}
		
		return results;
	}

	@ApiMethod(name="getID")
	public Identifier getId(@Named("id") String id) throws NotFoundException {
		
		List<Identifier> results = getAllIdsFromDatastore();
		
		if (!results.isEmpty()) {
			for (Identifier i : results) {
				if (i.getUniqueId().equals(id))
		    		return i;
				}
		} else {
			throw new NotFoundException("ID Record does not exist");
		}
		
		return null;
	}
	
	@ApiMethod(name="addTreasureHunt")
	public TreasureHunt addTreasureHunt(@Named("id") String id, @Named("name") String name) throws NotFoundException {
		//Check if already exists
		TreasureHunt newTH = new TreasureHunt(id, name);
				
		List<TreasureHunt> results = getAllTreasureHuntsFromDatastore();
		if (!results.isEmpty()) {
			for (TreasureHunt t : results) {
				if (t.getUniqueId().equals(id))
					//throw new EntityExistsException("ID Record already exists.");
					return newTH;
				}
		} 
		
		/*add the new treasure hunt*/
		pm.makePersistent(newTH);
					
		return newTH;
	}
	
	@ApiMethod(name="removeTreasureHunt")
	public void removeTreasureHunt(@Named("id") String id) throws NotFoundException {
		List<TreasureHunt> results = getAllTreasureHuntsFromDatastore();
		
		if (!results.isEmpty()) {
			for (TreasureHunt t : results) {
				if (t.getUniqueId().equals(id))
					pm.deletePersistent(t);
			    }
		} else {
			throw new NotFoundException("ID Record does not exist");
		}
	}

	@ApiMethod(name="listTreasureHunts")
	public List<TreasureHunt> getTreasureHunts() throws NotFoundException {	
		List<TreasureHunt> results = getAllTreasureHuntsFromDatastore();
		
		if (!results.isEmpty()) {
			for (TreasureHunt t : results) {
		    	System.out.println(t.getUniqueId() + " " + t.getName());
		    }
		} else {
			throw new NotFoundException("ID Record does not exist");
		}
		
		return results;
	}

	@ApiMethod(name="getTreasureHuntByID")
	public TreasureHunt getTreasureHuntById(@Named("id") String id) throws NotFoundException {
		
		List<TreasureHunt> results = getAllTreasureHuntsFromDatastore();
		
		if (!results.isEmpty()) {
			for (TreasureHunt t : results) {
				if (t.getUniqueId().equals(id))
		    		return t;
				}
		} else {
			throw new NotFoundException("ID Record does not exist");
		}
		
		return null;
	}
	
	@ApiMethod(name="setTreasureHuntsForId")
	public void setTreasureHuntsForId(@Named("id") String id) throws NotFoundException {
		List<TreasureHunt> resultsTH = getAllTreasureHuntsFromDatastore();
		Identifier found = getId(id);
		ArrayList<UserTreasureHunt> uths = new ArrayList<UserTreasureHunt>();
		
		for (TreasureHunt t : resultsTH){
			UserTreasureHunt uth = new UserTreasureHunt(t);
			uths.add(uth);
		}
			
		found.setTreasureHunts(uths);
	}
	
	private List<TreasureHunt> getAllTreasureHuntsFromDatastore() {
		javax.jdo.Query q = pm.newQuery(TreasureHunt.class);

		List<TreasureHunt> results;
		
		try {
		  results = (List<TreasureHunt>) q.execute();
		  results.size();
		} finally {
		  q.closeAll();
		}
		return results;
	}
	
	private List<Identifier> getAllIdsFromDatastore() {
		javax.jdo.Query q = pm.newQuery(Identifier.class);

		List<Identifier> results;
		
		try {
		  results = (List<Identifier>) q.execute();
		  results.size();
		} finally {
		  q.closeAll();
		}
		return results;
	}
}
