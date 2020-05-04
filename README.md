********************************
* Group Project
* Class: CS410 - Spring 2020
* Date: 05/03/2020
* Names: Jake Andrews
		     Jeong Boo
********************************

OVERVIEW:
	This program mainly manages grades in a class. It creates and manages classes, assignments and students in order to 
  accurately keep track of grades for each student. 
  
  
INCLUDED FILES: 
 
 *  DBManger.java - creates a database connection and contains all of the methods to execute user-provided commands
 
 *  model.pdf - is the Entity-Relationship Model that is implemented in the project 
 
 *  dump.sql - contains example data for each table
 
 *  schema.sql - defines the database schema that is implemented in the project 
 
 *  README - describes the design of the program
 
 
COMPILING AND RUNNING: 
  From the directory containing DBManager.java, complie the .java file with the command:
    $javac DBManager.java

  Run the complied class with the below commands: 
    $java DBManager new-class [class code] [term] [section number] [class description]
      : to create a new class 
      
    $java DBManager list-class
      : to list classes with the number of students in each class
      
    $java DBManager select-class [class code] [| term] [| section number]
      : to select the class 
      
    $java DBManager add-student [username] [| student ID] [| last name] [| first name]
      : to add a student and enroll them in the current class 
      
    $java DBManager show-student [| string] 
      : to show all students in the current class 
   
   $java DBManager grade [assignment name] [username] [grade]
      : to assign the grade for the student
      
   $java DBManager student-grades [username] 
      : to show the student's current grade
   
   $java DBManager gradebook
      : to show the current class's gradebook 
  
