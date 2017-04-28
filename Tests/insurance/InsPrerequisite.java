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
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
	import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
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
import reusableFunctions.insurance.RegistrationModuleInsurance;
import reusableFunctions.insurance.RolesAndUsers;
import reusableFunctions.insurance.SponsorTypeDetails;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
	import seleniumWebUIFunctions.KeywordSelectionLibrary;
	import seleniumWebUIFunctions.VerificationFunctions;

	/**
	 * @author Reena
	 *
	 */
	 public class InsPrerequisite{ 
		WebDriver driver = null;
		KeywordSelectionLibrary executeStep;
		KeywordExecutionLibrary initDriver;
		VerificationFunctions verifications;
		ReportingFunctions reporter;
		String AutomationID;
		String DataSet;
		String testCaseStatus="FAIL";
	
		
		public void openBrowser(){
			System.out.println("BeforeSuite");
									
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
		private void login(){
			
			
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
		@BeforeClass
	      public void InsPrerequisites(){
		    
			AutomationID = "Login - InsPrerequisites";
			DataSet = "INS_00A";
			
			openBrowser();
			login();
			
			List<String> lineItemIDs = null;
			System.out.println("InsPrerequisites - Before Navigation");
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateToSettings(driver, "RolesAndUserLink","RolesAndUserPage" );
			RolesAndUsers role = new RolesAndUsers(executeStep, verifications);
			role.createRole();
			role.selectScreen("AccessToScreen");
			role.selectActions("AccessToScreen");
			executeStep.performAction(SeleniumActions.Click, "", "RolePageSubmitButton");
			verifications.verify(SeleniumVerifications.Appears, "", "PageStatsPage", false);
            navigation.navigateToSettings(driver, "RolesAndUserLink","RolesAndUserPage" );
			RolesAndUsers user = new RolesAndUsers(executeStep, verifications);
			user.createUser();
			Login OPWorkFlowLogin = new Login(executeStep,verifications);
			OPWorkFlowLogin.logOut();
			
			System.out.println("Login - InsPrerequisites");
			
}
		
	
		@Test(priority=1)
		public void INSModuleEnabling(){
			
			DataSet = "INS_001";
			AutomationID = "INSModuleEnabling";
			
			login();
			
			List<String> lineItemIDs = null;
			System.out.println("Inside Test InventoryPurchaseDiscount - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
			 SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			 navigation.navigateTo(driver, "ClaimsSubmissionLink" , "ClaimsSubmissionPage");
			 navigation.navigateTo(driver, "RemittanceAdviceUploadLink" , "RemittanceUploadPage");
			 navigation.navigateTo(driver, "ClaimReconciliationLink" , "ClaimReconciliationPage");
			 
			 //edit claim and edit receipt
			 navigation.navigateTo(driver, "ClaimReceiptsLink" , "ClaimReceiptsPage");
			 navigation.navigateTo(driver, "EditClaimLink" , "EditClaimPage");
			 		
			 
				testCaseStatus="PASS";
				
				Login OPWorkFlowLogin = new Login(executeStep,verifications);
				OPWorkFlowLogin.logOut();
				System.out.println("INSModuleEnabling-Completed");
		}
		@Test(priority=2)
		public void InsSponsorSelectionIsCheckboxDriven(){
		
			DataSet = "TS_002";
			AutomationID = "InsSponsorSelectionIsCheckboxDriven";
			login();
			List<String> lineItemIDs = null;
			System.out.println("InsSponsorSelectionIsCheckboxDriven - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
          	
			//Go to Registration screen 
			 SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	    	 navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
	    	 
	    	 //2.Observe the Primary and Secondary Sponsor selection 
			
	    	 Registration OPReg = new Registration(executeStep, verifications);
	    	 OPReg.unCheckPrimarySponsor();
	    	 OPReg.unCheckPrimarySponsor();
	    	 Boolean we=false;
	    	 try{
					we = this.executeStep.getDriver().findElement(By.xpath("//input[@id='secondary_sponsor_wrapper']")).isEnabled();
				}catch (Exception e){
				System.out.println("Webelement for given xpath not found" + e.toString());
				}
				if (we==true){	
				System.out.println("Secondary Sponsor option is available only after primary sponsor selection" );
	    	 	}
	    	 
	    	// 3.Check the Primary Sponsor check box and observe the available fields 
	    	 OPReg.RegisterPatientInsurance("OP");
	    	 	    	     	 
	    	// 4.Check the Secondary Sponsor check box and observe the available fields
	    	 	 OPReg.RegisterPatientSecondaryInsurance();
	    	
	    	 	testCaseStatus="PASS";
	    	 	System.out.println("InsSponsorSelectionIsCheckboxDriven-Completed");
	    	 	Login OPWorkFlowLogin = new Login(executeStep,verifications);
	    	 	OPWorkFlowLogin.logOut();
	    	 	
		}
		@Test(priority=3)
		public void InsSponsorTypeConfiguration(){
			login();
			DataSet = "TS_003";
			AutomationID = "InsSponsorTypeConfiguration";
			List<String> lineItemIDs = null;
			System.out.println("InsSponsorTypeConfiguration - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
			//1) Go to sponsor type master under Admin Masters
			 SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		   	 navigation.navigateToSettings(driver, "SponsorTypeMasterLink", "SponsorTypeMasterPage");
		   	 
			SponsorTypeDetails sponsorDetails = new SponsorTypeDetails(executeStep, verifications);
			sponsorDetails.editSponsorTypeDetails();
			
			navigation.navigateToSettings(driver, "TPA/SponsorMasterLink", "TPA/SponsorMasterPage");
			sponsorDetails.editTPASponsorMasters();
			
			//Go to OP/IP/OSP registration screens, select Primary and Secondary Sponsor checkboxes
			
			
		//	SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			//OP Registration
			navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
			RegistrationModuleInsurance OPReg = new RegistrationModuleInsurance(executeStep, verifications);
			OPReg.RegisterPatientGenericDetails();
			OPReg.setBillType();
			OPReg.GovtIDDetailsCollapsedPanel();
			OPReg.visitInformationDetails();
			OPReg.RegisterPatientInsurance("OP");
			OPReg.RegisterPatientSecondaryInsurance();
			OPReg.additionalSponsorDetails();
			OPReg.asterickSignVerification();
			OPReg.storeDetails();
		
			testCaseStatus="PASS";
			System.out.println("InsSponsorTypeConfiguration-Completed");
			Login OPWorkFlowLogin = new Login(executeStep,verifications);
    	 	OPWorkFlowLogin.logOut();
			
		}
			
			@Test(priority=4)
			public void InsSponsorTypeConfigurationforIP(){
				
				login();
				DataSet = "TS_003a";
				AutomationID = "InsSponsorTypeConfigurationforIP";
				
				List<String> lineItemIDs = null;
				System.out.println("InsSponsorTypeConfiguration - Before Navigation");
				
				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);

			
			
            SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
			RegistrationModuleInsurance IPReg = new RegistrationModuleInsurance(executeStep, verifications);
			IPReg.RegisterPatientGenericDetails();
			IPReg.GovtIDDetailsExpandedPanel();
			IPReg.RegisterPatientInsurance("IP");
			IPReg.RegisterPatientSecondaryInsurance();
			IPReg.RegisterAdmissionInfoIP("IP");
			IPReg.additionalSponsorDetails();
			IPReg.asterickSignVerification();
			IPReg.storeDetails();
			testCaseStatus="PASS";
			System.out.println("InsSponsorTypeConfigurationforIP-Completed");
			Login OPWorkFlowLogin = new Login(executeStep,verifications);
    	 	OPWorkFlowLogin.logOut();
			
			}	
			@Test(priority=5)
			public void InsSponsorTypeConfigurationforOSP(){
				login();
				DataSet = "TS_003b";
				AutomationID = "InsSponsorTypeConfigurationforOSP";
				
				List<String> lineItemIDs = null;
				System.out.println("InsSponsorTypeConfiguration - Before Navigation");
				
				executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
				verifications = new VerificationFunctions(driver, AutomationID, DataSet);

			SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateTo(driver, "OSPatientRegistrationLink", "OSPatientRegistrationScreen");
			RegistrationModuleInsurance OSPReg = new RegistrationModuleInsurance(executeStep, verifications);
			
						
			OSPReg.RegisterPatientGenericDetails();
			OSPReg.RegisterPatientOSPInsurance();
			OSPReg.RegisterPatientSecondaryInsurance();
			OSPReg.additionalSponsorDetails();
			OSPReg.asterickSignVerification();
			OSPReg.storeDetails();
			testCaseStatus="PASS";
			System.out.println("InsSponsorTypeConfigurationforOSP-Completed");
		}
			@AfterTest
			public void closeBrowser(){
				reporter.UpdateTestCaseReport(AutomationID, DataSet, "InsPrerequisite", null, "", "", testCaseStatus);
				driver.close();
			}
		}	
		
