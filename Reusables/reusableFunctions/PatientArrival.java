package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.openqa.selenium.WebDriver;

import genericFunctions.CommonUtilities;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientArrival {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	
	public PatientArrival(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientArrival(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	public void markPatientAsArrived(){
		System.out.println("Inside PatientArrival markPatientAsArrived ");
		executeStep.performAction(SeleniumActions.Click, "PatientFirstName", "DoctorSchedulerBookedAppt");
		
		//TODO This block of wait is added because of the synchronisation problems that are being faced for tool tip and the patient arrived menu both coming up at the same time.
		//Need to remove it after finding a fix
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verifications.verify(SeleniumVerifications.Appears, "", "DoctorSchedulerPatientArrived", false); /// For Dealing with synchronization problems
		
		/*Actions ToolTip1 = new Actions(driver);
		WebElement googleLogo = this.executeStep.getDriver().findElement(By.xpath("//div[@id='hplogo']"));
		ToolTip1.clickAndHold(googleLogo).perform(); // Perform mouse hover action using 'clickAndHold' method
		String ToolTipText = googleLogo.getAttribute("title"); // Get the value of Tooltip by getAttribute command
		Assert.assertEquals(ToolTipText, "Google");
		System.out.println("Tooltip value is: " + ToolTipText);*/
		CommonUtilities.delay(1);
		executeStep.performAction(SeleniumActions.Click, "","DoctorSchedulerPatientArrived");
		verifications.verify(SeleniumVerifications.Appears, "","OPRegistrationScreen",false);
	}
	
}
