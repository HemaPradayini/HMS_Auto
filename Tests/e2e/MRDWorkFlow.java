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
import reusableFunctions.DischargeSummary;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.DrOrder;
import reusableFunctions.IPListSearch;
import reusableFunctions.Notes;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRDCasefile;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Registration;
import reusableFunctions.RegistrationPreferences;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.OperationScheduler;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientCategoryMaster;
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
public class MRDWorkFlow {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	
	//@BeforeSuite
	//public void BeforeSuite(){
	private void openBrowser(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - MRDWorkFlow";
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
		AutomationID = "MRDWorkFlow";
		DataSet = "TS_075";
				
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

	public void MRDWorkFlow(){
		openBrowser();
		login();
		
		DataSet = "TS_075";
		AutomationID = "MRDWorkFlow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test DayCareToIPWorkFlow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 

		//Navigate To
			
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");	
/*		
		//In 'General' patient category set Case File Required:Yes
		
		navigation.navigateToSettings(driver, "PatientCategoryMasterLink", "PatientCategoryMasterPage");
		PatientCategoryMaster patientCategoryMaster = new PatientCategoryMaster(executeStep, verifications);
		patientCategoryMaster.categorySearch();
		
		//4.In Registration Preferences set Generate Case File No:Yes
		//5.In Registration Preferences Set Issue to Dept./Indent on Registration:Yes
		
		executeStep.performAction(SeleniumActions.Click, "", "NavigationMenu");	
		executeStep.performAction(SeleniumActions.Click, "", "SettingsLink");	
		executeStep.performAction(SeleniumActions.Click, "", "RegistrationPreferencesLink");
		verifications.verify(SeleniumVerifications.Appears, "", "RegistrationPreferencesPage", false);
	
		RegistrationPreferences registrationPreferences = new RegistrationPreferences(executeStep, verifications);
		registrationPreferences.setMRDCaseFilePreferences();
	
	*/	
		// 1.Register an OP with patient category as General and department as Cardiology
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");

		Registration IPReg = new Registration(executeStep, verifications);
		IPReg.mrdPatientRegistration();

		//2.Under Medical Records, click on 'Case File search' screen and observe that case file 
		//with column Value,Requested Dept  and Requested By 
		navigation.navigateTo(driver, "CaseFileSearch", "MRDCaseFileSearchPage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("MRDCaseFileSearchPageMRNoField", "MRDCaseFileSearchPageMRNoList", "MRDCaseFileSearchPageSearchButton", "MRDCaseFileSearchPage");
		
		
		//3.Now under Action drop down select Action as Raise Indent. Select the existing record 
		//by checking the check box and click on 'Indent' button.User will be navigated to 'Raise 
		//Case file Indent' screen. Now click on 'Edit Case File' button and then change the 
		//Department to 'Dental' and click on 'Ok'. Now click on 'Raise Indent' button.In the same 
		//screen click on 'MRD Case File Search' link.
		
		MRDCasefile mrdCasefile = new MRDCasefile(executeStep, verifications);
		mrdCasefile.raiseIndent("IndentDeptNameFirst");
		
		executeStep.performAction(SeleniumActions.Click, "","RaiseCasefileIndentPageRaiseSearchLink");
		verifications.verify(SeleniumVerifications.Appears, "","MRDCaseFileSearchPage",false);
			
		navigation.navigateTo(driver, "CaseFileSearch", "MRDCaseFileSearchPage");
//		MRDCasefile mrdCasefile = new MRDCasefile(executeStep, verifications);
		mrdCasefile.searchAll();
	
		//4.Now select Action as 'Return Case File' and then select the record(the patient which we 
		//registered) by checking the check box and then click on Return button. User will be navigated 
		//to Return MRD Case File  where click on Return Case File button
		
		//MRDCasefile mrdCasefile = new MRDCasefile(executeStep, verifications);
		mrdCasefile.returnCasefile();

		
		//5.In  MRD Casefile Search dashboard,click on clear button and then select 'all' as search 
		//value for all the search filters, enetr thr mrno and then click on serach button. Obsreve the 
		//Casefile with Requested Dept values
		navigation.navigateTo(driver, "CaseFileSearch", "MRDCaseFileSearchPage");
		//MRDCasefile mrdCasefile = new MRDCasefile(executeStep, verifications);
		mrdCasefile.searchAll();
		
		//6.Now select actioan as 'Issue Case File', check the check box of the listed record
		//(patient which we registered) . Click on Issue button.
		
		//7.User will be navigate to 'Issue MRD Case File ' , now click on 'Issue' button
		mrdCasefile.issueCasefile();
		
		//8.Now navigate back to dashboard by clciking 'MRD Case Files Search' link
			
		//9.In  MRD Casefile Search dashboard,click on clear button and then select 'all' as search 
		//value for all the search filters, enetr thr mrno and then click on serach button.
		
		mrdCasefile.searchAll();
		
		
		//10.Now select action as 'Raise Indent',and then select the record(the patient which we 
		//registered) by checking the check box and then click on Raise Indent button. User will be 
		//navigated to 'Raise Case file Indent' screen. Now click on 'Edit Case File' button and then 
		//change the Department to 'Ortho' and click on 'Ok'. Now click on 'Raise Indent' button.In 
		//the same screen click on 'MRD Case File Search' link
		
		mrdCasefile.raiseIndent("IndentDeptNameSecond");
		mrdCasefile.searchAll();
		
		//11.Now select action as 'Close Indent' and then select the record(the patient which we 
		//registered) by checking the check box and then click on Close Indent button. 
	    //12.Afetr navigating to 'Close Case file Indent' click in Close Indent button
		mrdCasefile.closeIndent();
		System.out.println("MRD Workflow");
		
	}
	
	@AfterClass
	public void closeBrowser(){
		driver.close();
	}
	
		
}
