package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class SearchBillList {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public SearchBillList(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public SearchBillList(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void searchBills(){
		
		System.out.println("Inside searchBills searchBills ");


		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");

		executeStep.performAction(SeleniumActions.Click, "","BillListPatientBills");
		verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
	
		}
		
		executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		executeStep.performAction(SeleniumActions.ClickIfPresent,"","CloseTour");

		System.out.println("Bill Screen Opened");
		

	}
	
	public void searchBillsBillNo(){
		
		System.out.println("Inside CollectSamplesPage collectSamplesAndSave ");


		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		mrnoSearch.searchMRNo("BillListMRNoField", "BillListMRNoList", "BillListSearchButton", "BillListPatientBills");
		
		executeStep.performAction(SeleniumActions.Click, "","BillListPatientBills");
		verifications.verify(SeleniumVerifications.Appears, "","BillListViewEditMenu",false);
		
		executeStep.performAction(SeleniumActions.Click, "","BillListViewEditMenu");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
		System.out.println("Collect Sample Screen Saved");

	}
}
