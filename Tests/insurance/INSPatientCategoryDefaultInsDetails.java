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
import reusableFunctions.insurance.RegistrationModuleInsurance;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
	import seleniumWebUIFunctions.KeywordSelectionLibrary;
	import seleniumWebUIFunctions.VerificationFunctions;

	/**
	 * @author Reena
	 *
	 */
	 public class INSPatientCategoryDefaultInsDetails{ 
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
			AutomationID = "Login - INSPatientCategoryDefaultInsDetails";
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
			AutomationID = "Login - INSPatientCategoryDefaultInsDetails";
			DataSet = "TS_004";
			
			
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
		public void INSPatientCategoryDefaultInsDetails(){
			openBrowser();
			login();
			DataSet = "TS_004";
			AutomationID = "INSPatientCategoryDefaultInsDetails";
			List<String> lineItemIDs = null;
			System.out.println("INSPatientCategoryDefaultInsDetails - Before Navigation");
			
			executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
			verifications = new VerificationFunctions(driver, AutomationID, DataSet);
			
            //Application level pre-requisites
			
			 SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	    	 navigation.navigateToSettings(driver, "PatientCategoryMasterLink", "PatientCategoryMasterPage");
			
	    	 PatientCategoryMaster patCategaoryMaster = new PatientCategoryMaster(executeStep, verifications);
	    	 patCategaoryMaster.editPatientCategoryMaster();
	    	 
	    	 
	    	 
	    	 //2.Observe the Primary and Secondary Sponsor selection 
			
	    	      		
	    		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
	    		RegistrationModuleInsurance OPReg = new RegistrationModuleInsurance(executeStep, verifications);
	    	 
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
				executeStep.performAction(SeleniumActions.Check, "","OSPScreenSecondarySponsor");
				verifications.verify(SeleniumVerifications.Checked, "","OSPScreenSecondarySponsor",false);
				
				verifications.verify(SeleniumVerifications.Selected, "OPDefaultPrimarySponsor","OSPScreenPrimarySponsorName",false);
				verifications.verify(SeleniumVerifications.Selected, "OPDefaultPrimaryInsuranceCompany","OSPScreenPrimaryInsuranceCo",false);
			
				verifications.verify(SeleniumVerifications.Selected, "OPDefaultSecondarySponsor","OSPScreenSecondarySponsorName",false);
				verifications.verify(SeleniumVerifications.Selected, "OPDefaultSecondaryInsuranceCompany","OSPScreenSecondaryInsuranceCo",false);
				testCaseStatus="PASS";
				System.out.println("INSPatientCategoryDefaultInsDetails-Completed");

		}
		
		@AfterTest
		public void closeBrowser(){
			reporter.UpdateTestCaseReport(AutomationID, DataSet, "INSPatientCategoryDefaultInsDetails", null, "", "", testCaseStatus);
			driver.close();
		}
}