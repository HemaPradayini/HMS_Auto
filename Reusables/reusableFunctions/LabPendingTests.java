package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class LabPendingTests {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public LabPendingTests(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public LabPendingTests(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
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
	
}
