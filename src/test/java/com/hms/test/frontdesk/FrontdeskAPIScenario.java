package com.hms.test.frontdesk;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.hotelogix.ApiGeneric.ExcelUtils;
import com.hotelogix.admin.CreateUser;
import com.hotelogix.admin.GetCounters;
import com.hotelogix.admin.GetUserTypes;
import com.hotelogix.frontdesk.AddDNR;
import com.hotelogix.frontdesk.CheckNightAuditStatus;
import com.hotelogix.frontdesk.DeleteDNR;
import com.hotelogix.frontdesk.GetBookings;
import com.hotelogix.frontdesk.GetDNR;
import com.hotelogix.frontdesk.GetHotelData;
import com.hotelogix.frontdesk.GetHouseStatus;
import com.hotelogix.frontdesk.GetUsers;
import com.hotelogix.frontdesk.LoginFD;
import com.hotelogix.frontdesk.LogoutFD;
import com.hotelogix.frontdesk.WsauthFD;
import com.hotelogix.web.AddToCart;
import com.hotelogix.web.ConfirmBooking;
import com.hotelogix.web.LoadCart;
import com.hotelogix.web.SaveBooking;
import com.hotelogix.web.Search;

public class FrontdeskAPIScenario {

	
	
	HashMap<String, String> HM;
	private String WorkbookPath="E:\\APIFramework\\HotelogixAPI\\TestData\\FrontdeskAPI.xlsx";
	
	String sheet="Livestable";
	//String sheet="DotNet";
	String wsauthKey;
	String counterID;
	String loginAccessKey;
	String currentDate;
	
	
	@BeforeClass
	public void fn_Wsauth_GetCounters_LoginAPI() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		wsauthKey=WsauthFD.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("CRSKey"));
        GetCounters.class.newInstance().fn_GetCounters(HM.get("GetCounters_URL"), wsauthKey, HM.get("HotelID"),HM.get("CounterName"));
		loginAccessKey=LoginFD.class.newInstance().fn_loginFD(HM.get("FDLogin_URL"),HM.get("HotelID"), GetCounters.counterID, HM.get("Email"),HM.get("CRSKey"));

		}catch(Throwable e){
			throw e;
		}
	}
	
		
	
	//@Test(priority=1,description="To verify whether response of'gethoteldata' API displays hotel data or not")
	public void fn_validateGetHotelData() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String actResponse=GetHotelData.class.newInstance().fn_GetHotelData(HM.get("GetHotelData_URL"), loginAccessKey);
        Assert.assertEquals(actResponse.equals(currentDate), true);
		}catch(Throwable e){
			throw new APIException("GetHotelData API has failed", e);
		}
	}
	
	
	//@Test(priority=2,description="To verify whether 'getusers' API displays users that has been added under a particular user level using 'getusertypes' and 'createuser' API.")
	public void fn_validateGetUsersAPI() throws Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		GetUserTypes.class.newInstance().fn_GetUserTypesAPI(HM.get("GetUserType_URL"), wsauthKey, HM.get("HotelID"));
        CreateUser.class.newInstance().fn_CreateUserAPI(HM.get("CreateUser_URL"), wsauthKey, GetCounters.counterName,GetUserTypes.userTypeTitle, HM.get("Email"), HM.get("Password"), HM.get("HotelID"));
        GetUsers.class.newInstance().fn_GetUsersAPI(HM.get("GetUsers_URL"), wsauthKey, HM.get("HotelID"), CreateUser.fname, GetCounters.counterID,GetCounters.counterName );
		}catch(Throwable e){
			throw new APIException("GetUser API has failed", e);
		}
	}
	
	
	//@Test(priority=3,description="Addition of DNR using 'adddnr'in unassigned rooms using 'gethousestatus' API and verification of it using 'getdnr' API.")
	public void fn_validateGetDNRAPI() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
        GetHouseStatus.class.newInstance().fn_GetHouseStatusAPI(HM.get("GetHouseStatus_URL"), loginAccessKey, currentDate, HM.get("HotelID"));
	    AddDNR.class.newInstance().fn_AddDNRAPI(HM.get("AddDNR_URL"), loginAccessKey,GetHouseStatus.uaroomID, currentDate, GetHouseStatus.rtid);
	    GetDNR.class.newInstance().fn_GetDNRAPI(HM.get("GetDNR_URL"), loginAccessKey, currentDate, AddDNR.dnrID, GetHouseStatus.uaroomID, GetHouseStatus.rtid, currentDate,HM.get("HotelID"));
		DeleteDNR.class.newInstance().fn_deleteDNRAPI(HM.get("DeleteDNR_URL"), loginAccessKey, AddDNR.dnrID);
		
		}catch(Throwable e){
		 throw new APIException("GetDNR API has failed", e);
		}
	}
	
	
	
	//@Test(priority=4,description="Creation of a reservation on frontdesk and verifying whether 'getbookings' API displays created reservation or not.")
	public void fn_validateGetBookingsAPI() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCC(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.OrderID, LoadCart.DepositAmount);
	    GetBookings.class.newInstance().fn_GetBookingsAPI(HM.get("GetBookings_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), LoadCart.BookingID, HM.get("HotelID"), SaveBooking.OrderID);
		}catch(Throwable e){
			throw new APIException("GetBookings API has failed", e);
		}
	}
	
	
	
	
	@AfterClass
	public void fn_LogoutAPI() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
        LogoutFD.class.newInstance().fn_LogoutFDAPI(HM.get("FDLogout_URL"),loginAccessKey);
		}catch(Throwable e){
			throw new APIException("Logout API has failed", e);
		}
	}
}
