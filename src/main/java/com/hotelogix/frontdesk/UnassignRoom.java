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

public class UnassignRoom {

	
	public void fn_UnassignRoom(String URL,String loginaccesskey,String editBookingID) throws JSONException, UnirestException, APIException{
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
                		+ "\"method\": \"unassignroom\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"bookings\": ["
                		+ "{"
                		+ "\"id\": \""+editBookingID+"\""
                		+ "}]}}}}")
                     .asJson();
	 JsonNode responseJSONString=response.getBody();
	 Assert.assertEquals(response.getCode(), 200);
	 String str=responseJSONString.toString();
	 JSONObject jobj1=new JSONObject(str);
	 JSONObject hotelogix= jobj1.getJSONObject("hotelogix");
	 JSONObject respons=hotelogix.getJSONObject("response");
	 JSONObject status= respons.getJSONObject("status");
	 Assert.assertEquals(status.getString("message"), "success");
	 JSONArray bkgarr=respons.getJSONArray("bookings");
     int count=respons.getJSONArray("bookings").length();
	 for(int i=0;i<=count;i++){
		String id= bkgarr.getJSONObject(i).get("id").toString();
		if(id.equals(editBookingID)){
			JSONArray roomarr=bkgarr.getJSONObject(i).getJSONArray("roomStays");
			JSONObject roomObj= roomarr.getJSONObject(0);
			String roomid=roomObj.get("roomId").toString();
			Assert.assertEquals(roomid.equals("0"), true,"Room has not been unassigned");
			break;
		}
	 }
		}catch(Throwable e){
			throw new APIException("fn_UnassignRoom has failed", e);
		}
	}
	
}
