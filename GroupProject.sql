CREATE DATABASE GroupProject;

CREATE TABLE Class 
(
    c_id INT NOT NULL AUTO_INCREMENT,
    c_number VARCHAR(5) NOT NULL,
    term VARCHAR(4) NOT NULL,
    sec_number INT AUTO_INCREMENT NOT NULL,
    description VARCHAR(100),
    PRIMARY KEY (c_id)
);

CREATE TABLE Category 
(
    cg_id INT NOT NULL AUTO_INCREMENT,
    c_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    weight DECIMAL(2,2) NOT NULL,
    PRIMARY KEY(cg_id),
    FOREIGN KEY (c_id) REFERENCES Class(c_id)
);

CREATE TABLE Student
(
    s_id INT NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(20) NOT NULL,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (s_id)
);

CREATE TABLE Assignment 
(
    a_id INT NOT NULL AUTO_INCREMENT,
    cg_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(100), 
    point_value INT NOT NULL,
    s_id INT NOT NULL,
    grade VARCHAR(2) NOT NULL,
    PRIMARY KEY (a_id),
    FOREIGN KEY (s_id) REFERENCES Student(s_id),
    FOREIGN KEY (cg_id) REFERENCES Category(cg_id),
    FOREIGN KEY (s_id) REFERENCES Student(s_id)
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
CREATE PROCEDURE createClass(IN c_number VARCHAR(5), term VARCHAR(4),  description VARCHAR(100), sec_number INT)
BEGIN
	INSERT INTO Class (C_number, Term, Description, Sec_number) VALUES (c_number, term, description, sec_number);
END $$

    
    
