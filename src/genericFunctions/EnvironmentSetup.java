/**
 * 
 */
package genericFunctions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import executionDrivers.ExecutionController;
import genericFunctions.EnvironmentSetup;
import genericFunctions.TestArtifactConnections;


/**
 * @author Sai
 *
 */
public class EnvironmentSetup {
	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /F /IM ";
	
	//public static String strLogPath = ".\\Logs\\";
	public static String strLogPath = "./Logs/";
	public static String strCurrentLogName = "";
	public static Logger logger = null;
	
	//public static String strDataSheetName = "testdata_i18n";
	//public static String strDataSheetAddendumName = "testdataaddendum_i18n";
	//public static String strObjectSheetName = "objectproperties_i18n";
	public static String strDataSheetName = "testdata";
	public static String strDataSheetAddendumName = "testdataaddendum";
	public static String strObjectSheetName = "objectproperties";
	public static String strLineItemsSheetName = "lineitems";
	public static String strCurrentReportFile="";
	public static String strCurrentModuleName="";
	
	
	public static String strApplicationName = "";
	public static String environmentForexec = "";
	public static String browserForExec = "";
	
	//Added by Tejaswini
	//For making the testscripts work without anychanges
	public static String LineItemIdForExec = "";
	public static int lineItemCount=0;
	public static boolean UseLineItem = false;
	//Added by Tejaswini
	//For making the replace count in the XPath to work
	 public static int intGlobalLineItemCount=0;
	 //Added by Tejaswini
	 //For making part selection in the dropdowns
	 public static boolean selectByPartMatchInDropDown;
	 //Temp static variable created by Tejaswini - Needs to be removed 
	 public static ResultSet testDataResultSet;
	 public static String MRID ="";
	 public static String VisitID = "";
	 
	 public static int maxTimeOut = 0;

	/**
	 * Excel Related
	 */
	public static String strRootFolder = "";
	public static String strDataSheetPath = "";
	public static String strObjectSheetPath = "";
	public static String strReportFilePath = "";
	public static Connection testDataConnection;
	public static Connection lineItemDataConnection;
	public static Connection testObjectsConnection;
	public static Connection testReportConnection;
	
	/**New Variables for DB*/
	public static String dBConnectionString = "";
	public static Connection dBConnection;
	public static boolean useExcelSource;	
	public static String dBUsername="";
	public static String dBPassword="";
	public static String schemaName="";
	public static String currentReportID = "";
	public static int noOfDBConnections = 0;
	/**
	 * New Vars for TestNG
	 */
	public static String installationType="";  //TestNG, Excel, DB, TestLink etc
	public static String randNumForReport="";
	public static String URLforExec="";
	public static String ExecutionType="";
	public static String RunMode = "";
	public static final boolean testNG = true;
	
	/** Vars to be deleted/changed - ideally shouldn't be static*/
	
	public static String strMainWindowHandle = "";
	public static int windowsOpen = 0;
	public static String strCurrentScreenshotLocation = "";
	public static String strDownloadsPath = "";
	
	public static String strScreenshotsPath = "";
	public static String strCurrentLineItemDataSet = "";
	public static String strCurrentReportFilePath = "";

	public static int intSlNo = 0;
	
	public static String locale = "";
	
	public static String strTestStepStartTime = "";
	public static String strTestStepEndTime = "";
	public static String strDuration = "";
	public static String strCurrentDataset = "";
	public static String strTimeOutException = "";
	public static String strGlobalException = "";
	
	//**
	/**
	 * 
	 * @param Environment Variables for Module Level TCs
	 */	
	public static String moduleName = "";
	public static String testDataTableNameSuffix = "Inv";
	public static String dataToBeReplaced;
	public static boolean replaceData;
	
	//**
	/**
	 * 
	 */

	public static void initialSetup(String strCurrentModule){
		strCurrentModuleName = strCurrentModule;
		
		if(installationType.equalsIgnoreCase("testng_code")){
			
			try{
				InputStream inputStream = ClassLoader.class.getResourceAsStream("/ExecutionSettings.properties");
				Properties prop = new Properties();
				prop.load(inputStream);
				
				//TODO Update Based on Source
				useExcelSource = false;
				
				strApplicationName = prop.getProperty("ApplicationName");
				environmentForexec = prop.getProperty("Environment");
				browserForExec = prop.getProperty("Browser");
				ExecutionType = prop.getProperty("ExecutionType");
				strRootFolder = prop.getProperty("LocalTestArtefactPath");
				maxTimeOut = new Integer(prop.getProperty("MaxTimeOutInSeconds").trim()).intValue();
				locale = prop.getProperty("Locale");
				String dataSourceType = "";
				dataSourceType = prop.getProperty("DataSourceType");
				if (dataSourceType.equalsIgnoreCase("Excel")){
					useExcelSource = true;
				}
				
				if(useExcelSource){
					//strRootFolder = prop.getProperty("LocalTestArtefactPath");
					//strDataSheetPath = strRootFolder + "\\Test Data\\" + prop.getProperty("LocalDataSheetName");// + ".xlsx";
					strDataSheetPath = strRootFolder + "/Test Data/" + prop.getProperty("LocalDataSheetName");// + ".xlsx";
					//strObjectSheetPath = strRootFolder + "\\Objects\\" + prop.getProperty("LocalObjectSheetName");// + ".xlsx";
					strObjectSheetPath = strRootFolder + "/Objects/" + prop.getProperty("LocalObjectSheetName");// + ".xlsx";
					//strReportFilePath = EnvironmentSetup.strRootFolder + "\\Reports\\";
					strReportFilePath = EnvironmentSetup.strRootFolder + "/Reports/";
				}
				else{
					if(ExecutionType.trim().equalsIgnoreCase("Local")){
						dBConnectionString = prop.getProperty("LocalDBConnectionString");
					}
					else{
						dBConnectionString = prop.getProperty("GlobalDBConnectionString");
					}
					
					dBUsername = prop.getProperty("DBUsername");
					dBPassword = prop.getProperty("DBPassword");
					
					TestArtifactConnections.connectToDBSource();
				}
				
				moduleName = prop.getProperty("ModuleName");
				testDataTableNameSuffix = prop.getProperty("moduleNameSuffix");
				if (moduleName.equalsIgnoreCase("E2E")|| moduleName == null || moduleName ==""){
					//Do Nothing
				} else {
					strDataSheetName = strDataSheetName + testDataTableNameSuffix;
					strDataSheetAddendumName = strDataSheetAddendumName + testDataTableNameSuffix;
				}
				
				//VerificationLevel
				inputStream = ClassLoader.class.getResourceAsStream("/AppEnvironment.properties");
				prop = new Properties();
				prop.load(inputStream);
				
				URLforExec = prop.getProperty(strApplicationName + "_" + environmentForexec);
				// Modified By Tejaswini

				

				// Modified By Tejaswini				
				//environmentDetailsMap = new HashMap<>();
				Random r = new Random();
				randNumForReport = Integer.toString(r.nextInt((6000 - 0) + 1) + 0);
				
				currentReportID = "Report_" + environmentForexec + "_" +browserForExec + "_" + randNumForReport;
				createReportsFolder();
			}
			catch(Exception ex){
				
			}
			
		}
	}
	
	/**
	 * Create .log File
	 */
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
			logger = Logger.getLogger(ExecutionController.class.getName());
			strCurrentLogName = "Execution_" + new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date()) + ".log";
		}
		catch(Exception ex){
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
	
	
	public static void createReportsFolder(){
		String commonNaming = "";
		String strFolder = "";
		try{
			commonNaming = new SimpleDateFormat("yyyy_MMM_dd_HH_mm").format(new Date());
			//EnvironmentSetup.strCurrentReportFile
			//strFolder = ".\\Reports\\Report_"+commonNaming;
			strFolder = "./Reports/Report_"+commonNaming;
			File dir = new File(strFolder);
			boolean done = dir.mkdir();
			EnvironmentSetup.strCurrentReportFilePath = strFolder; 
			EnvironmentSetup.logger.info("Create Main Report Directory: " + done);
			//String cssStylesPath = ".\\Reports\\style.css";
			String cssStylesPath = "./Reports/style.css";
			//String stylesDestinationPath = strFolder + "\\style.css";
			String stylesDestinationPath = strFolder + "/style.css";
			
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
	    	//String strScreenshotsFolder = strFolder + "\\Screenshots";
			String strScreenshotsFolder = strFolder + "/Screenshots";
	    	//strScreenshotsPath = strScreenshotsFolder + "\\";
			strScreenshotsPath = strScreenshotsFolder + "/";
	    	
	    	dir = new File(strScreenshotsFolder);
			done = dir.mkdir();
			
	    	
	    	//Create Downloaded Files Folder
	    	//String strDownloadsFolder = strFolder + "\\Downloaded Files"  + "\\";
			String strDownloadsFolder = strFolder + "/Downloaded Files"  + "/";
	    	strDownloadsPath = strDownloadsFolder;
	    	
	    	dir = new File(strDownloadsFolder);
			done = dir.mkdir();
			
	    	//Create Test Case Reports Folder
	    	//String strTestCaseReportsFolder = strFolder + "\\TestCase Reports"  + "\\";
			String strTestCaseReportsFolder = strFolder + "/TestCase Reports"  + "/";
	    	
	    	dir = new File(strTestCaseReportsFolder);
			done = dir.mkdir();
	    	
			//Create Component Reports Folder
	    	//String strComponentReportsFolder = strFolder + "\\Component Reports"  + "\\";
			String strComponentReportsFolder = strFolder + "/Component Reports"  + "/";
	    	
	    	dir = new File(strComponentReportsFolder);
			done = dir.mkdir();
	    	
			
			EnvironmentSetup.logger.info("Create Module Report Directory: " + done);
			
			//String strReportFilePath = ".\\Reports\\Practo_ReportTemplate.xlsx";
			String strReportFilePath = "./Reports/Practo_ReportTemplate.xlsx";
	    	//commonNaming = "Report_" + new SimpleDateFormat("yyyy_MMM_dd_HH_mm").format(new Date());
	    	String strCurrentReportFileName = "Report_" + environmentForexec + "_" + browserForExec + commonNaming;
	    	//+"_ExecutionReport_"
	    	
	    	FileInputStream fileInputStream = new FileInputStream(strReportFilePath);
	    	
	    	//strCurrentReportFile = strCurrentReportFilePath + "\\" + strCurrentReportFileName+".xlsx"; 
	    	strCurrentReportFile = strCurrentReportFilePath + "/" + strCurrentReportFileName+".xlsx";
	    	FileOutputStream fileOutputStream = new FileOutputStream(new File(EnvironmentSetup.strCurrentReportFile));
	    	EnvironmentSetup.logger.info(EnvironmentSetup.strCurrentReportFile);
	    	FileChannel source = fileInputStream.getChannel();
	    	FileChannel destination = fileOutputStream.getChannel();
	    	
	    	destination.transferFrom(source, 0, source.size());
	    	
	    	fileInputStream.close();
	    	fileOutputStream.close();
		}
		catch(Exception ex){
			
		}
	}
	
	
	
	/**
	 * Kill Process
	 * @param strProcessName
	 */
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
			System.out.println(ex.toString());
		}
	}
}
