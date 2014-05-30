package com.cg.sjb_identifier.service;

import java.util.ArrayList;
import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.cg.sjb_identifier.entity.Identifier;

@Api(name="identifierapi",version="v1", description="An API to manage the ids for the Treasure Hunts")
public class IdentifierAPI {

	public static List<Identifier> ids = new ArrayList<Identifier>();

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
