/**
 * 
 */
package e2e;

import java.util.List;
import java.util.NoSuchElementException;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DiagnosisDetails;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientBillOSP;
import reusableFunctions.PatientEMR;
import reusableFunctions.QuickEstimate;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.SearchBillList;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Tejaswini
 *
 */
public class QuickEstimateWorkflow {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus ="Fail"; 
			
	@BeforeSuite
	public void BeforeSuite(){
//		System.out.println("BeforeSuite");
//		AutomationID = "Login - QuickEstimateWorkflow";
//		reporter = new ReportingFunctions();
//		System.out.println("AfterReport");
//		
//		initDriver = new KeywordExecutionLibrary();
//		System.out.println("AfterInitDriver");
//
//		try{
//			System.out.println("EnvironmentSetUp is ::" + EnvironmentSetup.URLforExec);
//			driver = initDriver.LaunchApp("Chrome",  EnvironmentSetup.URLforExec);
//			System.out.println("Am here");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		Assert.assertFalse(driver==null, "Browser Not Initialized - Check Log File for Errors");
//		initDriver = null;
//		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
//		verifications.verify(SeleniumVerifications.Appears, "", "LoginScreenHospital_Est", false);
//		verifications = null;
//		
//		AutomationID = "Login - OSPWorkFlowForCashPatient";
//		DataSet = "TS_053A";
//		//EnvironmentSetup.testScenarioId = "TS_001";
//		
//		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
//		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
//		
//		Login QuickEstimateWorkflowLogin = new Login(executeStep,verifications);
//		QuickEstimateWorkflowLogin.login();
//
//		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
//		System.out.println("After Close Tour");
//		
	}
	
	@BeforeMethod
	public void BeforeTest(){
	}
	
	@Test(groups={"E2E","Regression"})
	public void QuickEstimateIPWorkflow(){
		DataSet = "TS_053A";
		AutomationID = "QuickEstimateIPWorkflow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OSPWorkFlowForCashPatient - Before Navigation");

		System.out.println("BeforeSuite");
		AutomationID = "Login - QuickEstimateWorkflow";
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
		
		AutomationID = "Login - QuickEstimateIPWorkflow";
		DataSet = "TS_053A";
		//EnvironmentSetup.testScenarioId = "TS_001";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login QuickEstimateWorkflowLogin = new Login(executeStep,verifications);
		QuickEstimateWorkflowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "QuickEstimateLink", "QuickEstimatePage");
	
		QuickEstimate qkEstimate = new QuickEstimate(executeStep, verifications);
		qkEstimate.quickEstimateIPVisit();
		testCaseStatus = "Pass";
		System.out.println("TS_053A completed Successfully");

	}	

	@Test(groups={"E2E","Regression"})
	public void QuickEstimateOPWorkflow(){
		testCaseStatus = "Fail";
		DataSet = "TS_053B";
		AutomationID = "QuickEstimateOPWorkflow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OSPWorkFlowForCashPatient - Before Navigation");

		System.out.println("BeforeSuite");
		AutomationID = "Login - QuickEstimateWorkflow";
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

		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login QuickEstimateWorkflowLogin = new Login(executeStep,verifications);
		QuickEstimateWorkflowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
				
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		registerOPInsurancePatient();

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "QuickEstimateLink", "QuickEstimatePage");
	
		QuickEstimate qkEstimate = new QuickEstimate(executeStep, verifications);
		qkEstimate.quickEstimateForOPVisit();
		testCaseStatus = "Pass";
		System.out.println("TS_053B completed Successfully");
	}	
	
	@AfterClass
	public void closeBrowser(){
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPWorkFlowForInsurance", null, "", "", testCaseStatus);
		driver.close();
	}
	
	public void registerOPInsurancePatient(){
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		// Added for Insurance-PC Patient Category
		executeStep.performAction(SeleniumActions.Check, "","OSPScreenPrimarySponsor");
		verifications.verify(SeleniumVerifications.Checked, "","OSPScreenPrimarySponsor",false);
		OPReg.RegisterPatientInsurance("OP");
		OPReg.visitInformationDetails();
		OPReg.RegisterPatientInsuranceOP();
	}
}