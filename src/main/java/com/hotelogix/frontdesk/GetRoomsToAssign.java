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

public class GetRoomsToAssign {

	public static String roomTypeID;
	public static String roomID;
	
	public void fn_GetRoomsToAssignAPI(String URL,String loginaccesskey,String editBkngID,String frmDate,String toDate) throws JSONException, UnirestException, APIException{
		
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
                		+ "\"method\": \"getroomstoassign\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"bookings\": ["
                		+ "{"
                		+ "\"id\": \""+editBkngID+"\","
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
	 JSONArray availableRoomsArr=respons.getJSONArray("availableRooms");	
	 JSONObject roomsObj= availableRoomsArr.getJSONObject(0);
	 String bkgId=roomsObj.get("bookingId").toString();
	 Assert.assertEquals(bkgId.equals(editBkngID), true,"Edit booking ID didn't matched");
	 roomTypeID=roomsObj.get("roomTypeId").toString();
	 JSONArray rArr=roomsObj.getJSONArray("rooms");
	 JSONObject rObj= rArr.getJSONObject(0);
	 roomID=rObj.get("id").toString();
		}catch(Throwable e){
			throw new APIException("fn_GetRoomsToAssignAPI has failed", e);
		}
	 
	}
	
	
}
