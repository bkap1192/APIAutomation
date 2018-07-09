package com.hotelogix.web;

import static org.testng.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetOrder {

	public String StatusCode;
	
	
	
	public void fn_GetOrderAndValidateRequiredFiledAfterUpadateBooking(String url,String wsauth,String orderid,String updatefname,String firstfname) throws APIException{
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
		                		+ " \"method\": \"getorder\","
		                		+ "\"key\": \""+wsauth+"\","
		                		+ "\"languagecode\": \"en\","
		                		+ "\"data\": {"
		                		+ "\"orderId\": {"
		                		+ "\"value\": \""+orderid+"\""
		                		+"}}}}}")
		                     .asJson();
			 
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 JSONObject order=respons.getJSONObject("order");
			 JSONArray bookings=order.getJSONArray("bookings");
			 JSONObject bookingjson=bookings.getJSONObject(0);
			 JSONObject gueststaysjson=bookingjson.getJSONArray("gueststays").getJSONObject(0);
			 JSONObject guestdetails=gueststaysjson.getJSONObject("guestdetails");
			 String fname=guestdetails.get("fName").toString();
			 Assert.assertEquals(fname.equalsIgnoreCase(firstfname), false,"");
			 Assert.assertEquals(fname.equalsIgnoreCase(updatefname), true,"");
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
	}catch(Throwable e){
		throw new APIException("GetOrder cart api is failed.", e);
	}
	}
	
	
	public void fn_GetOrder(String url,String wsauth,String orderid) throws APIException{
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
		                		+ " \"method\": \"getorder\","
		                		+ "\"key\": \""+wsauth+"\","
		                		+ "\"languagecode\": \"en\","
		                		+ "\"data\": {"
		                		+ "\"orderId\": {"
		                		+ "\"value\": \""+orderid+"\""
		                		+"}}}}}")
		                     .asJson();
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 JSONObject order=respons.getJSONObject("order");
			 JSONArray bookings=order.getJSONArray("bookings");
			 JSONObject bookingjson=bookings.getJSONObject(0);
			 StatusCode=bookingjson.get("statuscode").toString();
			 String getorderid=order.get("id").toString();
			 Assert.assertEquals(getorderid, orderid," Order id is not matched with response of getorder id -: ");
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
	}catch(Throwable e){
		throw new APIException("GetOrder cart api is failed.", e);
	}
	}
	
	}
