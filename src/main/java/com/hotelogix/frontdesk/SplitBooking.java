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

public class SplitBooking {

	
	public void fn_SplitBookingsAPI(String URL,String loginaccesskey,String editbookingID,String roomTypeID,String frmDate,String toDate,String webRefID) throws JSONException, UnirestException, APIException{
		try{
		String editBkngID=null;
		String isRoomSplitted=null;
		
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
                		+ "\"method\": \"splitbooking\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"bookings\": ["
                		+ "{"
                		+ "\"id\": \""+editbookingID+"\","
                		+ "\"roomTypeId\": \""+roomTypeID+"\","
                		+ "\"fromDate\": \""+frmDate+"\","
                		+ "\"toDate\": \""+toDate+"\""
                		+ "}]}}}}")
                     .asJson();
	 JsonNode responseJSONString=response.getBody();
	 Assert.assertEquals(response.getCode(), 200);
	 String str=responseJSONString.toString();
	 JSONObject jobj=new JSONObject(str);
	 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
	 JSONObject respons=hotelogix.getJSONObject("response");
	 JSONObject status= respons.getJSONObject("status");
	 Assert.assertEquals(status.getString("message"), "success");
	 JSONArray bkngarr=respons.getJSONArray("bookings");
	 for(int i=0;i<=bkngarr.length();i++){
		 JSONObject bkngObj= bkngarr.getJSONObject(i);
		 String webID=bkngObj.get("webRefId").toString();
		 if(webID.equals(webRefID)){
			 editBkngID=bkngObj.get("id").toString();
			 isRoomSplitted=bkngObj.get("isRoomSplitted").toString();
			 break;
		 }		 
	 }
	 Assert.assertEquals(editBkngID.equals(editbookingID), true,"Edit Booking ID didn't matched.");
     Assert.assertEquals(isRoomSplitted.equals("true"), true,"Splitting of reservation failed");
		}catch(Throwable e){
			throw  new APIException("fn_SplitBookingsAPI has failed", e);
		}
	 
 	}
	
}
