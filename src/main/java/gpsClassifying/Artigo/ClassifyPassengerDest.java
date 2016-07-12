package gpsClassifying.Artigo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClassifyPassengerDest {
	static boolean countWork = false;
	static boolean countResidential = false;
	static boolean countNightLife = false;
	static boolean countPersonnal = false;
    public static void main( String[] args ) throws IOException, ParseException
    {
        String cardsFile = "D:/Raul/paper1/2_CARDS-GPS-STOPS.csv";
        String mergedFile = "D:/Raul/paper1/4_PASSENGERS_DEST.csv";
    	BufferedReader readCards = null;
    	String lineCards = "";
    	String cvsSplitBy = ",";
    	FileWriter writer = new FileWriter(mergedFile);
    	writer.append("GPS_ID,TIME,TYPE,BUS_LINE,STOP_DISTANCE,STOP_NAME,PROPOSAL");
    	String stopType="";
    	try {
    			readCards = new BufferedReader(new FileReader(cardsFile));
    			while ((lineCards = readCards.readLine()) != null) {
    				String[] partsCards = lineCards.split(cvsSplitBy);
    				SimpleDateFormat dateFormatCards = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
    				Date date = dateFormatCards.parse(partsCards[1]+":00");
    				classifyStops(date);
    				try{
        				if(countWork)
        					stopType = "WORK";
        				if(countResidential)
        					stopType = "RESIDENTIAL";
        				if(countNightLife)
        					stopType = "NIGHT";
        				if(countPersonnal)
        					stopType = "PERSONNAL";
        				
        				System.out.println("\n"+ countWork + countResidential + countNightLife);
        				writer.append("\n"+lineCards +","+stopType);   				  
    	    		    stopType="";
    	    		    countWork = false;
    	    			countResidential = false;
    	    			countNightLife = false;
    	    			countPersonnal = false;
        			}catch (Exception e){
        				System.out.println("DEU MERDA GALERA ***************** ");
        			}  		   
    			}			  			
    			writer.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	System.out.println("Done");      
    }
    
    public static void classifyStops(Date date) throws ParseException {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
    	Date dateRES_OUT_1 = dateFormat.parse("22/10/2013 06:00:00");
    	Date dateRES_OUT_2 = dateFormat.parse("22/10/2013 09:00:00");
    	
    	Date dateRES_OUT_3 = dateFormat.parse("22/10/2013 13:00:00");
    	Date dateRES_OUT_4 = dateFormat.parse("22/10/2013 14:00:00");
    	
    	Date dateWOR_OUT_1 = dateFormat.parse("22/10/2013 17:00:00");
    	Date dateWOR_OUT_2 = dateFormat.parse("22/10/2013 20:00:00");
    	
    	Date dateWOR_OUT_3 = dateFormat.parse("22/10/2013 11:30:00");
    	Date dateWOR_OUT_4 = dateFormat.parse("22/10/2013 12:30:00");
    	
    	Date dateNIG_OUT_1 =  dateFormat.parse("22/10/2013 23:00:00");
    	Date dateNIG_OUT_2 =  dateFormat.parse("22/10/2013 05:00:00");
    	
    	/*Date dateRES_IN_1 = dateFormat.parse("22/10/2013 18:00");
    	Date dateRES_IN_2 = dateFormat.parse("22/10/2013 20:00");
    	
    	Date dateWOR_IN_1 = dateFormat.parse("22/10/2013 07:00");
    	Date dateWOR_IN_2 = dateFormat.parse("22/10/2013 09:00");
    	
    	Date dateNIG_IN_1 =  dateFormat.parse("22/10/2013 20:00");
    	Date dateNIG_IN_2 =  dateFormat.parse("22/10/2013 23:00");*/
    	  	
    	if((date.compareTo(dateRES_OUT_1)>0 && date.compareTo(dateRES_OUT_2)<0) || (date.compareTo(dateRES_OUT_3)>0 && date.compareTo(dateRES_OUT_4)<0)){
    		System.out.println("RESIDENTIAL");
    		countResidential = true;
    	}else if((date.compareTo(dateWOR_OUT_1)>0 && date.compareTo(dateWOR_OUT_2)<0) || (date.compareTo(dateWOR_OUT_3)>0 && date.compareTo(dateWOR_OUT_4)<0)){
    		System.out.println("WORK");
    		countWork = true;
    	}else if(date.compareTo(dateNIG_OUT_1)>0 && date.compareTo(dateNIG_OUT_2)<0){
    		System.out.println("NIGHT");
    		countNightLife = true;
    	}else{
    		countPersonnal = true;
    		System.out.println("How to get here?");
    	}
	}
}
