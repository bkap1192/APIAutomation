package com.hms.test.frontdesk;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.hotelogix.ApiGeneric.ExcelUtils;
import com.hotelogix.admin.CreateUser;
import com.hotelogix.admin.GetCounters;
import com.hotelogix.admin.GetUserTypes;
import com.hotelogix.frontdesk.AddDNR;
import com.hotelogix.frontdesk.AssignRoom;
import com.hotelogix.frontdesk.AttachGuest;
import com.hotelogix.frontdesk.ChangeBookingStay;
import com.hotelogix.frontdesk.ChangeGroupStay;
import com.hotelogix.frontdesk.CheckNightAuditStatus;
import com.hotelogix.frontdesk.CommitEditBooking;
import com.hotelogix.frontdesk.DeleteDNR;
import com.hotelogix.frontdesk.EditBooking;
import com.hotelogix.frontdesk.GetBooking;
import com.hotelogix.frontdesk.GetBookingSearch;
import com.hotelogix.frontdesk.GetBookings;
import com.hotelogix.frontdesk.GetDNR;
import com.hotelogix.frontdesk.GetGroup;
import com.hotelogix.frontdesk.GetHotelData;
import com.hotelogix.frontdesk.GetHouseStatus;
import com.hotelogix.frontdesk.GetRoomsToAssign;
import com.hotelogix.frontdesk.GetUsers;
import com.hotelogix.frontdesk.LoginFD;
import com.hotelogix.frontdesk.LogoutFD;
import com.hotelogix.frontdesk.MoveBooking;
import com.hotelogix.frontdesk.SplitBooking;
import com.hotelogix.frontdesk.UnassignRoom;
import com.hotelogix.frontdesk.WsauthFD;
import com.hotelogix.web.AddToCart;
import com.hotelogix.web.ConfirmBooking;
import com.hotelogix.web.LoadCart;
import com.hotelogix.web.SaveBooking;
import com.hotelogix.web.Search;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.steadystate.css.util.ThrowCssExceptionErrorHandler;

public class FrontdeskAPITestCase {

	HashMap<String, String> HM;
	private String WorkbookPath="E:\\APIFramework\\HotelogixAPI\\TestData\\FrontdeskAPI.xlsx";
	
	
	String sheet="Livestable";
	//String sheet="DotNet";
	//String sheet="QA";
	
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
	
		
	//@Test(priority=1)
	public void fn_validateCheckNAStatusAPI() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		}catch(Throwable e){
			throw new APIException("CheckNightAuditStatus API has failed", e);
		}
	}
	
	
	//@Test(priority=2)
	public void fn_validateGetHotelData() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
        String actResponse=GetHotelData.class.newInstance().fn_GetHotelData(HM.get("GetHotelData_URL"), loginAccessKey);
        Assert.assertEquals(actResponse.equals(currentDate), true);
		}catch(Throwable e){
			throw new APIException("GetHotelData API has failed", e);
		}
	}
	
	
	//@Test(priority=3)
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
	
	
	//@Test(priority=4)
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
	
	
	//@Test(priority=5)
	public void fn_validateGetHouseStatusAPI() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		GetHouseStatus.class.newInstance().fn_GetHouseStatusAPI(HM.get("GetHouseStatus_URL"), loginAccessKey, currentDate, HM.get("HotelID"));
		}catch(Throwable e){
			throw new APIException("GetHouseStatus API has failed", e);
		}
	}
	
    
	//@Test(priority=6)
	public void fn_validateGetBookingsAPI() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		String wsauthKey=WsauthFD.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"), HM.get("CRSKey"));
        GetCounters.class.newInstance().fn_GetCounters(HM.get("GetCounters_URL"), wsauthKey, HM.get("HotelID"),HM.get("CounterName"));
		String loginAccessKey=LoginFD.class.newInstance().fn_loginFD(HM.get("FDLogin_URL"),HM.get("HotelID"), GetCounters.counterID, HM.get("Email"),HM.get("CRSKey"));				
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCC(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.OrderID, LoadCart.DepositAmount);
	    GetBookings.class.newInstance().fn_GetBookingsAPI(HM.get("GetBookings_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), LoadCart.BookingID, HM.get("HotelID"), SaveBooking.OrderID);
        LogoutFD.class.newInstance().fn_LogoutFDAPI(HM.get("FDLogout_URL"),loginAccessKey);
		
		}catch(Throwable e){
			throw new APIException("GetBookings API has failed", e);
		}
	}
	
	
	//@Test(priority=7)
	public void fn_validateSplitBookingAPIForSingleReserv() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,2), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCC(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.OrderID, LoadCart.DepositAmount);      
		GetBooking.class.newInstance().fn_GetBookingAPI(HM.get("GetBooking_URL"), loginAccessKey, HM.get("HotelID"), ConfirmBooking.bookinCode);
		EditBooking.class.newInstance().fn_EditBookingAPI(HM.get("EditBooking_URL"), loginAccessKey, "S", GetBooking.bookingID, SaveBooking.OrderID);
		GetRoomsToAssign.class.newInstance().fn_GetRoomsToAssignAPI(HM.get("GetRoomsToAssign_URL"), loginAccessKey, EditBooking.editBkngID, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate, 2));				
		String frmDate1=APIUtils.GA().fn_setFrmToDate(currentDate, 1);
		String toDate1=APIUtils.GA().fn_setFrmToDate(frmDate1, 1);
		SplitBooking.class.newInstance().fn_SplitBookingsAPI(HM.get("SplitBooking_URL"), loginAccessKey, EditBooking.editBkngID, GetRoomsToAssign.roomTypeID, frmDate1, toDate1, SaveBooking.OrderID);
		CommitEditBooking.class.newInstance().fn_CommitEditBookingAPI(HM.get("CommitEditBooking_URL"), loginAccessKey, "S", EditBooking.editBkngID);		
		}catch(Throwable e){
			throw new APIException("SplitBooking API for single reservation has failed", e);
		}				
	}
	
	
	
	//@Test(priority=8)
	public void fn_validateSplitBookingAPIForGrpReservation() throws InstantiationException, IllegalAccessException, Exception, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,2), HM.get("HotelID"),"2");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBookingAPIForGrp(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingAPIForGrp(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.depositAmt, SaveBooking.OrderID);
		GetGroup.class.newInstance().fn_GetGroupAPI(HM.get("GetGroup_URL"), loginAccessKey, ConfirmBooking.bookinCode,SaveBooking.OrderID);
		EditBooking.class.newInstance().fn_EditBookingAPI(HM.get("EditBooking_URL"), loginAccessKey, "G", GetGroup.grpID, SaveBooking.OrderID);
		String frmDate1=APIUtils.GA().fn_setFrmToDate(currentDate, 1);
		String toDate1=APIUtils.GA().fn_setFrmToDate(frmDate1, 1);
		GetRoomsToAssign.class.newInstance().fn_GetRoomsToAssignAPI(HM.get("GetRoomsToAssign_URL"), loginAccessKey, EditBooking.editBkngID, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,2));
		SplitBooking.class.newInstance().fn_SplitBookingsAPI(HM.get("SplitBooking_URL"), loginAccessKey, EditBooking.editBkngID, GetRoomsToAssign.roomTypeID, frmDate1, toDate1, SaveBooking.OrderID);
		CommitEditBooking.class.newInstance().fn_CommitEditBookingAPI(HM.get("CommitEditBooking_URL"), loginAccessKey, "G", GetGroup.grpID);
		}catch(Throwable e){
			throw new APIException("fn_validateSplitBookingAPIForGrpReservation has failed", e);
		}		
	}
	
	
	
	//@Test(priority=9)
	public void fn_validateAssignRoomForSingleReserv() throws Exception, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCC(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.OrderID, LoadCart.DepositAmount);      
		GetBooking.class.newInstance().fn_GetBookingAPI(HM.get("GetBooking_URL"), loginAccessKey, HM.get("HotelID"), ConfirmBooking.bookinCode);
		EditBooking.class.newInstance().fn_EditBookingAPI(HM.get("EditBooking_URL"), loginAccessKey, "S", GetBooking.bookingID, SaveBooking.OrderID);
		GetRoomsToAssign.class.newInstance().fn_GetRoomsToAssignAPI(HM.get("GetRoomsToAssign_URL"), loginAccessKey, EditBooking.editBkngID, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate, 1));				
        AssignRoom.class.newInstance().fn_AssignRoomAPI(HM.get("AssignRoom_URL"), loginAccessKey, EditBooking.editBkngID, GetRoomsToAssign.roomID, SaveBooking.OrderID, GetRoomsToAssign.roomTypeID);
		}catch(Throwable e){
			throw new APIException("fn_validateAssignRoomForSingleReserv has failed", e);
		}        
	}
	
	
	
	//@Test(priority=10)
	public void fn_validateAssignRoomForGrpReservation() throws Exception, Throwable{
		try{		
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBookingAPIForGrp(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingAPIForGrp(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.depositAmt, SaveBooking.OrderID);
		GetGroup.class.newInstance().fn_GetGroupAPI(HM.get("GetGroup_URL"), loginAccessKey, ConfirmBooking.bookinCode,SaveBooking.OrderID);
		EditBooking.class.newInstance().fn_EditBookingAPI(HM.get("EditBooking_URL"), loginAccessKey, "G", GetGroup.grpID, SaveBooking.OrderID);
		GetRoomsToAssign.class.newInstance().fn_GetRoomsToAssignAPI(HM.get("GetRoomsToAssign_URL"), loginAccessKey, EditBooking.editBkngID, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate, 1));				
        AssignRoom.class.newInstance().fn_AssignRoomAPI(HM.get("AssignRoom_URL"), loginAccessKey, EditBooking.editBkngID, GetRoomsToAssign.roomID, SaveBooking.OrderID, GetRoomsToAssign.roomTypeID);
		}catch(Throwable e){
			throw new APIException("fn_validateAssignRoomForGrpReservation", e);
		}				
	}
	
	
	//@Test(priority=11)
	public void fn_validateUnassignRoomForSingleReserv() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCC(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.OrderID, LoadCart.DepositAmount);      
		GetBooking.class.newInstance().fn_GetBookingAPI(HM.get("GetBooking_URL"), loginAccessKey, HM.get("HotelID"), ConfirmBooking.bookinCode);
		EditBooking.class.newInstance().fn_EditBookingAPI(HM.get("EditBooking_URL"), loginAccessKey, "S", GetBooking.bookingID, SaveBooking.OrderID);
		GetRoomsToAssign.class.newInstance().fn_GetRoomsToAssignAPI(HM.get("GetRoomsToAssign_URL"), loginAccessKey, EditBooking.editBkngID, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate, 1));				
        AssignRoom.class.newInstance().fn_AssignRoomAPI(HM.get("AssignRoom_URL"), loginAccessKey, EditBooking.editBkngID, GetRoomsToAssign.roomID, SaveBooking.OrderID, GetRoomsToAssign.roomTypeID);
		CommitEditBooking.class.newInstance().fn_CommitEditBookingAPI(HM.get("CommitEditBooking_URL"), loginAccessKey, "S", EditBooking.editBkngID);		
        GetBooking.class.newInstance().fn_GetBookingAPI(HM.get("GetBooking_URL"), loginAccessKey, HM.get("HotelID"), ConfirmBooking.bookinCode);
		EditBooking.class.newInstance().fn_EditBookingAPI(HM.get("EditBooking_URL"), loginAccessKey, "S", GetBooking.bookingID, SaveBooking.OrderID);
        UnassignRoom.class.newInstance().fn_UnassignRoom(HM.get("UnassignRoom_URL"), loginAccessKey, EditBooking.editBkngID);
		}catch(Throwable e){
			throw new APIException("fn_validateUnassignRoomForSingleReserv has failed", e);
		}				
	}
	
	
	//@Test(priority=12)
	public void fn_validateUnassignRoomForGrpReserv() throws InstantiationException, IllegalAccessException, Throwable{
	try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBookingAPIForGrp(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingAPIForGrp(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.depositAmt, SaveBooking.OrderID);
		GetGroup.class.newInstance().fn_GetGroupAPI(HM.get("GetGroup_URL"), loginAccessKey, ConfirmBooking.bookinCode,SaveBooking.OrderID);
		EditBooking.class.newInstance().fn_EditBookingAPI(HM.get("EditBooking_URL"), loginAccessKey, "G", GetGroup.grpID, SaveBooking.OrderID);
		GetRoomsToAssign.class.newInstance().fn_GetRoomsToAssignAPI(HM.get("GetRoomsToAssign_URL"), loginAccessKey, EditBooking.editBkngID, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate, 1));				
        AssignRoom.class.newInstance().fn_AssignRoomAPI(HM.get("AssignRoom_URL"), loginAccessKey, EditBooking.editBkngID, GetRoomsToAssign.roomID, SaveBooking.OrderID, GetRoomsToAssign.roomTypeID);
		CommitEditBooking.class.newInstance().fn_CommitEditBookingAPI(HM.get("CommitEditBooking_URL"), loginAccessKey, "G", GetGroup.grpID);
		GetGroup.class.newInstance().fn_GetGroupAPI(HM.get("GetGroup_URL"), loginAccessKey, ConfirmBooking.bookinCode,SaveBooking.OrderID);
		EditBooking.class.newInstance().fn_EditBookingAPI(HM.get("EditBooking_URL"), loginAccessKey, "G", GetGroup.grpID, SaveBooking.OrderID);
        UnassignRoom.class.newInstance().fn_UnassignRoom(HM.get("UnassignRoom_URL"), loginAccessKey, EditBooking.editBkngID);
	}catch(Throwable e){
		throw new APIException("fn_validateUnassignRoomForGrpReserv has failed", e);
	}	
	}
	
	
	
	//@Test(priority=13)
	public void fn_validateChangeGroupStayAPI() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBookingAPIForGrp(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingAPIForGrp(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.depositAmt, SaveBooking.OrderID);
		GetGroup.class.newInstance().fn_GetGroupAPI(HM.get("GetGroup_URL"), loginAccessKey, ConfirmBooking.bookinCode,SaveBooking.OrderID);
		EditBooking.class.newInstance().fn_EditBookingAPI(HM.get("EditBooking_URL"), loginAccessKey, "G", GetGroup.grpID, SaveBooking.OrderID);
        String toDate1=APIUtils.GA().fn_setFrmToDate(currentDate, 2);
		ChangeGroupStay.class.newInstance().fn_ChangeGroupStay(HM.get("ChangeGroupStay_URL"), loginAccessKey, currentDate, toDate1, GetGroup.grpID);
		CommitEditBooking.class.newInstance().fn_CommitEditBookingAPI(HM.get("CommitEditBooking_URL"), loginAccessKey, "G", GetGroup.grpID);
		}catch(Throwable e){
			throw new APIException("fn_validateChangeGroupStayAPI has failed", e);
		}	
	}
	
	
	//@Test(priority=14)
	public void fn_validateChangeBookingStayAPI() throws InstantiationException, IllegalAccessException, Exception, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCC(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.OrderID, LoadCart.DepositAmount);      
		GetBooking.class.newInstance().fn_GetBookingAPI(HM.get("GetBooking_URL"), loginAccessKey, HM.get("HotelID"), ConfirmBooking.bookinCode);
		EditBooking.class.newInstance().fn_EditBookingAPI(HM.get("EditBooking_URL"), loginAccessKey, "S", GetBooking.bookingID, SaveBooking.OrderID);
        String toDate1=APIUtils.GA().fn_setFrmToDate(currentDate, 2);
		ChangeBookingStay.class.newInstance().fn_ChangeBookingStay(HM.get("ChangeBookingStay_URL"), loginAccessKey, EditBooking.editBkngID, currentDate, toDate1);
		}catch(Throwable e){
			throw new APIException("fn_validateChangeBookingStay has failed", e);
		}
	}
	
	
	
	//@Test(priority=15)
	public void fn_validateMoveBookingAPI() throws Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCC(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.OrderID, LoadCart.DepositAmount);      
		GetBooking.class.newInstance().fn_GetBookingAPI(HM.get("GetBooking_URL"), loginAccessKey, HM.get("HotelID"), ConfirmBooking.bookinCode);
        GetHouseStatus.class.newInstance().fn_GetHouseStatusAPI(HM.get("GetHouseStatus_URL"), loginAccessKey, currentDate, HM.get("HotelID"));
	    MoveBooking.class.newInstance().fn_MoveBookingAPI(HM.get("MoveBooking_URL"), loginAccessKey,LoadCart.BookingID, GetHouseStatus.rtid, SaveBooking.OrderID,GetHouseStatus.uaroomID);
		}catch(Throwable e){
			throw new APIException("fn_validateMoveBookingAPI has failed", e);
		}
	}
	
	
	//@Test(priority=16)
	public void fn_validateAttachGuestAPI() throws InstantiationException, IllegalAccessException, Throwable{
		try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCC(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.OrderID, LoadCart.DepositAmount);      
		GetBooking.class.newInstance().fn_GetBookingAPI(HM.get("GetBooking_URL"), loginAccessKey, HM.get("HotelID"), ConfirmBooking.bookinCode);
		EditBooking.class.newInstance().fn_extractGuestID(HM.get("EditBooking_URL"), loginAccessKey, "S", GetBooking.bookingID, SaveBooking.OrderID);
        AttachGuest.class.newInstance().fn_AttachGuestAPI(HM.get("AttachGuest_URL"), loginAccessKey, EditBooking.editBkngID, EditBooking.newGuestID, APIUtils.generateRandomString(), APIUtils.generateRandomString());
		CommitEditBooking.class.newInstance().fn_CommitEditBookingAPI(HM.get("CommitEditBooking_URL"), loginAccessKey, "S", EditBooking.editBkngID);				
		}catch(Throwable e){
			throw new APIException("fn_validateAttachGuestAPI has failed", e);
		}       
	}
	
	
	//@Test(priority=17)
	public void fn_validateGetBookingSearchAPI() throws Throwable{
	try{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCC(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.OrderID, LoadCart.DepositAmount);      
        String toDate=APIUtils.GA().fn_setFrmToDate(currentDate, 1);
		GetBookingSearch.class.newInstance().fn_GetBookingSearchAPI(HM.get("GetBookingSearch_URL"), loginAccessKey, currentDate, toDate, ConfirmBooking.bookinCode);
	}catch(Throwable e){
		throw new APIException("fn_validateGetBookingSearchAPI has failed", e);
	}
		
	}
	
	
	@Test(priority=18)
	public void fn_GetAccStatementForSingleReservation() throws InstantiationException, IllegalAccessException, Exception, Throwable{
		String testcasename=Thread.currentThread().getStackTrace()[1].getMethodName();
		HM=ExcelUtils.UI().getTestCaseDataMap(WorkbookPath, sheet, testcasename);
		currentDate=CheckNightAuditStatus.class.newInstance().fn_CheckNAStatus(HM.get("CheckNAStatus_URL"), loginAccessKey, HM.get("NightAudit_Msg"));	
		String rateID=Search.class.newInstance().fn_Search(HM.get("Search_URL"), loginAccessKey, currentDate, APIUtils.GA().fn_setFrmToDate(currentDate,1), HM.get("HotelID"),"1");
		AddToCart.class.newInstance().AddToCardAPI(HM.get("AddToCart_URL"), loginAccessKey, rateID);
		LoadCart.class.newInstance().LoadcartAPI(HM.get("LoadCart_URL"), loginAccessKey);
		SaveBooking.class.newInstance().fn_SaveBooking(HM.get("SaveBooking_URL"), loginAccessKey, APIUtils.generateRandomString(), APIUtils.generateRandomString()+"@stayezee.com", LoadCart.HotelID, LoadCart.BookingID, LoadCart.GuestStaysID);
		ConfirmBooking.class.newInstance().fn_ConfirmBookingWithCC(HM.get("ConfirmBooking_URL"), loginAccessKey, SaveBooking.OrderID, LoadCart.DepositAmount);      

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
