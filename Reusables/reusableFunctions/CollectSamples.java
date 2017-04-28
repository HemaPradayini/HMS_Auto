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

public class CollectSamples {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public CollectSamples(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public CollectSamples(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void collectSamplesAndSave(){
		
		System.out.println("Inside CollectSamplesPage collectSamplesAndSave ");

		//Vitals Reusable
		//executeStep.getDriver().findElement(By.xpath("//select[@id='sampleTypeForTests']")).click();
		
		//PatientBillOSP patientBill = new PatientBillOSP (executeStep, verifications);	
		int rowCount = this.executeStep.getDriver().findElements(By.xpath("//div[@class='resultList']//table[@id='sampleList']//tr//input[@name='sampleFor']")).size();
		//int noOfOpenBills = patientBill.getNoOfBills();
		int noOfOpenBills = rowCount;
		System.out.println("Row Count in Collect sample is :: " + noOfOpenBills);
		for(int i=1; i<=noOfOpenBills; i++){
			EnvironmentSetup.intGlobalLineItemCount = i+1;
			// Added below by Alamelu
			Boolean we = false;
			int j=i+1;
			try{
				we = this.executeStep.getDriver().findElement(By.xpath("//div[@class='resultList']//table[@id='sampleList']//tr["+j+"]//input[@name='sampleFor']")).isEnabled();
			}catch (Exception e){
			System.out.println("Webelement for given xpath not found" + e.toString());
			}
			if (we==true){	
				executeStep.performAction(SeleniumActions.Check, "","CollectSamplesTableRow");
				verifications.verify(SeleniumVerifications.Checked, "","CollectSamplesTableRow",false);
				
			}	
			
/*		
			executeStep.performAction(SeleniumActions.Check, "","CollectSamplesForTest2");
			verifications.verify(SeleniumVerifications.Checked, "","CollectSamplesForTest2",false);*/
		}
		EnvironmentSetup.intGlobalLineItemCount = 0;
		//}//
		
		//executeStep.performAction(SeleniumActions.Check, "","CollectSamplesForTest2");
		//verifications.verify(SeleniumVerifications.Checked, "","CollectSamplesForTest2",false);
		
		executeStep.performAction(SeleniumActions.Check, "","CollectSamplePageCollectAll");
		verifications.verify(SeleniumVerifications.Checked, "","CollectSamplePageCollectAll",false);//changed to false by Tejaswini
		
		executeStep.performAction(SeleniumActions.Check, "","CollectSamplePageGenerateAll");
		verifications.verify(SeleniumVerifications.Checked, "","CollectSamplePageGenerateAll",true);//changed to false by Tejaswini
		
		executeStep.performAction(SeleniumActions.Click, "","CollectSamplePageSave");
		verifications.verify(SeleniumVerifications.Appears, "","CollectSamplePage",false);//changed to false by Tejaswini
		
		System.out.println("Collect Sample Screen Saved");

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
		
		executeStep.performAction(SeleniumActions.Enter, "MRID","PendingSampleAssertionsSearchMRID");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSampleAssertionsSearchMRIDContainer",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSampleAssertionsSearchMRIDContainer");
		verifications.verify(SeleniumVerifications.Entered, "MRID","PendingSampleAssertionsSearchMRID",false);
		
		executeStep.performAction(SeleniumActions.Click, "","PendingSampleAssertionsSearchButton");
		verifications.verify(SeleniumVerifications.Appears, "","PendingSampleAssertionsTable",false);
		
	}
	
	public void markTransferredLabSamples(){
		
		
		executeStep.performAction(SeleniumActions.Check, "","LaboratoryTransferSamplesManualCheckBox");
		verifications.verify(SeleniumVerifications.Checked, "","LaboratoryTransferSamplesManualCheckBox",false);
		
		executeStep.performAction(SeleniumActions.Click, "","MarkTransferredButton");
		verifications.verify(SeleniumVerifications.Appears, "","LaboratoryTransferSamplesManualPage",false);
		
		
		
	}
	
}
