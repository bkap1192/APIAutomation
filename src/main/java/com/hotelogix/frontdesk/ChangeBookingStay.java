package com.hotelogix.frontdesk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ChangeBookingStay {

	
	public void fn_ChangeBookingStay(String URL,String loginaccesskey,String editBookingID,String frmDate,String toDate) throws JSONException, UnirestException, APIException{
		String checkinDate=null;
		String checkoutDate=null;
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
                		+ "\"method\": \"changebookingstay\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"bookings\": ["
                		+ "{"
                		+ "\"id\": \""+editBookingID+"\","
                		+ "\"checkInDate\": \""+frmDate+"\","
                		+ "\"checkOutDate\": \""+toDate+"\""
                		+ "}]}}}}")
                     .asJson();
	 JsonNode responseJSONString=response.getBody();
	 Assert.assertEquals(response.getCode(), 200);
	 String str=responseJSONString.toString();
	 JSONObject jobj1=new JSONObject(str);
	 JSONObject hotelogix= jobj1.getJSONObject("hotelogix");
	 JSONObject respons=hotelogix.getJSONObject("response");
	 JSONObject status= respons.getJSONObject("status");
	 Assert.assertEquals(status.getString("message"), "success");
	 JSONArray bkgarr=respons.getJSONArray("bookings");
     int count=respons.getJSONArray("bookings").length();
     for(int i=0;i<=count;i++){
    	String bkgID= bkgarr.getJSONObject(i).get("id").toString();
    	if(bkgID.equals(editBookingID)){
    		checkinDate=bkgarr.getJSONObject(i).get("checkInDate").toString();
    		checkoutDate=bkgarr.getJSONObject(i).get("checkOutDate").toString();
    		break;
    	}
     }
	 Assert.assertEquals(checkinDate.equals(frmDate), true,"Checkin Date didn't matched");
	 Assert.assertEquals(checkoutDate.equals(toDate), true,"Checkout Date didn't matched");
		}catch(Throwable e){
			throw new APIException("fn_ChangeBookingStay has failed", e);
		}
	}
	
}
