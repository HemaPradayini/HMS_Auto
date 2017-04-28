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
import reusableFunctions.IPListSearch;
import reusableFunctions.Notes;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.OPListSearch;
import reusableFunctions.Registration;
import reusableFunctions.OpenGenericForm;
import reusableFunctions.OperationBill;
import reusableFunctions.OperationDetails;
import reusableFunctions.OperationScheduler;
import reusableFunctions.Order;
import reusableFunctions.PatientArrival;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientDischarge;
import reusableFunctions.PatientEMR;
import reusableFunctions.PatientIssue;
import reusableFunctions.PatientWard;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
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
 * @author C N Alamelu
 *
 */
public class INVCenterWiseSalesIssues {
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
		AutomationID = "Login - INVCenterWiseSalesIssues";
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
		AutomationID = "INVCenterWiseSalesIssues";
		DataSet = "TS_088A";	
		
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

	public void INVCenterWiseSalesIssues(){
		openBrowser();
		login();
		
		DataSet = "TS_088A";
		AutomationID = "INVCenterWiseSalesIssues";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet); 

		//Navigate To
		
		// System.out.println("Inside Test IPWorkFlow_1 - Before Navigation");
			
		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		
		//1. 1. Log in to C1 as User1.
		//2. Go to OP Registration screen and enter all patient level and visit level information 
		//(all mandatory fields) and select bill type as Bill Later. Click on Register & Edit Bill 
		//to register the patient.
		
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
		
		Registration OPReg = new Registration(executeStep, verifications);
		OPReg.RegisterPatientGenericDetails();
		OPReg.setBillType();
		//OPReg.unCheckPrimarySponsor();
		OPReg.visitInformationDetails();
		OPReg.storeAndEditBill();
		System.out.println("Step 1 complete");
		
		//3. Go to Patient Issue screen through Issue link in the hospital bill. Observe that 
		//Store1 is selected in Patient Issue screen.
		//4. Click on Add Item("+" symbol) and add medicines having package size 1, 10 and serial 
		//items. Click on Save to complete issues.
		//navigation.navigateTo(driver, "BillsLink", "BillListPage");
		//SearchBillList searchBillList = new SearchBillList(executeStep, verifications);
		//searchBillList.searchBills();
		
		executeStep.performAction(SeleniumActions.Click, "","IssueLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientIssuePage",false);
			
		PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
	//	patientIssue.searchPatientIssue(); //Why is this done
		patientIssue.addPatientIssueItem("FirstIssueItem");		
		patientIssue.saveIssue();
		verifications.verify(SeleniumVerifications.Opens, "","StoresStockPatientIssuePage",true);
		executeStep.performAction(SeleniumActions.CloseTab, "","StoresStockPatientIssuePage");
		verifications.verify(SeleniumVerifications.Closes, "","StoresStockPatientIssuePage",false);
		System.out.println("Step 3 complete");
				
		//	5. Go to Returns from Patient screen under Sales and Issues, add the above issued items 
		//by giving quantity as 3. Click on Save to complete issue returns.
		navigation.navigateTo(driver, "PatientIssueReturnLink", "PatientIssueReturnPage");
	//	PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
		patientIssue = new PatientIssue(executeStep, verifications);
		//patientIssue.searchPatientIssue();
		patientIssue.returnPatientIssueItem("FirstIssueItem");		
		patientIssue.saveIssueReturns();
		System.out.println("Step 5 complete");
		
		//6. Go to Add Patient Indent screen under Sales and Issues, enter the MR Number. 
		//Raise a patient indent by adding another set of serial and batch items (having packet size 
		//1 and 10 and serial items) by giving quantity as 5. Save this indent in finalized status.
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		//MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
		//mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");		
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("FirstIndent",true);
		System.out.println("Step 6 complete");
		
		//7. Go to Patient Indents List under Sales and Issues. Select the above generated indent and 
		//click on Issue.
		//8. Complete issues by clicking on Save.
		
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
	//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		//raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "");
		raisePatientIndent.savePatientIndentIssue(true);
		System.out.println("Step 7 complete");
		
		//9. Go to Add Patient Return Indent screen under Sales and Issues, enter the MR number and 
		//add above issued items(through indent) by giving 3 quantity each. Save this indent in finalized 
		//status.
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");		
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("FirstIndent", false);
		System.out.println("Step 8 complete");
		
		//10. Go to Patient Indents List under Sales and Issues. Select the above generated return indent 
		//and click on Issue Returns.
		//11. Complete issue returns by clicking on Save.
		
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
	//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		//raisePatientIndent.doSalesOrSalesReturnsForIndent(false, "");
		raisePatientIndent.savePatientIndentIssue(false);
		System.out.println("Step 9 complete");
		
		
		//12. Go to sales screen under Sales and Issues. Observe that Store1 is selected as sales store. 
		//Enter MR number. Select bill type as Raise Bill, add few medicines(having package size 1 and 10 
		//and serial items) by giving quantity as 5 each and complete sales by clicking on 
		//Raise Bill & Print.
		
		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);	
		//EnvironmentSetup.selectByPartMatchInDropDown = true;
		sales.doSales("FirstSales", "RaiseBillPayment");
		sales.closePrescriptionTab();
		System.out.println("Step 3a complete");
		
		//13. Go to Sales Returns screen under Sales and Issues, enter the patient's MR number. 
		//Add items to the grid by giving 3 quantity each. Click on Raise Bill to complete sales returns.
		
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturns = new SalesReturn(executeStep, verifications);
		salesReturns.doSalesReturn("FirstSales","OP");
		System.out.println("Step 3c complete");
		
		//14. Go to Add Patient Indent screen under Sales and Issues, enter the MR Number. 
		//Raise a patient indent by adding another set of serial and batch items (having packet size 
		//1 and 10 and serial items) by giving quantity as 5. Save this indent in finalized status. 
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
	//	MRNoSearch mrNoSearch = new MRNoSearch(executeStep, verifications);
	//	mrNoSearch.searchMRNo("RaisePatientIndentMRNoField", "RaisePatientIndentMRNoList", "RaisePatientIndentMRNoFindButton", "RaisePatientIndentPage");		
	//	RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("SecondIndent",true);
		System.out.println("Step 14 complete");
		
		//15. Go to Patient Indents List under Sales and Issues. Select the above generated indent and 
		//click on Sales.
		//	16. Complete sales by clicking on Raise Bill & Print.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "");
		System.out.println("Step 15 complete");
		
	 
		//17. Go to Add Patient Return Indent screen under Sales and Issues, enter the MR number and 
		//add medicines by giving 3 quantity each. Save this indent in finalized status. 
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");		
//		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("SecondIndent", false);
		System.out.println("Step 17 complete");
			
		//18. Go to Patient Indents List under Sales and Issues. Select the above generated return indent 
		//and click on Sales Returns. 
		//19. Complete sales returns by clicking on Raise Bill & Print.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		//RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		//raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.doSalesOrSalesReturnsForIndent(false, "");
		System.out.println("Step 9 complete");
		
		testCaseStatus="PASS";
		
		System.out.println("End Test INV Centerwise Sales Issues");
		
	}
	
	@AfterClass
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, "TS_088", "INVCenterWiseSalesIssues", null, "", "", testCaseStatus);
		driver.close();
	}
	
}
