package genericFunctions;

import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import genericFunctions.EnvironmentSetup.*;


/**
 *
 */
@SuppressWarnings("unused")
public class ReportingFunctions {
	
	Connection currentConnection = null;
	Statement QueryStatement = null;
	String strInsertQuery = "";
	
	final public static StringBuilder testCase = new StringBuilder(65536);
	final public static StringBuilder testSuite = new StringBuilder(65536);
	final public static StringBuilder componentReport = new StringBuilder(65536);
	final public static StringBuilder dashboard = new StringBuilder(65536);
	
	
	/**
	 * 
	 */
	public static void updateTestNGStepReport(String AutomationID, String CurrentDataSet, String StepType ,String Step, String ActualResult, String Status){
		
		EnvironmentSetup.intSlNo = EnvironmentSetup.intSlNo + EnvironmentSetup.intSlNoStart + 1;
		String strSlNo = Integer.toString(EnvironmentSetup.intSlNo);
		
		ConnectToReportFile();
		
		Statement QueryStatement = null;
		try {
			QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
		} catch (Exception e) {
			
			EnvironmentSetup.logger.info("Create Statement Update Test Case: " + e.toString());
		}
		
		String strInsertQuery = "";
		//SlNo	ReportID	AutomationID	DataSet	LineItemID	StepType	Step	ActualResult	Status	StartTime	EndTime	Duration	ReferenceFiles
		strInsertQuery = "INSERT INTO [TestStepResults$] values("
				+"'" + strSlNo + "',"
				+"'Report_" + EnvironmentSetup.randNumForReport + "'," 
				+"'" + AutomationID + "',"
				+"'" + CurrentDataSet + "',"
				+"'" + EnvironmentSetup.strCurrentLineItemDataSet + "',"
				+"'" + StepType + "',"
				+"'" + Step + "',"
				+"'" + ActualResult + "',"
				+"'" + Status + "',"
				+"'" + EnvironmentSetup.strTestStepStartTime + "',"
				+"'" + EnvironmentSetup.strTestStepEndTime + "',"
				+"'" + EnvironmentSetup.strDuration + "',"
				+"'" + EnvironmentSetup.strCurrentScreenshotLocation + "'"
				+")";
		//EnvironmentSetup.strTestStepEndTime + "','" + EnvironmentSetup.strDuration + "','" + EnvironmentSetup.strCurrentScreenshotLocation + "')";
		
		try{
			QueryStatement.executeUpdate(strInsertQuery);
			
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Update Test Step Report Execute: " + ex.toString());
		}
		
		try{
			
			EnvironmentSetup.testReportConnection.commit();
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Update Test Suite Report Commit : " + ex.toString());
		}
		
        try{
        	EnvironmentSetup.testReportConnection.close();
	    }
	    catch(Exception ex){
	    	EnvironmentSetup.logger.info("Closing Report Connection - Test Suite: " + ex.toString());
	    }
		
        try{
        	EnvironmentSetup.testReportConnection.close();
	    }
	    catch(Exception ex){
	    	EnvironmentSetup.logger.info("Closing Report Connection - Test Suite: " + ex.toString());
	    }
		
	}
	
	/**
	 * 
	 */
	public static void UpdateTestSuiteReport(){
    	
		EnvironmentSetup.intTestSuiteSlNo = EnvironmentSetup.intTestSuiteSlNo + EnvironmentSetup.intSlNoStart + 1;
		String strSlNo = Integer.toString(EnvironmentSetup.intTestSuiteSlNo);
		
		ConnectToReportFile();
		Statement QueryStatement = null;
		try {
			QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
		} catch (Exception e) {
			
			EnvironmentSetup.logger.info("Create Statement Update Test Case: " + e.toString());
		}
		
		String strInsertQuery = "";
		
		System.out.println(EnvironmentSetup.strTestCaseStatus);
		
		strInsertQuery = "INSERT INTO [" + EnvironmentSetup.strCurrentModuleName + "_TestSuiteResults$] "
			+ "values('" + strSlNo + "','" + EnvironmentSetup.strComponent + "','" + EnvironmentSetup.strTestCaseName + "','" 
				+ EnvironmentSetup.strAutomationID + "','" + EnvironmentSetup.strCurrentDataset + "','" + 
					EnvironmentSetup.strTestCaseStatus + "','" + EnvironmentSetup.strTestCaseStartTime + "','" + EnvironmentSetup.strTestCaseEndTime 
					+ "','" + EnvironmentSetup.strDuration + "')";
	
		
		try{
			QueryStatement.executeUpdate(strInsertQuery);
			
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Update Test Suite Report Execute: " + ex.toString());
		}
		
		try{
			
			EnvironmentSetup.testReportConnection.commit();
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Update Test Suite Report Commit : " + ex.toString());
		}
		
        try{
        	EnvironmentSetup.testReportConnection.close();
	    }
	    catch(Exception ex){
	    	EnvironmentSetup.logger.info("Closing Report Connection - Test Suite: " + ex.toString());
	    }
		
        try{
        	EnvironmentSetup.testReportConnection.close();
	    }
	    catch(Exception ex){
	    	EnvironmentSetup.logger.info("Closing Report Connection - Test Suite: " + ex.toString());
	    }

	}
	
	/**
	 * Connects to the Report File for the current execution.
	 * Executes an INSERT Statement to update the TestStepReport
	 */
    public static void UpdateTestCaseReport(String AutomationID, String CurrentDataSet, String ReusableName ,String Action, String ExpectedResult, String ActualResult, String Status){
		//SlNo, TimeStamp
    	EnvironmentSetup.intSlNo = EnvironmentSetup.intSlNo + EnvironmentSetup.intSlNoStart + 1;
		String strSlNo = Integer.toString(EnvironmentSetup.intSlNo);
		String strTimeStamp = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date());
		String strInsertQuery = "";
		
		ConnectToReportFile();
		
		// Replace ' with nothing - impact on SQL query
		if (ActualResult.contains("'")){
			ActualResult = ActualResult.replace("'", "");
		}
		
		Statement QueryStatement = null;
		
		try {
			QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (Exception e) {
			
			EnvironmentSetup.logger.info("Create Statement Update Test Case: " + e.toString());
		}
		
		if(EnvironmentSetup.strCurrentScreenshotLocation!=""){
			EnvironmentSetup.strCurrentScreenshotLocation = "=Hyperlink(" + "\"" + EnvironmentSetup.strCurrentScreenshotLocation + "\"" + ")";
		}
		
		strInsertQuery =
				"INSERT INTO [TestCaseResults$]" + "(SlNo,HostName,Browser,AutomationID,DataSet,TestCaseStatus,TestCaseStartTime,TestCaseEndTime,TestDuration) " +  "VALUES ('" 
				+ strSlNo + "','" +  EnvironmentSetup.URLforExec + "','" + EnvironmentSetup.browserForExec + "','" +  AutomationID + "','" + CurrentDataSet + "','" + Status + "','" 
				+ EnvironmentSetup.strTestStepStartTime + "','" + EnvironmentSetup.strTestStepEndTime+ "','" + EnvironmentSetup.strDuration+"')";
		
		/*strInsertQuery = "INSERT INTO [TestCaseResults$] "
				+ "values('" + strSlNo + "','" + AutomationID + "','" + EnvironmentSetup.strCurrentDataset + "','" + EnvironmentSetup.strCurrentLineItemDataSet + "','" + ReusableName + "','"
					+ Action + "','" + ExpectedResult + "','" + ActualResult + "','" + Status + "','" + EnvironmentSetup.strTestStepStartTime + "','" 
						+ EnvironmentSetup.strTestStepEndTime + "','" + EnvironmentSetup.strDuration + "','" + EnvironmentSetup.strCurrentScreenshotLocation + "')";*/
		System.out.println("Report Query is :: " + strInsertQuery);
		String delimiter = " :: ";
		String testCaseReportRecord = EnvironmentSetup.URLforExec + delimiter + EnvironmentSetup.browserForExec + delimiter+ AutomationID + delimiter +CurrentDataSet +delimiter + Status;
		CommonUtilities.WriteTestCaseReport(testCaseReportRecord);
		System.out.println("");
	    try{
			QueryStatement.executeUpdate(strInsertQuery);
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Update Test Case Report Execute: " + ex.toString());
		}
	    		
		try{
			
			EnvironmentSetup.testReportConnection.commit();
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Update Test Case Report Commit : " + ex.toString());
		}
		
		try{
			EnvironmentSetup.testReportConnection.close();
	    }
	    catch(Exception ex){
	    	EnvironmentSetup.logger.info("Closing Report Connection - Test Step: " + ex.toString());
	    }
		
		if (Status.toUpperCase().contains("FAIL")){
			UpdateFailedStepsReport(strSlNo, AutomationID, CurrentDataSet, ReusableName, Action, ExpectedResult, ActualResult, Status);
		}
		
		EnvironmentSetup.strCurrentScreenshotLocation = "";
	}
    
    /**
	 * Connects to the Report File for the current execution.
	 * Executes an INSERT Statement to update the FailedSteps Report
	 */
    public static void UpdateFailedStepsReport(String slNo, String AutomationID, String CurrentDataSet, String ReusableName ,String Action, String ExpectedResult, String ActualResult, String Status){
		String strSlNo = Integer.toString(EnvironmentSetup.intSlNo);
		String strTimeStamp = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date());
		String strInsertQuery = "";
		
		if (ActualResult.contains("'")){
			ActualResult.replace("'", "");
		}
		
		ConnectToReportFile();
		
		Statement QueryStatement = null;
		
		try {
			QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (Exception e) {
			
			EnvironmentSetup.logger.info("Create Statement Update Test Case: " + e.toString());
		}
		
		strInsertQuery = "INSERT INTO [" + EnvironmentSetup.strCurrentModuleName + "_FailedSteps$] "
				+ "values('" + strSlNo + "','" + AutomationID + "','" + EnvironmentSetup.strCurrentDataset + "','" + EnvironmentSetup.strCurrentLineItemDataSet + "','" + ReusableName + "','"
					+ Action + "','" + ExpectedResult + "','" + ActualResult + "','" + Status + "','" + EnvironmentSetup.strTestStepStartTime + "','" 
						+ EnvironmentSetup.strTestStepEndTime + "','" + EnvironmentSetup.strDuration + "','" + EnvironmentSetup.strCurrentScreenshotLocation + "')";
	    try{
			QueryStatement.executeUpdate(strInsertQuery);
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Update Failed Steps Report Execute: " + ex.toString());
		}
	    		
		try{
			
			EnvironmentSetup.testReportConnection.commit();
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Update Failed Steps Report Commit : " + ex.toString());
		}
		
		try{
			EnvironmentSetup.testReportConnection.close();
	    }
	    catch(Exception ex){
	    	EnvironmentSetup.logger.info("Closing Report Connection - Failed Step: " + ex.toString());
	    }
		
	}
    
    /**
	 * Commit and Close Report Connection
	 */
    public static void CloseReportConnection(){
    	try{
			
			EnvironmentSetup.testReportConnection.commit();
		}
		catch (Exception ex){
			//EnvironmentSetup.logger.info("Update Failed Steps Report Commit : " + ex.toString());
		}
		
		try{
			EnvironmentSetup.testReportConnection.close();
	    }
	    catch(Exception ex){
	    	//EnvironmentSetup.logger.info("Closing Report Connection - Failed Step: " + ex.toString());
	    }
    }
    
    /**
	 * Open Connection to Report File
	 */
    public static void ConnectToReportFile(){
    	
    	CloseReportConnection();
    	
		try{
    		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	}
    	catch (Exception ex){
    		EnvironmentSetup.logger.info("Class for name Reports:" + ex.toString());
    	}
		try{
			EnvironmentSetup.testReportConnection = DriverManager.getConnection("jdbc:odbc:Driver="
					+ "{Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
						+ EnvironmentSetup.strCurrentReportFile + ";readOnly=false");
			
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Test Report Connection: " + ex.toString());
		}
    }
	
    /**
	 * 
	 */
    public static void createReportFolder(){
    	
    	if(EnvironmentSetup.installationType.equalsIgnoreCase("testng_code")){
    		try{
    	    	
    			//EnvironmentSetup.strCurrentReportFile
    			//String strFolder = ".\\Reports\\Report_" + new SimpleDateFormat("yyyy_MMM_dd_HH_mm").format(new Date());
    			String strFolder = "C:\\InstaHmsBatch\\Reports\\Report_" + new SimpleDateFormat("yyyy_MMM_dd_HH_mm").format(new Date());
    			File dir = new File(strFolder);
    			boolean done = dir.mkdir();
    			EnvironmentSetup.strCurrentReportFilePath = strFolder; 
    			EnvironmentSetup.logger.info("Create Main Report Directory: " + done);
    			//String cssStylesPath = ".\\Reports\\style.css";
    			String cssStylesPath = "C:\\InstaHmsBatch\\Reports\\style.css";
    			String stylesDestinationPath = strFolder + "\\style.css";
    			
    			//Copy Style.css
    			try{
    				FileInputStream fileInputStream = new FileInputStream(cssStylesPath); 
    		    	FileOutputStream fileOutputStream = new FileOutputStream(new File(stylesDestinationPath));
    		    	FileChannel source = fileInputStream.getChannel();
    		    	FileChannel destination = fileOutputStream.getChannel();
    		    	
    		    	destination.transferFrom(source, 0, source.size());
    		    	
    		    	fileInputStream.close();
    		    	fileOutputStream.close();
    			}
    			catch (Exception ex){
    				System.out.println("Exception Copying styles: " + ex.toString());
    			}
    			
    			//Create Screenshots Folder
    	    	String strScreenshotsFolder = strFolder + "\\Screenshots";
    	    	EnvironmentSetup.strScreenshotsPath = strScreenshotsFolder + "\\";
    	    	
    	    	dir = new File(strScreenshotsFolder);
    			done = dir.mkdir();
    			
    	    	
    	    	//Create Downloaded Files Folder
    	    	String strDownloadsFolder = strFolder + "\\Downloaded Files"  + "\\";
    	    	EnvironmentSetup.strDownloadsPath = strDownloadsFolder;
    	    	
    	    	dir = new File(strDownloadsFolder);
    			done = dir.mkdir();
    			
    	    	//Create Test Case Reports Folder
    	    	String strTestCaseReportsFolder = strFolder + "\\TestCase Reports"  + "\\";
    	    	
    	    	dir = new File(strTestCaseReportsFolder);
    			done = dir.mkdir();
    	    	
    			//Create Component Reports Folder
    	    	String strComponentReportsFolder = strFolder + "\\Component Reports"  + "\\";
    	    	
    	    	dir = new File(strComponentReportsFolder);
    			done = dir.mkdir();
    	    	
    			
    			EnvironmentSetup.logger.info("Create Module Report Directory: " + done);
    			
    			String strReportFilePath = ".\\Reports\\Practo_ReportTemplate.xlsx";
    	    	
    	    	EnvironmentSetup.strCurrentReportFileName = EnvironmentSetup.strCurrentModuleName + "_" + EnvironmentSetup.environmentForexec + "_" 
    	    			+ EnvironmentSetup.browserForExec + new SimpleDateFormat("yyyy_MM_dd-HH_mm'.xlsx'").format(new Date());
    	    	//+"_ExecutionReport_"
    	    	
    	    	FileInputStream fileInputStream = new FileInputStream(strReportFilePath);
    	    	
    	    	EnvironmentSetup.strCurrentReportFile = EnvironmentSetup.strCurrentReportFilePath + "\\" + EnvironmentSetup.strCurrentReportFileName; 
    	    	FileOutputStream fileOutputStream = new FileOutputStream(new File(EnvironmentSetup.strCurrentReportFile));
    	    	EnvironmentSetup.logger.info(EnvironmentSetup.strCurrentReportFile);
    	    	FileChannel source = fileInputStream.getChannel();
    	    	FileChannel destination = fileOutputStream.getChannel();
    	    	
    	    	destination.transferFrom(source, 0, source.size());
    	    	
    	    	fileInputStream.close();
    	    	fileOutputStream.close();
    			
    			
        	}
        	catch (Exception ex){
        		EnvironmentSetup.logger.info(ex.toString());
        	}
    	}
    	else{
    		try{
    	    	String strFolder = EnvironmentSetup.strReportFilePath + "ExecutionReports_" + new SimpleDateFormat("yyyy_MMM_dd_HH_mm").format(new Date());
    			File dir = new File(strFolder);
    			EnvironmentSetup.strMainReportFolderPath = strFolder;
    			
    			boolean done = dir.mkdir();
    			EnvironmentSetup.logger.info("Create Main Report Directory: " + done);
    			
        	}
        	catch (Exception ex){
        		EnvironmentSetup.logger.info(ex.toString());
        	}
    	}
		
    }
    
    /**
	 * 
	 */
    private static void createModuleReportFolder() throws Exception{
    	boolean done;
    	File dir;
    	
    	String strFolder = EnvironmentSetup.strMainReportFolderPath + "\\" + EnvironmentSetup.strCurrentModuleName;
    	EnvironmentSetup.strCurrentReportFilePath = strFolder;
    	
    	//Create Module Report Folder
    	EnvironmentSetup.logger.info("Module report Folder: " + EnvironmentSetup.strCurrentReportFilePath);
		dir = new File(strFolder);
		done = dir.mkdir();
		String cssStylesPath = EnvironmentSetup.strRootFolder + "Reports\\style.css";
		String stylesDestinationPath = EnvironmentSetup.strCurrentReportFilePath + "\\style.css";
		//Copy Style.css
		try{
			FileInputStream fileInputStream = new FileInputStream(cssStylesPath); 
	    	FileOutputStream fileOutputStream = new FileOutputStream(new File(stylesDestinationPath));
	    	FileChannel source = fileInputStream.getChannel();
	    	FileChannel destination = fileOutputStream.getChannel();
	    	
	    	destination.transferFrom(source, 0, source.size());
	    	
	    	fileInputStream.close();
	    	fileOutputStream.close();
		}
		catch (Exception ex){
			System.out.println("Exception Copying styles: " + ex.toString());
		}
		
    	//Create Screenshots Folder
    	String strScreenshotsFolder = strFolder + "\\Screenshots";
    	EnvironmentSetup.strScreenshotsPath = strScreenshotsFolder + "\\";
    	
    	dir = new File(strScreenshotsFolder);
		done = dir.mkdir();
		
    	
    	//Create Downloaded Files Folder
    	String strDownloadsFolder = strFolder + "\\Downloaded Files"  + "\\";
    	EnvironmentSetup.strDownloadsPath = strDownloadsFolder;
    	
    	dir = new File(strDownloadsFolder);
		done = dir.mkdir();
		
    	//Create Test Case Reports Folder
    	String strTestCaseReportsFolder = strFolder + "\\TestCase Reports"  + "\\";
    	
    	dir = new File(strTestCaseReportsFolder);
		done = dir.mkdir();
    	
		//Create Component Reports Folder
    	String strComponentReportsFolder = strFolder + "\\Component Reports"  + "\\";
    	
    	dir = new File(strComponentReportsFolder);
		done = dir.mkdir();
    	
		
		EnvironmentSetup.logger.info("Create Module Report Directory: " + done);
		
    }
    
    /**
	 * 
	 */
    public static void CreateReportFile() throws Exception{
    	createModuleReportFolder();
    	EnvironmentSetup.logger.info(EnvironmentSetup.strCurrentModuleName);
    	String strReportFilePath = EnvironmentSetup.strReportFilePath + EnvironmentSetup.strCurrentModuleName + "_ReportTemplate.xlsx";
    	
    	EnvironmentSetup.strCurrentReportFileName = EnvironmentSetup.strCurrentModuleName + "_" + EnvironmentSetup.environmentForexec + "_" 
    			+ EnvironmentSetup.browserForExec + new SimpleDateFormat("yyyy_MM_dd-HH_mm'.xlsx'").format(new Date());
    	//+"_ExecutionReport_"
    	
    	FileInputStream fileInputStream = new FileInputStream(strReportFilePath);
    	
    	EnvironmentSetup.strCurrentReportFile = EnvironmentSetup.strCurrentReportFilePath + "\\" + EnvironmentSetup.strCurrentReportFileName; 
    	FileOutputStream fileOutputStream = new FileOutputStream(new File(EnvironmentSetup.strCurrentReportFile));
    	EnvironmentSetup.logger.info(EnvironmentSetup.strCurrentReportFile);
    	FileChannel source = fileInputStream.getChannel();
    	FileChannel destination = fileOutputStream.getChannel();
    	
    	destination.transferFrom(source, 0, source.size());
    	
    	fileInputStream.close();
    	fileOutputStream.close();
	}
    
    /**
	 * 
	 */
    public static void getDuration(String strStartTime, String strStopTime){
    	try{
    		
			Date StartTime = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").parse(strStartTime);
			Date EndTime = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").parse(strStopTime);
			long result = Math.abs(EndTime.getTime() - StartTime.getTime());
			
			DecimalFormat Numberformat = new DecimalFormat("#.###");
			 
			double diffSeconds = (double)(result / 1000 % 60);
			double diffMinutes = (double)(result / (60 * 1000) % 60);
			double diffHours = (double)(result / (60 * 60 * 1000) % 24);
			double diffDays = (double)(result / (24 * 60 * 60 * 1000));
			
			diffSeconds = diffSeconds/60;
			diffHours = diffHours*60;
			double durationInMins = diffHours + diffSeconds + diffMinutes;
			double durationInSeconds = durationInMins * 60;
			
			EnvironmentSetup.strDuration = Numberformat.format(durationInSeconds);
			
			EnvironmentSetup.logger.info("Duration in seconds: " + EnvironmentSetup.strDuration);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
	 * 
	 */
    public static void createTestCaseHTMLHeader(String currentAutomationID, String currentDataSet){
    	testCase.append("<!DOCTYPE html>");
		testCase.append("\n<html>");
		testCase.append("\n<head>");
		
		testCase.append("\n\t<link rel='stylesheet' href='../style.css'></link>");
		if(!EnvironmentSetup.strModuleName.equalsIgnoreCase("Apttus")){
			testCase.append("\n\t<h1> Results for: " + currentAutomationID + " : " + currentDataSet +"</h1>");
		}
		else{
			testCase.append("\n\t<h1> Results for: " + currentDataSet + " : " + currentAutomationID +"</h1>");
		}
		testCase.append("\n</head>");
		testCase.append("\n<body>");
		
		
		testCase.append("\n\t<table class='steps' name= 'TestCaseResults' cellspacing='0' style='border-spacing:0; border-collapse:collapse;'>\n");
        
        
        //Create Header Row
        testCase.append("\n\t\t<tr class='headerRow'>");
        
        
        testCase.append("\n\t\t\t<th>");
        
        testCase.append("Sl. No");
        testCase.append("</th>");
        testCase.append("\n\t\t\t<th>");
        
    	testCase.append("Current Line Item Id");
        testCase.append("</th>");
        
        testCase.append("\n\t\t\t<th>");
        
        
        testCase.append("Reusable Name");
        testCase.append("</th>");
        
        testCase.append("\n\t\t\t<th>");
        
        testCase.append("Action");
        testCase.append("</th>");
        
        testCase.append("\n\t\t\t<th>");
        
        testCase.append("Expected Result");
        testCase.append("</th>");
        
        testCase.append("\n\t\t\t<th>");
        
        testCase.append("Actual Result");
        testCase.append("</th>");
        
        testCase.append("\n\t\t\t<th>");
        
        testCase.append("Status");
        testCase.append("</th>");
        
        testCase.append("\n\t\t\t<th>");
        
        testCase.append("Start Time");
        testCase.append("</th>");
        
        testCase.append("\n\t\t\t<th>");
        
        testCase.append("End Time");
        testCase.append("</th>");
        
        testCase.append("\n\t\t\t<th>");
        
        testCase.append("Duration");
        testCase.append("</th>");
        
        testCase.append("\n\t\t\t<th>");
        
        testCase.append("File/Screenshot");
        testCase.append("</th>");
        
        testCase.append("\n\t\t</tr>");
    	
    }
    
    /**
	 * 
	 */
    public static void createTestCaseHTMLReport(String strAutomationID, String strDataSet){
    	
		int currentTestStepNumber = 0;
		String currentval = "";
		//EnvironmentSetup.logger.info(ReportPath);
		try{
			ConnectToReportFile();
			
		   try{
			   Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			   String strQuery = "Select * From [" + EnvironmentSetup.strModuleName + "_TestCaseResults$] where AutomationID='" + strAutomationID + "' and CurrentDataSet='" + strDataSet + "';";
			   
			   createTestCaseHTMLHeader(strAutomationID,strDataSet);
			   EnvironmentSetup.logger.info("Created Header");
				
			   EnvironmentSetup.logger.info(strQuery);
			   ResultSet TestSteps = QueryStatement.executeQuery(strQuery);
			   TestSteps.beforeFirst();
			   while(TestSteps.next()){
				   currentTestStepNumber = currentTestStepNumber +1;
				   if(currentTestStepNumber%2==0){
						testCase.append("\n\t\t<tr class='dataRow even'>");
					}
					else{
						testCase.append("\n\t\t<tr class='dataRow odd'>");
					}
				   
				   EnvironmentSetup.logger.info("Creating Report");
				   testCase.append("\n\t\t<tr class='dataRow'>");
				   int colCount = 13;
				   int statusCol=9;
				   
				   for(int i=1; i<=colCount; i++){
					   try{
						   currentval = TestSteps.getString(i);
					   }
					   catch(Exception ex){
						   currentval = "";
					   }
					   if(currentval==null){
						   currentval = "";
					   }
					   if(i!=2 && i!=3){
						   testCase.append("\n\t\t\t<td>");
				    		if(i==1){
				    			if(currentval.contains(".0")){
				    				currentval = currentval.replace(".0","");
				    			}
				    			testCase.append("\n\t\t\t\t<sln>");
				    			testCase.append(currentval);
				    			testCase.append("</sln>");
				    		}
				    		else if (i == statusCol){
				    			if(currentval.equalsIgnoreCase("Pass")){
				            		testCase.append("\n\t\t\t\t<sp>");
				            		testCase.append(currentval);
				            		testCase.append("</sp>");
				            	}
				            	else if (currentval.contains("Fail")){
				            		testCase.append("\n\t\t\t\t<sf>");
				            		testCase.append(currentval);
				            		testCase.append("</sf>");
				            		EnvironmentSetup.blnTestCaseStatus = false;
				    				EnvironmentSetup.strTestCaseStatus = currentval;
				            	}
				            	else if (currentval.contains("Skip")){
				            		testCase.append("\n\t\t\t\t<ss>");
				            		testCase.append(currentval);
				            		testCase.append("</ss>");
				            		EnvironmentSetup.blnTestCaseStatus = false;
				    				EnvironmentSetup.strTestCaseStatus = currentval;
				            	}
				            	else if (currentval.contains("Warning")){
				            		testCase.append("\n\t\t\t\t<sw>");
				            		testCase.append(currentval);
				            		testCase.append("</sw>");
				            		if(!EnvironmentSetup.blnTestCaseStatus == false)
				            		{
				            			EnvironmentSetup.strTestCaseStatus = currentval;
				            		}
				            	}
				            	else{
				            		testCase.append(currentval);
				            	}
				    		}
				    		else if (i == colCount){
				    			//screenshot link
				    			if(currentval != null || currentval!=""){
				    				//Value: =Hyperlink("C:\SalesForceAutomation\Salesforce Automation Suite\Reports\ExecutionReports_2014_Dec_26_18_00\Apttus\Screenshots\Login to SalesForce_Apttus-E2E-1_Test.png")
				    				if(currentval!=null){
				    					if(currentval.contains("Screenshots")){
				    						String[] arr = currentval.split("Screenshots");
						    				EnvironmentSetup.logger.info(Integer.toString(arr.length));
						    				EnvironmentSetup.logger.info(arr[0]);
						    				EnvironmentSetup.logger.info(arr[1]);
						    				arr[1] = arr[1].replace("\")", "");
						    				EnvironmentSetup.logger.info(arr[1]);
						    				
						    				arr[1] = "..\\Screenshots" + arr[1];
						    				testCase.append("\n\t\t\t\t<a href =" + "\"" + arr[1] + "\"" + ">");
						    				testCase.append("View");
						    				testCase.append("\n\t\t\t\t</a>");
				    					}
				    					else if (currentval.contains("Downloaded")){
				    						String[] arr = currentval.split("Downloaded Files");
						    				EnvironmentSetup.logger.info(Integer.toString(arr.length));
						    				EnvironmentSetup.logger.info(arr[0]);
						    				EnvironmentSetup.logger.info(arr[1]);
						    				arr[1] = arr[1].replace("\")", "");
						    				EnvironmentSetup.logger.info(arr[1]);
						    				
						    				arr[1] = "..\\Downloaded Files" + arr[1];
						    				testCase.append("\n\t\t\t\t<a href =" + "\"" + arr[1] + "\"" + ">");
						    				testCase.append("View");
						    				testCase.append("\n\t\t\t\t</a>");
				    					}
				    					else{
				    						testCase.append("");
				    					}
					    				
					    			}
				    				else{
				    					testCase.append("");
					    				testCase.append("\n\t\t\t\t</a>");
				    				}
				    			}
				    		}
				    		
				    		else{
				        		testCase.append(currentval);
				    		}
				    		
				    		testCase.append("\n\t\t\t</td>\n");
					   }
					   
			    		
				   }
				   testCase.append("\n\t\t</tr>");
			   }

			   testCase.append("\n\t</table>");
			   testCase.append("\n\t</body>");
			   testCase.append("\n</html>");
			   
			   //Save TC Report
			   //CurrentDataSet + "_" + EnvironmentSetup.strCurrentLineItemDataSet
			   String currentTCReport = "";
			   currentTCReport = "\\TestCase Reports\\" + strAutomationID + "_" + strDataSet +".html";
			    
			   String TestCasefilePath = EnvironmentSetup.strCurrentReportFilePath + currentTCReport;
		        try (FileOutputStream os = new FileOutputStream(TestCasefilePath)){
		        	
		        	String finalHTML = testCase.toString();
		        	
		        	os.write(finalHTML.getBytes(), 0, testCase.length());
		        	
		        	os.flush();
		        	os.close();
		        }
		        
		        testCase.setLength(0);
		        QueryStatement.close();
		        CloseReportConnection();
		   }
		   
		   catch (Exception ex){
			   ex.printStackTrace();
		   }
		}
		catch(Exception e){
			
		}
    }
    
    /**
	 * 
	 */
    public static void createTestSuiteHeader(){
		
		testSuite.append("<!DOCTYPE html>");
		testSuite.append("\n<html>");
		testSuite.append("\n<head>");
		
		testSuite.append("\n\t<link rel='stylesheet' href='style.css'>");
		
		testSuite.append("</link>\n</head>");
		testSuite.append("\n<body>");
		testSuite.append("\n\t<h1>"+EnvironmentSetup.strModuleName+" Test Suite Results</h1>");
		testSuite.append("\n\t<table class= 'suite' name= 'TestSuite' cellspacing='0' style='border-spacing:0; border-collapse:collapse;'>");
        
        //Create Header Row
        testSuite.append("\n\t\t<tr class='headerRow'>");
        
        testSuite.append("\n\t\t\t<th>");
        
        testSuite.append("Sl. No");
        
        testSuite.append("</th>");
        
        testSuite.append("\n\t\t\t<th>");
        
        testSuite.append("Component");
        
        testSuite.append("</th>");
        
        testSuite.append("\n\t\t\t<th>");
        
        testSuite.append("Test Case Name");
        
        testSuite.append("</th>");
        
        testSuite.append("\n\t\t\t<th>");
        
        testSuite.append("Automation ID");
        
        testSuite.append("</th>");
        
        testSuite.append("\n\t\t\t<th>");
        
        testSuite.append("DataSet");
        
        testSuite.append("</th>");
        
        testSuite.append("\n\t\t\t<th>");
        
        testSuite.append("Status");
        
        testSuite.append("</th>");
        
        testSuite.append("\n\t\t\t<th>");
        
        testSuite.append("Start Time");
        
        testSuite.append("</th>");
        
        testSuite.append("\n\t\t\t<th>");
        
        testSuite.append("End Time");
        
        testSuite.append("</th>");
        
        testSuite.append("\n\t\t\t<th>");
        
        testSuite.append("Duration");
        
        testSuite.append("</th>");
        
        testSuite.append("\n\t\t</tr>");
	}
    
    /**
	 * 
	 */
    public static void createTestSuiteHTMLReport(){
    	
    	int currentTestCaseNumber = 0;
    	String strAutomationID = "";
    	String strCurrentDataSet = "";
    	String currentTCReport = "";
    	
    	ConnectToReportFile();
    	createTestSuiteHeader();
    	
    	try{
		   Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		   String strQuery = "Select * From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$]";
		   EnvironmentSetup.logger.info(strQuery);
		   ResultSet TestCases = QueryStatement.executeQuery(strQuery);
		   TestCases.beforeFirst();
		   while(TestCases.next()){
			   	
			   	currentTestCaseNumber = currentTestCaseNumber + 1;
				
			   	if(currentTestCaseNumber%2==0){
					testSuite.append("\n\t\t<tr class='dataRow even'>");
				}
				else{
					testSuite.append("\n\t\t<tr class='dataRow odd'>");
				}
			   
			   	for(int i=1; i<=9; i++){
			   		testSuite.append("\n\t\t\t<td>");
			   		String currentValue = TestCases.getString(i);
			   		
			   		if(i==4){
	    				strAutomationID = currentValue;
	    			}
			   		
			   		if(i==1){
		    			testSuite.append("\n\t\t\t\t<sln>");
		    			testSuite.append(currentValue);
		    			testSuite.append("</sln>");
		    		}
		    		else if (i == 6){
		    			if(currentValue.equalsIgnoreCase("Pass")){
		            		testSuite.append("\n\t\t\t\t<sp>");
		            		testSuite.append(currentValue);
		            		testSuite.append("</sp>");
		            	}
		    			else if(currentValue.equalsIgnoreCase("Warning")){
		            		testSuite.append("\n\t\t\t\t<sw>");
		            		testSuite.append(currentValue);
		            		testSuite.append("</sw>");
		            	}
		    			else if(currentValue.equalsIgnoreCase("Skipped")){
		            		testSuite.append("\n\t\t\t\t<ss>");
		            		testSuite.append(currentValue);
		            		testSuite.append("</ss>");
		            	}
		            	else{
		            		testSuite.append("\n\t\t\t\t<sf>");
		            		testSuite.append(currentValue);
		            		testSuite.append("</sf>");
		            	}
		    		}
		    		else if (i == 5){
		    			strCurrentDataSet = currentValue;
		    			currentTCReport = ".\\TestCase Reports\\" + strAutomationID + "_" + strCurrentDataSet +".html";
		    			testSuite.append("\n\t\t\t\t<a href='" + currentTCReport + "'>");
		    			testSuite.append(currentValue);
		        		testSuite.append("\n\t\t\t\t</a>");
		    			
		    		}
		    		else{
		    			
		        		testSuite.append(currentValue);
		    		}
		    		
		    		testSuite.append("\n\t\t\t</td>");
			   		
			   	}
			   	
		   }
		   	testSuite.append("\n\t</table>");
			testSuite.append("\n\t</body>");
			testSuite.append("\n</html>");
			
			String TestSuitefilePath = EnvironmentSetup.strCurrentReportFilePath + "\\" + EnvironmentSetup.strCurrentReportFileName;
			TestSuitefilePath = TestSuitefilePath.replace(".xlsx", ".html");
			
			EnvironmentSetup.logger.info(TestSuitefilePath);
	        
			try (FileOutputStream os = new FileOutputStream(TestSuitefilePath)){
	        	
	        	String finalHTML = testSuite.toString();
	        	
	        	os.write(finalHTML.getBytes(), 0, testSuite.length());
	        	
	        	os.flush();
	        	os.close();
	        }
	        
	        testSuite.setLength(0);
    	}
    	catch (Exception ex){
    		EnvironmentSetup.logger.info(ex.toString());
    	}
    	
    	CloseReportConnection();
    }
    
    /**
	 * 
	 */
    public static void checkDependentTestStatus(String strPreReqAutomationID, String strDataSet){
    	
    	ConnectToReportFile();

    	try{

        	ResultSet dependentTestResultSet = null;
        	
        	Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        	String strQuery = "Select * From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where AutomationID='" 
        			+ strPreReqAutomationID + "' and DataSet='" + strDataSet + "'";
        	
        	dependentTestResultSet = QueryStatement.executeQuery(strQuery);
        	dependentTestResultSet.beforeFirst();
        	dependentTestResultSet.next();
        	String dependentStatus = dependentTestResultSet.getString("Status");
        	
        	if(dependentStatus.contains("Terminated")){
        		//Set Execution Status for the current Test
        		EnvironmentSetup.preReqFail = true;
        		
        	}
        	
        	
    	}
    	catch(Exception ex){
    		System.out.println(ex.toString());
    	}
    	
    	
    	//Select * from [TestSuiteResults$] where Automation ID= '' and DataSet = '';
    	//before first, .next
    	//get Status
    	//if status == pass return true; if status is not Fail - Terminated return true else return false
    	
    	//By default check if login was successful if not return false
    	//afterlast, previous
    	//Repeat Login?
    	
    	
    	//What happens if combo is not found?
    	
    	CloseReportConnection();
    }
    
    /**
	 * 
	 */
    public static void checkDependentTestStatusApttus(String strDataSet){
    	
    	ConnectToReportFile();

    	try{

        	ResultSet dependentTestResultSet = null;
        	
        	Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        	String strQuery = "Select * From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where DataSet='" + strDataSet 
        			+ "' and Status Like '%Terminated%' and AutomationID Not Like 'Login'";
        	
        	dependentTestResultSet = QueryStatement.executeQuery(strQuery);
        	dependentTestResultSet.beforeFirst();
        	if(dependentTestResultSet.next()){
        		EnvironmentSetup.preReqFail = true;
        	}
        	else{
        		EnvironmentSetup.preReqFail = false;
        	}
    	}
    	catch(Exception ex){
    		EnvironmentSetup.preReqFail = false;
    		System.out.println(ex.toString());
    	}
 	}
    
    /**
	 * 
	 */
	public static void checkTestCaseStatus(String strAutomationID, String strDataSet){
	 	
	 	String strQuery = "";
	 	ResultSet TestSteps = null;
	 	ConnectToReportFile();
	 	try{
			   Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			   strQuery = "Select * From [" + EnvironmentSetup.strModuleName + "_TestCaseResults$] where AutomationID='" 
					   + strAutomationID + "' and CurrentDataSet='" + strDataSet + "' and Status='Warning'";
			   
			   EnvironmentSetup.logger.info(strQuery);
			   
			   TestSteps = QueryStatement.executeQuery(strQuery);
			   TestSteps.beforeFirst();
			   if(TestSteps.next()){
				   EnvironmentSetup.strTestCaseStatus = "Warning";
			   }
			   else{
				   strQuery = "Select * From [" + EnvironmentSetup.strModuleName + "_TestCaseResults$] where AutomationID='" 
						   + strAutomationID + "' and CurrentDataSet='" + strDataSet + "' and Status Like '%Skipped%'";
				   TestSteps = QueryStatement.executeQuery(strQuery);
				   TestSteps.beforeFirst();
				   if(TestSteps.next()){
					   EnvironmentSetup.strTestCaseStatus = "Skipped";
				   }
				   else{
					   EnvironmentSetup.strTestCaseStatus = "Pass";
				   }
				   	
			   }
			   //
	 	}
	 	catch(Exception ex){
	 		
	 	}
	 	
	 	CloseReportConnection();
	 }
	
	/**
	 * 
	 */
    public static void createComponentReportHeader(String componentName){
		
    	componentReport.append("<!DOCTYPE html>");
		componentReport.append("\n<html>");
		componentReport.append("\n<head>");
		
		componentReport.append("\n\t<link rel='stylesheet' href='../style.css'>");
		
		componentReport.append("</link>\n</head>");
		componentReport.append("\n<body>");
		
		if(!EnvironmentSetup.strModuleName.equalsIgnoreCase("Apttus")){
			componentReport.append("\n\t<h1>"+EnvironmentSetup.strModuleName + " - " + componentName +" Results</h1>");
		}
		else{
			componentReport.append("\n\t<h1>"+EnvironmentSetup.strCurrentDataset + " : " + componentName +" Results</h1>");
		}
		
		componentReport.append("\n\t<table class= 'suite' name= 'TestSuite' cellspacing='0' style='border-spacing:0; border-collapse:collapse;'>");
        
        //Create Header Row
		componentReport.append("\n\t\t<tr class='headerRow'>");
        
		componentReport.append("\n\t\t\t<th>");
        
		componentReport.append("Sl. No");
        
		componentReport.append("</th>");
        
		componentReport.append("\n\t\t\t<th>");
        
		componentReport.append("Component");
        
		componentReport.append("</th>");
        
		componentReport.append("\n\t\t\t<th>");
        
		componentReport.append("Test Case Name");
        
		componentReport.append("</th>");
        
		componentReport.append("\n\t\t\t<th>");
        
		componentReport.append("Automation ID");
        
		componentReport.append("</th>");
        
		componentReport.append("\n\t\t\t<th>");
        
		componentReport.append("DataSet");
        
		componentReport.append("</th>");
        
		componentReport.append("\n\t\t\t<th>");
        
		componentReport.append("Status");
        
		componentReport.append("</th>");
        
		componentReport.append("\n\t\t\t<th>");
        
		componentReport.append("Start Time");
        
		componentReport.append("</th>");
        
		componentReport.append("\n\t\t\t<th>");
        
		componentReport.append("End Time");
        
		componentReport.append("</th>");
        
		componentReport.append("\n\t\t\t<th>");
        
		componentReport.append("Duration");
        
		componentReport.append("</th>");
        
		componentReport.append("\n\t\t</tr>");
	}
    
    /**
	 * 
	 */
    public static void createComponentReportHeaderApttus(String componentName){
		
    	componentReport.append("<!DOCTYPE html>");
		componentReport.append("\n<html>");
		componentReport.append("\n<head>");
		
		componentReport.append("\n\t<link rel='stylesheet' href='../style.css'>");
		
		componentReport.append("</link>\n</head>");
		componentReport.append("\n<body>");
		
		if(!EnvironmentSetup.strModuleName.equalsIgnoreCase("Apttus")){
			componentReport.append("\n\t<h1>"+EnvironmentSetup.strModuleName + " - " + componentName +" Results</h1>");
		}
		else{
			componentReport.append("\n\t<h1>"+EnvironmentSetup.strCurrentDataset + " : " + componentName +" Results</h1>");
		}
		componentReport.append("\n\t\t<table class= 'steps' name= 'TestCaseResults' cellspacing='0' style='border-spacing:0; border-collapse:collapse;'>");
		
		componentReport.append("\n\t\t<tr class='headerRow'>");
		
		componentReport.append("\n\t\t\t<th>");
        
        componentReport.append("Sl. No");
        componentReport.append("</th>");
        componentReport.append("\n\t\t\t<th>");
        
    	componentReport.append("Current Line Item Id");
        componentReport.append("</th>");
        
        componentReport.append("\n\t\t\t<th>");
        
        
        componentReport.append("Reusable Name");
        componentReport.append("</th>");
        
        componentReport.append("\n\t\t\t<th>");
        
        componentReport.append("Action");
        componentReport.append("</th>");
        
        componentReport.append("\n\t\t\t<th>");
        
        componentReport.append("Expected Result");
        componentReport.append("</th>");
        
        componentReport.append("\n\t\t\t<th>");
        
        componentReport.append("Actual Result");
        componentReport.append("</th>");
        
        componentReport.append("\n\t\t\t<th>");
        
        componentReport.append("Status");
        componentReport.append("</th>");
        
        componentReport.append("\n\t\t\t<th>");
        
        componentReport.append("Start Time");
        componentReport.append("</th>");
        
        componentReport.append("\n\t\t\t<th>");
        
        componentReport.append("End Time");
        componentReport.append("</th>");
        
        componentReport.append("\n\t\t\t<th>");
        
        componentReport.append("Duration");
        componentReport.append("</th>");
        
        componentReport.append("\n\t\t\t<th>");
        
        componentReport.append("File/Screenshot");
        componentReport.append("</th>");
        
        componentReport.append("\n\t\t</tr>");
	}
    
    /**
	 * 
	 */
    public static void createComponentReports(){
    	
    	
    	String Components = fetchComponentNames();
    	String currentComponent = "";
    	String [] componentNames = null;
    	String query = "";
    	ResultSet componentResultSet = null;
    	int currentTestCaseNumber = 0;
    	String strCurrentDataSet = "";
    	String strAutomationID="";
    	String currentTCReport = "";
    	
    	try{
    		
    		componentNames = Components.split(",");
    		
    		for(int i=0;i<componentNames.length;i++){
    			componentReport.setLength(0);
    			currentComponent = componentNames[i];
    			ConnectToReportFile();
    			Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				query = "Select * From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where Component ='" +currentComponent + "'";
				   
				EnvironmentSetup.logger.info(query);
				   
				componentResultSet = QueryStatement.executeQuery(query);
				componentResultSet.beforeFirst();
				createComponentReportHeader(currentComponent);
				while(componentResultSet.next()){
					currentTestCaseNumber = currentTestCaseNumber +1;
					if(currentTestCaseNumber%2==0){
						componentReport.append("\n\t\t<tr class='dataRow even'>");
					}
					else{
						componentReport.append("\n\t\t<tr class='dataRow odd'>");
					}
				   
				   	for(int j=1; j<=9; j++){
				   		componentReport.append("\n\t\t\t<td>");
				   		String currentValue = componentResultSet.getString(j);
				   		if(j==4){
		    				strAutomationID = currentValue;
		    			}
				   		
				   		if(j==1){
				   			componentReport.append("\n\t\t\t\t<sln>");
				   			componentReport.append(currentValue);
				   			componentReport.append("</sln>");
			    		}
			    		else if (j == 6){
			    			if(currentValue.equalsIgnoreCase("Pass")){
			    				componentReport.append("\n\t\t\t\t<sp>");
			    				componentReport.append(currentValue);
			    				componentReport.append("</sp>");
			            	}
			    			else if(currentValue.equalsIgnoreCase("Warning")){
			    				componentReport.append("\n\t\t\t\t<sw>");
			    				componentReport.append(currentValue);
			    				componentReport.append("</sw>");
			            	}
			    			else if(currentValue.equalsIgnoreCase("Skipped")){
			    				componentReport.append("\n\t\t\t\t<sw>");
			    				componentReport.append(currentValue);
			    				componentReport.append("</ss>");
			            	}
			            	else{
			            		componentReport.append("\n\t\t\t\t<sf>");
			            		componentReport.append(currentValue);
			            		componentReport.append("</sf>");
			            	}
			    		}
			    		else if (j == 5){
			    			strCurrentDataSet = currentValue;
			    			currentTCReport = "..\\TestCase Reports\\" + strAutomationID + "_" + strCurrentDataSet +".html";
			    			componentReport.append("\n\t\t\t\t<a href='" + currentTCReport + "'>");
			    			componentReport.append(currentValue);
			    			componentReport.append("\n\t\t\t\t</a>");
			    			
			    		}
			    		else{
			    			
			    			componentReport.append(currentValue);
			    		}
			    		
				   		componentReport.append("\n\t\t\t</td>");
				   		
				   	}
				}
				
				//Publish Report
				componentReport.append("\n\t</table>");
				componentReport.append("\n\t</body>");
				componentReport.append("\n</html>");
				
				String TestSuitefilePath = EnvironmentSetup.strCurrentReportFilePath + "\\Component Reports\\" + currentComponent + ".html";
								
				EnvironmentSetup.logger.info(TestSuitefilePath);
		        
				try (FileOutputStream os = new FileOutputStream(TestSuitefilePath)){
		        	
		        	String finalHTML = componentReport.toString();
		        	
		        	os.write(finalHTML.getBytes(), 0, componentReport.length());
		        	
		        	os.flush();
		        	os.close();
		        }
		        
				componentReport.setLength(0);
				CloseReportConnection();
			}
     	}
     	catch(Exception ex){
     		System.out.println(ex.toString());
     	}
    }
    
    /**
	 * 
	 */
    public static String fetchComponentNames(){
    	ConnectToReportFile();
    	String componentNames = "";
    	String currentComponent = "";
    	String query = "";
    	ResultSet componentResultSet = null;
    	try{
 		   Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
 		   query = "Select Distinct Component From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$]";
 		   
 		   EnvironmentSetup.logger.info(query);
 		   
 		   componentResultSet = QueryStatement.executeQuery(query);
 		   componentResultSet.beforeFirst();
 		   while(componentResultSet.next()){
 			  currentComponent = componentResultSet.getString("Component");
 			   if(componentNames==""){
 				  componentNames = currentComponent;
 			   }
 			   else{
 				  componentNames = componentNames + "," + currentComponent;
 			   }
 		   }
 		   //componentReport
     	}
     	catch(Exception ex){
     		System.out.println(ex.toString());
     		ex.printStackTrace();
     		//return "";
     	}
     	
     	CloseReportConnection();
     	return componentNames;
    }
    
    /**
	 * 
	 */
    public static void createHTMLDashboard(){
    	
    	String Components = fetchComponentNames();
    	String currentComponent = "";
    	String [] componentNames = null;
    	String query = "";
    	String currentStatus = "";
    	ResultSet componentResultSet = null;
    	
    	int compomentTestCasesPassed = 0;
    	int compomentTestCasesFailed = 0;
    	int compomentTestCasesSkipped = 0;
    	int componentTestCasesWarnings = 0;
    	
    	int totalTestCasesPassed = 0;
    	int totalTestCasesFailed = 0;
    	int totalTestCasesSkipped = 0;
    	int totalTestCasesWarnings = 0;
    	
    	int componentSlNo = 0;
    	
    	dashboard.append("<html><head><link rel='stylesheet' href='style.css'></link>"
    			+ "<script type='text/javascript' src='https://www.google.com/jsapi'></script>"
    			+ "<script type='text/javascript'>"
    			+ "google.load('visualization', '1', {packages:['corechart']});"
    			+ "google.setOnLoadCallback(drawChart);"
    			+ "function drawChart() {"
    			+ "var pass = parseInt(document.getElementById('Passed').firstChild.nodeValue);"
    			+ "var fail = parseInt(document.getElementById('Fail').firstChild.nodeValue);"
    			+"var skip = parseInt(document.getElementById('Skipped').firstChild.nodeValue);"
    			+ "var warning = parseInt(document.getElementById('Warning').firstChild.nodeValue);"
        		+ "var data = google.visualization.arrayToDataTable(["
        		+ "['Status', 'No. Of Tests'],"
        		+ "['Passed',     pass],"
        		+ "['Failed',     fail],"
        		+ "['Skipped',     skip],"
        		+ "['Warning',  warning]"
        		+ "]);"

        		+ "var options = {"
        		+ "pieHole: 0.5,"
        		+ "pieSliceTextStyle: {"
        		+ "color: 'none',"
        		+"},"
        		+ "legend: {alignment: 'center', position: 'right', textStyle: {color: 'black', fontSize: 16}},"
        		+ "slices: {0: {color: 'rgb(0,176,80)'}, 1: {color: 'rgb(255,0,0)'}, 2: {color: 'rgb(255,165,0)'}, 3: {color: 'rgb(255,192,0)'}}"
        		+ "};"

        		+ "var chart = new google.visualization.PieChart(document.getElementById('donut_single'));"
        		+ "chart.draw(data, options);"
        		+"}"
        		+ "</script>"
    			+"</head>");
    	
    	dashboard.append("\n<body>");
    	dashboard.append("\n<div id='donut_single' style='width: 100%; height: 500px;'></div>");
    	dashboard.append("\n<table style='width: 75%; alignment: center'>");
    	dashboard.append("\n<tr class='headerRow'>"
			+ "\n<th>Sl. No</th>"
			+ "\n<th>Component Name</th>"
			+ "\n<th>Passed</th>"
			+ "\n<th>Failed</th>"
			+ "\n<th>Skipped</th>"
			+ "\n<th>Warnings</th>"
			+"\n</tr>");
    	
    	componentNames = Components.split(",");
		
		for(int i=0;i<componentNames.length;i++){
			currentComponent = componentNames[i];
			//dashboard.append("\n<tr class='headerRow'>");
			if(!(currentComponent.contains("Login")) && !(currentComponent.contains("Logout"))){
				componentSlNo = componentSlNo +1;
				ConnectToReportFile();
				
				if(i%2==0){
					dashboard.append("\n\t\t<tr class='dataRow even'>");
				}
				else{
					dashboard.append("\n\t\t<tr class='dataRow odd'>");
				}
				
				try{
					Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					query = "Select * From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where Component ='" +currentComponent + "'";
					   
					EnvironmentSetup.logger.info(query);
					   
					componentResultSet = QueryStatement.executeQuery(query);
					componentResultSet.beforeFirst();
					while(componentResultSet.next()){
						
						currentStatus = componentResultSet.getString(6);
						if(currentStatus!=null){
							
							if(currentStatus.equalsIgnoreCase("Pass")){
								compomentTestCasesPassed = compomentTestCasesPassed + 1;
							}
							else if(currentStatus.contains("Fail")){
								compomentTestCasesFailed = compomentTestCasesFailed + 1; 
							}
							else if(currentStatus.equalsIgnoreCase("Skipped")){
								compomentTestCasesSkipped = compomentTestCasesSkipped + 1; 
							}
							else if(currentStatus.equalsIgnoreCase("Warning")){
								componentTestCasesWarnings = componentTestCasesWarnings +1;
							}
							
						}
					}
					
					//String ComponentReport = EnvironmentSetup.strCurrentReportFilePath + "\\Component Reports\\" + currentComponent + ".html";
					String ComponentReport =".\\Component Reports\\" + currentComponent + ".html";
					int j = i+1;
					dashboard.append("\n\t<td>" + j +"</td>");
					dashboard.append("\n\t<td><a href='"+ ComponentReport +"'>" + currentComponent +"</a></td>");
					dashboard.append("\n\t<td>" + compomentTestCasesPassed +"</td>");
					dashboard.append("\n\t<td>" + compomentTestCasesFailed +"</td>");
					dashboard.append("\n\t<td>" + compomentTestCasesSkipped +"</td>");
					dashboard.append("\n\t<td>" + componentTestCasesWarnings +"</td>");
					
					dashboard.append("\n</tr>");
					
					totalTestCasesPassed = totalTestCasesPassed + compomentTestCasesPassed;
					totalTestCasesFailed = totalTestCasesFailed + compomentTestCasesFailed;
					totalTestCasesSkipped = totalTestCasesSkipped + compomentTestCasesSkipped;
					totalTestCasesWarnings = totalTestCasesWarnings + componentTestCasesWarnings;
					
					compomentTestCasesPassed = 0;
					compomentTestCasesFailed = 0;
					compomentTestCasesSkipped = 0;
					componentTestCasesWarnings = 0;
					
					CloseReportConnection();
				}
				catch(Exception ex){
					System.out.println(ex.toString());
				}
			}
			
		}
		dashboard.append("\n<tr class='headerRow'>");
		dashboard.append("\n\t<th></th>");
		dashboard.append("\n\t<th>Total</th>");
		dashboard.append("\n\t<th>"+totalTestCasesPassed +"</th>");
		dashboard.append("\n\t<th>"+totalTestCasesFailed +"</th>");
		dashboard.append("\n\t<th>"+totalTestCasesSkipped +"</th>");
		dashboard.append("\n\t<th>"+totalTestCasesWarnings +"</th>");
		dashboard.append("\n</tr>");
		dashboard.append("</table>");
		dashboard.append("<div id='Passed' style='display:none'>" + totalTestCasesPassed + "</div>");
		dashboard.append("<div id='Fail' style='display:none'>" + totalTestCasesFailed + "</div>");
		dashboard.append("<div id='Skipped' style='display:none'>" + totalTestCasesSkipped + "</div>");
		dashboard.append("<div id='Warning' style='display:none'>" + totalTestCasesWarnings + "</div>");
		
		dashboard.append("</body>");
    	dashboard.append("</html>");
    	
    	String Dashboard = EnvironmentSetup.strCurrentReportFilePath+"\\ExecutionDashboard.html";
		
		EnvironmentSetup.logger.info(Dashboard);
        
		try (FileOutputStream os = new FileOutputStream(Dashboard)){
        	
        	String finalHTML = dashboard.toString();
        	
        	os.write(finalHTML.getBytes(), 0, dashboard.length());
        	
        	os.flush();
        	os.close();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		dashboard.setLength(0);
    	
    }
    
    /**
	 * 
	 */
    public static String fetchUniqueDataSets(){
    	ConnectToReportFile();
    	String strDataSets = "";
    	String currentDataSet = "";
    	String query = "";
    	ResultSet componentResultSet = null;
    	try{
 		   Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
 		   query = "Select Distinct DataSet From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$]";
 		   
 		   EnvironmentSetup.logger.info(query);
 		   
 		   componentResultSet = QueryStatement.executeQuery(query);
 		   componentResultSet.beforeFirst();
 		   while(componentResultSet.next()){
 			  currentDataSet = componentResultSet.getString("DataSet");
 			   if(strDataSets==""){
 				  strDataSets = currentDataSet;
 			   }
 			   else{
 				  strDataSets = strDataSets + "," + currentDataSet;
 			   }
 		   }
 		   //componentReport
     	}
     	catch(Exception ex){
     		System.out.println(ex.toString());
     		ex.printStackTrace();
     		//return "";
     	}
     	
     	CloseReportConnection();
     	return strDataSets;
    }
    
    /**
	 * 
	 *//*
    public static void createHTMLDashboardApttus(){
    	
    	String DataSets = fetchUniqueDataSets();
    	String currentDataSet = "";
    	String [] dataSets = null;
    	String query = "";
    	String currentStatus = "";
    	ResultSet dataSetResultSet = null;
    	
    	int dataSetComboPassed = 0;
    	int dataSetComboFailed = 0;
    	int dataSetComboWarnings = 0;
    	
    	int totalTestCasesPassed = 0;
    	int totalTestCasesFailed = 0;
    	int totalTestCasesWarnings = 0;
    	
    	int DataSetslNo = 0;
    	
    	dashboard.append("<html><head><link rel='stylesheet' href='style.css'></link>"
    			+ "<script type='text/javascript' src='https://www.google.com/jsapi'></script>"
    			+ "<script type='text/javascript'>"
    			+ "google.load('visualization', '1', {packages:['corechart']});"
    			+ "google.setOnLoadCallback(drawChart);"
    			+ "function drawChart() {"
    			+ "var pass = parseInt(document.getElementById('Passed').firstChild.nodeValue);"
    			+ "var fail = parseInt(document.getElementById('Fail').firstChild.nodeValue);"
    			+ "var warning = parseInt(document.getElementById('Warning').firstChild.nodeValue);"
        		+ "var data = google.visualization.arrayToDataTable(["
        		+ "['Status', 'No. Of Tests'],"
        		+ "['Pass',     pass],"
        		+ "['Fail',     fail],"
        		+ "['Warning',  warning]"
        		+ "]);"

        		+ "var options = {"
        		+ "pieHole: 0.5,"
        		+ "pieSliceTextStyle: {"
        		+ "color: 'none',"
        		+"},"
        		+ "legend: {alignment: 'center', position: 'right', textStyle: {color: 'black', fontSize: 16}},"
        		+ "slices: {0: {color: 'rgb(0,176,80)'}, 1: {color: 'rgb(255,0,0)'}, 2: {color: 'rgb(255,192,0)'}}"
        		+ "};"

        		+ "var chart = new google.visualization.PieChart(document.getElementById('donut_single'));"
        		+ "chart.draw(data, options);"
        		+"}"
        		+ "</script>"
    			+"</head>");
    	
    	dashboard.append("\n<body>");
    	dashboard.append("\n<div id='donut_single' style='width: 100%; height: 500px;'></div>");
    	dashboard.append("\n<table style='width: 75%; alignment: center'>");
    	dashboard.append("\n<tr class='headerRow'>"
			+ "\n<th>Sl. No</th>"
			+ "\n<th>DataSet/Jira Id</th>"
			+ "\n<th>Test Case Status</th>"
			+ "\n<th>Functional Areas Passed</th>"
			+ "\n<th>Functional Areas Failed</th>"
			+"\n</tr>");
    	
    	dataSets = DataSets.split(",");
		
		for(int i=0;i<dataSets.length;i++){
			currentDataSet = dataSets[i];
			//dashboard.append("\n<tr class='headerRow'>");
			if(!(currentDataSet.contains("Login")) && !(currentDataSet.contains("Logout"))){
				DataSetslNo = DataSetslNo +1;
				ConnectToReportFile();
				
				if(i%2==0){
					dashboard.append("\n\t\t<tr class='dataRow even'>");
				}
				else{
					dashboard.append("\n\t\t<tr class='dataRow odd'>");
				}
				try{
					Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					//Total Components Executed
					query = "Select count(Component) From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where DataSet ='" +currentDataSet + "'";
					dataSetResultSet = QueryStatement.executeQuery(query);
					try{
						dataSetResultSet.beforeFirst();
						dataSetResultSet.next();
						long totalComponents = dataSetResultSet.getLong(1);
					}
					catch(Exception ex){
						
					}
					//Total Components Executed
					query = "Select count(Component) From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where DataSet ='" +currentDataSet + "' and Status Like '%Pass%'"
							+ " and AutomationID NOT Like '%Login%' and AutomationID NOT Like '%Logout%'";
					dataSetResultSet = QueryStatement.executeQuery(query);
					try{
						dataSetResultSet.beforeFirst();
						dataSetResultSet.next();
						long totalComponentsPassed = dataSetResultSet.getLong(1);
					}
					catch(Exception ex){
						
					}
					
					query = "Select count(Component) From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where DataSet ='" +currentDataSet + "' and Status Like '%Fail%'"
							+ " and AutomationID NOT Like '%Login%' and AutomationID NOT Like '%Logout%'";
					
					dataSetResultSet = QueryStatement.executeQuery(query);
					try{
						dataSetResultSet.beforeFirst();
						dataSetResultSet.next();
						long totalComponentsFailed = dataSetResultSet.getLong(1);
					}
					catch(Exception ex){
						
					}
				}
				catch(Exception ex){
					
				}
				try{
					Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					query = "Select * From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where DataSet ='" +currentDataSet + "' and Status Like '%Fail%'"
							+ " and AutomationID NOT Like '%Login%' and AutomationID NOT Like '%Logout%'";
					   
					EnvironmentSetup.logger.info(query);
					dataSetResultSet = QueryStatement.executeQuery(query);
					try{
						dataSetResultSet.beforeFirst();
						if(dataSetResultSet.next()){
							totalTestCasesFailed = totalTestCasesFailed + 1;
							currentStatus = "Fail";
							//Store Names of All Failed Components?
						}
						else{
							totalTestCasesPassed = totalTestCasesPassed + 1;
							currentStatus = "Pass";
						}
					}
					catch(Exception ex){
						totalTestCasesPassed = totalTestCasesPassed + 1;
						currentStatus = "Pass";
					}
					
					
					String ComponentReport =".\\TestCase Reports\\" + currentDataSet + ".html";
					//int j = i+1;
					dashboard.append("\n\t<td>" + DataSetslNo +"</td>");
					dashboard.append("\n\t<td><a href='"+ ComponentReport +"'>" + currentDataSet +"</a></td>");
					dashboard.append("\n\t<td>" + currentStatus +"</td>");
					dashboard.append("\n</tr>");
					
					CloseReportConnection();
				}
				catch(Exception ex){
					System.out.println(ex.toString());
				}
			}
			
		}
		dashboard.append("</table>");
		dashboard.append("<div>\n"
				+ "<table class='summary'>\n"
				+ "<tr><th class='headerRow'>Passed</th><th id='Passed'>"+ totalTestCasesPassed +"</th></tr>\n"
				+ "<tr><th class='headerRow'>Failed</th><th id='Fail'>"+totalTestCasesFailed +"</th></tr>\n"
				+ "<tr><th class='headerRow'>Warnings</th><th id='Warning'>"+ totalTestCasesWarnings +"</th></tr>\n"
				+ "</table>\n"
				+ "</div>");
		dashboard.append("\n</body>");
    	dashboard.append("\n</html>");
    	
    	String Dashboard = EnvironmentSetup.strCurrentReportFilePath+"\\ExecutionDashboard.html";
		
		EnvironmentSetup.logger.info(Dashboard);
        
		try (FileOutputStream os = new FileOutputStream(Dashboard)){
        	
        	String finalHTML = dashboard.toString();
        	
        	os.write(finalHTML.getBytes(), 0, dashboard.length());
        	
        	os.flush();
        	os.close();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		dashboard.setLength(0);
    	
    }
    */
    /**
	 * 
	 */
    public static void createHTMLDashboardApttus(){
    	
    	String DataSets = fetchUniqueDataSets();
    	String currentDataSet = "";
    	String [] dataSets = null;
    	String query = "";
    	String currentStatus = "";
    	ResultSet dataSetResultSet = null;
    	
    	int dataSetComboPassed = 0;
    	int dataSetComboFailed = 0;
    	int dataSetComboWarnings = 0;
    	
    	int totalTestCasesPassed = 0;
    	int totalTestCasesFailed = 0;
    	int totalTestCasesWarnings = 0;
    	
    	int DataSetslNo = 0;
    	//dashboard.append("\n\t<td><a href='"+ ComponentReport +"'>" + currentDataSet +"</a></td>");
    	
    	
    	dashboard.append("<html><head><link rel='stylesheet' href='style.css'></link>"
    			+ "<script type='text/javascript' src='https://www.google.com/jsapi'></script>"
    			+ "<script type='text/javascript'>"
    			+ "google.load('visualization', '1', {packages:['corechart']});"
    			+ "google.setOnLoadCallback(drawChart);"
    			+ "function drawChart() {"
    			+ "var pass = parseInt(document.getElementById('TestPassed').firstChild.nodeValue);"
    			+ "var fail = parseInt(document.getElementById('TestFail').firstChild.nodeValue);"
    			+ "var warning = parseInt(document.getElementById('TestWarning').firstChild.nodeValue);"
        		+ "var data = google.visualization.arrayToDataTable(["
        		+ "['Status', 'No. Of Tests'],"
        		+ "['Pass',     pass],"
        		+ "['Fail',     fail],"
        		+ "['Warning',  warning]"
        		+ "]);"

        		+ "var options = {"
        		+ "pieHole: 0.5,"
        		+ "pieSliceTextStyle: {"
        		+ "color: 'none',"
        		+"},"
        		+ "legend: {alignment: 'center', position: 'right', textStyle: {color: 'black', fontSize: 16}},"
        		+ "slices: {0: {color: 'rgb(0,176,80)'}, 1: {color: 'rgb(255,0,0)'}, 2: {color: 'rgb(255,192,0)'}}"
        		+ "};"

        		+ "var chart = new google.visualization.PieChart(document.getElementById('donut_overall'));"
        		+ "chart.draw(data, options);"
        		+"}"
        		+ "</script>"
        		+ "<script type='text/javascript' src='https://www.google.com/jsapi'></script>"
    			+ "<script type='text/javascript'>"
    			+ "google.load('visualization', '1', {packages:['corechart']});"
    			+ "google.setOnLoadCallback(drawChart);"
    			+ "function drawChart() {"
    			+ "var pass = parseInt(document.getElementById('ComponentsPassed').firstChild.nodeValue);"
    			+ "var fail = parseInt(document.getElementById('ComponentsFail').firstChild.nodeValue);"
    			+ "var warning = parseInt(document.getElementById('ComponentsWarning').firstChild.nodeValue);"
        		+ "var data = google.visualization.arrayToDataTable(["
        		+ "['Status', 'No. Of Tests'],"
        		+ "['Pass',     pass],"
        		+ "['Fail',     fail],"
        		+ "['Warning',  warning]"
        		+ "]);"

        		+ "var options = {"
        		+ "pieHole: 0.5,"
        		+ "pieSliceTextStyle: {"
        		+ "color: 'none',"
        		+"},"
        		+ "legend: {alignment: 'center', position: 'right', textStyle: {color: 'black', fontSize: 16}},"
        		+ "slices: {0: {color: 'rgb(0,176,80)'}, 1: {color: 'rgb(255,0,0)'}, 2: {color: 'rgb(255,192,0)'}}"
        		+ "};"

        		+ "var chart = new google.visualization.PieChart(document.getElementById('donut_components'));"
        		+ "chart.draw(data, options);"
        		+"}"
        		+ "</script>"
        		
				+ "<script type='text/javascript' src='https://www.google.com/jsapi'></script>"
				+ "<script type='text/javascript'>"
				+ "google.load('visualization', '1', {packages:['corechart']});"
				+ "google.setOnLoadCallback(drawChart);"
				+ "function drawChart() {"
				+ "var pass = parseInt(document.getElementById('StepsPassed').firstChild.nodeValue);"
				+ "var fail = parseInt(document.getElementById('StepsFail').firstChild.nodeValue);"
				+ "var warning = parseInt(document.getElementById('StepsWarning').firstChild.nodeValue);"
				+ "var data = google.visualization.arrayToDataTable(["
				+ "['Status', 'No. Of Tests'],"
				+ "['Pass',     pass],"
				+ "['Fail',     fail],"
				+ "['Warning',  warning]"
				+ "]);"
				
				+ "var options = {"
				+ "pieHole: 0.5,"
				+ "pieSliceTextStyle: {"
				+ "color: 'none',"
				+"},"
				+ "legend: {alignment: 'center', position: 'right', textStyle: {color: 'black', fontSize: 16}},"
				+ "slices: {0: {color: 'rgb(0,176,80)'}, 1: {color: 'rgb(255,0,0)'}, 2: {color: 'rgb(255,192,0)'}}"
				+ "};"
				
				+ "var chart = new google.visualization.PieChart(document.getElementById('donut_steps'));"
				+ "chart.draw(data, options);"
				+"}"
				+ "</script>"
    			+"</head>");
    	
		//Start <body>
    	dashboard.append("\n<body>");
    	//Header
    	dashboard.append("\n<h1 style='text-align: center; color:royalblue;'>Apttus Test Execution Results</h1>");
    	
    	//Charts
    	dashboard.append("<table width='100%' border='0'>");
    	dashboard.append("\n<tr>");
    	dashboard.append("\n<td><h3 style='text-align: center; color:royalblue;'>Test Cases</h3>");
    	dashboard.append("\n<div id='donut_overall' style='width: 100%; height: 350px; display:inline-block;'></div></td>");
    	dashboard.append("\n<td><h3 style='text-align: center; color:royalblue;'>Functional Areas</h3>");
    	dashboard.append("\n<div id='donut_components' style='width: 100%; height: 350px; display:inline-block;'></div></td>");
    	dashboard.append("\n<td><h3 style='text-align: center; color:royalblue;'>Test Steps</h3>");
    	dashboard.append("\n<div id='donut_steps' style='width: 100%; height: 350px; display:inline-block;'></div></td>");
    	dashboard.append("\n</tr>");
    	dashboard.append("\n</table>");
    	
    	//Main Data for Charts
    	
    	dashboard.append("<table width='100%' border='none'>");
    	dashboard.append("\n<tr border='none'>");
    	//td for Test Case Report
    	dashboard.append("\n\t<td style='width: 100%; height: 100px; border: none;'>");
    	dashboard.append("<div class='container'>\n"
				+ "<table class='summary' style='width: 100%; height: 100px; display: inline-table';>\n"
				+ "<tr><th class='headerRow'>Total E2E Tests Passed</th><th id='TestPassed'></th></tr>\n"
				+ "<tr><th class='headerRow'>Total E2E Tests Failed</th><th id='TestFail'></th></tr>\n"
				+ "<tr><th class='headerRow'>Total E2E Tests Warnings</th><th id='TestWarning'></th></tr>\n"
				+ "</table>\n");
		
    	dashboard.append("\n\t</td>");
    	
    	//td for Components
    	dashboard.append("\n\t<td style='width: 100%; height: 100px; border: none;'>");
    	dashboard.append("<table class='summary' style='width: 100%; height: 100px; display: inline-table';>\n"
				+ "<tr><th class='headerRow'>Total Functional Areas Passed</th><th id='ComponentsPassed'></th></tr>\n"
				+ "<tr><th class='headerRow'>Total Functional Areas Failed</th><th id='ComponentsFail'></th></tr>\n"
				+ "<tr><th class='headerRow'>Total Functional Areas Warnings</th><th id='ComponentsWarning'></th></tr>\n"
				+ "</table>\n");
		
    	dashboard.append("\n\t</td>");
    	
    	
    	//td for Steps
    	dashboard.append("\n\t<td style='width: 100%; height: 100px; border: none;'>");
    	dashboard.append("<table class='summary' style='width: 100%; height: 100px; display: inline-table';>\n"
				+ "<tr><th class='headerRow'>Total Steps Passed</th><th id='StepsPassed'></th></tr>\n"
				+ "<tr><th class='headerRow'>Total Steps Failed</th><th id='StepsFail'></th></tr>\n"
				+ "<tr><th class='headerRow'>Total Steps Warnings</th><th id='StepsWarning'></th></tr>\n"
				+ "</table>\n"
				+ "</div>");
    	dashboard.append("\n\t</td>");
    	
    	
    	dashboard.append("\n</tr>");
    	dashboard.append("\n</table>");
    	
    	
    	//Start of Data Table with Hyperlinks
    	dashboard.append("\n<table style='width: 75%; alignment: center'>");
    	dashboard.append("\n<tr class='headerRow'>"
			+ "\n<th>Sl. No</th>"
			+ "\n<th>DataSet/Jira Id</th>"
			+ "\n<th>Status</th>"
			+ "\n<th>Functional Areas Covered</th>"
			+ "\n<th>Functional Areas Passed</th>"
			+ "\n<th>Functional Areas Failed</th>"
			+ "\n<th>No. Of Steps Executed</th>"
			+ "\n<th>No. Of Steps Passed</th>"
			+ "\n<th>No. Of Steps Failed</th>"
			+"\n</tr>");
    	
    	long TotalComponentsFailed = 0;
    	long TotalComponentsPassed = 0;
    	long TotalComponentsWarning = 0;
    	long TotalStepsExecuted = 0;
    	long TotalStepsFailed = 0;
    	long TotalStepsPassed = 0;
    	
    	long TotalComponents = 0;
    	long TotalStepsWarning = 0;
    	
    	//String DataSets = "QA-1005,QA-124,QA-125,QA-187,QA-188,QA-194,QA-36,QA-37,QA-38,QA-40,QA-41,QA-43,QA-44,QA-45,QA-47,QA-48,QA-49,QA-52,QA-54,QA-57,QA-60,QA-63";
    	//String DataSets = "QA-1005,QA-124,QA-125";
    	//TODO Insert Logic
    	dataSets = DataSets.split(",");

    	ConnectToReportFile();
		
    	for(int i=0;i<dataSets.length;i++){
			currentDataSet = dataSets[i];
			if(!(currentDataSet.contains("Login")) && !(currentDataSet.contains("Logout"))){
				DataSetslNo = DataSetslNo +1;
				long TestCaseComponents = 0;
		    	long TestCaseComponentsFailed = 0;
		    	long TestCaseComponentsPassed = 0;
		    	long TestCaseComponentsWarning = 0;
		    	long TestCaseStepsExecuted = 0;
		    	long TestCaseStepsFailed = 0;
		    	long TestCaseStepsPassed = 0;
				if(i%2==0){
					dashboard.append("\n\t\t<tr class='dataRow even'>");
				}
				else{
					dashboard.append("\n\t\t<tr class='dataRow odd'>");
				}
				
				try{
					Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					/*query = "Select Count(*) From [Apttus_TestSuiteResults$] where DataSet ='" +currentDataSet + "' and Status Like '%Fail%'"
							+ " and AutomationID NOT Like '%Login%' and AutomationID NOT Like '%Logout%'";*/
					
					query = "Select Count(*) From [Apttus_TestSuiteResults$] where DataSet ='" +currentDataSet + "'"
							+ " and AutomationID NOT Like '%Login%' and AutomationID NOT Like '%Logout%'";
					
					dataSetResultSet = QueryStatement.executeQuery(query);
					try{
						dataSetResultSet.beforeFirst();
						dataSetResultSet.next();
						TestCaseComponents = dataSetResultSet.getLong(1);
						
					}
					catch(Exception ex){
						ex.printStackTrace();
					}
					TotalComponents = TotalComponents + TestCaseComponents;
					
					query = "Select Count(*) From [Apttus_TestSuiteResults$] where DataSet ='" +currentDataSet + "' and Status Like '%Fail%'"
							+ " and AutomationID NOT Like '%Login%' and AutomationID NOT Like '%Logout%'";
					
					dataSetResultSet = QueryStatement.executeQuery(query);
					try{
						dataSetResultSet.beforeFirst();
						dataSetResultSet.next();
						TestCaseComponentsFailed =dataSetResultSet.getLong(1);
						
					}
					catch(Exception ex){
						ex.printStackTrace();
						TestCaseComponentsFailed = 0;
					}
					
					TotalComponentsFailed = TotalComponentsFailed + TestCaseComponentsFailed;
					
					TestCaseComponentsPassed = TestCaseComponents -  TestCaseComponentsFailed;
					
					TotalComponentsPassed += TestCaseComponentsPassed; 
					
					//Number of Steps
					QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					query = "Select Count(*) From [Apttus_TestCaseResults$] where CurrentDataSet ='" +currentDataSet + "'";
					
					System.out.println(query);
					try{
						
						dataSetResultSet = QueryStatement.executeQuery(query);
						dataSetResultSet.beforeFirst();
						dataSetResultSet.next();
						TestCaseStepsExecuted =dataSetResultSet.getLong(1);
						
					}
					catch(Exception ex){
						ex.printStackTrace();
						TestCaseStepsExecuted = 0;
					}
					
					TotalStepsExecuted += TestCaseStepsExecuted;
					
					query = "Select Count(*) From [Apttus_TestCaseResults$] where CurrentDataSet ='" +currentDataSet + "' and Status Like '%Fail%'";
					
					
					try{
						dataSetResultSet = QueryStatement.executeQuery(query);
						dataSetResultSet.beforeFirst();
						dataSetResultSet.next();
						TestCaseStepsFailed =dataSetResultSet.getLong(1);
						
					}
					catch(Exception ex){
						ex.printStackTrace();
						TestCaseStepsFailed = 0;
					}
					
					TotalStepsFailed += TestCaseStepsFailed;
					
					TestCaseStepsPassed = TestCaseStepsExecuted -TestCaseStepsFailed; 
					
					TotalStepsPassed += TestCaseStepsPassed;
					
					if(TestCaseComponentsFailed>0){
						//TODO Check Working
						query = "Select Count(*) From [Apttus_TestSuiteResults$] where DataSet ='" +currentDataSet + "' and Status Like '%Terminated%'"
								+ " and AutomationID NOT Like '%Login%' and AutomationID NOT Like '%Logout%'";
						
						dataSetResultSet = QueryStatement.executeQuery(query);
						try{
							dataSetResultSet.beforeFirst();
							dataSetResultSet.next();
							dataSetResultSet.getString("Status");
							currentStatus =  "Terminated";
						}
						catch(Exception ex){
							currentStatus = "Fail";
							//ex.printStackTrace();
						}
						
						totalTestCasesFailed +=1; 
					}
					else{
						currentStatus = "Pass";
						totalTestCasesPassed+=1;
					}
					
					String ComponentReport =".\\TestCase Reports\\" + currentDataSet + ".html";
					
					dashboard.append("\n\t<td>" + DataSetslNo +"</td>");
					dashboard.append("\n\t<td><a href='"+ ComponentReport +"'>" + currentDataSet +"</a></td>");
					//dashboard.append("\n\t<td>"+ currentDataSet +"</td>");
					dashboard.append("\n\t<td>" + currentStatus +"</td>");
					dashboard.append("\n\t<td>" + TestCaseComponents +"</td>");
					dashboard.append("\n\t<td>" + TestCaseComponentsPassed +"</td>");
					dashboard.append("\n\t<td>" + TestCaseComponentsFailed +"</td>");
					dashboard.append("\n\t<td>" + TestCaseStepsExecuted +"</td>");
					dashboard.append("\n\t<td>" + TestCaseStepsPassed +"</td>");
					dashboard.append("\n\t<td>" + TestCaseStepsFailed +"</td>");
					dashboard.append("\n</tr>");
					
					
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
		}

    	dashboard.append("</table>");
    	TotalStepsExecuted = TotalStepsPassed + TotalStepsFailed + TotalStepsWarning;
    	dashboard.append("<script>document.getElementById('TestPassed').innerHTML = " + totalTestCasesPassed + " </script>");
    	dashboard.append("<script>document.getElementById('TestFail').innerHTML = " + totalTestCasesFailed + " </script>");
    	dashboard.append("<script>document.getElementById('TestWarning').innerHTML = " + totalTestCasesWarnings + " </script>");
    	
    	dashboard.append("<script>document.getElementById('ComponentsPassed').innerHTML = " + TotalComponentsPassed + " </script>");
    	dashboard.append("<script>document.getElementById('ComponentsFail').innerHTML = " + TotalComponentsFailed + " </script>");
    	dashboard.append("<script>document.getElementById('ComponentsWarning').innerHTML = " + TotalComponentsWarning + " </script>");
    	
    	dashboard.append("<script>document.getElementById('StepsPassed').innerHTML = " + TotalStepsPassed + " </script>");
    	dashboard.append("<script>document.getElementById('StepsFail').innerHTML = " + TotalStepsFailed + " </script>");
    	dashboard.append("<script>document.getElementById('StepsWarning').innerHTML = " + TotalStepsWarning + " </script>");
    	
    	
		dashboard.append("\n</body>");
    	dashboard.append("\n</html>");
    	String Dashboard = EnvironmentSetup.strCurrentReportFilePath+"\\ExecutionDashboard.html";
		
		EnvironmentSetup.logger.info(Dashboard);
        
		try (FileOutputStream os = new FileOutputStream(Dashboard)){
        	
        	String finalHTML = dashboard.toString();
        	
        	os.write(finalHTML.getBytes(), 0, dashboard.length());
        	
        	os.flush();
        	os.close();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		dashboard.setLength(0);
		
    	
    }
    
    /**
     * 
	 */
	public static void createTestCaseHTMLReportApttus(String strDataSet){
		
		int currentComponentNumber = 0;
		String currentval = "";
		String components = fetchComponentNamesApttus();
		String[] Components = null;
		String currentComponent = "";
		ResultSet componentResultSet = null;
		
		int totalTestCasesPassed = 0;
    	int totalTestCasesFailed = 0;
    	int totalTestCasesWarnings = 0;
    	
    	String currentStatus = "";
		
		try{
			dashboard.append("<html><head><link rel='stylesheet' href='../style.css'></link>"
	    			+ "<script type='text/javascript' src='https://www.google.com/jsapi'></script>"
	    			+ "<script type='text/javascript'>"
	    			+ "google.load('visualization', '1', {packages:['corechart']});"
	    			+ "google.setOnLoadCallback(drawChart);"
	    			+ "function drawChart() {"
	    			+ "var pass = parseInt(document.getElementById('Passed').firstChild.nodeValue);"
	    			+ "var fail = parseInt(document.getElementById('Fail').firstChild.nodeValue);"
	    			+ "var warning = parseInt(document.getElementById('Warning').firstChild.nodeValue);"
	        		+ "var data = google.visualization.arrayToDataTable(["
	        		+ "['Status', 'No. Of Tests'],"
	        		+ "['Pass',     pass],"
	        		+ "['Fail',     fail],"
	        		+ "['Warning',  warning]"
	        		+ "]);"

	        		+ "var options = {"
	        		+ "pieHole: 0.5,"
	        		+ "pieSliceTextStyle: {"
	        		+ "color: 'none',"
	        		+"},"
	        		+ "legend: {alignment: 'center', position: 'right', textStyle: {color: 'black', fontSize: 16}},"
	        		+ "slices: {0: {color: 'rgb(0,176,80)'}, 1: {color: 'rgb(255,0,0)'}, 2: {color: 'rgb(255,192,0)'}}"
	        		+ "};"

	        		+ "var chart = new google.visualization.PieChart(document.getElementById('donut_single'));"
	        		+ "chart.draw(data, options);"
	        		+"}"
	        		+ "</script>"
	    			+"</head>");
	    	
	    	dashboard.append("\n<body>");
	    	dashboard.append("\n<div id='donut_single' style='width: 100%; height: 500px;'></div>");
	    	dashboard.append("\n<table style='width: 75%; alignment: center'>");
	    	dashboard.append("\n<tr class='headerRow'>"
				+ "\n<th>Sl. No</th>"
				+ "\n<th>Component Name</th>"
				+ "\n<th>Status</th>"
				+"\n</tr>");
	    	
			int j=0;
			if(components.contains(">>")){
				Components = components.split(">>");
				for(int i=0; i<Components.length;i++){
					currentComponentNumber = currentComponentNumber +1;
					ConnectToReportFile();
					currentComponent =Components[i].trim(); 
					
					if(i%2==0){
						dashboard.append("\n\t\t<tr class='dataRow even'>");
					}
					else{
						dashboard.append("\n\t\t<tr class='dataRow odd'>");
					}
					try{
						
						Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String query = "Select * From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where DataSet ='" + strDataSet + "' and Component='"+ currentComponent +"'";						   
						EnvironmentSetup.logger.info(query);
						componentResultSet = QueryStatement.executeQuery(query);
						
						try{
							componentResultSet.beforeFirst();
							if(componentResultSet.next()){
								currentStatus =  componentResultSet.getString("Status");
								j = j+1;
								dashboard.append("\n\t<td>" + j +"</td>");
								String ComponentReport = "..\\Component Reports\\" + strDataSet + "_" + currentComponent +".html";
								dashboard.append("\n\t<td><a href='"+ ComponentReport +"'>" + currentComponent +"</a></td>");
								dashboard.append("\n\t<td>");
								if(currentStatus.equalsIgnoreCase("Pass")){
									dashboard.append("\n\t\t\t\t<sp>");
									dashboard.append(currentStatus);
									dashboard.append("</sp>");
									totalTestCasesPassed = totalTestCasesPassed +1;
				            	}
				    			else if(currentStatus.equalsIgnoreCase("Warning")){
				    				dashboard.append("\n\t\t\t\t<sw>");
				    				dashboard.append(currentStatus);
				    				dashboard.append("</sw>");
				    				totalTestCasesWarnings = totalTestCasesWarnings+1;
				            	}
				    			else if(currentStatus.equalsIgnoreCase("Skipped")){
				    				dashboard.append("\n\t\t\t\t<sw>");
				    				dashboard.append(currentStatus);
				    				dashboard.append("</ss>");
				            	}
				            	else{
				            		dashboard.append("\n\t\t\t\t<sf>");
				            		dashboard.append(currentStatus);
				            		dashboard.append("</sf>");
				            		totalTestCasesFailed = totalTestCasesFailed +1;
				            	}
								dashboard.append("</td>");
								dashboard.append("\n</tr>");	
							}
							
						}
						catch(Exception ex){
							
						}
						
						CloseReportConnection();
					}
					catch(Exception ex1){
						
					}
					
				}
				
			}
			else{
				ConnectToReportFile();
				try{
					currentComponent = components;
					Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					String query = "Select * From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where DataSet ='" + strDataSet + "' and Component='"+ currentComponent +"'";						   
					EnvironmentSetup.logger.info(query);
					componentResultSet = QueryStatement.executeQuery(query);
					
					try{
						componentResultSet.beforeFirst();
						if(componentResultSet.next()){
							currentStatus =  componentResultSet.getString("Status");
							dashboard.append("\n\t<td>1</td>");
							String ComponentReport = "..\\Component Reports\\" + strDataSet + "_" + currentComponent +".html";
							dashboard.append("\n\t<td><a href='"+ ComponentReport +"'>" + currentComponent +"</a></td>");
							dashboard.append("\n\t<td>");
							if(currentStatus.equalsIgnoreCase("Pass")){
								dashboard.append("\n\t\t\t\t<sp>");
								dashboard.append(currentStatus);
								dashboard.append("</sp>");
								totalTestCasesPassed = totalTestCasesPassed +1;
			            	}
			    			else if(currentStatus.equalsIgnoreCase("Warning")){
			    				dashboard.append("\n\t\t\t\t<sw>");
			    				dashboard.append(currentStatus);
			    				dashboard.append("</sw>");
			    				totalTestCasesWarnings = totalTestCasesWarnings+1;
			            	}
			    			else if(currentStatus.equalsIgnoreCase("Skipped")){
			    				dashboard.append("\n\t\t\t\t<ss>");
			    				dashboard.append(currentStatus);
			    				dashboard.append("</ss>");
			    				
			            	}
			            	else{
			            		dashboard.append("\n\t\t\t\t<sf>");
			            		dashboard.append(currentStatus);
			            		dashboard.append("</sf>");
			            		totalTestCasesFailed = totalTestCasesFailed+1;
			            	}
							dashboard.append("</td>");
							dashboard.append("\n</tr>");	
						}
						
					}
					catch(Exception ex){
						EnvironmentSetup.logger.info(ex.toString());
					}
					
					CloseReportConnection();
				}
				catch(Exception ex1){
					EnvironmentSetup.logger.info(ex1.toString());
				}
			}
			dashboard.append("</table>");
			dashboard.append("<div>\n"
					+ "<table class='summary'>\n"
					+ "<tr><th class='headerRow'>Passed</th><th id='Passed'>"+ totalTestCasesPassed +"</th></tr>\n"
					+ "<tr><th class='headerRow'>Failed</th><th id='Fail'>"+totalTestCasesFailed +"</th></tr>\n"
					+ "<tr><th class='headerRow'>Warnings</th><th id='Warning'>"+ totalTestCasesWarnings +"</th></tr>\n"
					+ "</table>\n"
					+ "</div>");
			dashboard.append("\n</body>");
	    	dashboard.append("\n</html>");
	    	
			String Dashboard = EnvironmentSetup.strCurrentReportFilePath+"\\TestCase Reports\\"+strDataSet+".html";
			
			EnvironmentSetup.logger.info(Dashboard);
	        
			try (FileOutputStream os = new FileOutputStream(Dashboard)){
	        	
	        	String finalHTML = dashboard.toString();
	        	
	        	os.write(finalHTML.getBytes(), 0, dashboard.length());
	        	
	        	os.flush();
	        	os.close();
	        } catch (Exception e) {
	        	EnvironmentSetup.logger.info(e.toString());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			dashboard.setLength(0);
			
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info(ex.toString());
		}
	}
	
	/**Function to check status of last login Testcase
     * @param strPreReqAutomationID
     * @return
     * 
     * Author:Triveni Aroli
     * Reviewer: Sai
     * Review Changes Made: Removed beforeFirst & next() check
     */
    public static boolean checkLoginStatus(){
    	
    	ConnectToReportFile();
    	try{

        	ResultSet loginTestResultSet = null;
        	
        	Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        	String strQuery = "Select * From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where AutomationID like '%Login%'" ;
        	System.out.println(strQuery);
        	loginTestResultSet = QueryStatement.executeQuery(strQuery);
        	
        		loginTestResultSet.afterLast();
            	loginTestResultSet.previous();
            	String loginStatus = loginTestResultSet.getString("Status");
            	System.out.println(loginStatus);
            	
            	if(loginStatus.contains("Terminated")){
            		CloseReportConnection();
            		return true;
            	}
    	}
    	catch(Exception ex){
    		CloseReportConnection();
    		System.out.println(ex.toString());
    		return true;
    	}
    	
    	CloseReportConnection();
		return false;
    }
    
    /**
	 * 
	 */
    public static String fetchComponentNamesApttus(){
    	ConnectToReportFile();
    	String componentNames = "";
    	String currentComponent = "";
    	String query = "";
    	ResultSet componentResultSet = null;
    	String Status = "";
    	try{
 		   Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
 		   query = "Select Distinct Component From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where DataSet='" + EnvironmentSetup.strCurrentDataset + "'";
 		   
 		  /*query = "Select * From [" + EnvironmentSetup.strModuleName + "_TestSuiteResults$] where DataSet='" + EnvironmentSetup.strCurrentDataset + "'"
 	 		   		+ " ORDER BY SlNo";*/
 		   
 		   EnvironmentSetup.logger.info(query);
 		   
 		   componentResultSet = QueryStatement.executeQuery(query);
 		   componentResultSet.beforeFirst();
 		   while(componentResultSet.next()){
 			  currentComponent = componentResultSet.getString("Component");
 			   if(componentNames==""){
 				  componentNames = currentComponent;
 			   }
 			   else{
 				  componentNames = componentNames + ">>" + currentComponent;
 			   }
 		   }
 		   //componentReport
     	}
     	catch(Exception ex){
     		System.out.println(ex.toString());
     		ex.printStackTrace();
     		//return "";
     	}
     	
     	CloseReportConnection();
     	return componentNames;
    }
    
    /**
	 * 
	 */
    public static void createComponentReportsApttus(String Component, String DataSet){
    	
    	//String Components="";// = fetchComponentNamesApttus();
    	String currentComponent = Component;
    	String [] componentNames = null;
    	String query = "";
    	ResultSet componentResultSet = null;
    	int currentTestStepNumber = 0;
    	String currentval = "";
    	
    	try{
    		ConnectToReportFile();
    		
    		Statement QueryStatement = EnvironmentSetup.testReportConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    		query = "Select * From [" + EnvironmentSetup.strModuleName + "_TestCaseResults$] where CurrentDataSet='" + DataSet + "' and AutomationID='"+EnvironmentSetup.strAutomationID+"'";
		   
    		createComponentReportHeaderApttus(Component);
		   
		   EnvironmentSetup.logger.info("Created Header");
			
		   EnvironmentSetup.logger.info(query);
		   componentResultSet = QueryStatement.executeQuery(query);
		   componentResultSet.beforeFirst();
		   try{
			   while(componentResultSet.next()){
				   currentTestStepNumber = currentTestStepNumber +1;
				   if(currentTestStepNumber%2==0){
					   componentReport.append("\n\t\t<tr class='dataRow even'>");
					}
					else{
						componentReport.append("\n\t\t<tr class='dataRow odd'>");
					}
				   
				   EnvironmentSetup.logger.info("Creating Report");
				   componentReport.append("\n\t\t<tr class='dataRow'>");
				   int colCount = 13;
				   int statusCol=9;
				   
				   for(int i=1; i<=colCount; i++){
					   try{
						   currentval = componentResultSet.getString(i);
					   }
					   catch(Exception ex){
						   currentval = "";
					   }
					   if(currentval==null){
						   currentval = "";
					   }
					   if(i!=2 && i!=3){
						   componentReport.append("\n\t\t\t<td>");
				    		if(i==1){
				    			if(currentval.contains(".0")){
				    				currentval = currentval.replace(".0","");
				    			}
				    			componentReport.append("\n\t\t\t\t<sln>");
				    			componentReport.append(currentval);
				    			componentReport.append("</sln>");
				    		}
				    		else if (i == statusCol){
				    			if(currentval.equalsIgnoreCase("Pass")){
				    				componentReport.append("\n\t\t\t\t<sp>");
				    				componentReport.append(currentval);
				    				componentReport.append("</sp>");
				            	}
				            	else if (currentval.contains("Fail")){
				            		componentReport.append("\n\t\t\t\t<sf>");
				            		componentReport.append(currentval);
				            		componentReport.append("</sf>");
				            		EnvironmentSetup.blnTestCaseStatus = false;
				    				EnvironmentSetup.strTestCaseStatus = currentval;
				            	}
				            	else if (currentval.contains("Warning")){
				            		componentReport.append("\n\t\t\t\t<sw>");
				            		componentReport.append(currentval);
				            		componentReport.append("</sw>");
				            		if(!EnvironmentSetup.blnTestCaseStatus == false)
				            		{
				            			EnvironmentSetup.strTestCaseStatus = currentval;
				            		}
				            	}
				            	else{
				            		componentReport.append(currentval);
				            	}
				    		}
				    		else if (i == colCount){
				    			//screenshot link
				    			if(currentval != null || currentval!=""){
				    				if(currentval!=null){
				    					if(currentval.contains("Screenshots")){
				    						String[] arr = currentval.split("Screenshots");
						    				EnvironmentSetup.logger.info(Integer.toString(arr.length));
						    				EnvironmentSetup.logger.info(arr[0]);
						    				EnvironmentSetup.logger.info(arr[1]);
						    				arr[1] = arr[1].replace("\")", "");
						    				EnvironmentSetup.logger.info(arr[1]);
						    				
						    				arr[1] = "..\\Screenshots" + arr[1];
						    				componentReport.append("\n\t\t\t\t<a href =" + "\"" + arr[1] + "\"" + ">");
						    				componentReport.append("View");
						    				componentReport.append("\n\t\t\t\t</a>");
				    					}
				    					else if (currentval.contains("Downloaded")){
				    						String[] arr = currentval.split("Downloaded Files");
						    				EnvironmentSetup.logger.info(Integer.toString(arr.length));
						    				EnvironmentSetup.logger.info(arr[0]);
						    				EnvironmentSetup.logger.info(arr[1]);
						    				arr[1] = arr[1].replace("\")", "");
						    				EnvironmentSetup.logger.info(arr[1]);
						    				
						    				arr[1] = "..\\Downloaded Files" + arr[1];
						    				componentReport.append("\n\t\t\t\t<a href =" + "\"" + arr[1] + "\"" + ">");
						    				componentReport.append("View");
						    				componentReport.append("\n\t\t\t\t</a>");
				    					}
				    					else{
				    						componentReport.append("");
				    					}
					    				
					    			}
				    				else{
				    					componentReport.append("");
				    					componentReport.append("\n\t\t\t\t</a>");
				    				}
				    			}
				    		}
				    		
				    		else{
				    			componentReport.append(currentval);
				    		}
				    		
				    		componentReport.append("\n\t\t\t</td>\n");
					   }
					   
			    		
				   }
				   componentReport.append("\n\t\t</tr>");
			   }
		   }
		   catch(Exception ex1){
			   EnvironmentSetup.logger.info("Apttus Test Case Reports " + ex1.toString());
		   }
	
		   componentReport.append("\n\t</table>");
		   componentReport.append("\n\t</body>");
		   componentReport.append("\n</html>");
		   
		   String currentComponentReport = "\\Component Reports\\" + DataSet + "_" + Component +".html";
		   //}
		    
		   String ComponentfilePath = EnvironmentSetup.strCurrentReportFilePath + currentComponentReport;
	        try (FileOutputStream os = new FileOutputStream(ComponentfilePath)){
	        	
	        	String finalHTML = componentReport.toString();
	        	
	        	os.write(finalHTML.getBytes(), 0, componentReport.length());
	        	
	        	os.flush();
	        	os.close();
	        	//currentTCReportLocation = TestCasefilePath;
	        }
	        
	        componentReport.setLength(0);
	        QueryStatement.close();
	        CloseReportConnection();
	   }
	   catch(Exception ex){
		   EnvironmentSetup.logger.info("Apttus Test Case Reports " + ex.toString());
	   }
	}

}
;
