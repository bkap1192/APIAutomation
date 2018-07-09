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
import com.steadystate.css.util.ThrowCssExceptionErrorHandler;

public class DeleteDNR {

	public void fn_deleteDNRAPI(String URL,String loginaccessKey,String dnrID) throws Throwable{
		
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
		        		+ "\"method\": \"deletednrs\","
		        		+ "\"key\": \""+loginaccessKey+"\","
		        		+ "\"data\": {"
		        		+ "\"dnrs\": ["
		        		+ "{"
		        		+ "\"id\": \""+dnrID+"\""
		        		+ "}]}}}}")		        		
		        .asJson();
		
			JsonNode responseJSONString = response.getBody();
			Assert.assertEquals(response.getCode(), 200);
			JSONObject jobj=responseJSONString.getObject();
			JSONObject getSth =jobj.getJSONObject("hotelogix");
		    JSONObject resp=getSth.getJSONObject("response");
		    JSONObject status= resp.getJSONObject("status");
		    String msg=status.get("message").toString();
		    Assert.assertEquals(msg.equals("success"), true);
		    JSONArray dnrarr= resp.getJSONArray("dnrs");
		    JSONObject dnrObj= dnrarr.getJSONObject(0);
		    JSONObject dnrStatus= dnrObj.getJSONObject("updateStatus");
		    String statusMsg=dnrStatus.get("message").toString();
		    Assert.assertEquals(statusMsg.equals("success"), true);
		}catch(Throwable e){
			throw new APIException("fn_deleteDNRAPI has failed", e);
		}
	}
	
}
