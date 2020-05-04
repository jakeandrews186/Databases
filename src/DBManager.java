
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.sql.*;
import java.awt.List;
import java.lang.*;
import java.math.BigDecimal;
import java.math.MathContext;

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
			System.out.print("> ");
			String command = scan.nextLine();
			String[] inputs = command.split(" ");
			switch (inputs[0])
			{
			case "new-class":
				newClass(inputs[1], inputs[2], inputs[3], inputs[4]);
				continue;
			case "list-classes":
				listClass();
				continue;
			case "select-class":
				if (inputs.length == 2)
				{
					selectClass1(inputs[1]);
					continue;
				}
				else if (inputs.length == 3)
				{
					selectClass2(inputs[1], inputs[2]);
					continue;

				}
				else if (inputs.length == 4)
				{
					selectClass3(inputs[1], inputs[2], inputs[3]);
					continue;
				}
			case "show-class":
				showCurrentClass();
				continue;
			case "show-categories":
				showCategories();
				continue;
			case "add-category":
				addCategory(inputs[1], inputs[2]);
				continue;
			case "show-assignment":
				showAssignment();
				continue;
			case "add-assignment":
				addAssignment(inputs[1], inputs[2], inputs[3], inputs[4]);
				continue;
			case "add-student":
				if (inputs.length == 2)
				{
					addStudent(inputs[1]);
					continue;
				} else {
					addStudent(inputs[1], inputs[2], inputs[3], inputs[4]);
					continue;
				}
			case "show-students":
				if (inputs.length == 2)
				{
					showStudents(inputs[1]);
					continue;
				}
				else {
					showStudents();
					continue;
				}
			case "grade":
				grade(inputs[1], inputs[2], inputs[3]);
				continue;
			case "student-grades":
				showGrades(inputs[1]);
				continue;
			case "gradebook":
				showGradebook();
				continue;
			case "exit":
				System.exit(0);
			default:
				System.out.println("Command not recognized. Please try again.");
				continue;
		}
		}
	}

	/**
	 * show the current class’s gradebook: students (username, student ID, and
		name), along with their total grades in the class.
	 */
	private static void showGradebook() {
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
	        MathContext m = new MathContext(4);

			ResultSet rs = stmt.executeQuery();
			if (rs.next() == false) 
			{ 
				System.out.println("No students in current class."); 
			} else { 
				System.out.println("Username    |   Student ID  |  Full Name  |  Total Percentage");
				do {
					System.out.print("\n" + rs.getString(2) + " - " + rs.getString(3) + " - " + rs.getString(5) + " " + rs.getString(4) + " - ");
					int id = Integer.parseInt(rs.getString(1));
					String query1 = "CALL getGradebook(?, ?);";
					try
					{
						CallableStatement stmt1 = conn.prepareCall(query1);
						stmt1.setInt(1, selectedClass);
						stmt1.setInt(2, id);
						ResultSet rs1 = stmt1.executeQuery();
						while(rs1.next())
						{
							try {
								BigDecimal bd = rs1.getBigDecimal(1).round(m);
								System.out.print(bd);
							}
							catch (NullPointerException e)
							{
								System.out.print("No Grades\n");
							}

						}
					}
					catch (SQLException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
	                }
				while (rs.next());
				System.out.println();
			}
		}
		catch (SQLException e) 
		{
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
	private static void showGrades(String username) 
	{
		
		//first, get student
		int studentID = -1;
		String query = "CALL getStudent(?);";
		try
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				studentID = rs.getInt(1);
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (studentID == -1)
		{
			System.out.println("Student not found. Please try again.");
			return;
		}
		//then get individual assignment grades
		String query0 = "CALL studentGrades(?, ?);";
		try
		{
			CallableStatement stmt = conn.prepareCall(query0);
			stmt.setInt(1, studentID);
			stmt.setInt(2, selectedClass);
			ResultSet rs = stmt.executeQuery();
			int overall = 0;
			int overallPossible = 0;
			System.out.println("Assignments:");
			System.out.println();
			while(rs.next())
			{
				System.out.println(rs.getString("Assignment") + " - " + rs.getInt("grade") + "/" + rs.getInt("point_value") + " - " + rs.getString("Category") );
			}
			String query1 = "CALL getCategoryEarned(?, ?);";

			CallableStatement stmt1 = conn.prepareCall(query1);
			stmt1.setInt(2, studentID);
			stmt1.setInt(1, selectedClass);
			ResultSet rs2 = stmt1.executeQuery();
			System.out.println("Category Points Earned:");
			System.out.println();
			while(rs2.next())
			{
				System.out.println(rs2.getString(1) + " points earned: " + rs2.getInt(2));
				overall += rs2.getInt(2);
			}
			
			String query2 = "CALL getCategoryTotals(?, ?);";

			CallableStatement stmt2 = conn.prepareCall(query2);
			stmt2.setInt(2, studentID);
			stmt2.setInt(1, selectedClass);
			ResultSet rs3 = stmt2.executeQuery();
			System.out.println("Category Points Possible:");
			System.out.println();
			while(rs3.next())
			{
				System.out.println(rs3.getString(1) + " points possible: " + rs3.getInt(2));
				overallPossible += rs3.getInt(2);
			}
			System.out.println();
			System.out.println("Total for this class: " + overall + "/" + overallPossible);

		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	private static void grade(String assignment, String username, String grade) 
	{
		String query = "CALL assignGrade(?, ?, ?);";
		if (selectedClass == -1)
		{
			System.out.println("No class currently selected.");
			return;
		}
		int studentID = -1;
		String query0 = "CALL getStudentID(?);";

		try 
		{
			CallableStatement stmt = conn.prepareCall(query0);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				studentID = rs.getInt(1);
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		if (studentID == -1)
		{
			System.out.println("Student not found with username: " + username);
			return;
		}
		int assignmentID = -1;
		String query2 = "CALL getAssignmentID(?);";

		try 
		{
			CallableStatement stmt = conn.prepareCall(query2);
			stmt.setString(1, assignment);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				assignmentID = rs.getInt(1);
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		if (assignmentID == -1)
		{
			System.out.println("Assignment not found with name: " + assignment);
			return;
		}
		
		
		try
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setInt(1, assignmentID);
			stmt.setInt(2, studentID);
			stmt.setInt(3, Integer.parseInt(grade));
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) // if the number of points does not exceed the number of points configured for the assignment
			{
				if (rs.getInt(1) > 0) {
					System.out.println("The number of points entered exceeds the number of points configured for the assignment(" + rs.getInt(1) + ")");
				}
				return;
			}

		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			
			if (rs.next() == false) 
			{ 
				System.out.println("No students with " + string + " in their name or user name."); 
			} else { 
				System.out.println("Students with " + string + " in their name or user name: ");
				do {
	                System.out.println(rs.getInt("s_id") + " " + rs.getString("user_name") + " " + 
	                		rs.getString("l_name") + " " + rs.getString("f_name"));

	                }
				while (rs.next());
			}
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
			if (rs.next() == false) 
			{ 
				System.out.println("No students in current class."); 
			} else { 
				System.out.println("Students in Current Class: ");
				do {
	                System.out.println(rs.getInt("s_id") + " " + rs.getString("user_name") + " " + 
	                		rs.getString("l_name") + " " + rs.getString("f_name"));

	                }
				while (rs.next());
			}
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
		if (selectedClass == -1)
		{
			System.out.println("No class currently selected.");
			return;
		}
		

		int studentID = -1;
		String query0 = "CALL getStudentID(?);";

		try 
		{
			CallableStatement stmt = conn.prepareCall(query0);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				studentID = rs.getInt(1);
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		if (studentID == -1)
		{
			System.out.println("Student not found with username: " + username);
			return;
		}

		String check = "CALL checkIfInClass(?, ?);";
		try 
		{
			CallableStatement stmt = conn.prepareCall(check);
			stmt.setInt(1, studentID);
			stmt.setInt(2, selectedClass);

			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				 System.out.println("Student is already in the current class. ");
				 return;
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		String query = "CALL enrollStudent(?, ?);";
		try 
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setInt(1, studentID);
			stmt.setInt(2, selectedClass);
			stmt.executeQuery();
		} catch (SQLException e) 
		{
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
		if (selectedClass == -1)
		{
			System.out.println("No class currently selected.");
			return;
		}
		
		String check = "CALL checkIfInClass(?, ?);";
		try 
		{
			CallableStatement stmt = conn.prepareCall(check);
			stmt.setInt(1, Integer.parseInt(studentID));
			stmt.setInt(2, selectedClass);

			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				 System.out.println("Student is already in the current class. ");
				 
					String query2 = "CALL updateStudent(?, ?, ?, ?);";
					CallableStatement stmt2 = conn.prepareCall(query2);
					stmt2.setString(1, username);
					stmt2.setInt(2, Integer.parseInt(studentID));
					stmt2.setString(3, last);
					stmt2.setString(4, first);
					
					ResultSet rs2 = stmt2.executeQuery();
					while (rs2.next())
					{
						String test = rs2.getString(1);
						if (test.equals("updated"))
						{
							System.out.println("Selected student's name has been updated.");
						}
					}
				 return;
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		int existingStudent = -1;
		String query0 = "CALL getStudentIDFromSchoolID(?);";

		try 
		{
			CallableStatement stmt = conn.prepareCall(query0);
			stmt.setInt(1, Integer.parseInt(studentID));
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
			{
				existingStudent = rs.getInt(1);
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		if (existingStudent != -1)
		{
			String query = "CALL enrollStudent(?, ?);";
			try 
			{
				CallableStatement stmt = conn.prepareCall(query);
				stmt.setInt(1, existingStudent);
				stmt.setInt(2, selectedClass);
				stmt.executeQuery();
				
				String query2 = "CALL updateStudent(?, ?, ?, ?);";
				CallableStatement stmt2 = conn.prepareCall(query2);
				stmt2.setString(1, username);
				stmt2.setInt(2, existingStudent);
				stmt2.setString(3, last);
				stmt2.setString(4, first);
				
				ResultSet rs = stmt2.executeQuery();
				while (rs.next())
				{
					if (rs.getString(1) == "updated")
					{
						System.out.println("Selected student's name has been updated.");
					}
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		} else {
			try 
			{
				String query2 = "CALL addStudent1(?, ?, ?, ?);";
				CallableStatement stmt2 = conn.prepareCall(query2);
				stmt2.setString(1, username);
				stmt2.setInt(2, Integer.parseInt(studentID));
				stmt2.setString(3, last);
				stmt2.setString(4, first);
				ResultSet rs = stmt2.executeQuery();
				while (rs.next())
				{
					existingStudent = rs.getInt(1);
				}
				
				String query = "CALL enrollStudent(?, ?);";
				CallableStatement stmt = conn.prepareCall(query);
				stmt.setInt(1, existingStudent);
				stmt.setInt(2, selectedClass);
				stmt.executeQuery();

			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
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
		String categoryCheck = "SELECT cg_id FROM Category WHERE name = '" + category + "'";
		try {
			CallableStatement stmt0 = conn.prepareCall(categoryCheck);
			ResultSet rs = stmt0.executeQuery();
			if (!rs.next())
			{
				System.out.println("Category not found. Please try again.");
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		String query = "CALL addAssignment(?, ?, ?, ?, ?);";
		if (selectedClass == -1)
		{
			System.out.println("No class selected to add assignments.");
			return;
		}
		try 
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, name);
			stmt.setString(2, category);
			stmt.setString(3, description);
			stmt.setInt(4, Integer.parseInt(points));
			stmt.setInt(5, selectedClass);

			stmt.executeQuery();
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
		String query = "CALL showAssignment(?);";
		if (selectedClass == -1)
		{
			System.out.println("No class selected for assignments.");
			return;
		}
		try
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setInt(1, selectedClass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next() == false) 
			{ 
				System.out.println("No assignments found for the current class."); 
			} else { 
				do {
					System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4) + "\n"); 
	                }
				while (rs.next());
			}
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
		String query = "CALL addCategory(?, ?, ?);";
		Double decimalWeight = Double.parseDouble(weight)/100;
		if (decimalWeight > 1)
		{
			System.out.println("Number between 0 and 100 expected for weight. i.e. \"25\"");
			return;
		}
		if (selectedClass == -1)
		{
			System.out.println("No class selected to add category.");
			return;
		}
		try 
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, name);
			stmt.setDouble(2, decimalWeight);
			stmt.setInt(3, selectedClass);
			stmt.executeQuery();
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
		String query = "CALL showCategories(?);";
		try
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setInt(1, selectedClass);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next() == false) 
			{ 
				System.out.println("No categories found for the current class."); 
			} else { 
				do {
					System.out.println(rs.getString(2) + " " + rs.getDouble(3) + "\n"); 

	                }
				while (rs.next());
			}
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
		String query = "{CALL showClass(?)}";
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
		String query = "CALL selectClass1(?);";
		try {
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, code);	
			ResultSet rs = stmt.executeQuery();
			
			int mostRecentYear = 0;
			int classID = -1;
			String mostRecentTerm = "";
			String description = "";
			if (rs.next() == false) 
			{ 
				System.out.println("Class not found. Please try again."); 
			} else { 
				do {
					String term = rs.getString("term");
					int year = Integer.parseInt(term.substring(2));
					String semester = term.substring(0, 1);
					if (year > mostRecentYear)
					{
						mostRecentYear = year;
						mostRecentTerm = term;
						classID = rs.getInt("c_id");
						description = rs.getString("description");
					} else if (year == mostRecentYear)
					{
						if (semester == "Fa")
						{
							mostRecentTerm = term;
							classID = rs.getInt("c_id");
							description = rs.getString("description");
						}
					}

	                }
				while (rs.next());
				
                System.out.println("Selected Class: " + description + " " + mostRecentTerm);
                selectedClass = classID;
			}
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
		String query = "CALL selectClass2(?, ?);";
		try 
		{
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, code);
			stmt.setString(2, term);	
			ResultSet rs = stmt.executeQuery();
			if (rs.next() == false) 
			{ 
				System.out.println("Class not found. Please try again."); 
			} else { 
				do { 
	                System.out.println("Selected Class: " + rs.getString("description"));
	                selectedClass = rs.getInt("c_id");
	                }
				while (rs.next());
			}
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
		String query = "CALL selectClass3(?, ?, ?);";
		try {
			CallableStatement stmt = conn.prepareCall(query);
			stmt.setString(1, code);
			stmt.setString(2, term);
			stmt.setInt(3, Integer.parseInt(section));			
			ResultSet rs = stmt.executeQuery();
			if (rs.next() == false) 
			{ 
				System.out.println("Class not found. Please try again."); 
			} else { 
				do { 
	                System.out.println("Selected Class: " + rs.getString("description"));
	                selectedClass = rs.getInt("c_id");
	                }
				while (rs.next());
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
			if (rs.next() == false) 
			{ 
				System.out.println("There are no classes to list.");
			} else { 
				System.out.println("Number | Description | # Students");
				System.out.println();
				do { 
					System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + "\n"); 

	                }
				while (rs.next());
			}
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
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}		
	}
	


}
