package com.hms.test.admin;

import java.io.File;
import java.util.HashMap;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hms.test.RepoterAndCustomException.APIException;
import com.hotelogix.ApiGeneric.APIUtils;
import com.hotelogix.ApiGeneric.ExcelUtils;
import com.hotelogix.admin.CreateAgent;
import com.hotelogix.admin.CreateCorporate;
import com.hotelogix.admin.CreateUser;
import com.hotelogix.admin.EditAgent;
import com.hotelogix.admin.EditCorporate;
import com.hotelogix.admin.EditPackagePrice;
import com.hotelogix.admin.EditUser;
import com.hotelogix.admin.GetAgents;
import com.hotelogix.admin.GetAllPayTypes;
import com.hotelogix.admin.GetCorporates;
import com.hotelogix.admin.GetCounters;
import com.hotelogix.admin.GetUsers;
import com.hotelogix.admin.Login;
import com.hotelogix.admin.SaveEmailConfiguration;
import com.hotelogix.admin.WsauthAdmin;
import com.hotelogix.web.OccupancyWiseRate;
import com.hotelogix.web.Wsauth;


public class AdminAPI {

	
	 String sheet="livestable";
    //String sheet="Staygrid";
	 //String sheet="Dotnet";
	
	
	
	HashMap<String, String> HM;
	String path;
	String WsauthKey;
	
	@BeforeClass
	public void fn_ValidateWsauthAPI() throws Throwable{
		try{
			String testcasenme=Thread.currentThread().getStackTrace()[1].getMethodName();
			path="TestData\\"+File.separator+"APITestDataAdmin.xlsx";
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, testcasenme);
			WsauthKey=WsauthAdmin.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"),HM.get("CRSkey"));
			}catch(Throwable e){
			throw new APIException("Wsauth api has failed", e);
		}
	}
/*
	@Test(description="To validate to get all active counters by providing the hotel ids.")
	public void fn_ValidateGetCounters() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateGetCounters");
			GetCounters.class.newInstance().fn_GetCounters(HM.get("GetCounters_URL"), WsauthKey, HM.get("HotelID"), HM.get("CounterName"));
		}catch(Throwable e){
			throw new APIException("ValidateGetCounters api has failed", e);
		}
	}
	
	@Test(priority=1,description="To validate to get all active pay types by providing the hotel ids.")
	public void fn_ValidateGetAllPayTypes() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateGetAllPayTypes");
			GetCounters.class.newInstance().fn_GetCounters(HM.get("GetCounters_URL"), WsauthKey, HM.get("HotelID"), HM.get("CounterName"));
			Login.class.newInstance().fn_Login(HM.get("Login_URL"), HM.get("CRSkey"), HM.get("HotelID"), GetCounters.counterID,HM.get("EmailID"),HM.get("Password"));
		    GetAllPayTypes.class.newInstance().fn_GetAllPayTypes(HM.get("GetAllPayTypes_URL"), Login.LoginAccessKey, HM.get("HotelID"));
		}catch(Throwable e){
			throw new APIException("Validatelogin api has failed", e);
		}
	}
	
	@Test(priority=2,description="To validate create and get user.")
	public void fn_ValidateCreateAndGetUsers() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateCreateAndGetUsers");
			CreateUser.class.newInstance().fn_CreateUserAPI(HM.get("CreateUser_URL"), WsauthKey, HM.get("CounterName"), HM.get("UserTypeTitle"),HM.get("Password"),HM.get("HotelID"));
		    GetUsers.class.newInstance().fn_GetUsersForAdmin(HM.get("GetUsers_URL"), WsauthKey, HM.get("HotelID"));
		}catch(Throwable e){
			throw new APIException("ValidateCreateAndGetUsers api has failed", e);
		}
	}
	
	@Test(priority=3,description="To validate set up email configuration by providing the hotel ids.")
	public void fn_ValidateSaveEmailConfiguration() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateSaveEmailConfiguration");
			SaveEmailConfiguration.class.newInstance().fn_SaveEmailConfiguration(HM.get("SaveEmailConfiguration_URL"), WsauthKey, HM.get("HotelID"));
		}catch(Throwable e){
			throw new APIException(" ValidateSaveEmailConfiguration api has failed", e);
		}
	}
	
	
	@Test(priority=4,description="To validate edit user when status is active.")
	public void fn_ValidateEditUserWithActiveStatus() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateEditUser");
			CreateUser.class.newInstance().fn_CreateUserAPI(HM.get("CreateUser_URL"), WsauthKey, HM.get("CounterName"), HM.get("UserTypeTitle"),HM.get("Password"),HM.get("HotelID"));
		    GetUsers.class.newInstance().fn_GetUsersForAdmin(HM.get("GetUsers_URL"), WsauthKey, HM.get("HotelID"));
		    EditUser.class.newInstance().fn_EditUser(HM.get("EditUser_URL"), WsauthKey, HM.get("CounterName"), HM.get("UserTypeTitle"), HM.get("HotelID"), GetUsers.UserID, CreateUser.EmpNo,"A");
		    GetUsers.class.newInstance().fn_GetUsersAfterEdit(HM.get("GetUsers_URL"), WsauthKey, HM.get("HotelID"));
		}catch(Throwable e){
			throw new APIException(" ValidateEditUser api has failed when user is active", e);
		}
	}
	

	@Test(priority=5,description="To validate deleted user.")
	public void fn_ValidateEditUserWithDelete() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateEditUser");
			CreateUser.class.newInstance().fn_CreateUserAPI(HM.get("CreateUser_URL"), WsauthKey, HM.get("CounterName"), HM.get("UserTypeTitle"),HM.get("Password"),HM.get("HotelID"));
		    GetUsers.class.newInstance().fn_GetUsersForAdmin(HM.get("GetUsers_URL"), WsauthKey, HM.get("HotelID"));
		    EditUser.class.newInstance().fn_EditUser(HM.get("EditUser_URL"), WsauthKey, HM.get("CounterName"), HM.get("UserTypeTitle"), HM.get("HotelID"), GetUsers.UserID, CreateUser.EmpNo,"D");
		    GetUsers.class.newInstance().fn_GetUsersforDeleteOrInActive(HM.get("GetUsers_URL"), WsauthKey, HM.get("HotelID"));
		}catch(Throwable e){
			throw new APIException(" ValidateEditUser api has failed when user was deleted", e);
		}
	}
	
	
	@Test(priority=6,description="To validate edit user when status is Inactive.")
	public void fn_ValidateEditUserWithInactivestatus() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateEditUser");
			CreateUser.class.newInstance().fn_CreateUserAPI(HM.get("CreateUser_URL"), WsauthKey, HM.get("CounterName"), HM.get("UserTypeTitle"),HM.get("Password"),HM.get("HotelID"));
		    GetUsers.class.newInstance().fn_GetUsersForAdmin(HM.get("GetUsers_URL"), WsauthKey, HM.get("HotelID"));
		    EditUser.class.newInstance().fn_EditUser(HM.get("EditUser_URL"), WsauthKey, HM.get("CounterName"), HM.get("UserTypeTitle"), HM.get("HotelID"), GetUsers.UserID, CreateUser.EmpNo,"I");
		    GetUsers.class.newInstance().fn_GetUsersforDeleteOrInActive(HM.get("GetUsers_URL"), WsauthKey, HM.get("HotelID"));
		}catch(Throwable e){
			throw new APIException(" ValidateEditUser api has failed when user is inactive", e);
		}
	}
	
	
	@Test(priority=7,description="To validate edit an agent with active status.")
	public void fn_ValidateEditAgent() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateEditAndDeleteAgent");
			CreateAgent.class.newInstance().fn_CreateAgent(HM.get("CreateAgent_URL"), WsauthKey, HM.get("HotelID"));
		    EditAgent.class.newInstance().fn_EditAgent(HM.get("EditAgent_URL"), WsauthKey, HM.get("HotelID"),CreateAgent.AgentName,CreateAgent.AgentID,"A");
		    GetAgents.class.newInstance().fn_GetAgents(HM.get("GetAgents_URL"), WsauthKey, HM.get("HotelID"),EditAgent.EditedAgentName);
		}catch(Throwable e){
			throw new APIException("Edit Agent api has failed", e);
		}
	   }
	
	
	@Test(priority=8,description="To validate Delete an agent.")
	public void fn_ValidateDeleteAgent() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateEditAndDeleteAgent");
			CreateAgent.class.newInstance().fn_CreateAgent(HM.get("CreateAgent_URL"), WsauthKey, HM.get("HotelID"));
		    EditAgent.class.newInstance().fn_EditAgent(HM.get("EditAgent_URL"), WsauthKey, HM.get("HotelID"),CreateAgent.AgentName,CreateAgent.AgentID,"D");
		    GetAgents.class.newInstance().fn_GetAgentsAfterDelete(HM.get("GetAgents_URL"), WsauthKey, HM.get("HotelID"),EditAgent.EditedAgentName);
		}catch(Throwable e){
			throw new APIException("Delete agent scenario has failed", e);
		}
	   }
	
	
	@Test(priority=9,description="To create and edit corporate with active status.")
	public void fn_ValidateCreateAndEditCorporate() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateCreateEditAndDeleteCorporate");
			CreateCorporate.class.newInstance().fn_CreateCorporate(HM.get("CreateCorporte_URL"), WsauthKey, HM.get("HotelID"));
		    EditCorporate.class.newInstance().fn_EditCorporate(HM.get("EditCorporate_URL"), WsauthKey, HM.get("HotelID"), CreateCorporate.CorporateName, CreateCorporate.CorporateID, "A");
		    GetCorporates.class.newInstance().fn_GetCorporates(HM.get("GetCorporates_URL"), WsauthKey, HM.get("HotelID"), EditCorporate.EditedCorporateName, CreateCorporate.CorporateID);
		}catch(Throwable e){
			  throw new APIException("Create and Edit Corporate scenario has failed.", e);
		}
	   }
	
	
	@Test(priority=10,description="To create,edit and delete corporate with active status.")
	public void fn_ValidateDeleteCorporate() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateCreateEditAndDeleteCorporate");
			CreateCorporate.class.newInstance().fn_CreateCorporate(HM.get("CreateCorporte_URL"), WsauthKey, HM.get("HotelID"));
		    EditCorporate.class.newInstance().fn_EditCorporate(HM.get("EditCorporate_URL"), WsauthKey, HM.get("HotelID"), CreateCorporate.CorporateName, CreateCorporate.CorporateID, "D");
		    GetCorporates.class.newInstance().fn_GetCorporateAfterDelete(HM.get("GetCorporates_URL"), WsauthKey, HM.get("HotelID"));
		}catch(Throwable e){
			  throw new APIException("Create,Edit and Delete Corporate scenario has failed.", e);
		}
	   }
	
	*/
	//@Test(priority=11,description="")        //need to discuss
	public void fn_ValidateEditPackagePrice() throws APIException{
		try{
			HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_ValidateEditPackagePrice");
			APIUtils.GA().fn_GetCurrentDate();
			OccupancyWiseRate.class.newInstance().fn_OccupancyWiseRate(HM.get("OccupancyWiseRate_URL"), WsauthKey, APIUtils.GA().fromdate, APIUtils.GA().todate);
			EditPackagePrice.class.newInstance().fn_EditPackagePrice(HM.get("EditPackagePrice_URL"), WsauthKey, HM.get("HotelID"), OccupancyWiseRate.RateCode, OccupancyWiseRate.RoomTypeCode, "200",APIUtils.GA().fromdate, APIUtils.GA().todate);
		}catch(Throwable e){
			  throw new APIException("", e);
		}
	   }
	
		     
	 @Test(priority=12,description="")       
	 public void fn_GenerateGroupWsauthKey() throws APIException{
	  try{
		HM=ExcelUtils.UI().getTestCaseDataMap(path, sheet, "fn_GenerateGroupWsauthKey");
		WsauthAdmin.class.newInstance().fn_GetWsauthKey(HM.get("Wsauth_URL"),HM.get("GroupCRSkey"));
	  }catch(Throwable e){
		throw new APIException("", e);
	  }
	  }
	
	
}
