package com.hotelogix.admin;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetAgents {

	
	
	public void fn_GetAgents(String url, String wsauthkey, String hotelid,String agentname) throws APIException{
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
			        		+ "\"method\": \"getagents\","
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
			    JSONArray agent=agents.getJSONObject(0).getJSONArray("agents");
				  for(int i=0;i<=agent.length()-1;i++){
				    	String id=agent.getJSONObject(i).get("id").toString();
				    	arr.add(id);
				    	if(id.equalsIgnoreCase(CreateAgent.AgentID)){
				    		String fname=agent.getJSONObject(i).get("fName").toString();
				    		Assert.assertEquals(fname.equalsIgnoreCase(agentname), true,"Created or Edited agent name has mismatched in getagent api.");
				    	}
				    }
				Assert.assertEquals(arr.contains(CreateAgent.AgentID), true,"Created or Edited agent id has mismatched in getagent api.");
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
			}catch(Throwable e){ 
				throw new APIException("GetAgents api has failed", e);
			}
	       }
	
	
	public void fn_GetAgentsAfterDelete(String url, String wsauthkey, String hotelid,String agentname) throws APIException{
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
			        		+ "\"method\": \"getagents\","
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
			    JSONArray agent=agents.getJSONObject(0).getJSONArray("agents");
				  for(int i=0;i<=agent.length()-1;i++){
				    	String id=agent.getJSONObject(i).get("id").toString();
				    	arr.add(id);
				    }
				Assert.assertEquals(arr.contains(CreateAgent.AgentID), false,"Deleted agent id has found in getagent api.");
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
			}catch(Throwable e){ 
				throw new APIException("GetAgents api has failed", e);
			}
	       }
	
	
	
}
