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

public class GetDNR {

	public void fn_GetDNRAPI(String URL,String loginAccessKey,String NightAuditDt,String dnrID,String roomID,String rtID,String frmDate,String hotelID) throws Throwable{
		
		String toDate=APIUtils.GA().fn_setFrmToDate(frmDate,1);

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
		        		+ "\"method\": \"getdnrs\","
		        		+ "\"key\": \""+loginAccessKey+"\","
		        		+ "\"data\": {"
		        		+ "\"hotels\":[{"
		        		+ "\"id\":"+hotelID+""
		        		+ "}],"
		        		+ "\"fromDate\": \""+frmDate+"\","
		        		+ "\"toDate\": \""+toDate+"\""
		        		+ "}}}}")
		        .asJson();
		
			JsonNode responseJSONString = response.getBody();
			Assert.assertEquals(response.getCode(), 200);
			JSONObject jobj=responseJSONString.getObject();
			JSONObject getSth =jobj.getJSONObject("hotelogix");
		    JSONObject resp=getSth.getJSONObject("response");
		    JSONObject status= resp.getJSONObject("status");
		    String msg=status.get("message").toString();
		    Assert.assertEquals(msg.equals("success"), true);
		    String naDate=resp.get("nightAuditDate").toString();
		    Assert.assertEquals(naDate.equals(NightAuditDt), true,"Night Audit message din't matched.");
		    JSONArray dnrarr=resp.getJSONArray("dnrs");
		    JSONObject dnrObj= dnrarr.getJSONObject(0);
		    String dnrid=dnrObj.get("id").toString();
		    Assert.assertEquals(dnrid.equals(dnrID), true,"DNR id didn't matched.");
		    String room=dnrObj.get("roomId").toString();
		    Assert.assertEquals(room.equals(roomID), true,"oom id didn't matched.");
		    String rtypeID=dnrObj.get("roomTypeId").toString();
		    Assert.assertEquals(rtypeID.equals(rtID), true,"Room Type id didn't matched.");
		    String fromDt=dnrObj.get("fromDate").toString();
		    Assert.assertEquals(fromDt.equals(frmDate), true,"From date of reservation didn't matched.");
		    String toDt=dnrObj.get("toDate").toString();
		    Assert.assertEquals(toDt.equals(toDt), true,"To date of reservation didn't matched.");
		}catch(Throwable e){
			throw new APIException("fn_GetDNRAPI", e);
		}
	}
	
	
}
