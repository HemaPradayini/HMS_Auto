package e2e;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.ClaimsSubmission;
import reusableFunctions.CollectSamples;
import reusableFunctions.ConductingDoctorPayment;
import reusableFunctions.ConductionPackages;
import reusableFunctions.LabPendingTests;
import reusableFunctions.Login;
import reusableFunctions.MRNoSearch;
import reusableFunctions.Order;
import reusableFunctions.OutsourcePayments;
import reusableFunctions.PatientBill;
import reusableFunctions.PatientPackages;
import reusableFunctions.PaymentVoucher;
import reusableFunctions.PaymentVoucherDashboard;
import reusableFunctions.RadiologyPendingTests;
import reusableFunctions.Registration;
import reusableFunctions.ServicePending;
import reusableFunctions.SiteNavigation;
import reusableFunctions.StockEntry;
import reusableFunctions.TestsConduction;
import seleniumWebUIFunctions.KeywordExecutionLibrary;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PaymentsWorkflow {
	
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
		AutomationID = "Login - PaymentsWorkflow";
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
		AutomationID = "Login - PaymentsWorkflow";
		DataSet = "TS_091";
				
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
	public void PaymentsWorkflow(){
		DataSet = "TS_091";
		AutomationID = "PaymentsWorkflow";
		List<String> lineItemIDs = null;
		System.out.println("Inside Test PaymentsWorkflow - Before Navigation");
		
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
	    verifications = new VerificationFunctions(driver, AutomationID, DataSet);

		SiteNavigation navigation = new SiteNavigation(AutomationID, DataSet);
   	   navigation.navigateTo(driver, "OPRegistrationLink", "OPRegistrationScreen");
			
		//Register Op patient by selecting Consulting Doctor &
		//Referral Doctor as 'doc_A'& order Test payments_ workflow from registration order,
    	Registration OPReg = new Registration(executeStep, verifications);	
    	OPReg.unCheckPrimarySponsor();
		OPReg.setBillType();
		OPReg.RegisterPatientGenericDetails();
		OPReg.visitInformationDetails();
		OPReg.RegisterPatientReferralDoctor();
		OPReg.storeDetails();

		//2.Now go and finalize the bill. bill page change to finalized
		executeStep.performAction(SeleniumActions.Click, "","ProceedToBillingLink");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",true);
		PatientBill patientBill = new PatientBill(executeStep, verifications);
		patientBill.setBillStatus("Yes");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		
		executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
		
		//3.Now Go to conducting Doctor payments screen under payment Module and search by giving Doctor name as 'DocA'.
		navigation.navigateTo(driver, "ConductingDoctorPaymentLink", "ConductingDoctorPaymentPage");
		ConductingDoctorPayment conductingDocPayment = new ConductingDoctorPayment(executeStep, verifications);
		conductingDocPayment.searchRecords();

		//4.Select the Patient record by checking check box and click on save button.
		conductingDocPayment.checkPatientRecord();
		
	   //5.Now go Laboratory Pending test under laboratory module, give the mr no of patient and click on search.
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		LabPendingTests labTest = new LabPendingTests(executeStep, verifications);
		
		//6.Now select the test in pending test screen, click on it and select outsource.// changed to collect samlple
		
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");	
		//verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestOutsourceOption",false);
		CommonUtilities.delay(15);
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestCollectSample");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);
		
		CollectSamples collectSamples = new CollectSamples(executeStep, verifications);
		collectSamples.collectSamplesAndSave();


		/*//7.Now select the outsource name as 'out1' and save the screen.
		//set outsource name as Out_1 -- save
		executeStep.performAction(SeleniumActions.Select, "OutsourceName","OutsourceNameDropDown");
		verifications.verify(SeleniumVerifications.Selected, "OutsourceName","OutsourceNameDropDown",false);
		executeStep.performAction(SeleniumActions.Click, "","AssignOutsourceSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","AssignOutsourceScreen",false);*/
		
		//8.Now click on laboratory Reports list in Assign outsource screen.
		//Assign outsource screen.-- Now click on laboratory Reports
		
		navigation.navigateTo(driver, "LabPendingTestsLink", "LabPendingTests");
		search.searchMRNo("LabPendingTestPatientNameField","LabPendingTestsPatientOPNoSelectDiv","LabPendingTestSearchButton","LabPendingTests");
		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestResultToClick");
		verifications.verify(SeleniumVerifications.Appears, "","LabPendingTestEditOption",false);

		executeStep.performAction(SeleniumActions.Click, "","LabPendingTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);

		//Steps to conduct test
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.conductTests();
		
		/*executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsListLink");
		verifications.verify(SeleniumVerifications.Appears, "","LaboratoryReportsPage",false);
		
		//9.Now click on the test name and redirect to test conduction screen and conduct the test and mark as complete.
		//check bok: view/edit report
		//navigate to test conduction page -- complete all and validate all
		//set conducting doc: rakesh and signoff
		executeStep.performAction(SeleniumActions.Click, "","SampleTestCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","SampleTestCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabReportsList");
		verifications.verify(SeleniumVerifications.Appears, "","ViewAndEditResultsOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ViewAndEditResultsOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		TestsConduction testConduction = new TestsConduction(executeStep, verifications);
		testConduction.signOffReports();*/

		//10.Now go to Outsource Payment screen under Payments module,give the mr no of patient and outhouse name as 'out_1' and search.
		navigation.navigateTo(driver, "OutsourcePaymentsLink", "OutsourcePaymentsScreen");
		OutsourcePayments ousourcepayments = new OutsourcePayments(executeStep, verifications);
		ousourcepayments.searchOutsourceRecords();

		//11.In outsource payment screen select the test and click on save.
		//after checking it comes under view posted bill
		ousourcepayments.outsourcepayments();
	

		//12.Now go to Payment due screen under Payments module and search with out_1 and select more options
		//a. select payment type as Doctor and Date as todays date and search.
		navigation.navigateTo(driver, "PaymentsDueLink", "PaymentVoucherScreen");
		PaymentVoucherDashboard paymentvoucher = new PaymentVoucherDashboard(executeStep, verifications);
		paymentvoucher.searchPayeeWithDoctor("Doctor");

		//13.now select the record of patient and click on payment voucher 'OP Only'.
		//payment module; payments due page --payee name - doc_A serach--left click payment voucher op only
		paymentvoucher.selectPaymentVoucherOP();
		CommonUtilities.delay();
		
		//14.Now in Payment voucher screen verify the net payment and save the screen.
        verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherNetPaymentField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherScreen",false);
		
		verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
		executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
		verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);


		//15.Now go to Payment due screen under Payments module -- search with doc_A
		//and select payment type as outgoing tests and Date as todays date and search.
		navigation.navigateTo(driver, "PaymentsDueLink", "PaymentVoucherScreen");
		paymentvoucher = new PaymentVoucherDashboard(executeStep, verifications);
		paymentvoucher.searchPayeeWithDoctor("Outgoing Tests");

		//16.Now select the record with 'Test Payments_workfow' and left click on Payment voucher.
		paymentvoucher.selectPaymentVoucher();
		
		//17.Now in Payment voucher screen check the net payment and save the screen.
        verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherNetPaymentField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherScreen",false);
		
		verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
		executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
		verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
		
		//18.Now go to stock entry screen under procurement module, do a stock entry and save the screen.
		navigation.navigateTo(driver, "StockEntryLink", "StockEntryScreen");
		StockEntry stockentry = new StockEntry(executeStep, verifications);
		
		executeStep.performAction(SeleniumActions.Enter, "StockEntrySupplierName","SupplierField");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierNameList",true);
		
		executeStep.performAction(SeleniumActions.Click, "","SupplierNameList");
		verifications.verify(SeleniumVerifications.Entered, "StockEntrySupplierName","SupplierField",false);
		DbFunctions dbFunction = new DbFunctions();
		dbFunction.storeDate(this.executeStep.getDataSet(), "InvoiceNumber","N",1);
		
		executeStep.performAction(SeleniumActions.Enter, "InvoiceNumber","StockEntryInvoiceNo");
		verifications.verify(SeleniumVerifications.Entered, "InvoiceNumber","StockEntryInvoiceNo",true);
		
		stockentry.addItemsToStocks("stockentryItem");
		
		executeStep.performAction(SeleniumActions.Click, "","SaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
		executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
		executeStep.performAction(SeleniumActions.CloseTab, "","PrintTextReportTab");
		verifications.verify(SeleniumVerifications.Closes, "","PrintTextReportTab",false);
		
		//19.Now go to Supplier Payment screen under Payments module,give the supplier name and search.
		navigation.navigateTo(driver, "SupplierPaymentLink", "SupplierPaymentPage");
		
		executeStep.performAction(SeleniumActions.Enter, "StockEntrySupplierName","SupplierPaymentsSupplier");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierPaymentsSupplierList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","SupplierPaymentsSupplierList");
		verifications.verify(SeleniumVerifications.Entered, "StockEntrySupplierName","SupplierPaymentsSupplier",false);
		
		executeStep.performAction(SeleniumActions.Click, "","SupplierPaymentSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","SupplierPaymentPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","CheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","CheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","SupplierPaymentsSaveButton");
		CommonUtilities.delay();
		verifications.verify(SeleniumVerifications.Appears, "","SupplierPaymentsPostedpayments",false);
		
		//20.Now go to Payment due screen under Payments module and select payment type as supplier and Date as todays date and search.
		navigation.navigateTo(driver, "PaymentsDueLink", "PaymentVoucherScreen");
		paymentvoucher = new PaymentVoucherDashboard(executeStep, verifications);
		paymentvoucher.searchPayeeWithDoctor("Supplier");
		
		//21.Now select the supplier record and click on Payment voucher.
		paymentvoucher.selectPaymentVoucher();
		
		//22.Now in Payment voucher screen check the net payment and save the screen.
        verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherNetPaymentField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PaymentVoucherSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","PaymentVoucherScreen",false);
		
		verifications.verify(SeleniumVerifications.Opens, "","ErrorPage",true);
		executeStep.performAction(SeleniumActions.CloseTab, "","ErrorPage");
		verifications.verify(SeleniumVerifications.Closes, "","ErrorPage",true);
		
		//23.Go to Paid PAyments screen under payment module, select payment type as all and date as current date.
		navigation.navigateTo(driver, "PaymentVoucherLink", "PaymentVoucherPage");
		PaymentVoucher paymentvouchers = new PaymentVoucher(executeStep, verifications);
		paymentvouchers.SearchpayeeWithdate();
		paymentvouchers.ExportCsv();
		
		testCaseStatus="PASS";
		System.out.println("Paymentwork Flow");
		


}
	
	@AfterTest
	public void closeBrowser(){
		reporter.UpdateTestCaseReport(AutomationID, DataSet, "Paymentwork Flow", null, "", "", testCaseStatus);
		//driver.close();
	}
	
	
}
