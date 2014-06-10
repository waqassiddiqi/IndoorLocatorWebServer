package org.redpin.server.api.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.redpin.server.core.Map;


@Path("/map")
public class MapResource {
	
	@Context
    UriInfo uriInfo;
	
	@Context
    Request request;
	
	/*@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return "Demo service is ready!";
    }*/
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map> getSamplePerson() {
		Map m = new Map();
		 m.setMapName("Home");
		 m.setMapURL("http://192.4.70.1/maps/map1.png");
		
		List<Map> l = new ArrayList<Map>();
		
		l.add(m);
		
		return l;
    }
}