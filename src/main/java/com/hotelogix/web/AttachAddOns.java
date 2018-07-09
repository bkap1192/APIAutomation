package com.hotelogix.web;


import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class AttachAddOns {

	
	public void fn_AttachAddOns(String url, String wsauth,String bookingid,String addonid) throws APIException{
		  try{
				 HttpResponse<JsonNode> response = Unirest.post(url)
			                .header("content-type", "application/json")
			                .header("x-ig-sg", "D_gg%fkl85_j")
			                .header("cache-control", "no-cache")
			                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
			                .body("{ \"hotelogix\": {"
			                		+ "\"version\": \"1.0\","
			                		+ "\"datetime\": \"2015-09-16T11:01:29\","
			                		+ " \"request\": {"
			                		+ " \"method\": \"attachaddons\","
			                		+ "\"key\": \""+wsauth+"\","
			                		+ "\"data\": {"
			                		+ "\"itemId\": "
			                		+ "{"
			                		+ "\"value\": \""+bookingid+"\""
			                		+ "},"
			                		+"\"addons\":["
			                		+ "{"
			                		+ "\"id\": \""+addonid+"\","
			                	    + "\"quantity\": \"1\","
			                	    + "\"customPrice\": \"17\","
			                	    + "\"customPriceType\": \"UNIT\""
			                	    + " }]}}}}")
			                     .asJson();
				 JsonNode responseJSONString=response.getBody();
				 Assert.assertEquals(response.getCode(), 200);
				 JSONObject jobj=responseJSONString.getObject();
				 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
				 JSONObject respons=hotelogix.getJSONObject("response");
				 String hotel=respons.getString("hotels");
				 JSONObject hotels=APIUtils.GA().GetJsonObject(hotel);
				 String bookings=hotels.get("bookings").toString();
				 JSONObject jsonbooking=APIUtils.GA().GetJsonObject(bookings);
				 String rates=jsonbooking.get("rates").toString();
				 JSONObject jsonrates=APIUtils.GA().GetJsonObject(rates);
				 String jsonaddons=jsonrates.get("addons").toString();
				 JSONObject jsonaddon=APIUtils.GA().GetJsonObject(jsonaddons);
				 String addonsid=jsonaddon.get("id").toString();
				 Assert.assertEquals(addonsid, addonid," AddonID from getaddon api doesnot match with response of attachaddon api -: ");
				 JSONObject status= respons.getJSONObject("status");
				 Assert.assertEquals(status.getString("message"), "Successfull!");
		  }catch(Throwable e){
			  throw new APIException("fn_AttachAddOns api is failed", e);	
		  }
	}
	
	
	
	
	
}
