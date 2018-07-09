package com.hotelogix.web;



import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


public class Search {

	
	
	
	
	public String fn_Search(String url,String wsauthkey,String fromdate,String todate,String hotelid) throws Throwable{
		String RateID=null;
		try{
			 HttpResponse<JsonNode> response = Unirest.post(url)
		                .header("content-type", "application/json")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("{\"hotelogix\":{ \"version\":\"1.0\","
		                		+ "\"datetime\":\"2012-01-16T10:10:15\","
		                		+ "\"request\":{\"method\": \"search\","
		                		+ "\"key\": \""+wsauthkey+"\","
		                		+ "\"languagecode\": \"en\",\"data\": {"
		                		+ "\"stay\": {"
		                		+ "\"checkindate\": \""+fromdate+"\","
		                		+ "\"checkoutdate\": \""+todate+"\" "
		                		+ "},"
		                		+ "\"pax\": {"
		                		+ "\"adult\": \"1\","
		                		+ "\"child\": \"1\", "
		                		+ "\"infant\": \"1\" "
		                		+ " },"
		                		+ " \"hotels\": [ {"
		                		+ "\"id\": "+hotelid+""
		                		+ "}],"
		                		+ "\"roomrequire\": 1,"
		                		+ "\"limit\": {"
		                		+ "\"value\": 20,"
		                		+ "\"offset\": 0 }}}}}")
		                     .asJson();
			 
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 String hotel= respons.getString("hotels");
			 JSONObject jobj1=APIUtils.GA().GetJsonObject(hotel);
			 String roomtypes=jobj1.getString("roomtypes");
			 String roomtype=roomtypes.substring(1, roomtypes.length()-1);
			 String[] arr=roomtype.split(",");
			 for(String strr:arr){
				 if(strr.contains("id")){
					 RateID=strr.substring(6, strr.length()-1);
					 break;
				 }
			 }
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
		}catch(Throwable e){
			throw new APIException("Search API is failed", e);
		}
		return RateID;
	}
	
	
	
	public String fn_Searchfor2Rooms(String url,String wsauthkey,String fromdate,String todate,String hotelid) throws Throwable{
		String RateID=null;
		try{
			 HttpResponse<JsonNode> response = Unirest.post(url)
		                .header("content-type", "application/json")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("{\"hotelogix\":{ \"version\":\"1.0\","
		                		+ "\"datetime\":\"2012-01-16T10:10:15\","
		                		+ "\"request\":{\"method\": \"search\","
		                		+ "\"key\": \""+wsauthkey+"\","
		                		+ "\"languagecode\": \"en\",\"data\": {"
		                		+ "\"stay\": {"
		                		+ "\"checkindate\": \""+fromdate+"\","
		                		+ "\"checkoutdate\": \""+todate+"\" "
		                		+ "},"
		                		+ "\"pax\": {"
		                		+ "\"adult\": \"1\","
		                		+ "\"child\": \"1\", "
		                		+ "\"infant\": \"1\" "
		                		+ " },"
		                		+ " \"hotels\": [ {"
		                		+ "\"id\": "+hotelid+""
		                		+ "}],"
		                		+ "\"roomrequire\": 2,"
		                		+ "\"limit\": {"
		                		+ "\"value\": 20,"
		                		+ "\"offset\": 0 }}}}}")
		                     .asJson();
			 
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 String hotel= respons.getString("hotels");
			 JSONObject jobj1=APIUtils.GA().GetJsonObject(hotel);
			 String roomtypes=jobj1.getString("roomtypes");
			 String roomtype=roomtypes.substring(1, roomtypes.length()-1);
			 String[] arr=roomtype.split(",");
			 for(String strr:arr){
				 if(strr.contains("id")){
					 RateID=strr.substring(6, strr.length()-1);
					 break;
				 }
			 }
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
		}catch(Throwable e){
			throw new APIException("Search API is failed", e);
		}
		return RateID;
	}
	
	

	public String fn_Search(String url,String wsauthkey,String fromdate,String todate,String hotelid,String rooms) throws Throwable{
		String RateID=null;
		try{
			 HttpResponse<JsonNode> response = Unirest.post(url)
		                .header("content-type", "application/json")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("{\"hotelogix\":{ \"version\":\"1.0\","
		                		+ "\"datetime\":\"2012-01-16T10:10:15\","
		                		+ "\"request\":{\"method\": \"search\","
		                		+ "\"key\": \""+wsauthkey+"\","
		                		+ "\"languagecode\": \"en\",\"data\": {"
		                		+ "\"stay\": {"
		                		+ "\"checkindate\": \""+fromdate+"\","
		                		+ "\"checkoutdate\": \""+todate+"\" "
		                		+ "},"
		                		+ "\"pax\": {"
		                		+ "\"adult\": \"1\","
		                		+ "\"child\": \"1\", "
		                		+ "\"infant\": \"1\" "
		                		+ " },"
		                		+ " \"hotels\": [ {"
		                		+ "\"id\": "+hotelid+""
		                		+ "}],"
		                		+ "\"roomrequire\": "+rooms+","
		                		+ "\"limit\": {"
		                		+ "\"value\": 20,"
		                		+ "\"offset\": 0 }}}}}")
		                     .asJson();
			 
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 String str=responseJSONString.toString();
			 JSONObject jobj=new JSONObject(str);
			 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 String hotel= respons.getString("hotels");
			 String k=hotel.substring(1, hotel.length()-1);
			 JSONObject jobj1=new JSONObject(k);
			 String roomtypes=jobj1.getString("roomtypes");
			 String roomtype=roomtypes.substring(1, roomtypes.length()-1);
			 String[] arr=roomtype.split(",");
			 for(String strr:arr){
				 if(strr.contains("id")){
					 RateID=strr.substring(6, strr.length()-1);
					 break;
				 }
			 }
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
		}catch(Throwable e){
			throw e;
		}
		return RateID;
	}
	
	
	
	
	
}
