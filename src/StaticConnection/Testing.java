package StaticConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.mysql.jdbc.Connection;

public class Testing {

	public static void main(String arg[]){
		try {
			String strQuery  = "SELECT * FROM statictest WHERE COL1 = 'ROW1'";
			String updateQuery = "Update statictest set Col92 = 'Row256'";
			String strDataOp = "";
			strDataOp = "SELECT * from statictest where Col1 = 'ROW1'" ;
			Statement QueryStatement = Connector.getInstance().getTestDataConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet testObjectsResultSet = QueryStatement.executeQuery(strQuery);			
			while(testObjectsResultSet.next()){												
				strDataOp = testObjectsResultSet.getString("COL1");
				System.out.println("Value from result set is :: " + strDataOp); 
				break;
			}
			Statement updateQueryStmt = Connector.getInstance().getTestDataConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			updateQueryStmt.executeUpdate(updateQuery);
			Connector.closeConnection();

			ResultSet testObjectsResultSet1 = QueryStatement.executeQuery(strQuery);
			//Connector.getInstance().getConnection().commit();
			while(testObjectsResultSet1.next()){												
				strDataOp = testObjectsResultSet1.getString("COL92");
				System.out.println("Value from result set is :: " + strDataOp); 
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Connector.closeConnection();
		}
		
		;
	}
}
