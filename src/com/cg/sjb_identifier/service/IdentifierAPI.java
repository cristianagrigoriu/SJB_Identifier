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
	public Identifier addId(@Named("id") Integer id) throws NotFoundException {
		//Check for already exists
		int index = ids.indexOf(new Identifier(id));
		if (index != -1) throw new NotFoundException("ID Record already exists");

		Identifier q = new Identifier(id);
		ids.add(q);
		return q;
	}

	@ApiMethod(name="remove")
	public void removeId(@Named("id") Integer id) throws NotFoundException {
		int index = ids.indexOf(new Identifier(id));
		if (index == -1)
			throw new NotFoundException("ID Record does not exist");
		ids.remove(index);
	}

	@ApiMethod(name="list")
	public List<Identifier> getQuotes() {
		return ids;
	}

	@ApiMethod(name="getID")
	public Identifier getQuote(@Named("id") Integer id) throws NotFoundException {
		int index = ids.indexOf(new Identifier(id));
		if (index == -1)
			throw new NotFoundException("Quote Record does not exist");
		return ids.get(index);
	}
}
