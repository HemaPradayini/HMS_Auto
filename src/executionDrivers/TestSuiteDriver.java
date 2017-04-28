package executionDrivers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import genericFunctions.*;
import seleniumWebUIFunctions.TestCaseDriver;

/**
 *	 
 */
public class TestSuiteDriver { 
	
	/** 
     * Stores value from the DataSet Column in the ModuleName_TestSuite sheet
     * */
	public static String strDataSets = "";

	/** 
     * List of ModuleNames to Be executed
     * */
	static String strModuleNames="";
	/** 
     * If Multiple Modulenames are present, split by "," and store each module in an array for sequential execution
     * */
	static String [] arrModuleNames;
	
	/**
	 * Added by Triveni
     * true indicates that the previous login has failed and the scripts within that session should be Skipped.
     * false indicates successful Login
     * */
	static boolean loginFailed;
	
	/**
	 * Execution Begins
	 * @param args
	 * inputs received from the ".bat" file or the command used to invoke the jar file.
	 * Command Structure:
	 * java -jar "JarFileName.jar" "EmailID(s) for Notification. Separated by ," "ParentModuleName"
	 * Example:
	 * java -jar "AkamaiAutomationFramework.jar" "abc@akamai.com,def@akamai.com" "ServiceCloud"
	 * 
	 */
	public static void temp(String args[])
	{
		System.out.println(System.getProperty("java.runtime.version"));
		try{
			if(args.length!=0){
				System.out.println(args.length);
				
				//Fetch list of emails
				EnvironmentSetup.strEmailList = args[0].trim();
				System.out.println(EnvironmentSetup.strEmailList);
				//Fetch parent Module Name
				EnvironmentSetup.strModuleName = args[1].trim();
				System.out.println(EnvironmentSetup.strModuleName);
				//Initialize Root Folder
				EnvironmentSetup.strRootFolder = ".\\"+ EnvironmentSetup.strModuleName + "\\";
				System.out.println(EnvironmentSetup.strRootFolder);
				EnvironmentSetup.strEnvironmentSheetPath = EnvironmentSetup.strRootFolder + "Environment Details\\EnvironmentDetails.xlsx";
				EnvironmentSetup.strReportFilePath = EnvironmentSetup.strRootFolder + "Reports\\";
				
				/** Added for DB Source*/
				if (args.length==3){
					if(args[0].trim().equalsIgnoreCase("DB")){
						EnvironmentSetup.useExcelScripts = false;
					}
				}
				else{
					EnvironmentSetup.useExcelScripts = true;
				}
				
				//Create .log File - can be moved to the beginning if needed
				try{
					EnvironmentSetup.createLogFile();
				}
				catch(Exception ex){
					//EnvironmentSetup.logger.info("Suite Driver - Create Log File: " + ex.toString());
				}
				
				//Send Execution Email - Initial
				try{
					EnvironmentSetup.sendExecutionEmails("Initial");
				}
				catch(Exception ex){
					EnvironmentSetup.logger.warning("Send Initial Email: " + ex.toString());
				}
				
				//Initialize Global variables
				try{
					System.out.println("Before Global Vars" + EnvironmentSetup.strRootFolder);
					EnvironmentSetup.fetchGlobalVariables();
					strModuleNames = EnvironmentSetup.strModuleName;
					EnvironmentSetup.logger.info(strModuleNames);
				}
				catch(Exception ex){
					EnvironmentSetup.logger.info("Suite Driver - Fetch Global Vars: " + ex.toString());
				}
				
				//Create Report Folder - With Date and time stamp
				try{
					ReportingFunctions.createReportFolder();
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info(ex.toString());
				}
			}
			//Debug Code -  to run various modules from eclipse
			else{
				EnvironmentSetup.strEmailList = "user@company.com";
				EnvironmentSetup.useExcelScripts = true;
				EnvironmentSetup.strModuleName = "Practo";
				System.out.println(EnvironmentSetup.strModuleName);
				EnvironmentSetup.strRootFolder = "E:\\Executable\\"+ EnvironmentSetup.strModuleName + "\\";
				System.out.println(EnvironmentSetup.strRootFolder);
				/*EnvironmentSetup.strEmailList = "sniveda@akamai.com";
				EnvironmentSetup.strModuleName = "Apttus";
				System.out.println(EnvironmentSetup.strModuleName);
				EnvironmentSetup.strRootFolder = "C:\\Executable\\"+ EnvironmentSetup.strModuleName + "\\";
				System.out.println(EnvironmentSetup.strRootFolder);*/
				EnvironmentSetup.strEnvironmentSheetPath = EnvironmentSetup.strRootFolder + "Environment Details\\EnvironmentDetails.xlsx";
				EnvironmentSetup.strReportFilePath = EnvironmentSetup.strRootFolder + "Reports\\";
				System.out.println(EnvironmentSetup.strReportFilePath);
				EnvironmentSetup.killProcess("chromedriver.exe");
				EnvironmentSetup.killProcess("geckodriver.exe");
				try{
					EnvironmentSetup.createLogFile();
				}
				catch(Exception ex){
					EnvironmentSetup.logger.info("Suite Driver - Create Log File: " + ex.toString());
				}
				
				//Initialize Global variables
				try{
					System.out.println("Before Global Vars" + EnvironmentSetup.strRootFolder);
					EnvironmentSetup.fetchGlobalVariables();
					strModuleNames = EnvironmentSetup.strModuleName;
					EnvironmentSetup.logger.info(strModuleNames);
				}
				catch(Exception ex){
					EnvironmentSetup.logger.info("Suite Driver - Fetch Global Vars: " + ex.toString());
				}
				
				
				//Create Report Folder - With Date and time stamp
				try{
					ReportingFunctions.createReportFolder();
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info(ex.toString());
				}
				
			}
		}
		catch(Exception ex){
			
		}

		/*
		 * This section was initially used when we were executing Multiple modules in sequence
		 * Can be used to control which Component is to be executed within a module
		 * Not used currently
		 * Module Name: Siebel
		 * strModuleNames = Test Types: Sanity,Regression
		*/
		if(strModuleNames.contains(",")){
			arrModuleNames = strModuleNames.split(",");
			EnvironmentSetup.logger.log(Level.INFO,"Multiple Modules For Exec, Order: " + strModuleNames);
			
			for (String strModule : arrModuleNames){
				EnvironmentSetup.logger.log(Level.INFO,"Module Being Executed: " + strModule);
				EnvironmentSetup.strCurrentModuleName = strModule;
				try{
					ReportingFunctions.CreateReportFile();
				}

				catch (Exception ex){
					EnvironmentSetup.logger.info(ex.toString());
				}
				
				executeSuite(strModule);
				
				//Re-initialize Global Vars
				EnvironmentSetup.intSlNoStart = 0;
				EnvironmentSetup.intTestSuiteSlNoStart = 0;
				EnvironmentSetup.intSlNo=0;
				EnvironmentSetup.intTestSuiteSlNo=0;
			}
			
		}
		else{
			try{
				EnvironmentSetup.strCurrentModuleName = strModuleNames;
				ReportingFunctions.CreateReportFile();
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info(ex.toString());
			}
			System.out.println(strModuleNames);
			
			//Execute Test Suite
			executeSuite(strModuleNames);
		}
		
	}
	
	
	/** 
     * Used to execute a List of Test Cases (Automation IDs) within a Module based on the ExecutionRequired column in the TestSuite.xlsx
     * The method Queries the TestSuite sheet to fetch a list of rows that have ExecutionRequired = 'Yes'
     * Columns important to this method are - Component (Used For Reporting), AutomationID (Used to Query TestCases) 
     * & DataSet (Used to fetch the Input Data Required for Executions)
     * 
     * All supported formats of DataSet (Uniform Sequential Range, Comma Separated List and Single) are manipulated as needed for each AutomationID
     * 
     * @param strModule - Name of the Module to be executed
    */
	public static void executeSuite(String strModule){
		String[] arrDataSets;
		String strFirstDataSet;
		String strLastDataSet;
		String strDataSetIdentifier = "";
		String strDataSetParam;
		int intStartCount = 0;
		int intEndCount;
				
		TestCaseDriver testCaseDriver = new TestCaseDriver();
		
		try{
			EnvironmentSetup.initialSetup(strModule);
		}

		catch (Exception ex){
			EnvironmentSetup.logger.info(ex.toString());
		}
		
		//Actual Start of Executions
		try{
			//Select * From ModuleName_TestSuite where TestType='Sanity' OR TestType='Regression'
			//Select * From ModuleName_TestSuite where TestScenarioID='TS_AccountManagement'
			//Runs the Query - Select * from ModuleName_TestSuite where ExecutionRequired='Yes'
			//"TestScenarioID", "Sanity,AccountManagement,All"

			/**
			 * Changes to include DB Source 
			*/
			if(EnvironmentSetup.useExcelScripts == false){
				DatabaseFunctions.loadTestSuite();
			}
			else{
				DatabaseFunctions.FetchData(EnvironmentSetup.strTestSuitePath, "*", EnvironmentSetup.strTestSuiteName, "ExecutionRequired", "Yes");
			}
			
			EnvironmentSetup.logger.info(EnvironmentSetup.strTestSuitePath);
			EnvironmentSetup.logger.info(EnvironmentSetup.strTestSuiteName);
			
			EnvironmentSetup.testSuiteResultSet.beforeFirst();
			while (EnvironmentSetup.testSuiteResultSet.next()){
				
				EnvironmentSetup.logger.info("Executing Test Suite");
				//Fetch Values
				EnvironmentSetup.strAutomationID = EnvironmentSetup.testSuiteResultSet.getString("AutomationID").trim();
				EnvironmentSetup.strDataSets = EnvironmentSetup.testSuiteResultSet.getString("DataSet").trim();
				EnvironmentSetup.strComponent  = EnvironmentSetup.testSuiteResultSet.getString("Component").trim();
				EnvironmentSetup.strTestCaseName  = EnvironmentSetup.testSuiteResultSet.getString("TestCaseName").trim();

				//TODO Discuss later - 
				try{
					EnvironmentSetup.strDependentTC = EnvironmentSetup.testSuiteResultSet.getString("TCDependency").trim();
				}
				catch(Exception ex){
					EnvironmentSetup.strDependentTC = "";
				}
				
				//Determine Type of DataSet
				if (EnvironmentSetup.strDataSets.contains(" to ")){
					arrDataSets = EnvironmentSetup.strDataSets.split(" to ");
					strFirstDataSet = arrDataSets[0].trim();	//Oppty-1
					strLastDataSet = arrDataSets[1].trim();		//Oppty-10
					arrDataSets = strFirstDataSet.trim().split("-");
					intStartCount = Integer.parseInt(arrDataSets[1].trim());	//1
					strDataSetIdentifier = arrDataSets[0];	//Oppty-
					arrDataSets = strLastDataSet.trim().split("-");	
					intEndCount = Integer.parseInt(arrDataSets[1].trim()); //10
					for(int iCount = intStartCount; iCount<=intEndCount; iCount++){
						strDataSetParam = Integer.toString(iCount);
						strDataSets = strDataSetIdentifier + "-" + strDataSetParam;
						EnvironmentSetup.strTestCaseStatus = "Not Run";
						EnvironmentSetup.strCurrentDataset = strDataSets;
						EnvironmentSetup.strTestCaseStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());

						//Check if the Last Login has Failed
						checkLastLoginStatus();
						//Check if any Pre-Requisite Tests have Failed
						checkDependentTestCaseStatus();
						
						//Proceed with Test execution ONLY if the Last Login & the Pre-Requisites have gone through successfully
						if(!loginFailed && !EnvironmentSetup.preReqFail){
							EnvironmentSetup.strTestCaseStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
							
							testCaseDriver.executeTest(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset);
							//Update Test Suite Report & HTMLs
							UpdateReports("InFlight");
						}
					}
				}
				else if (EnvironmentSetup.strDataSets.contains(",")){
					arrDataSets = EnvironmentSetup.strDataSets.split(",");
					for (int iCount=0; iCount<arrDataSets.length; iCount++){
						EnvironmentSetup.strTestCaseStatus = "Not Run";
						EnvironmentSetup.strCurrentDataset = arrDataSets[iCount].trim();
						
						//Check if the Last Login has Failed
						checkLastLoginStatus();
						//Check if any Pre-Requisite Tests have Failed
						checkDependentTestCaseStatus();
						
						//Proceed with Test execution ONLY if the Last Login & the Pre-Requisites have gone through successfully
						if(!loginFailed && !EnvironmentSetup.preReqFail){
							EnvironmentSetup.strTestCaseStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
							
							testCaseDriver.executeTest(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset);
							//Update Test Suite Report & HTMLs
							UpdateReports("InFlight");
						}
					}
				}
				else{
					EnvironmentSetup.strCurrentDataset = EnvironmentSetup.strDataSets;
					EnvironmentSetup.strTestCaseStatus = "Not Run";
					
					//Check if the Last Login has Failed
					checkLastLoginStatus();
					//Check if any Pre-Requisite Tests have Failed
					checkDependentTestCaseStatus();
					
					//Proceed with Test execution ONLY if the Last Login & the Pre-Requisites have gone through successfully
					if(!loginFailed && !EnvironmentSetup.preReqFail){
						EnvironmentSetup.strTestCaseStartTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
						testCaseDriver.executeTest(EnvironmentSetup.strAutomationID, EnvironmentSetup.strDataSets);
						//Update Test Suite Report & HTMLs
						UpdateReports("InFlight");
					}
				}
			}			
		}
		
		catch (Exception ex)
		{
			EnvironmentSetup.logger.log(Level.SEVERE,"src.TestSuiteDriver.executeSuite: Exception in Executing Test Suite: " + ex.toString());
			EnvironmentSetup.logger.info("Execute Test Suite: "+ex.toString());
		}
		
		//CleanUp and Tear Down
		try{
			UpdateReports("TearDown");
			EnvironmentSetup.logger.log(Level.INFO,"Clean Up -> EnvironmentSetup.teardown()");
			EnvironmentSetup.teardown();
			EnvironmentSetup.sendExecutionEmails("Final");
		}

		catch (Exception ex){
			EnvironmentSetup.logger.info(ex.toString());
		}
	}
	
	/** 
     * Used to Update the Test Suite Report(xlsx) with the status of the Current Test Case and Create/Update HTML reports and Dashboard.
     * 
    */
	public static void UpdateReports(String SuiteStage){
		try{
			EnvironmentSetup.strTestCaseEndTime = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date());
			ReportingFunctions.getDuration(EnvironmentSetup.strTestCaseStartTime, EnvironmentSetup.strTestCaseEndTime);
			//Check test status
			System.out.println(EnvironmentSetup.strTestCaseStatus);
			
			if(!EnvironmentSetup.strTestCaseStatus.contains("Fail")){
				ReportingFunctions.checkTestCaseStatus(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset);
			}
			
			if(!SuiteStage.equalsIgnoreCase("TearDown")){
				ReportingFunctions.UpdateTestSuiteReport();
			}
			
			if(!EnvironmentSetup.strModuleName.equalsIgnoreCase("Apttus")){
				ReportingFunctions.createTestSuiteHTMLReport();
				ReportingFunctions.createComponentReports();
				ReportingFunctions.createHTMLDashboard();
			}
			else{
				ReportingFunctions.createTestCaseHTMLReportApttus(EnvironmentSetup.strCurrentDataset);
				ReportingFunctions.createHTMLDashboardApttus();
			}
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Update TS Result: " + ex.toString());
		}
		
	}
	
	/** 
     * Used to check whether the previous Login was successful.
     * If not, this method will report all Test Cases within that session as 'Skipped'
    */
	
	public static void checkLastLoginStatus(){
		if(!EnvironmentSetup.strAutomationID.toUpperCase().contains("CHECK_EMAIL") && !EnvironmentSetup.strAutomationID.toUpperCase().contains("SEND_EMAIL") 
				&& !EnvironmentSetup.strAutomationID.toUpperCase().contains("LOGIN") && !EnvironmentSetup.strAutomationID.toUpperCase().contains("REPLY_EMAIL")){
			loginFailed=ReportingFunctions.checkLoginStatus();
		}
		else{
			loginFailed=false;
		}
		if(loginFailed==true){
			EnvironmentSetup.strTestCaseStatus = "Skipped";
			EnvironmentSetup.strTestStepstatus = "Skipped";
			EnvironmentSetup.strActualResult="Login Failed - Skipping Testcase";
			System.out.println(EnvironmentSetup.strAutomationID+" This is skipped testcase");
			ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, "" ,"","Login successful", EnvironmentSetup.strActualResult, EnvironmentSetup.strTestCaseStatus);
			ReportingFunctions.createTestSuiteHTMLReport();
			ReportingFunctions.createComponentReports();
			ReportingFunctions.createHTMLDashboard();
		}
		
	}
	
	/** 
     * Used to check whether Pre-Requisites for the current Test Case has a major checkpoint that has Failed (Fail - Terminated).
     * If a Pre-Requisite has been Terminated, this method updates the Report with a status of 'PreRequisite Fail - Terminated' for the current Test Case
     * 
     * For Apttus - this is controlled by the DataSet. If any Component within a DataSet has a status of Fail - Terminated, The subsequent components
     * that use the same data set should not be executed.
     * 
     * Currently (December 4th,2015), there are no specific checks for other modules since the dependencies have not been defined.
     * This method can be enhanced to use the TCDependency Column in the TestSuite to perform necessary checks
     * 
    */
	
	public static void checkDependentTestCaseStatus(){
		if(EnvironmentSetup.strModuleName.equalsIgnoreCase("Apttus")){
			ReportingFunctions.checkDependentTestStatusApttus(EnvironmentSetup.strCurrentDataset);
		}
		else{
			EnvironmentSetup.preReqFail=false;
		}
		
		if(EnvironmentSetup.preReqFail==true){
			EnvironmentSetup.strTestCaseStatus = "PreRequisite Fail - Terminated";
			EnvironmentSetup.strTestStepstatus = "PreRequisite Fail - Terminated";
			ReportingFunctions.UpdateTestCaseReport(EnvironmentSetup.strAutomationID, EnvironmentSetup.strCurrentDataset, EnvironmentSetup.strReusableName ,"Executing Test Case", "No Failed Pre-Requisites", "Pre-Requisite Failed" ,EnvironmentSetup.strTestStepstatus);
			ReportingFunctions.createComponentReportsApttus(EnvironmentSetup.strComponent,EnvironmentSetup.strCurrentDataset);
			ReportingFunctions.createTestCaseHTMLReportApttus(EnvironmentSetup.strCurrentDataset);
		}
	}
}
