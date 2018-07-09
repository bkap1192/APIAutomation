package com.hotelogix.admin;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class EditUser {

	
	public static String EditFname;
	
	
	public void fn_EditUser(String URL,String wsauthKey,String counterName,String userTypeTitle,String hotelID,String userid,String empID,String acativatestatus) throws APIException{
		EditFname=APIUtils.GA().generateRandomString();
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
				        		+ "\"method\": \"edituser\","
				        		+ "\"key\": \""+wsauthKey+"\","
				        		+ "\"data\": {"
				        		+ "\"userTypeTitle\": \""+userTypeTitle+"\","
				        		+ "\"accessAllReport\":true,"
				        		+ "\"employeeId\":\""+empID+"\","
				        		+ "\"counters\": ["
				        		+ "{"
				        		+ "\"name\": \""+counterName+"\""
				        		+ "}"
				        		+ "],"
				        		+ "\"fName\": \""+EditFname+"\","
				        		+ "\"status\": \""+acativatestatus+"\","
				        		+ "\"hotels\": ["
				        		+ "{"
				        		+ "\"id\": \""+hotelID+"\","
				        		+ "\"userIds\": ["
				        		+ "\""+userid+"\""
				        		+ "]"
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
		}catch(Throwable e){
			throw new APIException("EditUser has failed", e);
		}
	}
	
	
	
}
