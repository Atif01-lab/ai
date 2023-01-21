SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS lecturer;
DROP TABLE IF EXISTS class;
DROP TABLE IF EXISTS classroom;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS major;
DROP TABLE IF EXISTS subject;
DROP TABLE IF EXISTS class_students;
DROP TABLE IF EXISTS lecturer_subjects;



CREATE TABLE admin (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       token VARCHAR(255) NOT NULL

);

CREATE TABLE department (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);
CREATE TABLE lecturer (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          token VARCHAR(255) NOT NULL,
                          department_id BIGINT NOT NULL,
                          PRIMARY KEY (id),
                          FOREIGN KEY (department_id) REFERENCES department (id)
);
CREATE TABLE classroom (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           floor_number INT NOT NULL,
                           room_number INT NOT NULL,
                            capacity INT NOT NULL,
                           department_id BIGINT NOT NULL,
                           FOREIGN KEY (department_id) REFERENCES department(id)
);
CREATE TABLE major (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       department_id BIGINT NOT NULL,
                       FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE subject (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         code VARCHAR(255) NOT NULL,
                         lecturer_id BIGINT NOT NULL,
                         major_id BIGINT NOT NULL,
                         FOREIGN KEY (lecturer_id) REFERENCES lecturer(id),
                         FOREIGN KEY (major_id) REFERENCES major(id)
);
CREATE TABLE student (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         token VARCHAR(255) NOT NULL,
                         major_id BIGINT NOT NULL,
                         PRIMARY KEY (id),
                         FOREIGN KEY (major_id) REFERENCES major (id)
);

CREATE TABLE class (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       time VARCHAR(255) NOT NULL,
                       date VARCHAR(255) NOT NULL,
                       is_cancelled BOOLEAN,
                       department_id BIGINT NOT NULL,
                       classroom_id BIGINT NOT NULL,
                       lecturer_id BIGINT NOT NULL,
                       FOREIGN KEY (department_id) REFERENCES department(id),
                       FOREIGN KEY (classroom_id) REFERENCES classroom(id),
                       FOREIGN KEY (lecturer_id) REFERENCES lecturer(id)
);

CREATE TABLE class_students (
                                class_id BIGINT NOT NULL,
                                student_id BIGINT NOT NULL,
                                PRIMARY KEY (class_id, student_id),
                                FOREIGN KEY (class_id) REFERENCES class(id),
                                FOREIGN KEY (student_id) REFERENCES student(id)
);
CREATE TABLE lecturer_subject (
                                  lecturer_id BIGINT NOT NULL,
                                  subject_id BIGINT NOT NULL,
                                  PRIMARY KEY (lecturer_id, subject_id),
                                  FOREIGN KEY (lecturer_id) REFERENCES lecturer(id),
                                  FOREIGN KEY (subject_id) REFERENCES subject(id)
);


INSERT INTO admin (name,email, password,token) VALUES
                                        ('admin1','admin@university.edu', 'password123','admintoken1'),
                                        ('admin2','timetable@university.edu', 'timetable456','admintoken2');

-- insert data into the department table
INSERT INTO department (name) VALUES
                                  ('Science'),
                                  ('Engineering'),
                                  ('Arts');

-- insert data into the lecturer table
INSERT INTO lecturer (name,email, password,department_id,token) VALUES
                                                         ('John','prof.john@university.edu', 'lecturer123', 1,'lecturertoken1'),
                                                         ('Jane','dr.jane@university.edu', 'lecturer456', 2,'lecturertoken2'),
                                                         ('Smith','mr.smith@university.edu', 'lecturer789', 3,'lecturertoken3');

-- insert data into the classroom table
INSERT INTO classroom (floor_number,room_number,capacity, department_id) VALUES
                                                        (1,1,30, 1),
                                                        (2,2,40, 2),
                                                        (3,3,50, 3);

-- insert data into the major table
INSERT INTO major (name, department_id) VALUES
                                            ('Computer Science', 1),
                                            ('Mathematics', 2),
                                            ('Physics', 3);

-- insert data into the subject table
INSERT INTO subject (name,code, lecturer_id, major_id) VALUES
                                                      ('Intro to CS','CS101', 1, 1),
                                                      ('Maths Basics ','MA201', 2, 2),
                                                      ('Physics Basics','PH301', 3, 3);
-- insert data into the student table
INSERT INTO student (name,email, password,major_id,token) VALUES
                                                   ('Jane','jane.doe@university.edu', 'student123', 1,'studenttoken1'),
                                                   ('John','john.smith@university.edu','student456', 2,'studenttoken2'),
                                                   ('Sarah','sarah.lee@university.edu','student789', 3,'studenttoken3');


-- insert data into the class table
INSERT INTO class (time, date, department_id, classroom_id, lecturer_id,is_cancelled) VALUES
                                                                                          ('10:00:00', '2022-09-10', 1, 1, 1,false),
                                                                                          ('12:00:00', '2022-09-10', 2, 2, 2,false),
                                                                                          ('14:00:00', '2022-09-10', 3, 3, 3,false);



-- insert data into the class_students join table
INSERT INTO class_students (class_id, student_id) VALUES
                                                      (1, 1),
                                                      (2, 2),
                                                      (3, 3);

INSERT INTO lecturer_subject (lecturer_id, subject_id) VALUES
                                                           (1, 1),
                                                           (2, 2),
                                                           (3, 3);

