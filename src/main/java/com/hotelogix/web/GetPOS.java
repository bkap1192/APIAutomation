package com.hotelogix.web;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetPOS {

	
	public static String POSId;
	
	public void fn_GetPOS(String url,String wsauth,String hotelid) throws APIException{
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
		                		+ " \"method\": \"getpos\","
		                		+ "\"key\": \""+wsauth+"\","
		                		+ "\"data\": {"
		                		+ "\"hotels\": ["
		                		+ "{"
		                		+ "\"id\": \""+hotelid+"\""
		                	    + "}]}}}}")
		                     .asJson();
			 
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 JSONObject hotels=respons.getJSONArray("hotels").getJSONObject(0);
			 JSONObject pos=hotels.getJSONArray("pos").getJSONObject(0);
			 POSId=pos.get("id").toString();
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "success");
	}catch(Throwable e){
		throw new APIException("GetPOS api is failed.", e);
	}
	}
	
}
