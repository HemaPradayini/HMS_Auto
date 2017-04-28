package reusableFunctions;

import genericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Roster {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public Roster(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 * @return 
	 */
	public Roster(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void clickAddRosterResourceTypeLink(){
		executeStep.performAction(SeleniumActions.Click, "","AddRosterResourceTypeLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddRosterResourceTypePage",false);
	}
	
	public void addRosterResourceType(){
		executeStep.performAction(SeleniumActions.Enter,"RosterResourceType" ,"RosterResourceType");
		verifications.verify(SeleniumVerifications.Entered,"RosterResourceType" ,"RosterResourceType",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddRosterResourceTypeSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditRosterResourceTypePage",false);
	
		}
	
	public void clickAddResourceLink(){
		executeStep.performAction(SeleniumActions.Click, "","AddResourceLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddRosterResourcePage",false);
	}
	
	public void addRosterResource(){
		executeStep.performAction(SeleniumActions.Enter, "PatientFirstName","RosterResourceName");
		verifications.verify(SeleniumVerifications.Entered,"PatientFirstName" ,"RosterResourceName",false);
		
		executeStep.performAction(SeleniumActions.Select, "RosterResourceType","SelectRosterResourceType");
		verifications.verify(SeleniumVerifications.Selected, "RosterResourceType","SelectRosterResourceType",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddRosterResourceSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditRosterResourcePage",false);
	
		}
	
	public void clickAddShift(){
		executeStep.performAction(SeleniumActions.Click, "","AddShiftLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddShiftPage",false);
	}
	
	public void addShift(){
		executeStep.performAction(SeleniumActions.Enter, "ShiftName","ShiftName");
		verifications.verify(SeleniumVerifications.Entered,"ShiftName" ,"ShiftName",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ShiftStartTime","ShiftStartTime");
		verifications.verify(SeleniumVerifications.Entered,"ShiftStartTime" ,"ShiftStartTime",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ShiftEndTime","ShiftEndTime");
		verifications.verify(SeleniumVerifications.Entered,"ShiftEndTime" ,"ShiftEndTime",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ShiftOrder","ShiftOrder");
		verifications.verify(SeleniumVerifications.Entered,"ShiftOrder" ,"ShiftOrder",false);
		
		executeStep.performAction(SeleniumActions.Click, "","AddShiftSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditShiftPage",false);
		
	}
	
	public void setDutyRoster(){
		
		executeStep.performAction(SeleniumActions.Select, "ShiftName","SearchShift");
		verifications.verify(SeleniumVerifications.Selected, "ShiftName","SearchShift",false);
		
		executeStep.performAction(SeleniumActions.Click, "","DutyRosterSeachButton");
		verifications.verify(SeleniumVerifications.Appears, "","DutyRosterPage",false);
		
		executeStep.performAction(SeleniumActions.Click, "","TodayDutyRoster");
		verifications.verify(SeleniumVerifications.Appears, "","TodayDutyRoster",false);
		
		//executeStep.performAction(SeleniumActions.Click, "","TomorrowDutyRoster");
		//verifications.verify(SeleniumVerifications.Appears, "","TomorrowDutyRoster",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditShift");
		verifications.verify(SeleniumVerifications.Appears, "","ShiftDialogBox",false);
		
		executeStep.performAction(SeleniumActions.Select, "UserRole","DutyRosterUserRole");
		verifications.verify(SeleniumVerifications.Selected, "UserRole","DutyRosterUserRole",false);
		
		executeStep.performAction(SeleniumActions.Select, "Username","DutyRosterUser");
		verifications.verify(SeleniumVerifications.Selected, "Username","DutyRosterUser",false);
		
		executeStep.performAction(SeleniumActions.Select, "RosterResourceType","DutyRosterResourceType");
		verifications.verify(SeleniumVerifications.Selected, "RosterResourceType","DutyRosterResourceType",false);
		
		executeStep.performAction(SeleniumActions.Select, "PatientFirstName","DutyRosterResource");
		verifications.verify(SeleniumVerifications.Selected, "PatientFirstName","DutyRosterResource",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ShiftDialogBoxAddButton");
		
		executeStep.performAction(SeleniumActions.Click, "","ShiftDialogBoxSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","DutyRosterPage",false);
		
		
		
	}
	
	public void editShift(){
		executeStep.performAction(SeleniumActions.Enter, "ShiftName","ShiftName");
		verifications.verify(SeleniumVerifications.Entered,"ShiftName" ,"ShiftName",false);
		
		executeStep.performAction(SeleniumActions.Click, "","DutyRosterSeachButton");
		verifications.verify(SeleniumVerifications.Appears, "","ShiftListResult",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ShiftListResult");
		verifications.verify(SeleniumVerifications.Appears, "","EditShift",false);
		
		executeStep.performAction(SeleniumActions.Click, "","EditShift");
		verifications.verify(SeleniumVerifications.Appears, "","EditShiftPage",false);
		
		EnvironmentSetup.UseLineItem = true;
		
		EnvironmentSetup.LineItemIdForExec = "EditShift";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		
		executeStep.performAction(SeleniumActions.Enter, "ShiftStartTime","ShiftStartTime");
		verifications.verify(SeleniumVerifications.Entered,"ShiftStartTime" ,"ShiftStartTime",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ShiftEndTime","ShiftEndTime");
		verifications.verify(SeleniumVerifications.Entered,"ShiftEndTime" ,"ShiftEndTime",false);
		
		EnvironmentSetup.UseLineItem = false;
		
		executeStep.performAction(SeleniumActions.Click, "","AddShiftSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","EditShiftPage",false);
	}
	
}
