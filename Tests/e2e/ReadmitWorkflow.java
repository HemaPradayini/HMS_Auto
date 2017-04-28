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

public class ReadmitWorkflow {

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
		AutomationID = "Login - ReadmitWorkflow";
		DataSet = "TS_048";
		//EnvironmentSetup.testScenarioId = "TS_001";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - ReadmitWorkflow";
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
	public void ReadmitWorkflow(){
		openBrowser();
		login();
		
		DataSet = "TS_048";
		AutomationID = "ReadmitWorkflow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test ReadmitWorkflow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		//1.Go to OP Registration screen, enter all mandatory fields at patient level and visit level.
		
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.RegisterPatientGenericDetails();
		
		
		OPReg.setBillType();
		OPReg.unCheckPrimarySponsor();
		OPReg.visitInformationDetails();
		executeStep.performAction(SeleniumActions.Select, "RatePlan","OPRScreenRatePlanField");
		verifications.verify(SeleniumVerifications.Selected, "RatePlan","OPRScreenRatePlanField",false);
		OPReg.storeDetails();
		
		//2a. Go to Consultation and prescribe all the items such as medicines(having package size 1 and 10 and serial items)
		//by giving quantity as 5 each, Lab, rad, operation, consultation. 
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		
		//Prescribe all types of items like medicines(serial and batch items having packet size 1 and 10) with quantity as 5,Lab,rad,services,diag packages and save the consultation screen
		consultMgmt.saveConsultationAndMgmt("Diagnosis");
		
		//2b. Go to order screen and add the prescribed items as well as other billable items
		Order order = new Order(executeStep, verifications);            // added by me now
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		order = new Order(executeStep, verifications);
		order.searchOrder(false,true);                                           // added by Abhishek
		
		order.addOrderItem("OrderItem", "Operation");
		//order.addOrderItem("OrderItem", "OrdersPage");
		order.saveOrder(false);
		
		//************************************
		executeStep.performAction(SeleniumActions.Accept, "","Framework");  // check this
		//******************************************
		
		//2c Enter all required parameters in consultation/ Triage/ IA/ Generic forms Screens
		
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
		
		executeStep.performAction(SeleniumActions.Click, "","ConsulationMgtScreenTriageLink");
		verifications.verify(SeleniumVerifications.Appears, "","TriagePage",true);

		Triage triage = new Triage(executeStep, verifications);
		triage.saveTriage();

		OpenGenericForm opGenericForm = new OpenGenericForm(executeStep, verifications);
		opGenericForm.openGenericForm();
		
		//3. Go to sales screen. Enter MR number. Select bill type as Raise Bill and complete sales by clicking on raise Bill & Print.
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.payAndPrint(false);
		
		
	// 4 Go to Sales Returns screen, enter the patient's MR number. Add items to the grid by giving 3 quantiity each. Click on Add to Bill.
		//4. Go to Sales Returns screen, enter the patient's MR number. Add items to the grid by giving 3 quantity each. Click on Raise Bill to complete sales returns.
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("SalesReturns","OP");
		
		//5. Go to Patient Indent screen, enter the MR Number. Raise a patient indent by adding serial and batch items (having packet size 1 and 10)
		//by giving quantity as 5. Save this indent in finalized satus.
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		
		raisePatientIndent.savePatientIndent("IndentSales",true);
		
		//6. Go to Patient Indents List under Sales and Issues. Select the above genarated indent and click on Sales.
		//Complete sales by clicking on Raise Bill & Print.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "IndentSales");
		
		//7a. Go to Add Patient Return Indent screen under Sales and Issues, enter the MR number and add medicines by 
		//giving 3 quantity each. Save this indent in finalized status.
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");
		raisePatientIndent.savePatientIndent("ReturnedSalesIndent",false);
		
		//7b. Go to Patient Indents List under Sales and Issues. Select the above genarated return indent and click 
		//on Sales Returns.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		raisePatientIndent.doSalesOrSalesReturnsForIndent(false, "");
		
		//8. Complete sales returns by clicking on Raise Bill & Print.
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		
		salesReturns.doSalesReturn("SalesReturns","OP");
		
		// 9a.Go to Laboratory Pending samples screen, select the test and click on collect menu option. 
		//Select the samples by ticking the check boxes and click on Save.

	/*	           // added by me now
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
		order = new Order(executeStep, verifications);
		order.searchOrder(true,false);                                           // added by Abhishek
		
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
		verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		
	*/
		
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		  MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		  Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		  
		  executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		  verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		  
		  executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		  verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		      
		  //collect samples and save
		  CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		  collectSamples.collectSamplesAndSave();
		//  executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		//  verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		  
		//9b. Go to laboratory reports screen > 
		//select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
		
	/*	navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();
		*/                         //commented on 14/4
		  
		  navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
			MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
			executeStep.performAction(SeleniumActions.Click, "","LabReportsReportCheckBox");
			executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageResultTable");
			verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);

			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
			verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
			TestsConduction testConduction = new TestsConduction(executeStep, verifications);
			testConduction.conductTests();
		
		// 9c and 9d 
		//9c 9c.Go to radiology reports > Select the test and click on View/Edit, choose a template then edit details and save.
		//Mark complete,validate and signoff.
	    navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);
		
		//9d.Go to Pending Services List, select the service and click on edit menu option mark completed and save.
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();
		
		//9EGo to planned operation list, select the operaion--> Edit--> OT management --> Complete the operation
		
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

			/*
			executeStep.performAction(SeleniumActions.Click, "","OperationDetailsPageOTManagementLink");
			verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);
			
			
			executeStep.performAction(SeleniumActions.Click, "","OTManagementPageOTFormLink");
			verifications.verify(SeleniumVerifications.Appears, "","OTFormsListPage",false);
		*/
		
		// step 10 Go to Patient &Visit EMR and Check the reports
		
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);

		//11. Now go to Edit Visit Details screen, under registration Module and Close The Visit by checking close visit check box and save the screen.
		navigation.navigateTo(driver, "EditPatientVisit", "EditPatientVisitPage");
		PatientVisit changecateg=new PatientVisit(executeStep, verifications);
        changecateg.closeVisit();
        
       // 12. Now go to 'Readmit Patient' screen from the hyperlink provided at the bottom of Edit visit details screen only.
        executeStep.performAction(SeleniumActions.Click, "","EditPatientVisitDetailsPageReAdmitLink");
        verifications.verify(SeleniumVerifications.Appears, "","ReAdmitPatientPage",false);
        
        //13. click on the visit Id of the patient in 'Readmit screen' and save.
        executeStep.performAction(SeleniumActions.Click, "","ReAdmitPatientVisitIDLink");
        verifications.verify(SeleniumVerifications.Appears, "","ReAdmitPatientPage",false);
        
        //14. Order few more tests bill order and try to Conduct the pending and new tests the same ways as mentioned from step 9.a to 9.d.
        navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
        
        order.searchOrder(false,false);                                           // added by Abhishek
		
		order.addOrderItem("OrderLabItem", "LaboratoryOrderTestsPage");
		order.saveOrder(false);
		
		// 14a.Go to Laboratory Pending samples screen, select the test and click on collect menu option. 
				//Select the samples by ticking the check boxes and click on Save.

		/*		           // added by me now
				navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
				order = new Order(executeStep, verifications);
				order.searchOrder(true,false);                                           // added by Abhishek
				
				executeStep.performAction(SeleniumActions.Click, "","OrdersScreenCollectSample");
				verifications.verify(SeleniumVerifications.Opens, "","CollectSamplePage",false);
				
				
				collectSamples.collectSamplesAndSave();
				executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
				verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
		*/
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		//  MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		  Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
		  
		  executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		  verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
		  
		  executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		  verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		      
		  //collect samples and save
		//  CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		  collectSamples.collectSamplesAndSave();
		
				
				//14b. Go to laboratory reports screen > 
				//select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
		/*		
				navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
				
				search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
				executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
				verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
				
				executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
				verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
				
				testConduction.conductTests();
			*/
		  
		  navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
		//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
			executeStep.performAction(SeleniumActions.Click, "","LabReportsReportCheckBox");
			executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageResultTable");
			verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);

			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
			verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
			//TestsConduction testConduction = new TestsConduction(executeStep, verifications);
			testConduction.conductTests();
				
				// 9c and 9d 
				//14c Complete,validate and sign off the pending radiology test
			    navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
				
				radiologyPendingTests.radiologyPending(true);
				
				//14d Complete the pending service test
				navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
				
				servicePending.conductPendingServices();
		
				//15. Try to perform all billing transaction On bill after readmitting
				
				navigation.navigateTo(driver, "NewBillLink", "NewBillPage");
					PatientBill patientBill = new PatientBill(executeStep, verifications);	
					patientBill.createBill();
					
					patientBill.addItemsIntoPatientBill();
					
				//doing settlement , closing bill.
			   navigation.navigateTo(driver, "OpenBills", "BillListPage");
				patientBill.settleBills("NO", "UNPAID", "OP");
				testCaseStatus = "Pass";
				System.out.println("TS_048 - completed");
}
	
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "ReadmitWorkflow", null, "", "", testCaseStatus);
		driver.close();
	}
}