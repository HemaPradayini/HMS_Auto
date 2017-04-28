package genericFunctions;

import java.sql.*;

public class DatabaseFunctions {
	
	private static String strDBQuery;
	
	//FetchData(TestSuite,"*","ModuleName_TestSuite","ExecutionRequired","Yes")
	public static void FetchData(String strFilePath, String strTargetColumnName, String strTableName, String strConditionColumnName, String strQueryCriteria){

		String strQuery;
		Statement QueryStatement = null;
		EnvironmentSetup.logger.info("TableName: " + strTableName);
		/*
		 *Added by Tejaswini for making lineItems work 
		 */
		boolean objectQuery = false;
		//if ((EnvironmentSetup.UseLineItem)&&!(strFilePath.toUpperCase().contains("OBJECT"))){
		//	strQueryCriteria = EnvironmentSetup.LineItemIdForExec;
		//}
		if (strFilePath.toUpperCase().contains("OBJECT")){
			objectQuery = true;
		}
		strQuery = BuildQuery(strTargetColumnName, strTableName, strConditionColumnName, strQueryCriteria, objectQuery);

		if (strFilePath.toUpperCase().contains("OBJECT")){

			try{
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
				EnvironmentSetup.testObjectsConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
						+ "DBQ="+ EnvironmentSetup.strObjectSheetPath + ";readOnly=false");
			}
			catch (Exception ex1){
				EnvironmentSetup.logger.info("Reopening Objects Connection: " + ex1.toString());
			}

			try{
				QueryStatement = EnvironmentSetup.testObjectsConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			}

			catch (NullPointerException ex){
				try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
					EnvironmentSetup.testObjectsConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
							+ "DBQ="+ EnvironmentSetup.strObjectSheetPath + ";readOnly=false");

					QueryStatement = EnvironmentSetup.testObjectsConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				}
				catch (Exception ex1){
					EnvironmentSetup.logger.info("Reopening Object Connection: " + ex1.toString());
				}
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Creating Query Statement to Objects: " + ex.toString());
			}

			try{
				EnvironmentSetup.testObjectsResultSet = QueryStatement.executeQuery(strQuery);
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Executing Query on Objects: " + ex.toString());
			}

		}

		else if (strFilePath.toUpperCase().contains("TESTDATA")){
			if (!(EnvironmentSetup.UseLineItem)){				
				String dataSheetToUse = "";
	/*			if (EnvironmentSetup.UseLineItem){
					dataSheetToUse = "LineItems";
				} else{
					dataSheetToUse = EnvironmentSetup.strDataSheetPath;
				}*/
				System.out.println("Exccel Sheet to Use is :: " + dataSheetToUse);
				try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
					EnvironmentSetup.testDataConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
							+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");// Changed the readOnly to true by tejaswini
				}
				catch (Exception ex1){
					EnvironmentSetup.logger.info("Reopening Data Connection: " + ex1.toString());
				}
	
	
				try{
					EnvironmentSetup.testDataQuery = EnvironmentSetup.testDataConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info("Create Query Statement Test Data: " + ex.toString());
				}
	
	
				try{
					EnvironmentSetup.testDataResultSet = EnvironmentSetup.testDataQuery.executeQuery(strQuery);
				}
				catch (NullPointerException ex){
					try{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
						EnvironmentSetup.testDataConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
								+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
					}
					catch (Exception ex1){
						EnvironmentSetup.logger.info("Reopening Data Connection: " + ex1.toString());
					}
	
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info("Creating Query Statement to Data: " + ex.toString());
				}
	
			} else { //Tejaswini - For Line Items
				System.out.println("FETCHING FROM LINEITEMS");
				try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
					EnvironmentSetup.lineItemConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
							+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
				}
				catch (Exception ex1){
					EnvironmentSetup.logger.info("LINEITEMS --- > Reopening Data Connection: " + ex1.toString());
				}
	
	
				try{
					EnvironmentSetup.lineItemQuery = EnvironmentSetup.lineItemConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info("LINEITEM ---->>> Create Query Statement Test Data: " + ex.toString());
				}
	
	
				try{
					EnvironmentSetup.lineItemResultSet = EnvironmentSetup.lineItemQuery.executeQuery(strQuery);
				}
				catch (NullPointerException ex){
					try{
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
						EnvironmentSetup.lineItemConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
								+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
					}
					catch (Exception ex1){
						EnvironmentSetup.logger.info("LINEITEM --->>> Reopening Data Connection: " + ex1.toString());
					}
	
				}
				catch (Exception ex){
					EnvironmentSetup.logger.info("LINEITEM --->> Creating Query Statement to Data: " + ex.toString());
				}

			} // Tejaswini - For LineItems
			
		}

		else if (strFilePath.toLowerCase().contains("testsuite")){
			try{
				EnvironmentSetup.logger.info(strQuery);
				QueryStatement = EnvironmentSetup.testSuiteConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			}
			catch (Exception ex){

			}

			try{
				EnvironmentSetup.testSuiteResultSet = QueryStatement.executeQuery(strQuery);
			}
			catch (NullPointerException ex){
				try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
					EnvironmentSetup.testDataConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
							+ "DBQ="+ EnvironmentSetup.strTestSuitePath + ";readOnly=false");
				}
				catch (Exception ex1){
					EnvironmentSetup.logger.info("Reopening Test Suite Connection: " + ex1.toString());
				}

			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Creating Query Statement to Test Suite: " + ex.toString());
			}

			try{
				EnvironmentSetup.testSuiteResultSet = QueryStatement.executeQuery(strQuery);
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Executing Query on Test Suite: " + ex.toString());
			}
		}

		else if (strFilePath.toUpperCase().contains("TESTCASES")){

			EnvironmentSetup.logger.info("Fetch Data Test Cases: " + EnvironmentSetup.strTestCasesPath);

			try{
				QueryStatement = EnvironmentSetup.testCasesConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			}
			catch (NullPointerException ex){
				try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
					EnvironmentSetup.testCasesConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
							+ "DBQ="+ EnvironmentSetup.strTestCasesPath + ";readOnly=false");
				}
				catch (Exception ex1){
					EnvironmentSetup.logger.info("Reopening Test Cases Connection: " + ex1.toString());
				}

			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Fetch Data Test Cases - Query Statement: " + ex.toString());
			}

			try{
				EnvironmentSetup.testCaseResultSet = QueryStatement.executeQuery(strQuery);
			}
			catch (NullPointerException ex){
				try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
					EnvironmentSetup.testCasesConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
							+ "DBQ="+ EnvironmentSetup.strTestCasesPath + ";readOnly=false");
				}
				catch (Exception ex1){
					EnvironmentSetup.logger.info("Reopening Test Cases Connection: " + ex1.toString());
				}

			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Creating Query Statement to Test Cases: " + ex.toString());
			}

			try{
				EnvironmentSetup.testCaseResultSet = QueryStatement.executeQuery(strQuery);
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Executing Query on Test Case: " + ex.toString());
			}

		}

		//Reusable Steps
		else if (strFilePath.toUpperCase().contains("REUSABLES")){
			try{
				QueryStatement = EnvironmentSetup.reusablesConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			}
			catch (Exception ex){

			}

			try{
				EnvironmentSetup.reusablesResultSet = QueryStatement.executeQuery(strQuery);
			}
			catch (NullPointerException ex){
				try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
					EnvironmentSetup.reusablesConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
							+ "DBQ="+ EnvironmentSetup.strReusablesPath + ";readOnly=false");
				}
				catch (Exception ex1){
					EnvironmentSetup.logger.info("Reopening Reusables Connection: " + ex1.toString());
				}
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Creating Query Statement to Reusables: " + ex.toString());
			}

			try{
				EnvironmentSetup.reusablesResultSet = QueryStatement.executeQuery(strQuery);
			}
			catch (Exception ex){
				EnvironmentSetup.logger.info("Executing Query on Reusables: " + ex.toString());
			}
		}
	}


	private static String BuildQuery(String strTargetColumnName, String strTableName, String strConditionColumnName, String strQueryCriteria, boolean objectQuery)
	//EnvironmentSetup.strReusablesPath, "*", strReusableName, "Type", strReusableParams + ",All"
	//R: CreateIssue NewIssue,UpdateIssue,IssueList,IssueForm
	/*
	 * strTargetColumnName = *
	 * strTableName = strReusableName = CreateIssue
	 * strConditionColumnName = Type
	 * strQueryCriteria = NewIssue,UpdateIssue,IssueList,IssueForm,All
	 * 
	 */
	{
		
		System.out.println("Column Name is :: "+ strConditionColumnName);
		System.out.println("Query Criteria is :: "+ strQueryCriteria);
		String strTempQuery = "";
		String [] strQueryParameters;
		int strQCLength = strQueryCriteria.length();
		System.out.println("Query Criteria is :: " + strQueryCriteria + " String Length is :: " + strQCLength);
		String strQueryConditions="";
		int intQueryParamSize;
		try {
			if ((strQueryCriteria!="")&& (strQueryCriteria != null) && (strConditionColumnName!="")) {
				if ((EnvironmentSetup.UseLineItem)&& !objectQuery){
					strTempQuery = "SELECT * " + " FROM " + "[" + strTableName + "$] WHERE " + "LineItemID LIKE '%" + EnvironmentSetup.LineItemIdForExec + "%'" + " AND DataSet = '" +  strQueryCriteria.substring(0,strQCLength) + "'";
					System.out.println("Query is :: " + strTempQuery);
				} else {
					if(strQueryCriteria.contains(",")){				
					strQueryParameters = strQueryCriteria.split(",");
					intQueryParamSize = strQueryParameters.length;
					for (int i=0; i<intQueryParamSize; i++) {

						if(i == intQueryParamSize-1){
							strQueryConditions = strQueryConditions + strConditionColumnName.trim() + "Like '%" + strQueryParameters[i] +  "%'";
						}
						else{
							strQueryConditions = strQueryConditions + strConditionColumnName.trim() + " Like '%" + strQueryParameters[i].trim() + "%' OR ";
						}
					}
					strTempQuery = "SELECT " + strTargetColumnName + " FROM " + "[" + strTableName + "$] WHERE " + strQueryConditions.trim() + " ORDER BY Step" ;
				}
				else{
					//if (strTableName.contains("Suite")){
					strTempQuery = "SELECT " + strTargetColumnName + " FROM " + "[" + strTableName + "$]" + " WHERE " + strConditionColumnName + " = '" + strQueryCriteria + "'" ;
					/*}
    							else{
    								strTempQuery = "SELECT " + strTargetColumnName + " FROM " + "[" + strTableName + "$]" + " WHERE " + strConditionColumnName + " = '" + strQueryCriteria + "'";
    							}*/
				}
			}
			}
			else {
				strTempQuery = "SELECT " + strTargetColumnName + " FROM " + "[" + strTableName + "$]";
			}
		}
		catch (Exception ex)
		{
			EnvironmentSetup.logger.info("Query: " + strTableName + " " + ex.toString());
		}
		EnvironmentSetup.logger.info(strTempQuery);
		System.out.println("Query Returned is :: "+ strTempQuery);
		return strTempQuery;
		
	}
	
	public static void UpdateTable(String strTargetColumnName, String strValueToUpdate, String strConditionColumnName, String strQueryCriteria){

		String strUpdateQuery="";

		try{
			EnvironmentSetup.lineItemDataConnection.commit();
			//EnvironmentSetup.testDataQuery.close();	
		}
		catch(Exception ex){
			EnvironmentSetup.logger.info("Line ITem Result Set Close before Update: " + ex.toString());
		}

		try{
			EnvironmentSetup.testDataConnection.commit();
			EnvironmentSetup.testDataQuery.close();	
		}
		catch(Exception ex){
			EnvironmentSetup.logger.info("Test Data Result Set Close before Update: " + ex.toString());
		}

		try{
			EnvironmentSetup.testDataConnection.close();	
		}
		catch(Exception ex){
			EnvironmentSetup.logger.info("Test Data Connection Close before Update: " + ex.toString());
		}

		//Test Data Connection
		try{
			EnvironmentSetup.testDataConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver "
					+ "(*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
			EnvironmentSetup.testDataConnection.setAutoCommit(true);
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Test Data Connection Exception in Update Table: " + ex.toString());
		}

		try{
			EnvironmentSetup.testDataQuery = EnvironmentSetup.testDataConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		if (strValueToUpdate.contains("'")){
			strValueToUpdate.replace("'", "\'");
		}
		//Tejaswini -- Added the trim() to strValueToUpdate
		strUpdateQuery = "UPDATE [" + EnvironmentSetup.strDataSheetName + "$] SET " + strTargetColumnName + "='" + strValueToUpdate.trim() +  "' WHERE " + strConditionColumnName + "='" + strQueryCriteria + "'";
		if (EnvironmentSetup.UseLineItem){
			strUpdateQuery = strUpdateQuery + " AND LineItemID = '" + EnvironmentSetup.LineItemIdForExec + "'";
		}
		System.out.println("Update Query is :: " + strUpdateQuery);
		EnvironmentSetup.logger.info(strUpdateQuery);

		try {   	
			EnvironmentSetup.testDataQuery.execute(strUpdateQuery);
			System.out.println("Update query executed");
			EnvironmentSetup.logger.info(Integer.toString(EnvironmentSetup.testDataQuery.getUpdateCount()));
		} catch (SQLException e) {
			System.out.println("****Update Execute Query Error storing MRID!!***");
			if (e.toString().contains("[Microsoft][ODBC Excel Driver] Too few parameters. Expected 1")){
				strUpdateQuery = "UPDATE [" + EnvironmentSetup.strAddendumDataSheetName + "$] SET " + strTargetColumnName + "='" + strValueToUpdate.trim() +  "' WHERE " + strConditionColumnName + "='" + strQueryCriteria + "'";
				if (EnvironmentSetup.UseLineItem){
					strUpdateQuery = strUpdateQuery + " AND LineItemID = '" + EnvironmentSetup.LineItemIdForExec + "'";
				}
				System.out.println("Retrying Update in addendum sheet.. Update Query is :: " + strUpdateQuery);
				try {
					EnvironmentSetup.testDataQuery.execute(strUpdateQuery);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}

		try{
			EnvironmentSetup.testDataConnection.commit();
			System.out.println("Update query Committed");
		}
		catch (Exception ex){ 
			System.out.println("****Error storing MRID --- COMMIT Error!!***");
			EnvironmentSetup.logger.info("Data Commit: " + ex.toString());
		}

		try{
			EnvironmentSetup.testDataQuery.close();	
			System.out.println("query Closed");
		}
		catch(Exception ex){
			System.out.println("****Error storing MRID!!*** Close Query Error*****");
			EnvironmentSetup.logger.info("Test Data Result Set Close after Update: " + ex.toString());
		} 

		try{
			EnvironmentSetup.testDataConnection.close();
			System.out.println("DBConnection Closed after commit");

		}
		catch(Exception ex){
			System.out.println("****Error storing MRID!!*** Close Connection Error*****");			
			EnvironmentSetup.logger.info("Commit Test Data Close: " + ex.toString());
		}

	}

	public static void FetchLineItemData(String strTargetColumnName, String strLineItemId, String strConditionColumnNames, String strQueryCriteria){
		//DatabaseFunctions.FetchLineItemData("*", EnvironmentSetup.strLineItemDataSet, strLineItemsCriteriaColumns, strLineItemsCriteriaData);

		try{
			if(EnvironmentSetup.lineItemDataConnection==null){
				try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
					EnvironmentSetup.lineItemDataConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
							+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
				}
				catch (Exception ex2){
					EnvironmentSetup.logger.info("FetchLineItems: Reopening Data Connection: " + ex2.toString());
				}
			}
			else if(EnvironmentSetup.lineItemDataConnection.isClosed()){
				try{
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
					EnvironmentSetup.lineItemDataConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
							+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");
				}
				catch (Exception ex2){
					EnvironmentSetup.logger.info("FetchLineItems: Reopening Data Connection: " + ex2.toString());
				}
			}
		}
		catch(Exception ex){

		}


		String strQuery = "";

		try{
			EnvironmentSetup.lineItemDataQuery = EnvironmentSetup.lineItemDataConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("FetchLineItems: Create Query Statement Test Data: " + ex.toString());
		}


		strQuery = "Select * from [" + EnvironmentSetup.strLineItemsSheetName + "$] Where DataSet ='" + EnvironmentSetup.strCurrentDataset + "' and LineItemID Like '%" 
				+ strLineItemId + "%'" ;

		System.out.println(strConditionColumnNames);

		if(strConditionColumnNames!=""){
			String strCriteriaToAppend = "";

			strCriteriaToAppend = BuildLineItemsQuery(strConditionColumnNames, strQueryCriteria).trim();
			strQuery = strQuery + " " + strCriteriaToAppend;
		}

		EnvironmentSetup.logger.info("Line Items Query: " + strQuery);
		System.out.println("Line Item Query: " + strQuery);

		try{
			EnvironmentSetup.lineItemDataResultSet = EnvironmentSetup.lineItemDataQuery.executeQuery(strQuery);

		}
		catch (NullPointerException ex){
			EnvironmentSetup.logger.info("FetchLineItems: Creating Query Statement to Data Null Ptr: " + ex.toString());
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("FetchLineItems: Creating Query Statement to Data: " + ex.toString());
		}

	}

	private static String BuildLineItemsQuery(String strConditionColNames, String strConditionData){

		String [] arrConditionCols;
		String [] arrConditionData;
		String strQuerytoAppend = " ";

		if(strConditionColNames.contains(",")){
			arrConditionCols = strConditionColNames.split(",");
			arrConditionData = strConditionData.split(",");

			for(int iCount=0; iCount<arrConditionCols.length; iCount++){
				strQuerytoAppend = strQuerytoAppend + " and " + arrConditionCols[iCount] + "='" + arrConditionData[iCount] + "'";
			}

		}
		else{
			strQuerytoAppend = strQuerytoAppend + " and " + strConditionColNames + "='" + strConditionData + "'";
		}

		strQuerytoAppend = strQuerytoAppend.trim();

		return strQuerytoAppend;
	}

	public static void UpdateLineItemsTable(String strTargetColumnName, String strValueToUpdate){
		String strUpdateQuery = "";

		Statement lineItemDataUpdateQuery = null;

		if (strValueToUpdate.contains("'")){
			strValueToUpdate.replace("'", "\'");
		}
		
		strUpdateQuery = "UPDATE [" + EnvironmentSetup.strLineItemsSheetName + "$] SET " + strTargetColumnName + "='" + strValueToUpdate +  "' WHERE DataSet='" 
				+ EnvironmentSetup.strCurrentDataset + "' and LineItemID='" + EnvironmentSetup.strCurrentLineItemDataSet + "'";

		System.out.println(strUpdateQuery);
		EnvironmentSetup.logger.info(strUpdateQuery);

		try{
			lineItemDataUpdateQuery = EnvironmentSetup.lineItemDataConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			lineItemDataUpdateQuery.execute(strUpdateQuery);
			EnvironmentSetup.logger.info(Integer.toString(lineItemDataUpdateQuery.getUpdateCount()));
			EnvironmentSetup.lineItemDataConnection.commit();
			lineItemDataUpdateQuery.close();

		}
		catch(Exception ex){
			EnvironmentSetup.logger.info("Exception in Storing Line Item: " + ex.toString());
		}

	}
	
	
	/*
		Functions For DataBase
	*/

    public static String fetchObjectProperty(String ObjectName){
    	Statement QueryStatement = null;
    	strDBQuery = "Select Property from ServiceCloud_Objects where FieldName = '" + ObjectName + "'";
    	String strObjectProperty = "";
    	try{
			QueryStatement = EnvironmentSetup.dBConnection.createStatement();
			
			EnvironmentSetup.testObjectsResultSet = QueryStatement.executeQuery(strDBQuery);
			//EnvironmentSetup.testObjectsResultSet.beforeFirst();
			EnvironmentSetup.testObjectsResultSet.next();
			strObjectProperty = EnvironmentSetup.testObjectsResultSet.getString("Property");
		}
		   
		catch (Exception ex){
			EnvironmentSetup.logger.info("Error creating queryStatement: " + ex.toString());
			strObjectProperty = ObjectName + " not found in the Objects Table";
			//ex.printStackTrace();
			
		} 
    	{
    	/*	try{
    			EnvironmentSetup.testObjectsResultSet.close();
    		}
    		catch(Exception ex){
    			
    		}*/
    	}
    	
    	return strObjectProperty;
    }
    
    public static void loadTestData(String DataSet){
    	strDBQuery = "Select * from ServiceCLoud_TestData where DataSet = '" + DataSet + "'";
    	Statement QueryStatement = null;
    	try{
			QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			EnvironmentSetup.testDataResultSet = QueryStatement.executeQuery(strDBQuery);
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Unable to load Test Data");
		} 
    	
    	strDBQuery = "Select * from DataLineItems where DataSet = '" + DataSet + "'";
       	try{
			QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			EnvironmentSetup.lineItemDataResultSet = QueryStatement.executeQuery(strDBQuery);
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Unable to load Data Line Items");
		}
    	
    }
    
    public static String fetchRequiredTestData(String ColumnName, String DataSet, String strConditionColumnNames, String strQueryCriteria){
    	String requiredTestData = "";
    	Statement QueryStatement = null;
    	
    	if(EnvironmentSetup.strLineItemDataSet.isEmpty()){
    		strDBQuery = "Select " + ColumnName + " from ServiceCloud_TestData where DataSet = '" + DataSet + "'";
        	try{
        		//QueryStatement.close();
        		QueryStatement = EnvironmentSetup.dBConnection.createStatement();
				EnvironmentSetup.testDataResultSet = QueryStatement.executeQuery(strDBQuery);
				EnvironmentSetup.testDataResultSet.beforeFirst();
				EnvironmentSetup.testDataResultSet.next();
				requiredTestData =EnvironmentSetup.testDataResultSet.getString(ColumnName);
			}
			catch (Exception ex){
				//EnvironmentSetup.logger.info("Unable to load Test Data");
				if(ex.toString().contains("Unknown column")){
					requiredTestData = "";
				}
			}
        	{
        		try{
        			EnvironmentSetup.testDataResultSet.close();
        		}
        		catch(Exception ex){
        			
        		}
        	}
    	}
    	else{
    		//TODO Add LineItems Query
    		
    	}
    	
    	return requiredTestData;
    	
    }
    
    
    
    public static void fetchReusableTestSteps(String ReusableName, String QueryCriteria){
    	String QueryConditions = BuildQuery("*", "ServiceCloud_Reusables", "Type", QueryCriteria, false);//Modified by Tejaswini
    	//TODO Optimize
    	Statement QueryStatement = null;
    	System.out.println(QueryConditions);
    	QueryConditions = QueryConditions.replace(" WHERE ", " and ");
    	QueryConditions = QueryConditions + " OR Type Like '%All%'";
    	strDBQuery = "Select * From ServiceCloud_Reusables WHERE ReusableName = '" + ReusableName + "'" + QueryConditions;
    	try{
        	QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        	EnvironmentSetup.reusablesResultSet = QueryStatement.executeQuery(strDBQuery);
    	}
    	catch(Exception ex){
    		
    	}
    }
    
    public static void loadTestSuite(){
    	Statement QueryStatement = null;
    	strDBQuery = "Select * from ServiceCloud_TestSuite where ExecutionRequired='Yes'";
		try{
			QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			//EnvironmentSetup.dBConnection
			EnvironmentSetup.testSuiteResultSet = QueryStatement.executeQuery(strDBQuery);
		}
		   
		catch (Exception ex){
			EnvironmentSetup.logger.info("Error creating queryStatement: " + ex.toString());
		}
    }
    
    /*public static void loadTestSuite(String TestScenarioIDs){
    	String[] ScenarioIdList = null;
    	String QueryFilter = "";
    	if(TestScenarioIDs.contains(",")){
    		ScenarioIdList = TestScenarioIDs.split(",");
    		for(String TestScenarioID : ScenarioIdList){
    			QueryFilter = QueryFilter + " TestScenarioID='"+ TestScenarioID +"'";
    		}
    		strQuery = "Select * From TestSuite where " + QueryFilter;
    	}
    	else{
    		strQuery = "Select * from TestSuite where TestScenarioID='"+ TestScenarioIDs+"'";
    	}
    	
    	try{
			QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			EnvironmentSetup.testSuiteResultSet = QueryStatement.executeQuery(strQuery);
		}
		   
		catch (Exception ex){
			EnvironmentSetup.logger.info("Error creating queryStatement: " + ex.toString());
		}
    }*/
    

    public static void fetchTestCase(String AutomationID){
    	Statement QueryStatement = null;
    	strDBQuery = "Select * from ServiceCloud_TestCases where AutomationID='" + AutomationID + "'";
		try{
			QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			EnvironmentSetup.testCaseResultSet = QueryStatement.executeQuery(strDBQuery);
		}
		catch (Exception ex){
			EnvironmentSetup.logger.info("Error creating queryStatement: " + ex.toString());
		}
    }
    
    public static void updateTestDataTable(String TargetColumnName, String ValueToUpdate, String ConditionColumnName, String QueryCriteria){
    	Statement QueryStatement = null;
    	strDBQuery = "UPDATE ServiceCloud_TestData SET " + TargetColumnName + "='" + ValueToUpdate +  "' WHERE " + ConditionColumnName + "='" + QueryCriteria + "'";;
		
    	try{
			QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			QueryStatement.execute(strDBQuery);
		}
		   
		catch (Exception ex){
			EnvironmentSetup.logger.info("Error creating queryStatement: " + ex.toString());
		}
    }
    
    public static void updateDataLineItemsTable(String TargetColumnName, String ValueToUpdate){
    	Statement QueryStatement = null;
    	strDBQuery = "UPDATE [" + EnvironmentSetup.strLineItemsSheetName + "$] SET " + TargetColumnName + "='" + ValueToUpdate +  "' WHERE DataSet='" 
    			+ EnvironmentSetup.strCurrentDataset + "' and LineItemID='" + EnvironmentSetup.strCurrentLineItemDataSet + "'";
    	
    	try{
			QueryStatement = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			QueryStatement.execute(strDBQuery);
		}
		   
		catch (Exception ex){
			EnvironmentSetup.logger.info("Error creating queryStatement: " + ex.toString());
		}
    }
    
    public static void loadRequiredLineItems(String ColumnName, String DataSet, String strConditionColumnNames, String strQueryCriteria){
    	
    	try{
    		EnvironmentSetup.lineItemDataQuery = EnvironmentSetup.dBConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    		
    		strDBQuery = "Select * from [" + EnvironmentSetup.strLineItemsSheetName + "$] Where DataSet ='" + EnvironmentSetup.strCurrentDataset + "' and LineItemID Like '%" 
    				+ EnvironmentSetup.strLineItemDataSet + "%'" ;

    		System.out.println(strConditionColumnNames);

    		if(strConditionColumnNames!=""){
    			String strCriteriaToAppend = "";

    			strCriteriaToAppend = BuildLineItemsQuery(strConditionColumnNames, strQueryCriteria).trim();
    			strDBQuery = strDBQuery + " " + strCriteriaToAppend;
    		}

    		EnvironmentSetup.logger.info("Line Items Query: " + strDBQuery);
    		System.out.println("Line Item Query: " + strDBQuery);

    		try{
    			EnvironmentSetup.lineItemDataResultSet = EnvironmentSetup.lineItemDataQuery.executeQuery(strDBQuery);

    		}
    		catch (NullPointerException ex){
    			EnvironmentSetup.logger.info("FetchLineItems: Creating Query Statement to Data Null Ptr: " + ex.toString());
    		}
    		catch (Exception ex){
    			EnvironmentSetup.logger.info("FetchLineItems: Creating Query Statement to Data: " + ex.toString());
    		}
    		
    	}
    	catch(Exception ex){
    		
    		
    	}
    }
    public static void UpdateLineItems(String strDataSheetField, String strValueToUpdate, String DataSet, String strDataSet){
    	Statement QueryStatement = null;
    	String strQuery = "UPDATE [" + EnvironmentSetup.strDataSheetName + "$] SET " + strDataSheetField + "='" + strValueToUpdate +  "' WHERE DataSet='" 
    			+ strDataSet + "' and LineItemID='" + EnvironmentSetup.LineItemIdForExec + "'";
    	System.out.println("Query String is :: " + strQuery);
		Connection testConnection = null;
		ResultSet testResultSet = null;
		int rowCount = 0;		
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");   
			testConnection = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};"
					+ "DBQ="+ EnvironmentSetup.strDataSheetPath + ";readOnly=false");	
			testConnection.setAutoCommit(true);
			QueryStatement = testConnection.createStatement();
			int sqlCode = QueryStatement.executeUpdate(strQuery);
			//testConnection.commit();
		}	catch (Exception ex){
			ex.printStackTrace();
			EnvironmentSetup.logger.info("Storing Line Item id " + ex.toString());
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
