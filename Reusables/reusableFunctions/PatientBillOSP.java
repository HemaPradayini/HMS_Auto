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

public class PatientBillOSP {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PatientBillOSP(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientBillOSP(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void payAndClose(){
		
		System.out.println("Inside PatientBillScreen savePatientBill ");
		executeStep.performAction(SeleniumActions.Select, "BillPaymentType","PatientBillPaymentType");
		verifications.verify(SeleniumVerifications.Selected, "BillPaymentType","PatientBillPaymentType",false);

		executeStep.performAction(SeleniumActions.Click, "","PatientBillPayCloseBtn");
		verifications.verify(SeleniumVerifications.Opens, "","PatientBillReceipt",true);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","PatientBillReceipt",false);

		System.out.println("OSP Patient Bill Screen Saved");

	}
	
	public void payAndCloseMultipleBills(){
		
		System.out.println("Inside PatientBillScreen payAndCloseMultipleBills ");
/*		String xPathPre="//table[@id='resultTable']//tr[";
		String xPathPost = "]/td[7]";
		String elementText ="";		
		
		int noOfBillsToClose = getNoOfBills();
		int j=2;*/
/*		executeStep.performAction(SeleniumActions.Click, "","SortingColumnLinkOnBillList");
		verifications.verify(SeleniumVerifications.Appears, "","PatientBillScreen",false);*/
		
/*		executeStep.performAction(SeleniumActions.Select, "OSPInsBillStatus","PatientBillStatus");
		verifications.verify(SeleniumVerifications.Selected, "OSPInsBillStatus","PatientBillStatus",true);*/
		
		executeStep.performAction(SeleniumActions.Click, "","PatientBillPayNSave");
		verifications.verify(SeleniumVerifications.Opens, "","PatientBillReceipt",true);
		
		executeStep.performAction(SeleniumActions.CloseTab, "","PatientBillReceipt");
		verifications.verify(SeleniumVerifications.Closes, "","PatientBillReceipt",false);
		System.out.println("OSP Patient Bill Settlement Saved");		
	}
	
	public int getNoOfBills(){
		System.out.println("Inside PatientBillScreen getNoOfBills ");
		
		int rowCount=this.executeStep.getDriver().findElements(By.xpath("//table[@id='resultTable']/tbody/tr")).size();
		System.out.println("The no of Rows in the Bills Table is :: " + rowCount);
		return rowCount;
	}
	/*public void iterateThruTable(){
		String elementText;
		String xPathPre="//table[@id='resultTable']//tr[";
		String xPathPost = "]/td[7]";		
		String elementXPath = "";		
		elementXPath=xPathPre + <<iteratorCount>> +xPathPost; // Replace the iteratorCount with the for loop's iterator;
		int rowCount = getNoOfBills();
		for(int i=0; i<rowCount-1;i++){//rowCount will include the header. Hence we need to iterate 1 less time that the rowCount
			elementXPath = xPathPre + (i+1) +xPathPost;
			WebElement we = this.executeStep.getDriver().findElement(By.xpath(elementXPath));
			elementText= we.getText();
			if elementText.equalsIgnoreCase(<Medicine or Investigation or whatever>){
				EnvironmentSetup.intGlobalLineItemCount = i;
				executeStep.performAction(SeleniumActions.Click, "","XXXXPATHXXXX");//XXXXPATHXXXX should have the xpath which can take in the rowCount such as //table[@id='resultTable']//tr[<count>]//				
			}
		}
	}*/
	
}
