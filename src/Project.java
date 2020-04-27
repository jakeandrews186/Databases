import java.sql.*;


public class Project 
{
	public static Class createClass(String c_number0, String term0, int sec_number0, String description0) throws SQLException
	{
		Connection connection = null;
		Class newClass = new Class (c_number0, term0, sec_number0, description0);
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sql = String.format("createClass('%s', '%s', %s, '%s');", c_number0, term0, sec_number0, description0);
		sqlStatement.executeUpdate(sql);
		connection.close();
		
	}
}
