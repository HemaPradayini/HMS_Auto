/**
 * 
 */
package e2e;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
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
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

/**
 * @author Sai
 *
 */
public class OSPWorkFlowForCashPatient {
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
//		AutomationID = "Login - OSPWorkFlowForCashPatient";
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
//	public void BeforeTest(){
	private void login(){
		AutomationID = "Login - OSPWorkFlowForCashPatient";
		DataSet = "TS_032";
		//EnvironmentSetup.testScenarioId = "TS_001";

		System.out.println("BeforeSuite");
		AutomationID = "Login - OSPWorkFlowForCashPatient";
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
	public void OSPWorkFlowForCashPatient(){
		login();
		DataSet = "TS_032";
		AutomationID = "OSPWorkFlowForCashPatient";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OSPWorkFlowForCashPatient - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OSPatientRegistrationLink", "OSPatientRegistrationScreen");
	

		//From here its for Entering details on the OP Registration Screen - Move to another reusable
			Registration OPReg = new Registration(executeStep, verifications);
			OPReg.RegisterPatientGenericDetails();		
			DiagnosisDetails diagDetails = new DiagnosisDetails(executeStep, verifications);
			diagDetails.addDianosisDetails("Diagnosis", "OSPatientRegistrationScreen");
			OPReg.storeDetails();
			executeStep.performAction(SeleniumActions.Click, "","OSPRegistrationOrder");
			verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);	
			//executeStep.performAction(SeleniumActions.CloseTab, "","OrdersTabTitle");
			//verifications.verify(SeleniumVerifications.Closes, "","OrdersTabTitle",false);	
		    Order order = new Order(executeStep, verifications);
		    order.addOSPOrder();	   

		    driver.navigate().back();
		    driver.navigate().back();

			executeStep.performAction(SeleniumActions.Click, "","OSPRegistrationPharmacySales");
			//verifications.verify(SeleniumVerifications.Appears, "","SalesPage",false);	

//	    	navigation.navigateTo(driver, "SalesLink", "SalesPage");
			Sales sales = new Sales(executeStep, verifications);
			sales.doPharmacySales();
			EnvironmentSetup.LineItemIdForExec = "PharmacySales";
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			EnvironmentSetup.UseLineItem = true;

			//sales.doSales("", "SalesPageBillType");
			sales.closePrescriptionTab();
		
			navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
			SalesReturn salesReturn=new SalesReturn(executeStep, verifications);
			salesReturn.doSalesReturn("PharmacySales", "OSP");
	
			navigation.navigateTo(driver, "SearchBillLink", "BillListPage");
			PatientBill patientBill = new PatientBill (executeStep, verifications);
			patientBill.settleBills("NO", "UNPAID", "OSP");
	
			navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
			MRNoSearch Search = new MRNoSearch(executeStep, verifications);
			Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");
			
			executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
			verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);
			
			executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
			verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
			
			CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
			collectSamples.collectSamplesAndSave();
			
			navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
			MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
			verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);
			
			executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
			verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
			TestsConduction testConduction = new TestsConduction(executeStep, verifications);
			testConduction.conductTests();
	
			navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
			RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
			radiologyPendingTests.radiologyPending(true);
	
			navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
			ServicePending servicePending = new ServicePending(executeStep, verifications);
			servicePending.conductPendingServices();
	
			navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
			search = new MRNoSearch(executeStep, verifications);
			search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
			verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);
			System.out.println("OSP Patient Bill Settlement Complete");
	
			testCaseStatus = "Pass";
			System.out.println("TS_032 completed Successfully");

	}	
	@AfterClass
	public void closeBrowser(){
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OSPWorkFlowForCashPatient", null, "", "", testCaseStatus);
		driver.close();
	}

}