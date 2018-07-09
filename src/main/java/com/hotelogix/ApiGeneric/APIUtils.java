package com.hotelogix.ApiGeneric;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class APIUtils {
 
	
	
	
	
	private static APIUtils AU;
	
	public APIUtils(){
		
	}
	
	public static APIUtils GA(){
		if(AU==null){
			AU=new APIUtils();
		}
		return AU;
	}
	
	
	
	
	
	
	private static final String Num_LIST ="0123456789";
    private static final int RANDOM_NUM_LENGTH = 4;
	private static final String CHAR_LIST ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int RANDOM_STRING_LENGTH =5;
	public String fromdate;
	public String todate;
	
	
	
	public void fn_GetCurrentDate() throws Throwable{
		try{
			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		      Date date = new Date();
		      fromdate = format.format(date);
			  Calendar cal = Calendar.getInstance();
			  cal.add(Calendar.DATE, 1);
			  Date dob=cal.getTime();
			  SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			  todate= format1.format(dob);
        }catch(Exception e){
            throw e;
        }
	}
	
	
	public static String generateRandomnum(){
	    StringBuffer randStr = new StringBuffer();
	    for(int i=0; i<RANDOM_NUM_LENGTH-1; i++){
	        int number = getRandomNumber();
	        char ch = Num_LIST.charAt(number);
	        randStr.append(ch);
	    }
	    return randStr.toString();
	}
	
	
	public static String generateRandomString(){
        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

	
   public static int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(Num_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt; 
        } else {
            return randomInt - 1;
        }
    }

   public String GetCancellationChargeInAmount(String orderamt, String chargeamtinpercent) throws Exception{
	  double orderamount=Double.parseDouble(orderamt);
	  double chageamtinpercent=Double.parseDouble(chargeamtinpercent);
	  Double cancellationcharge=orderamount*chageamtinpercent/100;
	  return cancellationcharge.toString();
   }
   
	public JSONObject GetJsonObject(String value) throws JSONException{
		 String str=value.substring(1, value.length()-1);
		 JSONObject bookingjobj=new JSONObject(str);
	     return bookingjobj;
	}

	public String fn_getAMtInDouble(String amt){
	    Double db=Double.parseDouble(amt);
	    return db.toString();
	  }
	
	
	public String fn_setFrmToDate(String NAdate,int noOfNights) throws Exception{
		try{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	     
		String date1=NAdate;
        Calendar cal=Calendar.getInstance();
        Date date11=cal.getTime();
		String date2=format.format(date11);
        
	       Date dateBefore = format.parse(date1);
	       Date dateAfter = format.parse(date2);
	       long difference = dateAfter.getTime() - dateBefore.getTime();
	       float daysBetween = (difference / (1000*60*60*24));
          // System.out.println(difference);
           int i=(int)daysBetween;
		//Date date = format.parse(NAdate);
          cal.add(Calendar.DATE, -i+noOfNights);
		  Date dob=cal.getTime();			 		  
		  SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		  todate= format1.format(dob);
         // System.out.println(todate);
          return todate;
		}catch(Exception e){
			throw e;
		}
	}
	


	
}
