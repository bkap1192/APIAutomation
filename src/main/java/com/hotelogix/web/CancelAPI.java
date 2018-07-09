package com.hotelogix.web;

import java.io.StringReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class CancelAPI {

	
	public String StatusCode;
	
	
	public void fn_CancelAPI(String url,String wsauthkey,String bookingid,String cancellationchargeamt,String orderid) throws APIException{
		try{
			 HttpResponse<String> response = Unirest.post(url)
		                .header("content-type", "application/xml")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		                		+ "<hotelogix version=\"1.0\" datetime=\"2015-10-27T11:23:54\">"
		                		+ "<request method=\"cancel\" key=\""+wsauthkey+"\" languagecode=\"en\">"
		                		+ "<orderId value=\""+orderid+"\"/>"
		                		+ "<reservationId  value=\""+bookingid+"\"/>"
		                		+ "<cancelCharge amount=\""+cancellationchargeamt+"\"/>"
		                		+ "<cancelDescription>This is a test cancel from webservice</cancelDescription>"
		                		+ "<doNotSendEmail value=\"0\" />"
		                		+ "</request>"
		                		+ "</hotelogix>")
		                     .asString();
			 String responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
		     SAXBuilder saxBuilder = new SAXBuilder();
			 Document doc = saxBuilder.build(new StringReader(responseJSONString));
			 Element hotelogix = doc.detachRootElement();
			 Element respons =hotelogix.getChild("response");
			 Element order =respons.getChild("order");
			 Element booking=order.getChild("bookings");
			 StatusCode=booking.getChild("booking").getAttributeValue("statuscode");
			 Assert.assertEquals(StatusCode, "CANCEL","After cancel reservation status code is not matched with cancel -: ");
			 Element status =respons.getChild("status");
			 String message=status.getAttributeValue("message");
			 Assert.assertEquals(message, "SUCCESS");
	}catch(Throwable e){
		throw new APIException("Cancel api is failed ", e);
	}
	}
	
	
	
	
}
