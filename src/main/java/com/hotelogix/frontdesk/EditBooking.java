package com.hotelogix.frontdesk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class EditBooking {

	
	public static String editBkngID;
	
	public static String newGuestID;
	
	public void fn_EditBookingAPI(String URL,String loginaccesskey,String type,String bookingID,String webRefID) throws UnirestException, JSONException, APIException{
		
		try{
		HttpResponse<JsonNode> response = Unirest.post(URL)
                .header("content-type", "application/json")
                .header("x-ig-sg", "D_gg%fkl85_j")
                .header("cache-control", "no-cache")
                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
                .body("{"
                		+ "\"hotelogix\": {"
                		+ "\"version\": \"1.0\","
                		+ "\"datetime\": \"2012-01-16T10:10:15\","
                		+ "\"request\": {"
                		+ "\"method\": \"editbooking\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"type\": \""+type+"\","
                		+ "\"id\": \""+bookingID+"\""
                		+ "}}}}")
                     .asJson();
	 JsonNode responseJSONString=response.getBody();
	 Assert.assertEquals(response.getCode(), 200);
	 String str=responseJSONString.toString();
	 JSONObject jobj1=new JSONObject(str);
	 JSONObject hotelogix= jobj1.getJSONObject("hotelogix");
	 JSONObject respons=hotelogix.getJSONObject("response");
	 JSONObject status= respons.getJSONObject("status");
	 Assert.assertEquals(status.getString("message"), "success");
	 JSONArray bookingarr= respons.getJSONArray("bookings");
	 for(int i=0;i<=bookingarr.length();i++){
		 JSONObject bkgObj=bookingarr.getJSONObject(i);
		 String webID=bkgObj.get("webRefId").toString();
		 if(webID.equals(webRefID)){
			 editBkngID=bkgObj.get("id").toString();
			 break;
		 }	 
	 }
		}catch(Throwable e){
			throw new APIException("fn_EditBookingAPI has failed", e);
		}
	 
	}
	
	
public void fn_extractGuestID(String URL,String loginaccesskey,String type,String bookingID,String webRefID) throws UnirestException, JSONException, APIException{
		
		try{
		HttpResponse<JsonNode> response = Unirest.post(URL)
                .header("content-type", "application/json")
                .header("x-ig-sg", "D_gg%fkl85_j")
                .header("cache-control", "no-cache")
                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
                .body("{"
                		+ "\"hotelogix\": {"
                		+ "\"version\": \"1.0\","
                		+ "\"datetime\": \"2012-01-16T10:10:15\","
                		+ "\"request\": {"
                		+ "\"method\": \"editbooking\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"type\": \""+type+"\","
                		+ "\"id\": \""+bookingID+"\""
                		+ "}}}}")
                     .asJson();
	 JsonNode responseJSONString=response.getBody();
	 Assert.assertEquals(response.getCode(), 200);
	 String str=responseJSONString.toString();
	 JSONObject jobj1=new JSONObject(str);
	 JSONObject hotelogix= jobj1.getJSONObject("hotelogix");
	 JSONObject respons=hotelogix.getJSONObject("response");
	 System.out.println(respons);
	 JSONObject status= respons.getJSONObject("status");
	 Assert.assertEquals(status.getString("message"), "success");
	 JSONArray bookingarr= respons.getJSONArray("bookings");
	 for(int i=0;i<=bookingarr.length();i++){
		 JSONObject bkgObj=bookingarr.getJSONObject(i);
		 String webID=bkgObj.get("webRefId").toString();
		 if(webID.equals(webRefID)){
			 editBkngID=bkgObj.get("id").toString();
			 String id=bkgObj.get("id").toString();
			 Assert.assertEquals(id.equals(bookingID), true,"Booking ID didn't matched");
			 JSONArray gueststayarr= bkgObj.getJSONArray("guestStays");
			 int count=gueststayarr.length();
			 for(int j=0;j<=count;j++){
				 JSONObject gueststayObj=gueststayarr.getJSONObject(j);
				 String detail=gueststayObj.get("guestDetails").toString();	
				 System.out.println(detail);
				 if(detail.equals("[]")){
					 newGuestID=gueststayObj.get("id").toString();
					 break;
				 }
				 
			 }	 
		 }	
		 break;
	 }
		}catch(Throwable e){
			throw new APIException("fn_EditBookingAPI has failed", e);
		}
	 
	}
	
	
	
	
}
