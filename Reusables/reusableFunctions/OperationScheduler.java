package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class OperationScheduler {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public OperationScheduler(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public OperationScheduler(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void scheduleOperationAppointment(){
		System.out.println("Inside Operation Scheduler ");
	/*	
	  DbFunctions dbFunctions = new DbFunctions();
		dbFunctions.storeDateForCalendarClick(this.executeStep.getDataSet(), "SurgeryDate");
		
		executeStep.performAction(SeleniumActions.Click, "","SurgerySchedulerDate");
		verifications.verify(SeleniumVerifications.Appears, "","SurgerySchedulerCalendar",false);
		
		
		executeStep.performAction(SeleniumActions.Click, "SurgeryDate","SurgerySchedulerCalendarDate");
		verifications.verify(SeleniumVerifications.Appears, "SurgeryDate","SurgerySchedulerDate",false);
	*/	
		//verifications.verify(SeleniumVerifications.Appears, "","SurgerySchedulerCalendar",false);
		executeStep.performAction(SeleniumActions.Click, "", "AvailableAppointmentSlot");
		verifications.verify(SeleniumVerifications.Appears, "", "Add_EditAppointmentLink", false);
		executeStep.performAction(SeleniumActions.Click, "", "Add_EditAppointmentLink");
		verifications.verify(SeleniumVerifications.Appears, "", "SurgeryAppointmentScreen", false);

		
		executeStep.performAction(SeleniumActions.Enter, "MRID","SurgeryAppointmentMRNoField");
		verifications.verify(SeleniumVerifications.Appears, "MRID","SurgeryAppointmentMRNoList",true);
		
		executeStep.performAction(SeleniumActions.Click, "MRID","SurgeryAppointmentMRNoList");
		verifications.verify(SeleniumVerifications.Entered, "MRID","SurgeryAppointmentMRNoField",false);	
		
		executeStep.performAction(SeleniumActions.Enter, "SurgeryName", "SurgeryAppointmentSurgeryNameField");
		verifications.verify(SeleniumVerifications.Appears, "SurgeryName","SurgeryAppointmentSurgeryNameDropdown",false);
		executeStep.performAction(SeleniumActions.Click, "SurgeryName","SurgeryAppointmentSurgeryNameDropdown");
		verifications.verify(SeleniumVerifications.Entered, "SurgeryName", "SurgeryAppointmentSurgeryNameField", false);
	
		executeStep.performAction(SeleniumActions.Select, "OperationalDetailsTheatre", "SurgeryAppointmentTheatreField");
		verifications.verify(SeleniumVerifications.Selected, "OperationalDetailsTheatre", "SurgeryAppointmentTheatreField", false);
		
		executeStep.performAction(SeleniumActions.Select, "SurgeryTime", "SurgeryAppointmentSurgeryTime");
		verifications.verify(SeleniumVerifications.Selected, "SurgeryTime", "SurgeryAppointmentSurgeryTime", false);
		
		executeStep.performAction(SeleniumActions.Enter, "SurgeryDuration","SurgeryAppointmentSurgeryDuration");
		verifications.verify(SeleniumVerifications.Entered, "SurgeryDuration","SurgeryAppointmentSurgeryDuration",false);
		
		executeStep.performAction(SeleniumActions.Select, "SurgeonName", "SurgeryAppointmentSurgeonNameDropdown");
		verifications.verify(SeleniumVerifications.Selected, "SurgeonName", "SurgeryAppointmentSurgeonNameDropdown", false);
				
		executeStep.performAction(SeleniumActions.Select, "AnesthetistName", "SurgeryAppointmentAnesthetistDropdown");
		verifications.verify(SeleniumVerifications.Selected, "AnesthetistName", "SurgeryAppointmentAnesthetistDropdown", false);
	
		executeStep.performAction(SeleniumActions.Click, "", "SurgeryAppointmentOkButton");
		verifications.verify(SeleniumVerifications.Appears, "", "SurgerySchedulerPage", false);
	

	}
	public void markPatientAsArrived(){
		System.out.println("Inside PatientArrival markPatientAsArrived ");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executeStep.performAction(SeleniumActions.Click, "PatientFirstName", "SurgerySchedulerBookedAppt");
		
		//TODO This block of wait is added because of the synchronisation problems that are being faced for tool tip and the patient arrived menu both coming up at the same time.
		//Need to remove it after finding a fix
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verifications.verify(SeleniumVerifications.Appears, "", "SurgerySchedulerPatientArrived", false); /// For Dealing with synchronization problems
		
		CommonUtilities.delay();
		executeStep.performAction(SeleniumActions.Click, "","SurgerySchedulerPatientArrived");
		verifications.verify(SeleniumVerifications.Appears, "","SurgeryAppointmentScreen",false);
		
		EnvironmentSetup.selectByPartMatchInDropDown = true;
		executeStep.performAction(SeleniumActions.Select, "VisitID", "SurgeryAppointmentActiveVisitField");
		verifications.verify(SeleniumVerifications.Selected, "VisitID", "SurgeryAppointmentActiveVisitField", true);
		EnvironmentSetup.selectByPartMatchInDropDown = false;
		
		executeStep.performAction(SeleniumActions.Click, "", "SurgeryAppointmentOkButton");
		verifications.verify(SeleniumVerifications.Appears, "", "SurgerySchedulerPage", false);
	}

	
}
	

