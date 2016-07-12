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

public class ClassifyBusStop {
	static int countWork = 0;
	static int countResidential = 0;
	static int countNightLife = 0;
	static int countPersonal = 0;
    public static void main( String[] args ) throws IOException, ParseException
    {
        String stopsFile = "D:/Raul/paper1/stops.csv";
        String cardsFile = "D:/Raul/paper1/2_CARDS-GPS-STOPS.csv";
        String mergedFile = "D:/Raul/paper1/3_STOPS_CLASSIFIED-2.csv";
    	BufferedReader readStops = null;
    	BufferedReader readCards = null;
    	Map<String,Integer> cardsStops = new HashMap<String,Integer>();
    	String lineStops = "";
    	String lineCards = "";
    	String cvsSplitBy = ",";
    	FileWriter writer = new FileWriter(mergedFile);
    	writer.append("STOP,TYPE,WORK,RESIDENTIAL,NIGHT,PERSONAL");
    	int count=0;
    	String stopType="";
    	try {
    		readStops = new BufferedReader(new FileReader(stopsFile));
    		while ((lineStops = readStops.readLine()) != null) {
    			String[] partsStops = lineStops.split(cvsSplitBy); 
    			readCards = new BufferedReader(new FileReader(cardsFile));
    			while ((lineCards = readCards.readLine()) != null) {
    				String[] partsCards = lineCards.split(cvsSplitBy);
    				
    				if(partsStops[1].equalsIgnoreCase(partsCards[5])){
    					//tratar classificacao
    					SimpleDateFormat dateFormatCards = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
    					Date date = dateFormatCards.parse(partsCards[1]+":00");
    					classifyStops(date);
    					count++;
    				}			
    			}
    			try{
    				if(countWork>countResidential && countWork>countNightLife)
    					stopType = "WORK";
    				if(countResidential>countWork && countResidential>countNightLife)
    					stopType = "RESIDENTIAL";
    				if(countNightLife>countResidential && countNightLife>countWork)
    					stopType = "NIGHT";
    				
    				System.out.println("\n"+ countWork + countResidential + countNightLife);
    				writer.append("\n"+partsStops[1]+","+stopType+","+countWork+","+countResidential+","+countNightLife);   				
	    		    count=0;    
	    		    stopType="";
	    		    countWork = 0;
	    			countResidential = 0;
	    			countNightLife = 0;
	    			countPersonal = 0;
    			}catch (Exception e){
    				System.out.println("merda  -   ");
    			}
    		   
    		}  	
    		writer.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} finally {
    		if (readStops != null) {
    			try {
    				readStops.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}

    	System.out.println("Done");      
    }
    
    public static void classifyStops(Date date) throws ParseException {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
    	Date dateRES_OUT_1 = dateFormat.parse("22/10/2013 06:00:00");
    	Date dateRES_OUT_2 = dateFormat.parse("22/10/2013 08:00:00");
    	
    	Date dateWOR_OUT_1 = dateFormat.parse("22/10/2013 17:00:00");
    	Date dateWOR_OUT_2 = dateFormat.parse("22/10/2013 19:00:00");
    	
    	Date dateNIG_OUT_1 =  dateFormat.parse("22/10/2013 23:00:00");
    	Date dateNIG_OUT_2 =  dateFormat.parse("22/10/2013 05:00:00");
    	
    	/*Date dateRES_IN_1 = dateFormat.parse("22/10/2013 18:00");
    	Date dateRES_IN_2 = dateFormat.parse("22/10/2013 20:00");
    	
    	Date dateWOR_IN_1 = dateFormat.parse("22/10/2013 07:00");
    	Date dateWOR_IN_2 = dateFormat.parse("22/10/2013 09:00");
    	
    	Date dateNIG_IN_1 =  dateFormat.parse("22/10/2013 20:00");
    	Date dateNIG_IN_2 =  dateFormat.parse("22/10/2013 23:00");*/
    	  	
    	if(date.compareTo(dateRES_OUT_1)>0 && date.compareTo(dateRES_OUT_2)<0){
    		System.out.println("RESIDENTIAL");
    		countResidential++;
    	}else if(date.compareTo(dateWOR_OUT_1)>0 && date.compareTo(dateWOR_OUT_2)<0){
    		System.out.println("WORK");
    		countWork++;
    	}else if(date.compareTo(dateNIG_OUT_1)>0 && date.compareTo(dateNIG_OUT_2)<0){
    		System.out.println("NIGHT");
    		countNightLife++;
    	}else{
    		System.out.println("How to get here?");
    		countPersonal++;
    	}
	}
}
