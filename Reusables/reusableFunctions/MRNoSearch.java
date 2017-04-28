package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class MRNoSearch {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public MRNoSearch(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public MRNoSearch(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void searchMRNo(String searchFieldName,String divToAppear, String linkToClick, String resultScreen){
		
		System.out.println("Inside MRNoSearch searchMRNo ");

		executeStep.performAction(SeleniumActions.Enter, "MRID",searchFieldName);
		verifications.verify(SeleniumVerifications.Appears, "MRID",divToAppear,false);
		
		executeStep.performAction(SeleniumActions.Click, "MRID",divToAppear);
		verifications.verify(SeleniumVerifications.Entered, "MRID",searchFieldName,true);	// Don't ever change this to false. Bcoz MRID doesn't get entered. Its the VisitID

		executeStep.performAction(SeleniumActions.Click, "",linkToClick);
		verifications.verify(SeleniumVerifications.Appears, "",resultScreen,false);	

		System.out.println("searchMRNo Saved");

	}
	
	public void searchWithPatientName(String landingPage, String patientName,String searchButton  ){
		
		System.out.println("Search patient by name");
		
		executeStep.performAction(SeleniumActions.Click, "","IncomingSamplePendingBillMoreOptions");
		verifications.verify(SeleniumVerifications.Appears, "",landingPage,true);	
		
		executeStep.performAction(SeleniumActions.Enter, "PatientFirstName",patientName);
		verifications.verify(SeleniumVerifications.Entered, "PatientFirstName",patientName,true);
		
		executeStep.performAction(SeleniumActions.Click, "",searchButton);
		verifications.verify(SeleniumVerifications.Appears, "",landingPage,true);	
		
		
	}
	
}
