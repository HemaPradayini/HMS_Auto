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
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.BedAllocation;
import reusableFunctions.BedFinalisation;
import reusableFunctions.ClaimsSubmission;
import reusableFunctions.Codification;
//import reusableFunctions.ClaimsSubmission;
//import reusableFunctions.Codification;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.DrOrder;
import reusableFunctions.IPListSearch;
import reusableFunctions.IPRecord;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.Notes;
import reusableFunctions.OPListSearch;
import reusableFunctions.OTRecord;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.OperationDetails;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientDischarge;
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientIssue;
import reusableFunctions.PatientWard;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.SearchBillList;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Triage;
import reusableFunctions.Vitals;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author C N Alamelu
 *
 */
public class IPWorkflowByOrderingAllItemsFromWA {
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
		AutomationID = "Login - IPWorkflowByOrderingAllItemsFromWA";
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
		AutomationID = "IPWorkflowByOrderingAllItemsFromWA";
		DataSet = "TS_021";
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		// Delay is introduced to handle the sync problem happening because of two 
		//consecutive close tours and no verification possible
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void IPWorkflowByOrderingAllItemsFromWA(){
		openBrowser();
		login();
		
		DataSet = "TS_021";
		AutomationID = "IPWorkflowByOrderingAllItemsFromWA";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForInsurance - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);	

		navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
		
		// 1. Register an IP patient

			Registration IPReg = new Registration(executeStep, verifications);
			IPReg.RegisterPatientCashIP();
	
				
		//2. Go to In Patient List--> Select the registered IP patient and click on IP case sheet	
			navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
						
			IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
			IPSearch.searchIPList();
						
		//3. In IP case sheet, click on Doctor Notes and enter doctor notes, select 
		// 'BILL' check box , and consultation types as wll as the doctor name and save
							
			Notes Notes = new Notes(executeStep, verifications);
			Notes.RecordDrNotes();
						
		//4a. Go back to IP case sheet and click on Nurse Notes and enter nurse notes and save the screen
			Notes.RecordNurseNotes();		
		
		//4b. Go to Vitals from IP case sheet and enter the vitals parameters
			executeStep.performAction(SeleniumActions.Click, "", "IPCaseSheetVitalsLink");
			verifications.verify(SeleniumVerifications.Appears, "","VitalMeasurementsPage",false);
			
			executeStep.performAction(SeleniumActions.Click, "","VitalMeasurementsAddItemButton");
			verifications.verify(SeleniumVerifications.Appears, "Pulse","ConsultMgtScreenVitalsDialog",false);
			Vitals vitals = new Vitals(executeStep, verifications);
			vitals.recordVitals();
			executeStep.performAction(SeleniumActions.Click, "","VitalMeasurementsSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","VitalMeasurementsPage",true);
			
			executeStep.performAction(SeleniumActions.Click, "","VitalMeaasurementIPPatientListLink");
			verifications.verify(SeleniumVerifications.Appears, "","InPatientListPage",false);
			
		//	IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
			IPSearch.searchIPList();
			
		//4c. Go to Intake/output from IP case sheet , and enter the intake output paramters and save
		// This part has manu xpaths reused from Vitals
			executeStep.performAction(SeleniumActions.Click, "", "IPCaseSheetIntakeLink");
			verifications.verify(SeleniumVerifications.Appears, "","IntakeOutputMeasurementsPage",false);
			executeStep.performAction(SeleniumActions.Click, "","VitalMeasurementsAddItemButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddIntakeOutputReadingPage",false);
			
			executeStep.performAction(SeleniumActions.Enter, "Urine","AddIntakeOutputReadingUrine");
			verifications.verify(SeleniumVerifications.Entered, "Urine","AddIntakeOutputReadingUrine",false);
			
			executeStep.performAction(SeleniumActions.Click, "", "AddIntakeOutputReadingOKButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddIntakeOutputReadingPage",false);
			
			executeStep.performAction(SeleniumActions.Click, "", "AddIntakeOutputReadingCloseButton");
			verifications.verify(SeleniumVerifications.Appears, "","IntakeOutputMeasurementsPage",false);
			
			//xpath reused
			executeStep.performAction(SeleniumActions.Click, "", "VitalMeasurementsSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","IntakeOutputMeasurementsPage",false);
			
			executeStep.performAction(SeleniumActions.Click, "","VitalMeaasurementIPPatientListLink");
			verifications.verify(SeleniumVerifications.Appears, "","InPatientListPage",false);
			
		//	IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
			IPSearch.searchIPList();
			
		//	4d. Go to IP record from IP case sheet and enter the data in all the sections.
			IPRecord ipRecord = new IPRecord(executeStep, verifications);
			ipRecord.ipRecordCreation();
			
		// 4e. Go to generic form and observe the data which are prepopulated and also enter the data in 
		//all the sections and save the screen. 
		  navigation.navigateTo(driver, "AddPatientGenericFormLink", "GenericFormPage");
		  OpenGenericForm openGenericForm = new OpenGenericForm(executeStep, verifications);
		  openGenericForm.openGenericFormFromIP();
			
		// 5. Go back to IP Cassheet and click on Doctor order and prescribe all types of items such as 
		  // Medicine,Service,Investigation, Care plan(IP template package) ,Consultation and Others
		  	navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
			
		//	IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
			IPSearch.searchIPList();
			executeStep.performAction(SeleniumActions.Click, "","IPCaseSheetDrOrderLink");
			verifications.verify(SeleniumVerifications.Appears, "","DrOrderScreen",false);
		
			DrOrder DrOrderDetails = new DrOrder(executeStep, verifications);
			DrOrderDetails.AddItems();
			DrOrderDetails.SaveDrOrder();
			
		//6. Click on Patient ward activities and select the items such as Service,Investigation and 
		//select the 'Order' check box and save
			
			executeStep.performAction(SeleniumActions.Click, "","DrOrderPatientWardLink");
			verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",false);
			
			PatientWard PatientWardDetails = new PatientWard(executeStep, verifications);
			PatientWardDetails.OrderItems();	
			PatientWardDetails.SavePatientWard();
		
		//7.a.Go to Laboratory Pending samples screen, select the test and click on collect menu option.
		//Select the samples by ticking the check boxes and click on Save.
			navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
			MRNoSearch Search = new MRNoSearch(executeStep, verifications);
			Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
			
			executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
			verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
			
			executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
			verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
			
			CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
			collectSamples.collectSamplesAndSave();
			
		//7b.Go to laboratory reports screen > select the test and click on View/Edit, 
		// enter values (1,2,3,4,etc) complete,validate and Signoff.
			
			navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
			MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
			executeStep.performAction(SeleniumActions.Click, "","LabReportsReportCheckBox");
			executeStep.performAction(SeleniumActions.Click, "","LabReportsReportRow");
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			
			verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
			verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
			
			TestsConduction testConduction = new TestsConduction(executeStep, verifications);
			testConduction.conductTests();
						
		//7c.Go to radiology reports > Select the test and click on View/Edit, choose a template 
		// then edit details and save. Mark complete,validate and signoff.
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
			//radiologyPendingTests.radiologyReportSignOff();
			radiologyPendingTests.radiologyPending(true);
			
		//7d.Go to Pending Services List, select the service and click on edit, and observe the data 
		//which are already filled and  enter the data in all the section and mark completed and save.
			
			navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
			ServicePending servicePending = new ServicePending(executeStep, verifications);
			servicePending.conductPendingServices();
				
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			
			}
			
		//8..Also order extra items in order screen and complete the same in respective modules.
			navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
			Order order = new Order(executeStep, verifications);
			order.searchOrder(false,false);
			order.addOrderItem("OrderScreenItem", "OrdersPage");
			order.saveOrder(false);
			
		//8.a.Go to Laboratory Pending samples screen, select the test and click on collect menu option.
		//Select the samples by ticking the check boxes and click on Save.
				navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
	//			MRNoSearch Search = new MRNoSearch(executeStep, verifications);
				Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
				
				executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
				verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
				
				executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
				verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
				
	//			CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
				collectSamples.collectSamplesAndSave();
				
		//8b.Go to laboratory reports screen > select the test and click on View/Edit, enter values 
		//(1,2,3,4,etc) complete,validate.
				navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
		//		MRNoSearch search = new MRNoSearch(executeStep, verifications);
				search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
				executeStep.performAction(SeleniumActions.Click, "","LabReportsReportCheckBox");
				executeStep.performAction(SeleniumActions.Click, "","LabReportsReportRow");
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
				
				verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
				executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
				verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
				
		//		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
				testConduction.conductTests();		
			
			//8c.Go to radiology reports > Select the test and click on View/Edit, choose a template then 
			//edit details and save. Mark complete,validate.
				navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
	//			RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
				radiologyPendingTests.radiologyPending(true);
		
		//9a. Go to Add Patient Indent screen under Sales and Issues, enter the MR Number, Click on OK 
		// for the alert displayed. Save this indent in Finalized status after the medicines ordered are 
		//auto-filled.
				navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
				
				RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
				raisePatientIndent.savePatientIndent("IndentSales", true);		
		
		//9b. 9b. Go to Patient Indents List under Sales and Issues. Select the above generated indent 
		// and click on Issue.
		//9c. Complete issues by clicking on Save.
				
				navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
			//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
				raisePatientIndent.searchPatientIndent();	
				raisePatientIndent.savePatientIndentIssue(true);
			
		//10. Come back to patient ward activities and select all the activities and mark as done
				navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
				//MR No search in Inpatient List page
	//			IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
				IPSearch.searchIPList();
				
				executeStep.performAction(SeleniumActions.Click, "","IPCaseSheetDrOrderLink");
				verifications.verify(SeleniumVerifications.Appears, "","DrOrderScreen",false);
				executeStep.performAction(SeleniumActions.Click, "","DrOrderPatientWardLink");
				verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",false);
	//			PatientWard PatientWardDetails = new PatientWard(executeStep, verifications);
				PatientWardDetails.CloseItems();
				PatientWardDetails.SavePatientWard();
						
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				
				}

			
		// 11. Order an operation from Order screen and go to OT management screen from planned operation 
		// list dash board.
		  
		navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
	//	Order order = new Order(executeStep, verifications);
		order.searchOrder(false,false);
		order.addOrderItem("OrderSurgery", "Operation");
		order.saveOrder(false);
			
		navigation.navigateTo(driver, "PlannedOperationsLink", "PlannedOperationListPage");
		MRNoSearch MRNOSearch = new MRNoSearch(executeStep, verifications);
		MRNOSearch.searchMRNo("PendingServicesListPageMRNoField", "PendingServicesListPageMRNoList", "PendingServicesListPageSearchButton", "PlannedOperationListPage");
		
		executeStep.performAction(SeleniumActions.Click, "","PlannedOperationListPageClick");
		verifications.verify(SeleniumVerifications.Appears, "","PendingServicesListPageOTManagementLink",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingServicesListPageOTManagementLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);
	 	
				
		//12. Click on OT forms and observe the data which are already filled and  enter the data in all 
		// the section and save.
		executeStep.performAction(SeleniumActions.Click, "","OTManagementPageOTFormLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTFormsListPage",false);
		
		//. Select the form , add the details and save the form
		executeStep.performAction(SeleniumActions.Click, "","OTFormsListPageFormName");
		verifications.verify(SeleniumVerifications.Appears, "","OTRecordPage",false);
		
	
		OTRecord otRecord = new OTRecord(executeStep, verifications);
		otRecord.otRecordEntry();
		 
		
		 //13. Go to operation link from operation managment and mark it as completed
		executeStep.performAction(SeleniumActions.Click, "","OTRecordPageOTManagementLink");
		verifications.verify(SeleniumVerifications.Appears, "","OTManagementPage",false);
		 
		executeStep.performAction(SeleniumActions.Click, "","OTManagementPageOperationLink");
		verifications.verify(SeleniumVerifications.Appears, "","OperationDetailsPage",false);
		
		OperationDetails operationDetails = new OperationDetails(executeStep, verifications);
		operationDetails.saveOperation();	
		
		DbFunctions dbFunctions = new DbFunctions();
		dbFunctions.storeDate(this.executeStep.getDataSet(), "ShiftEndDate","C",0);
		
					
		//14. In pateint's bill, finalize the bed using Bed Details link and also finalize the bill and 
		// select 'Ok to discharge' check box.
			
			navigation.navigateTo(driver, "ADTLink", "ADTPage");
			BedFinalisation FinaliseBed = new BedFinalisation(executeStep, verifications);
			FinaliseBed.finaliseBed();
			
			navigation.navigateTo(driver, "BillsLink", "BillListPage");			
			PatientBill patientBill = new PatientBill(executeStep, verifications);		
			patientBill.settleBills("NO", "UNPAID", "IP");
			
		//15. Go to Discharge under ADT and discharge the patient
				navigation.navigateTo(driver, "DischargeLink", "PatientDischargePage");
				MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
				mrnoSearch.searchMRNo("PatientDischargeMRNoField", "PatientDischargeMRNoLi", "PatientDischargeFindButton", "PatientDischargePage");

				PatientDischarge patientDischarge = new PatientDischarge(executeStep, verifications);		
				patientDischarge.patientDischarge();
			
				testCaseStatus="PASS";
				System.out.println("End Test IPWF By ordering All items from WA");
	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_021", "IPWorkflowByOrderingAllItemsFromWA", null, "", "", testCaseStatus);
		
		driver.close();
	}
	
		
}
