package org.redpin.server.api.resource;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.redpin.server.standalone.core.History;
import org.redpin.server.standalone.core.Location;
import org.redpin.server.standalone.core.Measurement;
import org.redpin.server.standalone.core.User;
import org.redpin.server.standalone.db.HomeFactory;
import org.redpin.server.standalone.json.GsonFactory;
import org.redpin.server.standalone.locator.LocatorHome;
import org.redpin.server.standalone.util.Log;

@Path("/location")
public class LocationResource {
	
	private Logger log = Log.getLogger();
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Location> getAll() {
		return HomeFactory.getLocationHome().getAll();
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Location postLocation(String jsonRequest) {
				
		log.finer("got measurement: " + jsonRequest);
		
		Location location = GsonFactory.getGsonInstance().fromJson(jsonRequest, Location.class);
		location = HomeFactory.getLocationHome().add(location);
		
		return location;
	}
	
	@POST
	@Path("/find")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Location findLocation(String jsonRequest) {
		
		log.finer("got measurement: " + jsonRequest);
		Measurement currentMeasurement = GsonFactory.getGsonInstance().fromJson(jsonRequest, Measurement.class);
		
		Location loc = LocatorHome.getLocator().locate(currentMeasurement);
		
		if(loc == null) {
			log.fine("no matching location found");
			
			loc = new Location();
			loc.setId(-1);
			
		} else {
			/*
			User u = new User();
			u.setName("Test User");
			u.setUserName("user1");
			
			boolean isExists = false;
			
			List<User> listUsers = HomeFactory.getUserHome().getAll();
			for(User user : listUsers) {
				if(user.getUserName().equals(u.getUserName())) {
					u = user;
					isExists = true;
					break;
				}
			}
			
			if(!isExists) {
				u = HomeFactory.getUserHome().add(u);
			}
			
			History h = new History();
			h.setUser(u);
			h.setLocation(loc);
			h.setDate(new Date());
			
			HomeFactory.getHistoryHome().add(h);
			*/
			
			log.finer("location found: " + loc + " accuracy: " + loc.getAccuracy());
		}
		
		return loc;
	}
}