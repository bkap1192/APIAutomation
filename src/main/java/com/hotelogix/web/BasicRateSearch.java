package com.hotelogix.web;

import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class BasicRateSearch {

	
	
	public void fn_BasicRateSearch(String url,String wsauthkey,String fromdate,String todate,String hotelid) throws APIException{
		try{
			 HttpResponse<String> response = Unirest.post(url)
		                .header("content-type", "application/xml")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		                		+ "<hotelogix version=\"1.0\" datetime=\"2015-10-27T11:23:54\">"
		                		+ "<request method=\"basicratesearch\" key=\""+wsauthkey+"\" languagecode=\"en\">"
		                	    + "<stay checkindate=\""+fromdate+"\" checkoutdate=\""+todate+"\"/>"
		                	    + "<pax adult=\"1\" child=\"0\" infant=\"0\"/>"
		                	    + "<minvaluetype value=\"1\"/>"
		                	    + "<hotels>"
		                	    + "<hotel id=\""+hotelid+"\"/>"
		                	    + "</hotels>"
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
			 String hotelcode=hotel.getAttributeValue("code");
			 Assert.assertEquals(hotelcode, hotelid);
	}catch(Throwable e){
		throw new APIException("BasicRateSearch API is failed", e);
	}
	}	
}
