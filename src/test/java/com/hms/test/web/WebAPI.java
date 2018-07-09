package com.hms.test.web;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.hotelogix.ApiGeneric.ExcelUtils;
import com.hotelogix.web.AddToCart;
import com.hotelogix.web.AttachAddOns;
import com.hotelogix.web.BasicRateSearch;
import com.hotelogix.web.BestSuggestedRate;
import com.hotelogix.web.CancelAPI;
import com.hotelogix.web.ClearCart;
import com.hotelogix.web.ConfirmBooking;
import com.hotelogix.web.Deletefromcart;
import com.hotelogix.web.GetAddOns;
import com.hotelogix.web.GetCancellationCharge;
import com.hotelogix.web.GetCountryList;
import com.hotelogix.web.GetHotelOptions;
import com.hotelogix.web.GetOrder;
import com.hotelogix.web.GetPOS;
import com.hotelogix.web.GetPOSProduct;
import com.hotelogix.web.GetPayType;
import com.hotelogix.web.GetZoneList;
import com.hotelogix.web.LoadCart;
import com.hotelogix.web.OccupancyWiseRate;
import com.hotelogix.web.RateIDsForadtoCart;
import com.hotelogix.web.RoomTypeRates;
import com.hotelogix.web.SaveBooking;
import com.hotelogix.web.Search;
import com.hotelogix.web.UpdateWebBooking;
import com.hotelogix.web.Wsauth;

public class WebAPI {
	
	//String sheet="livestable";
    //String sheet="Staygrid";
	String sheet="Dotnet";
	
	//HashMap<String, String> HM;
	String path="E:\\Automation Framework\\HotelogixAPI\\TestData\\APITestData.xlsx";
	String WsauthKey;
	String RatesCode;
	String Bookingid;
	String AddOnsID;
	
	
	
	@BeforeClass
	public void fn_ValidateWsauthAPI() throws Throwable{
		try{
			String testcasenme=Thread.currentThread().getStackTrace()[1].getMethodName();
			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, testcasenme);
			WsauthKey=Wsauth.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"),HM.get("CRSkey"));
			}catch(Throwable e){
			throw new APIException("Wsauth api is failed", e);
		}
	}

//	@Test(description="Get Access key from wsauth validate search API")
//	public void fn_ValidateSearchAPI() throws Throwable{
//		try{
//		HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateSearchAPI");
//		APIUtils.GA().fn_GetCurrentDate();
//		RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
//		}catch(Throwable e){
//			throw new APIException("Search api is failed", e);
//		}
//	}
//	
//	@Test(priority=1,description="Get RateId from Search api and validate AddToCard API")
//	public void fn_ValidateAddToCardAPI() throws APIException{
//		try{
//			 HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateAddToCardAPI");
//			 Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
//		}catch(Throwable e){
//			throw new APIException("AddToCard API is Failed", e);
//		}
//	}
//	
//	@Test(priority=2,description="Validate to get all reservations added to shopping cart.")
//	public void fn_ValidateLoadCart() throws APIException{
//		try{
//			 HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateLoadCart");
//			 LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
//		}catch(Throwable e){
//			throw new APIException("LoadCart API is Failed", e);
//		}
//	}
//	
//	@Test(priority=3,description="Validate to get a list of add-ons.")
//	public void fn_ValidateGetAddOns() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateGetAddOns");
//			 AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
//		}catch(Throwable e){
//			throw new APIException("GetAddOns API is Failed", e);
//		}
//	}
//	
//	@Test(priority=4,description="Validate to attach add-ons to a reservation added to cart.")
//	public void fn_ValidateAttachAddOns() throws APIException{
//		try{
//			 HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateAttachAddOns");
//			 AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
//		}catch(Throwable e){
//			throw new APIException("AttachAddOns API is Failed", e);
//		}
//	}
//	
//	@Test(priority=5,description="Validate to save cart before going to Payment Gateway.")
//	public void fn_ValidateSaveBooking() throws APIException{
//		try{
//			 HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateSaveBooking");
//			 String firstname=APIUtils.GA().generateRandomString();
//			 SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
//			}catch(Throwable e){
//			throw new APIException("SaveBooking API is Failed", e);
//		}
//	}
//	
//	@Test(priority=6,description="Validate to confirm a reservation after successful payment.")
//	public void fn_ValidateConfirmBooking() throws APIException{
//		try{
//			 HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateConfirmBooking");
//			 ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
//		}catch(Throwable e){
//			throw new APIException("ConfimBooking API is Failed", e);
//		}
//	}
//	
//	@Test(priority=7,description="Validate to clear shopping cart.")
//	public void fn_ValidateClearCart() throws APIException{
//		try{
//			 HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateClearCart");
//			 APIUtils.GA().fn_GetCurrentDate();
//			 RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));	
//			 Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
//			 LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
//			 ClearCart.class.newInstance().fn_ClearCart(HM.get("ClearCart_URL"), WsauthKey);
//			 LoadCart.class.newInstance().fn_LordCartToValidateClearCartAPI(HM.get("LoadCart_URL"), WsauthKey);
//		}catch(Throwable e){
//			throw new APIException("ClearCart API is Failed", e);
//		}
//	}
//	
//	@Test(priority=8,description="Validate to delete a reservation from shopping cart.")
//	public void fn_ValidateDeletefromCart() throws APIException{
//		try{
//			 HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateDeletefromCart");
//			 APIUtils.GA().fn_GetCurrentDate();
//			 RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));	
//			 Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
//			 LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
//			 Deletefromcart.class.newInstance().fn_DeletefromCart(HM.get("DeletefromBooking_URL"), WsauthKey,Bookingid);
//		}catch(Throwable e){
//			throw new APIException("DeletefromCart API is Failed", e);
//		}
//	}
//
//	@Test(priority=9,description="Validate to update guest details and preferences of the booking.")
//	public void fn_ValidateUpdateWebBooking() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateUpdateWebBooking");
//			APIUtils.GA().fn_GetCurrentDate();
//			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
//			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
//			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
//			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
//			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
//			String firstname=APIUtils.GA().generateRandomString();
//			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
//			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
//			String updatefirstname=APIUtils.GA().generateRandomString();
//			UpdateWebBooking.class.newInstance().fn_UpdateWebBooking(HM.get("UpdateWebBooking_URL"), WsauthKey,ConfirmBooking.BookingID,LoadCart.GuestStaysID,updatefirstname);
//		   // GetOrder.class.newInstance().fn_GetOrderAndValidateRequiredFiledAfterUpadateBooking(HM.get("GetOrder_URL"), WsauthKey, SaveBooking.OrderID,updatefirstname,firstname);
//		}catch(Throwable e){
//			throw new APIException("UpdateWebBooking is Failed", e);
//		}
//	}
//	
//	
//	@Test(priority=10,description="Validate ValidateGetHotelOptions API")
//	public void fn_ValidateGetHotelOptions() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateGetHotelOptions");
//			GetHotelOptions.class.newInstance().fn_GetHotelOptions(HM.get("GetHotelOptions_URL"), WsauthKey);
//			}catch(Throwable e){
//			throw new APIException("GetHotelOptions is Failed", e);
//		}
//	}
//	
//
//	@Test(priority=11,description="Validate to get Order details by Order ID.")
//	public void fn_ValidateGetOrder() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateGetOrder");
//			APIUtils.GA().fn_GetCurrentDate();
//			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
//			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
//			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
//			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
//			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
//			String firstname=APIUtils.GA().generateRandomString();
//			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
//			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
//			//GetOrder.class.newInstance().fn_GetOrder(HM.get("GetOrder_URL"), WsauthKey, SaveBooking.OrderID);
//		}catch(Throwable e){
//			throw new APIException("GetOrder api is Failed", e);
//		}
//	}
//
//	
//	//@Test(priority=12,description="Validate to get cancellation charge of a reservation.")
//	public void fn_ValidateGetCancellationCharge() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateGetCancellationCharge");
//			APIUtils.GA().fn_GetCurrentDate();
//			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
//			System.out.println("Search");
//			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
//			System.out.println("Addtocart");
//			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
//			System.out.println("LoadCart");
//			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
//			System.out.println("GetAddons");
//			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
//			System.out.println("AttachAddOns");
//			String firstname=APIUtils.GA().generateRandomString();
//			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
//			System.out.println("SaveBooking");
//			GetCancellationCharge.class.newInstance().fn_GetCencellationCharge(HM.get("GetCancellationCharge_URL"), WsauthKey, SaveBooking.OrderID, Bookingid);
//			System.out.println("GetCencellationCharge");
////			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
////			System.out.println("ConfirmBookingWithCreditCardPaymentMaode");
//		}catch(Throwable e){
//			throw new APIException("GetCancellationCharge api is Failed", e);
//		}
//	}
//	
//	
//	@Test(priority=13,description="Validate cancel a reservation of an order.")
//	public void fn_ValidateCancel() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateCancel");
//			APIUtils.GA().fn_GetCurrentDate();
//			String RatesCode=Search.class.newInstance().fn_Search(HM.get("Search_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate, HM.get("HotelID"));
//			String Bookingid=AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCard_URL"), WsauthKey, RatesCode);
//			LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), WsauthKey);
//			String AddOnsID=GetAddOns.class.newInstance().fn_GetAddOns(HM.get("GetAddOn_URL"), WsauthKey,Bookingid);
//			AttachAddOns.class.newInstance().fn_AttachAddOns(HM.get("AttachAddOns_URL"), WsauthKey,Bookingid,AddOnsID);
//			String firstname=APIUtils.GA().generateRandomString();
//			SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), WsauthKey, firstname, firstname);
//			ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
//			String cancellationamt=APIUtils.GA().GetCancellationChargeInAmount(SaveBooking.OrderAmount, SaveBooking.CancellationCharge);
//			CancelAPI.class.newInstance().fn_CancelAPI(HM.get("Cancel_URL"), WsauthKey, Bookingid, cancellationamt,SaveBooking.OrderID);
//			//GetOrder.class.newInstance().fn_GetOrder(HM.get("GetOrder_URL"), WsauthKey, SaveBooking.OrderID);
//			//Assert.assertEquals(CancelAPI.class.newInstance().StatusCode, GetOrder.class.newInstance().StatusCode);
//		}catch(Throwable e){
//			throw new APIException("Cancel API is Failed", e);
//		}
//	}
//	
//	@Test(priority=14,description="Validate to get pay types.")
//	public void fn_GetPayType() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetPayType");
//			GetPayType.class.newInstance().fn_GetPayType(HM.get("GetPayType_URL"), WsauthKey);
//		}catch(Throwable e){
//			throw new APIException("GetPayType API is Failed", e);
//		}
//	}
//	
//	@Test(priority=15,description="Validate to get the list of all countries.")
//	public void fn_GetCountyList() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetCountyList");
//			GetCountryList.class.newInstance().fn_GetCountryList(HM.get("GetCountyList_URL"), WsauthKey);
//		}catch(Throwable e){
//			throw new APIException("GetCountyList API is Failed", e);
//		}
//	}
//	
//	@Test(priority=16,description="Validate to get all zones.")
//	public void fn_GetZoneList() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetZoneList");
//			GetZoneList.class.newInstance().fn_GetZoneList(HM.get("GetZoneList_URL"), WsauthKey);
//		}catch(Throwable e){
//			throw new APIException("GetZoneList API is Failed", e);
//		}
//	}
//	
//	@Test(priority=17,description="Validate to get Occupancy wise per Day Rate.")
//	public void fn_GetOccupancyWiseRate() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetOccupancyWiseRate");
//			APIUtils.GA().fn_GetCurrentDate();
//			OccupancyWiseRate.class.newInstance().fn_OccupancyWiseRate(HM.get("GetOccupancyWiseRate_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate);
//		}catch(Throwable e){
//			throw new APIException("GetOccupancyWiseRate API is Failed", e);
//		}
//	}
//	
//	@Test(priority=18,description="Validate to Get Only Rate Ids for add to cart.")
//	public void fn_RateIdsForadtocart() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_RateIdsForadtocart");
//			APIUtils.GA().fn_GetCurrentDate();
//			RateIDsForadtoCart.class.newInstance().fn_RateIDsForadtoCart(HM.get("RateIdsForadtocart_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate,HM.get("HotelID"));
//		}catch(Throwable e){
//			throw new APIException("RateIdsForadtocart API is Failed", e);
//		}
//	}
//	
//	@Test(priority=19,description="Validate to get and display all room types or rates.")
//	public void fn_RoomTypeRates() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_RoomTypeRates");
//			APIUtils.GA().fn_GetCurrentDate();
//			RoomTypeRates.class.newInstance().fn_RoomTypeRates(HM.get("RoomTypeRates_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate);
//		}catch(Throwable e){
//			throw new APIException("RoomTypeRates API is Failed", e);
//		}
//	}
//	
//	@Test(priority=20,description="Validate to get Best Suggested Rate.")
//	public void fn_BestSuggestedRate() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_BestSuggestedRate");
//			APIUtils.GA().fn_GetCurrentDate();
//			BestSuggestedRate.class.newInstance().fn_BestSuggestedRate(HM.get("BestSuggestedRate_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate);
//		}catch(Throwable e){
//			throw new APIException("BestSuggestedRate API is Failed", e);
//		}
//	}
//	
//	@Test(priority=21,description="Validate to search availability for a period and show minimum rate.")
//	public void fn_BesicRateSearch() throws APIException{
//		try{
//			HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_BesicRateSearch");
//			APIUtils.GA().fn_GetCurrentDate();
//			BasicRateSearch.class.newInstance().fn_BasicRateSearch(HM.get("BesicRateSearch_URL"), WsauthKey,APIUtils.GA().fromdate,APIUtils.GA().todate,HM.get("HotelID"));
//		}catch(Throwable e){
//			throw new APIException("BesicRateSearch API is Failed", e);
//		}
//	    }
//	
//	   @Test(priority=22,description="Validate to get list of pos of hotel.")
//		public void fn_GetPOS() throws APIException{
//			try{
//				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetPOS");
//				GetPOS.class.newInstance().fn_GetPOS(HM.get("GetPOS_URL"), WsauthKey, HM.get("HotelID"));
//			}catch(Throwable e){
//				throw new APIException("GetPOS API is Failed", e);
//			}
//		}
//	
//	   @Test(priority=23,description="Validate to get list of product of pos.")
//		public void fn_GetPOSProduct() throws APIException{
//			try{
//				HashMap<String, String> HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GetPOSProduct");
//				GetPOS.class.newInstance().fn_GetPOS(HM.get("GetPOS_URL"), WsauthKey, HM.get("HotelID"));
//				GetPOSProduct.class.newInstance().fn_GetPOSProduct(HM.get("GetPOSProduct_URL"), WsauthKey,GetPOS.POSId);
//			}catch(Throwable e){
//				throw new APIException("GetPOSProduct API is Failed", e);
//			}
//		}
	
	   
	    //@Test(priority=24,description="To verify creation of CRS reservation when CRS key for Group hotel is used with paymode as credit card.")
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
				ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCreditCardPaymentMaode(HM.get("ConfirmBooking_URL"), WsauthKey, SaveBooking.OrderID, LoadCart.DepositAmount,AddOnsID);
			}catch(Throwable e){
			   throw new APIException("CreateGroupHotelCRSReservationWithCreditCardPaymentMode scenarios is failed", e);
			}
		    }
	
	   
	   
	   // @Test(priority=25,description="To verify creation of CRS reservation when CRS key for Travel Agent is used with paymode as credit card.")
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
			   throw new APIException("CreateTACRSReservationWithCreditCardPaymentMode scenarios is failed", e);
			}
		    }
	   
	   
	   
	   
	   
	   
	
}
