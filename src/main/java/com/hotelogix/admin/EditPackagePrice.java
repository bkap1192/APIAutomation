package com.hotelogix.admin;

import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class EditPackagePrice {

	
	
	public void fn_EditPackagePriceForGroupHotel(String url, String wsauthkey, String hotelid, String ratecode,String roomtypecode,String extrabedprice) throws APIException{
		try{
			HttpResponse<JsonNode> response = Unirest.post(url)
			        .header("content-type", "application/json")
			        .header("x-ig-sg", "D_gg%fkl85_j")
			        .header("cache-control", "no-cache")
			        .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
			        .body("{"
			        		+ "\"hotelogix\": {"
			        		+ "\"version\": \"1.0\","
			        		+ "\"datetime\": \"2016-05-02T12:34:18\","
			        		+ "\"request\": {"
			        		+ "\"method\": \"editpackageprice\","
			        		+ "\"key\": \"NO4CIu615R|enRW\","
			        		+ "\"data\":{"
			        		+ "\"hotels\":["
			        		+ "{"
			        		+ "\"id\":4444,"
			        		+ "\"rateCode\": \""+ratecode+"\","
			        		+ "\"rates\":["
			        		+ "{"
			        		+ "\"roomTypeCode\":\""+roomtypecode+"\","
			        		+ "\"fromDate\":\"2013-06-07\","
			        		+ "\"toDate\":\"2013-06-09\","
			        		+ "\"occupancy\":["
			        		+ "{"
			        		+ "\"type\":\"Adult\","
			        		+ "\"number\":1,"
			        		+ "\"amount\":625.00"
			        		+ "},"
			        		+ "{"
			        		+ "\"type\":\"Child\","
			        		+ "\"number\":1,"
			        		+ "\"amount\":36"
			        		+ "}"
			        		+ "],"
			        		+ "\"extraBedPrice\":"+extrabedprice+""
			        		+ "}"
			        		+ "]"
			        		+ "},"
			        		+ "{"
			        		+ "\"id\":4544,"
			        		+ "\"rateCode\": \"PKG1\","
			        		+ "\"rates\":["
			        		+ "{"
			        		+ "\"roomTypeCode\":\"DLX\","
			        		+ "\"fromDate\":\"2013-06-07\","
			        		+ "\"toDate\":\"2013-06-09\","
			        		+ "\"occupancy\":["
			        		+ "{"
			        		+ "\"type\":\"Adult\","
			        		+ "\"number\":1,"
			        		+ "\"amount\":425.00"
			        		+ "},"
			        		+ "{"
			        		+ "\"type\":\"Child\","
			        		+ "\"number\":1,"
			        		+ "\"amount\":36"
			        		+ "}"
			        		+ "],"
			        		+ "\"extraBedPrice\":695"
			        		+ "}]}]}}}}").
			        asJson();
				JsonNode responseJSONString = response.getBody();
				Assert.assertEquals(response.getCode(), 200);
				JSONObject jobj=responseJSONString.getObject();
				JSONObject getSth =jobj.getJSONObject("hotelogix");
			    JSONObject resp=getSth.getJSONObject("response");
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
		}catch(Throwable e){
			throw new APIException("", e);
		}
	}
	
	
	public void fn_EditPackagePrice(String url, String wsauthkey, String hotelid, String ratecode,String roomtypecode,String extrabedprice,String fromdate,String todate) throws APIException{
		try{
			HttpResponse<JsonNode> response = Unirest.post(url)
			        .header("content-type", "application/json")
			        .header("x-ig-sg", "D_gg%fkl85_j")
			        .header("cache-control", "no-cache")
			        .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
			        .body("{"
			        		+ "\"hotelogix\": {"
			        		+ "\"version\": \"1.0\","
			        		+ "\"datetime\": \"2016-05-02T12:34:18\","
			        		+ "\"request\": {"
			        		+ "\"method\": \"editpackageprice\","
			        		+ "\"key\": \""+wsauthkey+"\","
			        		+ "\"data\":{"
			        		+ "\"hotels\":["
			        		+ "{"
			        		+ "\"id\":"+hotelid+","
			        		+ "\"rateCode\": \""+ratecode+"\","
			        		+ "\"rates\":["
			        		+ "{"
			        		+ "\"roomTypeCode\":\""+roomtypecode+"\","
			        		+ "\"fromDate\":\""+fromdate+"\","
			        		+ "\"toDate\":\""+todate+"\","
			        		+ "\"occupancy\":["
			        		+ "{"
			        		+ "\"type\":\"Adult\","
			        		+ "\"number\":1,"
			        		+ "\"amount\":625.00"
			        		+ "},"
			        		+ "{"
			        		+ "\"type\":\"Child\","
			        		+ "\"number\":1,"
			        		+ "\"amount\":36"
			        		+ "}"
			        		+ "],"
			        		+ "\"extraBedPrice\":"+extrabedprice+""
			        		+ "}"
			        		+ "]"
			        		+ "}]}}}}").
			        asJson();
				JsonNode responseJSONString = response.getBody();
				Assert.assertEquals(response.getCode(), 200);
				JSONObject jobj=responseJSONString.getObject();
				JSONObject getSth =jobj.getJSONObject("hotelogix");
			    JSONObject resp=getSth.getJSONObject("response");
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
		}catch(Throwable e){
			throw new APIException("", e);
		}
	}
	
	
	
}
