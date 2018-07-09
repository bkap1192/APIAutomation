package com.hotelogix.admin;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class SaveEmailConfiguration {

	
	
	
	public void fn_SaveEmailConfiguration(String url,String wsauthkey,String hotelID) throws APIException{
		try{
			HttpResponse<JsonNode> response = Unirest.post(url)
			        .header("content-type", "application/json")
			        .header("x-ig-sg", "D_gg%fkl85_j")
			        .header("cache-control", "no-cache")
			        .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
			        .body("{"
			        		+ "\"hotelogix\": {"
			        		+ "\"version\": \"1.0\","
			        		+ "\"datetime\": \"2012-01-16T10:10:15\","
			        		+ "\"request\": {"
			        		+ "\"method\": \"saveemailconfiguration\","
			        		+ "\"key\": \""+wsauthkey+"\","
			        		+ "\"data\": {"
			        		+ "\"host\": \"smtp.gmail.com\","
			        		+ "\"auth\": true,"
			        		+ "\"port\": \"4655\","
			        		+ "\"username\": \"murli.jha@hotelogix.com\","
			        		+ "\"password\": \"xxxxxx\","
			        		+ "\"encrptionType\": \"ssl\","
			        		+ "\"hotels\": ["
			        		+ "{"
			        		+ "\"id\": \""+hotelID+"\""
			        		+ "}]}}}}")
			        .asJson();
				JsonNode responseJSONString = response.getBody();
				Assert.assertEquals(response.getCode(), 200);
				JSONObject jobj=responseJSONString.getObject();
				JSONObject hotelogix =jobj.getJSONObject("hotelogix");
				JSONObject respons = hotelogix.getJSONObject("response");
				JSONObject status= respons.getJSONObject("status");
				String msg=status.get("message").toString();
				Assert.assertEquals(msg.equals("success"), true);
		}catch(Throwable e){
			throw new APIException("SaveEmailConfiguration has failed.", e);
		}
	}
	
	
	
	
}
