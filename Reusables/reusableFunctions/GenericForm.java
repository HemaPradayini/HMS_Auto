package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class GenericForm {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	
	public GenericForm(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public GenericForm(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void addGenericForm(){
		
		System.out.println("Inside GenericForm addGenericForm ");

		executeStep.performAction(SeleniumActions.Enter, "MRID","OrdersScreenPatientIDField");
		verifications.verify(SeleniumVerifications.Appears, "","OrdersPageMRIDListBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","OrdersPageMRIDListBox");
		verifications.verify(SeleniumVerifications.Appears, "","OrdersPage",true);

		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenFind");
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		verifications.verify(SeleniumVerifications.Appears, "","OrdersScreenBillNumber",true);
		executeStep.performAction(SeleniumActions.Click, "","OrdersScreenSave");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		System.out.println("GenericForm addGenericForm Saved");
		

		

	}
	
}
