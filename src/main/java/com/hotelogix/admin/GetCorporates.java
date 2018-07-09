package com.hotelogix.admin;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetCorporates {

	
	
	
	
	
	public void fn_GetCorporates(String url, String wsauthkey, String hotelid,String corporatename,String corporateid) throws APIException{
		ArrayList<String> arr=new ArrayList<String>();
		try{
			HttpResponse<JsonNode> response = Unirest.post(url)
			        .header("content-type", "application/json")
			        .header("x-ig-sg", "D_gg%fkl85_j")
			        .header("cache-control", "no-cache")
			        .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
			        .body("{"
			        		+ "\"hotelogix\": {"
			        		+ "\"version\": \"1.0\","
			        		+ "\"datetime\": \"2012-01-16T10:10:15\","
			        		+ "\"request\": {"
			        		+ "\"method\": \"getcorporates\","
			        		+ "\"key\": \""+wsauthkey+"\","
			        		+ "\"data\": {"
			        		+ "\"hotels\": ["
			        		+ "{"
			        		+ "\"id\": \""+hotelid+"\""
			        		+ "}"
			        		+ "]"
			        		+ "}}}}")
			        .asJson();
				JsonNode responseJSONString = response.getBody();
				Assert.assertEquals(response.getCode(), 200);
				JSONObject jobj=responseJSONString.getObject();
				JSONObject getSth =jobj.getJSONObject("hotelogix");
			    JSONObject resp=getSth.getJSONObject("response");
			    JSONArray agents=resp.getJSONArray("hotels");
			    JSONArray agent=agents.getJSONObject(0).getJSONArray("corporates");
				  for(int i=0;i<=agent.length()-1;i++){
				    	String id=agent.getJSONObject(i).get("id").toString();
				    	arr.add(id);
				    	if(id.equalsIgnoreCase(corporateid)){
				    		String fname=agent.getJSONObject(i).get("fName").toString();
				    		Assert.assertEquals(fname.equalsIgnoreCase(corporatename), true,"Created or Edited corporate name has mismatched in getcorporate api.");
				    	}
				    }
				Assert.assertEquals(arr.contains(corporateid), true,"Created or Edited corporate id has mismatched in getcorporate api.");
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
			}catch(Throwable e){ 
				throw new APIException("GetCorporates api has failed", e);
			}
	}
	
	
	public void fn_GetCorporateAfterDelete(String url, String wsauthkey, String hotelid) throws APIException{
		ArrayList<String> arr=new ArrayList<String>();
		try{
			HttpResponse<JsonNode> response = Unirest.post(url)
			        .header("content-type", "application/json")
			        .header("x-ig-sg", "D_gg%fkl85_j")
			        .header("cache-control", "no-cache")
			        .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
			        .body("{"
			        		+ "\"hotelogix\": {"
			        		+ "\"version\": \"1.0\","
			        		+ "\"datetime\": \"2012-01-16T10:10:15\","
			        		+ "\"request\": {"
			        		+ "\"method\": \"getcorporates\","
			        		+ "\"key\": \""+wsauthkey+"\","
			        		+ "\"data\": {"
			        		+ "\"hotels\": ["
			        		+ "{"
			        		+ "\"id\": \""+hotelid+"\""
			        		+ "}"
			        		+ "]"
			        		+ "}}}}")
			        .asJson();
				JsonNode responseJSONString = response.getBody();
				Assert.assertEquals(response.getCode(), 200);
				JSONObject jobj=responseJSONString.getObject();
				JSONObject getSth =jobj.getJSONObject("hotelogix");
			    JSONObject resp=getSth.getJSONObject("response");
			    JSONArray agents=resp.getJSONArray("hotels");
			    JSONArray agent=agents.getJSONObject(0).getJSONArray("corporates");
				  for(int i=0;i<=agent.length()-1;i++){
				    	String id=agent.getJSONObject(i).get("id").toString();
				    	arr.add(id);
				    }
				Assert.assertEquals(arr.contains(CreateCorporate.CorporateID), false,"Deleted corporate id has found in getcorporate api.");
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
			}catch(Throwable e){ 
				throw new APIException("Get corporate api after delete has failed", e);
			}
	}
	
	
	
	
	
}
