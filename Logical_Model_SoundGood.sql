CREATE TABLE person (
 personal_id VARCHAR (20),
 first_name VARCHAR(500),
 last_name VARCHAR(500),
 city VARCHAR(500),
 street VARCHAR(500),
 postal_code VARCHAR(500),
 email_address VARCHAR(500),
 phone_nr VARCHAR(500)
);

ALTER TABLE person ADD CONSTRAINT PK_person PRIMARY KEY (personal_id);


CREATE TABLE pricing (
 type_of_lesson VARCHAR(500),
 skill_level VARCHAR(500),
 sibling_discount VARCHAR(500)
);


CREATE TABLE student (
 student_id INT NOT NULL,
 personal_id INT NOT NULL,
 nr_of_siblings VARCHAR(50),
 guardian_id VARCHAR(500) NOT NULL
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (student_id,personal_id);


CREATE TABLE instructor (
 instructor_id INT NOT NULL,
 personal_id INT NOT NULL,
 employment_id VARCHAR(500) NOT NULL,
 instrument_skill VARCHAR(50) NOT NULL,
 nr_of_lesson VARCHAR(50),
 type_of_lesson VARCHAR(500)
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (instructor_id,personal_id);


CREATE TABLE payment (
 student_id INT NOT NULL,
 personal_id INT NOT NULL,
 amount VARCHAR(500) NOT NULL,
 rental_instrument VARCHAR(500)
);

ALTER TABLE payment ADD CONSTRAINT PK_payment PRIMARY KEY (student_id,personal_id);


CREATE TABLE rental_instrument (
 instrument_id VARCHAR(500) NOT NULL,
 student_id INT NOT NULL,
 personal_id INT NOT NULL,
 type_instrument VARCHAR(100),
 is_rented VARCHAR(10),
 quantity VARCHAR(10),
 monthly_fee VARCHAR(100),
 delivery VARCHAR(100),
 rental_time TIMESTAMP(6),
 rental_date DATE
);

ALTER TABLE rental_instrument ADD CONSTRAINT PK_rental_instrument PRIMARY KEY (instrument_id,student_id,personal_id);


CREATE TABLE salary (
 instructor_id INT NOT NULL,
 personal_id INT NOT NULL,
 amount VARCHAR(500),
 employment_id VARCHAR(500)
);

ALTER TABLE salary ADD CONSTRAINT PK_salary PRIMARY KEY (instructor_id,personal_id);


CREATE TABLE Schedule (
 lesson_type VARCHAR(50) NOT NULL,
 student_id INT NOT NULL,
 personal_id INT NOT NULL,
 instructor_id INT NOT NULL,
 lesson_time TIMESTAMP(6) NOT NULL,
 lesson_date DATE NOT NULL
);

ALTER TABLE Schedule ADD CONSTRAINT PK_Schedule PRIMARY KEY (lesson_type,student_id,personal_id,instructor_id);


CREATE TABLE booking (
 type_of_lesson VARCHAR(500) NOT NULL,
 personal_id INT NOT NULL,
 student_id INT NOT NULL,
 instructor_id INT NOT NULL,
 booking_time TIMESTAMP(6) NOT NULL,
 booking_date DATE NOT NULL,
 skilllevel VARCHAR(500) NOT NULL
);

ALTER TABLE booking ADD CONSTRAINT PK_booking PRIMARY KEY (type_of_lesson,personal_id,student_id,instructor_id);


CREATE TABLE ensemble (
 skill_level VARCHAR(500) NOT NULL,
 type_of_lesson VARCHAR(500) NOT NULL,
 personal_id INT NOT NULL,
 student_id INT NOT NULL,
 instructor_id INT NOT NULL,
 instuctor_id VARCHAR(100) NOT NULL,
 instrument VARCHAR(500) NOT NULL,
 genre VARCHAR(100) NOT NULL,
 max_seats VARCHAR(100),
 min_sits VARCHAR(100),
 nr_of_students VARCHAR(100) NOT NULL,
 lesson_time TIMESTAMP(10) NOT NULL,
 lesson_date DATE NOT NULL
);

ALTER TABLE ensemble ADD CONSTRAINT PK_ensemble PRIMARY KEY (skill_level,type_of_lesson,personal_id,student_id,instructor_id);


CREATE TABLE group_lesson (
 skill_level VARCHAR(500) NOT NULL,
 type_of_lesson VARCHAR(500) NOT NULL,
 personal_id INT NOT NULL,
 student_id INT NOT NULL,
 instructor_id INT NOT NULL,
 instuctor_id VARCHAR(500) NOT NULL,
 instrument VARCHAR(500) NOT NULL,
 nr_of_students VARCHAR(100) NOT NULL,
 lesson_time TIMESTAMP(10) NOT NULL,
 lesson_date DATE NOT NULL
);

ALTER TABLE group_lesson ADD CONSTRAINT PK_group_lesson PRIMARY KEY (skill_level,type_of_lesson,personal_id,student_id,instructor_id);


CREATE TABLE individual_lesson (
 skill_level VARCHAR(50) NOT NULL,
 type_of_lesson VARCHAR(500) NOT NULL,
 personal_id INT NOT NULL,
 student_id INT NOT NULL,
 instructor_id_0 INT NOT NULL,
 instructor_id VARCHAR(500) NOT NULL,
 instrument VARCHAR(500) NOT NULL,
 nr_of_students VARCHAR(100) NOT NULL,
 lesson_time TIMESTAMP(10) NOT NULL,
 lesson_date DATE NOT NULL
);

ALTER TABLE individual_lesson ADD CONSTRAINT PK_individual_lesson PRIMARY KEY (skill_level,type_of_lesson,personal_id,student_id,instructor_id_0);


ALTER TABLE student ADD CONSTRAINT FK_student_0 FOREIGN KEY (personal_id) REFERENCES person (personal_id);


ALTER TABLE instructor ADD CONSTRAINT FK_instructor_0 FOREIGN KEY (personal_id) REFERENCES person (personal_id);


ALTER TABLE payment ADD CONSTRAINT FK_payment_0 FOREIGN KEY (student_id,personal_id) REFERENCES student (student_id,personal_id);


ALTER TABLE rental_instrument ADD CONSTRAINT FK_rental_instrument_0 FOREIGN KEY (student_id,personal_id) REFERENCES student (student_id,personal_id);


ALTER TABLE salary ADD CONSTRAINT FK_salary_0 FOREIGN KEY (instructor_id,personal_id) REFERENCES instructor (instructor_id,personal_id);


ALTER TABLE Schedule ADD CONSTRAINT FK_Schedule_0 FOREIGN KEY (student_id,personal_id) REFERENCES student (student_id,personal_id);
ALTER TABLE Schedule ADD CONSTRAINT FK_Schedule_1 FOREIGN KEY (instructor_id,personal_id) REFERENCES instructor (instructor_id,personal_id);


ALTER TABLE booking ADD CONSTRAINT FK_booking_0 FOREIGN KEY (personal_id) REFERENCES person (personal_id);
ALTER TABLE booking ADD CONSTRAINT FK_booking_1 FOREIGN KEY (student_id,personal_id) REFERENCES student (student_id,personal_id);
ALTER TABLE booking ADD CONSTRAINT FK_booking_2 FOREIGN KEY (instructor_id,personal_id) REFERENCES instructor (instructor_id,personal_id);


ALTER TABLE ensemble ADD CONSTRAINT FK_ensemble_0 FOREIGN KEY (type_of_lesson,personal_id,student_id,instructor_id) REFERENCES booking (type_of_lesson,personal_id,student_id,instructor_id);


ALTER TABLE group_lesson ADD CONSTRAINT FK_group_lesson_0 FOREIGN KEY (type_of_lesson,personal_id,student_id,instructor_id) REFERENCES booking (type_of_lesson,personal_id,student_id,instructor_id);


ALTER TABLE individual_lesson ADD CONSTRAINT FK_individual_lesson_0 FOREIGN KEY (type_of_lesson,personal_id,student_id,instructor_id_0) REFERENCES booking (type_of_lesson,personal_id,student_id,instructor_id);


