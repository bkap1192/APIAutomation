package com.hotelogix.frontdesk;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class LoginFD {

	public String fn_loginFD(String URL,String hotelId,String counter,String email,String consumerKey) throws Throwable{
		
		String accessKeyFD=null;
		try{
		HttpResponse<JsonNode> response = Unirest.post(URL)
		        .header("content-type", "application/json")
		        .header("x-ig-sg", "D_gg%fkl85_j")
		        .header("cache-control", "no-cache")
		        .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		        .body("{\r\n  \"hotelogix\": {\r\n   \"version\": \"1.0\",\r\n    \"datetime\": \"2012-01-16T10:10:15\",\r\n    \"request\": {\r\n      \"method\": \"login\",\r\n      \"key\": \""+consumerKey+"\",\r\n      \"data\": {\r\n         \"hotelId\":"+hotelId+",\r\n         \"counterId\":\""+counter+"\",\r\n         \"email\":\""+email+"\",\r\n         \"forceOpenCouner\":true\r\n      }\r\n    }\r\n  }\r\n }")
		        .asJson();
		
			JsonNode responseJSONString = response.getBody();
			Assert.assertEquals(response.getCode(), 200);
			String str=responseJSONString.toString();
			JSONObject jobj1=new JSONObject(str);
			JSONObject getSth =jobj1.getJSONObject("hotelogix");
			JSONObject resp = getSth.getJSONObject("response");
			JSONObject status=resp.getJSONObject("status");
			String msg=status.get("message").toString();
			Assert.assertEquals(msg.equals("success"), true);
			accessKeyFD=resp.get("accesskey").toString();
		}catch(Throwable e){
			throw new APIException("fn_loginFD has failed", e);
		}
		return accessKeyFD;
	}
	
}
