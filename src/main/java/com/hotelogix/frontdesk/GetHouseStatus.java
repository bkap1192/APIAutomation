package com.hotelogix.frontdesk;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetHouseStatus {

	
	public static String rtid;
	public static String uaroomID;
	
	public void fn_GetHouseStatusAPI(String URL,String loginaccessKey,String frmDate,String hotelID) throws Throwable{
		
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
		        		+ "\"method\": \"gethousestatus\","
		        		+ "\"key\": \""+loginaccessKey+"\","
		        		+ "\"data\": {"
		        		+ "\"hotels\":[{"
		        		+ "\"id\":"+hotelID+"}],"
		        		+ "\"fromDate\": \""+frmDate+"\","
		        		+ "\"toDate\": \""+toDate+"\""
		        		+ "}}}}")
		        .asJson();
		
			JsonNode responseJSONString = response.getBody();
			System.out.println(responseJSONString);
			Assert.assertEquals(response.getCode(), 200);
			JSONObject jobj1=responseJSONString.getObject();
			JSONObject getSth =jobj1.getJSONObject("hotelogix");
		    JSONObject resp=getSth.getJSONObject("response");
		    JSONObject status= resp.getJSONObject("status");
            String msg=status.get("message").toString();  
			Assert.assertEquals(msg.equals("success"), true);
			String NADate= resp.get("nightAuditDate").toString();
			Assert.assertEquals(NADate.equals(frmDate), true);
			JSONArray dayarr=  resp.getJSONArray("days");
            JSONObject rtObj= dayarr.getJSONObject(0);
            JSONArray rtarr=  rtObj.getJSONArray("roomTypes");
            JSONObject jsonrtype=  rtarr.getJSONObject(0);
            rtid=jsonrtype.get("id").toString();
            JSONArray unassignedarr= jsonrtype.getJSONArray("unassigRooms");
            JSONObject uaroomObj= unassignedarr.getJSONObject(0);
            uaroomID=uaroomObj.get("id").toString();
		}catch(Throwable e){
			throw new APIException("fn_GetHouseStatusAPI has failed", e);
		}
	}
	
}
