package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class InsuranceDetails {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public InsuranceDetails(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public InsuranceDetails(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void addInsuranceDetails(String screenName){
		
	//	System.out.println("Inside CollectSamplesPage collectSamplesAndSave ");
		
		executeStep.performAction(SeleniumActions.Click, "","OSPScreenPrimaySponsor");
		verifications.verify(SeleniumVerifications.Appears, "","OSPScreenPrimaySponsor",true);
		
		executeStep.performAction(SeleniumActions.Enter, "DiagnosisCode","OSPScreenPrimarySponsorName");
		verifications.verify(SeleniumVerifications.Appears, "","DiagDetailsCodeDropdown",true);
		EnvironmentSetup.UseLineItem = false;
		
		/*executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenAddDiagnosisButton");
		verifications.verify(SeleniumVerifications.Appears, "","DiagnosisDetailsScreen",true);
*/
		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.LineItemIdForExec = "Diagnosis";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		DbFunctions dbFunction = new DbFunctions();
		int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=0; i<rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "DiagnosisCode","DiagDetailsCodeField");
			verifications.verify(SeleniumVerifications.Appears, "","DiagDetailsCodeDropdown",true);
			EnvironmentSetup.UseLineItem = false;
			
			executeStep.performAction(SeleniumActions.Click, "","DiagDetailsCodeDropdown");
			verifications.verify(SeleniumVerifications.Appears, "DiagnosisCode","DiagDetailsCodeField",true);
			
			executeStep.performAction(SeleniumActions.Click, "","DiagDetailsAddButton");
			EnvironmentSetup.lineItemCount ++;
	//		verifications.verify(SeleniumVerifications.Entered, "DiagnosisCode","ConsultMgtScreenDiagRow",true);
		}
		EnvironmentSetup.lineItemCount =0;
		EnvironmentSetup.UseLineItem = false;
		
		executeStep.performAction(SeleniumActions.Click, "","DiagDetailsCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "DiagnosisCode",screenName,true);

		
		System.out.println("Collect Sample Screen Saved");

	}
	
}
