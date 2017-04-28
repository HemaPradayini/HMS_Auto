package reusableFunctions;



import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import genericFunctions.CommonUtilities;
import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;

import java.util.List;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class Dialysis {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;

	public Dialysis() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public Dialysis(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

public void addPrescriptionDetails(){
		executeStep.performAction(SeleniumActions.Select, "DialysisDoctor","AddDialysisPrescriptionDetailsDoctor");
	verifications.verify(SeleniumVerifications.Selected,"DialysisDoctor","AddDialysisPrescriptionDetailsDoctor",false);
	
	DbFunctions dbFunctions = new DbFunctions();
	dbFunctions.storeDate(this.executeStep.getDataSet(), "PrescriptionDate","C",0);
	
	executeStep.performAction(SeleniumActions.Enter, "PrescriptionDate","AddDialysisPrescriptionDate");
	verifications.verify(SeleniumVerifications.Entered,"PrescriptionDate","AddDialysisPrescriptionDate",false);
	
	dbFunctions.storeDate(this.executeStep.getDataSet(), "PrescriptionStartDate","C",0);
	
	executeStep.performAction(SeleniumActions.Enter, "PrescriptionStartDate","PrescriptionStartDate");
	verifications.verify(SeleniumVerifications.Entered,"PrescriptionStartDate","PrescriptionStartDate",false);
	

	dbFunctions.storeDate(this.executeStep.getDataSet(), "PrescriptionEndDate","C",0);
	
	executeStep.performAction(SeleniumActions.Enter, "PrescriptionEndDate","PrescriptionEndDate");
	verifications.verify(SeleniumVerifications.Entered,"PrescriptionEndDate","PrescriptionEndDate",false);
	
	
	executeStep.performAction(SeleniumActions.Enter, "DryWeight","PrescriptionDryWeight");
	verifications.verify(SeleniumVerifications.Entered,"DryWeight","PrescriptionDryWeight",false);
	
	dbFunctions.storeDate(this.executeStep.getDataSet(), "DryWeightDate","C",0);
	executeStep.performAction(SeleniumActions.Enter, "DryWeightDate","PrescriptionDryWeightDate");
	verifications.verify(SeleniumVerifications.Entered,"DryWeightDate","PrescriptionDryWeightDate",false);
	
	executeStep.performAction(SeleniumActions.Select, "DialysateType","DialysateType");
	verifications.verify(SeleniumVerifications.Selected,"DialysateType","DialysateType",false);
	
	executeStep.performAction(SeleniumActions.Select, "Dialyzer","DialyzerName");
	verifications.verify(SeleniumVerifications.Selected,"Dialyzer","DialyzerName",false);
		
}
		
		public void addTemporaryAccess(){
			executeStep.performAction(SeleniumActions.Click, "","TemporaryAccessAddButton");
			verifications.verify(SeleniumVerifications.Appears, "","TemporaryAccessScreen",false);
			
			executeStep.performAction(SeleniumActions.Select, "TemporaryAccess","TemporaryAccessType");
			verifications.verify(SeleniumVerifications.Selected,"TemporaryAccess","TemporaryAccessType",false);
			
			executeStep.performAction(SeleniumActions.Select, "AccessSite","AccessSite");
			verifications.verify(SeleniumVerifications.Selected,"AccessSite","AccessSite",false);
			
			executeStep.performAction(SeleniumActions.Click, "","TemporaryAccessScreenAddButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddDialysisPrescriptionDetailsPage",false);
			
			
		}
		

		public void addPreDialysisDetails(){
			executeStep.performAction(SeleniumActions.Select, "PreDialysisStatus","PreDialysisDetailsStatus");
			verifications.verify(SeleniumVerifications.Selected,"PreDialysisStatus","PreDialysisDetailsStatus",false);
			
			executeStep.performAction(SeleniumActions.Select, "PreDialysisPhysician","PreDialysisDetailsPhysician");
			verifications.verify(SeleniumVerifications.Selected,"PreDialysisPhysician","PreDialysisDetailsPhysician",false);
			
			
			executeStep.performAction(SeleniumActions.Select, "PrimaryDialysisTherapist","PrimaryDialysisTherapistName");
			verifications.verify(SeleniumVerifications.Selected,"PrimaryDialysisTherapist","PrimaryDialysisTherapistName",false);
		
			executeStep.performAction(SeleniumActions.Select, "SecondaryDialysisTherapist","SecondaryDialysisTherapistName");
			verifications.verify(SeleniumVerifications.Selected,"SecondaryDialysisTherapist","SecondaryDialysisTherapistName",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PreDialysisPassword","PasswordField");
			verifications.verify(SeleniumVerifications.Entered,"PreDialysisPassword","PasswordField",false);
			
			EnvironmentSetup.selectByPartMatchInDropDown = true;
			executeStep.performAction(SeleniumActions.Select, "PreDialysisLocation","PreDialysisDetailsLocation");
			verifications.verify(SeleniumVerifications.Selected,"PreDialysisLocation","PreDialysisDetailsLocation",true);
			EnvironmentSetup.selectByPartMatchInDropDown = false;
			
			executeStep.performAction(SeleniumActions.Select, "PreDialysisMachine","PreDialysisDetailsMachine");
			verifications.verify(SeleniumVerifications.Selected,"PreDialysisMachine","PreDialysisDetailsMachine",false);
						
			executeStep.performAction(SeleniumActions.Select, "PreDialysisAccessType","PreDialysisDetailsAccessType");
			verifications.verify(SeleniumVerifications.Selected,"PreDialysisAccessType","PreDialysisDetailsAccessType",false);
			
			executeStep.performAction(SeleniumActions.Select, "PreDialysisBruitThrill","PreDialysisBruitThrill");
			verifications.verify(SeleniumVerifications.Selected,"PreDialysisBruitThrill","PreDialysisBruitThrill",false);
			
			executeStep.performAction(SeleniumActions.Select, "PreDialysisAccessSite","PreDialysisDetailsAccessSiteId");
			verifications.verify(SeleniumVerifications.Selected,"PreDialysisAccessSite","PreDialysisDetailsAccessSiteId",false);
			
			executeStep.performAction(SeleniumActions.Select, "PreDialysisNeedleType","PreDialysisDetailsNeedleType");
			verifications.verify(SeleniumVerifications.Selected,"PreDialysisNeedleType","PreDialysisDetailsNeedleType",false);
			
			
			
			
		}
		
		public void addPreEquipPreparation()
		{
			executeStep.performAction(SeleniumActions.Click, "","PreEquipPreparation");
			verifications.verify(SeleniumVerifications.Opens, "","PreEquipPreparationDialog",true);
			//verifications.verify(SeleniumVerifications.Appears, "","PreEquipPreparationDialog",false);
			List <WebElement> weList = this.executeStep.getDriver().findElements(By.xpath("//input[@name='pre_prep_param_name']"));
			
			for (WebElement ele : weList){ 
				if (ele.isEnabled()){
				ele.click();
				}
			}
			
			
			executeStep.performAction(SeleniumActions.Click, "","PreEquipPreparationOKButton");
			
			verifications.verify(SeleniumVerifications.Appears, "","PreDialysisDetailsPage",false);
			
			
		}
		
		
		
		public void addDialyzerReuse()
		{
			executeStep.performAction(SeleniumActions.Enter, "ReprocessNumber","DialysisReprocessNumber");
			verifications.verify(SeleniumVerifications.Entered,"ReprocessNumber","DialysisReprocessNumber",false);
			
			DbFunctions dbFunctions = new DbFunctions();
			dbFunctions.storeDate(this.executeStep.getDataSet(), "ReprocessedDate","C",0);
			
			executeStep.performAction(SeleniumActions.Enter, "ReprocessedDate","ReprocessDate");
			verifications.verify(SeleniumVerifications.Entered,"ReprocessedDate","ReprocessDate",false);
		
			executeStep.performAction(SeleniumActions.Select, "ReuseRating","DialyzerReuseRating");
			verifications.verify(SeleniumVerifications.Selected,"ReuseRating","DialyzerReuseRating",false);
			
		
		}
		
		public void patientCondition()
		{
			executeStep.performAction(SeleniumActions.Enter, "BPSittingHigh","DialyzerBPSittingHigh");
			verifications.verify(SeleniumVerifications.Entered,"BPSittingHigh","DialyzerBPSittingHigh",false);
			
			executeStep.performAction(SeleniumActions.Enter, "BPSittinglow","DialyzerBPSittinglow");
			verifications.verify(SeleniumVerifications.Entered,"BPSittinglow","DialyzerBPSittinglow",false);
			
			executeStep.performAction(SeleniumActions.Enter, "BPStandingHigh","DialyzerBPStandingHigh");
			verifications.verify(SeleniumVerifications.Entered,"BPStandingHigh","DialyzerBPStandingHigh",false);
			
			executeStep.performAction(SeleniumActions.Enter, "BPStandingLow","DialyzerBPStandingLow");
			verifications.verify(SeleniumVerifications.Entered,"BPStandingLow","DialyzerBPStandingLow",false);
		
			executeStep.performAction(SeleniumActions.Enter, "PulseSitting","DialyzerPulseSitting");
			verifications.verify(SeleniumVerifications.Entered,"PulseSitting","DialyzerPulseSitting",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PulseStanding","DialyzerPulseStanding");
			verifications.verify(SeleniumVerifications.Entered,"PulseStanding","DialyzerPulseStanding",false);
			
		}
		
		public void addIntraDialysis()
		{
			executeStep.performAction(SeleniumActions.Select, "IntraDialysisStatus","IntraDialysisStatusSelection");
			verifications.verify(SeleniumVerifications.Selected,"IntraDialysisStatus","IntraDialysisStatusSelection",false);
			executeStep.performAction(SeleniumActions.Click, "","PostEquipPreparation");
			verifications.verify(SeleniumVerifications.Appears, "","PostEquipPreparationDialog",false);
			
List <WebElement> weList = this.executeStep.getDriver().findElements(By.xpath("//input[@name='post_prep_param_name']"));
			
			for (WebElement ele : weList){ 
				if (ele.isEnabled()){
				ele.click();
				}
			}
			
		
	
		executeStep.performAction(SeleniumActions.Click, "","PostEquipPreparationOKButton");
		verifications.verify(SeleniumVerifications.Appears, "","IntraDialysisDetailsPage",false);
		executeStep.performAction(SeleniumActions.Click, "","IntraDialysisSaveButton");
		verifications.verify(SeleniumVerifications.Appears, "","IntraDialysisDetailsPage",false);
		}
		
		
		
		public void postDialysisDetails()
		{
	
		
			executeStep.performAction(SeleniumActions.Select, "DialysisClosingAttendant","PostDialysisClosingAttendant");
			verifications.verify(SeleniumVerifications.Selected,"DialysisClosingAttendant","PostDialysisClosingAttendant",false);
			
			executeStep.performAction(SeleniumActions.Select, "CompletionStatus","PostDialysisCompletionStatus");
			verifications.verify(SeleniumVerifications.Selected,"CompletionStatus","PostDialysisCompletionStatus",false);
			
			DbFunctions dbFunctions = new DbFunctions();
			dbFunctions.storeDate(this.executeStep.getDataSet(), "CurrentSessionEndDate","C",0);
			dbFunctions.storeDate(this.executeStep.getDataSet(), "NewSessionEndDate","N",1);
			
			
			
			executeStep.performAction(SeleniumActions.Enter, "NewSessionEndDate","PostDialysisEndDate");
			verifications.verify(SeleniumVerifications.Entered,"NewSessionEndDate","PostDialysisEndDate",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PostBPSittingHigh","PostDialyzerBPSittingHigh");
			verifications.verify(SeleniumVerifications.Entered,"PostBPSittingHigh","PostDialyzerBPSittingHigh",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PostBPSittinglow","PostDialyzerBPSittinglow");
			verifications.verify(SeleniumVerifications.Entered,"PostBPSittinglow","PostDialyzerBPSittinglow",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PostBPStandingHigh","PostDialyzerBPStandingHigh");
			verifications.verify(SeleniumVerifications.Entered,"PostBPStandingHigh","PostDialyzerBPStandingHigh",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PostBPStandingLow","PostDialyzerBPStandingLow");
			verifications.verify(SeleniumVerifications.Entered,"PostBPStandingLow","PostDialyzerBPStandingLow",false);
		
			executeStep.performAction(SeleniumActions.Enter, "PostPulseSitting","PostDialyzerPulseSitting");
			verifications.verify(SeleniumVerifications.Entered,"PostPulseSitting","PostDialyzerPulseSitting",false);
			
			executeStep.performAction(SeleniumActions.Enter, "PostPulseStanding","PostDialyzerPulseStanding");
			verifications.verify(SeleniumVerifications.Entered,"PostPulseStanding","PostDialyzerPulseStanding",false);
			
			executeStep.performAction(SeleniumActions.Select, "PostRating","PostDialyzerRatingDetails");
			verifications.verify(SeleniumVerifications.Selected,"PostRating","PostDialyzerRatingDetails",false);
			
			executeStep.performAction(SeleniumActions.Select, "PostDialysisBruitThrill","PostDialyzerBruitThrillDetails");
			verifications.verify(SeleniumVerifications.Selected,"PostDialysisBruitThrill","PostDialyzerBruitThrillDetails",false);
			
			executeStep.performAction(SeleniumActions.Click, "","PostDialysisSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","PostDialysisDetailsPage",false);
						
		}
		
	
		
	
}
