package com.cg.sjb_identifier.service;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.apache.jasper.tagplugins.jstl.core.If;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Key;
import com.cg.sjb_identifier.entity.Identifier;
import com.cg.sjb_identifier.entity.Clue;
import com.cg.sjb_identifier.entity.TreasureHunt;

@Api(name="identifierapi",version="v1", description="An API to manage the ids for the Treasure Hunts")
public class IdentifierAPI {

	public static List<Identifier> ids = new ArrayList<Identifier>();
	PersistenceManager pm;

	@ApiMethod(name="addIdentifier")
	public Identifier addId(@Named("id") String id, @Named("name") String name) throws NotFoundException {	
		pm = PMF.get().getPersistenceManager();
		
		/*check if id already exists*/
		Identifier newId = new Identifier(id, name);
		
		List<Identifier> results = getAllIdsFromDatastore();
		if (results != null) {
			for (Identifier i : results) {
				if (i.getUniqueId().equals(id))
					//throw new EntityExistsException("ID Record already exists.");
			    	return newId;
			    }
		} 
		try {
			pm.makePersistent(newId);
		}
		finally {
			pm.close();
		}
		return newId;
	}

	@ApiMethod(name="removeIdentifier")
	public void removeId(@Named("id") String id) throws NotFoundException {
		pm = PMF.get().getPersistenceManager();
		
		List<Identifier> results = getAllIdsFromDatastore();
		
		if (!results.isEmpty()) {
			for (Identifier i : results) {
				if (i.getUniqueId().equals(id))
					try {
						pm.deletePersistent(i);
					}
					finally {
						pm.close();
					}
			    }
		} else {
			throw new NotFoundException("ID Record does not exist");
		}
	}

	@ApiMethod(name="list")
	public List<Identifier> getIds() throws NotFoundException {	
		pm = PMF.get().getPersistenceManager();
		List<Identifier> results = getAllIdsFromDatastore();
		
		try {
			if (results != null) {
				for (Identifier p : results) {
			    	System.out.println(p.getUniqueId() + " " + p.getName());
			    }
			} else {
				throw new NotFoundException("ID Record does not exist");
			}
			
			return results;
		}
		finally {
			pm.close();
		}	
	}

	@ApiMethod(name="getID")
	public Identifier getId(@Named("id") String id) throws NotFoundException {
		pm = PMF.get().getPersistenceManager();
		List<Identifier> results = getAllIdsFromDatastore();
		
		try {
			if (results != null) {
				for (Identifier i : results) {
					if (i.getUniqueId().equals(id))
			    		return i;
					}
			} else {
				throw new NotFoundException("ID Record does not exist");
			}
			
			return null;
		}
		finally {
			//pm.close();
		}
	}
	
	@ApiMethod(name="addTreasureHunt")
	public TreasureHunt addTreasureHunt(@Named("id") String id, @Named("name") String name) throws NotFoundException {
		/*check if already exists*/
		TreasureHunt newTH = new TreasureHunt(id, name);
				
		List<TreasureHunt> results = getAllTreasureHuntsFromDatastore();
		if (results!= null) {
			for (TreasureHunt t : results) {
				if (t.getUniqueId().equals(id))
					//throw new EntityExistsException("ID Record already exists.");
					return newTH;
				}
		} 
		
		pm = PMF.get().getPersistenceManager();
		
		/*add the new treasure hunt*/
		try {
			pm.makePersistent(newTH);
		}
		finally {
			pm.close();
		}
					
		return newTH;
	}
	
	@ApiMethod(name="removeTreasureHunt")
	public void removeTreasureHunt(@Named("id") String id) throws NotFoundException {
		pm = PMF.get().getPersistenceManager();
		
		List<TreasureHunt> results = getAllTreasureHuntsFromDatastore();
		
		if (results != null) {
			try
			{
				for (TreasureHunt t : results)
					if (t.getUniqueId().equals(id)) {
						/*delete cascade th from all users' lists*/
						List<Identifier> ids = getAllIdsFromDatastore();
						if (ids != null)
							for (Identifier i : ids)
								if (i.hasTreasureHunt(t))
									i.deleteTreasureHunt(t);
						
						pm.deletePersistent(t);
					}
			}
			finally {
				pm.close();
			}
		} else {
			throw new NotFoundException("ID Record does not exist");
		}
	}

	@ApiMethod(name="listTreasureHunts")
	public List<TreasureHunt> getTreasureHunts() throws NotFoundException {
		pm = PMF.get().getPersistenceManager();
		List<TreasureHunt> results = getAllTreasureHuntsFromDatastore();
		
		try {
			if (results != null) {
				for (TreasureHunt t : results) {
			    	System.out.println(t.getUniqueId() + " " + t.getName());
			    	//System.out.println(t.getAllClues().size());
			    	//System.out.println(t.getKey());
			    }
			} else {
				throw new NotFoundException("ID Record does not exist");
			}
			
			return results;
		}
		finally {
			pm.close();
		}
	}

	@ApiMethod(name="getTreasureHuntByID")
	public TreasureHunt getTreasureHuntById(@Named("id") String id) throws NotFoundException {
		//pm = PMF.get().getPersistenceManager();
		
		try {
			List<TreasureHunt> results = getAllTreasureHuntsFromDatastore();
			
			if (results != null) {
				for (TreasureHunt t : results) {
					if (t.getUniqueId().equals(id)) {
			    		return t; }
					}
			} else {
				throw new NotFoundException("ID Record does not exist");
			}
			return null;
		}
		finally {
			//pm.close();
		}
	}
	
	/*adds just the treasure hunts the user does not have already*/
	@ApiMethod(name="setTreasureHuntsForId")
	public Identifier setTreasureHuntsForId(@Named("id") String id) throws NotFoundException {
		//pm = PMF.get().getPersistenceManager();
		List<TreasureHunt> resultsTH = getAllTreasureHuntsFromDatastore();
		pm.close();
		Identifier found = getId(id);
		//Identifier found = pm.detachCopy(getId(id));
		List<Key> keyTHs = new ArrayList<Key>();
		List<TreasureHunt> ths = new ArrayList<TreasureHunt>();
		System.out.println(JDOHelper.getPersistenceManager(found));
		System.out.println(JDOHelper.getPersistenceManager(found.getKey()));
			//
			if (found != null) {
				if (resultsTH != null)
					for (TreasureHunt th : resultsTH) {
						if (!found.hasTreasureHunt(th)) {
							keyTHs.add(th.getKey());
							ths.add(th);
							System.out.println(JDOHelper.getPersistenceManager(th.getKey()));
						}
					}
				
				//found.setTreasureHuntKeys(keyTHs);
				//found.addTreasureHuntList(ths);
				
				//pm = PMF.get().getPersistenceManager();
				try {
					//pm.close();
					//pm = PMF.get().getPersistenceManager();
					found.addTreasureHuntList(ths);
					//System.out.println(JDOHelper.getPersistenceManager(found));
					//System.out.println(JDOHelper.getPersistenceManager(found.getKey()));
					
					pm.makePersistent(found);
				}
				finally {
					pm.close();
					//Identifier f = getId(id);
				}
				
				return found;
			}
			else
				throw new NotFoundException("ID Record does not exist");
		
		
	}
	
	@ApiMethod(name="getTreasureHuntsForId")
	public List<TreasureHunt> getTreasureHuntsForId(@Named("id") String id) throws NotFoundException {
		Identifier found = getId(id);
		//List<Key> resultsTH = found.getTreasureHuntKeys();
		
		return found.getTreasureHunts();
		
		/*try {
			List<TreasureHunt> ths = new ArrayList<TreasureHunt>();
			pm = PMF.get().getPersistenceManager();
			for (Key keyTH : resultsTH) {
				TreasureHunt t = pm.getObjectById(TreasureHunt.class, keyTH);
				ths.add(t);
			}
				
			return ths;
		}
		finally {
			pm.close();
		}*/
	}
	
	@ApiMethod(name="addClue")
	public TreasureHunt addClue(@Named("id") String id, 
			@Named("instruction1") String instructionMedium, @Named("instruction2") String instructionHard,
			@Named("coordinateLat") Double coordLat, @Named("coordinateLong") Double coordLong) throws NotFoundException {
		
		TreasureHunt found = getTreasureHuntById(id);
		
		ArrayList<String> instructions = new ArrayList<String>();
		instructions.add(instructionMedium); 
		instructions.add(instructionHard);
				
		ArrayList<Double> coordinates = new ArrayList<Double>();
		coordinates.add(coordLat); 
		coordinates.add(coordLong);
				
		Clue newClue = new Clue(instructions, coordinates);
		found.addClueTo(newClue);
		
		//pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(found);
			//TreasureHunt start = getTreasureHuntById(id);
			//System.out.println(start.getAllClues().size());
		}
		finally {
			//pm.close();
		}
		
		return found;
	}
	
	@ApiMethod(name="setTHCompletedForUser")
	public Identifier setTHCompletedTHForUser(@Named("id") String userId, @Named("thID") String thId) throws NotFoundException {		
		Identifier id = getId(userId);
		TreasureHunt th;
		
		if (id != null) {
			if ((th = id.getUserTH(thId)) != null)
				th.setCompleted();
			
			//pm = PMF.get().getPersistenceManager();
			try {
				pm.makePersistent(id);
				return id;
			}
			finally {
				pm.close();
			}
		}
		else
			throw new NotFoundException("ID Record does not exist");
	}
	
	@ApiMethod(name="setClueCompletedForTHForUser")
	public void setClueCompletedForTHForUser(@Named("id") String userId, @Named("thID") String thId, @Named("clueNo") int clueNo) throws NotFoundException {		
		Identifier id = getId(userId);
		
		if (id != null) {
			TreasureHunt th = id.getUserTH(thId);
			
			if (th != null && th.getAllClues() != null)
				th.getAllClues().get(clueNo).setClueFound();
			
				pm = PMF.get().getPersistenceManager();
				try {
					pm.makePersistent(id);
				}
				finally {
					pm.close();
				}	
		}
		else {
			throw new NotFoundException("ID Record does not exist");
		}
	}
	
	private List<TreasureHunt> getAllTreasureHuntsFromDatastore() {
		pm = PMF.get().getPersistenceManager();
		
		javax.jdo.Query q = pm.newQuery(TreasureHunt.class);

		List<TreasureHunt> results = new ArrayList<TreasureHunt>();
		
		try {
		  results = (List<TreasureHunt>) q.execute();
		  results.size();
		} finally {
		  q.closeAll();
		}
		
		//pm.close();
		
		return results;
	}
	
	private void addTreasureHuntToAllUsers(TreasureHunt newTH) {
		pm = PMF.get().getPersistenceManager();
		
		List<Identifier> resultsIds = getAllIdsFromDatastore();
		
		for (Identifier i : resultsIds) {
			i.addTreasureHunt(newTH);
		}
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
		
		//pm.close();
		
		return results;
	}
}
