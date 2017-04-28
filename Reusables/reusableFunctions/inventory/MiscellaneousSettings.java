package reusableFunctions.inventory;

import org.openqa.selenium.WebDriver;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class MiscellaneousSettings {
	
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public MiscellaneousSettings() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public MiscellaneousSettings(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void DoMiscellaneousSettings(){
		
		executeStep.performAction(SeleniumActions.Enter, "MiscellaneousSettingsCreditPeriod", "MiscellaneousSettingsCreditPeriodField");
	    verifications.verify(SeleniumVerifications.Entered, "MiscellaneousSettingsCreditPeriod","MiscellaneousSettingsCreditPeriodField",false);
	    
	    executeStep.performAction(SeleniumActions.Enter, "MiscellaneousSettingsIndentProcessNo", "MiscellaneousSettingsIndentProcessNoField");
	    verifications.verify(SeleniumVerifications.Entered, "MiscellaneousSettingsIndentProcessNo","MiscellaneousSettingsIndentProcessNoField",false);
	    
	    executeStep.performAction(SeleniumActions.Enter, "MiscellaneousSettingsDelivery", "MiscellaneousSettingsDeliveryField");
	    verifications.verify(SeleniumVerifications.Entered, "MiscellaneousSettingsDelivery","MiscellaneousSettingsDeliveryField",false);

	    executeStep.performAction(SeleniumActions.Enter, "POTermsAndConditions", "POTermsAndConditionsField");
	    verifications.verify(SeleniumVerifications.Entered, "POTermsAndConditions","POTermsAndConditionsField",false);
	    
	    executeStep.performAction(SeleniumActions.Click, "","AddItemCategorySaveButton");
	    verifications.verify(SeleniumVerifications.Appears, "","MiscellaneousSettingsHeader",true);
		
	
	}

}
