package org.redpin.server.standalone.locator;

import java.util.ArrayList;
import java.util.List;

import org.redpin.server.standalone.core.Fingerprint;
import org.redpin.server.standalone.core.Location;
import org.redpin.server.standalone.core.Measurement;
import org.redpin.server.standalone.core.measure.WiFiReading;
import org.redpin.server.standalone.db.HomeFactory;
import org.redpin.server.standalone.db.homes.FingerprintHome;
import org.redpin.server.standalone.db.homes.MeasurementHome;

public class CustomLocator implements ILocator {

	MeasurementHome measurementHome = null;
	FingerprintHome fingerprintHome = null;
	
	public CustomLocator() {
		measurementHome = HomeFactory.getMeasurementHome();
		fingerprintHome = HomeFactory.getFingerprintHome();
	}
	
	@Override
	public Location locate(Measurement m) {
		
		Location loc = null;
		List<Measurement> candidateMeasurement = new ArrayList<Measurement>();
		List<Fingerprint> candidateFingerprint = new ArrayList<Fingerprint>();
		
		List<Measurement> list = measurementHome.getAll();
		for(Measurement measurement : list) {
			for(WiFiReading r : measurement.getWiFiReadings()) {
				if(m.getWiFiReadings().contains(r)) {
					
					Fingerprint fp = fingerprintHome.getByMeasurementId(measurement.getId());
					if(fp != null) {
						candidateFingerprint.add(fp);
						candidateMeasurement.add(measurement);
					}
					//break;
				}
			}
		}
		
		Fingerprint p = MatchFingerprint(m, candidateFingerprint);
		if(p != null)
			loc = (Location) p.getLocation();
		
		return loc;
	}
	
	private Fingerprint MatchFingerprint(Measurement observedSignal, List<Fingerprint> candidates) {
		
		double closestMatchValue = 0.0;
        int closestMatchIdx = 0;
        double d;
        
        for (int i = 0; i < candidates.size(); i++) {
            //Signal[] candidateSignalVector = candidates[i].signal;
            
            d = EuclideanDistance(observedSignal, candidates.get(i).getMeasurement());
            if (d < closestMatchValue || i == 0) {
                closestMatchValue = d;
                closestMatchIdx = i;
            }
        }
        return candidates.get(closestMatchIdx);
	}
	
	private double EuclideanDistance(Measurement observedSignal, org.redpin.base.core.Measurement signalVector) {
        double euclideanDistance = 0;

        for(WiFiReading signal : observedSignal.getWiFiReadings()) {
        	for (int i = 0; i < signalVector.getWiFiReadings().size(); i++) {
        		System.out.println(((org.redpin.base.core.measure.WiFiReading) signalVector.getWiFiReadings().get(i)).getBssid());
        	}
        }
        
        for(WiFiReading signal : observedSignal.getWiFiReadings()) {
            boolean bssidFound = false;
            for (int i = 0; i < signalVector.getWiFiReadings().size(); i++) {
            	if(signalVector != null && signalVector.getWiFiReadings() != null && signalVector.getWiFiReadings().get(i) != null 
            			&& ((org.redpin.base.core.measure.WiFiReading) signalVector.getWiFiReadings().get(i)).getBssid() != null
            			&& signal != null && signal.getBssid() != null) {
            		
            		if (((org.redpin.base.core.measure.WiFiReading) signalVector.getWiFiReadings().get(i)).getBssid().equals(signal.getBssid())) // true at most once per vector
                    {
                        euclideanDistance += Math.pow(signal.getRssi() - ((org.redpin.base.core.measure.WiFiReading) signalVector.getWiFiReadings().get(i)).getRssi(), 2);
                        bssidFound = true;
                    }
            	}
            }
            
            if (!bssidFound) // observed AP/bssid not in fingerprint
            {
                // add "penalty" for missing AP
                euclideanDistance += Math.pow(65, 2); //Math.Pow(signal.rssi, 2);
            }
        }
        return Math.sqrt(euclideanDistance);
    }
	
	@Override
	public int measurementSimilarityLevel(org.redpin.base.core.Measurement t,
			org.redpin.base.core.Measurement o) {
		
		return 0;
	}

	@Override
	public Boolean measurmentAreSimilar(org.redpin.base.core.Measurement t,
			org.redpin.base.core.Measurement o) {
		
		return null;
	}

}