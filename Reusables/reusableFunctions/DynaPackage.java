package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;


public class DynaPackage {
	WebDriver driver = null;
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	String AutomationID;
	String DataSet;

	public DynaPackage() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public DynaPackage(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void AddDynaPkg(){
		
		MRNoSearch mrnoSearch = new MRNoSearch(executeStep, verifications);		
		PatientBill patientBill = new PatientBill(executeStep, verifications);	
		patientBill.viewEditBills("BL");
		
		executeStep.performAction(SeleniumActions.Click, "", "CloseTour");
		System.out.println("After Close Tour");
		                                                  
		executeStep.performAction(SeleniumActions.Enter, "PatientBillPackageName", "PatientBillPakageNameField");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillPkgDropDown",false);
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientBillPkgDropDown");
		verifications.verify(SeleniumVerifications.Entered, "PatientBillPackageName","PatientBillPakageNameField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientBillPackageProcessButton");
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PatientBillSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);
	}
	

}
