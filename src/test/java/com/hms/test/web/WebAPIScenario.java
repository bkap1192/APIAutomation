package com.hms.test.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hms.test.RepoterAndCustomException.Reporter;
import com.hotelogix.ApiGeneric.APIUtils;
import com.hotelogix.ApiGeneric.ExcelUtils;
import com.hotelogix.web.AddToCart;
import com.hotelogix.web.AttachAddOns;
import com.hotelogix.web.BasicRateSearch;
import com.hotelogix.web.BestSuggestedRate;
import com.hotelogix.web.BookingRestrictions;
import com.hotelogix.web.CancelAPI;
import com.hotelogix.web.ClearCart;
import com.hotelogix.web.ConfirmBooking;
import com.hotelogix.web.Deletefromcart;
import com.hotelogix.web.GetAddOns;
import com.hotelogix.web.GetCountryList;
import com.hotelogix.web.GetOrder;
import com.hotelogix.web.GetPOS;
import com.hotelogix.web.GetPOSProduct;
import com.hotelogix.web.GetPayType;
import com.hotelogix.web.GetZoneList;
import com.hotelogix.web.LoadCart;
import com.hotelogix.web.ModifyAmount;
import com.hotelogix.web.OccupancyWiseRate;
import com.hotelogix.web.RateIDsForadtoCart;
import com.hotelogix.web.RoomTypeRates;
import com.hotelogix.web.SaveBooking;
import com.hotelogix.web.Search;
import com.hotelogix.web.UpdateWebBooking;
import com.hotelogix.web.Wsauth;
import com.itextpdf.text.log.SysoCounter;
import com.mashape.unirest.http.Unirest;

public class WebAPIScenario {

	//String sheet="livestable";
    //String sheet="Staygrid";
	String sheet="Dotnet";
	//String sheet="QA";
	
	
	HashMap<String, String> HM;
	String path;
	String WsauthKey;
	
	@BeforeClass
	public void fn_ValidateWsauthAPI() throws Throwable{
		try{
			String testcasenme=Thread.currentThread().getStackTrace()[1].getMethodName();
			path="TestData\\"+File.separator+"APITestData.xlsx";
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, testcasenme);
			WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"),HM.get("CRSkey"));
			}catch(Throwable e){
			throw new APIException("Wsauth api has failed", e);
		}
	}
	
	
	
	@Test(priority=1,description="Validate to get Order details by Order ID.")
	public void fn_ValidateGetOrder() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateGetOrder");
			APIUtils.GA().fn_GetCurrentDate();
			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
			GetOrder.class.newInstance().fn_GetOrder(HM.get("GetOrder_URL"), WsauthKey, SaveBooking.OrderID);
		}catch(Throwable e){
			throw new APIException("GetOrder api has Failed", e);
		}
	}
	
	
	@Test(priority=2,description="To verify creation of CRS reservation when CRS key for individual hotel has used with paymode as cash.")
	public void fn_CreateSingleHotelCRSReservationWithCashPaymentMode() throws Throwable{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateSingleHotelCRSReservationWithDiffrentPayMode");
			APIUtils.GA().fn_GetCurrentDate();
			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCashPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
		}catch(Throwable e){
			throw e;
		}
	}
	
	
	@Test(priority=3,description="To verify creation of CRS reservation when CRS key for individual hotel has used with paymode as creditcard.")
	public void fn_CreateCRSReservationWhenGroupbookinghastrueWithCashPaymentMode() throws Throwable{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateSingleHotelCRSReservationWithDiffrentPayMode");
			APIUtils.GA().fn_GetCurrentDate();
			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBookingForGroup(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
		}catch(Throwable e){
			throw e;
		}
	}
	
	@Test(priority=4,description="To verify creation of CRS reservation when CRS key for individual hotel has used with paymode as credit card.")
	public void fn_CreateCRSReservationFor2RoomWithCashPaymentMode() throws Throwable{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateSingleHotelCRSReservationWithDiffrentPayMode");
			APIUtils.GA().fn_GetCurrentDate();
			String RatesCode=Search.class.newInstance().fn_Searchfor2Rooms(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBookingForGroup(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, SaveBooking.DepositTotal,AddOnsID);
		}catch(Throwable e){
			throw e;
		}
	}
	
	@Test(priority=5,description="To verify creation of CRS reservation when CRS key for individual hotel has used with paymode as credit card.")
	public void fn_CreateSingleHotelCRSReservationWithCreditCardPaymentMode() throws Throwable{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateSingleHotelCRSReservationWithDiffrentPayMode");
			APIUtils.GA().fn_GetCurrentDate();
			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
		}catch(Throwable e){
			throw e;
		}
	}
	
	@Test(priority=6,description="To verify creation of CRS reservation when CRS key for individual hotel has used with paymode as cheque.")
	public void fn_CreateSingleHotelCRSReservationWithChequePaymentMode() throws Throwable{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateSingleHotelCRSReservationWithDiffrentPayMode");
			APIUtils.GA().fn_GetCurrentDate();
			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithChequePaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
		}catch(Throwable e){
			throw e;
		}
	}
	
	
	@Test(priority=7,description="Validate to create reservation using rateidforaddtocart for single hotel.")
	public void fn_RateIdsForadtocart() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_RateIdsForadtocart");
			APIUtils.GA().fn_GetCurrentDate();
			String rateid=RateIDsForadtoCart.class.newInstance().fn_RateIDsForadtoCart(HM.get("RateIdsForadtocart_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate,HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, rateid);
			LoadCart.class.newInstance().LoadcartAPIForrateidforaddtocart(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBookingForRateIdforaddtocartapi(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
		}catch(Throwable e){
			throw new APIException("RateIdsForadtocart API has Failed", e);
		}
	}
	
	@Test(priority=8,timeOut=35000,description="Validate to create reservation using rateidforaddtocart for group hotel.")
	public void fn_RateIdsForadtocartForGroupHotel() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_RateIdsForadtocartForGroupHotel");
			String WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("GroupHotelCRSKey"));		
			APIUtils.GA().fn_GetCurrentDate();
			String rateid=RateIDsForadtoCart.class.newInstance().fn_RateIDsForadtoCart(HM.get("RateIdsForadtocart_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate,HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, rateid);
			LoadCart.class.newInstance().LoadcartAPIForrateidforaddtocart(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBookingForRateIdforaddtocartapi(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
		}catch(Throwable e){
			throw new APIException("RateIdsForadtocartForGroupHotel API has Failed", e);
		}
	}
	
	
	@Test(priority=9,description="Validate to update guest details and preferences of the booking.")
	public void fn_ValidateUpdateWebBooking() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateUpdateWebBooking");
			APIUtils.GA().fn_GetCurrentDate();
			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
			String updatefirstname=APIUtils.GA().generateRandomString();
			UpdateWebBooking.class.newInstance().fn_UpdateWebBooking(HM.get("UpdateWebBooking_URL"), WsauthKey,ConfirmBooking.BookingID,LoadCart.GuestStaysID,updatefirstname);
		    GetOrder.class.newInstance().fn_GetOrderAndValidateRequiredFiledAfterUpadateBooking(HM.get("GetOrder_URL"), WsauthKey, SaveBooking.OrderID,updatefirstname,firstname);
		}catch(Throwable e){
			throw new APIException("UpdateWebBooking API has Failed", e);
		}
	}
	
	
	@Test(priority=10,description="Validate cancel a reservation of an order.")
	public void fn_ValidateCancel() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateCancel");
			APIUtils.GA().fn_GetCurrentDate();
			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
			String cancellationamt=APIUtils.GA().GetCancellationChargeInAmount(SaveBooking.OrderAmount, SaveBooking.CancellationCharge);
			CancelAPI.class.newInstance().fn_CancelAPI(HM.get("Cancel_URL"), WsauthKey, Bookingid, cancellationamt,SaveBooking.OrderID);
			GetOrder.class.newInstance().fn_GetOrder(HM.get("GetOrder_URL"), WsauthKey, SaveBooking.OrderID);
			Assert.assertEquals(CancelAPI.class.newInstance().StatusCode, GetOrder.class.newInstance().StatusCode);
		}catch(Throwable e){
			throw new APIException("Cancel API has Failed", e);
		}
	   }

	@Test(priority=11,description="To verify creation of CRS reservation when CRS key for Group hotel has used with paymode as credit card.")
	public void fn_CreateGroupHotelCRSReservationWithCreditCardPaymentMode() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateGroupHotelCRSReservationWithDiffrentPayMode");				
			String WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("GroupHotelCRSkey"));
			APIUtils.GA().fn_GetCurrentDate();
			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
			String firstname=APIUtils.GA().generateRandomString();
			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithChequePaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
		}catch(Throwable e){
		   throw new APIException("CreateGroupHotelCRSReservationWithCashPaymentMode scenarios has failed", e);
		}
	    }
	
	    @Test(priority=12,description="To verify creation of CRS reservation when CRS key for Travel Agent has used with paymode as credit card.")
		public void fn_CreateTACRSReservationWithCreditCardPaymentMode() throws APIException{
			try{
				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateTACRSReservationWithDiffrentPayMode");				
				String WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("TravelAgentCRSkey"));
				APIUtils.GA().fn_GetCurrentDate();
				String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
				String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
				LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
				String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
				AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
				String firstname=APIUtils.GA().generateRandomString();
				SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
				ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
			}catch(Throwable e){
			   throw new APIException("CreateTACRSReservationWithCreditCardPaymentMode scenarios has failed", e);
			}
		    }
	
	    @Test(priority=13,description="To verify creation of CRS reservation when CRS key for individual hotel has used with paymode as cash.")
		public void fn_CreateTACRSReservationWithCashPaymentMode() throws Throwable{
			try{
				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateTACRSReservationWithDiffrentPayMode");				
				String WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("TravelAgentCRSkey"));
				APIUtils.GA().fn_GetCurrentDate();
				String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
				String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
				LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
				String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
				AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
				String firstname=APIUtils.GA().generateRandomString();
				SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
				ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCashPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
			}catch(Throwable e){
				throw e;
			}
		}
	
	    @Test(priority=14,description="To verify creation of CRS reservation when CRS key of individual hotel has used with paymode as cheque.")
		public void fn_CreateTACRSReservationWithChequePaymentMode() throws Throwable{
			try{
				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateTACRSReservationWithDiffrentPayMode");				
				String WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("TravelAgentCRSkey"));
				APIUtils.GA().fn_GetCurrentDate();
				String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
				String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
				LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
				String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
				AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
				String firstname=APIUtils.GA().generateRandomString();
				SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
				ConfirmBooking.class.newInstance().fn_ConfirmBookingWithChequePaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
			}catch(Throwable e){
				throw e;
			}
		}
	
	    @Test(priority=15,description="To verify creation of Corporate CRS reservation when use corporae CRS key for individual hotel has used with paymode as cheque.")
		public void fn_CreateCorporateCRSReservationWithChequePaymentMode() throws Throwable{
			try{
				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateCorporateCRSReservationWithDiffrentPayMode");				
				String WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("CorporateCRSkey"));
				APIUtils.GA().fn_GetCurrentDate();
				String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
				String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
				LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
				String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
				AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
				String firstname=APIUtils.GA().generateRandomString();
				SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
				ConfirmBooking.class.newInstance().fn_ConfirmBookingWithChequePaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
			}catch(Throwable e){
				throw e;
			}
		}
	
	
	    @Test(priority=16,description="To verify creation of Corporate CRS reservation when use corporae CRS key for individual hotel has used with paymode as credit card.")
		public void fn_CreateCorporateCRSReservationWithCreditCardPaymentMode() throws Throwable{
			try{
				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateCorporateCRSReservationWithDiffrentPayMode");				
				String WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("CorporateCRSkey"));
				APIUtils.GA().fn_GetCurrentDate();
				String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
				String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
				LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
				String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
				AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
				String firstname=APIUtils.GA().generateRandomString();
				SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
				ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
			}catch(Throwable e){
				throw e;
			}
		}
	   
	    
	    ///////////////////////////
	   
	    @Test(priority=17,description="To verify creation of Corporate CRS reservation when use corporae CRS key for individual hotel has used with paymode as cash.")
		public void fn_CreateCorporateCRSReservationWithCashPaymentMode() throws Throwable{
			try{
				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_CreateCorporateCRSReservationWithDiffrentPayMode");				
				String WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("CorporateCRSkey"));
				APIUtils.GA().fn_GetCurrentDate();
				String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
				String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
				LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
				String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
				AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
				String firstname=APIUtils.GA().generateRandomString();
				SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
				ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCashPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
			}catch(Throwable e){
				throw e;
			}
		}
	
	@Test(priority=18,description="Validate to delete a reservation from shopping cart.")
	public void fn_ValidateDeletefromCart() throws APIException{
		try{
			 HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateDeletefromCart");
			 APIUtils.GA().fn_GetCurrentDate();
			 String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));	
			 String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			 LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			 Deletefromcart.class.newInstance().fn_DeletefromCart(HM.get("DeletefromBooking_URL"), WsauthKey,Bookingid);
			 LoadCart.class.newInstance().fn_LordCartToValidateClearCartAPI(HM.get("LoadCart_URL"), WsauthKey);
		}catch(Throwable e){
			throw new APIException("DeletefromCart API has Failed", e);
		}
	}
	
	@Test(priority=19,description="Validate to clear shopping cart.")
	public void fn_ValidateClearCart() throws APIException{
		try{
			 HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateClearCart");
			 APIUtils.GA().fn_GetCurrentDate();
			 String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));	
			 AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
			 LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
			 ClearCart.class.newInstance().fn_ClearCart(HM.get("ClearCart_URL"), WsauthKey);
			 LoadCart.class.newInstance().fn_LordCartToValidateClearCartAPI(HM.get("LoadCart_URL"), WsauthKey);
		}catch(Throwable e){
			throw new APIException("ClearCart API has Failed", e);
		}
	}
	
	
	@Test(priority=20,description="Validate to get pay types.")
	public void fn_GetPayType() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetPayType");
			GetPayType.class.newInstance().fn_GetPayType(HM.get("GetPayType_URL"), WsauthKey);
		}catch(Throwable e){
			throw new APIException("GetPayType API has Failed", e);
		}
	}
	
	@Test(priority=21,description="Validate to get the list of all countries.")
	public void fn_GetCountyList() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetCountyList");
			GetCountryList.class.newInstance().fn_GetCountryList(HM.get("GetCountyList_URL"), WsauthKey);
		}catch(Throwable e){
			throw new APIException("GetCountyList API has Failed", e);
		}
	}
	
	@Test(priority=22,description="Validate to get all zones.")
	public void fn_GetZoneList() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetZoneList");
			GetZoneList.class.newInstance().fn_GetZoneList(HM.get("GetZoneList_URL"), WsauthKey);
		}catch(Throwable e){
			throw new APIException("GetZoneList API has Failed", e);
		}
	}
	
	@Test(priority=23,description="Validate to get Occupancy wise per Day Rate.")
	public void fn_GetOccupancyWiseRate() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetOccupancyWiseRate");
			APIUtils.GA().fn_GetCurrentDate();
			OccupancyWiseRate.class.newInstance().fn_OccupancyWiseRate(HM.get("GetOccupancyWiseRate_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate);
		}catch(Throwable e){
			throw new APIException("GetOccupancyWiseRate API has Failed", e);
		}
	}
	
	
	@Test(priority=24,description="Validate to get Best Suggested Rate.")
	public void fn_BestSuggestedRate() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_BestSuggestedRate");
			APIUtils.GA().fn_GetCurrentDate();
			BestSuggestedRate.class.newInstance().fn_BestSuggestedRate(HM.get("BestSuggestedRate_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate);
		}catch(Throwable e){
			throw new APIException("BestSuggestedRate API has Failed", e);
		}
	}
	
	@Test(priority=25,description="Validate to search availability for a period and show minimum rate.")
	public void fn_BesicRateSearch() throws APIException{
		try{
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_BesicRateSearch");
			String WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("GroupHotelCRSkey"));		
			APIUtils.GA().fn_GetCurrentDate();
			BasicRateSearch.class.newInstance().fn_BasicRateSearch(HM.get("BesicRateSearch_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate,HM.get("HotelID"));
		}catch(Throwable e){
			throw new APIException("BesicRateSearch API has Failed", e);
		}
	   }
	
	    @Test(priority=26,description="Validate to get list of product of POS.")
		public void fn_GetPOSProduct() throws APIException{
			try{
				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetPOSProduct");
				GetPOS.class.newInstance().fn_GetPOS(HM.get("GetPOS_URL"), WsauthKey, HM.get("HotelID"));
				GetPOSProduct.class.newInstance().fn_GetPOSProduct(HM.get("GetPOSProduct_URL"), WsauthKey,GetPOS.POSId);
			}catch(Throwable e){
				throw new APIException("GetPOSProduct API has Failed", e);
			}
		}

	  @Test(priority=27,description="Validate to modify cart reservation amount.")
	  public void fn_ModifyCartReservationAmount() throws APIException{
		  try{
			  HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ModifyCartReservationAmount");
			  APIUtils.GA().fn_GetCurrentDate();
			  String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));	
  			  String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
  			  LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
 			  ModifyAmount.class.newInstance().fn_ModifyAmount(HM.get("ModifyAmount_URL"), WsauthKey, LoadCart.HotelID, Bookingid, "500");
		      ClearCart.class.newInstance().fn_ClearCart(HM.get("ClearCart_URL"), WsauthKey);
		  }catch(Throwable e){
			  throw new APIException("ModifyCartReservationAmount API has failed", e);
		  }	
	  }
	
	    @Test(priority=28,description="Validate to get and display all room types or rates.")
		public void fn_RoomTypeRates() throws APIException{
			try{
				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_RoomTypeRates");
				APIUtils.GA().fn_GetCurrentDate();
				RoomTypeRates.class.newInstance().fn_RoomTypeRates(HM.get("RoomTypeRates_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate);
			}catch(Throwable e){
				throw new APIException("RoomTypeRates API has Failed", e);
			}
		}
	    
		@Test(priority=29,description="Validate to get booking restrictions.")
	    public void fn_BookingRestrictions() throws APIException{
	    	try{
				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_BookingRestrictions");
				APIUtils.GA().fn_GetCurrentDate();
				BookingRestrictions.class.newInstance().fn_BookingRestrictions(HM.get("BookingRestrictions_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate);
			}catch(Throwable e){
				throw new APIException("BookingRestrictions API has Failed", e);
			}
	    }
	
	    
	    
}
