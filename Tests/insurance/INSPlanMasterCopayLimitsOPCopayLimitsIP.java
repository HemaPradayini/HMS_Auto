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
	 public class INSPlanMasterCopayLimitsOPCopayLimitsIP{ 
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
			AutomationID = "Login - INSPlanMasterCopayLimitsOPCopayLimitsIP";
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
			AutomationID = "Login - INSPlanMasterCopayLimitsOPCopayLimitsIP";
			DataSet = "TS_007";
			
			
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
		public void INSPlanMasterCopayLimitsOPCopayLimitsIP(){
			openBrowser();
			login();
			DataSet = "TS_007";
			AutomationID = "INSPlanMasterCopayLimitsOPCopayLimitsIP";
			List<String> lineItemIDs = null;
			System.out.println("INSPlanMasterCopayLimitsOPCopayLimitsIP - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
		//Go to 'Insurance Plan' master under Admin Masters. Click on any of the available plans say Daman Plan	
		 SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	     navigation.navigateToSettings(driver, "InsurancePlanLink", "PlanMasterPage");
	     PlanMaster searchplanmaster = new PlanMaster(executeStep, verifications);
	      searchplanmaster.SearchPlanInPlanMaster();
		  
	  	    	 
	    //2.Define values for all the fields available under  Copay & Limits - OP section
			 PlanMaster editplanmaster = new PlanMaster(executeStep, verifications);
			  editplanmaster.editCoPayandLimitsOP();
	    	 
	    	 
	   //3.Define values for all the fields available under  Copay & Limits - IP section 
	      	 editplanmaster.editCoPayandLimitsIP();
	    	 
	     //4.Save the plan	
	      	 executeStep.performAction(SeleniumActions.Click, "","EditPlanDetailsPageSaveButton");
			  verifications.verify(SeleniumVerifications.Appears, "", "EditPlanDetailsPage", false);
	      	 
		//5.Go to OP Registration screen,select primary sponsor check box,enter Daman tpa,daman insurance company,daman plan type and then select insurance plan as 'Daman Plan'. 
			  
			  
	    	      		
	    	navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
	    	RegistrationModuleInsurance OPReg = new RegistrationModuleInsurance(executeStep, verifications);
	    	
	    	executeStep.performAction(SeleniumActions.Enter, "PrimarySponsorName","OSPScreenPrimarySponsorName");
			verifications.verify(SeleniumVerifications.Appears, "","OSPScreenSponsorDropDown",false);		
			//CommonUtilities.delay();
			executeStep.performAction(SeleniumActions.Click, "","OSPScreenSponsorDropDown");
			verifications.verify(SeleniumVerifications.Entered, "PrimarySponsorName","OSPScreenPrimarySponsorName",false);
	    	executeStep.performAction(SeleniumActions.Select, "PrimaryInsuranceCo","OSPScreenPrimaryInsuranceCo");
	    	verifications.verify(SeleniumVerifications.Selected, "PrimaryInsuranceCo","OSPScreenPrimaryInsuranceCo",false);	
	    	executeStep.performAction(SeleniumActions.Select, "InsurancePlanType","OSPScreenNetworkPlanType");
	    	verifications.verify(SeleniumVerifications.Selected, "InsurancePlanType","OSPScreenNetworkPlanType",false);	
	    	executeStep.performAction(SeleniumActions.Select, "InsurancePlanName","OSPScreenPlanName");
	    	verifications.verify(SeleniumVerifications.Selected, "InsurancePlanName","OSPScreenPlanName",false);
	    	
	    	//Observe Plan Limit,Available Limit,Visit Sponsor Limit,
	    	//Visit Deductible,Visit Co-Pay % and Visit Max Copay fields after selecting insurance plan
	    	
	    	
	    	verifications.verify(SeleniumVerifications.Entered, "OPPlanLimit","PrimaryPlanLimit",false);
	    	verifications.verify(SeleniumVerifications.Entered, "OPVisitSponsorLimit","PrimaryVisitSponsorLimit",false);
	    	verifications.verify(SeleniumVerifications.Entered, "OPVisitDeductible","PrimaryVisitDedcutible",false);
	    	verifications.verify(SeleniumVerifications.Entered, "OPVisitCopay","PrimaryVisitCopayPercent",false);
	    	verifications.verify(SeleniumVerifications.Entered, "OPVisitMaxCopay","PrimaryVisitMaxCopay",false);
	    	
	    	//6.Go to IP Registration screen,select primary sponsor check box,enter Daman tpa,daman insurance company,daman plan type and then select insurance plan as 'Daman Plan'. Observe Plan Limit,Available Limit,Visit Sponsor Limit,
	    	//Visit Deductible,Visit Co-Pay % and Visit Max Copay fields after selecting insurance plan
	    	
			navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
			RegistrationModuleInsurance IPReg = new RegistrationModuleInsurance(executeStep, verifications);
			
			executeStep.performAction(SeleniumActions.Click, "","OSPScreenPrimarySponsor");
			verifications.verify(SeleniumVerifications.Appears, "","IPRegistrationPage",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PrimarySponsorName","OSPScreenPrimarySponsorName");
			verifications.verify(SeleniumVerifications.Appears, "","OSPScreenSponsorDropDown",false);		
			//CommonUtilities.delay();
			executeStep.performAction(SeleniumActions.Click, "","OSPScreenSponsorDropDown");
			verifications.verify(SeleniumVerifications.Entered, "PrimarySponsorName","OSPScreenPrimarySponsorName",false);
	    	executeStep.performAction(SeleniumActions.Select, "PrimaryInsuranceCo","OSPScreenPrimaryInsuranceCo");
	    	verifications.verify(SeleniumVerifications.Selected, "PrimaryInsuranceCo","OSPScreenPrimaryInsuranceCo",false);	
	    	executeStep.performAction(SeleniumActions.Select, "InsurancePlanType","OSPScreenNetworkPlanType");
	    	verifications.verify(SeleniumVerifications.Selected, "InsurancePlanType","OSPScreenNetworkPlanType",false);	
	    	executeStep.performAction(SeleniumActions.Select, "InsurancePlanName","OSPScreenPlanName");
	    	verifications.verify(SeleniumVerifications.Selected, "InsurancePlanName","OSPScreenPlanName",false);
	    	
	    	//Observe Plan Limit,Available Limit,Visit Sponsor Limit,
	    	//Visit Deductible,Visit Co-Pay % and Visit Max Copay fields after selecting insurance plan
	    	
	    	
	    	verifications.verify(SeleniumVerifications.Entered, "IPPlanLimit","PrimaryPlanLimit",false);
	    	verifications.verify(SeleniumVerifications.Entered, "IPVisitSponsorLimit","PrimaryVisitSponsorLimit",false);
	    	verifications.verify(SeleniumVerifications.Entered, "IPVisitDeductible","PrimaryVisitDedcutible",false);
	    	verifications.verify(SeleniumVerifications.Entered, "IPVisitCopay","PrimaryVisitCopayPercent",false);
	    	verifications.verify(SeleniumVerifications.Entered, "IPVisitMaxCopay","PrimaryVisitMaxCopay",false);
	    	verifications.verify(SeleniumVerifications.Entered, "IPPerDayLimit","PrimaryPerDayLimit",false);
			
			
	    	testCaseStatus="PASS";
			System.out.println("INSPlanMasterCopayLimitsOPCopayLimitsIP-Completed");
	    	
		}
		
		@AfterTest
		public void closeBrowser(){
			reporter.UpdateTestCaseReport(AutomationID, DataSet, "INSPlanMasterCopayLimitsOPCopayLimitsIP", null, "", "", testCaseStatus);
			driver.close();
		}
}