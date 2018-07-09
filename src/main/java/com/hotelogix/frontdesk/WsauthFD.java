package com.hotelogix.frontdesk;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class WsauthFD {

	public String fn_GetWsauthKey(String url,String CRSKey) throws Throwable{
		String WsauthKey=null;
		try{
		 HttpResponse<JsonNode> response = Unirest.post(url)
		        .header("content-type", "application/json")
		        .header("x-ig-sg", "D_gg%fkl85_j")
		        .header("cache-control", "no-cache")
		        .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		        .body("{ \"hotelogix\": {"
		        		+ "\"version\": \"1.0\","
		        		+ "\"datetime\": \"2012-01-16T10:10:15\","
		        		+ "\"request\": {"
		        		+ "\"method\": \"wsauth\","
		        		+ "\"key\": \""+CRSKey+"\""
		        		+ "}"
		        		+ "}"
		        		+ "}")
		        .asJson();
		
			JsonNode responseJSONString = response.getBody();
			Assert.assertEquals(response.getCode(), 200);
			String str=responseJSONString.toString();
			JSONObject jobj=new JSONObject(str);
			JSONObject getSth =jobj.getJSONObject("hotelogix");
		    JSONObject resp=getSth.getJSONObject("response");
            JSONObject status=resp.getJSONObject("status");  
			String msg=status.getString("message");
            Assert.assertEquals(msg.equals("success"), true);
			Object level = getSth.get("response");			
			JSONObject jobj1=new JSONObject(level.toString());
			WsauthKey=jobj1.getString("accesskey");
			
		}catch(Throwable e){
			throw new APIException("fn_GetWsauthKey has failed", e);
		}
		return WsauthKey;
		}
		
	}
	
	

