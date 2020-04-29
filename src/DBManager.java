
import java.util.Scanner;
import java.sql.*;  
import java.lang.*;

/**
 * Class for the database manager for CS410
 * @author Jake Andrews and Jeong-Hyun Boo
 *
 */
public class DBManager {
	
	private static java.sql.Connection conn;
	private static int selectedClass = -1;  //ID of the class currently selected
	public static void main(String[] args) {
		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/groupproject?user=root&useSSL=false","root","root");  
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		Scanner scan = new Scanner(System.in);
		while(true)
		{
			String command = scan.nextLine();
			String[] inputs = command.split(" ");
			switch (inputs[0])
			{
			case "new-class":
				newClass(inputs[1], inputs[2], inputs[3], inputs[4]);
			case "list-classes":
				listClass();
			case "select-class":
				if (inputs.length == 2)
				{
					selectClass1(inputs[1]);
				}
				else if (inputs.length == 3)
				{
					selectClass2(inputs[1], inputs[2]);

				}
				else if (inputs.length == 4)
				{
					selectClass3(inputs[1], inputs[2], inputs[3]);

				}
			case "show-class":
				showCurrentClass();
			case "show-categories":
				showCategories();
			case "add-category":
				addCategory(inputs[1], inputs[2]);
			case "show-assignment":
				showAssignment();
			case "add-assignment":
				addAssignment(inputs[1], inputs[2], inputs[3], inputs[4]);
			case "add-student":
				if (inputs.length == 2)
				{
					addStudent(inputs[1]);
				} else {
					addStudent(inputs[1], inputs[2], inputs[3], inputs[4]);
				}
			case "show-students":
				if (inputs.length == 2)
				{
					showStudents(inputs[1]);
				}
				else {
					showStudents();
				}
			case "grade":
				grade(inputs[1], inputs[2], inputs[3]);
			case "student-grades":
				showGrades(inputs[1]);
			case "gradebook":
				showGradebook();

			}
			
		}
		
		
	}

	/**
	 * show the current class’s gradebook: students (username, student ID, and
		name), along with their total grades in the class.
	 */
	private static void showGradebook() {
		// TODO Auto-generated method stub
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from grade");  
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			}
			conn.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		
	}

	/**
	 * show student’s current grade: all assignments, visually
		grouped by category, with the student’s grade (if they have one). Show subtotals for each
		category, along with the overall grade in the class.
	 * @param username
	 */
	private static void showGrades(String username) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * assign the grade ‘grade’ for student
		with user name ‘username’ for assignment ‘assignmentname’. If the student already has a
		grade for that assignment, replace it. If the number of points exceeds the number of
		points configured for the assignment, print a warning (showing the number of points
		configured).
	 * @param assignment
	 * @param username
	 * @param grade
	 */
	private static void grade(String assignment, String username, String grade) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * show all students with ‘string’ in their name or username (case-insensitive)
	 * @param string
	 */
	private static void showStudents(String string) 
	{
		String query = "CALL showStudents2(?);";
		try
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, string);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
                System.out.println("Students with " + string + " " + "in their name or user name: " + rs.getInt("s_id") + " " + rs.getString("user_name") + " " + 
                		rs.getString("l_name") + " " + rs.getString("f_name"));

			}
			conn.close();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * show all students in the current class
	 */
	private static void showStudents() 
	{
		String query = "CALL showStudents1(?);";
		try
		{
			if (selectedClass == -1)
			{
				System.out.println("No class currently selected.");
				return;
			}
			
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setInt(1, selectedClass);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
                System.out.println("Students in Current Class: " + rs.getInt("s_id") + " " + rs.getString("user_name") + " " + 
                		rs.getString("l_name") + " " + rs.getString("f_name"));

			}
			conn.close();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * enrolls an already-existing student in the current class. If
		the specified student does not exist, report an error
	 * @param username
	 */
	private static void addStudent(String username) 
	{
		String query = "CALL addStudent2(?, ?);";
		try 
		{
			if (selectedClass == -1)
			{
				System.out.println("No class currently selected.");
				return;
			}
			
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, username);
			stmt.setInt(2, selectedClass);
			ResultSet rs = stmt.executeQuery();
			
			if (!rs.first()) //student successfully added
			{
				return; 
			}
			else
			{
				System.out.println("User name not found");
			}

			conn.close();
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

	/**
	 * adds a student and enrolls
		them in the current class. If the student already exists, enroll them in the class; if the
		name provided does not match their stored name, update the name but print a warning
		that the name is being changed. 
	 * @param username
	 * @param studentID
	 * @param last
	 * @param first
	 */
	private static void addStudent(String username, String studentID, String last, String first) 
	{
		String query = "CALL updateStudent(?, ?, ?, ?);";
		try 
		{
			if (selectedClass == -1)
			{
				System.out.println("No class currently selected.");
				return;
			}
			
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, username);
			stmt.setInt(2, Integer.parseInt(studentID));
			stmt.setString(3, last);
			stmt.setString(4, first);
			ResultSet rs = stmt.executeQuery();

			String query2 = "CALL addStudent1(?, ?, ?, ?, ?);";
			CallableStatement stmt2 = conn.prepareCall(query2);
			stmt2.setString(1, username);
			stmt2.setInt(2, Integer.parseInt(studentID));
			stmt2.setString(3, last);
			stmt2.setString(4, first);
			stmt2.setInt(5, selectedClass); 

			if(!rs.first()) //name not being updated
			{
				stmt2.executeQuery(); //adding student
			}
			
			else //if name is being updated
			{
				while(rs.next())
				{
					stmt2.executeQuery();
					System.out.println("Selected student's name has been updated.");
				}
			}
			conn.close();
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	

	/**
	 * add a new assignment
	 * @param name
	 * @param category
	 * @param description
	 * @param points
	 */
	private static void addAssignment(String name, String category, String description, String points) 
	{
		String query = "CALL addAssignment(?, ?, ?, ?);";
		try 
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, name);
			stmt.setString(2, category);
			stmt.setString(3, description);
			stmt.setInt(4, Integer.parseInt(points));

			stmt.executeQuery();
			conn.close();
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

	/**
	 * list the assignments with their point values, grouped by category
	 */
	private static void showAssignment() 
	{
		String query = "CALL showAssignment();";
		try
		{
			CallableStatement stmt = conn.prepareCall(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3) + "\n"); 
			}
			conn.close();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * add a new category
	 * @param name
	 * @param weight
	 */
	private static void addCategory(String name, String weight) 
	{
		String query = "CALL addCategory(?, ?);";
		try 
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, name);
			stmt.setLong(2, Long.parseLong(weight));
			stmt.executeQuery();
			conn.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * list the categories with their weights
	 */
	private static void showCategories() 
	{
		String query = "CALL showCategories();";
		try
		{
			CallableStatement stmt = conn.prepareCall(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getLong(3) + "\n"); 
			}
			conn.close();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * show the currently active class
	 */
	private static void showCurrentClass() {
		if (selectedClass == -1)
		{
			System.out.println("No class currently selected.");
			return;
		}
		String query = "{CALL show_class(?)}";
		try {
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setInt(1, selectedClass);
			ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Current class: " + rs.getString("description") + " - " +
            "section " + rs.getInt("sec_number"));
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	/**
	 * select a new class from the database
	 * @param code
	 * @param term
	 */
	private static void selectClass1(String code) 
	{
		String query = "CALL select_class1(?);";
		try {
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, code);	
			ResultSet rs = stmt.executeQuery();
			if(!rs.first())
			{
				System.out.println("Command failed as there are multiple sections.");
			}
			else
			{
	            while (rs.next()) 
	            {
	                System.out.println("Selected Class: " + rs.getString("description"));
	                selectedClass = rs.getInt("c_id");
	            }
			}
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}			
	}
	
	/**
	 * select a new class from the database
	 * @param code
	 * @param term
	 */
	private static void selectClass2(String code, String term) 
	{
		String query = "CALL select_class2(?, ?);";
		try 
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, code);
			stmt.setString(2, term);	
			ResultSet rs = stmt.executeQuery();
			if(!rs.first())
			{
				System.out.println("Command failed as there are multiple sections.");
			}
			else
			{
	            while (rs.next()) 
	            {
	                System.out.println("Selected Class: " + rs.getString("description"));
	                selectedClass = rs.getInt("c_id");
	            }
			}
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}			
	}

	/**
	 * select a new class from the database
	 * @param code
	 * @param term
	 * @param section
	 */
	private static void selectClass3(String code, String term, String section) 
	{
		String query = "CALL select_class3(?, ?, ?);";
		try {
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, code);
			stmt.setString(2, term);
			stmt.setInt(3, Integer.parseInt(section));			
			ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Selected Class: " + rs.getString("description"));
                selectedClass = rs.getInt("c_id");
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	/**
	 * Lists classes with the number of students in each class 
	 */
	private static void listClass()
	{
		String query = "CALL listClass();";
		try
		{
			CallableStatement stmt = conn.prepareCall(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + "\n"); 
			}
			conn.close();
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Creates a new class
	 * @param code - the class code
	 * @param term - the term of the class
	 * @param section - the section of the class
	 * @param title - the title of the class
	 */
	private static void newClass(String code, String term, String section, String title) 
	{
		String query = "CALL createClass(?, ?, ?, ?);";
		try 
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, code);
			stmt.setString(2, term);
			stmt.setInt(3, Integer.parseInt(section));
			stmt.setString(4, title);

			stmt.executeQuery();
			conn.close();
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
