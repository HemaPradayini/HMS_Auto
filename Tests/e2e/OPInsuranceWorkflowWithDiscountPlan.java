/**
 * 
 */
package e2e;

import java.util.List;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Reena
 *
 */
public class OPInsuranceWorkflowWithDiscountPlan {
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
		AutomationID = "Login - OPInsuranceWorkflowWithDiscountPlan";
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
		AutomationID = "Login - AppointmentToBilling";
		DataSet = "TS_051";
		
		
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
	public void OPInsuranceWorkflowWithDiscountPlan(){
		openBrowser();
		login();
		DataSet = "TS_051";
		AutomationID = "OPInsuranceWorkflowWithDiscountPlan";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPInsuranceWorkflowWithDiscountPlan - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		

		//OP Registration

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		Registration OPReg = new Registration(executeStep, verifications);
	    Upload uploadfile=new Upload(executeStep, verifications);
		
		  
		    OPReg.RegisterPatientGenericDetails();
		    OPReg.GovtIDDetailsCollapsedPanel();
	       OPReg.setBillType();
	       OPReg.RegisterPatientInsurance("OP");
	       String registrationupload="//input[@id='primary_insurance_doc_content_bytea1' and @type='file']";
	       uploadfile.upload(registrationupload);
		   
	       OPReg.visitInformationDetails();
	       //Enter the order on the Registration Screen 
	        Order order = new Order(this.executeStep, this.verifications);
			executeStep.performAction(SeleniumActions.Click, "", "OrdersScreenAddButton");
			verifications.verify(SeleniumVerifications.Appears, "", "AddItemScreen", false);
			order.addOrderItem("RegItem","OPRegistrationScreen");
	       	 
			OPReg.storeAndEditBill();
		//Order items on Order Screen
			
			
			//SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
			navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		//	Order order = new Order(executeStep, verifications);
			order.searchOrder(false,false);
			order.addOrderItem("OrderItem","OrdersPage");
			order.saveOrder(false);
	
		//Click on Bill Link Available	//need to navigate from order screen
					
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenBillLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		
		PatientBill patientBill = new PatientBill(executeStep, verifications);	
		patientBill.addItemsIntoPatientBill();
		
		
		//Collect samples
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
	
		
	 //Complete,validate and sign off the pending lab test
		
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		//MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();

		//Complete,validate and sign off the pending radiology test
	    navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
	
		//Complete the pending service test
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		

       //Sale
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.doSales("SalesItem", "SalesPageBillType");
		//sales.payAndPrint(true);
		sales.closePrescriptionTab();
	
		
		
		//Navigate to Bill Screen view hospital bill
	
		  navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
		//	MRNoSearch Search = new MRNoSearch(executeStep, verifications);		
	//		Search.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");

	//	PatientBill patientBill = new PatientBill(executeStep, verifications);
	    		
			patientBill.viewEditBills("BN-P");
			
			//Issue
			 navigation.navigateTo(driver, "PatientIssueLink", "PatientIssuePage");
			PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
    		patientIssue.searchPatientIssue();
	    	patientIssue.addPatientIssueItem("IssueItem");
		    patientIssue.saveIssue();
		    
		    
		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
	//	MRNoSearch Search = new MRNoSearch(executeStep, verifications);		
		//Search.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");

	//	PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.viewEditBills("BL");
		    
		    
		    
		//sales return
		    navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
			SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
			salesReturns.doSalesReturn("SalesReturns", "OP");
			
			
		//navigate to pharmacy bill
		
		    navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
	//		MRNoSearch Search = new MRNoSearch(executeStep, verifications);		
			//Search.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");

	//		PatientBill patientBill = new PatientBill(executeStep, verifications);
			//patientBill.viewEditBill("BN-P");
			patientBill.viewEditBills("BN-P");
			
			//Complete Issue Returns
	
			navigation.navigateTo(driver, "PatientIssueReturnLink", "PatientIssueReturnPage");
		//	PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
			patientIssue.returnPatientIssueItem("IssueReturns");
			patientIssue.saveIssueReturns();
			
		//Navigate to bill and observe the inventory items in the bill
			 navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
	//		 MRNoSearch Search = new MRNoSearch(executeStep, verifications);		
			// Search.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");

			// PatientBill patientBill = new PatientBill(executeStep, verifications);
			 patientBill.viewEditBills("BL");
			 
		//Do sponsor and patient amount settlement in bills, close the bills. Observe the claim status.
	//	SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
		
		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");			
     //	PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.sponsorAmountSettlement("OPEN_UNPAID_INS");
		
	//	Patient Amount Settlement
		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");			
    	//PatientBill patientBill = new PatientBill(executeStep, verifications);
    	patientBill.settleBills("YES", "UNPAID", "OP");
    	
    	
    	//Settle refund bills present
    	 navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
    	// PatientBill patientBill = new PatientBill(executeStep, verifications);
   // 	 MRNoSearch Search = new MRNoSearch(executeStep, verifications);
    	 patientBill.advancedSearch(Search,"REFUND");
    	 patientBill.viewEditMenuClick();
    	 patientBill.settleBillDetails("YES", "BillRefundPaymentType");
    	
    	
    	//close the bills
		navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
   // 	PatientBill patientBill = new PatientBill(executeStep, verifications);	
    	patientBill.closeBill("FINALIZED_INS");
    	testCaseStatus="PASS";
        System.out.println("OPInsuranceWorkflowWithDiscountPlan-Completed");	
	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_051", "OPInsuranceWorkflowWithDiscountPlan", null, "", "", testCaseStatus);
		driver.close();
	}
		
}

