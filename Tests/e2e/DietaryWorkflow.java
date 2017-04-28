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

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.BedAllocation;
import reusableFunctions.BedFinalisation;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.Dietary;
import reusableFunctions.DischargeSummary;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.DrOrder;
import reusableFunctions.IPListSearch;
import reusableFunctions.Notes;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.OperationScheduler;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientDischarge;
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
 * @author C N Alamelu
 *
 */
public class DietaryWorkflow {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "FAIL";
	
	//@BeforeSuite
	//public void BeforeSuite(){
	private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - DietaryWorkflow";
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
		AutomationID = "DietaryWorkFlow";
		DataSet = "TS_067";	
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();
		System.out.println("After Login");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
	//	verifications.verify(SeleniumVerifications.Appears, "", "NavigationMenu", false); 
	}
	
	@Test(groups={"E2E","Regression"})

	public void DietaryWorkFlow(){
		openBrowser();
		login();
		
		DataSet = "TS_067";
		AutomationID = "DietaryWorkFlow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 

		//Navigate To
			
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
		navigation.navigateTo(driver, "IPRegistrationLink", "IPRegistrationPage");
			
		//1.Register an IP patient
		Registration IPReg = new Registration(executeStep, verifications);
		IPReg.RegisterPatientCashIP();
		
		//2. Go to In Patient List--> Select the registered IP patient and click on IP case sheet	
		navigation.navigateTo(driver, "InPatientListLink", "InPatientListPage");
				
		IPListSearch IPSearch = new IPListSearch(executeStep, verifications);
		IPSearch.searchIPList();
		
		//Navigate to IP Case Sheet Screen
		//OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		//OPSearch.searchOPList("IPListScreenIPCaseSheetLink", "IPCaseSheetScreen");
				
		 //3. In IP case sheet , click on Doctor Notes and enter doctor notes, 
		// select 'BILL' check box , and consultation types as well as the doctor name and save
					
		Notes Notes = new Notes(executeStep, verifications);
		Notes.RecordDrNotes();
				
		//4. Go back to IP case sheet and click on Nurse Notes and enter nurse notes and save the screen
		Notes.RecordNurseNotes();
			
		// 5. Go to IP case sheet --> Prescribe diet 
		executeStep.performAction(SeleniumActions.Click, "","IPCaseSheetPrescribeDietLink");
		verifications.verify(SeleniumVerifications.Opens, "","PrescribeMealPage",true);
		
		
		//6. Prescribe the diet by entering doctor name, and selecting the meal 
		// and time-->Click on add and then save
		Dietary dietary = new Dietary(executeStep, verifications);
		dietary.prescribeMeal();
		
		executeStep.performAction(SeleniumActions.CloseTab, "","prescribeMealReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","prescribeMealReceipt",false);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","PrescribeMealPage");
		verifications.verify(SeleniumVerifications.Closes, "","PrescribeMealPage",false);
		
		//7. Select the Diet document template in the drop down and click on  
		//"Add Diet Chart" link enter few data and save
		//This is not implemented since it is entering data into pdf which is not possible
		
		
		//8. Go to Order screen of the same patient , and click ok on the alert 
		//which asks to save the prescribed diet/meal item
		//8a save order
			navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
			Order order = new Order(executeStep, verifications);
			order.searchOrder(false,false);
			order.saveOrder(false);

				
		// 9. Go to Canteen under Dietary Menu and update the delivery time.
			navigation.navigateTo(driver, "CanteenLink", "MealsScheduleForCanteenPage");
			verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
			executeStep.performAction(SeleniumActions.Accept, "","Framework");
		//	Dietary dietary = new Dietary(executeStep, verifications);
			dietary.updateDeliveryTime();
	

			DbFunctions dbFunctions = new DbFunctions();
			dbFunctions.storeDate(this.executeStep.getDataSet(), "ShiftEndDate","C",0);
			
		//10 Finalize and close the bill and discharge the patient
			
		navigation.navigateTo(driver, "ADTLink", "ADTPage");
		BedFinalisation FinaliseBed = new BedFinalisation(executeStep, verifications);
		FinaliseBed.finaliseBed();
	
		
		// Close the bill 
	
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		//SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		//searchBillList.searchBills();
		
		PatientBill patientBill = new PatientBill(executeStep, verifications);		
		patientBill.settleBills("NO", "UNPAID", "IP");
		
		//and discharge the patient
		
		navigation.navigateTo(driver, "DischargeLink", "PatientDischargePage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("PatientDischargeMRNoField", "PatientDischargeMRNoLi", "PatientDischargeFindButton", "PatientDischargePage");

		PatientDischarge patientDischarge = new PatientDischarge(executeStep, verifications);		
		patientDischarge.patientDischarge();
		
		testCaseStatus = "PASS";
		System.out.println("End Test Dietary Workflow");
		
	}

	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_067", "DietaryWorkFlow", null, "", "", testCaseStatus);
		driver.close();
	}
		
}
