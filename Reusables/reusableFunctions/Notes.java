package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Notes {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public Notes() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Notes(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void RecordDrNotes(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
		executeStep.performAction(SeleniumActions.Click, "", "IPCaseSheetDrNotesField");
		executeStep.performAction(SeleniumActions.Click, "", "DrNotesAddNewNoteButton");
			
		executeStep.performAction(SeleniumActions.Enter, "IPDrNotes","DrNotesNotesField");
		verifications.verify(SeleniumVerifications.Entered, "IPDrNotes","DrNotesNotesField",false);
	
		executeStep.performAction(SeleniumActions.Enter, "DrNotesDr","DrNotesDrField");
		verifications.verify(SeleniumVerifications.Entered, "DrNotesDr","DrNotesDrField",false);
		
		executeStep.performAction(SeleniumActions.Check, "","DrNotesBillCheckBox");
				
		executeStep.performAction(SeleniumActions.Select, "DrNotesConsultType","DrNotesConsultationTypeField");
		verifications.verify(SeleniumVerifications.Selected, "DrNotesConsultType","DrNotesConsultationTypeField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","DrNotesSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","DrNotesScreen",false);
	
		
		executeStep.performAction(SeleniumActions.Click, "","DrNotesIPCaseSheetLink");
		verifications.verify(SeleniumVerifications.Appears, "","IPCaseSheetScreen",false);
	
	}
    public void RecordNurseNotes(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
    	executeStep.performAction(SeleniumActions.Click, "", "IPCaseSheetNurseNotesField");
		executeStep.performAction(SeleniumActions.Click, "", "NurseNotesAddNewNoteButton");
			
		executeStep.performAction(SeleniumActions.Enter, "IPNurseNotes","NurseNotesNotesField");
		verifications.verify(SeleniumVerifications.Entered, "IPNurseNotes","NurseNotesNotesField",false);
	
		executeStep.performAction(SeleniumActions.Click, "","NurseNotesSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","NurseNotesScreen",false);
		
		executeStep.performAction(SeleniumActions.Click, "","DrNotesIPCaseSheetLink");
		verifications.verify(SeleniumVerifications.Appears, "","IPCaseSheetScreen",false);

	}
	}