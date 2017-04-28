package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;

import genericFunctions.CommonUtilities;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class SiteNavigation {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;
	
	public SiteNavigation(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public SiteNavigation(String AutomationID, String DataSet){
		this.AutomationID = AutomationID;
		this.DataSet = DataSet;
	}
	
	public void navigateTo(WebDriver driver, String linkName, String finalScreenName){
		System.out.println("Inside NavigateTo Function");
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		// Delay is introduced to handle the sync problem happening because of two 
		//consecutive close tours and no verification possible
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		verifications.verify(SeleniumVerifications.Appears, "", "NavigationMenu", true);
		executeStep.performAction(SeleniumActions.Click, "", "NavigationMenu");	
		executeStep.performAction(SeleniumActions.Click, "", linkName);//Added By Tejaswini
		verifications.verify(SeleniumVerifications.Appears, "", finalScreenName, false); //Added By Tejaswini
	}
	
	//added by Reena
	public void navigateToReport(WebDriver driver, String linkName, String finalScreenName){ 
		System.out.println("Inside NavigateTo Function");
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		verifications.verify(SeleniumVerifications.Appears, "", "NavigationMenu", true);
		executeStep.performAction(SeleniumActions.Click, "", "NavigationMenu");	
		executeStep.performAction(SeleniumActions.Click, "", "ReportLink");	
		executeStep.performAction(SeleniumActions.Click, "", linkName);
		verifications.verify(SeleniumVerifications.Appears, "", finalScreenName, false); 
	}
		
	public void navigateToSettings(WebDriver driver, String linkName, String finalScreenName){
		System.out.println("Inside NavigateTo Function");
		executeStep = new KeywordSelectionLibrary(driver, AutomationID, DataSet);
		verifications = new VerificationFunctions(driver, AutomationID, DataSet);
		
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");
		verifications.verify(SeleniumVerifications.Appears, "", "NavigationMenu", true);
		executeStep.performAction(SeleniumActions.Click, "", "NavigationMenu");	
		executeStep.performAction(SeleniumActions.Click, "", "SettingsLink");	
		executeStep.performAction(SeleniumActions.Click, "", linkName);//Added By Tejaswini
		verifications.verify(SeleniumVerifications.Appears, "", finalScreenName, false); //Added By Tejaswini
	}
	
}
