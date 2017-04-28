package StaticConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import genericFunctions.EnvironmentSetup;

//import GenericFunctions.EnvironmentSetup;

public class Connector {

private static Connector connector;
private static Connection testDataConnection = null;
private static Connection testObjectConnection = null;
//private static Connection testResultsConnection = null;

private Connector() {
}

//private static Connection c = null;

public synchronized static Connector getInstance() {
    if (connector == null) {
        connector = new Connector();
    }
    return connector;
}

public static Connection getTestDataConnection() {
    if (testDataConnection == null) {        
       try {
    	   String conString = "jdbc:mysql://127.0.0.1:3306/insta_hms_automation";
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			//Class.forName("com.mysql.jdbc.Driver");
			testDataConnection =  DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");			
			testDataConnection.setAutoCommit(true);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return testDataConnection;
    }
    	return testDataConnection;
    }

public static Connection getTestObjectConnection() {
    if (testObjectConnection == null) {        
       try {
    	   //String conString = "jdbc:mysql://127.0.0.1:3306/insta_hms_automation";
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			//Class.forName("com.mysql.jdbc.Driver");
			testObjectConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ EnvironmentSetup.strObjectSheetPath + ";readOnly=false");			
			testObjectConnection.setAutoCommit(true);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return testObjectConnection;
    }
    	return testObjectConnection;
    }

public static void closeConnection(){
	try {
		//c.commit();
		testObjectConnection.close();
		testDataConnection.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("Error closing connection");
	} finally {
		try {
			//c.commit();
			testObjectConnection.close();
			testDataConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Connections Closed");
		}		
	}
	
}
}