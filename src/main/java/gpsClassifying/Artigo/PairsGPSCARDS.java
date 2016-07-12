package gpsClassifying.Artigo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class PairsGPSCARDS {
    public static void main( String[] args ) throws IOException, ParseException
    {
        String gpsFile = "D:/Raul/paper1/1_GPS-STOPS.csv";
        String cardsFile = "D:/Raul/paper1/OUT_CARD.csv";
        String mergedFile = "D:/Raul/paper1/2_CARDS-GPS-STOPS.csv";
    	BufferedReader readGPS = null;
    	BufferedReader readCards = null;
    	TreeMap<Integer,String> cardsStops = new TreeMap<Integer,String>();
    	String lineGPS = "";
    	String lineCards = "";
    	String cvsSplitBy = ",";
    	FileWriter writer = new FileWriter(mergedFile);
    	writer.append("CARD_ID,TIME,TYPE,BUS_LINE,TIME_DIF,STOP");
    	int count=0;
    	try {
    		readCards = new BufferedReader(new FileReader(cardsFile));
    		while ((lineCards = readCards.readLine()) != null) {
    			String[] partsCards = lineCards.split(cvsSplitBy); 
    			readGPS = new BufferedReader(new FileReader(gpsFile));
    			while ((lineGPS = readGPS.readLine()) != null) {
    				String[] partsGPS = lineGPS.split(cvsSplitBy);
    				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
    				SimpleDateFormat dateFormatCards = new SimpleDateFormat("YYY-MM-dd hh:mm:ss");
    				Date parsedGPSDate = dateFormat.parse(partsGPS[1]+":00");
    				Date parsedCardDate = dateFormatCards.parse(partsCards[1]);
    				int result = compareDates(parsedGPSDate,parsedCardDate);    				
    				cardsStops.put(result, partsGPS[6]);
    			}   	
    			
    			try{
	    			TreeMap<Integer,String> cardsStopsSorted = new TreeMap<Integer,String>(cardsStops);
	    		    //System.out.print("\n"+cardsStopsSorted.firstEntry().getKey());
	    		    //System.out.print("\n"+cardsStopsSorted.firstEntry().getValue());
	    		    writer.append("\n"+lineCards+","+cardsStopsSorted.firstEntry().getKey()+","+cardsStopsSorted.firstEntry().getValue());
	    		    count++;
	    		    System.out.println(count);
	    		    cardsStops.clear();
	    		    cardsStopsSorted.clear();
    			}catch (Exception e){
    				System.out.println("merda  -   ");
    			}
    		   
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
    
    public static int compareDates(Date date1, Date date2) {
    	/*if(date1.compareTo(date2)>0){
    		System.out.println("Date1 is after Date2");
    		return "MAIOR";
    	}else if(date1.compareTo(date2)<0){
    		System.out.println("Date1 is before Date2");
    		return "MENOR";
    	}else if(date1.compareTo(date2)==0){
    		System.out.println("Date1 is equal to Date2");
    		return "IGUAL";
    	}else{
    		System.out.println("How to get here?");
    	}
    	return "FAIL";*/
    	return Math.abs(date1.getMinutes()-date2.getMinutes());
	}
}
