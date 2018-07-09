package com.hotelogix.admin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetUserTypes {

	
	public static String userTypeTitle;
	public static String userTypeID;
	
	public void fn_GetUserTypesAPI(String URL,String wsauthKey,String hotelID) throws Throwable{
		try{
		HttpResponse<JsonNode> response = Unirest.post(URL)
		        .header("content-type", "application/json")
		        .header("x-ig-sg", "D_gg%fkl85_j")
		        .header("cache-control", "no-cache")
		        .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		        .body("{"
		        		+ "\"hotelogix\": {"
		        		+ "\"version\": \"1.0\","
		        		+ "\"datetime\": \"2012-01-16T10:11:15\","
		        		+ "\"request\": {"
		        		+ "\"method\": \"getusertypes\","
		        		+ "\"key\": \""+wsauthKey+"\","
		        		+ "\"data\": {"
		        		+ "\"hotels\":["
		        		+ "{"
		        		+ "\"id\":"+hotelID+""
		        		+ "}]}}}}")
		        .asJson();
		JsonNode responseJSONString = response.getBody();
		Assert.assertEquals(response.getCode(), 200);
		JSONObject jobj=responseJSONString.getObject();
		JSONObject getSth =jobj.getJSONObject("hotelogix");
	    JSONObject resp=getSth.getJSONObject("response");
        JSONObject status=resp.getJSONObject("status");
        String str=status.get("message").toString();
        Assert.assertEquals(str.equals("success"), true);
        JSONArray hotelarr= resp.getJSONArray("hotels");
        JSONObject hotelObj= hotelarr.getJSONObject(0);
        JSONObject userTypeObj=hotelObj.getJSONArray("userTypes").getJSONObject(0);
        userTypeTitle=userTypeObj.get("title").toString();
        userTypeID=userTypeObj.get("id").toString();
		}catch(Throwable e){
			throw e;
		}
        
	}
}
