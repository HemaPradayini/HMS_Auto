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
	 public class INSPlanDetailsAndContractDetailsSectionInInsurancePlan{ 
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
			AutomationID = "Login - PatientCategoryMaster";
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
			AutomationID = "Login - INSPlanDetailsAndContractDetailsSectionInInsurancePlan";
			DataSet = "TS_006";
			
			
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
		public void INSPlanDetailsAndContractDetailsSectionInInsurancePlan(){
			openBrowser();
			login();
			DataSet = "TS_006";
			AutomationID = "INSPlanDetailsAndContractDetailsSectionInInsurancePlan";
			List<String> lineItemIDs = null;
			System.out.println("INSPlanDetailsAndContractDetailsSectionInInsurancePlan - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	     navigation.navigateToSettings(driver, "InsurancePlanLink", "PlanMasterPage");
			
	    	executeStep.performAction(SeleniumActions.Click, "", "PlanMasterAddNewPlanLink");
			verifications.verify(SeleniumVerifications.Appears, "", "AddPlanDetailsPage", false);
	        
	    	 PlanMaster planMaster = new PlanMaster(executeStep, verifications);
	    	 planMaster.editPlanMaster();
	    	 
	    	 //2.map company TPA sponsor
			
	    	 
	    	 navigation.navigateToSettings(driver, "InsuranceCompanyTPA/SponsorListLink", "InsuranceCompanyTPA/SponsorMasterPage");
	    	 SponsorTypeDetails mapsponsor = new SponsorTypeDetails(executeStep, verifications);
	    	 mapsponsor.mapCompanyTPASponsor();
	    	      		
	    	navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
	    	RegistrationModuleInsurance OPReg = new RegistrationModuleInsurance(executeStep, verifications);
	    	
	    	executeStep.performAction(SeleniumActions.Enter, "PrimarySponsorName","OSPScreenPrimarySponsorName");
			verifications.verify(SeleniumVerifications.Appears, "","OSPScreenSponsorDropDown",true);		
			//CommonUtilities.delay();
			
			executeStep.performAction(SeleniumActions.Click, "","OSPScreenSponsorDropDown");
			verifications.verify(SeleniumVerifications.Entered, "PrimarySponsorName","OSPScreenPrimarySponsorName",true);
	    		    	
	    	
	    	executeStep.performAction(SeleniumActions.Select, "PrimaryInsuranceCo","OSPScreenPrimaryInsuranceCo");
	    	verifications.verify(SeleniumVerifications.Selected, "PrimaryInsuranceCo","OSPScreenPrimaryInsuranceCo",true);	


	    	executeStep.performAction(SeleniumActions.Select, "InsurancePlanType","OSPScreenNetworkPlanType");
	    	verifications.verify(SeleniumVerifications.Selected, "InsurancePlanType","OSPScreenNetworkPlanType",false);	


	    	executeStep.performAction(SeleniumActions.Select, "InsurancePlanName","OSPScreenPlanName");
	    	verifications.verify(SeleniumVerifications.Selected, "InsurancePlanName","OSPScreenPlanName",false);
	    	
	    	//8.Observe the defaulted validity information and default rate plan
	    	verifications.verify(SeleniumVerifications.Entered, "ValidFrom","OSPScreenPrimarySponsorValidityStart",false);
	    	verifications.verify(SeleniumVerifications.Entered, "ValidUpto","OSPScreenPrimarySponsorValidityEnd",false);
	    	verifications.verify(SeleniumVerifications.Selected, "DefaultRatePlan","OPRScreenRatePlanField",false);
	    	testCaseStatus="PASS";
			System.out.println("INSPlanDetailsAndContractDetailsSectionInInsurancePlan-Completed");
	    	 
		}
		
		@AfterTest
		public void closeBrowser(){
			reporter.UpdateTestCaseReport(AutomationID, DataSet, "INSPlanDetailsAndContractDetailsSectionInInsurancePlan", null, "", "", testCaseStatus);
			driver.close();
		}
}