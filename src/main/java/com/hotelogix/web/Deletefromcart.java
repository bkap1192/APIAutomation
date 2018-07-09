package com.hotelogix.web;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Deletefromcart {

	
	public void fn_DeletefromCart(String url,String wsauth,String bookingid) throws APIException{
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
		                		+ " \"method\": \"deletefromcart\","
		                		+ "\"key\": \""+wsauth+"\","
		                		+ "\"languagecode\": \"en\","
		                		+ "\"data\": {"
		                		+ "\"itemId\": {"
		                		+ "\"value\": \""+bookingid+"\""
		                		+"}}}}}")
		                     .asJson();
			 
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 String hotel=respons.get("hotels").toString();
			 Assert.assertEquals(hotel, "[]"," After delete cart hotel data is not null -: ");
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
	}catch(Throwable e){
		throw new APIException("Deletefromcart cart api is failed.", e);
	}
	}
	
	
}
