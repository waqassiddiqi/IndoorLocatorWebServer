package org.redpin.server.api.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.redpin.server.standalone.core.Map;
import org.redpin.server.standalone.json.GsonFactory;

@Path("/map")
public class MapResource {
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map> getAll() {
         List<Map> listAll = new ArrayList<Map>();
         
         Map m = new Map();
         m.setId(1);
         m.setMapName("Home");
         m.setMapURL("http://10.12.100.9/mapuploads/home.png");
                 
         listAll.add(m);
         
         return listAll;
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map postMap(String jsonRequest) {
		
		System.out.println(jsonRequest);
		
		Map map = GsonFactory.getGsonInstance().fromJson(jsonRequest, Map.class);
		//map = mapHome.add(map);
		
		return map;
	}
}
