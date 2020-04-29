DROP DATABASE IF EXISTS GroupProject;
CREATE DATABASE GroupProject;
USE GroupProject;

DROP TABLE IF EXISTS Class;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Assignment;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Grade; 

DROP PROCEDURE IF EXISTS listClass; 


CREATE TABLE Term
(
	t_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(5) NOT NULL,
    start_date DATE NOT NULL,
    PRIMARY KEY (t_id)
);

CREATE TABLE Class 
(
	c_id INT NOT NULL AUTO_INCREMENT,
    c_number VARCHAR(10) NOT NULL,
    term INT NOT NULL,
    sec_number INT NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY (c_id),
	FOREIGN KEY (term) REFERENCES Term(t_id)

);


CREATE TABLE Category -- maybe delete c_id? 
(
	cg_id INT NOT NULL AUTO_INCREMENT,
    -- c_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    weight DECIMAL(2,2) NOT NULL,
    PRIMARY KEY(cg_id)
    -- FOREIGN KEY (c_id) REFERENCES Class(c_id)
);

CREATE TABLE Student -- s_id is not auto incremented, looks like it should always be given by user?
(
	s_id INT NOT NULL, 
    user_name VARCHAR(20) NOT NULL,
    l_name VARCHAR(20) NOT NULL,
    f_name VARCHAR(20) NOT NULL,
    c_id INT,
    PRIMARY KEY (s_id),
    FOREIGN KEY (c_id) REFERENCES Class(c_id)
);

CREATE TABLE Assignment -- we should probably get rid of s_id 
(
    a_id INT NOT NULL AUTO_INCREMENT,
    cg_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(100), 
    point_value INT NOT NULL,
    -- s_id INT NOT NULL,
    grade VARCHAR(2),
    PRIMARY KEY (a_id),
    -- FOREIGN KEY (s_id) REFERENCES Student(s_id),
    FOREIGN KEY (cg_id) REFERENCES Category(cg_id)
); 

CREATE TABLE Grade
(
	g_id INT NOT NULL AUTO_INCREMENT, 
    cg_id INT NOT NULL,
    s_id INT NOT NULL,
    c_id INT NOT NULL,
    a_id INT NOT NULL, 
    grade VARCHAR(2) NOT NULL, 
    PRIMARY KEY (g_id),
    FOREIGN KEY (cg_id) REFERENCES Category(cg_id),
    FOREIGN KEY (c_id) REFERENCES Class(c_id),
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
	JOIN Student s ON c.c_id = s.c_id
    GROUP BY c.c_id;
END $$


DELIMITER $$
CREATE PROCEDURE selectClass1(IN c_number0 VARCHAR(10)) 
BEGIN
	DECLARE num_of_secs INT;
    SET num_of_secs = (SELECT COUNT(sec_number) FROM Class GROUP BY term, c_number); -- if there's more than one section, then it'll execute the query & return a result set. Nothing will return otherwise. 
	IF (num_of_secs = 1) THEN
    SELECT c_id, c_number, term, description
    FROM Class c
    JOIN Term on t.name = c.term
    WHERE c_number = c_number0 AND sec_number = 1
    ORDER BY t.start_date DESC
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
CREATE PROCEDURE showCategories()
BEGIN 
	SELECT cg_id, name, weight
    FROM Category;
END $$

DELIMITER $$
CREATE PROCEDURE addCategory(IN name0 VARCHAR(20), weight0 DECIMAL(2,2)) -- I think there's no relationship between Category and Class? We don't require class to create a new category.
BEGIN
	INSERT INTO Category (name, weight) VALUES (name0, weight0);
END $$

DELIMITER $$
CREATE PROCEDURE showAssignment() -- I am not sure how this is supposed to work. Do we return the sum value of point values grouped by category? 
BEGIN 
	SELECT a.cg_id, cg.name, SUM(a.point_value) 
    FROM Assignment a
    JOIN Category cg ON cg.cg_id = a.cg_id
    GROUP BY cg_id;
END $$

DELIMITER $$
CREATE PROCEDURE addAssignment(IN name0 VARCHAR(20), name1 VARCHAR(20), description0 VARCHAR(100), point_value0 INT) -- name0 refers to name in Assignment and name1 refers to name in Category. Also I think there's no relationship linking Assignment and Student as we don't require s_id to create a new assignment. 
BEGIN
	DECLARE cg_id0 INT;
    SET cg_id0 = (SELECT cg_id FROM Category WHERE name = name1);
	INSERT INTO Assignment (name, cg_id, description, point_value) VALUES (name0, cg_id0, description0, point_value0);
END $$


DELIMITER $$
CREATE PROCEDURE updateStudent(IN user_name0 VARCHAR(20), s_id0 INT, l_name0 VARCHAR(20), f_name0 VARCHAR(20)) 
BEGIN
	IF CONCAT(f_name0, " ", l_name0) = (SELECT CONCAT(f_name0, " ", l_name0) FROM Student s WHERE s.s_id = s_id0) THEN
		UPDATE Student SET f_name = f_name0, l_name = l_name0 WHERE s_id = s_id0;
        SELECT "updated";
	END IF;
END $$

DELIMITER $$
CREATE PROCEDURE addStudent1(IN user_name0 VARCHAR(20), s_id0 INT, l_name0 VARCHAR(20), f_name0 VARCHAR(20), c_id0 INT) 
BEGIN
	INSERT INTO Student (user_name, s_id, l_name, f_name, c_id) VALUES (user_name0, s_id0, l_name0, f_name0, c_id0);
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
	SELECT * FROM Student WHERE c_id = c_id0;
END $$

DELIMITER $$
CREATE PROCEDURE showStudents2(IN str0 VARCHAR(20))
BEGIN
	SELECT * FROM Student WHERE l_name LIKE '%str0' OR f_name LIKE '%str0' OR user_name LIKE '%str0';
END $$




