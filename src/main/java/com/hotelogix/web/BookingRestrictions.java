package com.hotelogix.web;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class BookingRestrictions {

	
	
	public void fn_BookingRestrictions(String url,String wsauth,String fromdate,String todate) throws APIException{
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
		                		+ " \"method\": \"bookingrestrictions\","
		                		+ "\"key\": \""+wsauth+"\","
		                		+ "\"languagecode\": \"en\","
		                		+ "\"data\": {"
		                		+ "\"daterange\": {"
		                		+ "\"startdate\": \""+fromdate+"\","
		                		+ "\"enddate\": \""+todate+"\""
		                		+ "}}}}}")
		                     .asJson();
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix=jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "Success");
		}catch(Throwable e){
			throw new APIException("LoadcartAPI api is failed", e);
		}
	}
	
	
	
	
	
}
