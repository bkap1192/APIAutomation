package com.hotelogix.admin;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class GetUsers {

	
	public static String UserID;
	
	public void fn_GetUsersForAdmin(String url,String wsauthkey,String hotelid) throws APIException{
		ArrayList<String> arrlist=new ArrayList<String>();
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
			        		+ "\"method\": \"getusers\","
			        		+ "\"key\": \""+wsauthkey+"\","
			        		+ "\"data\": {"
			        		+ "\"hotels\": ["
			        		+ " { \"id\": \""+hotelid+"\" }"
			        		+ "]"
			        		+ "}}}}")
			        .asJson();
				JsonNode responseJSONString = response.getBody();
				Assert.assertEquals(response.getCode(), 200);
				JSONObject jobj=responseJSONString.getObject();
				JSONObject getSth =jobj.getJSONObject("hotelogix");
			    JSONObject resp=getSth.getJSONObject("response");
			    JSONArray arr=resp.getJSONArray("hotels").getJSONObject(0).getJSONArray("users");
			    int arrsize=arr.length();
			    for(int i=0;i<=arrsize-1;i++){
			    	String fname=arr.getJSONObject(i).get("fName").toString();
			    	arrlist.add(fname);
			    	if(fname.equalsIgnoreCase(CreateUser.fname)){
			    		UserID=arr.getJSONObject(i).get("id").toString();
			    	}
			    }
			    Assert.assertEquals(arrlist.contains(CreateUser.fname), true);
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
			}catch(Throwable e){ 
				throw new APIException("Getusers api has failed", e);
			}
	       }
	
	
	public void fn_GetUsersAfterEdit(String url,String wsauthkey,String hotelid) throws APIException{
		ArrayList<String> arrlist=new ArrayList<String>();
		String ModifiedUserName=null;
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
			        		+ "\"method\": \"getusers\","
			        		+ "\"key\": \""+wsauthkey+"\","
			        		+ "\"data\": {"
			        		+ "\"hotels\": ["
			        		+ " { \"id\": \""+hotelid+"\" }"
			        		+ "]"
			        		+ "}}}}")
			        .asJson();
				JsonNode responseJSONString = response.getBody();
				Assert.assertEquals(response.getCode(), 200);
				JSONObject jobj=responseJSONString.getObject();
				JSONObject getSth =jobj.getJSONObject("hotelogix");
			    JSONObject resp=getSth.getJSONObject("response");
			    JSONArray arr=resp.getJSONArray("hotels").getJSONObject(0).getJSONArray("users");
			    int arrsize=arr.length();
			    for(int i=0;i<=arrsize-1;i++){
			    	String id=arr.getJSONObject(i).get("id").toString();
			    	arrlist.add(id);
			    	if(id.equalsIgnoreCase(GetUsers.UserID)){
			    		ModifiedUserName=arr.getJSONObject(i).get("fName").toString();
			    		Assert.assertEquals(CreateUser.fname.equalsIgnoreCase(ModifiedUserName), false);
			    		Assert.assertEquals(EditUser.EditFname.equalsIgnoreCase(ModifiedUserName), true);
			    	}
			    }
			    Assert.assertEquals(arrlist.contains(UserID), true);
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
			}catch(Throwable e){ 
				throw new APIException("After edit Getusers api has failed", e);
			}
	       }
	
	
	public void fn_GetUsersforDeleteOrInActive(String url,String wsauthkey,String hotelid) throws APIException{
		ArrayList<String> arrlist=new ArrayList<String>();
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
			        		+ "\"method\": \"getusers\","
			        		+ "\"key\": \""+wsauthkey+"\","
			        		+ "\"data\": {"
			        		+ "\"hotels\": ["
			        		+ " { \"id\": \""+hotelid+"\" }"
			        		+ "]"
			        		+ "}}}}")
			        .asJson();
				JsonNode responseJSONString = response.getBody();
				Assert.assertEquals(response.getCode(), 200);
				JSONObject jobj=responseJSONString.getObject();
				JSONObject getSth =jobj.getJSONObject("hotelogix");
			    JSONObject resp=getSth.getJSONObject("response");
			    JSONArray arr=resp.getJSONArray("hotels").getJSONObject(0).getJSONArray("users");
			    int arrsize=arr.length();
			    for(int i=0;i<=arrsize-1;i++){
			    	String id=arr.getJSONObject(i).get("id").toString();
			    	arrlist.add(id);
			    }
			    Assert.assertEquals(arrlist.contains(UserID), false);
			    JSONObject status= resp.getJSONObject("status");
			    String msg=status.get("message").toString();
			    Assert.assertEquals(msg.equals("success"), true);
			}catch(Throwable e){ 
				throw new APIException("After delete or inactive Getusers api has failed", e);
			}
	       }
	
	
	
	
	
	
	
	
	}

