package reusableFunctions.inventory;

import org.openqa.selenium.WebDriver;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Stores {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public Stores() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Stores(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void createstore(){
		
		System.out.println("Inside Stores");
		
		executeStep.performAction(SeleniumActions.Click, "","AddNewStoreLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddStorePage",true);

		executeStep.performAction(SeleniumActions.Enter, "Store","StoreNameTextBox");
		verifications.verify(SeleniumVerifications.Entered, "Store","StoreNameTextBox",false);
		
		executeStep.performAction(SeleniumActions.Select, "StoreCenter","StorecenterDropdown");  //add the centre type of store i,e select from dropdown
		verifications.verify(SeleniumVerifications.Selected, "StoreCenter","StorecenterDropdown",false);
		
		executeStep.performAction(SeleniumActions.Select, "StoreCounter","StorecounterDropdown");//select counter
		verifications.verify(SeleniumVerifications.Selected, "StoreCounter","StorecounterDropdown",false);
		
		
		executeStep.performAction(SeleniumActions.Select, "StoreType","StoreTypeDropdown");//select store type
		verifications.verify(SeleniumVerifications.Selected, "StoreType","StoreTypeDropdown",false);
		
		executeStep.performAction(SeleniumActions.Click, "","StoreSavebutton");
		//verifications.verify(SeleniumVerifications.Appears, "","DialogBlock",false);
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");	
		
	}
	
	

}
