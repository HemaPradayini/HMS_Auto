package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class OpenGenericForm {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public OpenGenericForm(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public OpenGenericForm(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void openGenericForm(){
		
		System.out.println("Inside OpenGenericForm openGenericForm ");

		executeStep.performAction(SeleniumActions.Click, "","TriageGenericFormLink");
		//verifications.verify(SeleniumVerifications.Opens, "","GenericFormPage",false); // verify whether new window opens or opens in same window
		verifications.verify(SeleniumVerifications.Appears, "","GenericFormPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","GenericFormLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddGenericFormPage",false);
		
		//below is changed to true since this field is not available in cen and not mandatory - Alamelu
		executeStep.performAction(SeleniumActions.Enter, "GenericFormTestField1","AddGenericScreenTest1Field");
		verifications.verify(SeleniumVerifications.Entered, "GenericFormTestField1","AddGenericScreenTest1Field",true);
		
		executeStep.performAction(SeleniumActions.Click, "","AddGenericScreenSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","AddGenericFormPage",false);
		
	}
	public void openGenericFormFromIP(){
		
		System.out.println("Inside OpenGenericForm openGenericForm ");

		executeStep.performAction(SeleniumActions.Enter, "MRID","GenericFormMRNoField");
		verifications.verify(SeleniumVerifications.Appears, "","GenericFormMRNoList",false);
		
		executeStep.performAction(SeleniumActions.Click, "","GenericFormMRNoList");
		verifications.verify(SeleniumVerifications.Appears, "MRID","GenericFormMRNoField",true);

		executeStep.performAction(SeleniumActions.Click, "","GenericFormFindButton");
		verifications.verify(SeleniumVerifications.Appears, "","GenericFormPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","GenericFormLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddGenericFormPage",false);
		
		executeStep.performAction(SeleniumActions.Enter, "GenericFormTestField1","AddGenericScreenTest1Field");
		verifications.verify(SeleniumVerifications.Entered, "GenericFormTestField1","AddGenericScreenTest1Field",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddGenericScreenSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","AddGenericFormPage",false);
		
	}
	
}
