package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Triage {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public Triage(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Triage(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void saveTriage(){
		
		System.out.println("Inside Triage saveTriage ");

		//Vitals Reusable
		//executeStep.getDriver().findElement(By.xpath("//select[@id='sampleTypeForTests']")).click();

		executeStep.performAction(SeleniumActions.Check, "","TriageCompletedCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","TriageCompletedCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TriageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","TriagePage",false);
			
		System.out.println("saveTriage Saved");

	}
	
}
