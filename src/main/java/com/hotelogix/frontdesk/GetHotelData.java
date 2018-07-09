package com.hotelogix.frontdesk;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.steadystate.css.util.ThrowCssExceptionErrorHandler;

public class GetHotelData {

	
	public String fn_GetHotelData(String URL,String loginAccessKey) throws Throwable{
		String date=null;
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
		        		+ "\"method\": \"gethoteldata\","
		        		+ "\"key\": \""+loginAccessKey+"\","
		        		+ "\"data\": {"
		        		+ "\"customSetting\":true,"
		        		+ "\"roomType\":true,"
		        		+ "\"salutation\":true,"
		        		+ "\"identityType\":true,"
		        		+ "\"rate\":true"
		        		+ "}}}}")
		        .asJson();		
		JsonNode responseJSONString = response.getBody();
		Assert.assertEquals(response.getCode(), 200);
		String str=responseJSONString.toString();
		JSONObject jobj=new JSONObject(str);
		JSONObject getSth =jobj.getJSONObject("hotelogix");
		JSONObject resp = getSth.getJSONObject("response");
        JSONObject status=resp.getJSONObject("status");
        String msg=status.get("message").toString();
        Assert.assertEquals(msg.equals("success"), true);
        date=resp.get("currentNightAuditDate").toString();
		}catch(Throwable e){
			throw new APIException("fn_GetHotelData", e);
		}
        return date;
        
	}
	
	
}
