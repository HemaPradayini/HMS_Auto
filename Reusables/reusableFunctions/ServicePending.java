package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class ServicePending {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public ServicePending(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public ServicePending(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
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
	public void conductPendingServices()
	{
		MRNoSearch MrnoSearch=new MRNoSearch(executeStep,verifications);
		MrnoSearch.searchMRNo("PendingServicesListPageMRNoField","PendingServicesListPageMRNoList","PendingServicesListPageSearchButton","PendingServicesListPage");
		
		
		executeStep.performAction(SeleniumActions.Click, "","PendingServicesListPageTableRowToClick");
		verifications.verify(SeleniumVerifications.Appears, "","PendingServicesListPagePendingServiceEdit",true);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingServicesListPagePendingServiceEdit");
		verifications.verify(SeleniumVerifications.Appears, "","ServiceConductionPage",true);
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");

		executeStep.performAction(SeleniumActions.Click, "","ServiceConductionPageCompletedCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","ServiceConductionPage",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ServiceConductionPageSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","ServiceConductionPage",true);
				
	
		System.out.println("Pending Services Conductions Saved");

	}
	
}
