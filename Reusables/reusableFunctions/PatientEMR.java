package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientEMR {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PatientEMR(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientEMR(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void viewPatientEMR(){
		
		System.out.println("Inside PatientEMR viewPatientEMR ");

		//Vitals Reusable
		//executeStep.getDriver().findElement(By.xpath("//select[@id='sampleTypeForTests']")).click();


		
		System.out.println("Exit Patient EMR Screen");

	}
	
}
