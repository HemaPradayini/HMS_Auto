package e2e;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import genericFunctions.CommonUtilities;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.Login;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.SiteNavigation;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class NonTaxschemaSales {
	
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
		AutomationID = "Login - NonTaxschemaSales";
		reporter = new ReportingFunctions();
		System.out.println("AfterReport");
		
		initDriver = new KeywordExecutionLibrary();
		System.out.println("AfterInitDriver");

		try{
			System.out.println("EnvironmentSetUp is ::" + EnvironmentSetup.URLforExec);
			driver = initDriver.LaunchApp("chrome",  EnvironmentSetup.URLforExec);
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
		AutomationID = "Login - NonTaxschemaSales";
		DataSet = "TS_089";
				
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		Login OPWorkFlowLogin = new Login(executeStep,verifications);
		OPWorkFlowLogin.login();

		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.HoverOver, "", "NavigationMenu");
		//verifications.verify(SeleniumVerifications.Appears, "NavigationMenu", "" , false);
	}
	
	@Test(groups={"E2E","Regression"})
	public void NonTaxschemaSales(){
		openBrowser();
		login();
		DataSet = "TS_089";
		AutomationID = "NonTaxschemaSales";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test NonTaxschemaSales - Before Navigation");
		
		// Log in to C1 as User1 and navigate to  OP Registration screen and enter all patient level and visit level information (all mandatory fields) and select bill type as Bill Later. Click on Register & Edit Bill to register the patient.
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
    	navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.unCheckPrimarySponsor();
		OPReg.setBillType();
		OPReg.RegisterPatientGenericDetails();
		OPReg.visitInformationDetails();
		OPReg.storeDetails();

		//3. Go to Sales screen under Sales and Issues, enter above patient's MR No, wait for the patient's details to get auto-filled and add the created three items to the grid by giving quantity as 5.
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.doSales("SalesItem", "BillNowInsurance");
		sales.closePrescriptionTab();
		
		//4. Click on Add to Bill to complete sales.
		//5. Go to Sales Returns screen under Sales and Issues, enter the patient's MR number. Add items to the grid by giving 3 quantity each.
		//For serial items , add one by one (3 rows) Click on Raise Bill to complete sales returns.
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturn=new SalesReturn(executeStep, verifications);
		salesReturn.searchMRNo();
		salesReturn.doSalesReturn("SalesReturn","OP");


		//6. Go to Add Patient Indent screen under Sales and Issues, enter the MR Number. Raise a patient indent by adding the same 3 serial and batch items (having packet size 1 and 10 and serial items) by giving quantity as 5
		// Save this indent in finalized status. 
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales1", true);

	
		//7. Go to Patient Indents List under Sales and Issues. Select the above generated indent and click on Sales.
		//8. Complete sales by clicking on Raise Bill & Print. 
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "IndentSales1");

		//9. Go to Add Patient Return Indent screen under Sales and Issues, enter the MR number and add medicines by giving 3 quantity each. Save this indent in finalized status.
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");		
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("IndentSales1", false);

		//10. Go to Patient Indents List under Sales and Issues. Select the above generated return indent and click on Sales Returns. 
	
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		raisePatientReturnIndent.doSalesOrSalesReturnsForIndent(false, "");
		
		//11. Complete sales returns by clicking on Raise Bill & Print.
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		salesReturn.doSalesReturn("SalesReturns","OP");
		
		
		testCaseStatus="PASS";
		System.out.println("NonTaxschemaSales");
		

}
	
	@AfterTest
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, DataSet, "NonTaxschemaSales", null, "", "", testCaseStatus);
		driver.close();
	}
	
}