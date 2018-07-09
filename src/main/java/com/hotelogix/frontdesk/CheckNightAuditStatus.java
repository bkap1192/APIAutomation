package com.hotelogix.frontdesk;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CheckNightAuditStatus {

	
	public String fn_CheckNAStatus(String URL,String loginAccessKey,String expResult) throws Throwable{
		String currentDate=null;
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
		        		+ "\"method\": \"checknightauditstatus\","
		        		+ "\"key\": \""+loginAccessKey+"\""
		        		+ "}}}")
		        .asJson();
		
		  JsonNode respJSONString=response.getBody();
		  Assert.assertEquals(response.getCode(), 200);
		  String str1=respJSONString.toString();
		//  System.out.println(str1);
		  JSONObject jobj=new JSONObject(str1);
		  JSONObject resp= jobj.getJSONObject("hotelogix");
		  JSONObject resp1=resp.getJSONObject("response");
		  JSONObject status=resp1.getJSONObject("status");
		  String msg=status.get("message").toString();
		  Assert.assertEquals(msg.contains(expResult), true,"Night Audit message didn't matched");
		  String[] arr=msg.split(" ");
		  currentDate=arr[10];
		}catch(Throwable e){
			throw  new APIException("fn_CheckNAStatus has failed", e);
		}
		  return currentDate;
		  
	}    
	
	
	
	
	
	
}
