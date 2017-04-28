package genericFunctions;


//import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.io.PrintStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

















import seleniumWebUIFunctions.KeywordExecutionLibrary;
import executionDrivers.TestSuiteDriver;

public class EnvironmentSetup {


	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /F /IM ";

	public static String strModuleName;
	public static String strRootFolder;
	public static String strCurrentReportFileName;
	public static String strCurrentReportFile;
	public static String strReportFilePath;
	public static String strMainReportFolderPath = "";
	public static String strCurrentReportFilePath = "";
	public static String strTemplateFileName = "ReportTemplate.xlsx";
	public static String strScreenshotsPath = "";
	public static String strDownloadsPath = "";
	public static String strEmailList = "";
	public static String strCurrentObjectName = "";
	public static String strGlobalException = "";
	public static String strLoginReusable = "";
	public static String strLoginDataSet = "";
	public static String strLogPath = ".\\Logs\\";
	public static String strCurrentLogName = "";
	public static Logger logger = null;
	public static String xmlDataRootPath = "";
	public static String xmlDataPath = "";
	public static boolean fetchDataFromXML = false;
	public static boolean XMLDataPresent = false;
	public static int childLineItemCount = 0;
	public static int currentChildLineItemNum = 0;
	public static String XMLTagName = "";
	public static Map<String, String> environmentDetailsMap = null;
	public static String strChildLineItemId = "";
	public static boolean internalLineItemsPresent = false;
	public static String strChildLineItemCriteria = "";
	public static String uniqueValues = "";

	public static String strUniqueValueField = "";
	public static int intGlobalLineItemCount = 0;
	public static String strParentReusableName = "";
	public static String strTestCaseStartTime = "";
	public static String strTestCaseEndTime = "";
	public static String strTestStepStartTime = "";
	public static String strTestStepEndTime = "";
	public static String strDuration = "";
	public static String strLineItemDataSet = "";
	public static String strPreviousLineItemDataSet = "";
	public static String strCurrentLineItemDataSet = "";
	public static String strCurrentScreenshotLocation = "";

	public static boolean blnTestCaseStatus;
	public static boolean blnProceedExec = true;
	public static boolean blnMultiLevelDataPresent = false;
	public static String strMultiLevelDataCriteria = "";
	public static String strProceedOnError = "";

	public static String strTestCaseStatus;
	public static String strTestStepstatus;
	public static String strActualResult;
	public static int intSlNoStart = 0;
	public static int intSlNo;
	public static int intTestSuiteSlNoStart = 0;
	public static int intTestSuiteSlNo;
	public static String strReusableName;
	public static String strAutomationID;

	public static Statement testDataQuery;
	public static Statement lineItemDataQuery;
	public static Statement lineItemDataUpdateQuery;
	public static Statement lineItemQuery;
	public static String strMainWindowHandle;
	public static int windowsOpen = 0;

	public static String [] arrLineItemCriteriaData = null;
	public static String strLineItemRefCriteriaColName = "";

	public static String strLineItemsCriteria = "";
	public static boolean blnInternalReusable = false;

	public static boolean preReqFail = false;

	public static Connection testDataConnection;
//	public static Connection testDataConnectionForUpdate;
	public static Connection lineItemDataConnection;
	public static Connection testObjectsConnection;
	public static Connection testSuiteConnection;
	public static Connection reusablesConnection;
	public static Connection testReportConnection;
	public static Connection testCasesConnection;
	public static Connection environmentSheetConnection;
	public static Connection cancelledOrdersConnection;
	public static Connection lineItemConnection;

	public static ResultSet testSuiteResultSet;
	public static ResultSet testCaseResultSet ;
	public static ResultSet reusablesResultSet;
	public static ResultSet testDataResultSet;
	public static ResultSet testObjectsResultSet;
	public static ResultSet environmentSheetResultSet;
	public static ResultSet lineItemDataResultSet;
	public static ResultSet lineItemResultSet;

	public static String strObjectSheetName = "";
	public static String strObjectSheetPath = "";
	public static String strDataSheetName = "";
//Added by Tejaswini
	public static String strAddendumDataSheetName = "TestDataAddendum";
	
	public static String strDataSheetPath = "";
	public static String strLineItemsSheetName = "";
	public static String strLineItemsSheetPath = "";
//Added by Tejaswini
	public static int  lineItemCount = 0;
	public static boolean replaceData = false;
	public static String dataToBeReplaced ="";
	
	public static String strTestSuiteName = "";

	public static String strTestSuitePath = "";
	public static String strTestCasesSheetName = "";
	public static String strTestCasesPath = "";

	public static String strReusablesPath = "";

	public static String strEnvironmentSheetName = "EnvironmentDetails";
	public static String strEnvironmentController = "Controller";
	public static String strEnvironmentSheetPath;

	public static String strTimeOutException = "";
	public static String strSalesForceURL = "";
	public static String strLUNAURL = "";
	public static String browserForExec="";
	public static String strEmailAddress = "";
	public static String strEmailPassword = "";

	public static String strAutomatnID=""; 
	public static String strDataSets = "";
	public static String strComponent = "";
	public static String strTestCaseName = "";
	public static String strCurrentDataset="";
	public static String strDependentTC = "";

	public static String strCurrentModuleName = "";
	public static String environmentForexec = "";
	public static String strApplicationName="";

	
	/**New Variables for DB*/
	
	public static Connection dBConnection;
	public static boolean useExcelScripts = true;

	/**
	 * New Vars for TestNG
	 */
	public static String installationType="";  //TestNG, Excel, DB, TestLink etc
	public static String randNumForReport="";
	public static String URLforExec="";
	
	
	/**
	 * added by Tejaswini to make LineItems to work 
	 */
	
	public static String LineItemIdForExec = "";
	public static String LineItemIdValueForExec = "";
	public static boolean UseLineItem = false;
	public static String MRID ="";
	public static String VisitID="";
	public static boolean selectByPartMatchInDropDown = false; // Added by tejaswini - For Text Partial Selection from dropdown 
	
	//public static String testScenarioId = "";
	/**
	 * Changes to include DB source: if(useExcelScripts==false){
	 * else part remains the same for excel based testing
	*/
	public static void initialSetup(String strCurrentModule){

		strCurrentModuleName = strCurrentModule;
		
		if(installationType.equalsIgnoreCase("testng_code")){
			strDataSheetName = "TestData";
			strObjectSheetName = "ObjectProperties";
			strLineItemsSheetName = "LineItems";
			
			try{
				InputStream inputStream = ClassLoader.class.getResourceAsStream("/ExecutionSettings.properties");
				Properties prop = new Properties();
				prop.load(inputStream);
				strRootFolder = prop.getProperty("LocalTestArtefactPath");
				//strDataSheetPath = strRootFolder + "\\Test Data\\" + prop.getProperty("LocalDataSheetName");
				strDataSheetPath = strRootFolder + "\\Test Data\\" + prop.getProperty("LocalDataSheetName") +".xlsx";
				//strObjectSheetPath = strRootFolder + "\\Objects\\" + prop.getProperty("LocalObjectSheetName");
				strObjectSheetPath = strRootFolder + "\\Objects\\" + prop.getProperty("LocalObjectSheetName") + ".xlsx";
				strReportFilePath = EnvironmentSetup.strRootFolder + "\\Reports\\";
				strApplicationName = prop.getProperty("ApplicationName");
				environmentForexec = prop.getProperty("Environment");
				browserForExec = prop.getProperty("Browser");
				
				inputStream = ClassLoader.class.getResourceAsStream("/AppEnvironment.properties");
				prop = new Properties();
				prop.load(inputStream);
				
				URLforExec = prop.getProperty(strApplicationName + "_" + environmentForexec);
				
				//environmentDetailsMap = new HashMap<>();
				Random r = new Random();
				randNumForReport = Integer.toString(r.nextInt((6000 - 0) + 1) + 0);
				
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
		else{
			if (useExcelScripts==false){
				//String randToAdd = Integer.toString(r.nextInt((6000 - 0) + 1) + 0);
				connectToDB();
				fetchExecutionDetails();
			}
			else{
				try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				}
				catch (Exception ex){
					ex.printStackTrace();
				}
	
				initializeGlobalParams();
	
				EnvironmentSetup.logger.info("Module Init: " + strCurrentModule);
				EnvironmentSetup.logger.info("Test Suite Init: " + strTestSuitePath);
	
				//Test Suite Connection
				try{
					testSuiteConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver "
							+ "(*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ strTestSuitePath + ";readOnly=false");
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info("Test Suite Connection Exception: " + ex.toString());
					ex.printStackTrace();
				}
	
				//Test Cases Connection
				try{
					testCasesConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver "
							+ "(*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ strTestCasesPath + ";readOnly=false");
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info("Test Cases Connection Exception: " + ex.toString());
					ex.printStackTrace();
				}
	
				//Reusables Connection
				try{
					reusablesConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver "
							+ "(*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ strReusablesPath + ";readOnly=false");
	
				}
				catch (Exception ex){
					ex.printStackTrace();
					EnvironmentSetup.logger.info("Test Report Connection Exception: " + ex.toString());
				}
			}
		}
	}
	
	public static void initializeGlobalParams(){
		//Environment Sheet Connection

		strTestSuiteName = strCurrentModuleName + "_TestSuite";
		strTestSuitePath = strRootFolder + "Test Suite\\"+ strTestSuiteName +".xlsx";
		strObjectSheetName = strCurrentModuleName + "_Objects";
		strObjectSheetPath = strRootFolder + "Objects\\"+strCurrentModuleName+"_Objects.xlsx";
		strDataSheetName = strCurrentModuleName + "_TestData";
		strDataSheetPath = strRootFolder + "Test Data\\"+strCurrentModuleName+"_TestData.xlsx";

		strLineItemsSheetName = strCurrentModuleName + "_LineItems";
		strLineItemsSheetPath = strRootFolder + "Test Data\\"+strCurrentModuleName+"_TestData.xlsx";
		strTestCasesSheetName = strCurrentModuleName+"_TestCases";
		EnvironmentSetup.logger.info(strTestCasesSheetName);
		strTestCasesPath = strRootFolder + "Test Cases\\"+strCurrentModuleName+"_TestCases.xlsx";

		strReusablesPath = strRootFolder + "Reusable Components\\"+strCurrentModuleName+"_Reusables.xlsx";

	}

	public static void fetchGlobalVariables(){
		String strQuery="";

		environmentDetailsMap=new HashMap<>();

		//Environment Sheet Connection
		System.out.println(strEnvironmentSheetPath);
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		try{
			
			environmentSheetConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ "E:\\Executable\\Practo\\Environment Details\\EnvironmentDetails.xlsx" + ";readOnly=false"); 
					/*DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver "
					+ "(*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ strEnvironmentSheetPath + ";readOnly=false");*/

		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Environment Sheet Connection Exception: " + ex.toString());
		}

		//Environment Sheet
		Statement QueryStatement = null;

		try{

			QueryStatement = environmentSheetConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		//read data from controller
		System.out.println(" Entering controller module ");

		strQuery = "Select * From [controller$]";

		try{
			environmentSheetResultSet = QueryStatement.executeQuery(strQuery);
		}

		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Creating Query Statement to Envt: " + ex.toString());
		}

		try{
			environmentSheetResultSet.beforeFirst();
			while (environmentSheetResultSet.next()){
				environmentDetailsMap.put(environmentSheetResultSet.getString("Parameter"), environmentSheetResultSet.getString("Details"));
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Get Application Name and URl: " + ex.toString());
		}

		//Assign values to global variables-Environment and Browser
		environmentForexec=environmentDetailsMap.get("Environment");
		browserForExec=environmentDetailsMap.get("Browser");

		System.out.println("Environment to execute is "+ environmentForexec);
		System.out.println("Environment to execute is "+ browserForExec);

		//read data from Environment Details
		System.out.println(" Entering Environment Details module ");
		strQuery = "Select * From [EnvironmentDetails$] where EnvironmentName = '"+ environmentForexec +"'";

		try{
			environmentSheetResultSet = QueryStatement.executeQuery(strQuery);
		}

		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Creating Query Statement to Envt: " + ex.toString());
		}

		try{
			environmentSheetResultSet.beforeFirst();
			while (environmentSheetResultSet.next()){
				environmentDetailsMap.put(environmentSheetResultSet.getString("ApplicationName"), environmentSheetResultSet.getString("URL"));
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Get Application Name and URl: " + ex.toString());
		}


		try{
			environmentSheetResultSet.close();;
		}

		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Get Browser Name: " + ex.toString());
		}
		//Closing Environment Sheet Connection
		try{
			environmentSheetConnection.close();

		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Closing Environment Sheet Connection Exception: " + ex.toString());
		}

	}
	
	/**
	 * Changes to include DB source: if(useExcelScripts==false){
	 * else part remains the same for excel based testing
	*/
	
	public static void teardown(){

		//Terminate ChromeDriver
		if(EnvironmentSetup.browserForExec.equalsIgnoreCase("chrome")){
			killProcess("chromedriver");
		}
		else if(EnvironmentSetup.browserForExec.toUpperCase().contains("FOX")){
			try{
				KeywordExecutionLibrary.driver.quit();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}

		}
		if(useExcelScripts==false){
			try{
				dBConnection.close();
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Close Test Suite Connection Exception: " + ex.toString());
			}
		}
		else{
			//Test Suite Connection
			try{
				testSuiteConnection.close();
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Close Test Suite Connection Exception: " + ex.toString());
			}
	
			//Test Cases Connection
			try{
				testCasesConnection.close();
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Close Test Cases Connection Exception: " + ex.toString());
			}
	
			//Test Data Connection
			try{
				testDataConnection.close();
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Close Test Data Connection Exception: " + ex.toString());
			}
	
			//Objects Sheet Connection
			try{
				testObjectsConnection.close();
	
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Close Objects Sheet Connection Exception: " + ex.toString());
			}
	
			//Test Reports Connection
			try{
				testReportConnection.close();
	
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Close Test Report Connection Exception: " + ex.toString());
			}
	
			//Reusables Connection
			try{
				reusablesConnection.close();
	
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Close Reusables Connection Exception: " + ex.toString());
			}
	
			//Reusables Connection
			try{
				environmentSheetConnection.close();
	
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Close Reusables Connection Exception: " + ex.toString());
			}
		}
	}

	public static void createLogFile(){

		boolean done;
		File dir;
		FileHandler fh;
		String strFolder = strLogPath;
		System.out.println(strLogPath);
		try{
			dir = new File(strFolder);
			done = dir.mkdir();
			System.out.println(done);
			logger = Logger.getLogger(TestSuiteDriver.class.getName());  
			logger.setUseParentHandlers(false);
			strCurrentLogName = "Execution_" + new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date()) + ".log";
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex.toString());
		}
		String log = strLogPath + strCurrentLogName;

		try {  
			fh = new FileHandler(log);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);  
			logger.info("Starting Executions");  

		} catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		} 

	}

	public static void sendExecutionEmails(String strEmailType){

		/*final String username = "user@akamai.com";
		final String password = "password";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.akamai.com");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			//message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(strEmailList));

			if(strEmailType.contains("Initial")){
				message.setSubject("Starting Executions For: " + strModuleName);
				message.setText("Auto Generated Email, Starting Executions For " + strModuleName);
			}
			else{
				String computerPath = "";
				message.setSubject(strModuleName + " Executions Complete");
				//message.setText(
				try{
					java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
					System.out.println("Hostname of local machine: " + localMachine.getHostName());
					computerPath = localMachine.getCanonicalHostName();
				}
				catch(Exception ex){
					System.out.println(ex.toString());
				}
				message.setText(strModuleName + " Executions Complete on Machine: " + computerPath
						+ " \n Reports can be found in: " + strCurrentReportFilePath );
			}

			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			ex.printStackTrace();
		}*/
	}

	public static void killProcess(String strProcessName){
		try{

			Process p = Runtime.getRuntime().exec(TASKLIST);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			if(reader.readLine() != null){
				Runtime.getRuntime().exec(KILL + strProcessName);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex.toString());
		}
	}
	
	/**
	 * New Methods for DB Source
	*/
	public static void connectToDB(){
		//Initialize dbConnection
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		
		try{
			dBConnection = DriverManager.getConnection("jdbc:mysql://localhost/" + strModuleName,
            		"root", "root");
		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Test Suite Connection Exception: " + ex.toString());
		}
	}
	
	public static void fetchExecutionDetails(){
		String strQuery="";
		Statement QueryStatement = null;
		
		try{
			QueryStatement = dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		
		strQuery = "Select Details From Controller Where Parameter = 'Browser'";
		
		try{
		   environmentSheetResultSet = QueryStatement.executeQuery(strQuery);
		   environmentSheetResultSet.beforeFirst();
		   environmentSheetResultSet.next();
		   browserForExec = environmentSheetResultSet.getString("Details").trim();
		   System.out.println(browserForExec);
		}
		catch (Exception ex){
			ex.printStackTrace();
		   EnvironmentSetup.logger.info("Creating Query Statement to Envt: " + ex.toString());
		}
		
		strQuery = "Select * From Controller Where Parameter = 'Environment'";
		
		try{
		   environmentSheetResultSet = QueryStatement.executeQuery(strQuery);
		   environmentSheetResultSet.beforeFirst();
		   environmentSheetResultSet.next();
		   environmentForexec = environmentSheetResultSet.getString("Details");
		}
		catch (Exception ex){
			ex.printStackTrace();
		   EnvironmentSetup.logger.info("Creating Query Statement to Envt: " + ex.toString());
		}
		
		strQuery = "Select * From EnvironmentDetails Where EnvironmentName = '"+ environmentForexec +"'";
		
		try{
		   environmentSheetResultSet = QueryStatement.executeQuery(strQuery);
		}
		catch (Exception ex){
			ex.printStackTrace();
		   EnvironmentSetup.logger.info("Creating Query Statement to Envt: " + ex.toString());
		}
		
		environmentDetailsMap = new HashMap<>();
		   
	   try{
		   environmentSheetResultSet.beforeFirst();
		   while (environmentSheetResultSet.next()){
			   environmentDetailsMap.put(environmentSheetResultSet.getString("ApplicationName"), environmentSheetResultSet.getString("URL"));
		   }
		   
	   }
	   catch (Exception ex){
		   ex.printStackTrace();
	   }
		
	}
}
