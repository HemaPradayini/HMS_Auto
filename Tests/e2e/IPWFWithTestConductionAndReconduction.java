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
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.Notes;
import reusableFunctions.OPListSearch;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
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
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author C N Alamelu
 *
 */
public class IPWFWithTestConductionAndReconduction {
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
		AutomationID = "Login - IPWFWithTestConductionAndReconduction";
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
		AutomationID = "IPWFWithTestConductionAndReconduction";
		DataSet = "TS_022";
		
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
	public void IPWFWithTestConductionAndReconduction(){
		openBrowser();
		login();
		
		DataSet = "TS_022";
		AutomationID = "IPWFWithTestConductionAndReconduction";
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
						
		//4. Go back to IP case sheet and click on Nurse Notes and enter nurse notes and save the screen
			Notes.RecordNurseNotes();		
			
			
		// 5. Go back to IP Cassheet and click on Doctor order and prescribe all types of items such as 
		// Medicines(having package size as 1 and 10 and serial items) , Service, Investigation, 
		//Care plan(IP template package), Consultation and Others
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
			//executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		//	verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);
			
		//7b.Go to laboratory reports screen > select the test and click on View/Edit, enter values 
		//(1,2,3,4,etc) complete,validate.
			
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
				testConduction.conductTestsWithoutSignoff();
		
				
		//7c.Go back to Lab report dashboard, select the test and click on Reconduct menu option.
		//Select the test to be reconducted and click on Save.
			
			navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
			//TestsConduction testConduction = new TestsConduction(executeStep, verifications);
			testConduction.reconductTest();
		
	
		//7d. Again go to Lab report dashboard, select the test and click on View/Edit, enter values 
		//(1,2,3,4,etc) complete,validate and signoff.
			navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
		//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
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
			
		//	TestsConduction testConduction = new TestsConduction(executeStep, verifications);
			testConduction.conductTests();
					
		//7e.Go to radiology reports > Select the test and click on View/Edit, choose a template then 
		//edit details and save. Mark complete,validate.
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
			radiologyPendingTests.radiologyReportWithoutSignOff();
			
		
		//7f.Go back to Radiology report dashboard, select the test and click on Reconduct menu option.
		//Select the test to be reconducted and click on Save.
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		//	RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
			radiologyPendingTests.reconductRadiology();
						
		//7g. Again go to Radiology report dashboard, select the test and click on View/Edit, choose a 
		//template then edit details and save.Mark complete,validate and signoff.
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
	//		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
			//radiologyPendingTests.radiologyReportSignOff();
			radiologyPendingTests.radiologyPending(true);
			
		//7h.Go to Pending Services List, select the service and click on edit menu option mark completed 
		// and save.
			
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
				
		//		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
				collectSamples.collectSamplesAndSave();
				
		//8b.Go to laboratory reports screen > select the test and click on View/Edit, enter values 
		//(1,2,3,4,etc) complete,validate.
				navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
				//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
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
					
				//	TestsConduction testConduction = new TestsConduction(executeStep, verifications);
					testConduction.conductTests();		
		
			//8c.Go to radiology reports > Select the test and click on View/Edit, choose a template then 
			//edit details and save. Mark complete,validate.
				navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			//	RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
				radiologyPendingTests.radiologyPending(true);
		
		//9a. Go to Add Patient Indent screen under Sales and Issues, enter the MR Number, Click on 
		//OK for the alert displayed. Save this indent in Finalized status after the medicines ordered 
		//are auto-filled.
				navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
				
				RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
				raisePatientIndent.savePatientIndent("IndentSales", true);		
		
		//9b. Go to Patient Indents List under Sales and Issues. Select the above generated indent and 
		//click on Issue.
		//9c. Complete issues by clicking on Save.
				
				navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
			//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
				raisePatientIndent.searchPatientIndent();
				
				raisePatientIndent.savePatientIndentIssue(true);
			
		//10. Come back to patient ward activities and select all the activities and mark as done
				navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
				//MR No search in Inpatient List page
		//		IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
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
				
		DbFunctions dbFunctions = new DbFunctions();
		dbFunctions.storeDate(this.executeStep.getDataSet(), "ShiftEndDate","C",0);
				
			
		//11. In pateint's bill, finalize the bed using Bed Details link and also finalize the bill 
		// and select 'Ok to discharge' check box.
			
			navigation.navigateTo(driver, "ADTLink", "ADTPage");
			BedFinalisation FinaliseBed = new BedFinalisation(executeStep, verifications);
			FinaliseBed.finaliseBed();
			
			navigation.navigateTo(driver, "BillsLink", "BillListPage");			
			PatientBill patientBill = new PatientBill(executeStep, verifications);		
			patientBill.settleBills("NO", "UNPAID", "IP");
			
		//12. Go to Discharge under ADT and discharge the patient
				navigation.navigateTo(driver, "DischargeLink", "PatientDischargePage");
				MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
				mrnoSearch.searchMRNo("PatientDischargeMRNoField", "PatientDischargeMRNoLi", "PatientDischargeFindButton", "PatientDischargePage");

				PatientDischarge patientDischarge = new PatientDischarge(executeStep, verifications);		
				patientDischarge.patientDischarge();
			
		//13.Check EMR	
			navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
	//		MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
			verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
			testCaseStatus="PASS";
			System.out.println("End Test IPWF with test conduction and reconduction");
	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_022", "IPWFWithTestConductionAndReconduction", null, "", "", testCaseStatus);
		
		driver.close();
	}
		
}
