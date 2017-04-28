package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class RadiologyPendingTests {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public RadiologyPendingTests(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public RadiologyPendingTests(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void setConductingDoctor(){
		
		System.out.println("Inside LabPendingTests setConductingDoctor ");

		System.out.println("setConductingDoctor Saved");

	}
	
	public void markComplete(){

		System.out.println("Inside LabPendingTests markComplete ");

		System.out.println("markComplete Saved");		
		
	}
	public void radiologyPending(boolean searchWithMRID)
	{
		if(searchWithMRID){
		MRNoSearch MrnoSearch=new MRNoSearch(executeStep,verifications);
		MrnoSearch.searchMRNo("RadiologyReportsPageMRNo","RadiologyReportsPageMRnoSelectList","RadiologyReportsPageSearchButton","RadiologyReportsPage");
		}else{
			MRNoSearch search = new MRNoSearch(executeStep, verifications);
			search.searchWithPatientName("RadiologyReportsPage","LabPendingTestSearchPatientNameInputbox","LabPendingTestSearchButton");
		}
		
		verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageResultTable",false);
		//verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageFlag",true);
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportsPageTestCheckBox");
		
		
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportsPageTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");

		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReport");
		verifications.verify(SeleniumVerifications.Opens, "","Test(s)ConductionDefaultReportFile",false);
		
		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReportFileSave");
		verifications.verify(SeleniumVerifications.Closes, "","Test(s)ConductionDefaultReportFile",false);
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		TestsConduction testsConduction = new TestsConduction(executeStep, verifications);
		testsConduction.signOffReports();
		
		System.out.println("Pending Radiology Test Conductions Saved");

	}
	
	//The below method is no more needed since default template is made mandatory. Can remove this after e2e signoff - Alamelu
	 
	public void radiologyReportDisplay()
	{
		MRNoSearch MrnoSearch=new MRNoSearch(executeStep,verifications);
		MrnoSearch.searchMRNo("RadiologyReportsPageMRNo","RadiologyReportsPageMRnoSelectList","RadiologyReportsPageSearchButton","RadiologyReportsPage");
		
		verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageResultTable",false);
		//verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageFlag",true);
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportReportCheckbox");
		
		
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportsPageTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		/*
		executeStep.performAction(SeleniumActions.Click, "","TestConductionSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		*/
		System.out.println("Pending Radiology Test Conductions Saved");

	}
	
	
	//added by Reena 
	public void reconductSignedoffRadiologyTest()
	{
		MRNoSearch MrnoSearch=new MRNoSearch(executeStep,verifications);
		MrnoSearch.searchMRNo("SignedoffMrNoField","SignedoffMrNoList","SignedoffMrNoSearchButton","SignedoffReportResultTable");
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		executeStep.performAction(SeleniumActions.Click, "","SignedoffReportResultTestsCheckBox");
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageResultTable");
		//below is introduced to beat the tool tip menu
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				
				}
		verifications.verify(SeleniumVerifications.Appears, "","LaboratoryReportsPageReconductMenu",false);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageReconductMenu");
		verifications.verify(SeleniumVerifications.Opens, "","ReconductRadiologyTestsPage",false);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		executeStep.performAction(SeleniumActions.Check, "","ReconductLabTestsPageCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","ReconductLabTestsPageCheckBox",false);
		executeStep.performAction(SeleniumActions.Enter, "TestsReconductReason","ReconductRadiologyTestsReconductReason");
		verifications.verify(SeleniumVerifications.Entered, "TestsReconductReason","ReconductRadiologyTestsReconductReason",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ReconductLabTestsPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","ReconductRadiologyTestsPage",false);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","ReconductRadiologyTestsPage");
		verifications.verify(SeleniumVerifications.Closes, "","ReconductRadiologyTestsPage",false);
		
		System.out.println("Pending Radiology Test re Conductions Saved");

	}
	
	
	public void radiologyReportWithoutSignOff()
	{
		MRNoSearch MrnoSearch=new MRNoSearch(executeStep,verifications);
		MrnoSearch.searchMRNo("RadiologyReportsPageMRNo","RadiologyReportsPageMRnoSelectList","RadiologyReportsPageSearchButton","RadiologyReportsPage");
		
		verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageResultTable",false);
		//verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageFlag",true);
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportReportCheckbox");
		
		
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","RadiologyReportsPageTestEditOption",false);
		
		executeStep.performAction(SeleniumActions.Click, "","RadiologyReportsPageTestEditOption");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		
		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReport");
		verifications.verify(SeleniumVerifications.Opens, "","Test(s)ConductionDefaultReportFile",false);
		
		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReportFileSave");
		verifications.verify(SeleniumVerifications.Closes, "","Test(s)ConductionDefaultReportFile",false);
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		TestsConduction testsConduction = new TestsConduction(executeStep, verifications);
		testsConduction.conductRadiologyTestsWithoutSignoff();
		
		System.out.println("Pending Radiology Test Conductions Saved");

	}
	public void reconductRadiology()
	{
		MRNoSearch MrnoSearch=new MRNoSearch(executeStep,verifications);
		MrnoSearch.searchMRNo("RadiologyReportsPageMRNo","RadiologyReportsPageMRnoSelectList","RadiologyReportsPageSearchButton","RadiologyReportsPage");
		
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageResultTable");
		//below is introduced to beat the tool tip menu
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				
				}
		verifications.verify(SeleniumVerifications.Appears, "","LaboratoryReportsPageReconductMenu",false);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		executeStep.performAction(SeleniumActions.Click, "","LaboratoryReportsPageReconductMenu");
		verifications.verify(SeleniumVerifications.Opens, "","ReconductRadiologyTestsPage",false);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		executeStep.performAction(SeleniumActions.Check, "","ReconductLabTestsPageCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","ReconductLabTestsPageCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ReconductLabTestsPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","ReconductRadiologyTestsPage",false);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","ReconductRadiologyTestsPage");
		verifications.verify(SeleniumVerifications.Closes, "","ReconductRadiologyTestsPage",false);
		
		System.out.println("Pending Radiology Test re Conductions Saved");

	}
	
	public void IncomingRadiologyTestsIncomingSampleGenericDetails(){
		
		System.out.println("Entered IncomingRadiologyTestsGenericDetails ");
		
	executeStep.performAction(SeleniumActions.Enter, "IncomingHospitalName","IncomingRadiologyTestsHospitalName");        // new data column to be added
	verifications.verify(SeleniumVerifications.Entered, "IncomingHospitalName","IncomingRadiologyTestsHospitalName",false);
	
	executeStep.performAction(SeleniumActions.Select, "Title","IncomingRadiologyTestsPatientTitle");
	verifications.verify(SeleniumVerifications.Selected, "Title","IncomingRadiologyTestsPatientTitle",false);
	
	executeStep.performAction(SeleniumActions.Enter, "PatientFirstName","IncomingRadiologyTestsPatientName");
	verifications.verify(SeleniumVerifications.Entered, "PatientFirstName","IncomingRadiologyTestsPatientName",false);
	
	executeStep.performAction(SeleniumActions.Select, "Gender","IncomingRadiologyTestsPatientGender");
	verifications.verify(SeleniumVerifications.Selected, "Gender","IncomingRadiologyTestsPatientGender",false);
	
	executeStep.performAction(SeleniumActions.Enter, "Age","IncomingRadiologyTestsPatientAge");
	verifications.verify(SeleniumVerifications.Entered, "Age","IncomingRadiologyTestsPatientAge",false);	
	
	
	executeStep.performAction(SeleniumActions.Enter, "PatientMobileNumber","IncomingRadiologyTestsPatientPhone");
	verifications.verify(SeleniumVerifications.Entered, "PatientMobileNumber","IncomingRadiologyTestsPatientPhone",false);
	
	executeStep.performAction(SeleniumActions.Select, "BillType","IncomingRadiologyTestsBillType");
	verifications.verify(SeleniumVerifications.Selected, "BillType","IncomingRadiologyTestsBillType",false);
	
	executeStep.performAction(SeleniumActions.Select, "RatePlan","IncomingRadiologyTestsRatePlan");
	verifications.verify(SeleniumVerifications.Selected, "RatePlan","IncomingRadiologyTestsRatePlan",true);	
		
	
	}
	
	public void addItems(String lineItem){
		
		 System.out.println("Add Items for incoming samples and radiology  ");
		
				executeStep.performAction(SeleniumActions.Click, "", "IncomingRadiologyTestsAddItemButton");
				verifications.verify(SeleniumVerifications.Appears, "","IncomingRadiologyTestsAddItemScreen",false);
				
				//EnvironmentSetup.replaceData= true;
				EnvironmentSetup.lineItemCount = 0;
				EnvironmentSetup.UseLineItem = true;
				EnvironmentSetup.LineItemIdForExec = lineItem;
				System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
				
				DbFunctions dbFunction = new DbFunctions();
				int rowCount=0;
				rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
				System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
				EnvironmentSetup.selectByPartMatchInDropDown = true;
				for(int i=0; i<rowCount; i++){			
					EnvironmentSetup.UseLineItem = true;
					
					executeStep.performAction(SeleniumActions.Enter, "Item","IncomingRadiologyTestsItemName");
					verifications.verify(SeleniumVerifications.Appears, "","IncomingRadiologyTestItemContainer",false);        //Added on 14/3
					
					executeStep.performAction(SeleniumActions.Click, "", "IncomingRadiologyTestItemContainer");    // added on 23/2
					verifications.verify(SeleniumVerifications.Entered, "Item","IncomingRadiologyTestsItemName",false);
					
					if(lineItem.equalsIgnoreCase("IncomingSample")){
						
						executeStep.performAction(SeleniumActions.Enter, "SampleNumber","IncomingSampleRegistrationAddItemSampleNo");
						verifications.verify(SeleniumVerifications.Entered, "SampleNumber","IncomingSampleRegistrationAddItemSampleNo",false);
						
						executeStep.performAction(SeleniumActions.Select, "SampleType","IncomingSampleRegistrationAddItemSampleType");
						verifications.verify(SeleniumVerifications.Selected, "SampleType","IncomingSampleRegistrationAddItemSampleType",false);
						
						
					}else{
					
				//	executeStep.performAction(SeleniumActions.Enter, "ConsultingDoctor","IncomingRadiologyTestsConsultingDr");
				//	verifications.verify(SeleniumVerifications.Entered, "ConsultingDoctor","IncomingRadiologyTestsConsultingDr",false);
					}			
					EnvironmentSetup.UseLineItem = false;

					executeStep.performAction(SeleniumActions.Click, "","IncomingRadiologyTestsAddItemScreenAddBtn");
					verifications.verify(SeleniumVerifications.Appears, "","IncomingRadiologyTestsAddItemScreen",false);
			
					EnvironmentSetup.lineItemCount ++;
					
				}
				
				EnvironmentSetup.selectByPartMatchInDropDown = false;
				EnvironmentSetup.lineItemCount = 0;
				
				executeStep.performAction(SeleniumActions.Click, "","IncomingRadiologyTestsAddItemScreenCloseBtn");
				
				if(lineItem.equalsIgnoreCase("IncomingSample")){
					verifications.verify(SeleniumVerifications.Appears, "","IncomingSampleRegistrationScreen",false);
				}else{
				verifications.verify(SeleniumVerifications.Appears, "","IncomingRadiologyTestsScreen",false);
				}
				
				executeStep.performAction(SeleniumActions.Click, "","IncomingRadiologyTestsSaveButton");
				
				if(lineItem.equalsIgnoreCase("IncomingSample")){
					verifications.verify(SeleniumVerifications.Appears, "","IncomingSampleRegistrationScreen",false);
					
					verifications.verify(SeleniumVerifications.Opens, "","IncomingSampleTextReport",true);
					executeStep.performAction(SeleniumActions.CloseTab, "","IncomingSampleTextReport");
					verifications.verify(SeleniumVerifications.Closes, "","IncomingSampleTextReport",true);
					
				}else{
				verifications.verify(SeleniumVerifications.Appears, "","IncomingRadiologyTestsScreen",false);
				}
	}
	
	public void searchPatient(){
		 System.out.println("Search bill Items for incoming samples apending bil  ");
		
		 executeStep.performAction(SeleniumActions.Click, "","IncomingSamplePendingBillMoreOptions");
			verifications.verify(SeleniumVerifications.Appears, "","IncomingSamplePendingBillsScreen",false);
		
		 executeStep.performAction(SeleniumActions.Enter, "PatientFirstName","IncomingSamplePendingBillSearchPatientName");
		verifications.verify(SeleniumVerifications.Entered, "PatientFirstName","IncomingSamplePendingBillSearchPatientName",false);
		
		executeStep.performAction(SeleniumActions.Click, "","IncomingSamplePendingBillSearchBtn");
		verifications.verify(SeleniumVerifications.Appears, "","IncomingSamplePendingBillResultTable",false);
	}

	public void viewEditIncomingSampleBill(){
		executeStep.performAction(SeleniumActions.Click, "","IncomingSamplePendingBillResult");
		verifications.verify(SeleniumVerifications.Appears, "","IncomingSamplePendingBillViewEdit",false);
	
		
		executeStep.performAction(SeleniumActions.Click, "","IncomingSamplePendingBillViewEdit");
	
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
	
	}
	public void amendRadiology(){
		MRNoSearch MrnoSearch=new MRNoSearch(executeStep,verifications);
		MrnoSearch.searchMRNo("PendingSampleAssertionsSearchMRID","PendingSampleAssertionsSearchMRIDContainer","PendingSampleAssertionsSearchButton","PendingSampleAssertionsTable");
		
		//executeStep.performAction(SeleniumActions.Click, "","SignedOffReportListAlert");
		
		executeStep.performAction(SeleniumActions.Check, "","ReportNameCheckbox");
		verifications.verify(SeleniumVerifications.Checked, "","ReportNameCheckbox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","SignedOffReport");	
		verifications.verify(SeleniumVerifications.Appears, "","SignedOffReportAmendReportMenu",false);
		
		executeStep.performAction(SeleniumActions.Click, "","SignedOffReportAmendReportMenu");
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AmendButton");	
		verifications.verify(SeleniumVerifications.Appears, "","AmendmentReason",false);
		
		
		executeStep.performAction(SeleniumActions.Enter, "AmendmentReason","AmendmentReason");
		verifications.verify(SeleniumVerifications.Entered, "AmendmentReason","AmendmentReason",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AmendmentReasonOKButton");	
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
//		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReport");
//		verifications.verify(SeleniumVerifications.Opens, "","Test(s)ConductionDefaultReportFile",false);
//		
//		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReportFileSave");
//		verifications.verify(SeleniumVerifications.Closes, "","Test(s)ConductionDefaultReportFile",false);
//		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		TestsConduction testsConduction = new TestsConduction(executeStep, verifications);
		testsConduction.signOffReports();
		
		System.out.println("Amend Radiology Test Conductions Saved");
	}
	
}
