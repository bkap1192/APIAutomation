package com.hotelogix.web;


import org.json.JSONObject;
import org.testng.Assert;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class ConfirmBooking {

	public static String BookingID;
	public static String bookinCode;
	
	public void fn_ConfirmBookingWithCreditCardPaymentMaode(String url,String wsauthkey,String orderid,String bookingamount,String AddOnsID) throws APIException{
		try{
			 HttpResponse<JsonNode> response = Unirest.post(url)
		                .header("content-type", "application/json")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("{\"hotelogix\": {"
		                		+ "\"version\": \"1.0\","
		                		+ "\"datetime\": \"2012-01-16T10:10:15\","
		                		+ "\"request\": {"
		                		+ "\"method\": \"confirmbooking\","
		                		+ "\"key\": \""+wsauthkey+"\","
		                		+ "\"languagecode\": \"en\","
		                		+ "\"data\": {"
		                		+ "\"payment\": {"
		                		+ "\"amount\": \""+bookingamount+"\","
		                		+ "\"isguestcc\": \"0\","
		                		+ "\"type\": \"CC\","
		                		+ "\"payType\":\"agent\","
		                		+ "\"chequeNo\":\"11111111111\","
		                		+ "\"transactionId\":\"2222222222\","
		                		+ "\"receiptNo\":\"3333333333\","
		                		+ "\"description\": \"Test Payment\""
		                		+ "},"
		                		+ "\"creditcard\": {"
		                		+ "\"nameoncard\": \"Test Dev\","
		                		+ "\"cardno\": \"4111111111111111\"," 
		                		+ "\"cardtype\": \"Visa\","
		                		+ "\"cvc\": \"111\","
		                		+ "\"expirymonth\": \"12\","
		                		+ "\"expiryyear\": \"2018\""
		                		+ " },"
		                		+ "\"orderId\": {"
		                		+ "\"value\": \""+orderid+"\""
		                		+ " },"
		                		+ "\"source\": {"
		                		+ "\"bookingchannel\": {"
		                		+ "\"companyName\": \"wrong company name\""
		                		+ "}"
		                		+ "},"
		                		+ "\"ignoreSourceError\": {"
		                		+"\"value\": true"
		                		+ "},"
		                		+ "\"errorIfNotConfirm\": {"
		                		+ "\"value\": 1"
		                		+ "}}}}}")
		                    .asJson();
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix=jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 JSONObject order=respons.getJSONObject("order");
			 String paidamount=order.getJSONObject("paidamount").get("amount").toString();
			 Assert.assertEquals(APIUtils.GA().fn_getAMtInDouble(bookingamount), APIUtils.GA().fn_getAMtInDouble(paidamount)," In ConfirmBooking API DepositAmount is mismatched to PaidAmount-: ");	 
			 APIUtils.GA().fn_getAMtInDouble(bookingamount);
			 String booking=order.get("bookings").toString();
			 JSONObject bookingjobj=APIUtils.GA().GetJsonObject(booking);
			 BookingID=bookingjobj.get("id").toString();
			 String rate=bookingjobj.get("rates").toString();
			 JSONObject ratesjobj=APIUtils.GA().GetJsonObject(rate);
			 String addons=ratesjobj.get("addons").toString();
			 JSONObject addonsjobj=APIUtils.GA().GetJsonObject(addons);
			 String addonid=addonsjobj.get("id").toString();
			 Assert.assertEquals(addonid, AddOnsID,"In ConfirmBooking API Addons ID is mismatched-: ");
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
		}catch(Throwable e){
			throw new APIException("ConfirmBooking api with credit card mode payment is failed", e);
		}
	}
	
	
	public void fn_ConfirmBookingWithCashPaymentMaode(String url,String wsauthkey,String orderid,String bookingamount,String AddOnsID) throws APIException{
		try{
			 HttpResponse<JsonNode> response = Unirest.post(url)
		                .header("content-type", "application/json")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("{\"hotelogix\": {"
		                		+ "\"version\": \"1.0\","
		                		+ "\"datetime\": \"2012-01-16T10:10:15\","
		                		+ "\"request\": {"
		                		+ "\"method\": \"confirmbooking\","
		                		+ "\"key\": \""+wsauthkey+"\","
		                		+ "\"languagecode\": \"en\","
		                		+ "\"data\": {"
		                		+ "\"payment\": {"
		                		+ "\"amount\": \""+bookingamount+"\","
		                		+ "\"isguestcc\": \"0\","
		                		+ "\"type\": \"cs\","
		                		+ "\"payType\":\"cash\""
		                		+ "},"
		                		+ "\"orderId\": {"
		                		+ "\"value\": \""+orderid+"\""
		                		+ " }}}}}")
		                    .asJson();
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix=jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 JSONObject order=respons.getJSONObject("order");
			 String paidamount=order.getJSONObject("paidamount").get("amount").toString();
			 Assert.assertEquals(APIUtils.GA().fn_getAMtInDouble(bookingamount), APIUtils.GA().fn_getAMtInDouble(paidamount)," In ConfirmBooking API DepositAmount is mismatched to PaidAmount-: ");	 
			 String booking=order.get("bookings").toString();
			 JSONObject bookingjobj=APIUtils.GA().GetJsonObject(booking);
			 BookingID=bookingjobj.get("id").toString();
			 String rate=bookingjobj.get("rates").toString();
			 JSONObject ratesjobj=APIUtils.GA().GetJsonObject(rate);
			 String addons=ratesjobj.get("addons").toString();
			 JSONObject addonsjobj=APIUtils.GA().GetJsonObject(addons);
			 String addonid=addonsjobj.get("id").toString();
			 Assert.assertEquals(addonid, AddOnsID,"In ConfirmBooking API Attached Addons ID is mismatched-: ");
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
		}catch(Throwable e){
			throw new APIException("ConfirmBooking api with cash mode payment is failed", e);
		}
	}
	
	public void fn_ConfirmBookingWithChequePaymentMaode(String url,String wsauthkey,String orderid,String bookingamount,String AddOnsID) throws APIException{
		try{
			 HttpResponse<JsonNode> response = Unirest.post(url)
		                .header("content-type", "application/json")
		                .header("x-ig-sg", "D_gg%fkl85_j")
		                .header("cache-control", "no-cache")
		                .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
		                .body("{\"hotelogix\": {"
		                		+ "\"version\": \"1.0\","
		                		+ "\"datetime\": \"2012-01-16T10:10:15\","
		                		+ "\"request\": {"
		                		+ "\"method\": \"confirmbooking\","
		                		+ "\"key\": \""+wsauthkey+"\","
		                		+ "\"languagecode\": \"en\","
		                		+ "\"data\": {"
		                		+ "\"payment\": {"
		                		+ "\"amount\": \""+bookingamount+"\","
		                		+ "\"isguestcc\": \"0\","
		                		+ "\"type\": \"ch\","
		                		+ "\"payType\":\"Cheque\","
		                		+ "\"transactionId\": \"TF67676\","
		                		+ "\"receiptNo\": \"R-111544\","
		                		+ "\"chequeNo\":\"C-123456\","
		                		+ "\"description\": \"test cheque\""
		                		+ "},"
		                		+ "\"orderId\": {"
		                		+ "\"value\": \""+orderid+"\""
		                		+ "}}}}}")
		                    .asJson();
			 JsonNode responseJSONString=response.getBody();
			 Assert.assertEquals(response.getCode(), 200);
			 JSONObject jobj=responseJSONString.getObject();
			 JSONObject hotelogix=jobj.getJSONObject("hotelogix");
			 JSONObject respons=hotelogix.getJSONObject("response");
			 JSONObject order=respons.getJSONObject("order");
			 String paidamount=order.getJSONObject("paidamount").get("amount").toString();
			 Assert.assertEquals(APIUtils.GA().fn_getAMtInDouble(bookingamount), APIUtils.GA().fn_getAMtInDouble(paidamount)," In ConfirmBooking API DepositAmount is mismatched to PaidAmount-: ");	 
			 String booking=order.get("bookings").toString();
			 JSONObject bookingjobj=APIUtils.GA().GetJsonObject(booking);
			 BookingID=bookingjobj.get("id").toString();
			 String rate=bookingjobj.get("rates").toString();
			 JSONObject ratesjobj=APIUtils.GA().GetJsonObject(rate);
			 String addons=ratesjobj.get("addons").toString();
			 JSONObject addonsjobj=APIUtils.GA().GetJsonObject(addons);
			 String addonid=addonsjobj.get("id").toString();
			 Assert.assertEquals(addonid, AddOnsID," In ConfirmBooking API Attached Addons ID is mismatched-: ");
			 JSONObject status= respons.getJSONObject("status");
			 Assert.assertEquals(status.getString("message"), "SUCCESS");
		}catch(Throwable e){
			throw new APIException("ConfirmBooking api with cheque mode payment is failed", e);
		}
	}
	
	
	
	public void fn_ConfirmBookingWithCC(String url,String wsauthkey,String orderid,String bookingamount) throws APIException{
        try{
             HttpResponse<JsonNode> response = Unirest.post(url)
                        .header("content-type", "application/json")
                        .header("x-ig-sg", "D_gg%fkl85_j")
                        .header("cache-control", "no-cache")
                        .header("postman-token", "586c8072-4a88-dff1-cb04-294c764db5d7")
                        .body("{\"hotelogix\": {"
                                + "\"version\": \"1.0\","
                                + "\"datetime\": \"2012-01-16T10:10:15\","
                                + "\"request\": {"
                                + "\"method\": \"confirmbooking\","
                                + "\"key\": \""+wsauthkey+"\","
                                + "\"languagecode\": \"en\","
                                + "\"data\": {"
                                + "\"payment\": {"
                                + "\"amount\": \""+bookingamount+"\","
                                + "\"isguestcc\": \"0\","
                                + "\"type\": \"CC\","
                                + "\"payType\":\"agent\","
                                + "\"chequeNo\":\"11111111111\","
                                + "\"transactionId\":\"2222222222\","
                                + "\"receiptNo\":\"3333333333\","
                                + "\"description\": \"Test Payment\""
                                + "},"
                                + "\"creditcard\": {"
                                + "\"nameoncard\": \"Test Dev\","
                                + "\"cardno\": \"4111111111111111\","
                                + "\"cardtype\": \"Visa\","
                                + "\"cvc\": \"111\","
                                + "\"expirymonth\": \"12\","
                                + "\"expiryyear\": \"2018\""
                                + " },"
                                + "\"orderId\": {"
                                + "\"value\": \""+orderid+"\""
                                + " },"
                                + "\"source\": {"
                                + "\"bookingchannel\": {"
                                + "\"companyName\": \"wrong company name\""
                                + "}"
                                + "},"
                                + "\"ignoreSourceError\": {"
                                +"\"value\": true"
                                + "},"
                                + "\"errorIfNotConfirm\": {"
                                + "\"value\": 1"
                                + "}}}}}")
                            .asJson();
             JsonNode responseJSONString=response.getBody();
             Assert.assertEquals(response.getCode(), 200);
             JSONObject jobj=responseJSONString.getObject();
             JSONObject hotelogix=jobj.getJSONObject("hotelogix");
             JSONObject respons=hotelogix.getJSONObject("response");
             JSONObject order=respons.getJSONObject("order");
             String str1=order.getJSONObject("paidamount").get("amount").toString();
            /*if(bookingamount.length()>=6){
                 Assert.assertEquals(bookingamount, str1.substring(0, str1.length()-4)," In ConfirmBooking API DepositAmount is mismatched to PaidAmount-: ");
             }else{
                 Assert.assertEquals(bookingamount, str1.split("\\.")[0]," In ConfirmBooking API DepositAmount is mismatched to PaidAmount-: ");     
             }*/
             String depositAmt=APIUtils.GA().fn_getAMtInDouble(bookingamount);
             String paidAmt=APIUtils.GA().fn_getAMtInDouble(str1);
             
             Assert.assertEquals(depositAmt.equals(paidAmt), true);
             String booking=order.get("bookings").toString();
             JSONObject bookingjobj=APIUtils.GA().GetJsonObject(booking);
             BookingID=bookingjobj.get("id").toString();
             bookinCode=bookingjobj.get("code").toString();
             //String rate=bookingjobj.get("rates").toString();
             JSONObject status= respons.getJSONObject("status");
             Assert.assertEquals(status.getString("message"), "SUCCESS");
        }catch(Throwable e){
            throw new APIException("ConfirmBooking api with credit card mode payment has failed", e);
        }
    }
	
	public void fn_ConfirmBookingAPIForGrp(String URL,String loginaccesskey,String depositAmt,String orderID) throws APIException{
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
                       		+ "\"method\": \"confirmbooking\","
                       		+ "\"key\": \""+loginaccesskey+"\","
                       		+ "\"languagecode\": \"en\","
                       		+ "\"data\": {"
                       		+ "\"payment\": {"
                       		+ "\"amount\": \""+depositAmt+"\","
                       		+ "\"isguestcc\": \"0\","
                       		+ "\"type\": \"CC\""
                       		+ "},"
                       		+ "\"creditcard\": {"
                       		+ "\"nameoncard\": \"Test Dev\","
                       		+ "\"cardno\": \"4111111111111111\","
                       		+ "\"cardtype\": \"visa\","
                       		+ "\"cvc\": \"111\","
                       		+ "\"expirymonth\": \"12\","
                       		+ "\"expiryyear\": \"2030\""
                       		+ "},"
                       		+ "\"orderId\": {"
                       		+ "\"value\": \""+orderID+"\""
                       		+ "},"
                       		+ "\"source\": {"
                       		+ "\"bookingchannel\": {"
                       		+ "\"companyName\": \"Test company name\""
                       		+ "}},"
                       		+ "\"ignoreSourceError\": {"
                       		+ "\"value\": true"
                       		+ "},"
                       		+ "\"errorIfNotConfirm\": {"
                       		+ "\"value\": 1"
                       		+ "}}}}}")
                           .asJson();
            JsonNode responseJSONString=response.getBody();
            Assert.assertEquals(response.getCode(), 200);
            JSONObject jobj=responseJSONString.getObject();
            JSONObject hotelogix=jobj.getJSONObject("hotelogix");
            JSONObject respons=hotelogix.getJSONObject("response");
            JSONObject order=respons.getJSONObject("order");
            String str1=order.getJSONObject("paidamount").get("amount").toString();
           /*if(bookingamount.length()>=6){
                Assert.assertEquals(bookingamount, str1.substring(0, str1.length()-4)," In ConfirmBooking API DepositAmount is mismatched to PaidAmount-: ");
            }else{
                Assert.assertEquals(bookingamount, str1.split("\\.")[0]," In ConfirmBooking API DepositAmount is mismatched to PaidAmount-: ");     
            }*/
            String depositamt=APIUtils.GA().fn_getAMtInDouble(depositAmt);
            String paidAmt=APIUtils.GA().fn_getAMtInDouble(str1);
            
            Assert.assertEquals(depositamt.equals(paidAmt), true);
            String booking=order.get("bookings").toString();
            JSONObject bookingjobj=APIUtils.GA().GetJsonObject(booking);
            BookingID=bookingjobj.get("id").toString();
            bookinCode=bookingjobj.get("groupcode").toString();
            //String rate=bookingjobj.get("rates").toString();
            JSONObject status= respons.getJSONObject("status");
            Assert.assertEquals(status.getString("message"), "SUCCESS");
       }catch(Throwable e){
           throw new APIException("ConfirmBooking api with credit card mode payment for group reservation has failed", e);
       }
    }
	
}
