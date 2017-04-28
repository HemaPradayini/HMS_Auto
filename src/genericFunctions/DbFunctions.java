package genericFunctions;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

public class DbFunctions {
	
	private static String strDBQuery;
	
	//FetchData(TestSuite,"*","ModuleName_TestSuite","ExecutionRequired","Yes")
	public int getRowCount(String dataSet){

		Connection testConnection = null;
		ResultSet testResultSet = null;
		int rowCount = 0;
		String strQuery;
		Statement QueryStatement = null;
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
			QueryStatement = testConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			strQuery = "SELECT count(*) as rowCount from [TestData$] where LineItemID LIKE '%" + EnvironmentSetup.LineItemIdForExec + "%' AND DataSet = '" + dataSet + "'";
			testResultSet = QueryStatement.executeQuery(strQuery);
			testResultSet.beforeFirst();							
			if (testResultSet.next()){
				rowCount = testResultSet.getInt("rowCount");				
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
		} finally {
			try {
				if (!(testConnection.isClosed()))
					testConnection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rowCount;
	}
	public HashMap<String, String> getOrdersToCancel(String dataSet){
		Connection testConnection = null;
		ResultSet testResultSet = null;
		int rowCount = 0;
		String strQuery;
		Statement QueryStatement = null;
		HashMap <String, String>  ordersToCancel= new HashMap  <String, String> ();
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
			QueryStatement = testConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			strQuery = "SELECT Item, ItemCancelFlag from [TestData$] where LineItemID LIKE '%" + EnvironmentSetup.LineItemIdForExec + "%' AND DataSet = '" + dataSet + "'";
			testResultSet = QueryStatement.executeQuery(strQuery);
			testResultSet.beforeFirst();
			String cancelFlag ="";
			String itemToCancel = "";
			while (testResultSet.next()){
				cancelFlag = testResultSet.getString("ItemCancelFlag");
				itemToCancel =testResultSet.getString("Item");
				ordersToCancel.put(itemToCancel, cancelFlag);
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
		} finally{
			try {
				if (!(testConnection.isClosed()))
					testConnection.close();			
			} catch (SQLException e) {
					//TODO Auto-generated catch block
					e.printStackTrace();
			}							
		}
		return ordersToCancel;
	}
	public void saveMRIDVisitID(String dataSet){
		//EnvironmentSetup.MRID = "MR013119";
		System.out.println("MRID is :: " + EnvironmentSetup.MRID);
		HashMap <String, String>  ordersToCancel= new HashMap  <String, String> ();
		try{
			saveData(dataSet, "TestData");
		}	catch (SQLException sqlE) { 
				System.out.println("SQL Exception is :: " + sqlE.toString());
				if (sqlE.toString().contains("[Microsoft][ODBC Excel Driver] Too few parameters. Expected 1")){
					try {
						saveData(dataSet, "TestDataAddendum");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.println("SQL Exception is :: " + e.toString());
						e.printStackTrace();
					}
				}
		}	catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
		} finally{
/*			try {
				if (testResultSet !=null)
					testResultSet.close();
				if (QueryStatement !=null)
					QueryStatement.close();
				if (testConnection !=null)
					testConnection.close();;			
			} catch (SQLException e) {
				System.out.println("***ERROR CLOSING DB CONNECTIONS***");
			}*/
		}
		}
	
	public void storeClaimsBatchSubmissionId(String batchId, String dataSet){
		Connection testConnection = null;
		ResultSet testResultSet = null;
		int rowCount = 0;
		String strQuery;
		Statement QueryStatement = null;
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
			QueryStatement = testConnection.createStatement();
			strQuery = "UPDATE [TestData$] SET ClaimsBatchSubmissionId = '" + batchId + "'where DataSet = '" + dataSet + "'";
			int sqlCode = QueryStatement.executeUpdate(strQuery);
			testConnection.commit();
		}	catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Storing Claims Submission Id: " + ex.toString());
		} finally{
			try {
				if (testResultSet !=null)
					testResultSet.close();
				if (QueryStatement !=null)
					QueryStatement.close();
				if ( testConnection !=null)
					testConnection.close();;				
			} catch (SQLException e) {
				System.out.println("***ERROR CLOSING DB CONNECTIONS***");
			}							
		}		
	}
	public void storeDate(String dataSet, String excelColName, String currentOrNext, int numberOfDays){
		Connection testConnection = null;
		ResultSet testResultSet = null;
		int rowCount = 0;
		String strQuery;
		String systemDate;
		Statement QueryStatement = null;
		if (currentOrNext == "C"){
			systemDate = CommonUtilities.getSystemDate("dd-MM-yyyy");
			System.out.println(systemDate);
		}
		else {
			systemDate = CommonUtilities.getNextSystemDate("dd-MM-yyyy", numberOfDays);
			System.out.println(systemDate);
		}
		try{
			saveDateData(dataSet, "TestData",excelColName,systemDate);
		}	catch (SQLException sqlE) { 
				System.out.println("SQL Exception is :: " + sqlE.toString());
				if (sqlE.toString().contains("[Microsoft][ODBC Excel Driver] Too few parameters. Expected 1")){
					try {
						saveDateData(dataSet, "TestDataAddendum",excelColName,systemDate);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.println("SQL Exception is :: " + e.toString());
						e.printStackTrace();
					}
				}
		}	catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
		}
		
		
	}
	/*
		public void storeDateForCalendarClick(String dataSet, String excelColName){
			Connection testConnection = null;
			ResultSet testResultSet = null;
			int rowCount = 0;
			String strQuery;
			String systemDate;
			Statement QueryStatement = null;
			systemDate = CommonUtilities.getSystemDateUSFormat("dd-MM-yyyy");
			System.out.println(systemDate);
			
			
			try{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
				testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
						+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
				QueryStatement = testConnection.createStatement();
				strQuery = "UPDATE [TestData$] SET " + excelColName + "=" + "Select " + "'" + systemDate + "'where DataSet = '" + dataSet + "'";
				int sqlCode = QueryStatement.executeUpdate(strQuery);
				testConnection.commit();
			}	catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Storing Claims Submission Id: " + ex.toString());
			} finally{
				try {
					if (testResultSet !=null)
						testResultSet.close();
					if (QueryStatement !=null)
						QueryStatement.close();
					if ( testConnection !=null)
						testConnection.close();;				
				} catch (SQLException e) {
					System.out.println("***ERROR CLOSING DB CONNECTIONS***");
				}							
			}		
		}
		*/
		private void saveData(String dataSet, String testDataSheet) throws SQLException {
		Connection testConnection = null;
		ResultSet testResultSet = null;
		int rowCount = 0;
		String strQuery;
		Statement QueryStatement = null;

		try{
			if (EnvironmentSetup.testDataResultSet != null){
				EnvironmentSetup.testDataResultSet.close();
			}
			if (EnvironmentSetup.testDataConnection != null){
				EnvironmentSetup.testDataConnection.close();
			}
		} catch (Exception e){
			System.out.println("Error closing Env testDataResultSet and testDataConnection");
		}
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
				+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
		testConnection.setAutoCommit(true);
		QueryStatement = testConnection.createStatement();
		strQuery = "UPDATE [" + testDataSheet + "$] SET MRID = '" + EnvironmentSetup.MRID + "', VisitID = '" + EnvironmentSetup.VisitID + "' where DataSet = '" + dataSet + "'";
		System.out.println("Update Query ::" + strQuery);
		int sqlCode = QueryStatement.executeUpdate(strQuery);
		System.out.println("SQL return code is :: " + sqlCode);
		String MRID = "";
	     String sql = "SELECT MRID FROM [TestData$] where DataSet = '" + dataSet + "	'";
	      ResultSet rs = QueryStatement.executeQuery(sql);

	      while(rs.next()){
	    	  MRID = rs.getString("MRID");
	      }
	      
	         //Display values
	         System.out.print("MRID: " + MRID);
		//testConnection.commit();
		if (testResultSet !=null)
			testResultSet.close();
		if (QueryStatement !=null)
			QueryStatement.close();
		if (testConnection !=null)
			testConnection.close();;

	}
		private void saveDateData(String dataSet, String testDataSheet, String colName, String sysDate) throws SQLException {
			Connection testConnection = null;
			ResultSet testResultSet = null;
			int rowCount = 0;
			String strQuery;
			Statement QueryStatement = null;
			try{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			}	catch (ClassNotFoundException ex){
				ex.printStackTrace();
			}
				testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
						+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");

				//EnvironmentSetup.logger.info("Storing Claims Submission Id: " + ex.toString());
			 
				QueryStatement = testConnection.createStatement();
				strQuery = "UPDATE [" + testDataSheet + "$] SET " + colName + "=" + "'" + sysDate + "' where DataSet = '" + dataSet + "'";
				int sqlCode = QueryStatement.executeUpdate(strQuery);
				testConnection.commit();
			/*	finally{
				try {
					if (testResultSet !=null)
						testResultSet.close();
					if (QueryStatement !=null)
						QueryStatement.close();
					if ( testConnection !=null)
						testConnection.close();;				
				} catch (SQLException e) {
					System.out.println("***ERROR CLOSING DB CONNECTIONS***");
				}							
			}*/
		}
		
		public String monthYear(){
			String monthName="";
			Date date = new Date();
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int month = localDate.getMonthValue();
			int year = localDate.getYear();
			
			switch (month){
			case	1:
				monthName = "January";
			break;
			case   2:
				monthName = "February";
				break;
			case	3:
				monthName = "March";
			break;
			case   4:
				monthName = "April";
				break;
			case	5:
				monthName = "May";
			break;
			case   6:
				monthName = "June";
				break;
			case	7:
				monthName = "July";
			break;
			case   8:
				monthName = "August";
				break;
			case	9:
				monthName = "September";
			break;
			case   10:
				monthName = "October";
				break;
			case	11:
				monthName = "November";
			break;
			case   12:
				monthName = "December";
				break;
			}
			String dateFormat = monthName+year;
			return dateFormat;
		}

		public void storeSalesPageBillType(String salesPageBillType, String dataSet){
			Connection testConnection = null;
			ResultSet testResultSet = null;
			int rowCount = 0;
			String strQuery;
			Statement QueryStatement = null;
			try{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
				testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
						+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
				QueryStatement = testConnection.createStatement();
				strQuery = "UPDATE [TestData$] SET SalesPageBillType = '" + salesPageBillType + "'where DataSet = '" + dataSet + "'";
				int sqlCode = QueryStatement.executeUpdate(strQuery);
				testConnection.commit();
			}	catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Storing SalesPageBillType: " + ex.toString());
			} finally{
				try {
					if (testResultSet !=null)
						testResultSet.close();
					if (QueryStatement !=null)
						QueryStatement.close();
					if ( testConnection !=null)
						testConnection.close();;				
				} catch (SQLException e) {
					System.out.println("***ERROR CLOSING DB CONNECTIONS***");
				}							
			}		
		}

		public String getInsuranceDetails(String dataSet,String centreAcc){
			Connection testConnection = null;
			ResultSet testResultSet = null;
			int rowCount = 0;
			String strQuery;
			String downloadedFile="";
			Statement QueryStatement = null;
			// changed 23Mar
			DbFunctions dbfunc = new DbFunctions();
			String month= dbfunc.monthYear();
			
			try{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
				testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
						+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
				QueryStatement = testConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				strQuery = "SELECT PrimaryInsuranceCo,PrimarySponsorName,InsurancePlanType,InsurancePlanName from [TestData$] where DataSet = '" + dataSet + "'";
				
				testResultSet = QueryStatement.executeQuery(strQuery);
				testResultSet.beforeFirst();
				String PrimaryInsuranceCo ="";
	            String PrimarySponsorName="";
	            String InsurancePlanType="";
	            String InsurancePlanName="";
	            
				while (testResultSet.next()){
					
	                      PrimaryInsuranceCo =testResultSet.getString("PrimaryInsuranceCo");
	                      PrimarySponsorName =testResultSet.getString("PrimarySponsorName");
	                      InsurancePlanType= testResultSet.getString("InsurancePlanType");
	                      InsurancePlanName=testResultSet.getString("InsurancePlanName");
	                    //  getInsuranceDetails.put(PrimaryInsuranceCo,PrimarySponsorName,InsurancePlanType,InsurancePlanName);
	                      //downloadedFile = PrimaryInsuranceCo+ "-" +PrimarySponsorName+"-All-"+month+"-"+InsurancePlanType+"-"+InsurancePlanName+"- Account Group -"+centreAcc;
	                      downloadedFile = PrimaryInsuranceCo+ "-" +PrimarySponsorName+"-All-"+month+"-"+InsurancePlanType+"-"+InsurancePlanName+centreAcc;
	                		
	                      break;
				}
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
			} finally{
				try {
					if (!(testConnection.isClosed()))
						testConnection.close();			
				} catch (SQLException e) {
						//TODO Auto-generated catch block
						e.printStackTrace();
				}							
			}
			//DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Account Group -Pharmacy
			System.out.println(downloadedFile);
			return downloadedFile;
			
			}
		
		public String getSecondaryInsuranceDetails(String dataSet,String centreAcc){
			Connection testConnection = null;
			ResultSet testResultSet = null;
			int rowCount = 0;
			String strQuery;
			String downloadedFile="";
			Statement QueryStatement = null;
			// changed 23Mar
			DbFunctions dbfunc = new DbFunctions();
			String month= dbfunc.monthYear();
			
			try{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
				testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
						+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
				QueryStatement = testConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				strQuery = "SELECT SecondaryInsuranceCo,SecondarySponsorName,SecondaryInsurancePlanType,SecondaryInsurancePlanName from [TestData$] where DataSet = '" + dataSet + "'";
				
				testResultSet = QueryStatement.executeQuery(strQuery);
				testResultSet.beforeFirst();
				String SecondaryInsuranceCo ="";
	            String SecondarySponsorName="";
	            String SecondaryInsurancePlanType="";
	            String SecondaryInsurancePlanName="";
	            
				while (testResultSet.next()){
					
					SecondaryInsuranceCo =testResultSet.getString("SecondaryInsuranceCo");
					SecondarySponsorName =testResultSet.getString("SecondarySponsorName");
					SecondaryInsurancePlanType= testResultSet.getString("SecondaryInsurancePlanType");
					SecondaryInsurancePlanName=testResultSet.getString("SecondaryInsurancePlanName");
	                downloadedFile = SecondaryInsuranceCo+ "-" +SecondarySponsorName+"-All-"+month+"-"+SecondaryInsurancePlanType+"-"+SecondaryInsurancePlanName+centreAcc;
	                		
	                      break;
				}
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
			} finally{
				try {
					if (!(testConnection.isClosed()))
						testConnection.close();			
				} catch (SQLException e) {
						//TODO Auto-generated catch block
						e.printStackTrace();
				}							
			}
			//DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Account Group -Pharmacy
			System.out.println(downloadedFile);
			return downloadedFile;
			
			}
		
		public String getInsuranceDetailsForResubmission(String dataSet,String centreAcc){
			Connection testConnection = null;
			ResultSet testResultSet = null;
			int rowCount = 0;
			String strQuery;
			String downloadedFile="";
			Statement QueryStatement = null;
			// changed 23Mar
			DbFunctions dbfunc = new DbFunctions();
			String month= dbfunc.monthYear();
			
			try{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
				testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
						+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
				QueryStatement = testConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				strQuery = "SELECT PrimaryInsuranceCo,PrimarySponsorName,InsurancePlanType,InsurancePlanName from [TestData$] where DataSet = '" + dataSet + "'";
				
				testResultSet = QueryStatement.executeQuery(strQuery);
				testResultSet.beforeFirst();
				String PrimaryInsuranceCo ="";
	            String PrimarySponsorName="";
	            String InsurancePlanType="";
	            String InsurancePlanName="";
	            
				while (testResultSet.next()){
					
	                      PrimaryInsuranceCo =testResultSet.getString("PrimaryInsuranceCo");
	                      PrimarySponsorName =testResultSet.getString("PrimarySponsorName");
	                      InsurancePlanType= testResultSet.getString("InsurancePlanType");
	                      InsurancePlanName=testResultSet.getString("InsurancePlanName");
	                    //  getInsuranceDetails.put(PrimaryInsuranceCo,PrimarySponsorName,InsurancePlanType,InsurancePlanName);
	                      //downloadedFile = PrimaryInsuranceCo+ "-" +PrimarySponsorName+"-All-"+month+"-"+InsurancePlanType+"-"+InsurancePlanName+"- Account Group -"+centreAcc;
	                      downloadedFile = "Resubmission-"+PrimaryInsuranceCo+ "-" +PrimarySponsorName+"-All-"+month+"-"+InsurancePlanType+"-"+InsurancePlanName+centreAcc;
	                		
	                      break;
				}
			}
			catch (Exception ex){
				ex.printStackTrace();
				EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
			} finally{
				try {
					if (!(testConnection.isClosed()))
						testConnection.close();			
				} catch (SQLException e) {
						//TODO Auto-generated catch block
						e.printStackTrace();
				}							
			}
			//DAMAN-National Health Insurance Company-National Health Insurance Company (DAMAN)-ALL-March2017-DAMAN - NW1-DAMAN - NW1 - GLO.  (C0,D0,Dn100,Ch0)- Account Group -Pharmacy
			System.out.println(downloadedFile);
			return downloadedFile;
			
			}
		public void storeGRN(String GRNNo, String dataSet){
		Connection testConnection = null;
		ResultSet testResultSet = null;
		int rowCount = 0;
		String strQuery;
		Statement QueryStatement = null;
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
			QueryStatement = testConnection.createStatement();
			strQuery = "UPDATE [TestData$] SET GRNNO = '" + GRNNo + "'where DataSet = '" + dataSet + "'";
			int sqlCode = QueryStatement.executeUpdate(strQuery);
			testConnection.commit();
		}	catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Storing GRNNO: " + ex.toString());
		} finally{
			try {
				if (testResultSet !=null)
					testResultSet.close();
				if (QueryStatement !=null)
					QueryStatement.close();
				if ( testConnection !=null)
					testConnection.close();;				
			} catch (SQLException e) {
				System.out.println("***ERROR CLOSING DB CONNECTIONS***");
			}							
		}		
		}			
	}