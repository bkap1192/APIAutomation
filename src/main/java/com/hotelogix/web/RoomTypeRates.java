package com.hotelogix.web;

import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class RoomTypeRates {

	
	
	
	
	public void fn_RoomTypeRates(String url,String wsauthkey,String fromdate,String todate) throws APIException{
		try{
			 HttpResponse<String> response = Unirest.post(url)
		                .header("content-type", "application/xml")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		                		+ "<hotelogix version=\"1.0\" datetime=\"2015-10-27T11:23:54\">"
		                		+ "<request method=\"roomtyperates\" key=\""+wsauthkey+"\" languagecode=\"en\">"
		                	    + "<stay checkindate=\""+fromdate+"\" checkoutdate=\""+todate+"\"/>"
		                	    + "<pax adult=\"1\" child=\"0\" infant=\"0\"/>"
		                	    + "<limit value=\"200\" offset=\"0\" />"
		                		+ "</request>"
		                		+ "</hotelogix>")
		                     .asString();
			 String responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
		     SAXBuilder saxBuilder = new SAXBuilder();
			 Document doc = saxBuilder.build(new StringReader(responseJSONString));
			 Element hotelogix = doc.detachRootElement();
			 Element respons =hotelogix.getChild("response");
			 Element hotels =respons.getChild("hotels");
			 Element hotel =hotels.getChild("hotel");
			 Element status =hotel.getChild("status");
			 String message=status.getAttributeValue("message");
			 Assert.assertEquals(message, "SUCCESS");
	}catch(Throwable e){
		throw new APIException("RoomTypeRates API is failed", e);
	}
	}
	
	
	
}
