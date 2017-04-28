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

public class ConsultationAndMgmt {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public ConsultationAndMgmt(){
		
	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public ConsultationAndMgmt(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	
	//public void saveConsultationAndMgmt(){
	public void saveConsultationAndMgmt(String lineItemId){      // Parameter added by Abhishek		
		System.out.println("Inside ConsultationAndMgmt saveConsultationAndMgmt ");
		DiagnosisDetails diagDetails = new DiagnosisDetails(executeStep, verifications);
		//diagDetails.addDianosisDetails("ConsultationAndManagementPage");
		diagDetails.addDianosisDetails( lineItemId,"ConsultationAndManagementPage");  // Parameter added by Abhishek
		doConsultationAndMangement(); //added by Reena
		
	}
	
	public void EditCheifComplaint() //added by Reena for DHA Claim
	{  
		executeStep.performAction(SeleniumActions.Enter, "ChiefComplaint","TestConductionChiefComplaint");
		verifications.verify(SeleniumVerifications.Entered, "ChiefComplaint","TestConductionChiefComplaint",false);
	}
	
	public void TobaccoUsage()//added by Reena for DHA Claim
	{
		WebElement we = null;
		try{
			we = this.executeStep.getDriver().findElement(By.xpath("//legend[contains(text(),'Tobacco Usage')]/input[@type='button' and @value='+']"));
		}catch (Exception e){
			System.out.println("Webelement for given xpath not found" + e.toString());

		}
		
		if (we!=null){	
			executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenTobaccoButton");
			verifications.verify(SeleniumVerifications.Appears, "","ConsultMgtScreenTobaccoExp",false);
			executeStep.performAction(SeleniumActions.Select, "TobaccoUsage", "TobaccoUsageSelect");
			verifications.verify(SeleniumVerifications.Selected, "TobaccoUsage", "TobaccoUsageSelect", false);
		}
		
	
	}
	public void doConsultationAndMangement() //added by Reena
	{
		
		//Vitals Reusable
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenAddVitalsBtn");
		verifications.verify(SeleniumVerifications.Appears, "Pulse","ConsultMgtScreenVitalsDialog",false);
		
		executeStep.performAction(SeleniumActions.Enter, "Pulse","ConsultMgtScreenVitalsDialogPulseField");
		verifications.verify(SeleniumVerifications.Entered, "Pulse","ConsultMgtScreenVitalsDialogPulseField",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenVitalsDialogOKButton");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultMgtScreenVitalsDialog",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenVitalsDialogCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",true);
		
		//ReviewOfSystems Reusable
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenReviewOfSystemsButton");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultMgtScreenReviewOfSystemsExp",true);

		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenReviewOfSystemsAllSysNormalButton");
		verifications.verify(SeleniumVerifications.Appears, "ReviewOfSystems","ConsultMgtScreenReviewOfSystemsConstitutionalField",true);

		//PhysicalExam Reusable
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenPhyExamBtn");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultMgtScreenPhyExamBtnExp",true);

		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenPhyExamAllSysNormalButton");
		verifications.verify(SeleniumVerifications.Entered, "ReviewOfSystems","ConsultMgtScreenPhyExamConstitutionalField",true);
		
		
		//TreatmentPlan Reusable
		//Treatment plan is not available for Cen. Hence, modified to check the availability of the field. - Alamelu
		WebElement we = null;
		try{
			we = this.executeStep.getDriver().findElement(By.xpath("//legend[contains(text(),'Treatment Plan & Goals')]/input[@type='button' and @value='+']"));
		}catch (Exception e){
			System.out.println("Webelement for given xpath not found" + e.toString());
		}
		if (we!=null){	
			executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenTreatmentPlanAndGoalsButton");
			verifications.verify(SeleniumVerifications.Appears, "","ConsultMgtScreenTreatmentPlanAndGoalsExp",false);
			executeStep.performAction(SeleniumActions.Enter, "TreatmentPlanAndGoals","ConsultMgtScreenTreatmentPlanAndGoalsField");
			verifications.verify(SeleniumVerifications.Entered, "TreatmentPlanAndGoals","ConsultMgtScreenTreatmentPlanAndGoalsField",true);
		}
		
		//Add Prescription Reusable
		addManagementItems();
		}
	
	public void closeConsultaion(){
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenPFSHAddButton");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",true);
		
		executeStep.performAction(SeleniumActions.Enter, "PFSH","ConsultMgtScreenPFSHTextBox");
		verifications.verify(SeleniumVerifications.Entered, "PFSH","ConsultMgtScreenPFSHTextBox",false);
		
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenHPIAddButton");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenHPITextBox");
		executeStep.performAction(SeleniumActions.Enter, "HistoryPresentIllness","ConsultMgtScreenHPITextBox");
		verifications.verify(SeleniumVerifications.Entered, "HistoryPresentIllness","ConsultMgtScreenHPITextBox",true);
		
		
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenCloseConsultationCheckBox");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",true);
		
		
	}
	
	public void addManagementItems(){
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenAddItems");
		verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);

		EnvironmentSetup.UseLineItem = true;
		EnvironmentSetup.lineItemCount = 0;           //Added by Tejaswini
		EnvironmentSetup.LineItemIdForExec = "Investigation";
		System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);
		//DbFunctions dbFunction = new DbFunctions();
		int rowCount=0;
		DbFunctions dbFunction = new DbFunctions();

		rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		for(int i=0; i<rowCount; i++){			
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Click, "ItemType","AddItemScreenItemType");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);

			executeStep.performAction(SeleniumActions.Enter, "Item","AddItemScreenItemField");
			verifications.verify(SeleniumVerifications.Appears,"","AddItemScreenItemList",true);
			
			executeStep.performAction(SeleniumActions.Click, "Item","AddItemScreenItemList");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);

			try{
				executeStep.performAction(SeleniumActions.Enter, "ItemDuration","ItemDurationFeild");
				verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
				
				executeStep.performAction(SeleniumActions.Enter, "NumberOfUnits","NumberOfUnitsFeild");
				verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
				}catch(Exception e){
				
				System.out.println("Item Duration and Number of Units for PBM work flow");
				
			}
			
		WebElement	 we = this.executeStep.getDriver().findElement(By.xpath("//a[@title='Select Tooth Numbers']//img[@class='button']"));

					 try{
					 if(we.isEnabled())
					 we.click();
					 executeStep.performAction(SeleniumActions.Click, "","ToothNumberButton");
					 					verifications.verify(SeleniumVerifications.Appears, "","ToothNumberScreen",true);
					 					
					 					executeStep.performAction(SeleniumActions.Check, "","PediatricToothA");
					 					verifications.verify(SeleniumVerifications.Checked, "","PediatricToothA",false);
					 					
					 					executeStep.performAction(SeleniumActions.Check, "","PediatricToothB");
					 					verifications.verify(SeleniumVerifications.Checked, "","PediatricToothB",false);
					 					
					 					executeStep.performAction(SeleniumActions.Check, "","AdultTooth1");
					 					verifications.verify(SeleniumVerifications.Checked, "","AdultTooth1",false);
					 					
					 					executeStep.performAction(SeleniumActions.Check, "","AdultTooth2");
					 					verifications.verify(SeleniumVerifications.Checked, "","AdultTooth2",false);
					 					
					 					executeStep.performAction(SeleniumActions.Click, "","ToothNumberScreenOKButton");
					 					verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);

					 }catch (Exception e){
					 						System.out.println("Webelement for given xpath not found" + e.toString());
					 					}

			
			
			
			executeStep.performAction(SeleniumActions.Enter, "ItemQuantity","AddItemScreenItemQuantity");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
			
			if(this.executeStep.getAutomationID().equalsIgnoreCase("PriorAuthWorkflow")){
				//Added by Tejaswini for Priot Auth Workflow
				executeStep.performAction(SeleniumActions.Check, "SendForPriorAuth","AddItemSendForPriorAuthChkBox");
				verifications.verify(SeleniumVerifications.Checked, "SendForPriorAuth","AddItemSendForPriorAuthChkBox",true);
			}
					//Added by Tejaswini for Prior Auth Workflow
			EnvironmentSetup.UseLineItem = false;

			executeStep.performAction(SeleniumActions.Click, "","AddItemScreenAddButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddItemScreen",true);
			EnvironmentSetup.lineItemCount ++;

		}
		EnvironmentSetup.lineItemCount = 0;           //Added by Tejaswini
		EnvironmentSetup.UseLineItem = false;          //Added by abhishek
		executeStep.performAction(SeleniumActions.Click, "","AddItemScreenCloseButton");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",true);//Synchronisation issue
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenSaveButton");
		System.out.println("Consultation Screen Saved");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",false);//Synchronisation issue
		System.out.println("Consultation Screen Saved");
}
	public void doConsultationAndMangementCorpInsurance() //added by Reena
	{
		
		//Vitals Reusable
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenAddVitalsBtn");
		verifications.verify(SeleniumVerifications.Appears, "Pulse","ConsultMgtScreenVitalsDialog",false);
		
		executeStep.performAction(SeleniumActions.Enter, "Pulse","ConsultMgtScreenVitalsDialogPulseField");
		verifications.verify(SeleniumVerifications.Entered, "Pulse","ConsultMgtScreenVitalsDialogPulseField",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenVitalsDialogOKButton");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultMgtScreenVitalsDialog",true);
		
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenVitalsDialogCancelButton");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultationAndManagementPage",true);
		/*		
		//ReviewOfSystems Reusable
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenReviewOfSystemsButton");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultMgtScreenReviewOfSystemsExp",true);
*/
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenReviewOfSystemsAllSysNormalButtonCaps");
		verifications.verify(SeleniumVerifications.Appears, "ReviewOfSystems","ConsultMgtScreenReviewOfSystemsConstitutionalFieldCaps",true);
/*
		//PhysicalExam Reusable
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenPhyExamBtn");
		verifications.verify(SeleniumVerifications.Appears, "","ConsultMgtScreenPhyExamBtnExp",true);
*/
		executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenPhyExamAllSysNormalButton");
		verifications.verify(SeleniumVerifications.Entered, "ReviewOfSystems","ConsultMgtScreenPhyExamConstitutionalField",true);
	/*	
		
		//TreatmentPlan Reusable
		//Treatment plan is not available for Cen. Hence, modified to check the availability of the field. - Alamelu
		WebElement we = null;
		try{
			we = this.executeStep.getDriver().findElement(By.xpath("//legend[contains(text(),'Treatment Plan & Goals')]/input[@type='button' and @value='+']"));
		}catch (Exception e){
			System.out.println("Webelement for given xpath not found" + e.toString());
		}
		if (we!=null){	
			executeStep.performAction(SeleniumActions.Click, "","ConsultMgtScreenTreatmentPlanAndGoalsButton");
			verifications.verify(SeleniumVerifications.Appears, "","ConsultMgtScreenTreatmentPlanAndGoalsExp",false);
			executeStep.performAction(SeleniumActions.Enter, "TreatmentPlanAndGoals","ConsultMgtScreenTreatmentPlanAndGoalsField");
			verifications.verify(SeleniumVerifications.Entered, "TreatmentPlanAndGoals","ConsultMgtScreenTreatmentPlanAndGoalsField",true);
		}
	*/	
		//Add Prescription Reusable
		addManagementItems();
		}
}
