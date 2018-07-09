package com.hotelogix.web;

import java.io.StringReader;
import java.util.Iterator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class RateIDsForadtoCart {

	
	
	
	
	
	public String fn_RateIDsForadtoCart(String url,String wsauthkey,String fromdate,String todate,String hotelid) throws APIException{
		 String RateID=null;
		try{
			 HttpResponse<String> response = Unirest.post(url)
		                .header("content-type", "application/xml")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		                		+ "<hotelogix version=\"1.0\" datetime=\"2015-10-27T11:23:54\">"
		                		+ "<request method=\"rateidsforadtocart\" key=\""+wsauthkey+"\" languagecode=\"en\">"
		                	    + "<stay checkindate=\""+fromdate+"\" checkoutdate=\""+todate+"\"/>"
		                	    + "<hotels>"
		                	    + "<hotel id=\""+hotelid+"\">"
		                	    + "</hotel>"
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
			 Element roomtypes =hotel.getChild("roomtypes");
			 Element roomtype =roomtypes.getChild("roomtype");
			 Element rates =roomtype.getChild("rates");
			 Element rate =rates.getChild("rate");
			 Element paxes =rate.getChild("paxes");
			 Iterator<Element> iete=paxes.getChildren().iterator();
				 while(iete.hasNext()==true){
					 Element ele=iete.next();
					 String adult=ele.getAttributeValue("adult");
					if(adult.equals("1")){
						RateID=ele.getAttributeValue("id");
						break;
					}
				    }
			 Element status =hotel.getChild("status");
			 String message=status.getAttributeValue("message");
			 Assert.assertEquals(message, "Success");
	}catch(Throwable e){
		throw new APIException("RateIDsForadtoCart API is failed", e);
	}
		return RateID;
	}	
	
	
	
}
