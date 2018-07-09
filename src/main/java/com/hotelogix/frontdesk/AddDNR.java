package com.hotelogix.frontdesk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.google.gson.JsonObject;
import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class AddDNR {

	
	public static String dnrID;
	public static String roomid;
	public static String rtID;
	
	public void fn_AddDNRAPI(String URL,String loginAccessKey,String roomID,String frmDate,String roomtypeID) throws Throwable{
		
		String toDate=APIUtils.GA().fn_setFrmToDate(frmDate,1);

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
		        		+ "\"method\": \"adddnrs\","
		        		+ "\"key\": \""+loginAccessKey+"\","
		        		+ "\"data\": {"
		        		+ "\"rooms\": ["
		        		+ "{"
		        		+ "\"id\": \""+roomID+"\","
		        		+ "\"fromDate\": \""+frmDate+"\","
		        		+ "\"toDate\": \""+toDate+"\","
		        		+ "\"comment\": \"TestAutomation DNR Comment\""
		        		+ "}"
		        		+ "]}}}}")
		        .asJson();		
			JsonNode responseJSONString = response.getBody();
			Assert.assertEquals(response.getCode(), 200);
			JSONObject jobj=responseJSONString.getObject();
			JSONObject getSth =jobj.getJSONObject("hotelogix");
		    JSONObject resp=getSth.getJSONObject("response");
		    
		    System.out.println(resp);
		    JSONObject status= resp.getJSONObject("status");
		    String msg=status.get("message").toString();
		    Assert.assertEquals(msg.equals("success"), true);
		    JSONArray dnrarr= resp.getJSONArray("dnrs");
		    JSONObject dnrObj= dnrarr.getJSONObject(0);
		    dnrID=dnrObj.get("id").toString();
		    roomid=dnrObj.get("roomId").toString(); 
		    Assert.assertEquals(roomid.equals(roomID), true,"Unassigned room id didn't matched.");
		    rtID=dnrObj.get("roomTypeId").toString();
		    Assert.assertEquals(rtID.equals(roomtypeID), true,"Room Type id didn't matched.");
		    JSONObject updtStatusObj= dnrObj.getJSONObject("updateStatus");
		    String statusMsg=updtStatusObj.get("message").toString();
		    Assert.assertEquals(statusMsg.equals("success"), true);
		}catch(Throwable e){
			throw new APIException("fn_AddDNRAPI has failed", e);
		}
		    
		    
	}
	
	
	
}
