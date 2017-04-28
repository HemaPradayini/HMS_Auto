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

import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import reusableFunctions.BedAllocation;
import reusableFunctions.BedFinalisation;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.Dialysis;
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
import reusableFunctions.RadiologyPendingTests;
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
public class DialysisWorkflow {
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
		AutomationID = "Login - DialysisWorkflow";
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
		AutomationID = "Login - DialysisWorkflow";
		DataSet = "TS_065";	
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();
		System.out.println("After Login");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
	 
	}
	
	@Test(groups={"E2E","Regression"})

	public void DialysisWorkflow(){
		openBrowser();
		login();
		
		DataSet = "TS_065";
		AutomationID = "DialysisWorkflow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test DialysisWorkflow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 

		
//Navigate To
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
    	 navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");

		//From here its for Entering details on the OP Registration Screen 
		  Registration OPReg = new Registration(executeStep, verifications);
		  OPReg.GovtIDDetailsCollapsedPanel();
		  OPReg.RegisterPatientOPDialysis();
		
		   navigation.navigateTo(driver, "OrdersLink", "OrdersPage");
			Order order = new Order(executeStep, verifications);
			order.searchOrder(false,false);
			order.addOrderItem("ItemOrder","");
			order.saveOrder(false);
		
			navigation.navigateTo(driver, "DialysisPrescriptionsLink", "DialysisPrescriptionPage");
			MRNoSearch Search = new MRNoSearch(executeStep, verifications);
			Search.searchMRNo("DialysisPrescriptionMrNo","DialysisPrescriptionMrNoList","DialysisPrescriptionMrNoSearchButton","DialysisPrescriptionPage");
			executeStep.performAction(SeleniumActions.Click, "","AddNewPrescriptionLink");
			verifications.verify(SeleniumVerifications.Appears, "","AddDialysisPrescriptionDetailsPage",false);
			
		    Dialysis dialysis = new Dialysis(executeStep, verifications);
			dialysis.addPrescriptionDetails();
			dialysis.addTemporaryAccess();
			executeStep.performAction(SeleniumActions.Click, "","AddDialysisPrescriptionDetailsSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","DialysisPrescriptionPage",false);
			
			//Go to 'Current Dialysis Sessions' & select same patient which is registered and click on view/edit, user will navigate to Pre Dialysis Details screen. 
		
			navigation.navigateTo(driver, "CurrentDialysisSessionsLink", "CurrentDialysisSessionsPage");
			executeStep.performAction(SeleniumActions.Click, "","CurrentDialysisSessionSearchButton");
			verifications.verify(SeleniumVerifications.Appears, "","CurrentDialysisSessionsPage",false);
			
			executeStep.performAction(SeleniumActions.Click, "","CurrentDialysisSessionRowToClick");
			verifications.verify(SeleniumVerifications.Appears, "","CurrentDialysisSessionViewEdit",false);
			
			executeStep.performAction(SeleniumActions.Click, "","CurrentDialysisSessionViewEdit");
			verifications.verify(SeleniumVerifications.Appears, "","PreDialysisDetailsPage",false);
			
		//6.In pre Dialysis screen select Status->Inprogress & add Session Details,Dialyzer Reuse,Patient Condition 
		//then click on 'Pre Equip Preparation' & select all option & click on 'Ok 'than click on 'save'.
	// Dialysis dialysis = new Dialysis(executeStep, verifications);
			
		    dialysis.addPreDialysisDetails();
		 	dialysis.addPreEquipPreparation();
			dialysis.addDialyzerReuse();
			dialysis.patientCondition();
			
			executeStep.performAction(SeleniumActions.Click, "","PreDialysisSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","CurrentDialysisSessionsPage",false);

//7.Go to Current Dialysis screen & click on view/edit for the same patient ,user will navigate to Intra dialysis details screen.
			navigation.navigateTo(driver, "CurrentDialysisSessionsLink", "CurrentDialysisSessionsPage");
			executeStep.performAction(SeleniumActions.Click, "","CurrentDialysisSessionSearchButton");
			verifications.verify(SeleniumVerifications.Appears, "","CurrentDialysisSessionsPage",false);
			
			executeStep.performAction(SeleniumActions.Click, "","CurrentDialysisSessionRowToClick");
			verifications.verify(SeleniumVerifications.Appears, "","CurrentDialysisSessionViewEdit",false);
			
			executeStep.performAction(SeleniumActions.Click, "","CurrentDialysisSessionViewEdit");
			verifications.verify(SeleniumVerifications.Appears, "","IntraDialysisDetailsPage",false);
			
//	8.In Intra dialysis screen select status- completed & click 'Post Equip Preparation' option & select all option & click on 'Ok' then click on 'Save'.
		//	 Dialysis dialysis = new Dialysis(executeStep, verifications);
	     	dialysis.addIntraDialysis();
			
		//9. Click on 'post dailysis link,user will navigate to post dailysis details screen.
	     	executeStep.performAction(SeleniumActions.Click, "","PostLink");
			verifications.verify(SeleniumVerifications.Appears, "","PostDialysisDetailsPage",false);
			
		//10.In post dailysis screen add Status as completed, add Session Details,Patient Condition,dialysis details & click on save.
			dialysis.postDialysisDetails();
			
			testCaseStatus="PASS";
			System.out.println("DialysisWorkflow-Completed");		
	}
	
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_065", "DialysisWorkflow", null, "", "", testCaseStatus);
		driver.close();
	}
		
}
