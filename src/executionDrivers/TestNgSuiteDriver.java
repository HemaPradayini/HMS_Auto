/**
 * 
 */
package executionDrivers;

import java.util.List;

import org.testng.TestNG;
import org.testng.collections.Lists;

import StaticConnection.Connector;
import genericFunctions.EnvironmentSetup;
import genericFunctions.ReportingFunctions;

/**
 * @author Sai
 *
 */
public class TestNgSuiteDriver {
	public void runTestNGSuites(){
		//Create Report Folder - With Date and time stamp
		try{
			EnvironmentSetup.createLogFile();
			EnvironmentSetup.initialSetup("Practo");
			EnvironmentSetup.strCurrentModuleName = "Practo";
			ReportingFunctions.createReportFolder();
	//		EnvironmentSetup.testDataConnection = Connector.getInstance().getTestDataConnection();
	//		EnvironmentSetup.testObjectsConnection = Connector.getInstance().getTestObjectConnection();
			EnvironmentSetup.killProcess("chromedriver.exe");
			EnvironmentSetup.killProcess("geckodriver.exe");
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info(ex.toString());
		}
		
		TestNG testNG = new TestNG();
		List<String> testSuites = Lists.newArrayList();
		//testSuites.add("C:\\Tejaswini\\Work\\LMC\\LMCIntFramework-20161221T044926Z\\LMCIntFrameworkPracto\\TestNG.xml");
		//testSuites.add("E:\\Alamelu\\Practo\\git\\Insta_HMS_automation\\TestNG.xml");
		testSuites.add("C:\\InstaHmsBatch\\Alamelu\\TestNG.xml");
	//	testSuites.add("C:\\InstaHmsBatch\\Reena\\TestNG.xml");
	//	testSuites.add("C:\\InstaHmsBatch\\Abhishek\\TestNG.xml");
	//	testSuites.add("C:\\InstaHmsBatch\\Hema\\TestNG.xml");
	//	testSuites.add("C:\\InstaHmsBatch\\Tejaswini\\TestNG.xml");
		testNG.setTestSuites(testSuites);
		testNG.run();
	}
	
}
