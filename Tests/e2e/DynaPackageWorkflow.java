package e2e;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.CommonUtilities;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.CollectSamples;
import reusableFunctions.DoctorReferral;
import reusableFunctions.DynaPackage;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.Order;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientIssue;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.RaisePatientIndent;
import reusableFunctions.Registration;
import reusableFunctions.Sales;
import reusableFunctions.SalesReturn;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.TestsConduction;
import reusableFunctions.Upload;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class DynaPackageWorkflow {
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
		AutomationID = "Login - DynaPackageWorkflow";
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
		AutomationID = "Login - DynaPackageWorkflow";
		DataSet = "TS_074";
				
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
	public void DynaPackageWorkflow(){
		openBrowser();
		login();
		DataSet = "TS_074";
		AutomationID = "DynaPackageWorkflow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test DynaPackageWorkflow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
		navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");

		//Register user with basic, patient info, sponsor info, pay info, visit info 
		//and order items(lab, radiology and service) and register
	    Registration OPReg = new Registration(executeStep, verifications);
		Upload upload = new Upload(executeStep, verifications);	
		OPReg.RegisterPatientGenericDetails();
		OPReg.GovtIDDetailsCollapsedPanel();
		OPReg.RegisterPatientInsurance("OP");		
		OPReg.visitInformationDetails();
		upload.upload("//input[@id='primary_insurance_doc_content_bytea1' and @type='file']");
		OPReg.RegistrationOrderItem();
		OPReg.storeDetails();

		//After registration, in bill also add all billable items say service is ordered with 1 qty and save the bill
		
		executeStep.performAction(SeleniumActions.Click, "","ProceedToBillingLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.Click, "","BillPageOrderLink");
	    verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",false);
		
		Order order = new Order(executeStep, verifications);
		executeStep.performAction(SeleniumActions.Select, "OrderPageBillNoChoice","OrdersScreenBillNumberDropDown");
		order.addOrderItem("OrderItem","DynaPackageAddOrderPage");
		order.saveOrder(false);
		
		//3. Add dyna pacakge in the bill by clicking on 'Package Name' field in bill under bill details section.Observe Package Charge field after adding dyna package
		//4(a) After adding dyna package,Save the bill.
		//4(b) After saving the bill click on Package Process button which will be available under items grid
		
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		DynaPackage DynaPkg = new DynaPackage(executeStep, verifications);
		DynaPkg.AddDynaPkg();
		

		//5(a)order the same items from Patient bill page and do package process
		PatientBill patientBill = new PatientBill(executeStep, verifications);	
		patientBill.addItemsIntoPatientBill();
		executeStep.performAction(SeleniumActions.Click, "","PatientBillPackageProcessButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		

		//Add Dyna Package
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		DynaPkg.AddDynaPkg();
		
		//include service package manually7(a). 
		//Out of 2 services in bill one will be included and one will be excluded, so now click the check box of excluded service(the one with blue flag), click the edit dialogue box and then check the Finalize check box, click ok of the dialogue box. Now click on 'Inc In Pkg' button and then save the bill.
		
		int ServiceTestCount = this.executeStep.getDriver().findElements(By.xpath("//table[@id='chargesTable']//label[@title = 'Service']")).size();
		for (int j=2; j<ServiceTestCount+1; j++){
			EnvironmentSetup.intGlobalLineItemCount = j;
			String img = this.executeStep.getDriver().findElement(By.xpath("(//table[@id='chargesTable']//label[@title = 'Service'])["+j+"]/parent::td/preceding-sibling::td/img")).getAttribute("src");
			System.out.println(img+ "........................................");

			if(img.contains("blue_flag.gif")){
				executeStep.performAction(SeleniumActions.Click, "","PatientBillItemEditButton");
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillEditChargeDialogbox",false);
				WebElement checkbox = this.executeStep.getDriver().findElement(By.xpath("(//table[@id='chargesTable']//label[@title = 'Service'])["+j+"]/parent::td/preceding-sibling::td/img"));
				if(!checkbox.isSelected()){
					executeStep.performAction(SeleniumActions.Click, "","PatientBillPkgFinalizedChkBox");
					verifications.verify(SeleniumVerifications.Appears, "","PatientBillPkgFinalizedChkBox",false);
				}
				executeStep.performAction(SeleniumActions.Check, "","PatientBillPkgFinalizedChkBox");
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillEditChargeDialogbox",false);
				executeStep.performAction(SeleniumActions.Enter, "ItemQuantity","PatientBillPakageQtyIncluded");
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
				executeStep.performAction(SeleniumActions.Click, "","PatientBillEditChargeOkButton");
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
				executeStep.performAction(SeleniumActions.Click, "","");
				CommonUtilities.delay();
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillSaveButton",true);

			}else{
				System.out.println("All services are included in dyna package");
			}

		}
		
		//manually exclude laboratory test from the package

		int labTestRowCount = this.executeStep.getDriver().findElements(By.xpath("//table[@id='chargesTable']//label[@title = 'Lab Tests']")).size();
		for (int j=2; j<=labTestRowCount; j++){

			EnvironmentSetup.intGlobalLineItemCount = j;

			String img = this.executeStep.getDriver().findElement(By.xpath("(//table[@id='chargesTable']//label[@title = 'Lab Tests'])["+j+"]/parent::td/preceding-sibling::td/img")).getAttribute("src");

			if(img.contains("empty_flag.gif")){
				executeStep.performAction(SeleniumActions.Click, "","PatientBillCheckBox");
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillCheckBox",true);
				executeStep.performAction(SeleniumActions.Click, "","PatientBillEncludePackage");
				verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
				executeStep.performAction(SeleniumActions.Accept, "","Framework");
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
				executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
				verifications.verify(SeleniumVerifications.Appears, "","PatientBillExcludedFlag",false);

			}

		}
		
		//Now click on 'Exc In Pkg' button and then save the bill.
		executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);

		//(d) Click on 'Package Process'
		executeStep.performAction(SeleniumActions.Click, "","PatientBillPackageProcessButton");

		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);


		//verify radiology test are not added in the package
		
		int radiologyTestCount = this.executeStep.getDriver().findElements(By.xpath("//table[@id='chargesTable']//label[@title = 'Radiology Tests']")).size();
		System.out.println(radiologyTestCount+ "........................................");
		for (int j=1; j<radiologyTestCount; j++){
			EnvironmentSetup.intGlobalLineItemCount = j;
			verifications.verify(SeleniumVerifications.Appears, "","PatientBillExcludedFlag",false);

		}
   
		
	   //8 Go to sales screen. Enter MR number. Select bill type as Raise Bill, add few medicines
		// by giving quantity as 5 each and complete sales by clicking on raise Bill & Print.

		navigation.navigateTo(driver, "SalesLink", "SalesPage");
		Sales sales = new Sales(executeStep, verifications);
		sales.doSales("SalesItem1", "SalesPageBillType");
		sales.closePrescriptionTab();;

		//8b. Go to Sales Returns screen, enter the patient's MR number. 
		//Add items to the grid by giving 3 quantity each. Click on Raise Bill to complete sales returns.
		navigation.navigateTo(driver, "SalesReturnLink", "SalesReturnPage");
		SalesReturn salesReturn=new SalesReturn(executeStep, verifications);
		salesReturn.searchMRNo();
		salesReturn.doSalesReturn("SalesReturn1","OP");

		

		//9 Go to Add Patient Indent screen under Sales and Issues, enter the MR Number
		//Raise a patient indent by adding another set of serial and batch items (having packet size 1 and 10 and serial items) by giving quantity as 5. 
		//Save this indent in finalized status.
        navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		RaisePatientIndent raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales1", true);

		//9b. Go to Patient Indents List under Sales and Issues. 
		//Select the above generated indent and click on Sales.
		//9c. Complete sales by clicking on Raise Bill & Print.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");	
		raisePatientIndent.doSalesOrSalesReturnsForIndent(true, "IndentSales1");
		

		//10a. Go to Add Patient Return Indent screen under Sales and Issues, 
		//enter the MR number and add medicines by giving 3 quantity each. 
		//Save this indent in finalized status. 
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");		
		RaisePatientIndent raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("IndentSales1", false);

		//10b. Go to Patient Indents List under Sales and Issues. 
		//Select the above generated return indent and click on Sales Returns. 
		//10c. Complete sales returns by clicking on Raise Bill & Print.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");		
		raisePatientReturnIndent.doSalesOrSalesReturnsForIndent(false, "");
		/*EnvironmentSetup.selectByPartMatchInDropDown = true;
		executeStep.performAction(SeleniumActions.Select, "SalesPageBillType","SalesPageRaiseBillDropdown");
		verifications.verify(SeleniumVerifications.Selected,"SalesPageBillType" ,"SalesPageRaiseBillDropdown",true);*/

		//11a. Go to Issue to Patient screen under Sales and Issues, add items by giving quantity as 5. 
		//Click on Save to complete issues.
		navigation.navigateTo(driver, "PatientIssueLink", "PatientIssuePage");
		PatientIssue patientIssue = new PatientIssue(executeStep, verifications);
		patientIssue.searchPatientIssue();
		patientIssue.addPatientIssueItem("ItemIssuePatient");		
		patientIssue.saveIssue();

		//11b. Go to Returns from Patient screen under Sales and Issues, add the above issued items by giving quantity as 3. 
		//Click on Save to complete issue returns.
		navigation.navigateTo(driver, "PatientIssueReturnLink", "PatientIssueReturnPage");
		patientIssue = new PatientIssue(executeStep, verifications);
		//patientIssue.searchPatientIssue();
		patientIssue.returnPatientIssueItem("ItemIssuePatient");		
		patientIssue.saveIssueReturns();
  

		//12a. Go to Add Patient Indent screen under Sales and Issues, enter the MR Number. 
		//Raise a patient indent by adding another set of serial and batch items by giving quantity as 5. 
		//Save this indent in finalized status.
		navigation.navigateTo(driver, "RaiseIndentLink", "RaisePatientIndentPage");
		raisePatientIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientIndent.savePatientIndent("IndentSales2", true);
		
		//12b. Go to Patient Indents List under Sales and Issues. Select the above generated indent and click on Issue.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		raisePatientIndent.searchPatientIndent();
		raisePatientIndent.savePatientIndentIssue(true);

		//13a. Go to Add Patient Return Indent screen under Sales and Issues, enter the MR number and add above issued items by giving 3 quantity each. Save this indent in finalized status.
		navigation.navigateTo(driver, "RaisePatientReturnIndentLink", "RaisePatientReturnIndentScreen");		
		 raisePatientReturnIndent = new RaisePatientIndent(executeStep, verifications);
		raisePatientReturnIndent.savePatientIndent("IndentSales2", false);
		
		//13b. Go to Patient Indents List under Sales and Issues. Select the above generated return indent and click on Issue Returns.
		navigation.navigateTo(driver, "PatientIndentSearchLink", "PatientIndentSearchListPage");
		raisePatientIndent.searchPatientIndent();
		raisePatientIndent.savePatientIndentIssue(false);

		//14(a). After doing sales, issues, sales returns and issue returns, 
		//click on Pacakge Process and then save the bill.
		//14(b). Observe all the sale items and inventory items in bill.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");	
		patientBill = new PatientBill(executeStep, verifications);	
		patientBill.viewEditBills("BL");
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
        executeStep.performAction(SeleniumActions.Click, "","PatientBillPackageProcessButton");
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);

		//15. Do settlement in bill and close the bill.
		navigation.navigateTo(driver, "BillsLink", "BillListPage");
		mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");	
		patientBill = new PatientBill(executeStep, verifications);;

		patientBill.settleBills("Yes", "UNPAID", "OP");

		//16. Go to Laboratory Pending samples screen, select the test and click on collect menu option. Select the samples by ticking the check boxes and click on Save.
		//17. Go to laboratory reports screen > select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
		//18. Go to radiology reports > Select the test and click on View/Edit, choose a template then edit details and save. Mark complete,validate and signoff.
		//19. Go to Pending Services List, select the service and click on edit menu option mark completed and save.
		performOrderedTestsAndSignOffReports(navigation);


	}	


	public void performOrderedTestsAndSignOffReports(SiteNavigation navigation){
		//Click on collect sample link
		navigation.navigateTo(driver, "LaboratoryPendingSamplesLink", "LaboratoryPendingSamplesPage");
		MRNoSearch Search = new MRNoSearch(executeStep, verifications);
		Search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LaboratoryPendingSamplesPage");

		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesList");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSamplesCollectSampleMenu",true);

		executeStep.performAction(SeleniumActions.Click, "","PendingSamplesCollectSampleMenu");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);

		//collect samples and save
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();
		executeStep.performAction(SeleniumActions.CloseTab, "","CollectSamplePage");
		verifications.verify(SeleniumVerifications.Closes, "","CollectSamplePage",false);

		//Go to laboratory reports screen select the test and click on View/Edit, enter values (1,2,3,4,etc) complete,validate and Signoff.
		navigation.navigateTo(driver, "LaboratoryReportLink", "LaboratoryReportsPage");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
		executeStep.performAction(SeleniumActions.Click, "","LabReportsReportCheckBox");
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageResultTable");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);

		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();	

		//validate and sign off the pending radiology test
		navigation.navigateTo(driver, "RadiologyReportsLink", "RadiologyReportsPage");
		RadiologyPendingTests radiologyPendingTests = new RadiologyPendingTests(executeStep, verifications);
		radiologyPendingTests.radiologyPending(true);

		//Complete the pending service test
		navigation.navigateTo(driver, "PendingServicesListLink", "PendingServicesListPage");		
		ServicePending servicePending = new ServicePending(executeStep, verifications);
		servicePending.conductPendingServices();

		//Go to Patient/Visit EMR and observe.
		navigation.navigateTo(driver, "PatientEMRLink", "PatientEMRPage");
		Search.searchMRNo("PatientEMRPageMRNoField","PatientEMRPageSelectMRNoLi","PatientEMRPageGetDetailsButton","PatientEMRPage");
		verifications.verify(SeleniumVerifications.Appears, "","PatientEMRPage",false);

		
		testCaseStatus="PASS";
		System.out.println("Dyna package workflow");


	}
	
 
    
    @AfterTest
	public void closeBrowser(){
    	reporter.UpdateTestCaseReport(AutomationID, DataSet, "Dyna package workflow", null, "", "", testCaseStatus);
		driver.close();
	}
}



