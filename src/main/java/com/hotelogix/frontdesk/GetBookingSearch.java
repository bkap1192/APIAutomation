package com.hotelogix.frontdesk;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class GetBookingSearch {

	
	public void fn_GetBookingSearchAPI(String URL,String loginaccesskey,String frmDate,String toDate,String bookingCode) throws UnirestException, JSONException, APIException{
		try{
		HttpResponse<JsonNode> response = Unirest.post(URL)
                .header("content-type", "application/json")
                .header("x-ig-sg", "D_gg%fkl85_j")
                .header("cache-control", "no-cache")
                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
                .body("{"
                		+ "\"hotelogix\": {"
                		+ "\"version\": \"1.0\","
                		+ "\"datetime\": \"2012-01-16T10:10:15\","
                		+ "\"request\": {"
                		+ "\"method\": \"getbookingsearch\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"fromDate\": \""+frmDate+"\","
                		+ "\"toDate\": \""+toDate+"\","
                		+ "\"searchText\": \""+bookingCode+"\""
                		+ "}}}}")
                     .asJson();
	 JsonNode responseJSONString=response.getBody();
	 Assert.assertEquals(response.getCode(), 200);
	 String str=responseJSONString.toString();
	 JSONObject jobj1=new JSONObject(str);
	 JSONObject hotelogix= jobj1.getJSONObject("hotelogix");
	 JSONObject respons=hotelogix.getJSONObject("response");
	 JSONObject status= respons.getJSONObject("status");
	 Assert.assertEquals(status.getString("message"), "success");
	 JSONObject bkgObj=respons.getJSONArray("bookings").getJSONObject(0);
	 String code=bkgObj.get("code").toString();
	 Assert.assertEquals(code.equals(bookingCode), true,"Booking Code didn't matched");
	 JSONObject guestStayObj= bkgObj.getJSONArray("guestStays").getJSONObject(0);
	 String checkin=guestStayObj.get("checkInDate").toString();
	 Assert.assertEquals(checkin.equals(frmDate), true,"Checkin Date didn't matched");
	 String checkout=guestStayObj.get("checkOutDate").toString();
	 Assert.assertEquals(checkout.equals(toDate), true,"Checkout Date didn't matched");
		}catch(Throwable e){
			throw new APIException("fn_GetBookingSearchAPI has failed", e);
		}
	}
	
}
