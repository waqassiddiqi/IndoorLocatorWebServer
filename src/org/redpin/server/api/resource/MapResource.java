package org.redpin.server.api.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.redpin.server.standalone.core.Map;
import org.redpin.server.standalone.db.HomeFactory;
import org.redpin.server.standalone.json.GsonFactory;

@Path("/map")
public class MapResource {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map> getAll() {
         return HomeFactory.getMapHome().getAll();
    }
	
	@GET
	@Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Map getById(@PathParam("id") String id) {
		Map m;
		
		try {
			m = HomeFactory.getMapHome().getById(Integer.parseInt(id));
		} catch (Exception e) {
			throw new WebApplicationException(404);
		}
		
		return m;
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map postMap(String jsonRequest) {
		
		Map map = GsonFactory.getGsonInstance().fromJson(jsonRequest, Map.class);
		
		map = HomeFactory.getMapHome().add(map);
		
		return map;
	}
}