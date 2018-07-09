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

public class GetUsers {

	public void fn_GetUsersAPI(String URL,String WsauthKey,String hotelId,String fname,String counterID,String counterName) throws JSONException, UnirestException, APIException{
		try{
		HttpResponse<JsonNode> response = Unirest.post(URL)
		        .header("content-type", "application/json")
		        .header("x-ig-sg", "D_gg%fkl85_j")
		        .header("cache-control", "no-cache")
		        .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		        .body("{"
		        		+ "\"hotelogix\": {"
		        		+ "\"version\": \"1.0\","
		        		+ "\"datetime\": \"2012-01-16T10:11:15\","
		        		+ "\"request\": {"
		        		+ "\"method\": \"getusers\","
		        		+ "\"key\": \""+WsauthKey+"\","
		        		+ "\"data\": {"
		        		+ "\"hotels\": ["
		        		+ "{ \"id\": \""+hotelId+"\" }"
		        		+ "]"
		        		+ "}}}}")
		        .asJson();
		
		JsonNode responseJSONString = response.getBody();
		Assert.assertEquals(response.getCode(), 200);
		JSONObject jobj1=responseJSONString.getObject();
		JSONObject getSth =jobj1.getJSONObject("hotelogix");
        JSONObject resp=getSth.getJSONObject("response");
		JSONObject status=resp.getJSONObject("status");
		String str=status.get("message").toString();
		Assert.assertEquals(str.equals("success"), true);
		JSONArray hotelarr= resp.getJSONArray("hotels");
		for(int i=0;i<=hotelarr.length();i++){
		JSONObject hotelObj= hotelarr.getJSONObject(i);
		String ID=hotelObj.get("id").toString();
		if(ID.equals(hotelId)){
			JSONArray userarr=hotelObj.getJSONArray("users");
			for(int j=0;j<=userarr.length();j++){
				JSONObject userObj= userarr.getJSONObject(i);
				String fName=userObj.get("fName").toString();
				if(fName.equals(fname)){
					JSONArray countersarr= userObj.getJSONArray("counters");
					for(int k=0;k<=countersarr.length();k++){
						JSONObject counterObj= countersarr.getJSONObject(i);
						String cntrID=counterObj.get("id").toString();
						if(cntrID.equals(counterID)){
							Assert.assertEquals(cntrID.equals(counterID), true,"Counter ID did not matched.");
							String cntrName=counterObj.get("name").toString();
							Assert.assertEquals(cntrName.equals(counterName), true,"Counter Name didn't matched");
							break;
						}
					}
				}
				break;
			}						
		}
		break;
		}
		
	}catch(Throwable e){
		throw new APIException("fn_GetUsersAPI has failed", e);
	}
	
	
	}
}
