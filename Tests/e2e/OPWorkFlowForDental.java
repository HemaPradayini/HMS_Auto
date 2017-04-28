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
import reusableFunctions.ChangeRatePlanBedType;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.DentalConsultations;
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

/**
 * @author Reena
 *
 */
public class OPWorkFlowForDental {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus="FAIL";
	
//	@BeforeSuite
//	public void BeforeSuite(){
	private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - OPWorkFlowForCashPatient";
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
		DataSet = "TS_064";
		
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		
	}
	
	@Test(groups={"E2E","Regression"})
	public void OPWorkFlowForDental(){
		openBrowser();
		login();
		DataSet = "TS_064";
		AutomationID = "OPWorkFlowForDental";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test OPWorkFlowForDental - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		//Register a patient
	     SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.unCheckPrimarySponsor();
		OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		OPReg.visitInformationDetails();
		OPReg.storeDetails();
	
		//Go to Dental consultation screen under Patient module and Enter the MR No
		navigation.navigateTo(driver, "DentalConsultationsLink", "DentalConsultationPage");
		
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("DentalConsultationPageMRNoField","DentalConsultationPageMRNoList","DentalConsultationPageMRNoFindButton","DentalConsultationPage");
		
		//Click on add icon under the treatment grid
		
		DentalConsultations dentalConsult=new DentalConsultations(executeStep, verifications);
		executeStep.performAction(SeleniumActions.Click, "","DentalConsultationTreatmentAddItemButton");
		verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationAddTreatmentDetailsScreen",false);
		dentalConsult.addTreatmentDetails("DentalTreatmentLineItem");
		
		//Click on 'Print Quotation' 
		executeStep.performAction(SeleniumActions.Click, "","DentalConsultationPrintQuotationButton");
		verifications.verify(SeleniumVerifications.Opens, "","DentalQuotationPrintPage",false);
		//verifications.verify(SeleniumVerifications.Appears, "","DentalQuotationPagePatientName",false);
		executeStep.performAction(SeleniumActions.CloseTab, "","DentalQuotationPrintPage");
		verifications.verify(SeleniumVerifications.Closes, "","DentalQuotationPrintPage",false);
		
		//Select the service and click on Order and finalize
		executeStep.performAction(SeleniumActions.Check, "","DentalConsultationTreatmentSelectAllCheckbox");
		verifications.verify(SeleniumVerifications.Checked, "","DentalConsultationTreatmentSelectAllCheckbox",false);
		executeStep.performAction(SeleniumActions.Click, "","DentalConsultationOrderFinalizeBillsButton");
		verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationPage",false);
		
		
		//Click on Dental Supplies link and Edit the supplies and mark it as completed , enter the remarks and save the screen
		dentalConsult.editDentalSupplies();
		executeStep.performAction(SeleniumActions.Click, "","PatientDentalConsultationLink");
		verifications.verify(SeleniumVerifications.Appears, "","DentalConsultationPage",false);
		
		//In the payment section initiate the payment and pay the bill
	
		
        navigation.navigateTo(driver, "DentalConsultationsLink", "DentalConsultationPage");
		
	//	MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("DentalConsultationPageMRNoField","DentalConsultationPageMRNoList","DentalConsultationPageMRNoFindButton","DentalConsultationPage");
		
		
	//	DentalConsultations dentalConsult=new DentalConsultations(executeStep, verifications);
		
		dentalConsult.payDentalBills();

	
	
	
	
	// Click on Edit sub task icon in the grid and mark one task as completed by selecting doctor and another as  'Not Required' and save the screen
      navigation.navigateTo(driver, "DentalConsultationsLink", "DentalConsultationPage");
		
		//MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("DentalConsultationPageMRNoField","DentalConsultationPageMRNoList","DentalConsultationPageMRNoFindButton","DentalConsultationPage");
		//DentalConsultations dentalConsult=new DentalConsultations(executeStep, verifications);
		dentalConsult.editSubTasks();
		
		
	//	 Click on Edit treatment against the same service in the grid and change the status of the service to 'Completed' and save the screen
		
		dentalConsult.editTreatment();
		testCaseStatus="PASS";
		 System.out.println("OPWorkFlowForDental-Completed");

	  
		

	}	
	@AfterClass
	public void closeBrowser(){
	reporter.UpdateTestCaseReport(AutomationID, "TS_064", "OPWorkFlowForDental", null, "", "", testCaseStatus);
	driver.close();
	}
}
