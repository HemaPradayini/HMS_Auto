package reusableFunctions;

import keywords.SeleniumActions;
import keywords.SeleniumVerifications;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import genericFunctions.DbFunctions;
import genericFunctions.EnvironmentSetup;
import seleniumWebUIFunctions.KeywordSelectionLibrary;
import seleniumWebUIFunctions.VerificationFunctions;

public class PatientFeedBack {
	KeywordSelectionLibrary executeStep;
	VerificationFunctions verifications;
	
	public PatientFeedBack() {

	}
	
	/**
	 * Use this
	 * @param AutomationID
	 */
	public PatientFeedBack(KeywordSelectionLibrary execStep,VerificationFunctions verifications){
		this.executeStep = execStep;
		this.verifications = verifications;
	}
	

	public void feedbackForm(){

		executeStep.performAction(SeleniumActions.Click, "","FeedbackFormsAddNewFormLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddFeedbackFormPage",false);
		
	//	executeStep.performAction(SeleniumActions.Enter, "FormName","AddFeedbackFormPageFormName");
	//	verifications.verify(SeleniumVerifications.Entered, "FormName","AddFeedbackFormPageFormName",false);
		
		YourUniqueFormName();
		
		executeStep.performAction(SeleniumActions.Enter, "FormTitle","AddFeedbackFormPageFormTitle");
		verifications.verify(SeleniumVerifications.Entered, "FormTitle","AddFeedbackFormPageFormTitle",false);
		
		
		
		
		executeStep.performAction(SeleniumActions.Enter, "FormFooter","AddFeedbackFormPageFormFooter");
		verifications.verify(SeleniumVerifications.Entered, "FormFooter","AddFeedbackFormPageFormFooter",false);
		
		
		
	executeStep.performAction(SeleniumActions.Click, "","AddFeedbackFormPageFormSaveButton");
	verifications.verify(SeleniumVerifications.Appears, "","EditFeedbackFormPage",false);
	executeStep.performAction(SeleniumActions.Store, "FormName","AddFeedbackFormPageFormName");
	//executeStep.performAction(SeleniumActions.Store, "ChooseAForm","AddSurveyCategoryField");	
		
		//Click on  Feedback Form Design in the same screen and click on Add New Section 
		executeStep.performAction(SeleniumActions.Click, "","FeedbackFormDesignLink");
		verifications.verify(SeleniumVerifications.Appears, "","FeedbackFormDesignPage",false);
		executeStep.performAction(SeleniumActions.Click, "","FeedbackFormAddNewSectionLink");
		verifications.verify(SeleniumVerifications.Appears, "","AddFormSectionPage",false);
		
		
		
		//Enter all the mandatory fields and click on click on add icon under questions
		executeStep.performAction(SeleniumActions.Enter, "SectionTitle","AddFormSectionPageTitleSection");
		verifications.verify(SeleniumVerifications.Entered, "SectionTitle","AddFormSectionPageTitleSection",false);
		executeStep.performAction(SeleniumActions.Enter, "SectionOrder","AddFormSectionOrder");
		verifications.verify(SeleniumVerifications.Entered, "SectionOrder","AddFormSectionOrder",false);
		executeStep.performAction(SeleniumActions.Click, "","AddFormSectionAddButton");
		verifications.verify(SeleniumVerifications.Appears, "","AddQuestionDetailsPage",false);

		//Add two or more questions by selecting the mandatory fields and the options
		addSurveyQuestion("SurveyQuestionLineItem");
		
	//	Order order = new Order(executeStep, verifications);
	//	order.addOrderItem("SurveyResponseTypeItem", "AddQuestionDetailsPage");
	 //   order.addSurveyQuestion("SurveyQuestionLineItem");
		
	    executeStep.performAction(SeleniumActions.Click, "","AddFormSectionSaveButton");
	    verifications.verify(SeleniumVerifications.Appears, "","EditFormSectionPage",false);
			
	}
	public void patientResponse()
	{
		executeStep.performAction(SeleniumActions.Click, "","RecordPatientResponseLink");
		verifications.verify(SeleniumVerifications.Appears, "","RecordPatientResponsePage",false);
		
		MRNoSearch search = new MRNoSearch(executeStep, verifications);
		search.searchMRNo("RecordPatientResponsesMRNo","RecordPatientResponsesMRNoList","RecordPatientResponsesMRNoSearchButton","RecordPatientResponsePage");
		
    //Save the answers for the questions
		executeStep.performAction(SeleniumActions.Select, "FormName","RecordPatientResponseFormSelection");
		verifications.verify(SeleniumVerifications.Selected, "FormName","RecordPatientResponseFormSelection",false);
		verifications.verify(SeleniumVerifications.Opens, "","OperationDocumentsPrintPage",true);
	    verifications.verify(SeleniumVerifications.Appears, "","ResponseFormNextButton",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ResponseFormAnswerYesRadioButton");
		verifications.verify(SeleniumVerifications.Appears, "","ResponseFormNextButton",false);
		
		executeStep.performAction(SeleniumActions.Click, "","ResponseFormNextButton");
    	verifications.verify(SeleniumVerifications.Appears, "","ResponseFormSecondNextButton",false);
		
		executeStep.performAction(SeleniumActions.Enter, "ResponseFormTextAnswer","ResponseFormTextAnswer");
		verifications.verify(SeleniumVerifications.Entered, "ResponseFormTextAnswer","ResponseFormTextAnswer",false);
		executeStep.performAction(SeleniumActions.Click, "","ResponseFormSecondNextButton");
		verifications.verify(SeleniumVerifications.Appears, "","ResponseFormSaveButton",false);
		executeStep.performAction(SeleniumActions.Click, "","ResponseFormRatingSelection");
		verifications.verify(SeleniumVerifications.Appears, "","ResponseFormSaveButton",false);
		executeStep.performAction(SeleniumActions.Click, "","ResponseFormSaveButton");
		
		verifications.verify(SeleniumVerifications.Appears, "","Dialog",false);
		executeStep.performAction(SeleniumActions.Accept, "","Framework");
		
		verifications.verify(SeleniumVerifications.Appears, "","PatientResponsesPage",false);
		

		
	}
	
		
		public void addSurveyQuestion(String lineItemId)
		{
			EnvironmentSetup.LineItemIdForExec = lineItemId;
		    EnvironmentSetup.lineItemCount =0;
		    System.out.println("LineItemIdForExec :: "+ EnvironmentSetup.LineItemIdForExec);

		  EnvironmentSetup.UseLineItem = true;
		  DbFunctions dbFunction = new DbFunctions();
		  int rowCount = dbFunction.getRowCount(this.executeStep.getDataSet());
		  System.out.println("Row Count for " + EnvironmentSetup.LineItemIdForExec + "is :: " + rowCount);
		  for(int i=1; i<=rowCount; i++){	
			EnvironmentSetup.UseLineItem = true;
			executeStep.performAction(SeleniumActions.Enter, "QuestionDetail","SurveyQuestionDetail");
			verifications.verify(SeleniumVerifications.Entered, "QuestionDetail","SurveyQuestionDetail",false);
			executeStep.performAction(SeleniumActions.Enter, "QuestionOrder","SurveyQuestionOrder");
			verifications.verify(SeleniumVerifications.Entered, "QuestionOrder","SurveyQuestionOrder",false);
			executeStep.performAction(SeleniumActions.Select, "Category","SurveyQuestionCategory");
			verifications.verify(SeleniumVerifications.Selected, "Category","SurveyQuestionCategory",false);
			executeStep.performAction(SeleniumActions.Select, "QuestionResponseType","SurveyQuestionResponseType");
			verifications.verify(SeleniumVerifications.Selected, "QuestionResponseType","SurveyQuestionResponseType",false);
			//if("QuestionResponseType".equals("Rating")){
				executeStep.performAction(SeleniumActions.Select, "QuestionRating","SurveyQuestionRating");
				verifications.verify(SeleniumVerifications.Selected, "QuestionRating","SurveyQuestionRating",true);	
			//	} 
			executeStep.performAction(SeleniumActions.Click, "","SurveyQuestionDetailsScreenOKButton");
			verifications.verify(SeleniumVerifications.Appears, "","AddQuestionDetailsPage",false);
			EnvironmentSetup.lineItemCount ++;
			EnvironmentSetup.UseLineItem = false;
		  }
		  executeStep.performAction(SeleniumActions.Click, "","SurveyQuestionDetailsScreenCancelButton");
		  verifications.verify(SeleniumVerifications.Appears, "","AddFormSectionPage",false);
		  
		  EnvironmentSetup.lineItemCount =0;
		  EnvironmentSetup.UseLineItem = false;
			
		}

		public String generateRandomString(int length){
		      return RandomStringUtils.randomAlphabetic(length);
		     }  //for letters

		public String generateRandomNumber(int length){
		      return RandomStringUtils.randomNumeric(length);
		     }  //for numbers


	    	public void YourUniqueFormName(){

	    		try{
	    			//driver.findElement(By.xpath("//input[@id='form_name']")).clear();
	    			this.executeStep.getDriver().findElement(By.xpath("//input[@id='form_name']")).sendKeys("FormName"+"Test"+ RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(3));
	    		} catch (Exception e){
	    			e.printStackTrace();
	    		}
		 
		    }
	
	}
