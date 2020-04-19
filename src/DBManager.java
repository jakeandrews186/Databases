import java.util.Scanner;

/**
 * Class for the database manager for CS410
 * @author Jake Andrews and Jeong-Hyun Boo
 *
 */
public class DBManager {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while(true)
		{
			String command = scan.nextLine();
			String[] inputs = command.split(" ");
			switch (inputs[0])
			{
			case "new-class":
				newClass(inputs[1], inputs[2], inputs[3], inputs[4]);
			
			case "select-class":
				if (inputs.length == 2)
				{
					selectClass(inputs[1], "", "");
				}
				else if (inputs.length == 3)
				{
					selectClass(inputs[1], inputs[2], "");

				}
				else if (inputs.length == 4)
				{
					selectClass(inputs[1], inputs[2], inputs[3]);

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
	 * show the current class�s gradebook: students (username, student ID, and
		name), along with their total grades in the class.
	 */
	private static void showGradebook() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * show student�s current grade: all assignments, visually
		grouped by category, with the student�s grade (if they have one). Show subtotals for each
		category, along with the overall grade in the class.
	 * @param username
	 */
	private static void showGrades(String username) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * assign the grade �grade� for student
		with user name �username� for assignment �assignmentname�. If the student already has a
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
	 * show all students with �string� in their name or username (case-insensitive)
	 * @param string
	 */
	private static void showStudents(String string) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * show all students in the current class
	 */
	private static void showStudents() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * enrolls an already-existing student in the current class. If
		the specified student does not exist, report an error
	 * @param username
	 */
	private static void addStudent(String username) {
		// TODO Auto-generated method stub
		
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
	private static void addStudent(String username, String studentID, String last, String first) {
		// TODO Auto-generated method stub
		
	}
	

	/**
	 * add a new assignment
	 * @param name
	 * @param category
	 * @param description
	 * @param points
	 */
	private static void addAssignment(String name, String category, String description, String points) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * list the assignments with their point values, grouped by category
	 */
	private static void showAssignment() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * add a new category
	 * @param name
	 * @param weight
	 */
	private static void addCategory(String name, String weight) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * list the categories with their weights
	 */
	private static void showCategories() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * show the currently active class
	 */
	private static void showCurrentClass() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * select a new class from the database
	 * @param code
	 * @param term
	 * @param section
	 */
	private static void selectClass(String code, String term, String section) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Creates a new class
	 * @param code - the class code
	 * @param term - the term of the class
	 * @param section - the section of the class
	 * @param title - the title of the class
	 */
	private static void newClass(String code, String term, String section, String title) {
		// TODO Auto-generated method stub
		
	}

}
