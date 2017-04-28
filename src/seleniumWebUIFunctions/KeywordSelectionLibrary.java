package seleniumWebUIFunctions;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import keywords.SeleniumActions;
import CustomFunctions.DocumentVerificationFunctions;
import CustomFunctions.PMILibrary;
import genericFunctions.DataParser;
import genericFunctions.DatabaseFunctions;
import genericFunctions.EmailFunctions;
import genericFunctions.EnvironmentSetup;
import genericFunctions.FileDownload;
import genericFunctions.ReportingFunctions;

//@SuppressWarnings("deprecation")
public class KeywordSelectionLibrary{

	//Initialize Keyword Exec Library
	KeywordExecutionLibrary executeKeyword;
	VerificationFunctions performVerification;
	DatabaseFunctions dbFunction = new DatabaseFunctions();

	DataParser dataParser = new DataParser();
	EmailFunctions emailFunctions = new EmailFunctions();

	//New vars for TestNG
	boolean testNG = false;
	String Keyword = "";
	String ObjName = "";
	String DataColName="";
	
	private WebDriver driver;
	String DataSet;
	String LineItemID;
	String AutomationID;
	
	//Ignore these lines
	boolean blnElementPresent = false;
	
	ResultSet rsObjectDefns = null;
	ResultSet rsData = null;
	
	/**
	 * Do not use this for TestNG
	 */
	public KeywordSelectionLibrary(){
		executeKeyword = new KeywordExecutionLibrary();
		performVerification = new VerificationFunctions();

	}
	
	public KeywordSelectionLibrary(WebDriver driver, String AutomationID, String DataSet){
		this.setDriver(driver);
		this.AutomationID = AutomationID;
		this.DataSet = DataSet;
		EnvironmentSetup.strCurrentLineItemDataSet = "";
		executeKeyword = new KeywordExecutionLibrary(this.getDriver());
		//performVerification = new VerificationFunctions(this.driver, AutomationID, DataSet);
	}
	
	public KeywordSelectionLibrary(WebDriver driver, String AutomationID, String DataSet, String LineItemID){
		this.setDriver(driver);
		this.AutomationID = AutomationID;
		this.DataSet = DataSet;
		this.LineItemID = LineItemID;
		EnvironmentSetup.strCurrentLineItemDataSet = this.LineItemID;
		executeKeyword = new KeywordExecutionLibrary(this.getDriver());
		//performVerification = new VerificationFunctions(this.driver, AutomationID, DataSet, LineItemID);
	}
	
	/**
	 * 
	 * @param action
	 * @param ObjectName
	 * @param DataColumnName
	 * @param DataSet
	 */
	//public void performAction(SeleniumActions action, String ObjectName, String DataColumnName){
	public void performAction(SeleniumActions action, String DataColumnName, String ObjectName){
		testNG = true;
		Keyword = action.toString();
		ObjName = ObjectName;
		DataColName = DataColumnName;
		if(DataColName.isEmpty()){
			DataColName = ObjName;
		}
		selectandExecuteKeyword("", "", DataSet);
		
	}
	
	public void selectandExecuteKeyword(String currentAction, String currentExpected, String strCurrentDataSet){

		//Initialize/Re-initialize base parameters to ""
		//Column Names ONLY
		String strKeyword="";
		String strDatatoUse = "";
		String strObjectName = "";
		String strVerificationType = "";

		//Contains Action
		String[] arrStepDetails = {"","","",""};
		//Contains Expected
		String[] arrVerificationDetails = {"","",""};

		//Actuals For Action
		String strRequiredData = "";
		String strObjectProperty = "";

		//Actuals for Expected Result
		String strExpectedObject = "";
		String strExpectedData = "";
		String strExpectedVerifyType = "";

		//If R: , Fetch Sheetname and Params
		String strReusableSheet="";
		String strReusableParam = "";

		//TODO Discuss with Reports Initialize Report Vars
		String strVerificationResult = "";
		String[] arrVerificationResult = null;
		String Errors = "";
		String Step="";
		
		if(!testNG){
			//Call data Parser - Action: This identifies what keyword structure is used and what the keyword, datacol name etc are
			arrStepDetails = dataParser.parseActionData(currentAction);
	
			//Extract values from the array - Action
			strKeyword = arrStepDetails[0];
			strObjectName = arrStepDetails[1];
			strDatatoUse = arrStepDetails[2];
			strVerificationType = arrStepDetails[3];
	
			EnvironmentSetup.logger.info(strKeyword);
			EnvironmentSetup.logger.info(strObjectName);
			EnvironmentSetup.logger.info(strDatatoUse);
			EnvironmentSetup.logger.info(strVerificationType);
	
			//Call data Parser - Expected
			arrVerificationDetails = dataParser.parseVerificationData(currentExpected);
	
			//Extract values from the array - Action
			strExpectedObject = arrVerificationDetails[1];
			strExpectedData = arrVerificationDetails[2];
			strExpectedVerifyType = arrVerificationDetails[0];
		}
		else{
			strKeyword = Keyword;
			strObjectName = ObjName;
			strDatatoUse = DataColName;
			Step = "Perform Action: " + Keyword + " Object Name: " + ObjName + " Data Column Name: " + DataColName;
		}

		EnvironmentSetup.strCurrentObjectName = strObjectName;

		switch (strKeyword.toUpperCase()) {
		//R: SheetName (Params)
		//Keyword - R:
		//ObjectName - SheetName
		//Data - Params


		case "R:":
			//For LineItems & Internal Reusables
			if(EnvironmentSetup.strReusableName!=null){
				if(!EnvironmentSetup.strReusableName.isEmpty()){
					EnvironmentSetup.strParentReusableName = EnvironmentSetup.strReusableName;
				}
			}

			//Assign Values 
			strReusableSheet = strObjectName;
			strReusableParam = strDatatoUse;

			//TODO Cover with Recovery Scenario + Appears
			if(strReusableSheet.toUpperCase().contains("LOGIN")){ //&& (strReusableParam.toUpperCase().contains("SALESFORCE"))){
				EnvironmentSetup.strLoginReusable = currentAction;
				EnvironmentSetup.strLoginDataSet = strCurrentDataSet;
			}

			//Purely for Delegated Admin
			if(strReusableSheet.toUpperCase().contains("LOGOUT")){
				if (strReusableParam.toUpperCase().contains("SALESFORCE") == false){
					executeKeyword.switchtoMainWindow();
				}
			}

			//For Reporting Purposes - Assigning the Reusable SheetName (Local) to a Global Variable
			EnvironmentSetup.strReusableName = strReusableSheet;
			EnvironmentSetup.logger.info(strReusableSheet);

			//Initialize ReusableExecutionDriver
			ReusableExecutionDriver reusableExec = new ReusableExecutionDriver();
			//Actual Execution
			//R: LoginToSalesForce All
			//Select * From [LogintoSalesForce$] Where Type Like '%All%' Order By Step
			reusableExec.ExecuteReusable(strReusableSheet, strReusableParam, strCurrentDataSet);

			EnvironmentSetup.strReusableName = "";

			break;

		case "LAUNCH":

			EnvironmentSetup.strApplicationName = strObjectName.trim();
			EnvironmentSetup.logger.info("Current App URL = " + EnvironmentSetup.environmentDetailsMap.get(strObjectName));

			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());

			executeKeyword.LaunchApp(EnvironmentSetup.browserForExec, EnvironmentSetup.environmentDetailsMap.get(strObjectName));
			if(!testNG){
				strVerificationResult = performVerification.verify(strExpectedVerifyType, strExpectedObject, strExpectedData, strCurrentDataSet);
			}

			break;
		case "CLICKIFPRESENT": // Added by Tejaswini - Missing Action
		case "CLICK":
			EnvironmentSetup.strGlobalException = "";
			String strTableObject;
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			//EnvironmentSetup.strUniqueValueField


			if(strObjectProperty.contains("<count>")){
				strObjectProperty = strObjectProperty.replace("<count>", Integer.toString(EnvironmentSetup.intGlobalLineItemCount));
			}
			strObjectProperty = dataParser.BuildObjectWithData(strObjectProperty, strCurrentDataSet);

			if (!strDatatoUse.isEmpty()){
				if(strDatatoUse.contains(",")){
					//strObjectProperty = dataParser.BuildObjectWithData(strObjectProperty, strCurrentDataSet);
					strObjectProperty = dataParser.BuildObjectWithData(strDatatoUse, strObjectProperty, strCurrentDataSet);
				}
				else{
					//
					strRequiredData = dataParser.getData(strDatatoUse, strCurrentDataSet);
					//Abercrombie & Fitch Management Co. >> 1-AUL1
					strRequiredData = strRequiredData.trim();
					if (strRequiredData.contains(" >> ")){
						strTableObject = "//td[.='<data2>']//preceding-sibling::th/a[.='<data1>']";
						String[] arrDataParser = strRequiredData.split(" >> ");
						String strDataToClick = arrDataParser[0].trim();
						String strIdentifier = arrDataParser[1].trim();
						strTableObject =  strTableObject.replace("<data1>", strDataToClick);
						strObjectProperty = "//*[contains(@class,'lookup') or contains(@id,'body') or contains(@id,'Body')]//table/tbody";
						strTableObject =  strObjectProperty + strTableObject.replace("<data2>", strIdentifier);
						strObjectProperty= strTableObject;
						EnvironmentSetup.logger.info(strTableObject);
					}
					else{
						strObjectProperty = dataParser.BuildObjectWithData(strDatatoUse, strObjectProperty, strCurrentDataSet);
						//<ActualColumnName>
						strObjectProperty = dataParser.BuildObjectWithData(strObjectProperty, strCurrentDataSet);

					}
				}
			}
			//TODO Remove step and following condition if there is no impact on Script Execution else change to blnElementPresent = VerificationFunctions.waitForElementPresent(strObjectProperty);
			//blnElementPresent = true;
			VerificationFunctions.waitForNoLoadElements();
			blnElementPresent = true;
			if(blnElementPresent==true){
				EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
				try{
					executeKeyword.click(strObjectProperty);

/*					if(strObjectProperty.toUpperCase().contains("SAVE")){
						Errors = performVerification.verifySaveErrors();
						if (Errors!=null){
							EnvironmentSetup.logger.info("KWSEL: " + Errors);
						}
					}*/
				} catch (Exception e){
					e.printStackTrace();
					System.out.println(e.toString());
				}
				EnvironmentSetup.logger.info("After Click: " + strExpectedObject);
				if(!testNG){
					try{
						if (strExpectedObject.toUpperCase().contains("ERROR")){
							//Error Verification
							strVerificationResult = performVerification.verify("VERIFYERROR", strExpectedObject, strExpectedData, strCurrentDataSet);
							EnvironmentSetup.logger.info("Expected Error Validation: "+ strVerificationResult);
							Errors = "";
						}
						else{
							//Verification
							strVerificationResult = performVerification.verify(strExpectedVerifyType, strExpectedObject, strExpectedData, strCurrentDataSet);
							if(strVerificationResult.toUpperCase().contains("FAIL") && strObjectProperty.toUpperCase().contains("SAVE")){
								Errors = performVerification.verifySaveErrors();
								EnvironmentSetup.logger.info("KWSEL2: " + Errors);
							}
	
							if(!(Errors.trim().isEmpty())){// || !(Errors.trim() == "")){
								EnvironmentSetup.logger.info("Replace ");
								strVerificationResult = strVerificationResult.replace("Pass", "Fail");
								Errors = "";
								VerificationFunctions.captureScreenshot();
							}
						}
						/*else{				
							//VerificationFunctions.captureScreenshot();
							//strVerificationResult = strObjectName + " Not Present:::Fail";
						}*/
	
					}
					catch (Exception ex){
						
						EnvironmentSetup.logger.info("Click Verification: " + ex.toString());
					}
				}
				
			}		
			break;

		case "DOUBLECLICK":
			EnvironmentSetup.strGlobalException = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			strObjectProperty = dataParser.BuildObjectWithData(strObjectProperty, strCurrentDataSet);
			strObjectProperty = dataParser.BuildObjectWithData(strDatatoUse, strObjectProperty, strCurrentDataSet);

				executeKeyword.doubleClick(strObjectProperty);
				if(!testNG){
					//Verification
					strVerificationResult = performVerification.verify(strExpectedVerifyType, strExpectedObject, strExpectedData, strCurrentDataSet);
					strVerificationResult = strObjectName + " is not Present:::Fail";
				}
			break;		

		case "ENTER":
			EnvironmentSetup.strGlobalException = "";

			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			//<ActualColumnName>
			strObjectProperty = dataParser.BuildObjectWithData(strObjectProperty, strCurrentDataSet);
			//<data>
			strObjectProperty = dataParser.BuildObjectWithData(strDatatoUse, strObjectProperty, strCurrentDataSet);

			if (!strDatatoUse.isEmpty()){
				strRequiredData = dataParser.getData(strDatatoUse, strCurrentDataSet);
			}
			else{
				strRequiredData = dataParser.getData(strObjectName, strCurrentDataSet);
			}

			if (strRequiredData.contains(" >> ")){
				strRequiredData = strRequiredData.split(" >> ")[0].trim();
			}

			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());

			executeKeyword.enter(strRequiredData, strObjectProperty);
			//Comment By Tejaswini
			System.out.println("StrRequiredData is :: " + strRequiredData + "\n StrObjectProperty is :: " + strObjectProperty);
			if(!testNG){
				//Verification
				strVerificationResult = performVerification.verify(strExpectedVerifyType, strExpectedObject, strExpectedData, strCurrentDataSet);
			}
			break;

		case "SELECT":
			EnvironmentSetup.strGlobalException = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			strObjectProperty = dataParser.BuildObjectWithData(strObjectProperty, strCurrentDataSet);
			strObjectProperty = dataParser.BuildObjectWithData(strDatatoUse, strObjectProperty, strCurrentDataSet);

			if (!strDatatoUse.isEmpty()){
				strRequiredData = dataParser.getData(strDatatoUse, strCurrentDataSet);
			}
			else{
				strRequiredData = dataParser.getData(strObjectName, strCurrentDataSet);
			}
			try{
				EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());

				executeKeyword.select(strRequiredData, strObjectProperty);
			}
			catch(Exception ex){
				EnvironmentSetup.logger.info("Select Execution: " + ex.toString());
			}
			if(!testNG){
				//Verification
				try{
					strVerificationResult = performVerification.verify(strExpectedVerifyType, strExpectedObject, strExpectedData, strCurrentDataSet);
				}
				catch(Exception ex){
					EnvironmentSetup.logger.info("Select Verification: " + ex.toString());
				}
			}
			break;

		case "STORE":
			EnvironmentSetup.strGlobalException = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			strObjectProperty = dataParser.BuildObjectWithData(strObjectProperty, strCurrentDataSet);
			strObjectProperty = dataParser.BuildObjectWithData(strDatatoUse, strObjectProperty, strCurrentDataSet);
			//blnElementPresent = VerificationFunctions.waitForElementPresent(strObjectProperty);
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			executeKeyword.store(strCurrentDataSet, strDatatoUse, strObjectProperty);
			if(!testNG){
				if (strDatatoUse!=""){
					strVerificationResult = executeKeyword.store(strCurrentDataSet, strDatatoUse, strObjectProperty);
					strVerificationResult = strVerificationResult + " Stored in " + strDatatoUse + ":::Done";
				}
				else{
					strVerificationResult = executeKeyword.store(strCurrentDataSet, strObjectName, strObjectProperty);
					strVerificationResult = strVerificationResult + " Stored in " + strObjectName + ":::Done";
				}
			}

			break;
			
		case "CHECK":
			EnvironmentSetup.strGlobalException = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			strObjectProperty = dataParser.BuildObjectWithData(strObjectProperty, strCurrentDataSet);
			strObjectProperty = dataParser.BuildObjectWithData(strDatatoUse, strObjectProperty, strCurrentDataSet);
			if (!strDatatoUse.isEmpty()){
				strRequiredData = dataParser.getData(strDatatoUse, strCurrentDataSet);

			}
			else{
				strRequiredData = dataParser.getData(strObjectName, strCurrentDataSet);
			}

			EnvironmentSetup.logger.info(strObjectProperty);

			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());

			executeKeyword.check(strRequiredData, strObjectProperty);

			EnvironmentSetup.logger.info("Verification Type: " + strExpectedVerifyType + "Data Set " + strCurrentDataSet);
			if(!testNG){
				//Verification
				strVerificationResult = performVerification.verify(strExpectedVerifyType, strExpectedObject, strExpectedData, strCurrentDataSet);
			}
			break;

		case "COMPOSE":
			EnvironmentSetup.strGlobalException = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			strObjectProperty = dataParser.BuildObjectWithData(strObjectProperty, strCurrentDataSet);
			strObjectProperty = dataParser.BuildObjectWithData(strDatatoUse, strObjectProperty, strCurrentDataSet);
			if (!strDatatoUse.isEmpty()){
				strRequiredData = dataParser.getData(strDatatoUse, strCurrentDataSet);
			}
			else{
				strRequiredData = dataParser.getData(strObjectName, strCurrentDataSet);
			}

			try{
				EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());

				executeKeyword.compose(strObjectProperty, strRequiredData);
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Compose: " + ex.toString());
			}

			break;

		case "VERIFY":
			EnvironmentSetup.strGlobalException = "";
			try{
				EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());

				strVerificationResult = performVerification.verify(strVerificationType, strObjectName, strDatatoUse, strCurrentDataSet);
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Verification: " + ex.toString());
			}

			break;
		
		//TODO deprecate after Service cloud scripts are updated - This was used for webtable Verification before a standard object was identified
		case "VERIFYLIST":
			EnvironmentSetup.strGlobalException = "";
			try{
				EnvironmentSetup.logger.info("In Verify");
				//VerifyList InternalOnly,TaskType,TaskStatus Displayed for AnsCustEmailSubject in ActivityHistoryRelatedList
				//VerifyList TaskType Displayed in ActivityHistoryRelatedList
				//0 -VerifyList
				//1 - InternalOnly,TaskType,TaskStatus
				//2 - Displayed
				//4 - AnsCustEmailSubject
				//6 - ActivityHistoryRelatedList
				
				String [] arrTempCurrentStep = null;

				//Map<String,String> arrCurrentStep = new HashMap<String,String>();

				int intArraySize = 0;
				if (currentAction.contains("  ")){
					currentAction.replace("  ", " ");
				}
				else if (currentAction.contains("   ")){
					currentAction.replace("   ", " ");
				}
				//arrCurrentStep -> 0 - Keyword, 1 - Object, 2 - Data, 3 - VerificationType
				arrTempCurrentStep = currentAction.split(" ");
				intArraySize = arrTempCurrentStep.length;

				strObjectProperty = dataParser.getObjectProperty(arrTempCurrentStep[intArraySize-1]);
				String strValuestoVerify = arrTempCurrentStep[1].trim();
				if(intArraySize < 6){
					strDatatoUse = "FETCH";
				}
				else{
					strDatatoUse = dataParser.getData(arrTempCurrentStep[4], strCurrentDataSet);
				}
				String strVerification = arrTempCurrentStep[2].trim();

				EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
				strVerificationResult = performVerification.verifyRelatedList(strObjectProperty, strDatatoUse, strValuestoVerify, strVerification, strCurrentDataSet);

			}
			catch (Exception ex){
				EnvironmentSetup.logger.info(ex.toString());
			}

			break;

		case "CLOSETAB":
			EnvironmentSetup.strGlobalException = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!testNG){
				strVerificationResult = executeKeyword.closeTab(strObjectProperty);
			}

			break;

		case "CLOSE":
			EnvironmentSetup.strGlobalException = "";
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!testNG){
				strVerificationResult = executeKeyword.closeBrowser();
			}

			break;	

		case "SENDEMAIL":
			EnvironmentSetup.strGlobalException = "";
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!testNG){
				strVerificationResult = emailFunctions.SendEmail(strCurrentDataSet,strDatatoUse);
			}

			break;

		case "REPLYEMAIL":
			EnvironmentSetup.strGlobalException = "";
			String[] arrParam = strObjectName.split(",");
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!testNG){
				strVerificationResult = emailFunctions.ReplyEmail(arrParam[0].trim(), arrParam[1].trim(),arrParam[2].trim(),strCurrentDataSet);
				if(strVerificationResult.contains("Fail")){
					strVerificationResult = strVerificationResult.replace("Fail", "Warning");
				}
			}

			break;

		case "CHECKEMAIL":
			EnvironmentSetup.strGlobalException = "";
			String[] arrParams = strObjectName.split(",");
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!testNG){
			strVerificationResult = emailFunctions.CheckEmail(arrParams[0].trim(), arrParams[1].trim(), strCurrentDataSet);
				if(strVerificationResult.contains("Fail")){
					strVerificationResult = strVerificationResult.replace("Fail", "Warning");
				}
			}
			break;

		case "CHOOSE":
			EnvironmentSetup.strGlobalException = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			strObjectProperty = dataParser.BuildObjectWithData(strObjectProperty, strCurrentDataSet);
			strObjectProperty = dataParser.BuildObjectWithData(strDatatoUse, strObjectProperty, strCurrentDataSet);
			if (!strDatatoUse.isEmpty()){
				strRequiredData = dataParser.getData(strDatatoUse, strCurrentDataSet);
			}
			else{
				strRequiredData = dataParser.getData(strObjectName, strCurrentDataSet);
			}
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(strDatatoUse.equalsIgnoreCase("All")){
				executeKeyword.choose("All", strObjectProperty);
			}
			else{
				executeKeyword.choose(strRequiredData, strObjectProperty);
			}
			

			break;

		case "ACCEPT":
		case "HANDLE":
			EnvironmentSetup.strGlobalException = "";
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!testNG){
				strVerificationResult = executeKeyword.handleDialog();
			}
			else{
				executeKeyword.handleDialog();
			}
			break;

		case "DISMISS":
			EnvironmentSetup.strGlobalException = "";
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!testNG){
				strVerificationResult = executeKeyword.dismissDialog();
			}
			else{
				executeKeyword.dismissDialog();
			}
			break;

		case "ATTACH":
			EnvironmentSetup.strGlobalException = "";
			EnvironmentSetup.logger.info(strObjectName);
			String strFilePath = dataParser.getData(strObjectName, strCurrentDataSet);
			EnvironmentSetup.logger.info(strFilePath);

			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!testNG){
				strVerificationResult = executeKeyword.attachFile(strFilePath);
			}
			break;

		case "CAPTURESCREENSHOT":
			EnvironmentSetup.strGlobalException = "";
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!testNG){
				strVerificationResult = KeywordExecutionLibrary.captureScreenshot(strObjectName);
			}
			break;

		case "DOWNLOAD":
			//
			EnvironmentSetup.strGlobalException = "";
			if (strDatatoUse!=""){
				strRequiredData = dataParser.getData(strDatatoUse, strCurrentDataSet);
			}
			else{
				strRequiredData = dataParser.getData(strObjectName, strCurrentDataSet);
			}
			if(!testNG){
				strVerificationResult = FileDownload.getFileURL(strRequiredData);
			}

			break;
					
		//TODO Remove - used for Preview download - using ROBOT
		case "DOWNLOADPDF":
			EnvironmentSetup.strGlobalException = "";
			if (strDatatoUse!=""){
				strRequiredData = dataParser.getData(strDatatoUse, strCurrentDataSet);
			}
			else{
				strRequiredData = dataParser.getData(strObjectName, strCurrentDataSet);
			}

			strRequiredData = EnvironmentSetup.strDownloadsPath + EnvironmentSetup.strCurrentDataset + "_" + strRequiredData + ".pdf";
			if(!testNG){
				strVerificationResult = executeKeyword.downloadPDF(strRequiredData);
			}

			break;

		case "CLOSETABS":
			EnvironmentSetup.strGlobalException = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!testNG){
				strVerificationResult = executeKeyword.closeAllServiceCloudTabs(strObjectProperty);
			}

			break;

		case "CLEAR":
			EnvironmentSetup.strGlobalException = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			executeKeyword.clearInputField(strObjectProperty);
			if(!testNG){
				//Verification
				strVerificationResult = performVerification.verify(strExpectedVerifyType, strExpectedObject, strExpectedData, strCurrentDataSet);
			}

			break;
		
		case "GOTOURL":
			String TargetURL = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			EnvironmentSetup.strTestStepStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			if(!strObjectProperty.isEmpty()){
				TargetURL = executeKeyword.getRequiredLink(strObjectProperty);
				if(!(TargetURL.contains("http:") || TargetURL.contains("https:"))){
					TargetURL = executeKeyword.buildURL(TargetURL);
				}
				executeKeyword.goToURL(TargetURL);
			}
			else{
				EnvironmentSetup.logger.info("TO DO");
			}
			if(!testNG){
				strVerificationResult = performVerification.verify(strExpectedVerifyType, strExpectedObject, strExpectedData, strCurrentDataSet);
			}
//Added for MoveToElement Action - HoverOver			
		case "HOVEROVER":
			System.out.println("Inside HoverOver");
			EnvironmentSetup.strGlobalException = "";
			strObjectProperty = dataParser.getObjectProperty(strObjectName);
			executeKeyword.hoverOver(strObjectProperty);
			break;
		
		default:
			EnvironmentSetup.logger.warning(strKeyword + " Does not Exist in the List, Check and correct Test Step");
			if(!testNG){
				strVerificationResult = "Keyword Not Present - Check Test Step:::Warning";
			}

			break;
		}
		
		//strVerificationResult samples
		//Step done:::Pass
		//Step done:::Fail
		if(testNG){
			//Report Action
			ReportingFunctions.updateTestNGStepReport(AutomationID, DataSet, "Action", Step, "Done", "Done");
			
		}
		else{
			if (strKeyword.toUpperCase().contains("R:") == false){
				EnvironmentSetup.logger.info(strReusableSheet);
				try{
					arrVerificationResult = strVerificationResult.trim().split(":::");
					if(Errors!="" || !Errors.isEmpty()){
						EnvironmentSetup.strActualResult = arrVerificationResult[0].trim() + Errors;
						Errors = "";
					}
					else{
						EnvironmentSetup.strActualResult = arrVerificationResult[0].trim();
					}
	
					EnvironmentSetup.strTestStepstatus = arrVerificationResult[1].trim();
				}
				catch(Exception ex){
					EnvironmentSetup.logger.info("Key Select-Verification Result: " + ex.toString());
				}
	
				if (EnvironmentSetup.strTestStepstatus.equalsIgnoreCase("Fail")){
					if(!(strExpectedVerifyType.equalsIgnoreCase("NotAppear") && (strExpectedObject.equalsIgnoreCase("Dialog")))){
						KeywordExecutionLibrary.captureScreenshot("Error");
					}
					EnvironmentSetup.blnTestCaseStatus = false;
					EnvironmentSetup.strTestCaseStatus = "Fail";
				}
	
				if (EnvironmentSetup.strTestStepstatus.contains("Fail")){
					System.out.println(EnvironmentSetup.strTestStepstatus + " " + EnvironmentSetup.blnProceedExec);
					if(EnvironmentSetup.blnProceedExec==false){
						if(!(strExpectedVerifyType.equalsIgnoreCase("NotAppear") && (strExpectedObject.equalsIgnoreCase("Dialog")))){
							KeywordExecutionLibrary.captureScreenshot("Error");
							EnvironmentSetup.blnProceedExec = false;
						}
	
						EnvironmentSetup.strTestStepstatus = "Fail - Terminated";
						EnvironmentSetup.strTestCaseStatus = "Fail - Terminated";
	
						if(!EnvironmentSetup.strGlobalException.isEmpty()){
							EnvironmentSetup.strActualResult = "Error Details: " + EnvironmentSetup.strGlobalException;
						}
	
					}
				}
				else{
					EnvironmentSetup.blnProceedExec= true;
				}
	
				try{
					EnvironmentSetup.strTestStepEndTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
					ReportingFunctions.getDuration(EnvironmentSetup.strTestStepStartTime, EnvironmentSetup.strTestStepEndTime);
					System.out.println(EnvironmentSetup.strTestStepstatus);
					ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, strCurrentDataSet, EnvironmentSetup.strReusableName ,currentAction, currentExpected, EnvironmentSetup.strActualResult,EnvironmentSetup.strTestStepstatus);
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info("Test Step Report: " + ex.toString());
				}
	
				//Clear Value in UniqueValueField before executing the next Step
				EnvironmentSetup.strUniqueValueField = "";
	
			}
		}
	}
	public String getAutomationID(){
		return this.AutomationID;
	}	
	public String getDataSet(){
		return this.DataSet;
	}
	
	public String getLineItemId(){
		return this.LineItemID;
	}
	
	public void setLineItemId(String lineItemId){
		this.LineItemID = lineItemId;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}