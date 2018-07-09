package com.hotelogix.frontdesk;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CommitEditBooking {

	public void fn_CommitEditBookingAPI(String URL,String loginaccesskey,String reservType,String editBookingID) throws JSONException, UnirestException, APIException{
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
                		+ "\"method\": \"commiteditbooking\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"type\": \""+reservType+"\","
                		+ "\"id\": \""+editBookingID+"\""
                		+ "}}}}")
                     .asJson();
	 JsonNode responseJSONString=response.getBody();
	 Assert.assertEquals(response.getCode(), 200);
	 String str=responseJSONString.toString();
	 JSONObject jobj=new JSONObject(str);
	 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
	 JSONObject respons=hotelogix.getJSONObject("response");
	 JSONObject status= respons.getJSONObject("status");
	 String msg=status.get("message").toString();
	 Assert.assertEquals(msg.equals("success"),true);
		}catch(Throwable e){
			throw new APIException("fn_CommitEditBookingAPI has failed", e);
		}
	 
	 
	}
	
	
	
}
