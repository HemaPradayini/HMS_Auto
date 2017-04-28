package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientWard {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public PatientWard() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientWard(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	public int getNoOfActivities(){
		//System.out.println("Inside PatientBillScreen getNoOfBills ");
		
		int rowCount=this.executeStep.getDriver().findElements(By.xpath("//table[@id='activitiesTable']/tbody/tr")).size();
		System.out.println("The no of Rows in the  Table is :: " + rowCount);
		return rowCount;
	}

	public void OrderItems(){
		
		
		String elementText;
		String xPathPre="//table[@id='activitiesTable']//tr[";
		String xPathPost = "]/td[2]";
		String elementXPath = "";	
		
		int rowCount = getNoOfActivities();
		for(int i=1; i<rowCount-1; i++){
			EnvironmentSetup.intGlobalLineItemCount = i+1;
			elementXPath = xPathPre + (i+1) +xPathPost;
			
		    System.out.println("The XPath :: " + elementXPath);
			WebElement we = this.executeStep.getDriver().findElement(By.xpath(elementXPath));
			elementText= we.getText();
			
			System.out.println("The Type :: " + elementText);
			
		
			if(elementText.equalsIgnoreCase("Inv.")){
				
				System.out.println("Inside Inv");
															
				executeStep.performAction(SeleniumActions.Click, "","PatientWardActivitiesEditItemIcon");
				verifications.verify(SeleniumVerifications.Appears,"","EditActivityScreen",false);
				
				executeStep.performAction(SeleniumActions.Check,"","EditActivityOrderCheckBox");
				verifications.verify(SeleniumVerifications.Checked, "","EditActivityOrderCheckBox",false);

				executeStep.performAction(SeleniumActions.Click, "","EditActivityOkButton");
				verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",false);
				
				//executeStep.performAction(SeleniumActions.Click, "","EditActivityCancelButton");
				//verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",true);
			}
			if(elementText.equalsIgnoreCase("Service")){
				
				System.out.println("Inside Service If");
															
				executeStep.performAction(SeleniumActions.Click, "","PatientWardActivitiesEditItemIcon");
				verifications.verify(SeleniumVerifications.Appears,"","EditActivityScreen",true);
				
				executeStep.performAction(SeleniumActions.Check,"","EditActivityOrderCheckBox");
				verifications.verify(SeleniumVerifications.Checked, "","EditActivityOrderCheckBox",true);
				
				
				executeStep.performAction(SeleniumActions.Click, "","EditActivityOkButton");
				verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",true);

				//executeStep.performAction(SeleniumActions.Click, "","EditActivityCancelButton");
				//verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",true);
				
			}
			EnvironmentSetup.intGlobalLineItemCount = 0;
			
		}
		
	
	}
	public void CloseItems(){
		String elementText;
		String xPathPre="//table[@id='activitiesTable']//tr[";
		String xPathPost = "]/td[2]";
		String elementXPath = "";	
		
		int rowCount = getNoOfActivities();
		for(int i=1; i<rowCount-1; i++){
			elementXPath = xPathPre + (i+1) +xPathPost;
			EnvironmentSetup.intGlobalLineItemCount = i+1;
			
		    System.out.println("The XPath :: " + elementXPath);
			WebElement we = this.executeStep.getDriver().findElement(By.xpath(elementXPath));
			elementText= we.getText();
			
			System.out.println("The Type :: " + elementText);
			
			executeStep.performAction(SeleniumActions.Click, "","PatientWardActivitiesEditItemIcon");
			verifications.verify(SeleniumVerifications.Appears,"","EditActivityScreen",true);
			
			executeStep.performAction(SeleniumActions.Select, "ActivityStatus","EditActivityStatus");
			verifications.verify(SeleniumVerifications.Selected,"","EditActivityStatus",true);

			if(elementText.equalsIgnoreCase("Medicine")){
				
				System.out.println("Inside If");
															
				
				executeStep.performAction(SeleniumActions.Enter, "MedicationBatch","EditActivityMedicationBatchField");
				verifications.verify(SeleniumVerifications.Entered,"","EditActivityMedicationBatchField",true);
				
				executeStep.performAction(SeleniumActions.Enter, "ExpiryMonth","EditActivityExpiryMonthField");
				verifications.verify(SeleniumVerifications.Entered,"","EditActivityExpiryMonthField",true);
				
				executeStep.performAction(SeleniumActions.Enter, "ExpiryYear","EditActivityExpiryYearField");
				verifications.verify(SeleniumVerifications.Entered,"","EditActivityExpiryYearField",true);
				
			}
			
			executeStep.performAction(SeleniumActions.Click, "","EditActivityOkButton");
			verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",true);
		
			executeStep.performAction(SeleniumActions.Click, "","EditActivityCancelButton");
			verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",true);
			
			
			EnvironmentSetup.intGlobalLineItemCount = 0;
		}
		
	}
    
	public void SavePatientWard(){
		executeStep.performAction(SeleniumActions.Click, "","PatientWardActivitiesSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","PatientWardActivitiesScreen",false);
		
		//executeStep.performAction(SeleniumActions.Click, "","PatientIndentLink");
		//verifications.verify(SeleniumVerifications.Appears, "","PatientIssueScreen",false);
	}
	
	}
