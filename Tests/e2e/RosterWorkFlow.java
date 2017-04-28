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
import reusableFunctions.Login;
import reusableFunctions.Registration;
import reusableFunctions.Roster;
import reusableFunctions.SiteNavigation;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class RosterWorkFlow {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus = "Fail";
	
//	@BeforeSuite
	//public void BeforeSuite(){
	private void openBrowser(){
		AutomationID = "Login - RosterWorkFlow";
		DataSet = "TS_066";
		//EnvironmentSetup.testScenarioId = "TS_016";
		
		System.out.println("BeforeSuite");
		AutomationID = "Login - RosterWorkFlow";
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
	public void RosterWorkFlow(){
		openBrowser();
		login();
		DataSet = "TS_066";
		AutomationID = "RosterWorkFlow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test RosterWorkFlow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
	
		
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		//1) In the roster module click on Roster Resource Type Master
		navigation.navigateTo(driver, "RosterResourceTypeMasterLink", "RosterResourceTypeMasterPage");
		
		//2) Click on Add Roster Resource Type.
		Roster roster = new Roster(executeStep, verifications);
		roster.clickAddRosterResourceTypeLink();
		
		//3) Enter the name of roster resource type and click on save.
		roster.addRosterResourceType();
		
		//4) In the roster module click on Roster Resource Master
		navigation.navigateTo(driver, "RosterResourceMasterLink", "RosterResourceMasterPage");
		
		//5) Click on Add Resource.
		roster.clickAddResourceLink();
		
		//6) Enter the resource name, Roster resource type and click on save.
		roster.addRosterResource();
		
		//7) In the roster module click on Shift Master.
		navigation.navigateTo(driver, "ShiftMasterLink", "ShiftMasterListPage");
		
		//8) Click on add shift.
		roster.clickAddShift();
		
		//9) Enter shift name, Start and end time and shift order and click on save.
		roster.addShift();
		
		//10) In the roster module click on Duty roster.
		navigation.navigateTo(driver, "DutyRosterLink", "DutyRosterPage");
		
		//11) Click on the grid of the required day and shift and then click on Edit shift.
		//12) Select the user role, user name, resource type and resource name and click on Add.
		//13) Click on save.
		
		roster.setDutyRoster();
		//15) Login with the added user in time that is as per duty roster.
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.logOut();
		
		OPWorkFlowLogin.login();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		//14) Login with the added user in time that is not as per duty roster.
		navigation.navigateTo(driver, "ShiftMasterLink", "ShiftMasterListPage");
		roster.editShift();
		
		OPWorkFlowLogin.logOut();
		OPWorkFlowLogin.login();
		verifications.verify(SeleniumVerifications.Appears, "", "LoginScreenHospital_Est", false);
		testCaseStatus = "Pass";
		System.out.println("TS_066 - completed");
		
	}
	@AfterClass
	public void closeBrowser(){
		String delimiter = " :: ";
		//String testCaseReportRecord = AutomationID + delimiter + DataSet + delimiter + "OPWorkFlowForBillNow" + delimiter + testCaseStatus;
		//CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		ReportingFunctions.UpdateTestCaseReport(AutomationID, DataSet, "RosterWorkFlow", null, "", "", testCaseStatus);
		driver.close();
	}
}
