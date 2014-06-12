package com.cg.sjb_identifier.service;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.cg.sjb_identifier.entity.Identifier;

@Api(name="identifierapi",version="v1", description="An API to manage the ids for the Treasure Hunts")
public class IdentifierAPI {

	public static List<Identifier> ids = new ArrayList<Identifier>();
	//private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	PersistenceManager pm = PMF.get().getPersistenceManager();

	@ApiMethod(name="add")
	public Identifier addId(@Named("id") String id, @Named("name") String name) throws NotFoundException {
		//Check if already exists
		for (Identifier i: ids) {
			if (id.equals(i.getUniqueId())) {
				throw new NotFoundException("ID Record already exists");
			}
		}
		Identifier q = new Identifier(id, name);
		ids.add(q);
		
		try {
            pm.makePersistent(q);
        } finally {
            pm.close();
        }
		
		/*Query qq = new Query("Identifier");		
		PreparedQuery pq = datastore.prepare(qq);
		
		for (Entity result : pq.asIterable()) {
		  if (id.equals((String) result.getProperty("id"))) {
			  Entity newIdentifier = new Entity("Identifier");
			  newIdentifier.setProperty("id", id);
			  newIdentifier.setProperty("name", name);
				
			  datastore.put(newIdentifier);  
		  }		
		}*/
		
		return q;
	}

	@ApiMethod(name="remove")
	public void removeId(@Named("id") String id) throws NotFoundException {
		for (Identifier i: ids) {
			if (id.equals(i.getUniqueId())) {
				ids.remove(i);
				return;
			}
		}
		throw new NotFoundException("ID Record does not exist");
	}

	@ApiMethod(name="list")
	public List<Identifier> getQuotes() {	
		/*Query q = new Query("Identifier");		
		PreparedQuery pq = datastore.prepare(q);
		
		for (Entity result : pq.asIterable()) {
		  String id = (String) result.getProperty("id");
		  String name = (String) result.getProperty("name");

		  System.out.println(id + " " + name);
		}*/
		
		return ids;
	}

	@ApiMethod(name="getID")
	public Identifier getId(@Named("id") String id) throws NotFoundException {
		for (Identifier i: ids) {
			if (id.equals(i.getUniqueId()))
				return i;
		}
		throw new NotFoundException("ID Record does not exist");
	}
}
