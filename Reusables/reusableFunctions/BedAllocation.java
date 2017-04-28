package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class BedAllocation {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public BedAllocation() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public BedAllocation(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void AssignBed(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
				
		executeStep.performAction(SeleniumActions.Select, "ShiftBedType", "ShiftBedTypeField");
		verifications.verify(SeleniumVerifications.Selected, "ShiftBedType","ShiftBedTypeField",false);
		
		executeStep.performAction(SeleniumActions.Select, "ShiftWard", "ShiftBedWardField");
		verifications.verify(SeleniumVerifications.Selected, "ShiftWard","ShiftBedWardField",false);
		
		executeStep.performAction(SeleniumActions.Select, "ShiftBedName", "ShiftBedBedNameField");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Selected, "ShiftBedName","ShiftBedBedNameField",false);
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		
		executeStep.performAction(SeleniumActions.Select, "ShiftChargedBedType", "ShiftChargedBedTypeField");
		verifications.verify(SeleniumVerifications.Selected, "ShiftChargedBedType","ShiftChargedBedTypeField",false);
		
		DbFunctions dbFunctions = new DbFunctions();
		dbFunctions.storeDate(this.executeStep.getDataSet(), "ShiftStartDate","N",1);
		executeStep.performAction(SeleniumActions.Enter, "ShiftStartDate", "ShiftBedStartDateField");
		verifications.verify(SeleniumVerifications.Entered, "ShiftStartDate","ShiftBedStartDateField",false);
		
		dbFunctions.storeDate(this.executeStep.getDataSet(), "ShiftEndDate","N",2);
		executeStep.performAction(SeleniumActions.Enter, "ShiftEndDate", "ShiftBedEndDateField");
		verifications.verify(SeleniumVerifications.Entered, "ShiftEndDate","ShiftBedEndDateField",false);
		
			
	}
	public void AssignBystanderBed(){
		
		// System.out.println("Inside OPRegistration RegisterPatientAsOP ");
		
				
		executeStep.performAction(SeleniumActions.Select, "BystanderBedType", "ShiftBedTypeField");
		verifications.verify(SeleniumVerifications.Selected, "BystanderBedType","ShiftBedTypeField",false);
		
		executeStep.performAction(SeleniumActions.Select, "BystanderWard", "ShiftBedWardField");
		verifications.verify(SeleniumVerifications.Selected, "BystanderWard","ShiftBedWardField",false);
		
		executeStep.performAction(SeleniumActions.Select, "BystanderBedName", "ShiftBedBedNameField");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Selected, "BystanderBedName","ShiftBedBedNameField",false);
		
		
		
		executeStep.performAction(SeleniumActions.Select, "BystanderChargedBedType", "ShiftChargedBedTypeField");
		verifications.verify(SeleniumVerifications.Selected, "BystanderChargedBedType","ShiftChargedBedTypeField",false);
		
		//executeStep.performAction(SeleniumActions.Enter, "BystanderStartDate", "ShiftBedStartDateField");
		//verifications.verify(SeleniumVerifications.Entered, "BystanderStartDate","AddAdmissionOrdersScreen",false);
		
			
	}
    
	public void SaveShiftBed(){
		executeStep.performAction(SeleniumActions.Select, "DutyDrName", "ShiftBedDutyDrField");
		verifications.verify(SeleniumVerifications.Selected, "DutyDrName","ShiftBedDutyDrField",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ShiftBedShiftButton");
		verifications.verify(SeleniumVerifications.Appears, "","ADTPage",true);
	}
	
	public void SaveByStanderBed(){
				
		executeStep.performAction(SeleniumActions.Click, "","AllocatBystanderAllocateButton");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","ADTPage",true);
	}
	
	public void setDutyDoctor(){
		executeStep.performAction(SeleniumActions.Select, "DutyDrName","OPRDutyDrName");
		verifications.verify(SeleniumVerifications.Selected, "DutyDrName","OPRDutyDrName",false);
	}
	public void AllocateBed(){ // Added By Tejaswini
	
		executeStep.performAction(SeleniumActions.Click, "","AllocateBedAllocateBtn");
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",true);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		verifications.verify(SeleniumVerifications.Appears, "","ADTPage",false);
	}
	
	}
