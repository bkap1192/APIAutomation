package com.hotelogix.web;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class LoadCart {

	
	
	public static String BookingID;
	public static String HotelID;
	static String InfantID;
	public static String GuestStaysID;
	public static String DepositAmount;
	
	
public void LoadcartAPI(String url,String wsauth) throws APIException{
		try{
			 HttpResponse<JsonNode> response = Unirest.post(url)
		                .header("content-type", "application/json")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("{ \"hotelogix\": {"
		                		+ "\"version\": \"1.0\","
		                		+ "\"datetime\": \"2012-01-16T10:10:15\","
		                		+ " \"request\": {"
		                		+ " \"method\": \"loadcart\","
		                		+ "\"key\": \""+wsauth+"\","
		                		+ "\"languagecode\": \"en\""
		                		+ "}}}")
		                     .asJson();
			 
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 String hotel=respons.get("hotels").toString();
			 JSONObject jsonhote=APIUtils.GA().GetJsonObject(hotel);
			 String booking=jsonhote.get("bookings").toString();
			 JSONObject jsonbooking=APIUtils.GA().GetJsonObject(booking);
			 JSONObject bookingpolicy=jsonbooking.getJSONObject("bookingpolicy");
			 DepositAmount=bookingpolicy.get("depositamount").toString();
			 BookingID=jsonbooking.get("id").toString();
			 HotelID=jsonbooking.get("hotelid").toString();
			 String gueststay=jsonbooking.get("gueststays").toString();
			 JSONObject gueststayjson=APIUtils.GA().GetJsonObject(gueststay);
			 GuestStaysID=gueststayjson.get("id").toString();
			 String infant=jsonbooking.get("infantstays").toString();
			 JSONObject infantjson=APIUtils.GA().GetJsonObject(infant);
			 InfantID=infantjson.get("id").toString();
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
		}catch(Throwable e){
			throw new APIException("LoadcartAPI api is failed", e);
		}
	}
	




public void LoadcartAPIForrateidforaddtocart(String url,String wsauth) throws APIException{
	try{
		 HttpResponse<JsonNode> response = Unirest.post(url)
	                .header("content-type", "application/json")
	                .header("x-ig-sg", "D_gg%fkl85_j")
	                .header("cache-control", "no-cache")
	                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
	                .body("{ \"hotelogix\": {"
	                		+ "\"version\": \"1.0\","
	                		+ "\"datetime\": \"2012-01-16T10:10:15\","
	                		+ " \"request\": {"
	                		+ " \"method\": \"loadcart\","
	                		+ "\"key\": \""+wsauth+"\","
	                		+ "\"languagecode\": \"en\""
	                		+ "}}}")
	                     .asJson();
		 
		 JsonNode responseJSONString=response.getBody();
		 Assert.assertEquals(response.getCode(), 200);
		 JSONObject jobj=responseJSONString.getObject();;
		 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
		 JSONObject respons=hotelogix.getJSONObject("response");
		 String hotel=respons.get("hotels").toString();
		 JSONObject jsonhote=APIUtils.GA().GetJsonObject(hotel);
		 String booking=jsonhote.get("bookings").toString();
		 JSONObject jsonbooking=APIUtils.GA().GetJsonObject(booking);
		 JSONObject bookingpolicy=jsonbooking.getJSONObject("bookingpolicy");
		 DepositAmount=bookingpolicy.get("depositamount").toString();
		 BookingID=jsonbooking.get("id").toString();
		 HotelID=jsonbooking.get("hotelid").toString();
		 String gueststay=jsonbooking.get("gueststays").toString();
		 JSONObject gueststayjson=APIUtils.GA().GetJsonObject(gueststay);
		 GuestStaysID=gueststayjson.get("id").toString();
		 JSONObject status= respons.getJSONObject("status");
		 Assert.assertEquals(status.getString("message"), "SUCCESS");
	}catch(Throwable e){
		throw new APIException("LoadcartAPI api is failed", e);
	}
}
	
	public void fn_LordCartToValidateClearCartAPI(String url,String wsauth) throws APIException{
		try{
			 HttpResponse<JsonNode> response = Unirest.post(url)
		                .header("content-type", "application/json")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("{ \"hotelogix\": {"
		                		+ "\"version\": \"1.0\","
		                		+ "\"datetime\": \"2012-01-16T10:10:15\","
		                		+ " \"request\": {"
		                		+ " \"method\": \"loadcart\","
		                		+ "\"key\": \""+wsauth+"\","
		                		+ "\"languagecode\": \"en\""
		                		+ "}}}")
		                     .asJson();
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();;
			 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 String hotel=respons.get("hotels").toString();
			 Assert.assertEquals(hotel, "[]","After clear cart the load cart api is failed -: ");
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
		}catch(Throwable e){
			throw new APIException("", e);
		}
	}
	
	
	public void fn_LoadCartFor2Rooms() throws APIException{
		try{ 
			
		}catch(Throwable e){
			throw new APIException("", e);
		}
	}
	
	
	
	
	
}
