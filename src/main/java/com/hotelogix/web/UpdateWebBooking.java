package com.hotelogix.web;



import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.testng.Assert;
import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class UpdateWebBooking {

	
	
	
	
	public void fn_UpdateWebBooking(String url,String wsauthkey,String bookingid,String gueststayid,String firstname) throws APIException{
		try{
			 HttpResponse<String> response = Unirest.post(url)
		                .header("content-type", "application/xml")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		                		+ "<hotelogix version=\"1.0\" datetime=\"2015-10-27T11:23:54\">"
		                		+ "<request method=\"updatewebbooking\" key=\""+wsauthkey+"\" languagecode=\"en\">"
		                		+ " <bookings>"
		                		+ "<booking id=\""+bookingid+"\">"
		                		+ "<gueststays>"
		                		+ "<gueststay id=\""+gueststayid+"\">"
		                		+ "<guest>"
		                		+ "<fname>"+firstname+"</fname>"
		                		+ "<lname>baskandi</lname>"
		                		+ "<email>yashbir.singh@hotelogix.com</email>"
		                		+ "<phone>2121221212121</phone>"
		                		+ "<mobile>666666</mobile>"
		                		+ "<country code=\"IN\">India</country>"
		                		+ "<state code=\"UP\">noida</state>"
		                		+ "<address>ssssss</address>"
		                		+ "<city>noida</city>"
		                		+ "<zip>777777</zip>"
		                		+ "<taxCode>07AQSPJ0999EABC</taxCode>"
		                		+ "</guest>"
		                		+ "</gueststay>"
		                		+ "</gueststays>"
		                		+ "<preference>"
		                		+ "<![CDATA[Preference for group]]>"
		                		+ "</preference>"
		                		+ "</booking>"
		                		+ "</bookings>"
		                		+ "</request>"
		                		+ "</hotelogix>")
		                     .asString();
			 String responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
		     SAXBuilder saxBuilder = new SAXBuilder();
			 Document doc = saxBuilder.build(new StringReader(responseJSONString));
			 Element hotelogix = doc.detachRootElement();
			 Element respons =hotelogix.getChild("response");
			 Element status =respons.getChild("status");
			 String message=status.getAttributeValue("message");
			 Assert.assertEquals(message, "SUCCESS");
		}catch(Throwable e){
			throw new APIException("UpdateWebBooking API is failed", e);
		}
	}
}
