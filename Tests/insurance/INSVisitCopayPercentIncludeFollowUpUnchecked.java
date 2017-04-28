package insurance;

	import java.util.List;

	import keywords.SeleniumActions;
	import keywords.SeleniumVerifications;
	import GenericFunctions.CommonUtilities;
	import GenericFunctions.EnvironmentSetup;
	import GenericFunctions.ReportingFunctions;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.testng.Assert;
	import org.testng.annotations.AfterMethod;
	import org.testng.annotations.AfterTest;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.BeforeSuite;
	import org.testng.annotations.Test;

	import reusableFunctions.ClaimsSubmission;
	import reusableFunctions.Codification;
	import reusableFunctions.CollectSamples;
	import reusableFunctions.ConsultationAndMgmt;
	import reusableFunctions.DoctorScheduler;
	import reusableFunctions.LabPendingTests;
	import reusableFunctions.Login;
	import reusableFunctions.MRNoSearch;
	import reusableFunctions.OPListSearch;
	import reusableFunctions.OpenGenericForm;
	import reusableFunctions.Order;
	import reusableFunctions.PatientArrival;
	import reusableFunctions.PatientBill;
	import reusableFunctions.PatientEMR;
	import reusableFunctions.PatientIssue;
	import reusableFunctions.RadiologyPendingTests;
	import reusableFunctions.RaisePatientIndent;
	import reusableFunctions.Registration;
	import reusableFunctions.Sales;
	import reusableFunctions.SalesReturn;
	import reusableFunctions.ServicePending;
	import reusableFunctions.SiteNavigation;
	import reusableFunctions.TestsConduction;
	import reusableFunctions.Triage;
	import reusableFunctions.Upload;
import reusableFunctions.insurance.BillVerification;
import reusableFunctions.insurance.OrderModuleInsurance;
import reusableFunctions.insurance.PatientCategoryMaster;
import reusableFunctions.insurance.PlanMaster;
import reusableFunctions.insurance.RegistrationModuleInsurance;
import reusableFunctions.insurance.SponsorTypeDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
	import seleniumWebUIFunctions.KeywordSelectionLibrary;
	import seleniumWebUIFunctions.VerificationFunctions;

	/**
	 * @author Reena
	 *
	 */
	 public class INSVisitCopayPercentIncludeFollowUpUnchecked{ 
		WebDriver driver = null;
		KeywordSelectionLibrary executeStep;
		KeywordExecutionLibrary initDriver;
		VerificationFunctions verifications;
		ReportingFunctions reporter;
		String AutomationID;
		String DataSet;
		String testCaseStatus="FAIL";
		
		//@BeforeSuite
		//public void BeforeSuite(){
		private void openBrowser(){
			System.out.println("BeforeSuite");
			AutomationID = "Login - INSVisitCopayPercentIncludeFollowUpUnchecked";
			reporter = new ReportingFunctions();
			System.out.println("AfterReport");
			
			initDriver = new KeywordExecutionLibrary();
			System.out.println("AfterInitDriver");

			try{
				System.out.println("EnvironmentSetUp is ::" + EnvironmentSetup.URLforExec);
				driver = initDriver.LaunchApp("Chrome",  EnvironmentSetup.URLforExec);
				System.out.println("Am here");
			}catch(Exception e){
				e.printStackTrace();
			}
			Assert.assertFalse(driver==null, "Browser Not Initialized - Check Log File for Errors");
			initDriver = null;
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			verifications.verify(SeleniumVerifications.Appears, "", "LoginScreenHospital_Est", false);
			verifications = null;
			
		}
		
		//@BeforeMethod
		//public void BeforeTest(){
		private void login(){
			AutomationID = "Login - INSVisitCopayPercentIncludeFollowUpUnchecked";
			DataSet = "TS_008";
			
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			Login OPWorkFlowLogin = new Login(executeStep,verifications);
			OPWorkFlowLogin.login();

			executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
			System.out.println("After Close Tour");
			CommonUtilities.delay();
			executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
			System.out.println("After Close Tour");
			
		}
		
		@Test(groups={"E2E","Regression"})
		public void INSVisitCopayPercentIncludeFollowUpUnchecked(){
			openBrowser();
			login();
			DataSet = "TS_008";
			AutomationID = "INSVisitCopayPercentIncludeFollowUpUnchecked";
			List<String> lineItemIDs = null;
			System.out.println("INSVisitCopayPercentIncludeFollowUpUnchecked - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			
	 SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
     navigation.navigateToSettings(driver, "InsurancePlanLink", "PlanMasterPage");
	      	  
	  //Go to  Settings--> Hospital Admin Master ---> Insurance Plans--->
	  //click on " Add new Plan" --- > give plan name  ,[select ]insurance company name
	  //sponsor,plan type ,  op-Applicable[check box]
	      
	      executeStep.performAction(SeleniumActions.Click, "", "PlanMasterAddNewPlanLink");
		  verifications.verify(SeleniumVerifications.Appears, "", "AddPlanDetailsPage", false);
	      	      
		PlanMaster planmaster = new PlanMaster(executeStep, verifications);
		planmaster.editPlanMaster();
		planmaster.editCoPayandLimitsOP();
	    	 
	  
	  
	    	 
	     //4.Save the plan	
	      	executeStep.performAction(SeleniumActions.Click, "","EditPlanDetailsPageSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "", "EditPlanDetailsPage", false);
			
	   	 
		//Go to OP Registration screen,select primary sponsor check box,enter Daman tpa,daman insurance company,daman plan type and then select insurance plan as 'Daman Plan'. 
			  	
	    	navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
	    	RegistrationModuleInsurance OPReg = new RegistrationModuleInsurance(executeStep, verifications);
	    	Upload uploadfile=new Upload(executeStep, verifications);
	    	OPReg.RegisterPatientGenericDetails();
			OPReg.setBillType();
			OPReg.GovtIDDetailsCollapsedPanel();
			OPReg.visitInformationDetails();
			OPReg.RegisterPatientInsurance("OP");
			OPReg.uncheckSecondarySponsor();
			OPReg.additionalSponsorDetails();//need as its mandatory on the application
			String registrationupload="//input[@id='primary_insurance_doc_content_bytea1' and @type='file']";
		    uploadfile.upload(registrationupload);
			OPReg.storeDetails();
	
		//Go to bill and click on plus icon. Add  few billable items and Save the bill.
			navigation.navigateTo(driver, "OpenBills", "BillListPage");
			MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
			mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
			
			PatientBill patientBill = new PatientBill(executeStep, verifications);
			executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
			CommonUtilities.delay();
		    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
		    CommonUtilities.delay();
		    executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
		    CommonUtilities.delay(10);
	        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
	        executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
	        
	        
			patientBill.addItemsIntoPatientBill();
			
			verifications.verify(SeleniumVerifications.Opens, "","PatientBillErrorPage",true);
			executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillErrorPage");
			verifications.verify(SeleniumVerifications.Closes, "","PatientBillErrorPage",true);
			
			
			
			
	
		 // 3)Now click on Order link which is available in bill screen
		//  4)Select bill type as New Bill Insurance. Order below billable items. Save the order screen. 
	        
		    navigation.navigateTo(driver, "OpenBills", "BillListPage");
	//	MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
			mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
			
		//    PatientBill patientBill = new PatientBill(executeStep, verifications);
			executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
			CommonUtilities.delay();
		    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
		    CommonUtilities.delay();
		    executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
		    CommonUtilities.delay(10);
	        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
	        executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
	        BillVerification billdetails = new BillVerification(executeStep, verifications);
	        billdetails.toVerifyBillDetails("BillVerify");
			
			
	        executeStep.performAction(SeleniumActions.Click, "","BillPageOrderLink");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
			verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);
			
			
			OrderModuleInsurance order = new OrderModuleInsurance(executeStep, verifications);
			order.addOrderInsurance("OrderItem");
		
			
		
			
			
				 navigation.navigateTo(driver, "OpenBills", "BillListPage");
		//		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);
				mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillsListResultsTable");
				
		//		PatientBill patientBill = new PatientBill(executeStep, verifications);
				executeStep.performAction(SeleniumActions.Click, "","BillListItemToClick");
				CommonUtilities.delay();
			    verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
			    CommonUtilities.delay();
			    executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
			    CommonUtilities.delay(10);
		        verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
		        executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
			
		        
		     //   BillVerification billdetails = new BillVerification(executeStep, verifications);
				billdetails.toVerifyBillDetails("BillSecondVerify");
				
		
		}
}