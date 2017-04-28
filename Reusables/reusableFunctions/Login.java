package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Login {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public Login(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Login(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void login(){
		
		//executeStep.setLineItemId("HospitalName");
		System.out.println("Inside Login login() ");
//		EnvironmentSetup.UseLineItem = true;
//		EnvironmentSetup.LineItemIdForExec = "HospitalName";
//		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
//		DbFunctions dbFunction = new DbFunctions();
//		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
//		System.out.println("Row Count is :: " + rowCount);
		executeStep.performAction(SeleniumActions.Enter, "Hospital_Est", "LoginScreenHospital_Est");
		verifications.verify(SeleniumVerifications.Entered, "Hospital_Est", "LoginScreenHospital_Est", false);
		EnvironmentSetup.UseLineItem = false;
		executeStep.performAction(SeleniumActions.Enter, "Username", "LoginScreenUserName");
		verifications.verify(SeleniumVerifications.Entered, "Username", "LoginScreenUserName", false);
		
		executeStep.performAction(SeleniumActions.Enter, "Password", "LoginScreenPassword");
		verifications.verify(SeleniumVerifications.Entered, "Password", "LoginScreenPassword", false);
		System.out.println("Before Login.Click");

		executeStep.performAction(SeleniumActions.Click, "", "Login");
		System.out.println("After Login.Click");

	}
	
	public void logOut(){
		
		executeStep.performAction(SeleniumActions.Click, "", "UserSpan");
		verifications.verify(SeleniumVerifications.Appears, "", "LogOutMenu", false);
		
		executeStep.performAction(SeleniumActions.Click, "", "LogOutMenu");
		verifications.verify(SeleniumVerifications.Appears, "", "LoginScreenHospital_Est", false);
	}
}
