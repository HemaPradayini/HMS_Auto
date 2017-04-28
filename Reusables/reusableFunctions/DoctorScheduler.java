package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;

import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class DoctorScheduler {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public DoctorScheduler(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public DoctorScheduler(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void scheduleDoctorAppointment(){
		System.out.println("Inside Doctor Scheduler scheduleDoctorAppointment ");
		executeStep.performAction(SeleniumActions.Click, "", "AvailableAppointmentSlot");
		verifications.verify(SeleniumVerifications.Appears, "", "Add_EditAppointmentLink", false);
		executeStep.performAction(SeleniumActions.Click, "", "Add_EditAppointmentLink");
		verifications.verify(SeleniumVerifications.Appears, "", "DoctorAppointmentScreen", false);

		executeStep.performAction(SeleniumActions.Enter, "PatientFirstName", "DocApptScreenName");
		verifications.verify(SeleniumVerifications.Entered, "PatientFirstName", "DocApptScreenName", false);
		
		executeStep.performAction(SeleniumActions.Enter, "PatientMobileNumber", "DocApptScreenMobile");
		verifications.verify(SeleniumVerifications.Entered, "PatientMobileNumber", "DocApptScreenMobile", false);
		
		executeStep.performAction(SeleniumActions.Select, "DoctorName", "DocApptScreenDoctor");
		verifications.verify(SeleniumVerifications.Selected, "DoctorName", "DocApptScreenDoctor", false);
		
		/*executeStep.performAction(SeleniumActions.Store, "DoctorName", "DocApptScreenDoctor");
		executeStep.performAction(SeleniumActions.Store, "AppointmentTime", "DocApptScreenTime");*/
		
		executeStep.performAction(SeleniumActions.Select, "AppointmentTime", "DocApptScreenTime");
		verifications.verify(SeleniumVerifications.Selected, "AppointmentTime", "DocApptScreenTime", false);
		
		executeStep.performAction(SeleniumActions.Select, "AppointmentStatus", "DoctorSchedulerBookedStatus");
		verifications.verify(SeleniumVerifications.Selected, "AppointmentStatus", "DoctorSchedulerBookedStatus", false);
				
		executeStep.performAction(SeleniumActions.Click, "", "DocApptScreenOk");
		verifications.verify(SeleniumVerifications.Appears, "", "DoctorSchedulerPage", false);
		

	}
	
}
