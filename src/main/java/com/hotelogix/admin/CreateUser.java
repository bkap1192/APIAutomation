package com.hotelogix.admin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class CreateUser {

	
	
public static String fname;
public static String EmpNo;	
	
	public void fn_CreateUserAPI(String URL,String wsauthKey,String counterName,String userTypeTitle,String pwd,String hotelID) throws Throwable{
		EmpNo="EMP"+APIUtils.GA().generateRandomnum();
		fname=APIUtils.GA().generateRandomString();
		String lname=APIUtils.GA().generateRandomString();
		String emailID=APIUtils.GA().generateRandomString()+"@stayezee.com";
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
		        		+ "\"method\": \"createuser\","
		        		+ "\"key\": \""+wsauthKey+"\","
		        		+ "\"data\": {"
		        		+ "\"userTypeTitle\": \""+userTypeTitle+"\","
		        		+ "\"accessAllReport\":true,"
		        		+ "\"counters\" : [{\"name\":\""+counterName+"\"}],"
		        		+ "\"employeeId\": \""+EmpNo+"\","
		        		+ "\"fName\": \""+fname+"\","
		        		+ "\"lName\": \""+lname+"\","
		        		+ "\"email\": \""+emailID+"\","
		        		+ "\"phone\": \"89789789789\","
		        		+ "\"mobile\": \"89789789789\","
		        		+ "\"country\": \"IN\","
		        		+ "\"state\": \"UP\","
		        		+ "\"city\": \"Noida\","
		        		+ "\"zip\": \"201301\","
		        		+ "\"gender\": \"F\","
		        		+ "\"designation\": \"Employee\","
		        		+ "\"password\": \""+pwd+"\","
		        		+ "\"hotels\":"
		        		+ "["
		        		+ "{"
		        		+ "\"id\": \""+hotelID+"\""
		        		+ "}]}}}}")
		        .asJson();
		JsonNode responseJSONString = response.getBody();
		Assert.assertEquals(response.getCode(), 200);
		String str=responseJSONString.toString();
		JSONObject jobj=new JSONObject(str);
		JSONObject hotelogix=jobj.getJSONObject("hotelogix");
		JSONObject resp=hotelogix.getJSONObject("response");
		JSONObject status=resp.getJSONObject("status");
		String msg=status.get("message").toString();	
	    Assert.assertEquals(msg.equals("success"), true);
		JSONArray hotelarr= resp.getJSONArray("hotels");
		for(int i=0;i<=hotelarr.length();i++){
			JSONObject hotelObj= hotelarr.getJSONObject(i);
			String ID=hotelObj.get("id").toString();
			if(ID.equals(hotelID)){
				JSONObject statusObj= hotelObj.getJSONObject("updateStatus");
				String msg1=statusObj.get("message").toString();
				Assert.assertEquals(msg1.equals("success"), true);
				break;
			}						
		}	
	}catch(Throwable e){
		throw e;
	}
	}
	
	
public void fn_CreateUserAPI(String URL,String wsauthKey,String counterName,String userTypeTitle,String email,String pwd,String hotelID) throws Throwable{
		
		String empNo="EMP"+APIUtils.generateRandomnum();
		fname=APIUtils.generateRandomString();
		String lname=APIUtils.generateRandomString();
		String emailID=APIUtils.generateRandomString()+"@stayezee.com";
		
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
		        		+ "\"method\": \"createuser\","
		        		+ "\"key\": \""+wsauthKey+"\","
		        		+ "\"data\": {"
		        		+ "\"userTypeTitle\": \""+userTypeTitle+"\","
		        		+ "\"accessAllReport\":true,"
		        		+ "\"counters\" : [{\"name\":\""+counterName+"\"}],"
		        		+ "\"employeeId\": \""+empNo+"\","
		        		+ "\"fName\": \""+fname+"\","
		        		+ "\"lName\": \""+lname+"\","
		        		+ "\"email\": \""+emailID+"\","
		        		+ "\"phone\": \"89789789789\","
		        		+ "\"mobile\": \"89789789789\","
		        		+ "\"country\": \"IN\","
		        		+ "\"state\": \"UP\","
		        		+ "\"city\": \"Noida\","
		        		+ "\"zip\": \"201301\","
		        		+ "\"gender\": \"F\","
		        		+ "\"designation\": \"Employee\","
		        		+ "\"password\": \""+pwd+"\","
		        		+ "\"hotels\":"
		        		+ "["
		        		+ "{"
		        		+ "\"id\": \""+hotelID+"\""
		        		+ "}]}}}}")
		        .asJson();
		JsonNode responseJSONString = response.getBody();
		Assert.assertEquals(response.getCode(), 200);
		String str=responseJSONString.toString();
		JSONObject jobj=new JSONObject(str);
		JSONObject hotelogix=jobj.getJSONObject("hotelogix");
		JSONObject resp=hotelogix.getJSONObject("response");
		JSONObject status=resp.getJSONObject("status");
		String msg=status.get("message").toString();	
	    Assert.assertEquals(msg.equals("success"), true);
		JSONArray hotelarr= resp.getJSONArray("hotels");
		for(int i=0;i<=hotelarr.length();i++){
			JSONObject hotelObj= hotelarr.getJSONObject(i);
			String ID=hotelObj.get("id").toString();
			if(ID.equals(hotelID)){
				JSONObject statusObj= hotelObj.getJSONObject("updateStatus");
				String msg1=statusObj.get("message").toString();
				Assert.assertEquals(msg1.equals("success"), true);
				break;
			}						
		}	
	}catch(Throwable e){
		throw e;
	}
	}
	
	
}
