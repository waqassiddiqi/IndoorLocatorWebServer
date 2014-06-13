package org.redpin.server.api.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.redpin.server.standalone.core.Location;
import org.redpin.server.standalone.core.Map;
import org.redpin.server.standalone.db.HomeFactory;
import org.redpin.server.standalone.json.GsonFactory;

@Path("/location")
public class LocationResource {
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Location> getAll() {
         List<Location> listAll = new ArrayList<Location>();
         
         Location loc = new Location();
         loc.setId(1);
         loc.setSymbolicID("Room 1");
         loc.setAccuracy(90);
         
         Map m = new Map();
         m.setId(1);
         m.setMapName("Home");
         m.setMapURL("http://10.12.100.9/mapuploads/home.png");
         
         loc.setMap(m);
         
         listAll.add(loc);
         
         return listAll;
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Location postLocation(String jsonRequest) {
				
		System.out.println(jsonRequest);
		
		Location location = GsonFactory.getGsonInstance().fromJson(jsonRequest, Location.class);
		location = HomeFactory.getLocationHome().add(location);
		
		return location;
	}
}
