package com.hotelogix.admin;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class CreateCorporate {

	public static String CorporateName;
	public static String CorporateID;
	
	public void fn_CreateCorporate(String url, String wsauthkey, String hotelid) throws APIException{
		CorporateName=APIUtils.GA().generateRandomString();
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
			        		+ "\"method\": \"createcorporate\","
			        		+ "\"key\": \""+wsauthkey+"\","
			        		+ "\"data\": {"
			        		+ "\"businessName\": \""+CorporateName+"\","
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
			        		+ "\"fName\": \""+CorporateName+"\","
			        		+ "\"lName\": \"Mishra\","
			        		+ "\"designation\": \"Manager\","
			        		+ "\"phoneNo\": \"Manager\","
			        		+ "\"phoneExtension\": \"101\","
			        		+ "\"faxNo\": \"101\","
			        		+ "\"email\": \""+CorporateName+"@stayezee.com\","
			        		+ "\"mobileNo\": \"123\","
			        		+ "\"gender\": \"M\","
			        		+ "\"dob\": \"2016-03-01\","
			        		+ "\"website\": \"www.hotelogix.com\""
			        		+ "},"
			        		+ "\"billing\": {"
			        		+ "\"salutation\": \"Mr\","
			        		+ "\"fName\": \""+CorporateName+"\","
			        		+ "\"lName\": \"billing\","
			        		+ "\"designation\": \"Manager\","
			        		+ "\"phoneNo\": \"Manager\","
			        		+ "\"phoneExtension\": \"101\","
			        		+ "\"faxNo\": \"101\","
			        		+ "\"email\": \""+CorporateName+"@stayezee.com\","
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
			        		+ "\"creditLimit\": \"100\","
			        		+ "\"paymentTerms\": \"5\","
			        		+ "\"discountable\": true,"
			        		+ "\"discount\": \"5\","
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
			    CorporateID=hotels.get("corporateId").toString();
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
			}catch(Throwable e){ 
				throw new APIException("Create Corporate api has failed", e);
			}
	}
	
	
	
}
