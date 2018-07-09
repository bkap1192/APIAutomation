package com.hotelogix.frontdesk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class AttachGuest {

	/*public static String fname;
	public static String lname;
*/	
	
	
	public void fn_AttachGuestAPI(String URL,String loginaccesskey,String bookingID,String guestStayID,String fname,String lname) throws JSONException, UnirestException, APIException{
		
		String FName=null;
		String LName=null;
		
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
                		+ "\"method\": \"attachguest\","
                		+ "\"key\": \""+loginaccesskey+"\","
                		+ "\"data\": {"
                		+ "\"bookings\": ["
                		+ "{"
                		+ "\"id\": \""+bookingID+"\","
                		+ "\"guests\": ["
                		+ "{"
                		+ "\"guestStayId\": \""+guestStayID+"\","
                		+ "\"replaceGuest\": false,"
                		+ "\"taxCode\": \"AQSPJ0000\","
                		+ "\"fName\": \""+fname+"\","
                		+ "\"lName\": \""+lname+"\","
                		+ "\"email\": \""+fname+"@stayezee.com\","
                		+ "\"phoneNo\": \"011598888\","
                		+ "\"mobileNo\": \"9968040558\","
                		+ "\"gender\": \"M\","
                		+ "\"nationality\": \"US\","
                		+ "\"identityTypeId\": \"\","
                		+ "\"identityNo\": null,"
                		+ "\"organization\": \"\","
                		+ "\"otherDetails\": {"
                		+ "\"spouseSalutation\": \"Mrs.\","
                		+ "\"spouseFName\": \""+APIUtils.generateRandomString()+"\","
                		+ "\"spouseLName\": \"ji\","
                		+ "\"spouseDob\": \"1985-02-05\","
                		+ "\"anniversary\": \"2015-12-05\""
                		+ "},"
                		+ "\"addresses\": {"
                		+ "\"home\": {"
                		+ "\"address\": \"D-996, Cross Road\","
                		+ "\"country\": \"US\","
                		+ "\"state\": \"CA\","
                		+ "\"city\": \"Los Angeles\","
                		+ "\"zip\": \"325215\""
                		+ "},"
                		+ "\"work\": {}"
                		+ "}}]}]}}}}")
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
	 int count=bkgObj.getJSONArray("guestStays").length();
	 for(int i=0;i<=count;i++){
		 JSONObject gueststayObj= bkgObj.getJSONArray("guestStays").getJSONObject(i);
		 String guestID=gueststayObj.get("id").toString();
		 if(guestID.equals(guestStayID)){
			 JSONObject guestDetailObj=gueststayObj.getJSONObject("guestDetails");
			 FName=guestDetailObj.get("fName").toString();
			 LName=guestDetailObj.getString("lName").trim();
			 break;
		 }		 		 
	 }
	 Assert.assertEquals(FName.equalsIgnoreCase(fname), true,"Guest first name didn't matched");
	 Assert.assertEquals(LName.equalsIgnoreCase(lname), true,"Guest last name didn't matched");
		}catch(Throwable e){
			throw new APIException("fn_AttachGuestAPI", e);
		}
	}
	
}
