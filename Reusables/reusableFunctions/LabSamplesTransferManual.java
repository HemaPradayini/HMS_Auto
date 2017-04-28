package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class LabSamplesTransferManual {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public LabSamplesTransferManual(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public LabSamplesTransferManual(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void transferLabSamples(){

		System.out.println("Inside transferLabSamples transferLabSamples ");
		searchSample();
		executeStep.performAction(SeleniumActions.Check, "","LabTransferSamplesManualCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","LabTransferSamplesManualCheckBox",false);
				
		executeStep.performAction(SeleniumActions.Click, "","LabTransferSamplesManualMarkAndPrintBtn");
		verifications.verify(SeleniumVerifications.Appears, "","LabTransferSamplesManualPage",false);
		
		verifications.verify(SeleniumVerifications.Opens, "","SampleWorkSheet",false);
		executeStep.performAction(SeleniumActions.CloseTab, "","SampleWorkSheet");
		verifications.verify(SeleniumVerifications.Closes, "","SampleWorkSheet",false);

	}	

	public void assertPendingSamples(){
		searchSample();
		
		executeStep.performAction(SeleniumActions.Enter, "ItemQuantity","PendingSampleAssertionsQuantityReceived");
		verifications.verify(SeleniumVerifications.Entered, "ItemQuantity","PendingSampleAssertionsQuantityReceived",false);
		
		executeStep.performAction(SeleniumActions.Check, "","PendingSampleAssertionsCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","PendingSampleAssertionsCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSampleAssertionsAssertButton");
		verifications.verify(SeleniumVerifications.Appears, "","LaboratoryPendingSampleAssertionsPage",false);
		
		System.out.println("LaboratoryPendingSampleAssertionsPage Asserted");
		
	}
	
	public void searchSample(){
		
		executeStep.performAction(SeleniumActions.Enter, "MRID","LabTransferSamplesManualMRNO");
		verifications.verify(SeleniumVerifications.Appears, "","LabTransferSamplesManualMRNoLi",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabTransferSamplesManualMRNoLi");
		verifications.verify(SeleniumVerifications.Appears, "","LabTransferSamplesManualPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","LabTransferSamplesManualSearchBtn");
		verifications.verify(SeleniumVerifications.Appears, "","LabTransferSamplesManualResultsTable",false);
		
	}
	
	public void searchSampleBySampleNo(){
		
		executeStep.performAction(SeleniumActions.Enter, "MRID","LabPendingTestSampleNo");
		verifications.verify(SeleniumVerifications.Appears, "","LabTransferSamplesManualPage",false);
		
		/*executeStep.performAction(SeleniumActions.Click, "","LabTransferSamplesManualMRNoLi");
		verifications.verify(SeleniumVerifications.Appears, "","LabTransferSamplesManualPage",false);*/
		
		executeStep.performAction(SeleniumActions.Click, "","LabTransferSamplesManualSearchBtn");
		verifications.verify(SeleniumVerifications.Appears, "","LabTransferSamplesManualResultsTable",false);
		
	}
	
	public void receiveSamples(){
		MRNoSearch searchMRNo = new MRNoSearch(executeStep, verifications);
		searchMRNo.searchMRNo("LabTransferSamplesManualMRNO", "LabTransferSamplesManualMRNoLi", "LabTransferSamplesManualSearchBtn", "LabTransferSamplesManualResultsTable");
		verifications.verify(SeleniumVerifications.Appears, "","ReceiveCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Check, "","ReceiveCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","ReceiveCheckBox",false);

		executeStep.performAction(SeleniumActions.Click, "","MarkReceivedBtn");
		verifications.verify(SeleniumVerifications.Appears, "","LaboratoryReceiveSamplesPage",false);
			
	}
	
}

	
