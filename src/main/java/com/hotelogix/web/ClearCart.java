package com.hotelogix.web;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class ClearCart {

	
	
	
	
	
	public void fn_ClearCart(String url,String wsauth) throws APIException{
			try{
				 HttpResponse<JsonNode> response = Unirest.post(url)
			                .header("content-type", "application/json")
			                .header("x-ig-sg", "D_gg%fkl85_j")
			                .header("cache-control", "no-cache")
			                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
			                .body("{ \"hotelogix\": {"
			                		+ "\"version\": \"1.0\","
			                		+ "\"datetime\": \"2015-09-16T11:01:29\","
			                		+ " \"request\": {"
			                		+ " \"method\": \"clearcart\","
			                		+ "\"key\": \""+wsauth+"\","
			                		+ "\"languagecode\": \"en\""
			                		+"}}}")
			                     .asJson();
				 
				 JsonNode responseJSONString=response.getBody();
				 Assert.assertEquals(response.getCode(), 200);
				 JSONObject jobj=responseJSONString.getObject();
				 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
				 JSONObject respons=hotelogix.getJSONObject("response");
				 JSONObject status= respons.getJSONObject("status");
				 Assert.assertEquals(status.getString("message"), "SUCCESS");
		}catch(Throwable e){
			throw new APIException("Clear cart api is failed.", e);
		}
	}
	
	
	
	
	
	
	
}
