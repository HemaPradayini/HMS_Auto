package executionDrivers;


import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import StaticConnection.Connector;
import genericFunctions.EnvironmentSetup;

public class ExecutionController{

	public static void main(String[] args) {
		System.out.println("Started");
		Properties prop = new Properties();
		
		InputStream inputStream = ClassLoader.class.getResourceAsStream("/FrameworkInstallation.properties");
		
		if (inputStream != null) {
			try {
				prop.load(inputStream);
				EnvironmentSetup.installationType = prop.getProperty("InstallationType");
				System.out.println("Installaton Type :: " + EnvironmentSetup.installationType);
				TestNgSuiteDriver executeTests = new TestNgSuiteDriver();
				executeTests.runTestNGSuites();
			}
			catch(Exception ex){
				ex.printStackTrace();
				System.exit(1);
			} 
			/*finally{
				try {
					Connector.closeConnection();
					EnvironmentSetup.dBConnection.close();
					//EnvironmentSetup.testDataConnection.close();
					EnvironmentSetup.testDataQuery.close();
					//EnvironmentSetup.testObjectsConnection.close();
					EnvironmentSetup.testDataResultSet.close();
					EnvironmentSetup.testObjectsResultSet.close();
					EnvironmentSetup.testReportConnection.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}*/
		
		} else{
			System.out.println("Am here");
		}
	}
}

