/**
 * 
 */
package e2e;

import java.util.List;
import java.util.concurrent.TimeUnit;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.CommonUtilities;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.BedAllocation;
import reusableFunctions.BedFinalisation;
import reusableFunctions.ChangeRatePlanBedType;
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
import reusableFunctions.PatientBillOSP;
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientIssue;
import reusableFunctions.PatientVisit;
import reusableFunctions.PriorAuth;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.SearchBillList;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import reusableFunctions.Upload;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Tejaswini
 *
 */
public class PriorAuthWorkflow {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";

	
	@BeforeSuite
	public void BeforeSuite(){
//		System.out.println("BeforeSuite");
//		AutomationID = "Login - OPToIPWorkflowWithCopyChargesBillAction";
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
	}
	
//	@BeforeMethod
	//public void BeforeTest(){
	private void login(){
		AutomationID = "Login - PriorAuthWorkflow";
		DataSet = "TS_070";
		//EnvironmentSetup.testScenarioId = "TS_001";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - PriorAuthWorkflow";
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
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		//executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void PriorAuthWorkflow(){
		login();
		
		DataSet = "TS_070";
		AutomationID = "PriorAuthWorkflow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test PriorAuthWorkflow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//1.Register an OP patient with Bill Later bill by enetring all mandatory fields		
		//Navigate To OP Registration Screen
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		//This is for navigation to the OP List and searching with the MR No on the OPList and navigating to the consultation and management screen
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		Registration OPReg = new Registration(executeStep, verifications);
//		OPReg.RegisterPatientGenericDetails();
//		OPReg.setBillType();
//		OPReg.RegisterPatientInsurance("OP");
//		Upload uploadfile=new Upload(executeStep, verifications);
//	    String registrationupload="//input[@id='primary_insurance_doc_content_bytea1' and @type='file']";
//	    uploadfile.upload(registrationupload);
//		OPReg.visitInformationDetails();
//		OPReg.RegisterPatientInsuranceOP();
	   OPReg.GovtIDDetailsCollapsedPanel();
	   OPReg.RegisterPatientGenericDetails();
       OPReg.setBillType();
       OPReg.RegisterPatientInsurance("OP");
       String registrationupload="//input[@id='primary_insurance_doc_content_bytea1' and @type='file']";
       Upload uploadfile=new Upload(executeStep, verifications);
       uploadfile.upload(registrationupload);
		   
	   OPReg.visitInformationDetails();
       OPReg.storeDetails();

		System.out.println("Step 1 complete");

//		2.Go to OP list: Open the consultation screen,
//
//		Add vital records 
//		Add diagnosis codes 
//		Add mandatory fields in the hospital form. 
//		Prescribe tests : with marking 'Send For Prior Auth:' checkbox, checked. 
//		Prescribe services : with marking 'Send For Prior Auth:' checkbox, checked. 
//		-->This checkbox will be automatically checked for the items which has Prior Auth required as 'Always' in respective masters.
//		-->Else , the checkbox will not be checked by default. 
//		-->Whereas its upto the user , who can decide whether the item requires prior auth or not.Hence forth he can modify “Send For Prior Auth” to be checked.
		
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		consultMgmt.saveConsultationAndMgmt("Diagnosis");

//		3.Go to Prior Auth Prescriptions List. Select the patient record and click on edit prior auth prescription.
		navigation.navigateTo(driver, "SendForPriorAuthListLink", "PriorAuthPrescriptionsListPage");

//		4.The Prescribed items from the consultation screen flows to the Prior Auth Prescriptions List.
//		5.Add one more test in the prior auth prescription screen , mark for send for prior auth, save.
//		6.Add one more service , and do not mark check 'send for prior auth' check box. 
//		7.Click on 'View Prior Auth Request XML'. Only the items which are marked for prior auth should be seen in the xml. 
//		8.Click on send prior auth request. User will get a pop up, Prior Authorization Request will be Sent Do you want to proceed with the prescribed activities. ? Click on Ok.
		
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		
		search.searchMRNo("PriorAuthPrescriptionsListMRNoField", "PriorAuthPrescriptionsListMRNoList", "PriorAuthPrescriptionsListSearchBtn", "PriorAuthPrescriptionsListResultList");
		executeStep.performAction(SeleniumActions.Click, "","PriorAuthPrescriptionsListResultList");
		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthPrescriptionsEditMenu",false);		

		executeStep.performAction(SeleniumActions.Click, "","PriorAuthPrescriptionsEditMenu");
		verifications.verify(SeleniumVerifications.Opens, "","PriorAuthPage",false);		
		
		PriorAuth priorAuth = new PriorAuth(executeStep, verifications);
		priorAuth.addItems();
		
		executeStep.performAction(SeleniumActions.CloseTab, "","PriorAuthPage");
		verifications.verify(SeleniumVerifications.Closes, "","PriorAuthPage",false);	


		navigation.navigateTo(driver, "SendForPriorAuthListLink", "PriorAuthPrescriptionsListPage");
		//MRNoSearch search = new MRNoSearch(executeStep, verifications);		
		search.searchMRNo("PriorAuthPrescriptionsListMRNoField", "PriorAuthPrescriptionsListMRNoList", "PriorAuthPrescriptionsListSearchBtn", "PriorAuthPrescriptionsListResultList");
		executeStep.performAction(SeleniumActions.Click, "","PriorAuthPrescriptionsListResultList");
		verifications.verify(SeleniumVerifications.Appears, "","PriorAuthPrescriptionsEditMenu",false);	
		executeStep.performAction(SeleniumActions.Click, "","PriorAuthPrescriptionsEditMenu");
		verifications.verify(SeleniumVerifications.Opens, "","PriorAuthPage",false);		

		//PriorAuth priorAuth = new PriorAuth(executeStep, verifications);
		priorAuth.sendForPriorAuth();
		
		CommonUtilities.delay(300000); /// Waiting for the response from Shafafiya WebSite			
		
//		9.Go to Prior Auth Approvals List and see the request sent . 

		navigation.navigateTo(driver, "PriorAuthApprovalLink", "PriorAuthApprovalPage");
		//MRNoSearch search = new MRNoSearch(executeStep, verifications);		
		search.searchMRNo("PriorAuthPrescriptionsListMRNoField", "PriorAuthPrescriptionsListMRNoList", "PriorAuthPrescriptionsListSearchBtn", "PriorAuthPrescriptionsListResultList");
		//PriorAuth priorAuth = new PriorAuth(executeStep, verifications);
		priorAuth.verifyPriorAuthApprovalStatus();
		testCaseStatus = "Pass";

		System.out.println("TS_070 completed Successfully");
		////***/
	}
	
	@AfterClass
	public void closeBrowser(){
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "PriorAuthWorkflow", null, "", "", testCaseStatus);
		driver.close();
	}
}
