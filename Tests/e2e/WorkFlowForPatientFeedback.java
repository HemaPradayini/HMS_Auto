/**
 * 
 */
package e2e;

import java.util.List;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
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
import reusableFunctions.CollectSamples;
import reusableFunctions.ConsultationAndMgmt;
import reusableFunctions.Deposits;
import reusableFunctions.DischargeSummary;
import reusableFunctions.DoctorScheduler;
import reusableFunctions.DrOrder;
import reusableFunctions.Notes;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.OTRecord;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.OperationDetails;
import reusableFunctions.OperationScheduler;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientFeedBack;
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
 * @author Reena
 *
 */
public class WorkFlowForPatientFeedback {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	KeywordExecutionLibrary initDriver;
	VerificationFunctions verifications;
	ReportingFunctions reporter;
	String AutomationID;
	String DataSet;
	String testCaseStatus="FAIL";
	
	@BeforeSuite
	public void BeforeSuite(){
		System.out.println("BeforeSuite");
		AutomationID = "Login - WorkFlowForPatientFeedback";
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
	
	@BeforeMethod
	public void BeforeTest(){
		AutomationID = "Login - WorkFlowForPatientFeedback";
		DataSet = "TS_069";	
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();
		System.out.println("After Login");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
	//	verifications.verify(SeleniumVerifications.Appears, "", "NavigationMenu", false); 
	}
	
	@Test(groups={"E2E","Regression"})

	public void WorkFlowForPatientFeedback(){
		
		DataSet = "TS_069";
		AutomationID = "WorkFlowForPatientFeedback";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test WorkFlowForPatientFeedback - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 

		//Create a question category in Survey Question Category Master under Patient Feedback module
				SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
				navigation.navigateTo(driver, "SurveyQuestionCategoryMasterLink", "SurveyQuestionCategoryMasterPage");
				
							
				executeStep.performAction(SeleniumActions.Click, "","SurveyQuestionCategoryAddNewCategory");
				verifications.verify(SeleniumVerifications.Appears, "","AddSurveyQuestionCategoryPage",true);

				YourUniqueName();
			//	CommonUtilities text=new CommonUtilities();
		//		String locator = "//input[@id='category']";
		//		text.YourUniqueName(locator);
				
				
				//executeStep.performAction(SeleniumActions.Enter, "Category","AddSurveyCategoryField");
				//verifications.verify(SeleniumVerifications.Entered, "Category","AddSurveyCategoryField",false);
				
				executeStep.performAction(SeleniumActions.Enter, "CategoryDescription","AddSurveyQuestionCategoryPageCategoryDescription");
				verifications.verify(SeleniumVerifications.Entered, "CategoryDescription","AddSurveyQuestionCategoryPageCategoryDescription",false);
				
				
				executeStep.performAction(SeleniumActions.Click, "","AddSurveyQuestionCategorySaveButton");
				verifications.verify(SeleniumVerifications.Appears, "","EditSurveyQuestionCategoryPage",false);
				
				
				executeStep.performAction(SeleniumActions.Store, "Category","AddSurveyCategoryField");
				
				
				//Create Feed back form in Feed back form under Patient Feedback module by giving header,footer.
				
				navigation.navigateTo(driver, "FeedbackFormsLink", "FeedbackFormsPage");
				PatientFeedBack feedback=new PatientFeedBack(executeStep, verifications);
				feedback.feedbackForm();
			    
			    //Register the patient
			     navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
					
					
					Registration OPReg = new Registration(executeStep, verifications);
					OPReg.RegisterPatientGenericDetails();
					OPReg.unCheckPrimarySponsor();
					OPReg.setBillType();
					OPReg.visitInformationDetails();
					OPReg.storeDetails();
				
				//Go to Patient Responses and click on Record Patient Response, Enter the MR number	
					
					navigation.navigateTo(driver, "PatientResponsesLink", "PatientResponsesPage");
					feedback.patientResponse();
					
			   //Go to Patient Survey Response Details Report Builder and generate the report
					
					//navigation.navigateTo(driver, "PatientSurveyResponseDetailsReportBuilderLink", "PatientSurveyResponseDetailsReportBuilderPage");
					navigation.navigateToReport(driver, "PatientSurveyResponseDetailsReportBuilderLink", "PatientSurveyResponseDetailsReportBuilderPage");
					
					
					executeStep.performAction(SeleniumActions.Select, "SurveyReportDateRange","SurveyReportBuilderDateRange");
					verifications.verify(SeleniumVerifications.Selected, "SurveyReportDateRange","SurveyReportBuilderDateRange",false);
					
					
					executeStep.performAction(SeleniumActions.Select, "SurveyReportPDFFontSize","SurveyReportBuilderPDFFontSize");
					verifications.verify(SeleniumVerifications.Selected, "SurveyReportPDFFontSize","SurveyReportBuilderPDFFontSize",false);
					executeStep.performAction(SeleniumActions.Click, "","SurveyReportBuilderPDFButton");
					//verifications.verify(SeleniumVerifications.Appears, "","PatientSurveyResponseDetailsReport",false);
					verifications.verify(SeleniumVerifications.Opens, "","PatientSurveyResponseDetailsReport",false);
					
					testCaseStatus="PASS";
					System.out.println("WorkFlowForPatientFeedback-Completed");
					
	}		
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_069", "WorkFlowForPatientFeedback", null, "", "", testCaseStatus);
		driver.close();
	}			

	public String generateRandomString(int length){
	      return RandomStringUtils.randomAlphabetic(length);
	     }  //for letters

	public String generateRandomNumber(int length){
	      return RandomStringUtils.randomNumeric(length);
	     }  //for numbers


	public void YourUniqueName(){
		driver.findElement(By.xpath("//input[@id='category']")).sendKeys("Name"+"Test"+ RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(3));
	  //      driver.findElement(By.xpath("//input[@id='category']")).sendKeys("Name" +" - "+"Test" + " - " + RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(3));
	    }
	
}
