package org.redpin.server.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.redpin.server.standalone.core.Fingerprint;
import org.redpin.server.standalone.db.HomeFactory;
import org.redpin.server.standalone.json.GsonFactory;

@Path("/fingerprint")
public class FingerprintResource {
	/*@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map> getAll() {
		List<Fingerprint> listAll = new ArrayList<Fingerprint>();
		
		//Fingerprint p = new Fingerprint();
		//p.setLocation();
		//p.set
    }*/
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Fingerprint postFingerprint(String jsonRequest) {
		Fingerprint fingerprint = GsonFactory.getGsonInstance().fromJson(jsonRequest, Fingerprint.class);
		fingerprint = HomeFactory.getFingerprintHome().add(fingerprint);
		
		return fingerprint;
	}
}
