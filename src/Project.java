import java.sql.*;
import java.util.List;
import java.util.ArrayList;



public class Project 
{
	public static Class createClass(String c_number0, String term0, int sec_number0, String description0) throws SQLException
	{
		Connection connection = null;
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		Class newClass = new Class (c_number0, term0, sec_number0, description0);
		String sql = String.format("CALL createClass('%s', '%s', %s, '%s');", c_number0, term0, sec_number0, description0);
		sqlStatement.executeUpdate(sql);
		connection.close();
		
		return newClass;
	}
	
	public static String listClass() throws SQLException 
	{
		Connection connection = null;
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sql = "CALL listClass();";
		
		ResultSet rs = sqlStatement.executeQuery(sql);
		String returnStr = "";
		
		while(rs.next())
		{
			returnStr += rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + "\n"; 
		}
		
		connection.close();
		return returnStr; 
	}
	
	public static String selectClass1(String c_number0) throws SQLException 
	{
		Connection connection = null;	
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sql = String.format("CALL selectClass1('%s');", c_number0);
		
		ResultSet rs = sqlStatement.executeQuery(sql);
		String returnStr = "";
		
		if(!rs.first())
		{
			returnStr = "Command failed as there are multiple sections.";
		}
		else
		{
			while(rs.next())
			{
				returnStr = rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
			}
		}
		
		connection.close();
		return returnStr; 
	}
	
	public static String selectClass2(String c_number0, String term0) throws SQLException
	{
		Connection connection = null;	
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sql = String.format("CALL selectClass2('%s', '%s');", c_number0, term0);
		
		ResultSet rs = sqlStatement.executeQuery(sql);
		String returnStr = "";
		
		if(!rs.first())
		{
			returnStr = "Command failed as there are multiple sections.";
		}
		else
		{
			while(rs.next())
			{
				returnStr = rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
			}
		}

		connection.close();
		return returnStr; 
	}
	
	public static String selectClass3(String c_number0, String term0, int sec_number0) throws SQLException
	{
		Connection connection = null;	
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sql = String.format("CALL selectClass3('%s', '%s', %s);", c_number0, term0, sec_number0);
		
		ResultSet rs = sqlStatement.executeQuery(sql);
		String returnStr = "";
		
		while(rs.next())
		{
			returnStr = rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4) + " " + rs.getString(5);
		}
		
		connection.close();
		return returnStr; 
	}
	
	
	
	
	
	
	/* Skipping show-class, I am gonna need your help on this! */
	
	
	
	
	
	public static String showCategories() throws SQLException
	{

		Connection connection = null;	
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sql = "CALL showCategories();";
		
		ResultSet rs = sqlStatement.executeQuery(sql);
		String returnStr = "";
		
		while(rs.next())
		{
			returnStr += rs.getInt(1) + " " + rs.getString(2) + " " + rs.getLong(3) + "\n";
		}
		
		connection.close();
		return returnStr; 
	}
	
	
	public static Category addCategory(String name0, long weight0) throws SQLException
	{
		Connection connection = null;
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		Category newCategory = new Category (name0, weight0);
		String sql = String.format("CALL addCategory('%s', %s);", name0, weight0);
		sqlStatement.executeUpdate(sql);
		connection.close();
		
		return newCategory;
	}
	
	
	public static String showAssignment() throws SQLException
	{
		Connection connection = null;	
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sql = "CALL showAssignment();";
		
		ResultSet rs = sqlStatement.executeQuery(sql);
		String returnStr = "";
		
		while(rs.next())
		{
			returnStr += rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + "\n";
		}
		
		return returnStr; 
	}
	
	public static Assignment addAssignment(String name0, String name1, String description0, int point_value0) throws SQLException
	{
		Connection connection = null;
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		Assignment newAssignment = new Assignment (name0, name1, description0, point_value0);
		String sql = String.format("CALL addAssignment('%s', '%s', '%s', %s);", name0, name1, description0, point_value0);
		sqlStatement.executeUpdate(sql);
		connection.close();
		
		return newAssignment;
	}
	
	
	







































}
