DROP DATABASE IF EXISTS GroupProject;
CREATE DATABASE GroupProject;
USE GroupProject;

DROP TABLE IF EXISTS Class;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Assignment;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Grade; 

CREATE TABLE Class 
(
	c_id INT NOT NULL AUTO_INCREMENT,
    c_number VARCHAR(10) NOT NULL,
    term varchar(5) NOT NULL,
    sec_number INT NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY (c_id)
);


CREATE TABLE Category -- maybe delete c_id? 
(
	cg_id INT NOT NULL AUTO_INCREMENT,
    c_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    weight DECIMAL(2,2) NOT NULL,
    PRIMARY KEY(cg_id),
    FOREIGN KEY (c_id) REFERENCES Class(c_id)
);

CREATE TABLE Student -- s_id is not auto incremented, looks like it should always be given by user?
(
	s_id INT NOT NULL auto_increment, 
    user_name VARCHAR(20) NOT NULL,
    studentID INT NOT NULL,
    l_name VARCHAR(20) NOT NULL,
    f_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (s_id)
);

CREATE TABLE Enrollment
(
	e_id INT NOT NULL auto_increment, 
    s_id INT NOT NULL,
    c_id INT NOT NULL,
    PRIMARY KEY (e_id),
    FOREIGN KEY (c_id) REFERENCES Class(c_id),
	FOREIGN KEY (s_id) REFERENCES Student(s_id)
);

CREATE TABLE Assignment -- we should probably get rid of s_id 
(
    a_id INT NOT NULL AUTO_INCREMENT,
    cg_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(100), 
    point_value INT NOT NULL,
    c_id INT NOT NULL, 
    PRIMARY KEY (a_id),
    FOREIGN KEY (cg_id) REFERENCES Category(cg_id),
	FOREIGN KEY (c_id) REFERENCES Class(c_id)
); 

CREATE TABLE Grade
(
	g_id INT NOT NULL AUTO_INCREMENT, 
    s_id INT NOT NULL,
    a_id INT NOT NULL, 
    grade INT, 
    PRIMARY KEY (g_id),
    FOREIGN KEY (s_id) REFERENCES Student(s_id),
    FOREIGN KEY (a_id) REFERENCES Assignment(a_id)
);

DELIMITER $$
CREATE PROCEDURE createClass(IN c_number0 VARCHAR(10), term0 VARCHAR(5),  sec_number0 INT, description0 VARCHAR(100))
BEGIN
	INSERT INTO Class (c_number, term, sec_number, description) VALUES (c_number0, term0, sec_number0, description0);
END $$

DELIMITER $$
CREATE PROCEDURE listClass()
BEGIN
	SELECT c.c_number, c.description, COUNT(s.s_id)
    FROM Class c 
	LEFT JOIN Student s ON c.c_id = s.c_id
    GROUP BY c.c_id;
END $$


DELIMITER $$
CREATE PROCEDURE selectClass1(IN c_number0 VARCHAR(10)) 
BEGIN
	DECLARE num_of_secs INT;
    SET num_of_secs = (SELECT COUNT(sec_number) FROM Class where c_number = c_number0 GROUP BY term, c_number); -- if there's more than one section, then it'll execute the query & return a result set. Nothing will return otherwise. 
	IF (num_of_secs = 1) THEN
    SELECT c_id, c_number, term, description
    FROM Class c
    WHERE c_number = c_number0 AND sec_number = 1
    LIMIT 1;
    END IF;
END $$

DELIMITER $$
CREATE PROCEDURE selectClass2(IN c_number0 VARCHAR(10), term0 VARCHAR(5)) 
BEGIN 
	DECLARE num_of_secs INT;
    SET num_of_secs = (SELECT COUNT(sec_number) FROM Class GROUP BY term, c_number); 
	IF (num_of_secs = 1) THEN
    SELECT c_id, c_number, term, description
    FROM Class
    WHERE c_number = c_number0 AND term = term0 AND sec_number = 1;
    END IF;
END $$

DELIMITER $$
CREATE PROCEDURE selectClass3(IN c_number0 VARCHAR(10), term0 VARCHAR(5), sec_number0 INT)
BEGIN
    SELECT * 
    FROM Class
    WHERE c_number = c_number0 AND term = term0 AND sec_number = sec_number0;
END $$

DELIMITER $$
CREATE PROCEDURE showClass(IN c_id0 INT) 
BEGIN 
	SELECT *
    FROM Class
    where c_id = c_id0;
END $$

DELIMITER $$
CREATE PROCEDURE showCategories(IN c_id0 INT)
BEGIN 
	SELECT cg_id, name, weight
    FROM Category
    where c_id = c_id0;
END $$

DELIMITER $$
CREATE PROCEDURE addCategory(IN name0 VARCHAR(20), weight0 DECIMAL(2,2), c_id0 INT) -- I think there's no relationship between Category and Class? We don't require class to create a new category.
BEGIN
	INSERT INTO Category (name, weight, c_id) VALUES (name0, weight0, c_id0);
END $$

DELIMITER $$
CREATE PROCEDURE showAssignment(IN c_id0 INT) -- I am not sure how this is supposed to work. Do we return the sum value of point values grouped by category? 
BEGIN 
	SELECT a.cg_id, a.name, cg.name, a.point_value
    FROM Assignment a
    JOIN Category cg ON cg.cg_id = a.cg_id
    WHERE a.c_id = c_id0
    GROUP BY cg_id;
END $$

DELIMITER $$
CREATE PROCEDURE addAssignment(IN name0 VARCHAR(20), name1 VARCHAR(20), description0 VARCHAR(100), point_value0 INT, c_id0 INT) -- name0 refers to name in Assignment and name1 refers to name in Category. Also I think there's no relationship linking Assignment and Student as we don't require s_id to create a new assignment. 
BEGIN
	DECLARE cg_id0 INT;
    SET cg_id0 = (SELECT cg_id FROM Category WHERE name = name1);
	INSERT INTO Assignment (name, cg_id, description, point_value, c_id) VALUES (name0, cg_id0, description0, point_value0, c_id0);
END $$


DELIMITER $$
CREATE PROCEDURE updateStudent(IN user_name0 VARCHAR(20), s_id0 INT, l_name0 VARCHAR(20), f_name0 VARCHAR(20)) 
BEGIN
	IF CONCAT(f_name0, " ", l_name0) != (SELECT CONCAT(f_name, " ", l_name) FROM Student s WHERE s.studentID = s_id0) THEN
		UPDATE Student SET f_name = f_name0, l_name = l_name0 WHERE studentID = s_id0;
        SELECT "updated"; 
	else
		select "not updated";
	END IF;
END $$

DELIMITER $$
CREATE PROCEDURE addStudent1(IN user_name0 VARCHAR(20), studentID0 INT, l_name0 VARCHAR(20), f_name0 VARCHAR(20)) 
BEGIN
	INSERT INTO Student (user_name, studentID, l_name, f_name) VALUES (user_name0, studentID0, l_name0, f_name0);
    select s_id from Student where studentID = studentID0;
END $$

DELIMITER $$
CREATE PROCEDURE enrollStudent(IN s_id0 INT, c_id0 INT) 
BEGIN
	INSERT INTO Enrollment (c_id, s_id) VALUES (c_id0, s_id0);
END $$

DELIMITER $$
CREATE PROCEDURE checkIfInClass(IN schoolID INT, c_id0 INT) 
BEGIN
	select s.s_id from Enrollment 
    join student s on s.studentID = schoolID 
    where c_id = c_id0;
END $$

DELIMITER $$
CREATE PROCEDURE getStudentID(IN username VARCHAR(20)) 
BEGIN
	select s_id from Student where user_name = username;
END $$

DELIMITER $$
CREATE PROCEDURE getSchoolID(IN username VARCHAR(20)) 
BEGIN
	select studentID from Student where user_name = username;
END $$

DELIMITER $$
CREATE PROCEDURE getStudentIDFromSchoolID(IN schoolID INT) 
BEGIN
	select s_id, user_name from Student where studentID = schoolID;
END $$
    
DELIMITER $$
CREATE PROCEDURE addStudent2(IN user_name0 VARCHAR(20), c_id0 INT)
BEGIN 
	IF user_name0 IN (SELECT user_name FROM Student) THEN
		INSERT INTO Student (user_name, s_id, l_name, f_name, c_id) VALUES 
		(user_name0, 
		(SELECT s_id FROM Student WHERE user_name = user_name0), 
		(SELECT l_name FROM Student WHERE user_name = user_name0), 
		(SELECT f_name FROM Student WHERE user_name = user_name0), 
		c_id0);
	ELSE
		SELECT "error";
	END IF; 
END $$

DELIMITER $$
CREATE PROCEDURE showStudents1(IN c_id0 INT)
BEGIN
	SELECT * FROM Student s
    join Enrollment e on e.s_id = s.s_id
    where e.c_id = c_id0;
END $$

DELIMITER $$
CREATE PROCEDURE showStudents2(IN str0 VARCHAR(20))
BEGIN
	SELECT * FROM Student 
		WHERE lower(l_name) LIKE lower(CONCAT('%', str0, '%')) 
        OR lower(f_name) LIKE lower(CONCAT('%', str0, '%')) 
        OR lower(user_name) LIKE lower(CONCAT('%', str0, '%'));
END $$


DELIMITER $$
CREATE PROCEDURE assignGrade(IN a_id0 INT, s_id0 INT, grade0 INT)
BEGIN 
    DECLARE grade1 INT;
    IF (SELECT point_value FROM Assignment WHERE a_id = a_id0) >= grade0 THEN
		SET grade1 = (SELECT g.grade FROM Grade g
						WHERE g.a_id = a_id0 and g.s_id = s_id0);
		IF (isnull(grade1)) THEN
			insert into Grade (a_id, s_id, grade ) values (a_id0, s_id0, grade0);
		end if;
		IF grade1 != grade0 THEN
			UPDATE Grade g SET g.grade = grade0 
				WHERE g.a_id = a_id0
				AND g.s_id = s_id0;
		END IF;
        (SELECT 0);
	ELSE
		(SELECT point_value FROM Assignment WHERE a_id = a_id0); -- if the number of points exceeds the number of points configured for the assignment
	END IF;
END $$


DELIMITER $$
CREATE PROCEDURE studentGrades(IN s_id0 INT, c_id0 INT)
BEGIN 
	select a.a_id, a.name as Assignment, cg.name as Category, cg.weight, a.point_value, g.grade from assignment a
	join grade g on g.a_id = a.a_id
    join category cg on cg.cg_id = a.cg_id
	where g.s_id = s_id0
    and a.c_id = c_id0
    order by a.cg_id;
END $$

DELIMITER $$
CREATE PROCEDURE getStudent(IN username varchar(20))
BEGIN 
	select s_id from student where user_name = username;
END $$

DELIMITER $$
CREATE PROCEDURE getAssignmentID(IN name0 varchar(20))
BEGIN 
	select a_id from assignment where name = name0;
END $$

DELIMITER $$
CREATE PROCEDURE getCategoryTotals(c_id0 INT, s_id0 INT)
BEGIN 
	select cg.name, SUM(point_value) from assignment
    join Category cg on a.cg_id = cg.cd_id
    and a.c_id = c_id0
	and a.s_id = s_id0
	group by cg_id;
END $$

DELIMITER $$
CREATE PROCEDURE getCategoryEarned( c_id0 INT, s_id0 INT)
BEGIN 
	select cg.name, SUM(grade) from Grade
    join assignment a on a.a_id = g.a_id
    join Category cg on a.cg_id = cg.cd_id
    and a.c_id = c_id0
	and a.s_id = s_id0
    group by cg_id;
END $$

