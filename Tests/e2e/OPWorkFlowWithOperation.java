/**
 * 
 */
package e2e;

import java.util.List;

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

import genericFunctions.CommonUtilities;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.BedAllocation;
import reusableFunctions.BedFinalisation;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.Deposits;
import reusableFunctions.DischargeSummary;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.DrOrder;
import reusableFunctions.Notes;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.OTRecord;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.OperationDetails;
import reusableFunctions.OperationScheduler;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientWard;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Sales;
import reusableFunctions.SearchBillList;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Reena
 *
 */
public class OPWorkFlowWithOperation {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus="FAIL";
	
//	@BeforeSuite
	//public void BeforeSuite(){
	private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPWorkFlowWithOperation";
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
	
//	@BeforeMethod
	//public void BeforeTest(){
	private void login(){
		AutomationID = "Login - OPWorkFlowWithOperation";
		DataSet = "TS_006";	
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();
		System.out.println("After Login");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
	//	verifications.verify(SeleniumVerifications.Appears, "", "NavigationMenu", false); 
	}
	
	@Test(groups={"E2E","Regression"})

	public void OPWorkFlowWithOperation(){
		openBrowser();
		login();
		DataSet = "TS_006";
		AutomationID = "OPWorkFlowWithOperation";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 

				
				
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	   navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		
		Registration OPReg = new Registration(executeStep, verifications);
		
		OPReg.RegisterPatientGenericDetails();
		OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.unCheckPrimarySponsor();
		OPReg.setBillType();
		OPReg.visitInformationDetails();
		OPReg.storeDetails();
		
		
		
		executeStep.performAction(SeleniumActions.Click, "","ProceedToBillingLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
		
	
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBillDetails("NO","BillPaymentType");
	
		//SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		consultMgmt.doConsultationAndMangement();
		
	//	SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		order.addOrderItem("OrderItem","Operation");
		
		order.saveOrder(true);
		
		
		// Prescribe an operation in OP consultation
	    navigation.navigateTo(driver, "PlannedOperationsLink", "PlannedOperationListPage");
		MRNoSearch MRNOSearch = new MRNoSearch(executeStep, verifications);
		MRNOSearch.searchMRNo("PendingServicesListPageMRNoField", "PendingServicesListPageMRNoList", "PendingServicesListPageSearchButton", "PlannedOperationListPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PlannedOperationListPageClick");
		verifications.verify(SeleniumVerifications.Appears, "","PendingServicesListPageOTManagementLink",false);
		executeStep.performAction(SeleniumActions.Click, "","PendingServicesListPageOTManagementLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","OTManagementPageOperationLink");
		verifications.verify(SeleniumVerifications.Appears, "","OperationDetailsPage",false);
	
				
		OperationDetails operationDetails = new OperationDetails(executeStep, verifications);
		operationDetails.saveOperation();
		
		executeStep.performAction(SeleniumActions.Click, "","OperationDetailsPageOTManagementLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);
		
				
		executeStep.performAction(SeleniumActions.Click, "","OTManagementPageOTFormLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTFormsListPage",false);
		
		//. Select the form , add the details and save the form
		executeStep.performAction(SeleniumActions.Click, "","OTFormsListPageFormName");
		verifications.verify(SeleniumVerifications.Appears, "","OTRecordPage",false);
		
		OTRecord otRecord = new OTRecord(executeStep, verifications);
		otRecord.otRecordEntry();
		
		executeStep.performAction(SeleniumActions.Click, "","OTRecordPageOTManagementLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);
	

		executeStep.performAction(SeleniumActions.Click, "","OTManagementAddEditLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientOperationDocumentPage",false);
		
		// Select the doc, add the doc and save
		executeStep.performAction(SeleniumActions.Click, "","PatientOperationDocumentPageRichTextRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientOperationDocumentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientOperationDocumentPageAddButton");
		verifications.verify(SeleniumVerifications.Appears, "","AddRichTextDocumentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddRichTextDocumentPageSaveButton");
		verifications.verify(SeleniumVerifications.Opens, "","OperationDocumentsPrintPage",false);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","OperationDocumentsPrintPage");
		verifications.verify(SeleniumVerifications.Closes, "","OperationDocumentsPrintPage",false);
		
		//Click on patient document in the same screen and select the attached document and 
		// click on sign off.
		executeStep.performAction(SeleniumActions.Click, "","AddRichTextDocumentPagePatientDocumentLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientOperationDocumentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientOperationDocumentPageSignOffCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","PatientOperationDocumentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientOperationDocumentPageSignOffButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientOperationDocumentPage",false);
	
		
			
		 navigation.navigateTo(driver, "DischargeSummaryLink", "DischargeSummaryPage");
         MRNoSearch search = new MRNoSearch(executeStep, verifications);
         
 	 	 search.searchMRNo("DischargeSummaryMRNoField","DischargeSummaryMRContainerField","DischargeSummaryFindButton","DischargeSummaryPage");
 		
	     verifications.verify(SeleniumVerifications.Appears, "","DischargeSummaryPage",false);
		 DischargeSummary DisSummary = new DischargeSummary(executeStep, verifications);
		 DisSummary.doDischargeSummary();
		

			//Observe the documents in Patient/Visit EMR
		
			navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
            search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
			verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
			
			
			
			//close the bill
			
			    navigation.navigateTo(driver, "SearchOpenBills", "BillListPage");
				MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
				mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");
				
     //	PatientBill patientBill = new PatientBill(executeStep, verifications);	
				patientBill.viewEditBills("BN");
				executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
				patientBill.settleBillDetails("NO","BillPaymentType");
				testCaseStatus="PASS";
				 System.out.println("OPWorkFlowWithOperation-Completed");

	
		
}
	@AfterClass
	public void closeBrowser(){
	reporter.UpdateTestCaseReport(AutomationID, "TS_006", "OPWorkFlowWithOperation", null, "", "", testCaseStatus);
	driver.close();
	}
		
	
}
