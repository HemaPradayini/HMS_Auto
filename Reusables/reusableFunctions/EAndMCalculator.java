package reusableFunctions;

import genericFunctions.EnvironmentSetup;
import keywords.SeleniumActions;
import keywords.SeleniumVerifications;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class EAndMCalculator {
	
		KeywordSelectionLibrary executeStep;
		VerificationFunctions verifications;
		
		public EAndMCalculator(){
			
		}
		
		/**
		 * Use this
		 * @param AutomationID
		 * @return 
		 */
		public EAndMCalculator(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
			this.executeStep = execStep;
			this.verifications = verifications;
		}
		
		
		public void navigateToEAndMFromConsultation(){
			
			executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenE&MCalculatorLink");
			verifications.verify(SeleniumVerifications.Appears, "","E&MCalculatorPage",false);
		}
		
		public void selectVisitType(){
			executeStep.performAction(SeleniumActions.Select, "VisitType","E&MCalculatorScreenVisitType");
			verifications.verify(SeleniumVerifications.Selected, "VisitType","E&MCalculatorScreenVisitType",false);
		}
		
		public void setTreatmentOptions(){
			executeStep.performAction(SeleniumActions.Select, "ProblemStatus","E&MCalculatorScreenProblemStatus");
			verifications.verify(SeleniumVerifications.Selected, "ProblemStatus","E&MCalculatorScreenProblemStatus",false);
			
			executeStep.performAction(SeleniumActions.Enter, "TreatmentCount","E&MCalculatorScreenTreatmentCount");
			verifications.verify(SeleniumVerifications.Entered, "TreatmentCount","E&MCalculatorScreenTreatmentCount",false);
		}
		
		public void selectRiskEvolution(){
			executeStep.performAction(SeleniumActions.Select, "RiskEvaluation","E&MCalculatorScreenRiskOfComplications");
			verifications.verify(SeleniumVerifications.Selected, "RiskEvaluation","E&MCalculatorScreenRiskOfComplications",false);
			
		}
		
		//**************************************************
		public void complexityOfDataReview(){
			
			EnvironmentSetup.UseLineItem = true;
			EnvironmentSetup.LineItemIdForExec = "ReviewLabTest";
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			executeStep.performAction(SeleniumActions.Check, "PrimarySponsor","E&MCalculatorScreenReviewLabTestCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "PrimarySponsor","E&MCalculatorScreenReviewLabTestCheckBox",false);
			
			EnvironmentSetup.LineItemIdForExec = "ReviewRadiology";
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			
			executeStep.performAction(SeleniumActions.Check, "PrimarySponsor","E&MCalculatorScreenReviewRadiologyCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "PrimarySponsor","E&MCalculatorScreenReviewRadiologyCheckBox",false);
			
			
			EnvironmentSetup.LineItemIdForExec = "ReviewMedicine";
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			executeStep.performAction(SeleniumActions.Check, "PrimarySponsor","E&MCalculatorScreenReviewMedicineCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "PrimarySponsor","E&MCalculatorScreenReviewMedicineCheckBox",false);
			
			EnvironmentSetup.LineItemIdForExec = "ReviewPerformingPhysician";
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			executeStep.performAction(SeleniumActions.Check, "PrimarySponsor","E&MCalculatorScreenReviewPerformingPhysicianCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "PrimarySponsor","E&MCalculatorScreenReviewPerformingPhysicianCheckBox",false);
			
			EnvironmentSetup.LineItemIdForExec = "ObtainOldRecord";
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			executeStep.performAction(SeleniumActions.Check, "PrimarySponsor","E&MCalculatorScreenObtainOldRecordCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "PrimarySponsor","E&MCalculatorScreenObtainOldRecordCheckBox",false);
			
			EnvironmentSetup.LineItemIdForExec = "ReviewHistory";
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			executeStep.performAction(SeleniumActions.Check, "PrimarySponsor","E&MCalculatorScreenReviewObtainingHistoryFormCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "PrimarySponsor","E&MCalculatorScreenReviewObtainingHistoryFormCheckBox",false);
			
			EnvironmentSetup.LineItemIdForExec = "IndependentVisualisation";
			System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
			executeStep.performAction(SeleniumActions.Check, "PrimarySponsor","E&MCalculatorScreenIndependentVisualisationCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "PrimarySponsor","E&MCalculatorScreenIndependentVisualisationCheckBox",false);
			
			EnvironmentSetup.UseLineItem = false;
		}
		
		public void finalizeEMCode(){
			
			executeStep.performAction(SeleniumActions.Check, "","E&MCalculatorScreenFinalizeCheckBox");
			verifications.verify(SeleniumVerifications.Checked, "","E&MCalculatorScreenFinalizeCheckBox",false);
			
			executeStep.performAction(SeleniumActions.Enter, "E&Mcode","E&MCalculatorScreenE&MCodeContainer");
			verifications.verify(SeleniumVerifications.Appears, "","E&MCalculatorScreenE&MCodeListResult",false);
			
			executeStep.performAction(SeleniumActions.Click, "","E&MCalculatorScreenE&MCodeListResult");
			
			
			verifications.verify(SeleniumVerifications.Entered, "E&Mcode","E&MCalculatorScreenE&MCodeContainer",false);
			
			executeStep.performAction(SeleniumActions.Click, "","E&MCalculatorScreenSaveButton");
			verifications.verify(SeleniumVerifications.Appears, "","E&MCalculatorPage",false);
		}
}
