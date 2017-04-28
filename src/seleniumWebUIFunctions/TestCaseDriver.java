package seleniumWebUIFunctions;

import java.sql.*;
import java.util.logging.Level;

import RecoveryScenarios.SalesForceRecovery;
import RecoveryScenarios.SiebelRecovery;
import genericFunctions.*;

@SuppressWarnings("unused")
public class TestCaseDriver {

	KeywordSelectionLibrary executeStep = new KeywordSelectionLibrary();
	public String strMainWindowHandle;
	String strAction = "";
	String strExpected = "";
	String strProceed = "";
	
	public void executeTest(String strAutomationID, String strDataSet){
		
		try{
			EnvironmentSetup.logger.info(strDataSet);
			EnvironmentSetup.logger.info(EnvironmentSetup.strTestCasesPath);
			
			//Fetch all Test Steps - Select * From TestCases Where AutomationID='strAutomationID' Order By Step
			if(EnvironmentSetup.useExcelScripts==false){
				DatabaseFunctions.fetchTestCase(strAutomationID);
			}
			else{
				DatabaseFunctions.FetchData(EnvironmentSetup.strTestCasesPath, "*", EnvironmentSetup.strTestCasesSheetName, "AutomationID", strAutomationID);
			}
			
			EnvironmentSetup.testCaseResultSet.beforeFirst();
			while (EnvironmentSetup.testCaseResultSet.next()){
				EnvironmentSetup.strGlobalException = "";
				EnvironmentSetup.strTestStepstatus = "";
				EnvironmentSetup.strTestCaseStatus = "";
				
				strAction = EnvironmentSetup.testCaseResultSet.getString("Action");
				strExpected = EnvironmentSetup.testCaseResultSet.getString("ExpectedResult");
				EnvironmentSetup.strProceedOnError = EnvironmentSetup.testCaseResultSet.getString("ProceedOnError");
				
				if(EnvironmentSetup.strProceedOnError!=null){
					if(EnvironmentSetup.strProceedOnError.equalsIgnoreCase("No")){
						EnvironmentSetup.blnProceedExec = false;
					}
					else{
						EnvironmentSetup.blnProceedExec = true;
					}
				}				
				else{
					EnvironmentSetup.blnProceedExec = false;
				}
				//Clear Line Item Data Set before proceeding
				EnvironmentSetup.strLineItemDataSet = "";
				
				EnvironmentSetup.strLineItemDataSet = EnvironmentSetup.testCaseResultSet.getString("DataSet");
				EnvironmentSetup.strLineItemsCriteria = EnvironmentSetup.testCaseResultSet.getString("Criteria");
				
				if(EnvironmentSetup.strLineItemDataSet == null){
					EnvironmentSetup.strLineItemDataSet = "";
				}
				if(EnvironmentSetup.strLineItemsCriteria == null){
					EnvironmentSetup.strLineItemsCriteria = "";
				}
				
				//Used along with Line Items and <count>
				//Re-Initialize Global Line Item Count
				EnvironmentSetup.intGlobalLineItemCount = 0;	
				EnvironmentSetup.blnInternalReusable= false;
            	EnvironmentSetup.fetchDataFromXML=false;
            	EnvironmentSetup.XMLDataPresent =false;
            	
            	//Actual Step Execution (Keyword Selection Library)
				executeStep.selectandExecuteKeyword(strAction, strExpected, strDataSet);

				try{
					KeywordExecutionLibrary.driver.switchTo().defaultContent();
				}
				catch(Exception ex){
					
				}
				
				try{
					EnvironmentSetup.blnInternalReusable = false;
					EnvironmentSetup.reusablesResultSet.close();
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info("Reusable Execution Driver: Close Line Items Result Set Exception: " + ex.toString());
				}
				//for(int i=0;i<=100;i++){
					try{
						EnvironmentSetup.lineItemDataConnection.commit();
						EnvironmentSetup.lineItemDataResultSet.close();
						
						/**
						 * If Source is DB - a Global connection is used which should not be closed
						 * For any commits to be visible on Excel - it is mandatory to close this connection 
						*/
						if(EnvironmentSetup.useExcelScripts==true){
							EnvironmentSetup.lineItemDataConnection.close();
						}
						
					}
					catch (Exception ex){//System.out.println(i);
						EnvironmentSetup.logger.info("Reusable Execution Driver: Close Line Items Result Set Exception: " + ex.toString());
					}
				//}				
					
				//Check for Proceed on Error
				if(EnvironmentSetup.blnProceedExec == false && EnvironmentSetup.strTestStepstatus.contains("Fail")){
						EnvironmentSetup.strTestStepstatus = "Fail - Terminated";
						EnvironmentSetup.strTestCaseStatus = "Fail - Terminated";
						if(EnvironmentSetup.strModuleName.equalsIgnoreCase("Apttus")){
							SalesForceRecovery.recoverApttus();
						}
						else if(EnvironmentSetup.strModuleName.equalsIgnoreCase("ProjectPhoenix")){
							SiebelRecovery.saveErrors();
						}
						else if(EnvironmentSetup.strModuleName.equalsIgnoreCase("ServiceCloud")){
							SalesForceRecovery.recoverServiceCloud();
						}
						else if(EnvironmentSetup.strModuleName.equalsIgnoreCase("FinancialForce")){
							SalesForceRecovery.recoverFF();
						}

						break;
				}
				
			}
			
			//Update Test Case Reports
			if(!EnvironmentSetup.strModuleName.equalsIgnoreCase("Apttus")){
				ReportingFunctions.createTestCaseHTMLReport(strAutomationID, strDataSet);
			}
			else{
				//ReportingFunctions.createTestCaseHTMLReportApttus(strDataSet);
				if(!EnvironmentSetup.strComponent.contains("Login") && !EnvironmentSetup.strComponent.contains("Logout")){
					ReportingFunctions.createComponentReportsApttus(EnvironmentSetup.strComponent,strDataSet);
				}
			}
			
			/**
			 * If Source is DB - a Global connection is used which should not be closed
			 * For any commits to be visible on Excel - it is mandatory to close this connection 
			*/
			try{
				if(EnvironmentSetup.useExcelScripts==true){
					EnvironmentSetup.lineItemDataConnection.commit();
					EnvironmentSetup.lineItemDataConnection.close();
				}
			}
			catch (Exception ex){
				
			}
			
			if(EnvironmentSetup.strModuleName.equalsIgnoreCase("ServiceCloud") || EnvironmentSetup.strModuleName.equalsIgnoreCase("RemedyForce")){
				SalesForceRecovery.recoverServiceCloud();
			}
			
			EnvironmentSetup.strCurrentLineItemDataSet = "";
			
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info(ex.toString());
		}
	}
}
