package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class OTRecord {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public OTRecord() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public OTRecord(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	
	public void otRecordEntry(){
		
		executeStep.performAction(SeleniumActions.Enter, "OTRecordPageFindings","OTRecordPageFindingsField");
		verifications.verify(SeleniumVerifications.Appears, "OTRecordPageFindings","OTRecordPageFindingsField",true);
		
		executeStep.performAction(SeleniumActions.Enter, "OTRecordPageComplications","OTRecordPageComplicationsField");
		verifications.verify(SeleniumVerifications.Appears, "OTRecordPageComplications","OTRecordPageComplicationsField",true);
		
		executeStep.performAction(SeleniumActions.Enter, "OTRecordPagePostOprFindings","OTRecordPagePostOprFindingsField");
		verifications.verify(SeleniumVerifications.Appears, "OTRecordPagePostOprFindings","OTRecordPagePostOprFindingsField",true);
				
		DiagnosisDetails diagDetails = new DiagnosisDetails(executeStep, verifications);
		diagDetails.addDianosisDetails("Diagnostic","OTRecordPage");   // Parameter added by Abhishek
	
		executeStep.performAction(SeleniumActions.Click, "","OTRecordPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","OTRecordPage",false);
		
	}
	
}
