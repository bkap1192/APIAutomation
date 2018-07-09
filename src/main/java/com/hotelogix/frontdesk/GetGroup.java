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

public class GetGroup {

	public static String grpID;
	
	
	public void fn_GetGroupAPI(String URL,String loginaccesskey,String bookingCode,String orderID) throws UnirestException, JSONException, APIException{
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
                		+ "\"method\": \"getgroup\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"code\": \""+bookingCode+"\""
                		+ "}}}}")
                     .asJson();
	 JsonNode responseJSONString=response.getBody();
	 Assert.assertEquals(response.getCode(), 200);
	 String str=responseJSONString.toString();
	 JSONObject jobj=new JSONObject(str);
	 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
	 JSONObject respons=hotelogix.getJSONObject("response");
	 JSONObject status= respons.getJSONObject("status");
	 Assert.assertEquals(status.getString("message"), "success");
	 JSONArray bkgarr= respons.getJSONArray("bookings");
	 JSONObject  bkgObj=bkgarr.getJSONObject(0);
	 JSONObject grpObj=bkgObj.getJSONObject("group");
	 grpID=grpObj.get("id").toString();
	 String crsID=grpObj.get("webRefId").toString();
	 Assert.assertEquals(crsID.equals(orderID), true,"Order ID didn't matched");
		}catch(Throwable e){
			throw new APIException("fn_GetGroupAPI has failed", e);
		}
	 
	 
	 
	}
	
	
	
	
	
	
}
