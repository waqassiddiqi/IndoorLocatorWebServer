package org.redpin.server.api.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.redpin.server.standalone.core.History;
import org.redpin.server.standalone.db.HomeFactory;
import org.redpin.server.standalone.json.GsonFactory;

@Path("/history")
public class HistoryResource {

	
	@GET
	@Path("/live/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<History> getCurrentByMapId(@PathParam("id") String id) {
		List<History> history = HomeFactory.getHistoryHome().getLastKnownHistoryByMapId(Integer.parseInt(id));
		
		List<History> currentHistory = new ArrayList<History>();
		HashMap<String, History> map = new HashMap<String, History>();
		
		for(History h : history) {
			if(map.containsKey(h.getUser()) == false) {
				map.put(h.getUser().getUserName(), h);
			} else {
				History temp = map.get(h.getUser().getUserName());
				if(temp.getDate().compareTo(h.getDate()) > 0) {
					map.put(h.getUser().getUserName(), h);
				}
			}
		}
		
		Iterator<Entry<String, History>> it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        
	        currentHistory.add((History) pairs.getValue());
	    }
	    
	    return currentHistory;
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public History postHistory(String jsonRequest) {
		
		History history = GsonFactory.getGsonInstance().fromJson(jsonRequest, History.class);
		history = HomeFactory.getHistoryHome().add(history);
		
		return history;
	}
}
