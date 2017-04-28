package e2e;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;


import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.OperationDetails;
import reusableFunctions.Order;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientVisit;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class IncomingRegistrationWorkFlow {

	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";
	
//	@BeforeSuite
//	public void BeforeSuite(){
	private void openBrowser(){
		AutomationID = "Login - IncomingRegistrationWorkFlow";
		DataSet = "TS_054";
		//EnvironmentSetup.testScenarioId = "TS_001";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - IncomingRegistrationWorkFlow";
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
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		//executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void IncomingRegistrationWorkFlow(){
		
		openBrowser();
		login();
		DataSet = "TS_054";
		AutomationID = "IncomingRegistrationWorkFlow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test IncomingRegistrationWorkFlow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		//1.Go to Laboratory Incoming Sample Registration/Incoming Radiology Test screen.
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "IncomingRadiologyTestsLink", "IncomingRadiologyTestsScreen");
		
		//2. Enter incoming hospital name,patient name,gender,age,rate plan.
		//3. Select a bill type as  bill later.
		
		RadiologyPendingTests incomingRadiology= new RadiologyPendingTests( executeStep,verifications);
		incomingRadiology.IncomingRadiologyTestsIncomingSampleGenericDetails();
		
		//4. Click on Add test icon, search a test eg: complete blood count from the Test/Lab package field.
		//5. Enter original sample number (any number),sample type,conducting doctor and click on Add.
		// Step 6 not required for bill later
		//7. For Bill Later, after step5, click on Save.
		incomingRadiology.addItems("IncomingRadiology");
		//8. Go to Incoming Sample Pending Bills , select the patient and click on View/Edit Bill menu option
		navigation.navigateTo(driver, "IncomingPendinBillsLink", "IncomingPendingBillsScreen");
		incomingRadiology.searchPatient();
		incomingRadiology.viewEditIncomingSampleBill();
		//9. Patient Bill screen, click on payments bar select payment type as settlement, select a mode
		//as cash/card/etc. Enter the Net amount in the Pay field.
		//10. Click on Save.
		PatientBill patientBill = new PatientBill(executeStep ,verifications);
		patientBill.settleSecondaryBillSBillDetails("NO","SettlementPaymentType");
		
		
		
		
		//Step 1-7 for Laboratory Incoming Sample Registration
		
		navigation.navigateTo(driver, "IncomingSampleRegistrationLink", "IncomingSampleRegistrationScreen");
		incomingRadiology.IncomingRadiologyTestsIncomingSampleGenericDetails();
        incomingRadiology.addItems("IncomingSample");
		
		
		
		
		
		//8. Go to Incoming Pending Bills, select the patient and click on View/Edit Bill menu option
		navigation.navigateTo(driver, "IncomingSamplePendingBillsLink", "IncomingSamplePendingBillsScreen");
			incomingRadiology.searchPatient();
		//9. Patient Bill screen, click on payments bar select payment type as settlement, 
		//select a mode as cash/card/etc. Enter the Net amount in the Pay field.
		//10. Click on Save.
		incomingRadiology.viewEditIncomingSampleBill();
		patientBill.settleSecondaryBillSBillDetails("NO","SettlementPaymentType");
		
		
		//11. Go to laboratory reports screen > 
		//select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
		
		navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");        //changed on 4/11
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		//search.searchWithPatientName("LabPendingTests","LabPendingTestSearchPatientNameInputbox","LabPendingTestSearchButton");
		//search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryReportsPage");  // changed on 4/11
		search.searchWithPatientName("LaboratoryReportsPage","LabPendingTestSearchPatientNameInputbox","LabPendingTestSearchButton");
		
		executeStep.performAction(SeleniumActions.Click, "","LabReportsScreenSelectLabCheckBox");
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
    	TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();
		
		//12.Go to radiology reports screen> Select the test and click on View/Edit, choose a template
		//then edit details and save. Mark complete, validate and signoff.
		 navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(false);
			
		testCaseStatus = "Pass";
		System.out.println("TS_054 - completed");
		}
	
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "IncomingRegistrationWorkFlow", null, "", "", testCaseStatus);
		driver.close();
	}
	
}