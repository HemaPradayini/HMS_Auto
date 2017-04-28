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
import reusableFunctions.Codification;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.EAndMCalculator;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.PatientBill;
import reusableFunctions.Registration;
import reusableFunctions.SiteNavigation;
import reusableFunctions.Upload;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class EAndMWorkflow {

	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";
	
	//@BeforeSuite
	//public void BeforeSuite(){
	private void openBrowser(){
		AutomationID = "Login - EAndMWorkFlow";
		DataSet = "TS_063";
		//EnvironmentSetup.testScenarioId = "TS_016";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - EAndMWorkFlow";
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
	public void eAndMWorkFlow(){
		openBrowser();
		login();
		
		DataSet = "TS_063";
		AutomationID = "EAndMWorkFlow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test EAndMWorkFlow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
	
		
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
	
		

		//1.Register an OP patient with insurance having 10% as co-pay where consultation
		//type is Ctype1 with charge as 200
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.setBillType();
		OPReg.RegisterPatientInsurance("OP");
		OPReg.InsuranceCoPayDetails();
		Upload upload = new Upload(executeStep, verifications);
		upload.upload("//input[@id='primary_insurance_doc_content_bytea1' and @type='file']");                                            // added on 16/3 for the changed insurance data
		
		OPReg.visitInformationDetails();
		OPReg.storeDetails();
		
		//2.Go to consultation screen and enter all form values like HPI,ROS,PFSH i.e form values which are required for E&M
		//3.Close the consultation after providing all required information
		
		navigation.navigateTo(driver, "OutPatientListLink", "OutPatientListPage");
		OPListSearch OPSearch = new OPListSearch(executeStep, verifications);
		OPSearch.searchOPList("OPListScreenConsultLink", "ConsultationAndManagementPage");
				
		ConsultationAndMgmt consultMgmt = new ConsultationAndMgmt(executeStep, verifications);
		consultMgmt.closeConsultaion();
		consultMgmt.saveConsultationAndMgmt("Diagnosis");
		
		//4.Now navigate to E&M Calculator screen and enter all required information
		//5.Click on finalize and Generate the E&M Code
		EAndMCalculator eMCalc = new EAndMCalculator(executeStep, verifications);
		eMCalc.navigateToEAndMFromConsultation();
		eMCalc.selectVisitType();
		eMCalc.setTreatmentOptions();
		eMCalc.selectRiskEvolution();
		eMCalc.complexityOfDataReview();
		eMCalc.finalizeEMCode();
		
		//6.Naviagte to bill and observe the consultation type and its charge
		//7.Observe the sposnor and patient due for the updated charge
		navigation.navigateTo(driver, "OpenBills", "BillListPage");
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.viewEditBills("");	
		
		//8.Try to change the E&M code/Consultation type from Codification Screen as well
		navigation.navigateTo(driver, "CodificationLink", "EditCodesScreen");
		Codification codification=new Codification(executeStep, verifications);
		codification.editEMCode();
		testCaseStatus = "Pass";
		System.out.println("TS_063 - completed");
		
	}
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "EAndMWorkFlow", null, "", "", testCaseStatus);
		driver.close();
	}
	
}
