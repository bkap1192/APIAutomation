package com.hotelogix.frontdesk;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MoveBooking {

	public void fn_MoveBookingAPI(String URL,String loginaccesskey,String bookingID,String rtID,String webRefID,String rID) throws UnirestException, JSONException, APIException{
		
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
                		+ "\"method\": \"movebooking\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"id\": \""+bookingID+"\","
                		+ "\"roomTypeId\": \""+rtID+"\","
                		+ "\"roomId\": \""+rID+"\""               		
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
	 JSONObject bkgObj=respons.getJSONObject("booking");
	 String webID=bkgObj.get("webRefId").toString();
	 Assert.assertEquals(webID.equals(webRefID), true,"WebRefId didn't matched");
	 JSONObject roomObj=bkgObj.getJSONArray("roomStays").getJSONObject(0);
	 String rType=roomObj.get("roomTypeId").toString();
	 Assert.assertEquals(rType.equals(rtID), true,"Room Type ID didn,t matched.");
		}catch(Throwable e){
			throw new APIException("fn_MoveBookingAPI has failed", e);
		}
	}
	
	
}
