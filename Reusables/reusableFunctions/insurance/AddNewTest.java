package reusableFunctions.insurance;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import GenericFunctions.CommonUtilities;
import GenericFunctions.DbFunctions;
import GenericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import reusableFunctions.MRNoSearch;
import reusableFunctions.SiteNavigation;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class AddNewTest {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public AddNewTest(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public AddNewTest(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void AddNewTestForLab(){
		executeStep.performAction(SeleniumActions.Click, "","DiagnosticTestListPageAddNewTest");
		verifications.verify(SeleniumVerifications.Appears, "", "AddNewTestPage", false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddNewTestPgTestName","AddNewTestPageTestName");
		verifications.verify(SeleniumVerifications.Entered, "AddNewTestPgTestName","AddNewTestPageTestName",false);
		  
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPgDeptName","AddNewTestPageDeptName");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgDeptName","AddNewTestPageDeptName",false);
		
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPgServiceGroup","AddNewTestPageServiceGroup");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgServiceGroup","AddNewTestPageServiceGroup",false);
		
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPageServiceSubGroup","AddNewTestPageServiceSubGroup");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPageServiceSubGroup","AddNewTestPageServiceSubGroup",false);
		
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPgInsuranceCategory","AddNewTestPageInsuranceCategory");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgInsuranceCategory","AddNewTestPageInsuranceCategory",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageAllowRateIncreaseYes");
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageAllowRateDecreaseYes");
		
			
		
		executeStep.performAction(SeleniumActions.Enter, "AddNewTestPgSampleNeeded","AddNewTestPageSampleNeeded");
		verifications.verify(SeleniumVerifications.Entered, "AddNewTestPgSampleNeeded","AddNewTestPageSampleNeeded",false);
		
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPgSampleType","AddNewTestPageSampleType");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgSampleType","AddNewTestPageSampleType",false);
		
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageConductionRequiredYes");
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageResultEntryYes");
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageConductionFormatUseValues");
		
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPgConductingDocRequired","AddNewTestPageConductingDocRequired");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgConductingDocRequired","AddNewTestPageConductingDocRequired",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageAddResultButton");
		
		executeStep.performAction(SeleniumActions.Enter, "AddNewTestPgAddResultName","AddNewTestPageAddResultName");
		verifications.verify(SeleniumVerifications.Entered, "AddNewTestPgAddResultName","AddNewTestPageAddResultName",false);
		  
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPgAddResultObservationCodeType","AddNewTestPageAddResultObservationCodeType");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgAddResultObservationCodeType","AddNewTestPageAddResultObservationCodeType",false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddNewTestPgAddResultCode","AddNewTestPageAddResultCode");
		verifications.verify(SeleniumVerifications.Entered, "AddNewTestPgAddResultCode","AddNewTestPageAddResultCode",false);
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageResultAddButton");
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageSubmit");
	}
	
	
	public void AddNewTestForRadiology(){
		executeStep.performAction(SeleniumActions.Click, "","DiagnosticTestListPageAddNewTest");
		verifications.verify(SeleniumVerifications.Appears, "", "AddNewTestPage", false);
		
		executeStep.performAction(SeleniumActions.Enter, "AddNewTestPgTestName","AddNewTestPageTestName");
		verifications.verify(SeleniumVerifications.Entered, "AddNewTestPgTestName","AddNewTestPageTestName",false);
		  
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPgDeptName","AddNewTestPageDeptName");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgDeptName","AddNewTestPageDeptName",false);
		
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPgServiceGroup","AddNewTestPageServiceGroup");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgServiceGroup","AddNewTestPageServiceGroup",false);
		
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPageServiceSubGroup","AddNewTestPageServiceSubGroup");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPageServiceSubGroup","AddNewTestPageServiceSubGroup",false);
		
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPgInsuranceCategory","AddNewTestPageInsuranceCategory");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgInsuranceCategory","AddNewTestPageInsuranceCategory",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageAllowRateIncreaseYes");
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageAllowRateDecreaseYes");
					
		
		executeStep.performAction(SeleniumActions.Enter, "AddNewTestPgSampleNeeded","AddNewTestPageSampleNeeded");
		verifications.verify(SeleniumVerifications.Entered, "AddNewTestPgSampleNeeded","AddNewTestPageSampleNeeded",false);
		
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageConductionRequiredYes");
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageResultEntryYes");
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageConductionFormatUseTemplate");
		
		executeStep.performAction(SeleniumActions.Select, "AddNewTestPgConductingDocRequired","AddNewTestPageConductingDocRequired");
		verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgConductingDocRequired","AddNewTestPageConductingDocRequired",false);
		
		executeStep.performAction(SeleniumActions.Click, "AddNewTestPgSelectFormatTemplate","AddNewTestPageSelectFormatTemplate");
		//verifications.verify(SeleniumVerifications.Selected, "AddNewTestPgSelectFormatTemplate","AddNewTestPageSelectFormatTemplate",false);
		
			
		executeStep.performAction(SeleniumActions.Click, "","AddNewTestPageSubmit");
	}
	
	
	
	
	
	}
		
	
		
		