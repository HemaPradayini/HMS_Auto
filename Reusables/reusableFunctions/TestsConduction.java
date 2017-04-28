package reusableFunctions;

import org.openqa.selenium.WebElement;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

public class TestsConduction {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public TestsConduction(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public TestsConduction(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void conductTests(){
		
		System.out.println("Inside TestsConduction conductTests ");

		//Vitals Reusable
		//executeStep.getDriver().findElement(By.xpath("//select[@id='sampleTypeForTests']")).click();

		executeStep.performAction(SeleniumActions.Select, "ConductionStatus","TestConductionStatusDropDown");
		verifications.verify(SeleniumVerifications.Selected, "ConductionStatus","TestConductionStatusDropDown",false);
		
		//executeStep.performAction(SeleniumActions.Check, "","CollectSamplesForTest2");
		//verifications.verify(SeleniumVerifications.Checked, "","CollectSamplesForTest2",false);
		
		executeStep.performAction(SeleniumActions.Enter, "TestConductionValue","TestConductionValueField");
		verifications.verify(SeleniumVerifications.Entered, "TestConductionValue","TestConductionValueField",false);
		
		executeStep.performAction(SeleniumActions.Select, "TestSeverity","TestConductionSeverityDropDown");
		verifications.verify(SeleniumVerifications.Selected, "TestSeverity","TestConductionSeverityDropDown",false);
		
try{
		WebElement	 we = this.executeStep.getDriver().findElement(By.xpath("//textarea[@id='script2']"));        //Added by abhishek

		if(we.isDisplayed()){
			executeStep.performAction(SeleniumActions.Enter, "TestConductionValue","TestConductionValueField2");
			verifications.verify(SeleniumVerifications.Entered, "TestConductionValue","TestConductionValueField2",false);
			
			executeStep.performAction(SeleniumActions.Select, "TestSeverity","TestConductionSeverityDropDown2");
			verifications.verify(SeleniumVerifications.Selected, "TestSeverity","TestConductionSeverityDropDown2",false);

		}
		}catch(Exception ex){
			System.out.println("Lab with 2 values and severity not present" + ex.toString());
		}
		
		
		/*	
 //Alamelu
 		//commented since it is not mandatory and not getting populated properly - C N Alamelu
		EnvironmentSetup.selectByPartMatchInDropDown = true;
		executeStep.performAction(SeleniumActions.Select, "ConductingDoctor","TestConductionConductingDrDropDown");
		verifications.verify(SeleniumVerifications.Selected, "ConductingDoctor","TestConductionConductingDrDropDown",false);
		EnvironmentSetup.selectByPartMatchInDropDown = false;
*/		
		signOffReports();
		
		System.out.println("Conduct Tests Screen saved");

	}
	public void conductTestsWithoutSignoff(){
		
		System.out.println("Inside TestsConduction conductTests ");

		//Vitals Reusable
		//executeStep.getDriver().findElement(By.xpath("//select[@id='sampleTypeForTests']")).click();

		executeStep.performAction(SeleniumActions.Select, "ConductionStatus","TestConductionStatusDropDown");
		verifications.verify(SeleniumVerifications.Selected, "ConductionStatus","TestConductionStatusDropDown",false);
		
		//executeStep.performAction(SeleniumActions.Check, "","CollectSamplesForTest2");
		//verifications.verify(SeleniumVerifications.Checked, "","CollectSamplesForTest2",false);
		
		executeStep.performAction(SeleniumActions.Enter, "TestConductionValue","TestConductionValueField");
		verifications.verify(SeleniumVerifications.Entered, "TestConductionValue","TestConductionValueField",false);
		
		executeStep.performAction(SeleniumActions.Select, "TestSeverity","TestConductionSeverityDropDown");
		verifications.verify(SeleniumVerifications.Selected, "TestSeverity","TestConductionSeverityDropDown",false);
		
		executeStep.performAction(SeleniumActions.Check, "","TestConductionCompleteAllCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","TestConductionCompleteAllCheckBox",false);

		executeStep.performAction(SeleniumActions.Check, "","TestConductionAllValidatedCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","TestConductionAllValidatedCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TestConductionSaveButton");	
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionSignOffArrow",false);
		
		System.out.println("Conduct Tests Screen saved");

	}
	public void conductRadiologyTests(){
		
		System.out.println("Inside TestsConduction conductTests ");

		
		EnvironmentSetup.selectByPartMatchInDropDown = true;
		//executeStep.performAction(SeleniumActions.Select, "RadiologyConductionStatus","RadiationTestConductionStatusDropDown");
		//verifications.verify(SeleniumVerifications.Selected, "RadiologyConductionStatus","RadiationTestConductionStatusDropDown",false);
		
	//	executeStep.performAction(SeleniumActions.Select, "RadiologyConductionResult","RadiationTestConductionResultDropDown");
		
	//	verifications.verify(SeleniumVerifications.Opens, "","Test(s)ConductionDefaultReportFile",false);
		
		/*	
		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReport");
		verifications.verify(SeleniumVerifications.Opens, "","Test(s)ConductionDefaultReportFile",false);
		
		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReportFileSave");
		verifications.verify(SeleniumVerifications.Closes, "","Test(s)ConductionDefaultReportFile",false);
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
	*/
		
	// verifications.verify(SeleniumVerifications.Selected, "RadiologyConductionResult","RadiationTestConductionResultDropDown",false);
	
	//	executeStep.performAction(SeleniumActions.Select, "RadiologyConductingDr","TestConductionConductingDrDropDown");
	//	verifications.verify(SeleniumVerifications.Selected, "RadiologyConductingDr","TestConductionConductingDrDropDown",false);
		
		EnvironmentSetup.selectByPartMatchInDropDown = false;
		signOffReports();
		
		System.out.println("Conduct Tests Screen saved");

	}
	public void conductRadiologyTestsWithoutSignoff(){
		
		System.out.println("Inside TestsConduction conductTests ");

		
		EnvironmentSetup.selectByPartMatchInDropDown = true;
		//executeStep.performAction(SeleniumActions.Select, "RadiologyConductionStatus","RadiationTestConductionStatusDropDown");
		//verifications.verify(SeleniumVerifications.Selected, "RadiologyConductionStatus","RadiationTestConductionStatusDropDown",false);
		
		//executeStep.performAction(SeleniumActions.Select, "RadiologyConductionResult","RadiationTestConductionResultDropDown");
		
		//verifications.verify(SeleniumVerifications.Opens, "","Test(s)ConductionDefaultReportFile",false);
	/*	
		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReport");
		verifications.verify(SeleniumVerifications.Opens, "","Test(s)ConductionDefaultReportFile",false);
		
		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReportFileSave");
		verifications.verify(SeleniumVerifications.Closes, "","Test(s)ConductionDefaultReportFile",false);
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
	*/	
		executeStep.performAction(SeleniumActions.Click, "","Test(s)ConductionDefaultReportFileSave");
		verifications.verify(SeleniumVerifications.Closes, "","Test(s)ConductionDefaultReportFile",false);
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionScreen",false);
		
		//verifications.verify(SeleniumVerifications.Selected, "RadiologyConductionResult","RadiationTestConductionResultDropDown",false);
	
		//executeStep.performAction(SeleniumActions.Select, "RadiologyConductingDr","TestConductionConductingDrDropDown");
	//	verifications.verify(SeleniumVerifications.Selected, "RadiologyConductingDr","TestConductionConductingDrDropDown",false);
		
		EnvironmentSetup.selectByPartMatchInDropDown = false;
		executeStep.performAction(SeleniumActions.Check, "","TestConductionCompleteAllCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","TestConductionCompleteAllCheckBox",false);

		executeStep.performAction(SeleniumActions.Check, "","TestConductionAllValidatedCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","TestConductionAllValidatedCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TestConductionSaveButton");	
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionSignOffArrow",false);
		
		System.out.println("Conduct Tests Screen saved");

	}
	
	public void signOffReports(){
		System.out.println("Inside Signed off reports ");
		executeStep.performAction(SeleniumActions.Check, "","TestConductionCompleteAllCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","TestConductionCompleteAllCheckBox",false);

		executeStep.performAction(SeleniumActions.Check, "","TestConductionAllValidatedCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","TestConductionAllValidatedCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TestConductionSaveButton");	
		signOff();
		
		System.out.println("Reports Sign Off Complete ");
	}
	public void reconductTest(){
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("LaboratoryReportsPageMRNo","LaboratoryReportsPageMRList","LaboratoryReportsPageSearchButton","LaboratoryReportsPage");
		
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
		verifications.verify(SeleniumVerifications.Opens, "","ReconductLabTestsPage",false);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		executeStep.performAction(SeleniumActions.Check, "","ReconductLabTestsPageCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","ReconductLabTestsPageCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ReconductLabTestsPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","ReconductLabTestsPage",false);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","ReconductLabTestsPage");
		verifications.verify(SeleniumVerifications.Closes, "","ReconductLabTestsPage",false);
	}
	
	public void saveConductionResults(){
		System.out.println("Inside TestsConduction saveConductionResults ");

		executeStep.performAction(SeleniumActions.Select, "ConductionStatus","TestConductionStatusDropDown");
		verifications.verify(SeleniumVerifications.Selected, "ConductionStatus","TestConductionStatusDropDown",false);
		
		executeStep.performAction(SeleniumActions.Check, "","TestConductionCompleteAllCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","TestConductionCompleteAllCheckBox",false);

		executeStep.performAction(SeleniumActions.Check, "","TestConductionAllValidatedCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","TestConductionAllValidatedCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TestConductionSaveButton");	
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionSignOffArrow",false);
	}
	
	public void signOff(){                              //made public by abhishek
		verifications.verify(SeleniumVerifications.Appears, "","TestConductionSignOffArrow",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TestConductionSignOffArrow");	// Added by Tejaswini - Bug Fix

		verifications.verify(SeleniumVerifications.Appears, "","TestConductionSignOffCheckBox",false); // Added by Tejaswini - Bug Fix

		executeStep.performAction(SeleniumActions.Check, "","TestConductionSignOffCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","TestConductionSignOffCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TestConductionSignOffButton");
		verifications.verify(SeleniumVerifications.NotPresent, "","TestConductionSignOffArrow",false);
	}
	//added by Reena 
	public void reconductSignedoffLabTest(){ 
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("SignedoffMrNoField","SignedoffMrNoList","SignedoffMrNoSearchButton","SignedoffReportResultTable");
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
		verifications.verify(SeleniumVerifications.Opens, "","ReconductLabTestsPage",false);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		
		}
		
		
		
		executeStep.performAction(SeleniumActions.Check, "","ReconductLabTestsPageCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","ReconductLabTestsPageCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Enter, "TestsReconductReason","ReconductLaboratoryTestsReconductReason");
		verifications.verify(SeleniumVerifications.Entered, "TestsReconductReason","ReconductLaboratoryTestsReconductReason",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ReconductLabTestsPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","ReconductLabTestsPage",false);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","ReconductLabTestsPage");
		verifications.verify(SeleniumVerifications.Closes, "","ReconductLabTestsPage",false);
	}
	

public void amendReports(){
		
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
		
		executeStep.performAction(SeleniumActions.Enter, "TestConductionValue2","TestConductionValueField");
		verifications.verify(SeleniumVerifications.Entered, "TestConductionValue2","TestConductionValueField",false);
		
		executeStep.performAction(SeleniumActions.Select, "TestSeverity","TestConductionSeverityDropDown");
		verifications.verify(SeleniumVerifications.Selected, "TestSeverity","TestConductionSeverityDropDown",false);
		
		signOffReports();
		
		
		
		
	}

public void addAddendum(){
	
	executeStep.performAction(SeleniumActions.Check, "","ReportNameCheckbox");
	verifications.verify(SeleniumVerifications.Checked, "","ReportNameCheckbox",false);
	
	executeStep.performAction(SeleniumActions.Click, "","SignedOffReport");	
	verifications.verify(SeleniumVerifications.Appears, "","SignedOffReporAddAddendumMenu",false);
	
	executeStep.performAction(SeleniumActions.Click, "","SignedOffReporAddAddendumMenu");
	verifications.verify(SeleniumVerifications.Appears, "","ManageReportsPage",false);
	
	executeStep.performAction(SeleniumActions.Click, "","AddAddendumLink");	
	verifications.verify(SeleniumVerifications.Opens, "","AddenDumReports",false);
	executeStep.performAction(SeleniumActions.Click, "","AddenDumReportsSaveButton");
	
	
	executeStep.performAction(SeleniumActions.CloseTab, "","ReconductLabTestsPage");
	verifications.verify(SeleniumVerifications.Closes, "","ReconductLabTestsPage",false);
	
	 
	executeStep.performAction(SeleniumActions.Click, "","ManageReportsSaveButton");
	verifications.verify(SeleniumVerifications.Appears, "","ManageReportsPage",false);
	
	}

public void signOffAddendum(){
	
	executeStep.performAction(SeleniumActions.Check, "","ReportNameCheckbox");
	verifications.verify(SeleniumVerifications.Checked, "","ReportNameCheckbox",false);
	
	executeStep.performAction(SeleniumActions.Click, "","SignedOffReport");	
	verifications.verify(SeleniumVerifications.Appears, "","SignOffAddendumMenu",false);
	
	executeStep.performAction(SeleniumActions.Click, "","SignOffAddendumMenu");	
	verifications.verify(SeleniumVerifications.Appears, "","SignedOffReportListPage",false);
	
}
public void revertSignOff(){
	
	executeStep.performAction(SeleniumActions.Check, "","ReportNameCheckbox");
	verifications.verify(SeleniumVerifications.Checked, "","ReportNameCheckbox",false);
	
	executeStep.performAction(SeleniumActions.Click, "","RevertSignOffButton");	
	verifications.verify(SeleniumVerifications.Appears, "","SignedOffReportListPage",false);
	
	
}

}
