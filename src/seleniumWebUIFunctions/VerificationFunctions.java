package seleniumWebUIFunctions;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import keywords.SeleniumVerifications;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import RecoveryScenarios.SalesForceRecovery;
import RecoveryScenarios.SiebelRecovery;
import genericFunctions.DataParser;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;

//import com.thoughtworks.selenium.Wait.WaitTimedOutException;

@SuppressWarnings("deprecation")
public class VerificationFunctions extends KeywordExecutionLibrary {

	DataParser dataParser = new DataParser();
	String strInputData;
	static String strObjectProp;
	String strApplicationData="";
	String strAlternateApplicationData = "";
	By objectIdentifier;
	boolean blnVerificationResult = false;
	boolean blnStepResult = false;
	String strStepActualResult = "";
	String strStepActualStatus = "";
	String strCurrentException="";
	String strRequiredWindowHandle = "";
	String UniqueValue="";
	String dataFieldName = "";
	String strCurrentObjName = "";
	boolean isRadioButton = false;
	
	static WebDriver driver;
	String AutomationID;
	String DataSet;
	String LineItemID;
	
	public VerificationFunctions(WebDriver driver, String AutomationID, String DataSet, String LineItemID){
		VerificationFunctions.driver = driver;
		this.AutomationID = AutomationID;
		this.DataSet = DataSet;
		this.LineItemID = EnvironmentSetup.strCurrentLineItemDataSet = LineItemID;
	}
	
	public VerificationFunctions(WebDriver driver, String AutomationID, String DataSet){
		VerificationFunctions.driver = driver;
		this.AutomationID = AutomationID;
		this.DataSet = DataSet;
		this.LineItemID = EnvironmentSetup.strCurrentLineItemDataSet = "";
	}
	
	public VerificationFunctions(){
		
	}
	
	public void verify(SeleniumVerifications verificationType, String DataColName , String ObjectName , boolean ProceedOnError){
		String verificationStatus = "";
		verificationStatus = verify(verificationType.toString(), ObjectName, DataColName, DataSet);
		if(DataColName.isEmpty()){
			DataColName = ObjectName;
		}
		//Report
        String calledClassName1 = sun.reflect.Reflection.getCallerClass(1).getName();
        String calledClassName2 = sun.reflect.Reflection.getCallerClass(2).getName();
        String calledClassName3 = sun.reflect.Reflection.getCallerClass(3).getName();
        System.out.println("Called Class is :: 1: " + calledClassName1 + "2: " + calledClassName2 + "3: " + calledClassName3);

        String Step = "Perform Step in Class / Reusable:" +calledClassName1+"::"+calledClassName2+"::" + calledClassName3 + " "+verificationType.toString() + "\n Object: " + ObjectName + "\nData: " + DataColName;
		String[] tempResult = verificationStatus.split(":::");
		String ActualResult = "";
		String Status = "";
		try{
			ActualResult = tempResult[0];
			Status = tempResult[1];
		}
		catch(Exception ex){
			
		}
		
		if (Status.equalsIgnoreCase("FAIL")&& ProceedOnError){
			Status = "Warning";
		}
		ReportingFunctions.updateTestNGStepReport(AutomationID, DataSet, "Verification", Step, ActualResult, Status);
		
		if(!ProceedOnError){
			Assert.assertTrue(verificationStatus.contains(":::Pass"));
		}
		
	}
	
	/*public void verify(SeleniumVerifications verificationType, String ObjectName, String DataColName,  boolean ProceedOnError){
		String verificationStatus = "";
		verificationStatus = verify(verificationType.toString(), ObjectName, DataColName, DataSet);
		
		//Report
		//ReportingFunctions.updateTestNGStepReport(AutomationID, CurrentDataSet, StepType, Step, ActualResult, Status);
		if(!ProceedOnError){
			Assert.assertTrue(verificationStatus.contains("Pass"));
		}
	}*/
	
	public String verify(String strCurrentVerificationType, String strCurrentObjectName, String strCurrentDataField, String strCurrentDataset){
		strAlternateApplicationData = "";
		strApplicationData = "";
		dataFieldName = strCurrentDataField;
		strObjectProp = "";
		strCurrentObjName = strCurrentObjectName;
		//Tejaswini
		if (strCurrentDataset == null || strCurrentDataset == ""){
			strCurrentDataset = this.DataSet;
		}//Tejaswini
		switch (strCurrentVerificationType.trim().toUpperCase()) {
		
		case "ENTERED":
			
			if(!strCurrentDataField.isEmpty()){
				//Data Column Name and Object Name are different
				strInputData = dataParser.getData(strCurrentDataField, strCurrentDataset);
			}
			else{
				//Data Column Name and Object Name are the same
				strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
			}
			
			if(strInputData == null || strInputData.length()<0){
				strInputData = "";
			}
			
			//Fetch Object Property
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);

			//Build Object - to replace <data>,<count>,<DataColName> etc
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			
			//Method to Fetch Data From Application and Compare with Input Data. Method Does comparisons and returns True or False 
			blnVerificationResult = entered();
			
			if (blnVerificationResult == true){
				strStepActualResult = strApplicationData + " entered in " + strCurrentObjectName;
			}
			else{
				strStepActualResult = strInputData + " not entered in " + strCurrentObjectName + "; Actual value: " + strApplicationData;
			}
			
			break;
			
		case "PRESENT":
			if(!strCurrentDataField.isEmpty()){
				strInputData = dataParser.getData(strCurrentDataField, strCurrentDataset);
			}
			else{
				strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
			}
			
			if(strInputData == null || strInputData.length()<0){
				strInputData = "";
			}
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);

			if(strObjectProp.contains("<data>")){
				strObjectProp = strObjectProp.replace("<data>", strInputData);
			}
			
			blnVerificationResult = present();
			
			if (blnVerificationResult == true){
				strStepActualResult = strCurrentObjectName + " is Present";
			}
			else{
				strStepActualResult = strCurrentObjectName + " is Not Present";
			}
			
			break;
		
		case "NOTPRESENT":
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			if(strObjectProp.contains("<data>")){
				strObjectProp = strObjectProp.replace("<data>", strInputData);
			}
			
			blnVerificationResult = present();
			
			if (blnVerificationResult == false){
				strStepActualResult = strCurrentObjectName + " is Not Present";
				blnVerificationResult = true;
			}
			else {
				strStepActualResult = strCurrentObjectName + " is Present";
				blnVerificationResult = false;
			}
			
			break;
		
		case "DISPLAYED":
			
			if(!strCurrentDataField.isEmpty()){
				strInputData = dataParser.getData(strCurrentDataField, strCurrentDataset);
			}
			else{
				strInputData = "";
			//	strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
			}
			
			
			if(strInputData == null || strInputData.length()<0){
				strInputData = "";
			}
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			
			if(strObjectProp.contains("<data>")){
				strObjectProp = strObjectProp.replace("<data>", strInputData);
			}
			
			blnVerificationResult = displayed();
			
			if (blnVerificationResult == true){
				if(!strCurrentDataField.isEmpty()){
					strStepActualResult = strApplicationData + " is Displayed in " + strCurrentObjectName;
				}
				else{
					strStepActualResult = strCurrentObjectName + " is Displayed";
				}
			}
			else{
				if(!strCurrentDataField.isEmpty()){
					strStepActualResult = strInputData + " is not Displayed in " + strCurrentObjectName + "; Actual Value: "+ strApplicationData + "; Errors: " + strCurrentException;
				}
				else{
					strStepActualResult = strCurrentObjectName + " is not Displayed";
				}
			}
			
			break;
			
		case "NOTDISPLAYED":
			if(!strCurrentDataField.isEmpty()){
				strInputData = dataParser.getData(strCurrentDataField, strCurrentDataset);
			}
			else{
				strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
			}
			
			if(strInputData == null || strInputData.length()<0){
				strInputData = "";
			}
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			
			if(strObjectProp.contains("<data>")){
				strObjectProp = strObjectProp.replace("<data>", strInputData);
			}
			
			blnVerificationResult = displayed();
			
			if (blnVerificationResult == false){
				blnVerificationResult = true;
				if(!strCurrentDataField.isEmpty()){
					strStepActualResult = strInputData + " is not Displayed in " + strCurrentObjectName + "; Actual Value: "+ strApplicationData;
				}
				
				else{
					strStepActualResult = strCurrentObjectName + " is not Displayed";
				}
			}
			else{
				blnVerificationResult = false;
				if(!strCurrentDataField.isEmpty()){
					strStepActualResult = strInputData + " is Displayed in " + strCurrentObjectName;
				}
				else{
					strStepActualResult = strCurrentObjectName + " is Displayed";
				}
			}
			break;
			
		case "SELECTED":
			
			if(!strCurrentDataField.isEmpty()){
				strInputData = dataParser.getData(strCurrentDataField, strCurrentDataset);
			}
			else{
				strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
			}
			
			if(strInputData == null || strInputData.length()<0){
				strInputData = "";
			}
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);

			if(strObjectProp.contains("<data>")){
				strObjectProp = strObjectProp.replace("<data>", strInputData);
			}
			
			blnVerificationResult = selected();
			
			if(isRadioButton){
				strApplicationData = strCurrentObjectName;
			}
			
			if (blnVerificationResult == true){
				strStepActualResult = strApplicationData + " is Selected in " + strCurrentObjectName;
			}
			else{
				strStepActualResult = strInputData + " is Not Selected in " + strCurrentObjectName + "; Actual Data: " + strApplicationData;
			}
			
			if(isRadioButton){
				strStepActualResult = strStepActualResult.replace(" in " + strCurrentObjectName , "");
				isRadioButton = false;
				if(!blnVerificationResult){
					strStepActualResult = strStepActualResult.replace("; Actual Data: " + strApplicationData, "");
				}
			}
			
			break;
		
		case "CHECKED":
			if(!strCurrentDataField.isEmpty()){
				strInputData = dataParser.getData(strCurrentDataField, strCurrentDataset);
			}
			else{
				strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
			}
			
			if(strInputData == null || strInputData.length()<0){
				strInputData = "";
			}
			
			//Default Value will be taken as Y
			if(strInputData.isEmpty()){
				strInputData = "Y";
			}
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			
			if(strObjectProp.contains("<data>")){
				strObjectProp = strObjectProp.replace("<data>", strInputData);
			}
			
			blnVerificationResult = checked();
			
			if (blnVerificationResult == true && strInputData.equalsIgnoreCase("Y")){
				strStepActualResult = strCurrentObjectName + " Checked; Expected: " + strInputData;
			}
			else if (blnVerificationResult == true && strInputData.equalsIgnoreCase("N")){
				strStepActualResult = strCurrentObjectName + " Checked; Expected: " + strInputData;
				blnVerificationResult = false;
			}
			else if (blnVerificationResult == false && strInputData.equalsIgnoreCase("N")){
				strStepActualResult = strCurrentObjectName + " Not Checked; Expected: " + strInputData;
				blnVerificationResult = true;
			}
			else if (blnVerificationResult == false && strInputData.equalsIgnoreCase("Y")){
				strStepActualResult = strCurrentObjectName + " Not Checked; Expected: " + strInputData;
				//blnVerificationResult = true;
			}
			
			break;
		
		case "NOTCHECKED":
		case "UNCHECKED":
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			
			strInputData = "N";
			
			blnVerificationResult = checked();
			
			if (blnVerificationResult == true){
				strStepActualResult = strCurrentObjectName + " is Checked";
				blnVerificationResult = false;
			}
			
			else if (blnVerificationResult == false){
				strStepActualResult = strCurrentObjectName + " is Not Checked";
				blnVerificationResult = true;
			}
			
			break;
			
			
		case "NOTAPPEAR":
			
			if((strCurrentObjectName.equalsIgnoreCase("Dialog"))){
				blnVerificationResult = verifyNoDialog();
				if(blnVerificationResult==true){
					blnVerificationResult = false;
					strStepActualResult = "Dialog with text: " + strApplicationData + " appeared";
					EnvironmentSetup.strGlobalException = strStepActualResult;
					System.out.println(strStepActualResult);
					EnvironmentSetup.logger.info(strStepActualResult);
					//Handle Sieb Save Error
					SiebelRecovery.saveErrors();
				}
				else{
					blnVerificationResult = true;
					strStepActualResult = "No Dialog Present";
				}
			}
			else{
				strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
				strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
				strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
				
				if(strObjectProp.contains("<data>")){
					strObjectProp = strObjectProp.replace("<data>", strInputData);
				}
				
				blnVerificationResult = notPresentWithSync();
				
				if(blnVerificationResult==true){
					strStepActualResult = strCurrentObjectName + " is not Present";
				}
				else{
					strStepActualResult = strCurrentObjectName + " is Present";
				}
			}
			
			break;
			
		case "APPEARS":
			EnvironmentSetup.logger.info("Appears");
			//waitForNoLoadElements();// Added by Tejaswini
			if(!strCurrentDataField.isEmpty()){
				strInputData = dataParser.getData(strCurrentDataField, strCurrentDataset);
			}
			else{
				strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
			}
			
			if(strInputData == null || strInputData.length()<0){
				strInputData = "";
			}
			
			if(!(strCurrentObjectName.equalsIgnoreCase("Dialog"))){
				strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
				strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
				strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
				if(strObjectProp.contains("<data>")){
					strObjectProp = strObjectProp.replace("<data>", strInputData.trim());
				}
			}
			else{
				strObjectProp = "Dialog";
			}
			
			EnvironmentSetup.logger.info("Appears: Expected Object: " + strObjectProp);

			if(strObjectProp.contains("<data>")){
				strObjectProp = strObjectProp.replace("<data>", strInputData);
			}
			blnVerificationResult = appears();

			if (blnVerificationResult == true){
				strStepActualResult = strCurrentObjectName + " Appeared";
			}
			else{
				strStepActualResult = strCurrentObjectName + " Not Appeared";
				captureScreenshot();
			}
			
			break;
		
		case "OPENS":
			//Method to check if a new window has opened. This method will also switch the focus to the new/child window
			blnVerificationResult = opens();
			
			if (blnVerificationResult == true){
				strStepActualResult = "Lookup Window Opened";
			}
			else{
				strStepActualResult = "Lookup Window not Opened";
			}
			
			break;
		
		case "CLOSES":
			//Method to check if a new window is closed. This will also switch focus to the main/parent window
			blnVerificationResult = closes();
			
			if (blnVerificationResult == true){
				strStepActualResult = "New Window Closed";
			}
			else{
				strStepActualResult = "New Window not Closed";
			}
			
			strRequiredWindowHandle = "";
			break;

		case "ENABLED":
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			blnVerificationResult = enabled();
			
			if (blnVerificationResult == true){
				strStepActualResult = strCurrentObjectName + " is Enabled";
			}
			else{
				strStepActualResult = strCurrentObjectName + " is Disabled";
			}
			break;
		
		case "DISABLED":
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			blnVerificationResult = enabled();
			
			if (blnVerificationResult == true){
				strStepActualResult = strCurrentObjectName + " is Enabled";
				blnVerificationResult = false;
			}
			else{
				strStepActualResult = strCurrentObjectName + " is Disabled";
				blnVerificationResult = true;
			}
			break;
		
			
		case "EDITABLE":
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			blnVerificationResult = editable();
			
			if (blnVerificationResult == true){
				strStepActualResult = strCurrentObjectName + " is Editable";
			}
			else{
				strStepActualResult = strCurrentObjectName + " is ReadOnly";
			}
			break;
		
			
		case "READONLY":
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			blnVerificationResult = editable();
			
			if (blnVerificationResult == true){
				strStepActualResult = strCurrentObjectName + " is Editable";
				blnVerificationResult = false;
			}
			else{
				strStepActualResult = strCurrentObjectName + " is ReadOnly";
				blnVerificationResult = true;
			}
			break;
			
		case "VERIFYERROR":
			String strActualError = "";
			String[] arrObjects = null;
			String[] arrErrors = null;
			blnVerificationResult = true;
			String strMainError = "";
			String strFieldError = "";
			if(strCurrentObjectName.contains(",")){
				arrObjects = strCurrentObjectName.split(",");
				
				for(String strExpected: arrObjects){
					strInputData = dataParser.getData(strExpected.trim(), strCurrentDataset);
					if (strExpected.toUpperCase().contains("MAIN")){
						strMainError = verifyMainError();
						strActualError = strMainError;
					}
					else{
						strFieldError = verifyFieldLevelErrors();
						strActualError = strActualError + strFieldError;
					}
					
					
					if (strInputData.contains(" >> ")){
						arrErrors = strInputData.split(" >> ");
						for(String error : arrErrors){
							if(strActualError.trim().contains(error.trim()) || error.trim().contains(strActualError.trim())){
								strStepActualResult = strActualError + " Displayed as expected"; 
							}
							else{
								blnVerificationResult = false;
								strStepActualResult = error + " Not Displayed as expected. Actual Result: " + strActualError;
							}
						}
					}
					else{
						if(strActualError.trim().contains(strInputData.trim()) || strInputData.trim().contains(strActualError.trim())){
							strStepActualResult = strActualError + " Displayed as expected"; 
						}
						else{
							blnVerificationResult = false;
							strStepActualResult = strInputData + " Not Displayed as expected. Actual Result: " + strActualError;
						}
					}
				}
			}
			else if(strCurrentObjectName.toUpperCase().contains("MAIN") && !strCurrentObjectName.toUpperCase().contains("FIELD")){
				strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
				strActualError = verifyMainError();
				if(strActualError.trim().contains(strInputData.trim()) || strInputData.trim().contains(strActualError.trim())){
					strStepActualResult = strActualError + " Displayed as expected"; 
				}
				else{
					blnVerificationResult = false;
					strStepActualResult = strInputData + " Not Displayed as expected. Actual Result: " + strActualError;
				}
			}
			else if(!strCurrentObjectName.toUpperCase().contains("MAIN") && strCurrentObjectName.toUpperCase().contains("SUB")){
				strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
				strActualError = verifyMainError();
				if(strActualError.trim().contains(strInputData.trim()) || strInputData.trim().contains(strActualError.trim())){
					strStepActualResult = strActualError + " Displayed as expected"; 
				}
				else{
					blnVerificationResult = false;
					strStepActualResult = strInputData + " Not Displayed as expected. Actual Result: " + strActualError;
				}
			}
			
			break;
			
		case "CHOSEN":
			
			if(!strCurrentDataField.isEmpty()){
				strInputData = dataParser.getData(strCurrentDataField, strCurrentDataset);
			}
			else{
				strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
			}
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			blnVerificationResult = chosen();
			
			break;
			
		case "NOTBLANK":
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			notBlank();
			EnvironmentSetup.logger.info("Not Blank App Data>>" + strApplicationData + "<<");
			if((strApplicationData == null || strApplicationData.trim().isEmpty() || strApplicationData.trim()=="") 
					&& (strAlternateApplicationData == null || strAlternateApplicationData.isEmpty() || strAlternateApplicationData.trim()=="")){
				strStepActualResult = strCurrentObjectName + " is Blank";
				blnVerificationResult = false;
				
			}
			else{
				strStepActualResult = strCurrentObjectName + " is not Blank, Actual Value: " + strApplicationData + strAlternateApplicationData;
				blnVerificationResult = true;
			}
			
			break;
		
		case "BLANK":
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			notBlank();
			
			if((strApplicationData == null || strApplicationData.trim().isEmpty()) && 
				strAlternateApplicationData == null || strAlternateApplicationData.trim().isEmpty()){
					strStepActualResult = strCurrentObjectName + " is Blank";
					blnVerificationResult = true;
			}
			else{
				strStepActualResult = strCurrentObjectName + " is not Blank, Actual Value: " + strApplicationData + strAlternateApplicationData;
				blnVerificationResult = false;
			}
			
			break;
			
		case "POPULATED":
			
			strObjectProp = dataParser.getObjectProperty(strCurrentObjectName);
			strObjectProp = dataParser.BuildObjectWithData(strObjectProp, strCurrentDataset);
			strObjectProp = dataParser.BuildObjectWithData(strCurrentDataField,strObjectProp, strCurrentDataset);
			if(!strCurrentDataField.isEmpty()){
				strInputData = dataParser.getData(strCurrentDataField, strCurrentDataset);
				
			}
			else{
				strInputData = dataParser.getData(strCurrentObjectName, strCurrentDataset);
			}
			
			notBlank();
			
			if(strInputData.equalsIgnoreCase("N") || strInputData.equalsIgnoreCase("No")){
				if((strApplicationData == null || strApplicationData.trim().isEmpty()) && 
					strAlternateApplicationData == null || strAlternateApplicationData.trim().isEmpty()){
						strStepActualResult = strCurrentObjectName + " is Blank";
						blnVerificationResult = true;
				}
				else{
					strStepActualResult = strCurrentObjectName + " is not Blank, Actual Value: " + strApplicationData + strAlternateApplicationData;
					blnVerificationResult = false;
				}
			}
			else{
				if((strApplicationData == null || strApplicationData.trim().isEmpty() || strApplicationData.trim()=="") 
						&& (strAlternateApplicationData == null || strAlternateApplicationData.isEmpty() || strAlternateApplicationData.trim()=="")){
					strStepActualResult = strCurrentObjectName + " is Blank";
					blnVerificationResult = false;
					
				}
				else{
					strStepActualResult = strCurrentObjectName + " is not Blank, Actual Value: " + strApplicationData + strAlternateApplicationData;
					blnVerificationResult = true;
				}
			}
			
			break;
			
		default:
			String strVerificationStatus = "Action Performed:::Done";
			return strVerificationStatus;
		}
		
		//Actual values for Reporting
		if (blnVerificationResult == true){
			strStepActualStatus = "Pass";
		}
		else{
			strStepActualStatus = "Fail";
		}
		
		//If Unique values are not Empty, The Actual Values are appended to the Actual Result
		if(!EnvironmentSetup.uniqueValues.isEmpty()){
			strStepActualResult = strStepActualResult + " for " + EnvironmentSetup.uniqueValues;
			EnvironmentSetup.uniqueValues = "";
		}
		//Verification Status returned to the Keyword Selection Library for Reporting
		//Format: Actual Result:::Pass/Fail
		String strVerificationStatus = strStepActualResult + ":::" + strStepActualStatus;
		EnvironmentSetup.logger.info(strVerificationStatus);
		
		return strVerificationStatus;
	}
	
	public boolean present(){
		blnStepResult = false;
		if (strObjectProp.contains("iframe")){	
			driver.switchTo().defaultContent();
		}
		
		for(int iWait = 0; iWait<60; iWait++){
			try{
				blnStepResult = driver.findElement(By.xpath(strObjectProp)).isDisplayed();
				//Driver.isElementPresent(strObjectProp);
				//blnStepResult = true;
			}
			catch(UnhandledAlertException ex){
				handleAlert();
			}
			catch(Exception ex){
				EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException + ex.toString();
				System.out.println(ex.toString());
			}
			if (blnStepResult == true){
				if (strObjectProp.contains("iframe")){	
					driver.switchTo().defaultContent();
				}
				if (strObjectProp.contains("frame")){
					driver.switchTo().frame(driver.findElement(By.xpath(strObjectProp)));
				}
				break;
			}
		}
		
		if(strObjectProp.contains("resultsFrame")){
			EnvironmentSetup.windowsOpen = EnvironmentSetup.windowsOpen-1;
			opens();
			driver.switchTo().frame(driver.findElement(By.xpath(strObjectProp)));
			blnStepResult = true;
		}
		EnvironmentSetup.logger.info(strObjectProp +">> "+ blnStepResult);
		return blnStepResult;
	}
	
	public boolean notPresentWithSync(){
		blnStepResult = false;
		
		for(int i=0;i<100;i++){
			try{
				blnStepResult = driver.findElement(By.xpath(strObjectProp)).isDisplayed();
			}
			catch(NoSuchElementException ex){
				return true;
			}
			catch(Exception ex){
				EnvironmentSetup.strGlobalException = ex.toString();
				//blnStepResult = false;
			}
		}
		
		return blnStepResult;
	}
	
	
	public boolean verifyNoDialog(){
		
		boolean alertPresent;
		alertPresent = false;
		//String alertText = "";
				
		for(int iCount=0; iCount<5; iCount++){
			
			try {
				Thread.sleep(500);
				driver.getTitle();
				
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
			catch(UnhandledAlertException ex){
				EnvironmentSetup.strCurrentScreenshotLocation = EnvironmentSetup.strScreenshotsPath +"Step_" 
						+ Integer.toString(EnvironmentSetup.intSlNo+1)+ "_Dialog_" + EnvironmentSetup.strCurrentDataset +".png";
				
				try {
					BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
					ImageIO.write(image, "png", new File(EnvironmentSetup.strCurrentScreenshotLocation));
					
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				
				alertPresent = true;
				strApplicationData = driver.switchTo().alert().getText();
				driver.switchTo().alert().accept();
				break;
			}
			catch (Exception ex){
				alertPresent = false;
			}
			
		}
		return alertPresent;
	}
	
	public boolean displayed(){
		blnStepResult = false;
		try{
			//strInputData = "NoDataToVerify";
			if(dataFieldName.isEmpty()){
				try{
					blnStepResult = driver.findElement(By.xpath(strObjectProp)).isDisplayed();
				}
				catch(NoSuchElementException ex){
					blnStepResult = false;
				}
			}
			else{
				if(strObjectProp.contains("//input")){
					try{
						strApplicationData = driver.findElement(By.xpath(strObjectProp)).getAttribute("value");
						strAlternateApplicationData = driver.findElement(By.xpath(strObjectProp)).getText();
						if(strAlternateApplicationData == null){
							strAlternateApplicationData = "";
						}
					}
					catch(NoSuchElementException ex){
						
					}
				}
				else{
					try{
						
						strApplicationData = driver.findElement(By.xpath(strObjectProp)).getText();
						strAlternateApplicationData = driver.findElement(By.xpath(strObjectProp)).getAttribute("value");
						if(EnvironmentSetup.strModuleName.equalsIgnoreCase("ProjectPhoenix")){
							if(strObjectProp.contains("//table")){
								strAlternateApplicationData = driver.findElement(By.xpath(strObjectProp)).getAttribute("title");
							}
							/*else{
								strAlternateApplicationData = driver.findElement(By.xpath(strObjectProp)).getAttribute("value");
							}*/
						}
						
						if(strObjectProp.contains("Additional Web")){
							System.out.println("Input");
						}
						
						if(strApplicationData==null || strApplicationData.trim().isEmpty()){
							strApplicationData = driver.findElement(By.xpath(strObjectProp)).getAttribute("value");
							if(strApplicationData==null || strApplicationData.trim().isEmpty()){
								strApplicationData = "";
							}
							if(strAlternateApplicationData==null || strAlternateApplicationData.trim().isEmpty()){
								strAlternateApplicationData = "";
							}
						}
					}
					catch(NoSuchElementException ex){
						blnStepResult = false;
						strApplicationData = "";
						strAlternateApplicationData = "";
						EnvironmentSetup.strGlobalException = ex.toString();
						return false;
					}
					
					catch(Exception ex){
						
					}
					
				}
				
				if(strApplicationData==null || strApplicationData.trim().isEmpty()){
					strApplicationData = "";
				}
				if(strAlternateApplicationData==null || strAlternateApplicationData.trim().isEmpty()){
					strAlternateApplicationData = "";
				}
				
				if(strApplicationData.isEmpty() && !strAlternateApplicationData.isEmpty() ){
					strApplicationData = strAlternateApplicationData;
				}
				else if(!strApplicationData.isEmpty() && strAlternateApplicationData.isEmpty()){
					strAlternateApplicationData = strApplicationData; 
				}
				/*if(strApplicationData.equalsIgnoreCase(strAlternateApplicationData)){
					strAlternateApplicationData = "";
				}*/
				
				if(strInputData.trim().isEmpty() && strAlternateApplicationData.trim().isEmpty() && strApplicationData.trim().isEmpty()){
					EnvironmentSetup.logger.info("All Empty");
					blnStepResult = true;
				}
				
				else if ((!strInputData.isEmpty() && (strApplicationData.isEmpty() && strAlternateApplicationData.isEmpty())) || 
						(strInputData.isEmpty() && (!strApplicationData.isEmpty() && !strAlternateApplicationData.isEmpty()))){
					blnStepResult = false;
				}
				
				else{
					
					/*if (strApplicationData.toUpperCase().contains(strInputData.toUpperCase()) || strApplicationData.trim().contains(strInputData.trim()) 
						|| strInputData.trim().contains(strApplicationData.trim()) || strAlternateApplicationData.toUpperCase().contains(strInputData.toUpperCase()) 
						|| strAlternateApplicationData.trim().contains(strInputData.trim())	|| strInputData.trim().contains(strAlternateApplicationData.trim())){
						
						blnStepResult = true;
						EnvironmentSetup.logger.info("Data Displayed in app " + strApplicationData);
					}*/
					
					if (strApplicationData.toUpperCase().contains(strInputData.toUpperCase()) || strApplicationData.trim().contains(strInputData.trim()) 
							|| strInputData.trim().contains(strApplicationData.trim())){
						blnStepResult = true;
						EnvironmentSetup.logger.info("Data Displayed in app " + strApplicationData);
					}
					
				}
			}
			
		}
		catch(UnhandledAlertException ex){
			handleAlert();
		}
		catch (Exception ex){
			ex.printStackTrace();
			if(ex.toString().length()>=70){
				strCurrentException = ex.toString().substring(1, 70);
				EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
			}
			else{
				strCurrentException = ex.toString().substring(1, ex.toString().length());
				EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
			}
			blnStepResult = false;
		}
		
			
		return blnStepResult;
	}
	
	public boolean notDisplayed(){
		blnStepResult = false;
		if (strInputData != ""){
			if(strObjectProp.contains("//input")){
				strApplicationData = driver.findElement(By.xpath(strObjectProp)).getAttribute("value");
			}
			else{
				strApplicationData = driver.findElement(By.xpath(strObjectProp)).getText();
			}

			if (strApplicationData.toUpperCase().contains(strInputData.toUpperCase())){
				blnStepResult = true;
				EnvironmentSetup.logger.info("Data Displayed in app " + strApplicationData);
			}
		}
		else{
			try{
				blnStepResult = driver.findElement(By.xpath(strObjectProp)).isDisplayed();
			}
			catch(UnhandledAlertException ex){
				handleAlert();
			}
			catch (Exception ex){
				EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
			}
		}
		return blnStepResult;
	
	}
	
	public boolean editable(){
		//
		try{
			String objectEnabled="";
			/*
			 * Find Object and Get Type Attribute. 
			 * If Type is text - try entering something
			 * If Type is Checkbox/radio - try clicking on it
			 * If Type is select, try selecting the default value
			 * All of these actions MUST throw the ElementNotVisibleException for this method to return true
			*/
			try{
				objectEnabled = driver.findElement(By.xpath(strObjectProp)).getAttribute("aria-readonly");
				EnvironmentSetup.logger.info("Aria Read Only Prop" + objectEnabled);
			}
			catch(UnhandledAlertException ex){
				SiebelRecovery.saveErrors();
			}
			catch (NoSuchElementException ex){
				EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
			}
			catch(ElementNotVisibleException ex){
				
			}
			catch(Exception ex){
				
			}
			
			if(objectEnabled == null){
				blnStepResult = true;
			}
			else if(objectEnabled.equalsIgnoreCase("true")){
				blnStepResult = false;
			}
			else if(objectEnabled.equalsIgnoreCase("false")){
				blnStepResult = true;
			}
			
			EnvironmentSetup.logger.info("Editable Status: " + blnStepResult);
			
		}
		catch(Exception ex){
			System.err.println(ex.toString());
			EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
		}
		
		return blnStepResult;
	}
	
	//Sync Function
	public boolean appears(){
		//Verification Step in Verify Keyword OR Expected result is Dialog Appears
		if(strObjectProp.equalsIgnoreCase("Dialog")){
			EnvironmentSetup.logger.info("Expected Dialog Handler");
			String pageLoad= "";
        	System.out.println(pageLoad);
        	
			try{
				pageLoad = (String) ((JavascriptExecutor)driver).executeScript("return document.readyState");
	        	System.out.println(pageLoad);
	    		driver.wait(1);
	    	}
			catch(TimeoutException ex){
				EnvironmentSetup.strTimeOutException = ex.toString();
				EnvironmentSetup.logger.info("Timed Out: " + ex.toString());
				if(EnvironmentSetup.strApplicationName.equalsIgnoreCase("SalesForce")){
					if(EnvironmentSetup.strTimeOutException.contains("renderer")){
						SalesForceRecovery.timeOutRecovery();
					}
				}
			}
			catch(UnhandledAlertException ex){
				blnStepResult = true;
				//handleAlert();
			}
	    	catch (IllegalMonitorStateException ex){
	    		blnStepResult = true;
				EnvironmentSetup.logger.info("Expected Alert Present " + ex.toString());
	    	}
	    	
	    	catch (Exception ex){
	    		blnStepResult = false;
	    		EnvironmentSetup.logger.info("Expected Alert Not Present " + ex.toString());
	    		pageLoad = (String) ((JavascriptExecutor)driver).executeScript("return document.readyState");
	        	System.out.println(pageLoad);
	        	
	    	}
		}
		//All Elements within a webpage
		else{
			//Switch to Default
			if (strObjectProp.contains("iframe") && (!(strObjectProp.contains("SelfServiceIncident")) && !(strObjectProp.contains("Transition")) && !(strObjectProp.contains("AnswerCustomer")))){   
				driver.switchTo().defaultContent();
			}
			
			for(int iWait = 0; iWait<120; iWait++){
				//Before Check
				waitForNoLoadElements();
				
				/*TODO be Changed laterif(strObjectProp.contains("value='-'")){ Also handle the opening and closing span
					Click on the + Version of the object to open it Tejaswini
				}*/
				if (strObjectProp.contains("CollapsiblePanel")){
					if (strObjectProp.contains("CollapsiblePanelOpen")){
						System.out.println("Collapsible Panel is already Open");
					} else{
						System.out.println("Collapsible Panel is closed");
		        		try{
		        			Actions action = new Actions(driver);
		        			System.out.println("strObjectProp is :: " + strObjectProp);
		        			action.moveToElement(driver.findElement(By.xpath(strObjectProp)), 1, 1).click().build().perform();
		        		} catch (Exception e){
		        				System.out.println("Inside the CollapsiblePanelOpen Exception Block");
			        			EnvironmentSetup.logger.warning(EnvironmentSetup.strCurrentObjectName + " Not found: " + e.toString());
		        		}		
					}					
				} 				
				if (strObjectProp.contains("iframe") && (!(strObjectProp.contains("SelfServiceIncident")) && !(strObjectProp.contains("Transition")) && !(strObjectProp.contains("AnswerCustomer")))){   
					driver.switchTo().defaultContent();
				}
				
				if(strObjectProp.contains("resultsFrame")){
					opens();
					blnStepResult = true;
				}
				else{
	               try{
	            	   //During
	            	   waitForNoLoadElements();
	            	   
	            	   blnStepResult = driver.findElement(By.xpath(strObjectProp)).isDisplayed();
	            	   if(EnvironmentSetup.strApplicationName.equalsIgnoreCase("SalesForce") || EnvironmentSetup.strApplicationName.equalsIgnoreCase("Luna")){
							handleUpdateMessage();
						}
						EnvironmentSetup.logger.info("Sync: "+blnStepResult);

	               }
	               catch(UnhandledAlertException ex){
	   					handleAlert();
	   					waitForNoLoadElements();
	               }
	   	    	catch (IllegalMonitorStateException ex){
	   	    			handleAlert();
	   	    			waitForNoLoadElements();
	   	    	}
	               catch(Exception ex){
	            	   System.out.println("exception" + ex.toString());
	            	   handleUpdateMessage();
	            	   waitForNoLoadElements();
	            	   blnStepResult = false;

	               }
				}
					
				if(blnStepResult==true){
					if (strObjectProp.contains("iframe") && (!(strObjectProp.contains("SelfServiceIncident")) && !(strObjectProp.contains("Transition")) && !(strObjectProp.contains("AnswerCustomer")))){   
						driver.switchTo().defaultContent();
					}
					//frame, Frame, iframe
					if (strObjectProp.contains("Frame") || strObjectProp.contains("frame")){
						driver.switchTo().frame(driver.findElement(By.xpath(strObjectProp)));
					}
					
					//After
					waitForNoLoadElements();
					break;
                }
				else{
					//After
					waitForNoLoadElements();
				}
			}
		}
		
		if(strCurrentObjName.equalsIgnoreCase("ContactsViewCancel")){
			try{
				EnvironmentSetup.logger.severe("Contact view Sync");
				waitforNoSiebelLoadElements();
			}
			catch(Exception ex){
				
			}
		}
		
		return blnStepResult;
	}
	
	public boolean closes(){
		int intWindowsOpen;
		blnStepResult = true;
		
		intWindowsOpen = driver.getWindowHandles().size();
			
		for (int intWindowCount=0; intWindowCount<=20; intWindowCount++){
			Set<String> strWindowHandles = driver.getWindowHandles();
			if (strWindowHandles.size()==1){
				blnStepResult = true;
				driver.switchTo().window(EnvironmentSetup.strMainWindowHandle);
				break;
			}
			else
				blnStepResult = false;
		}
		
		if(blnStepResult == false){
			Set<String> strWindowHandles = driver.getWindowHandles();
			for (String window : strWindowHandles){
				intWindowsOpen = driver.getWindowHandles().size();
				if (intWindowsOpen == 1){
					blnStepResult = true;
					driver.switchTo().window(EnvironmentSetup.strMainWindowHandle);
					break;
				}
				else{
					if (!EnvironmentSetup.strMainWindowHandle.trim().contains(window)){
						driver.switchTo().window(window);
						driver.close();
						blnStepResult = true;
						break;
					}
				}
			}
		}
		
		if(blnStepResult==true){
			driver.switchTo().window(EnvironmentSetup.strMainWindowHandle);
		}
		
		return blnStepResult;
	}
	
	public boolean opens(){
        blnStepResult = false;
        int WindowHandleCount=1;
        int currentWindowCount = 0;
        //int initialWindowCount = 0;
        
        driver.switchTo().defaultContent();
        
        if(strRequiredWindowHandle== ""){
            for(int iCount=0; iCount<=100;iCount++){
                   Set<String> strWindowHandles = driver.getWindowHandles();
                   WindowHandleCount = strWindowHandles.size();
                   System.out.println(WindowHandleCount);
                   if(WindowHandleCount>EnvironmentSetup.windowsOpen){
                	   for(String window : strWindowHandles){
                           currentWindowCount++;
                         //Store Main Window Handle
                           if(currentWindowCount==1){
                    		   EnvironmentSetup.strMainWindowHandle = window;
                    	   }
                           
                           if(currentWindowCount == WindowHandleCount){
                                  driver.switchTo().window(window);
                                  EnvironmentSetup.logger.info("New Window/Browser Title: " + driver.getTitle());
                                  //Driver.waitForPageToLoad("10000");
                                  blnStepResult = true;
                                  break;
                           }
                	   }
                   }
                   
                   if(blnStepResult==true){
                       break;
                 }
            }	
        }
        else{
        	driver.switchTo().window(strRequiredWindowHandle);
        }
        
       
        return blnStepResult;
	}
	
	public void notBlank(){
		blnStepResult = false;
		try{
			strApplicationData = driver.findElement(By.xpath(strObjectProp)).getText();
		}
		catch(UnhandledAlertException ex){
			handleAlert();
		}
		catch(Exception ex){
			EnvironmentSetup.logger.info("Class - Verification Functions, Method - NotBlank: " + ex.toString());
		}
		
		if(strApplicationData == null || strApplicationData.isEmpty() || strApplicationData ==""){
			try{
				strAlternateApplicationData = driver.findElement(By.xpath(strObjectProp)).getAttribute("value");
			}
			catch(UnhandledAlertException ex){
				handleAlert();
			}
			catch(Exception ex){
				EnvironmentSetup.logger.info("Blank -  Alternate: " + ex.toString());
			}
			
		}
		
	}
	
	public boolean entered(){
		blnStepResult = false;
		
		String strAppData = "";
		try{
			//Specific to Service Cloud - Answer Customer. When email IDs are entered, each email is converted to an Object. 
			if(strObjectProp.toLowerCase().contains("emailrecipients")){
				
				try{
					List<WebElement> elements = driver.findElements(By.xpath(strObjectProp + "//span[@class='recipient']//input[contains(@class,'innerRecipient')]"));
					
					for(int iCount=0; iCount<=10; iCount++){
						elements = driver.findElements(By.xpath(strObjectProp + "//span[@class='recipient']//input[contains(@class,'innerRecipient')]"));
						if (elements.size()>0 || strObjectProp.toLowerCase().contains("bcc")){
							EnvironmentSetup.logger.info(Integer.toString(elements.size()));
							break;
						}
					}
					if (elements.size()>=1){
						for (WebElement elem : elements){
							try{
								strAppData = elem.getAttribute("value").trim() + "," + strAppData;
								EnvironmentSetup.logger.info(strAppData);
								
							}
							catch(Exception ex1){
								EnvironmentSetup.logger.info("Exception in Ans Cust Fields: " + ex1.toString());
							}
						}
						strApplicationData = strAppData;
						EnvironmentSetup.logger.info("Data elem size>=1" + strApplicationData);
					}
					else{
						strApplicationData = "";
						EnvironmentSetup.logger.info("Data elem size<1" + strApplicationData);
					}
				}
				
				catch(Exception ex){
					EnvironmentSetup.logger.info("Capturing Entered Data ans Cust: " + ex.toString());
				}
			}
			else{
				if(strInputData.contains(" >> ")){
					String[] arrInputData = strInputData.split(" >> ");
					strInputData = arrInputData[0].trim();
				}
				
				try{
					strApplicationData = driver.findElement(By.xpath(strObjectProp)).getAttribute("value");
				}
				catch(UnhandledAlertException ex){
					handleAlert();
				}
				catch (Exception ex){
					
				}

				if(strApplicationData == null){
					strApplicationData = "";
				}
			}
		}
		catch(UnhandledAlertException ex){
			handleAlert();
		}
		catch (Exception ex){
			
		}
		//Specific to Password Fields - We clear the Data Fetched from the application after Enter to mask them in the report
		if (strObjectProp.toUpperCase().contains("PASS")){
			strInputData = "";
			strApplicationData = "";
		}
		
		if(strInputData.contains(",")){
			if(strApplicationData.toUpperCase().contains(strInputData.toUpperCase().split(",")[0]) && strApplicationData.toUpperCase().contains(strInputData.toUpperCase().split(",")[1])) {
				blnStepResult = true;
				EnvironmentSetup.logger.info("Entered app data>> " + strApplicationData);
			}
		}
		
		else {
			if ((!strInputData.trim().isEmpty() && strApplicationData.trim().isEmpty()) || (strInputData.trim().isEmpty() && !strApplicationData.trim().isEmpty())){
				blnStepResult = false;
			}
			else if(strApplicationData.toUpperCase().contains(strInputData.toUpperCase()) || strInputData.toUpperCase().contains(strApplicationData.toUpperCase())){		
				blnStepResult = true;
				//EnvironmentSetup.logger.info("Entered app data>> " + strApplicationData);
			}
		}
		return blnStepResult;
	}

	public boolean selected(){
		blnStepResult = false;
		String strTagName = "";
		//Specific to Apttus - Sometimes the column required contains a select tag. Sometimes the select should not be present
		//In this case we need to verify the text in span so we call the displayed method
		//This is done in order to avoid having multiple objects and different steps in the reusable to verify the same field
		//Example: Pricing Cart Discount Type Field
		try{
			strTagName = driver.findElement(By.xpath(strObjectProp)).getTagName();
		}
		catch(NoSuchElementException ex){
			EnvironmentSetup.strGlobalException = ex.toString();
			return false;
		}
		catch(Exception ex){
			EnvironmentSetup.strGlobalException = ex.toString();
			EnvironmentSetup.logger.warning(ex.toString());
		}
		
		if(strTagName.equalsIgnoreCase("select")){
			try{
				Select picklist = new Select(driver.findElement(By.xpath(strObjectProp)));
				strApplicationData = picklist.getFirstSelectedOption().getText().trim();
				EnvironmentSetup.logger.info("Selected app data>> " + strApplicationData);
				if(strApplicationData.equalsIgnoreCase(strInputData)){
					blnStepResult = true;
				}
			}
			catch(UnhandledAlertException ex){
				handleAlert();
			}
			catch (Exception ex) {
				EnvironmentSetup.logger.info("Inside Selected: " + ex.toString());
			}
		}
		else{
			String FieldType= "";
			try{
				if(strObjectProp.contains("@type='radio'")){
					FieldType = "radio";
					isRadioButton = true;
				}
				else{
					FieldType = "other";
					isRadioButton = false;
				}
			}
			catch(Exception ex){
				EnvironmentSetup.logger.warning("Radio " + ex.toString());
			}
			
			if(FieldType.trim().equalsIgnoreCase("radio")){
				try{
					blnStepResult = driver.findElement(By.xpath(strObjectProp)).isSelected();
				}
				catch(Exception ex){
					EnvironmentSetup.logger.warning("Radio " + ex.toString());
				}
			}
			else{
				try{
					blnStepResult = displayed();
				}
				catch(UnhandledAlertException ex){
					handleAlert();
				}
				catch (Exception ex) {
					EnvironmentSetup.logger.info("Inside Selected: " + ex.toString());
				}
			}
			
		}
		
		return blnStepResult;
	}
	
	public boolean chosen(){
		blnStepResult = true;
		String[] arrInputData = null;
		try{
			if(strInputData.contains(",")){
				strApplicationData = driver.findElement(By.xpath(strObjectProp + "//*")).getText();
				arrInputData = strInputData.split(",");
				for(String strInput: arrInputData){
					if(!strApplicationData.equalsIgnoreCase(strInput)){
						blnStepResult = false;
						strStepActualResult = strInput + " not Selected";
					}
				}
			}
			else{
				EnvironmentSetup.logger.info("Selected app data>> " + strApplicationData);
				if(strApplicationData.equalsIgnoreCase(strInputData)){
					blnStepResult = true;
					strStepActualResult = strApplicationData + " Chosen as expected";
				}
				else{
					blnStepResult = false;
				}
			}
			
		}
		catch(UnhandledAlertException ex){
			handleAlert();
		}
		catch (Exception ex) {
			EnvironmentSetup.logger.info("Inside Selected: " + ex.toString());
		}
		
		return blnStepResult;
	}
	
	public boolean checked(){
        blnStepResult = false;
        //This is for Checkboxes that are displayed as images
        if(strObjectProp.contains("img")){
              try{
                    String status = driver.findElement(By.xpath(strObjectProp)).getAttribute("alt");
                    
                    //EnvironmentSetup.logger.info("");
                    if (strInputData.contains("Y") && status.equalsIgnoreCase("Checked")){
                    	blnStepResult = true;
                    }
                    else if (strInputData.contains("Y") && status.equalsIgnoreCase("Not Checked")){
                      blnStepResult = false;
                    }
                  else if (strInputData.contains("N") && status.equalsIgnoreCase("Checked")){
                      blnStepResult = false;
                    }
                    else{
                      blnStepResult = false;
                    }
                    
             }
              catch(UnhandledAlertException ex){
  				handleAlert();
  			}
             catch (NoSuchElementException ex){
            	 EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();            
             }
             catch (StaleElementReferenceException ex){
            	 EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
             }
             catch (Exception ex){
                EnvironmentSetup.logger.info("Checked State before: " + ex.toString());
             }
        }
        else{
               try{
            	   //Siebel Checkboxes contain the aria-checked attribute as opposed to other applications.
            	   String status = "";
                   if(!EnvironmentSetup.strApplicationName.equalsIgnoreCase("Siebel")){
                	   status = driver.findElement(By.xpath(strObjectProp)).getAttribute("checked");
                	   EnvironmentSetup.logger.info("Checked State App Value Before: " + status);
                       if (status == null){
                    	   blnStepResult = false;
                       }
                       else if (status.equalsIgnoreCase("Checked")){
                    	   blnStepResult = true;
                       }
                       else{
                         blnStepResult = true;
                       }
                   }
                   else{
                	   status = driver.findElement(By.xpath(strObjectProp)).getAttribute("aria-checked");
                	   EnvironmentSetup.logger.info("Checked State App Value Before: " + status);
                       if (status.equalsIgnoreCase("false")){
                         blnStepResult = false;
                       }
                       else if (status.equalsIgnoreCase("true")){
                         blnStepResult = true;
                       }
                       else{
                         blnStepResult = true;
                       }
                   }
                    
             }
           catch(UnhandledAlertException ex){
   				handleAlert();
   			}
             catch (NoSuchElementException ex){
            	 EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
             }
             catch (StaleElementReferenceException ex){
            	 EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
             }
             catch (Exception ex){
                EnvironmentSetup.logger.info("Checked State before: " + ex.toString());
             }
        }
        
        return blnStepResult;
       }


	public boolean enabled(){
		
		String objectEnabled = "";
		
		try{
			objectEnabled = driver.findElement(By.xpath(strObjectProp)).getAttribute("aria-disabled");
			EnvironmentSetup.logger.info("Aria Read Only Prop" + objectEnabled);
		}
		catch(NoSuchElementException ex){
			EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
		}
		catch(UnhandledAlertException ex){
			handleAlert();
		}
		catch(Exception ex){
			objectEnabled = "";
		}
		/**
		 * Changed By Tejaswini
		 * 		if(objectEnabled.equalsIgnoreCase("false")){
		 */
		if ((objectEnabled != null) && (objectEnabled.equalsIgnoreCase("false"))){
			blnStepResult = true;
		}
		else{
			blnStepResult = false;
		}
			
		EnvironmentSetup.logger.info("Enabled Status: " + blnStepResult);
		return blnStepResult;
	}
	
	//TODO deprecate after Service cloud scripts are updated - This was used for webtable Verification before a standard object was identified
	public String verifyRelatedList(String strListObject, String strUniqueIdentifier, String strValuestoVerify, String strVerification, String strCurrentDataSet){

		String[] arrVerificationVals = null;
		String[] arrVerificationData = null;
		String strReferenceValue;
		String strApplicationValue="";
		int intColnum = 0;
		
		String strCurrentObject = "";
		boolean blnStepResult = true;
		String strColumnName;
		String strExpectedData;
		String strVerificationStatus ="";
		
		String strDetails="";
		//
		if(strUniqueIdentifier == "FETCH"){
			strUniqueIdentifier = dataParser.getData(strValuestoVerify, strCurrentDataSet);
			
			String strReferenceObject="//tr[*[contains(text(),'" + strUniqueIdentifier + "') or contains(*,'" + strUniqueIdentifier + "')]]";
			EnvironmentSetup.logger.info("Object: " + strCurrentObject);
			
			try{
				strApplicationValue = driver.findElement(By.xpath(strListObject + strReferenceObject)).getText();
				blnStepResult = true;
			}
			catch(UnhandledAlertException ex){
				handleAlert();
			}
			catch (Exception ex){
				strApplicationValue = ex.toString().substring(0, 75);
				blnStepResult = false;
			}
					
			if (strVerification.toUpperCase() == "DISPLAYED" && blnStepResult==true){
				strDetails = strUniqueIdentifier + " displayed in " +  strValuestoVerify + " Actual Value: " + strApplicationValue;
			}
			else if (strVerification.toUpperCase() == "DISPLAYED" && blnStepResult==false){
				strDetails = strUniqueIdentifier + " not displayed in " +  strValuestoVerify + " Actual Value: " + strApplicationValue;
			}
			else if (strVerification.toUpperCase() == "NOTDISPLAYED" && blnStepResult==true){
				strDetails = strUniqueIdentifier + " displayed in " +  strValuestoVerify + " Expected: " + strUniqueIdentifier + " should not be displayed";
			}
			else if (strVerification.toUpperCase() == "NOTDISPLAYED" && blnStepResult==false){
				strDetails = strUniqueIdentifier + " not displayed in " +  strValuestoVerify + " as expected";
			}
		}
		else{
			if(strUniqueIdentifier.contains("[")){
				strUniqueIdentifier = strUniqueIdentifier.replace("[", "");
				strUniqueIdentifier = strUniqueIdentifier.replace("]", "");
				strUniqueIdentifier = strUniqueIdentifier.trim();
			}
			
			if(strValuestoVerify.contains(",")){
				arrVerificationVals = strValuestoVerify.split(",");
				for(int verifyColnum = 0; verifyColnum<arrVerificationVals.length; verifyColnum++){
					String strReferenceObject="//tr[*[contains(*,'" + strUniqueIdentifier + "')]]//*[<colNum>]";
					strReferenceValue = dataParser.getData(arrVerificationVals[verifyColnum], strCurrentDataSet);
					arrVerificationData = strReferenceValue.split(":::");
					//Column Name
					strColumnName = arrVerificationData[0].trim();
					strExpectedData = arrVerificationData[1].trim();
					intColnum = getColumnNumber(strListObject, strColumnName);
					EnvironmentSetup.logger.info("Expected: " + strExpectedData);
					if(intColnum !=0){
						strReferenceObject = strReferenceObject.replace("<colNum>", Integer.toString(intColnum));
						
						if (strExpectedData.toUpperCase().contains("CHECKED")){
							strReferenceObject = strReferenceObject +"/img";
							strApplicationValue = driver.findElement(By.xpath(strListObject + strReferenceObject)).getAttribute("alt");
						}
						else{
							//strCurrentObject = strReferenceObject;
							EnvironmentSetup.logger.info("Object: " + strCurrentObject);
							strApplicationValue = driver.findElement(By.xpath(strListObject + strReferenceObject)).getText();
						}
						
						if (strVerification.toUpperCase() == "DISPLAYED"){
							if(strApplicationValue.trim() != strExpectedData){
								blnStepResult = false;
								strDetails = strDetails + strExpectedData + " not displayed in " +  strColumnName + " Actual Value: " + strApplicationValue + "; ";
							}
						}
						else{
							if(strApplicationValue.trim() == strExpectedData){
								blnStepResult = false;
								strDetails = strDetails + strApplicationValue + " displayed in " +  strColumnName + " Expected Value: " + strExpectedData + "; ";
							}
						}
						
						EnvironmentSetup.logger.info("Step: " + strReferenceValue + " Column number: " +  Integer.toString(intColnum) + " Actual " + strApplicationValue);
					}
					else{
						strDetails = strColumnName + " Column does not exist;" + strDetails;
					}
				}
			}
		}
		if (blnStepResult == false){
			strVerificationStatus = strDetails + ":::Fail";
		}
		else{
			strVerificationStatus = "As Expected:::Pass";
		}
		
		return strVerificationStatus;
	}
	
	//TODO deprecate after Service cloud scripts are updated - This was used to find the column number in the webtable before a standard template was identified
	private int getColumnNumber(String strListObject, String strColumnName){
		
		String strCurrentColName = "";
		int intColNumber = 0;
		
		List<WebElement> columns =  driver.findElement(By.xpath(strListObject + "//tr[@class='headerRow']")).findElements(By.tagName("*"));
		
		for (WebElement column : columns){
			intColNumber = intColNumber + 1;
			strCurrentColName = column.getText();
			if (strCurrentColName.trim().equalsIgnoreCase(strColumnName)){
				return intColNumber;
			}
		}
		
		return 0;
	}
	
	public String verifySaveErrors(){
		String strErrors = "";
		System.out.println("Inside verifySaveErrors");

		strErrors = verifyMainError();
        strErrors = strErrors + " " + verifyFieldLevelErrors();
        
        EnvironmentSetup.logger.info("return Error: " + strErrors);
		System.out.println("Inside verifySaveErrors 2");

        return strErrors;
	}
	
	private String verifyMainError(){
		System.out.println("Inside verifyMainError 1");

		String strErrors = "";
		System.out.println("Inside verifyMainError 2");
		try{
	        List<WebElement> mainErrorMsgs = driver.findElements(By.xpath("//div[@class='pbError']"));
			System.out.println("Inside verifyMainError1");
	        for (WebElement mainErrorMsg : mainErrorMsgs){
	        	if(mainErrorMsg.getText().trim().isEmpty() == false){
	               strErrors = strErrors + " " + mainErrorMsg.getText().trim() + ";";
	               EnvironmentSetup.logger.info("Main Error: " + strErrors);
	        	}
	        }
		}
        catch(Exception ex){
        	EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
        }
        if (!strErrors.trim().isEmpty()){
        	strErrors="Errors on Save: " + strErrors;
        }
		System.out.println("Inside verifyMainError 3");
		
		return strErrors;
	}
	
	private String verifyFieldLevelErrors(){
		System.out.println("Inside verifyFieldLevelErrors 1");
		String strErrors = "";
		String strFieldName = "";
		try{
        	List<WebElement> errors = driver.findElements(By.xpath("//td[contains(div,'Error')]//div[contains(@class,'errorMsg')] "
        			+ "| //td/following-sibling::td[contains(div,'Error')]/preceding-sibling::td/label"));
        	
            for (WebElement error : errors){
            	if(!error.getText().trim().isEmpty()){
            		//strFieldName = error.findElement(By.xpath("/preceding-sibling::td/label")).getText();
            		strErrors = strErrors + " " + strFieldName + ": " + error.getText().trim() + ";";
                    EnvironmentSetup.logger.info("Error: " + strErrors);
            	}
            }
    		System.out.println("Inside verifyFieldLevelErrors 2");
        }
        catch(Exception ex){
        	EnvironmentSetup.strGlobalException = EnvironmentSetup.strGlobalException +  "\t" +ex.toString();
        }
		
		if (!strErrors.trim().isEmpty()){
       		strErrors = "Fields with Error: " + strErrors;
        }
		System.out.println("Inside verifyFieldLevelErrors 3");
		return strErrors;
	}
	
	public static void captureScreenshot(){
		driver = new Augmenter().augment(driver);
		
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			EnvironmentSetup.strCurrentScreenshotLocation = EnvironmentSetup.strScreenshotsPath +"Step_" 
					+ Integer.toString(EnvironmentSetup.intSlNo+1)+ "_" + EnvironmentSetup.strCurrentDataset +".png";
			
			FileUtils.copyFile(screenshot, new File(EnvironmentSetup.strCurrentScreenshotLocation));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void handleUpdateMessage(){
		
		try{
	        driver.findElement(By.xpath("//div[*[contains(text(),'Salesforce.com') and contains(text(),'FLASH')]]/"
	        		+ "following-sibling::div//div[a[img[contains(@src,'Close')]]]//a")).click();
	    }
	    catch (NoSuchElementException ex){
	        //EnvironmentSet
	    	EnvironmentSetup.logger.info("update message not found");
	    }
		
		try{
	        driver.findElement(By.xpath("//form[contains(@action,'Flash')]//input[@value='Continue']")).click();
	    }
	    catch (NoSuchElementException ex){
	        //EnvironmentSetup.logger.info("update message not found");
	    }
		
		try{
	        driver.findElement(By.xpath("//div[h1[contains(text(),'Scheduled Maintenance Notification')]]//a[.='Continue']")).click();
	    }
	    catch (NoSuchElementException ex){
	        //EnvironmentSetup.logger.info("update message not found");
	    }
		
    }
	
	public static void handleAlert(){
		String alertText = "";
		
		captureScreenshotUsingRobot();
		
		try{
			alertText = driver.switchTo().alert().getText();
			ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"Unexpected Dialog", "Unexpected Dialog", alertText,"Warning");
		}
		catch(Exception ex){
			System.out.println("Error Handler Alert");
		}
		
		try{
			driver.switchTo().alert().accept();
		}
		catch(Exception ex){
			
		}
		if(EnvironmentSetup.strModuleName.equalsIgnoreCase("ProjectPhoenix")){
			SiebelRecovery.saveErrors();
		}
		
	}
	
	private static void captureScreenshotUsingRobot(){
		EnvironmentSetup.strCurrentScreenshotLocation = EnvironmentSetup.strScreenshotsPath +"Step_" 
				+ Integer.toString(EnvironmentSetup.intSlNo+1)+ "_Dialog_" + EnvironmentSetup.strCurrentDataset +".png";
		
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(EnvironmentSetup.strCurrentScreenshotLocation));
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	//TODO Rename - Fully Tested and application independent sync function. Replaces all old methods
	//Sync takes care of regular page load as well as all pending Requests to be complete before performing subsequent actions
	public static void waitForNoLoadElements(){
		
	    int timeoutInSeconds = 60;
	    
	    	for (int i = 0; i< timeoutInSeconds; i++) 
            {
	    		try {
			        if (driver instanceof JavascriptExecutor) {
			            JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
			                Object numberOfAjaxConnections = jsDriver.executeScript("return window.openHTTPs");
			                // return should be a number
			                if (numberOfAjaxConnections instanceof Long) {
			                    Long n = (Long)numberOfAjaxConnections;
			                    EnvironmentSetup.logger.info("Number of active calls: " + n);
			                    if (n.longValue() == 0L){
			    	            	break;
			                    }
			                    else{
			                    	
			                    }
			                    	
			                } 
			                else{
			                    // If it's not a number, the page might have been freshly loaded indicating the monkey
			                    // patch is replaced or we haven't yet done the patch.
			                	EnvironmentSetup.logger.info("Monkey Patch " + i);
			                	monkeyPatchXMLHttpRequest();
			                }
			                Thread.sleep(1000);
			            }
		//	        }
			        else {
			        	EnvironmentSetup.logger.info("Web driver: " + driver + " cannot execute javascript");
			        }
		    }
    		catch (InterruptedException e) {
    	    	EnvironmentSetup.logger.info(e.toString());
    	    }
    		catch(UnhandledAlertException ex){
        		handleAlert();
        	}
    	    catch(Exception e){
    	    	EnvironmentSetup.logger.info(e.toString());
    	    }
	    }
	    	
	    //TODO Check if Required
    	try{
    		
    		//Driver.waitForPageToLoad("10000");
    	}
    	catch(Exception ex){
    		EnvironmentSetup.logger.info(ex.toString());
    	}
		
	}
	
	public static void monkeyPatchXMLHttpRequest() {
	    try {
	        if (driver instanceof JavascriptExecutor) {
	            JavascriptExecutor jsDriver = (JavascriptExecutor)driver;
	            Object numberOfAjaxConnections = jsDriver.executeScript("return window.openHTTPs");
	            if (numberOfAjaxConnections instanceof Long) {
	            	Long n = (Long)numberOfAjaxConnections;
	            	EnvironmentSetup.logger.info("Ajax inside " + n);
	                return;
	            }
	            EnvironmentSetup.logger.info("Is It even coming here?");
	            String script = "  (function() {" +
	                "var oldOpen = XMLHttpRequest.prototype.open;" +
	                "window.openHTTPs = 0;" +
	                "XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {" +
	                "window.openHTTPs++;" +
	                "this.addEventListener('readystatechange', function() {" +
	                "if(this.readyState == 4) {" +
	                "window.openHTTPs--;" +
	                "}" +
	                "}, false);" +
	                "oldOpen.call(this, method, url, async, user, pass);" +
	                "}" +
	                "})();";
	            jsDriver.executeScript(script);
	        }
	        else {
	        	EnvironmentSetup.logger.info("Web driver: " + driver + " cannot execute javascript");
	        }
	    }
	    catch(UnhandledAlertException ex){
    		handleAlert();
    	}
	    catch (Exception e) {
	    	EnvironmentSetup.logger.info(e.toString());
	    }
	}
	
	private static void waitforNoSiebelLoadElements(){
		FluentWait<WebDriver> waitA = new FluentWait<WebDriver>(driver);
        
		EnvironmentSetup.logger.severe("Contact view Sync Madness Begins");
		
        waitA.withTimeout(1, TimeUnit.MINUTES);
        waitA.ignoring(NoSuchElementException.class);
        
		try{
			EnvironmentSetup.logger.severe("Contact view Sync Madness Mask Overlay Begin");
			waitA.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='maskoverlay']")));
			EnvironmentSetup.logger.severe("Contact view Sync Madness Mask Overlay End");
		}
		catch(TimeoutException ex){
			EnvironmentSetup.logger.severe("Contact view Sync Madness Mask Overlay Exception");
			EnvironmentSetup.logger.info("Timed Out: " + ex.toString());
			
		}
		catch(ClassCastException ex){
			handleAlert();
			
		}
		catch(UnhandledAlertException ex){
    		handleAlert();
    	}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Siebel Page Load Fluent Wait B: " + ex.toString());
	    }
		try{
			EnvironmentSetup.logger.severe("Contact view Sync");
            waitA.withTimeout(3, TimeUnit.MINUTES);
            waitA.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='mask-img']")));
            EnvironmentSetup.logger.severe("Contact view Sync Madness Mask Img End");
	     }
		catch(TimeoutException ex){
			
			EnvironmentSetup.logger.info("Timed Out: " + ex.toString());
			
		}
		catch(UnhandledAlertException ex){
    		handleAlert();
    	}
		catch(ClassCastException ex){
			handleAlert();
			
		}
		catch (Exception ex){
	    	 EnvironmentSetup.logger.info("Siebel Page Load Fluent Wait A: " + ex.toString());
	     }
		
		EnvironmentSetup.logger.severe("Contact view Sync Madness Ends");
	}
}

