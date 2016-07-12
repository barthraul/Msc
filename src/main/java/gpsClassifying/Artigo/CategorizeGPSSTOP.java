package gpsClassifying.Artigo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class CategorizeGPSSTOP 
{
    public static void main( String[] args ) throws IOException
    {
        String stopsFile = "D:/Raul/paper1/stops.csv";
        String gpsFile = "D:/Raul/paper1/OUT_GPS.csv";
        String mergedFile = "D:/Raul/paper1/1_GPS-STOPS.csv";
    	BufferedReader readGPS = null;
    	BufferedReader readStops = null;
    	TreeMap<Float,String> distancesStops = new TreeMap<Float,String>();
    	String lineGPS = "";
    	String lineStops = "";
    	String cvsSplitBy = ",";
    	FileWriter writer = new FileWriter(mergedFile);
    	writer.append("ID,TIME,LINE,LATITUDE,LONGITUDE,DISTANCE,BUSSTOP");
    	try {
    		readGPS = new BufferedReader(new FileReader(gpsFile));
    		while ((lineGPS = readGPS.readLine()) != null) {
    			String[] partsGPS = lineGPS.split(cvsSplitBy);
    			readStops = new BufferedReader(new FileReader(stopsFile));
    			while ((lineStops = readStops.readLine()) != null) {
    				String[] partsStops = lineStops.split(cvsSplitBy);
    				Float result = distFrom(Float.parseFloat(partsGPS[3]),Float.parseFloat(partsGPS[4]),Float.parseFloat(partsStops[4]),Float.parseFloat(partsStops[3]));
    				distancesStops.put(result,partsStops[1]);
    			}   			
    			TreeMap<Float,String> distancesStopsSorted = new TreeMap<Float,String>(distancesStops);
    		    System.out.print("\n"+distancesStopsSorted.firstEntry().getKey());
    		    System.out.print("\n"+distancesStopsSorted.firstEntry().getValue());
    		    writer.append("\n"+lineGPS+","+distancesStopsSorted.firstEntry().getKey()+","+distancesStopsSorted.firstEntry().getValue());

    		    distancesStops.clear();
    		    distancesStopsSorted.clear();
    		}  	
    		writer.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} finally {
    		if (readGPS != null) {
    			try {
    				readGPS.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}

    	System.out.println("Done");      
    }
      
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}

		return (dist);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
	    double earthRadius = 6371000; //meters
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    float dist = (float) (earthRadius * c);

	    return dist;
	}
}
