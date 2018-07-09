package com.hotelogix.web;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class ModifyAmount {

	
	
	
	
	public void fn_ModifyAmount(String url,String wsauth,String hotelid,String bookingid,String changeblebookingprice) throws APIException{
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
		                		+ " \"method\": \"modifyamount\","
		                		+ "\"key\": \""+wsauth+"\","
		                		+ "\"languagecode\": \"en\","
		                		+ "\"data\": {"
		                		+ "\"hotels\": ["
		                		+ "{"
		                		+ "\"id\": \""+hotelid+"\","
		                		+ "\"reservations\": ["
		                		+ "{"
		                		+ "\"id\": \""+bookingid+"\","
		                		+ "\"base\": {"
		                		+ "\"amount\": \""+changeblebookingprice+"\","
		                		+ "\"inclusiveoftax\": \"0\""
		                		+ "}}]}]}}}}")
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
			 JSONObject rates=jsonbooking.getJSONArray("rates").getJSONObject(0);
			 String price=rates.get("price").toString();
			 double priceindouble=Double.parseDouble(price);
			 long priceinlong=Math.round(priceindouble);
			 String priceinstring=new Long(priceinlong).toString();
			 Assert.assertEquals(priceinstring, changeblebookingprice);
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
		}catch(Throwable e){
			throw new APIException("LoadcartAPI api is failed", e);
		}
	}
	}
	
	
	
