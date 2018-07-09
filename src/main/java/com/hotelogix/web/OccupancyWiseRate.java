package com.hotelogix.web;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class OccupancyWiseRate {

	
	public static String RoomTypeCode;
	public static String RateCode;
	
	public void fn_OccupancyWiseRate(String url,String wsauthkey,String fromdate,String todate) throws APIException{
		try{
			 HttpResponse<JsonNode> response = Unirest.post(url)
		                .header("content-type", "application/json")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("{ \"hotelogix\": {"
		                		+ "\"version\": \"1.0\","
		                		+ "\"datetime\": \"2016-04-20T10:42:57\","
		                		+ "\"request\": {"
		                		+ "\"method\": \"occupancywiserate\","
		                		+ "\"key\": \""+wsauthkey+"\","
		                		+ "\"languagecode\": \"en\","
		                		+ "\"data\": {"
		                		+ "\"stay\": {"
		                		+ "\"checkindate\": \""+fromdate+"\","
		                		+ "\"checkoutdate\": \""+todate+"\""
		                		+ "}}}}}")
		                     .asJson();
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 JSONArray hotearr=respons.getJSONArray("hotels");
			 JSONArray roomtype=hotearr.getJSONObject(0).getJSONArray("roomtypes");
			 RoomTypeCode=roomtype.getJSONObject(0).get("code").toString();
			 JSONArray rates=roomtype.getJSONObject(0).getJSONArray("rates");
				for(int i=0;i<=rates.length()-1;i++){
					 JSONObject rate=rates.getJSONObject(i);
					 String ratesname=rate.get("name").toString();
					 if(ratesname.toLowerCase().contains("package")){
						 RateCode=rate.get("code").toString();
					 }
				 }
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "Success");
		}catch(Throwable e){
			throw new APIException("OccupancyWiseRate API is failed", e);
		}
	}
	
	
	
	
}
