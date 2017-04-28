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

public class Codification {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public Codification(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Codification(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void saveCodes(boolean codificationComplete){
		
		System.out.println("Inside CodificationPage saveCodes ");


		saveEncounterCodes(codificationComplete);
		savePrimaryDiagnosisCode();
		storeCodes();
		
		System.out.println("Edit Codes Screen Saved");

	}
	public void savePrimaryDiagnosisCode(){
		
		WebElement we = null;
		try{
				we = this.executeStep.getDriver().findElement(By.xpath("//table[@id='diagnosiscodes']//tr[1]/td[1][contains(text(),'Primary Diagnosis(Type):')]"));
			}catch (Exception e){
			System.out.println("Webelement for given xpath not found" + e.toString());
			}
			if (we==null){	
				executeStep.performAction(SeleniumActions.Click, "","EditCodesCodificationAddDiagLink");
				verifications.verify(SeleniumVerifications.Appears, "","EditCodesCodificationAddDiagPage",false);
				
				executeStep.performAction(SeleniumActions.Click, "","AddDiagnosisCodePageAddButton");
				verifications.verify(SeleniumVerifications.Appears, "","EditCodesCodificationAddDiagPage",false);
				
				executeStep.performAction(SeleniumActions.Enter, "CodificationDiagnosisCode","AddDiagnosisCodeCodeField");
				verifications.verify(SeleniumVerifications.Appears, "CodificationDiagnosisCode","AddDiagnosisCodeCodeList",false);
				
				executeStep.performAction(SeleniumActions.Click, "CodificationDiagnosisCode","AddDiagnosisCodeCodeList");
				verifications.verify(SeleniumVerifications.Appears, "","EditCodesCodificationAddDiagPage",false);
				
				executeStep.performAction(SeleniumActions.Click, "","AddDiagnosisCodeOkButton");
				verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);
			
				
			}
	}
	public void treatmentCode(){
		
		int rowCount=this.executeStep.getDriver().findElements(By.xpath("//table[@id='treatment']/tbody/tr")).size();
		System.out.println("The no of Rows in the treatment Table is :: " + rowCount);
		
		
		
		for (int i = 1; i<rowCount; i++){
 	
		
			int editableRow = i+1;
			EnvironmentSetup.intGlobalLineItemCount= editableRow;
			String xpath ="//table[@id='treatment']/tbody/tr["+ editableRow +"]/td[9]";
			String startCode = this.executeStep.getDriver().findElement(By.xpath(xpath)).getText();
 			if(startCode== null || startCode.equals("")){
 				
 				executeStep.performAction(SeleniumActions.Click, "","EditTreatmentButton");
 				verifications.verify(SeleniumVerifications.Appears, "","EditTreatmentCodePage",false);	
 				
 				
 				executeStep.performAction(SeleniumActions.Select, "TreatmentCodeType","TreatmentCodeTypeSelect");
 				verifications.verify(SeleniumVerifications.Selected, "TreatmentCodeType","TreatmentCodeTypeSelect",false);
 				
 				executeStep.performAction(SeleniumActions.Enter, "TreatmentCode","TreatmentCodeField");
 				verifications.verify(SeleniumVerifications.Appears, "TreatmentCode","TreatmentCodeList",false);
 				
 				executeStep.performAction(SeleniumActions.Click, "TreatmentCode","TreatmentCodeList");
 				verifications.verify(SeleniumVerifications.Entered, "TreatmentCode","TreatmentCodeField",false);
 				
 				executeStep.performAction(SeleniumActions.Click, "","EditTreatmentCodeSaveButton");
 				verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);
 			}
 	
		}
		EnvironmentSetup.intGlobalLineItemCount=0;
		
		
	}
		
	
	public void saveEncounterCodes(boolean codificationComplete){
		
		System.out.println("Inside CodificationPage saveEnCounterCodes ");

		executeStep.performAction(SeleniumActions.Enter, "MRID","EditCodesMRNoField");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesMRNoList",false);					

		executeStep.performAction(SeleniumActions.Click, "","EditCodesMRNoList");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);	
		
		executeStep.performAction(SeleniumActions.Click, "","EditCodesFindButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);	
		
		executeStep.performAction(SeleniumActions.Click, "","EditEncounterCodesLink");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesDiv",false);
	//modified by Alamelu	
		String startCode = this.executeStep.getDriver().findElement(By.xpath("//input[@id='encStartCode']")).getAttribute("value");
		if(startCode== null || startCode == ""){
			executeStep.performAction(SeleniumActions.Enter, "EncounterStartCode","EditCodesEncounterStart");
			verifications.verify(SeleniumVerifications.Appears, "","EditCodesEncounterStartDropdown",false);
			executeStep.performAction(SeleniumActions.Click, "","EditCodesEncounterStartDropdown");
			verifications.verify(SeleniumVerifications.Entered, "EncounterStartCode","EditCodesEncounterStart",false);
			
		}
		
		executeStep.performAction(SeleniumActions.Select, "EncounterTransferFrom","EditCodesEncounterTransferFrom");
		verifications.verify(SeleniumVerifications.Selected, "EncounterTransferFrom","EditCodesEncounterTransferFrom",false);
		
		String endCode = this.executeStep.getDriver().findElement(By.xpath("//input[@id='encEndCode']")).getAttribute("value");
		if(endCode== null || endCode ==""){
			executeStep.performAction(SeleniumActions.Enter, "EncounterEndCode","EditCodesEncounterEnd");
			verifications.verify(SeleniumVerifications.Appears, "","EditCodesEncounterEndDropdown",false);
			executeStep.performAction(SeleniumActions.Click, "","EditCodesEncounterEndDropdown");
			verifications.verify(SeleniumVerifications.Entered, "EncounterEndCode","EditCodesEncounterEnd",false);
		}
		
		
		executeStep.performAction(SeleniumActions.Select, "EncounterTransferTo","EditCodesEncounterTransferTo");
		verifications.verify(SeleniumVerifications.Selected, "EncounterTransferTo","EditCodesEncounterTransferTo",false);
			
		executeStep.performAction(SeleniumActions.Click, "","EditCodesEncounterCodesSave");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);
		
		if (codificationComplete){
			executeStep.performAction(SeleniumActions.Select, "CodificationStatus","EditCodesCodificationStatus");
			verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);
		} 
		

	}
	
	public void storeCodes(){
		
		executeStep.performAction(SeleniumActions.Click, "","EditCodesSave");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);
	}
	
	

public void EditCodesFinalizeAllCheckBox() //added by Reena for DHA Claim
{
		
		executeStep.performAction(SeleniumActions.Click, "","EditCodesFinalizeAllCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);
	}
	
	
	public void saveCodesForOSP(boolean codificationComplete){
		saveEncounterCodes(codificationComplete);
		EditCodesFinalizeAllCheckBox();
		storeCodes();
		System.out.println("Edit Codes Screen Saved");
	}
	public void editEMCode(){
		
		executeStep.performAction(SeleniumActions.Enter, "MRID","EditCodesMRNoField");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesMRNoList",false);					

		executeStep.performAction(SeleniumActions.Click, "","EditCodesMRNoList");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);	
		
		executeStep.performAction(SeleniumActions.Click, "","EditCodesFindButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditDoctorConsultationCodeLink");
		verifications.verify(SeleniumVerifications.Appears, "","EditDoctorConsultationCodeDiv",false);					

		executeStep.performAction(SeleniumActions.Clear, "","EditDoctorConsultationCodeContainer");
		executeStep.performAction(SeleniumActions.Enter, "EditE&Mcode","EditDoctorConsultationCodeContainer");
		verifications.verify(SeleniumVerifications.Appears, "EditE&Mcode","EditDoctorConsultationCodeList",false);	
		
		executeStep.performAction(SeleniumActions.Click, "EditE&Mcode","EditDoctorConsultationCodeList");
		verifications.verify(SeleniumVerifications.Entered, "EditE&Mcode","EditDoctorConsultationCodeContainer",false);
		
		executeStep.performAction(SeleniumActions.Select, "CodificationConsultationType","EditDoctorConsultationType");
		verifications.verify(SeleniumVerifications.Selected, "CodificationConsultationType","EditDoctorConsultationType",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditDoctorConsultationCodeSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditCodesScreen",false);					

		
		
		
	}
}
