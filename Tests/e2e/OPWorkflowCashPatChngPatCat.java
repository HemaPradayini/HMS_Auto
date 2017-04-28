/**
 * 
 */
package e2e;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
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
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientVisit;
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
import supportedKeywords.SeleniumActions;
import supportedKeywords.SeleniumVerifications;

/**
 * @author Tejaswini
 *
 */
public class OPWorkflowCashPatChngPatCat {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "FAIL";	
	@BeforeSuite
	public void BeforeSuite(){
//		System.out.println("BeforeSuite");
//		AutomationID = "Login - OPWorkflowCashPatChngPatCat";
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
		
	}
	
//	@BeforeMethod
//	public void BeforeTest(){
	private void login(){
		AutomationID = "Login - OPWorkflowCashPatChngPatCat";
		DataSet = "TS_007";
		//EnvironmentSetup.testScenarioId = "TS_001";

		System.out.println("BeforeSuite");
		AutomationID = "Login - OPWorkflowCashPatChngPatCat";
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
	public void OPWorkflowCashPatChngPatCat(){
		login();
		
		DataSet = "TS_007";
		AutomationID = "OPWorkflowCashPatChngPatCat";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForBillNow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		//Step 1
		/*navigation.navigateTo(driver, "DoctorSchedulerLink", "DoctorSchedulerPage");		
		DoctorScheduler OPWorkFlowDocScheduler = new DoctorScheduler(executeStep,verifications);
		OPWorkFlowDocScheduler.scheduleDoctorAppointment();
		PatientArrival OPPatientArrival = new PatientArrival(executeStep,verifications);
		OPPatientArrival.markPatientAsArrived();		*/
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");

		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.unCheckPrimarySponsor();
		OPReg.setBillType();
		OPReg.visitInformationDetails();
		OPReg.storeDetails();
		
		//2.Go to bill, from order link at the bottom navigate to bill Order screen and order all billable items and save the screen.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		searchBillList.searchBills();
		String billType = "Settlement";		
		PatientBill patientBillNow = new PatientBill(executeStep, verifications);		
		patientBillNow.addBillOrder("OrderBillItem");
		
		//Step 3.Observe the rates, discounts and total dues in bill
		
		//4.Now go to Edit Visit Details screen via Registration module and click on Change Patient Category link.
		//5.Now in change pat category screen Change the patient category to' NewCat'  and save the screen.
		
		navigation.navigateTo(driver, "EditPatientVisit", "EditPatientVisitPage");
		PatientVisit patientVisit = new PatientVisit(executeStep, verifications);
		patientVisit.changePatientCategory();

		//6.Now go to Change Rate plan screen from change rate plan link at bottom of Patient cayegory change screen,observe the Visit Rate Plan column in Change Rate Plan screen.
		//7.Now save the change rate plan screen by selecting 'All Previous charges' value for update charges drop down.  
		ChangeRatePlanBedType changeRatePlan = new ChangeRatePlanBedType(executeStep, verifications);
		changeRatePlan.changeAndSaveRatePlanAndBedType();
		
		//8.Now go to bill and observe the rate plan name, Item rates,discounts and dues in bill
		executeStep.performAction(SeleniumActions.Click, "","ChangeRatePlanBillLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);

		testCaseStatus = "PASS";
		System.out.println("TS_007 completed Successfully");

	}
	
	//@AfterTest
	public void closeBrowser(){
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "OPWorkflowCashPatChngPatCat", null, "", "", testCaseStatus);
		driver.close();
	}
	
}
