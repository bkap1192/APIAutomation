package com.hotelogix.admin;


import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class Login {

	
	public static String LoginAccessKey;
	
	public void fn_Login(String url,String crskey,String hotelID,String counterid, String emailid,String password) throws APIException{
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
			        		+ "\"method\": \"login\","
			        		+ "\"key\": \""+crskey+"\","
			        		+ "\"data\": {"
			        		+ "\"hotelId\":"+hotelID+","
			        		+ "\"counterId\":\""+counterid+"\","
			        		+ "\"email\":\""+emailid+"\","
			        		+ "\"password\":\""+password+"\","
			        		+ "\"forceOpenCouner\":true,"
			        		+ "\"forceLogin\":true"
			        		+ "}}}}")
			        .asJson();
				JsonNode responseJSONString = response.getBody();
				Assert.assertEquals(response.getCode(), 200);
				JSONObject jobj=responseJSONString.getObject();
				JSONObject getSth =jobj.getJSONObject("hotelogix");
			    JSONObject resp=getSth.getJSONObject("response");
			    LoginAccessKey=resp.get("accesskey").toString();
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
			}catch(Throwable e){
				throw new APIException("Login api has failed", e);
			}
	       }
	
}
