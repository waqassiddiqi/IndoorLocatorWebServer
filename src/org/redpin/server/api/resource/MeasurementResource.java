package org.redpin.server.api.resource;

import javax.ws.rs.Path;

@Path("/measurement")
public class MeasurementResource {
	/*@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map> getAll() {
		List<Fingerprint> listAll = new ArrayList<Fingerprint>();
		
		//Fingerprint p = new Fingerprint();
		//p.setLocation();
		//p.set
    }*/
	/*
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Measurement postFingerprint(String jsonRequest) {
		Fingerprint fingerprint = GsonFactory.getGsonInstance().fromJson(jsonRequest, Fingerprint.class);
		fingerprint = HomeFactory.getFingerprintHome().add(fingerprint);
		
		return fingerprint;
	}
	*/
}
