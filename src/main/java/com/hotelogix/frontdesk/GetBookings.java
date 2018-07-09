package com.hotelogix.frontdesk;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetBookings {

	
	public void fn_GetBookingsAPI(String URL,String loginaccesskey,String NADate,String toDate,String bookingID,String hotelID,String webID) throws Throwable{
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
                		+ "\"method\": \"getbookings\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"hotels\":[{\"id\":"+hotelID+"}],"
                		+ "\"fromDate\": \""+NADate+"\","
                		+ "\"toDate\": \""+toDate+"\","
                		+ "\"searchBy\": \"STAYDATE\","
                		+ "\"reservationStatus\":\"RESERVE\""
                		+ "}}}}")
                     .asJson();
	 JsonNode responseJSONString=response.getBody();
	 Assert.assertEquals(response.getCode(), 200);
	 String str=responseJSONString.toString();
	 JSONObject jobj=new JSONObject(str);
	 JSONObject hotelogix= jobj.getJSONObject("hotelogix");
	 JSONObject respons=hotelogix.getJSONObject("response");
	 JSONObject status= respons.getJSONObject("status");
	 Assert.assertEquals(status.getString("message"), "success");
	 String nadate=respons.get("nightAuditDate").toString();
	 Assert.assertEquals(nadate.equals(NADate), true,"Night Audit date didn't matched.");
	 JSONArray bookingsarr1=respons.getJSONArray("bookings");
	 System.out.println(bookingsarr1.length());
	 for(int i=0;i<=bookingsarr1.length();i++){
		 JSONObject bookingObj=bookingsarr1.getJSONObject(i);
		 String webRefId1= bookingObj.get("webRefId").toString();
		 if(webRefId1.equals(webID)){
			 Assert.assertEquals(webRefId1.equals(webID), true,"Web reference ID didn't matched.");
			 break;
		 }		 		 
	 }
	
	}catch(Throwable e){
		throw new APIException("fn_GetBookingsAPI method has failed", e);
	}
	}
}
