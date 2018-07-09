package com.hotelogix.admin;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class CreateAgent {

	
	public static String AgentID;
	public static String AgentName;
	
	public void fn_CreateAgent(String url, String wsauthkey, String hotelid) throws APIException{
		AgentName=APIUtils.GA().generateRandomString();
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
			        		+ "\"method\": \"createagent\","
			        		+ "\"key\": \""+wsauthkey+"\","
			        		+ "\"data\": {"
			        		+ "\"businessName\": \""+AgentName+"\","
			        		+ "\"addresses\": {"
			        		+ "\"office\": {"
			        		+ "\"address\": \"abcd\","
			        		+ "\"country\": \"India\","
			        		+ "\"state\": \"delhi\","
			        		+ "\"city\": \"delhi\","
			        		+ "\"zip\": \"123\""
			        		+ "},"
			        		+ "\"billing\": {"
			        		+ "\"companyName\": \"billingCompany\","
			        		+ "\"address\": \"billingAddress\","
			        		+ "\"country\": \"India\","
			        		+ "\"state\": \"Punjab\","
			        		+ "\"city\": \"delhi\","
			        		+ "\"zip\": \"123456\""
			        		+ " }"
			        		+ "},"
			        		+ "\"contacts\": {"
			        		+ "\"personal\": {"
			        		+ "\"salutation\": \"Mr\","
			        		+ "\"fName\": \""+AgentName+"\","
			        		+ "\"lName\": \"Mishra\","
			        		+ "\"designation\": \"Manager\","
			        		+ "\"phoneNo\": \"Manager\","
			        		+ "\"phoneExtension\": \"101\","
			        		+ "\"faxNo\": \"101\","
			        		+ "\"email\": \""+AgentName+"@stayezee.com\","
			        		+ "\"mobileNo\": \"123\","
			        		+ "\"gender\": \"M\","
			        		+ "\"dob\": \"2016-03-01\","
			        		+ "\"website\": \"www.hotelogix.com\""
			        		+ "},"
			        		+ "\"billing\": {"
			        		+ "\"salutation\": \"Mr\","
			        		+ "\"fName\": \""+AgentName+"\","
			        		+ "\"lName\": \"billing\","
			        		+ "\"designation\": \"Manager\","
			        		+ "\"phoneNo\": \"Manager\","
			        		+ "\"phoneExtension\": \"101\","
			        		+ "\"faxNo\": \"101\","
			        		+ "\"email\": \""+AgentName+"@stayezee.com\","
			        		+ "\"mobileNo\": \"123\","
			        		+ "\"gender\": \"M\","
			        		+ "\"dob\": \"2016-03-01\","
			        		+ "\"website\": \"www.hotelogix.com\""
			        		+ "}"
			        		+ "},"
			        		+ "\"creditCardDetail\": {"
			        		+ "\"nameOnCard\": \"Mohan\","
			        		+ "\"cardNo\": \"4111111111111111\","
			        		+ "\"cardType\": \"Visa\","
			        		+ "\"expiryMonth\": \"05\","
			        		+ "\"expiryYear\": \"2019\","
			        		+ "\"cvc\": \"101\","
			        		+ "\"address\": \"creditCardAddress\","
			        		+ "\"country\": \"India\","
			        		+ "\"state\": \"Delhi\","
			        		+ "\"city\": \"Delhi\","
			        		+ "\"zip\": \"20160301\""
			        		+ "},"
			        		+ "\"hotels\": [{"
			        		+ "\"id\": \""+hotelid+"\""
			        		+ "}]}}}}")
			        .asJson();
				JsonNode responseJSONString = response.getBody();
				Assert.assertEquals(response.getCode(), 200);
				JSONObject jobj=responseJSONString.getObject();
				JSONObject getSth =jobj.getJSONObject("hotelogix");
			    JSONObject resp=getSth.getJSONObject("response");
			    JSONObject hotels=resp.getJSONArray("hotels").getJSONObject(0);
			    AgentID=hotels.get("agentId").toString();
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
			}catch(Throwable e){ 
				throw new APIException("After edit Getusers api has failed", e);
			}
	       }
	
	
	
}
