DROP DATABASE IF EXISTS GroupProject;
CREATE DATABASE GroupProject;
USE GroupProject;

INSERT INTO Class (c_number, term, sec_number, description) VALUES 
('CS410', "FA18", 1, "Databases"),
('CS410', "FA18", 2, "Databases"),
('CS410', "SP19", 1, "Databases"),
('CS410', "SP19", 2, "Databases"),
('CS410', "FA19", 1, "Databases"),
('CS410', "FA19", 2, "Databases"),
('CS410', "SP20", 1, "Databases"),
('CS410', "FA20", 2, "Databases");


INSERT INTO Category (c_id, name, weight) VALUES 
(1, 'homework', 0.2),
(1, 'exam', 0.5),
(1, 'project', 0.3),
(2, 'homework', 0.2),
(2, 'exam', 0.5),
(2, 'project', 0.3),
(3, 'homework', 0.2),
(3, 'exam', 0.5),
(3, 'project', 0.3),
(4, 'homework', 0.2),
(4, 'exam', 0.5),
(4, 'project', 0.3),
(5, 'homework', 0.2),
(5, 'exam', 0.5),
(5, 'project', 0.3),
(6, 'homework', 0.2),
(6, 'exam', 0.5),
(6, 'project', 0.3),
(7, 'homework', 0.2),
(7, 'exam', 0.5),
(7, 'project', 0.3),
(8, 'homework', 0.2),
(8, 'exam', 0.5),
(8, 'project', 0.3);

INSERT INTO Student (user_name, studentID, l_name, f_name) VALUES
('frankreynolds', 100, 'reynolds', 'frank'),
('dennisreynolds', 110, 'reynolds', 'dennis'),
('deandrareynolds', 120, 'reynolds', 'dee'),
('ronaldmcdonald', 130, 'mcdonald', 'ron'),
('charleskelly', 140, 'charlie', 'kelly'),
('liammcpoyle', 150, 'mcpoyle', 'liam'),
('gailmcpoyle', 160, 'mcpoyle', 'gail'),
('ryanmcpoyle', 170, 'mypoyle', 'ryan');

INSERT INTO Enrollment (s_id, c_id) VALUES 
(1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8);

INSERT INTO Assignment (cg_id, name, description, point_value, c_id) VALUES 
(1, 'homework1', 'very difficult homework', 50, 1), 
(2, 'exam1', 'very difficult exam', 100,  1), 
(3, 'project1', 'very difficult project', 80, 1), 
(4, 'homework2', 'very difficult homework', 50, 2), 
(5, 'exam2', 'very difficult exam', 100, 2), 
(6, 'project2', 'very difficult project', 80, 2), 
(7, 'homework3', 'very difficult homework', 50, 3), 
(8, 'exam3', 'very difficult exam', 100, 3), 
(9, 'project3', 'very difficult project', 80, 3), 
(10, 'homework4', 'very difficult homework', 50, 4), 
(11, 'exam4', 'very difficult exam', 100, 4), 
(12, 'project4', 'very difficult project', 80, 4), 
(13, 'homework5', 'very difficult homework', 50, 5), 
(14, 'exam5', 'very difficult exam', 100, 5), 
(15, 'project5', 'very difficult project', 80, 5), 
(16, 'homework6', 'very difficult homework', 50, 6), 
(17, 'exam6', 'very difficult exam', 100, 6), 
(18, 'project6', 'very difficult project', 80, 6), 
(19, 'homework7', 'very difficult homework', 50, 7), 
(20, 'exam7', 'very difficult exam', 100, 7), 
(21, 'project7', 'very difficult project', 80, 7), 
(22, 'homework8', 'very difficult homework', 50, 8), 
(23, 'exam8', 'very difficult exam', 100, 8), 
(24, 'project8', 'very difficult project', 80, 8);

INSERT INTO Grade (s_id, a_id, grade) VALUES 
(1, 1, 48), 
(1, 2, 88), 
(1, 3, 78), 
(2, 1, 28), 
(2, 2, 38), 
(2, 3, 58), 
(3, 1, 49), 
(3, 2, 49), 
(3, 3, 49), 
(4, 1, 48), 
(4, 2, 48), 
(4, 3, 78), 
(5, 1, 48), 
(5, 2, 89), 
(5, 3, 78), 
(6, 1, 29), 
(6, 2, 88), 
(6, 3, 78), 
(7, 1, 43), 
(7, 2, 83), 
(7, 3, 69), 
(8, 1, 50), 
(8, 2, 100), 
(8, 3, 80); 

