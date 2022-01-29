CREATE TABLE person (
    personal_id VARCHAR(100) NOT NULL,
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
    lesson_cost VARCHAR(500) NOT NULL,
    type_of_lesson VARCHAR(50) NOT NULL,
    level_of_lesson VARCHAR(50) NOT NULL
);

ALTER TABLE pricing ADD CONSTRAINT PK_pricing PRIMARY KEY (lesson_cost);


CREATE TABLE salary (
    employment_id VARCHAR(500) NOT NULL,
    amount VARCHAR(500) NOT NULL
);

ALTER TABLE salary ADD CONSTRAINT PK_salary PRIMARY KEY (employment_id);


CREATE TABLE student (
    student_id VARCHAR(100) NOT NULL,
    nr_of_siblings VARCHAR(50),
    guardian_id VARCHAR(500) NOT NULL,
    personal_id VARCHAR(100)
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (student_id);


CREATE TABLE instructor (
    instructor_id VARCHAR(100) NOT NULL,
    instrument_skill VARCHAR(50) NOT NULL,
    prefered_class VARCHAR(100) NOT NULL,
    personal_id VARCHAR(100),
    employment_id VARCHAR(500)
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (instructor_id);


CREATE TABLE payment (
    amount VARCHAR(500) NOT NULL,
    rental_instrument VARCHAR(500),
    sibling_discount VARCHAR(500),
    student_id VARCHAR(100) 
);


CREATE TABLE rental_instrument (
    instrument_id VARCHAR(100) NOT NULL,
    type_instrument VARCHAR(100),
    is_rented BOOLEAN,
    quantity VARCHAR(10),
    monthly_fee VARCHAR(100),
    rental_time TIME(6),
    rental_date DATE,
    student_id VARCHAR(100) 
);

ALTER TABLE rental_instrument ADD CONSTRAINT PK_rental_instrument PRIMARY KEY (instrument_id);


CREATE TABLE Schedule (
    lesson_type VARCHAR(50) NOT NULL,
    lesson_time TIME(6) NOT NULL,
    lesson_date DATE NOT NULL,
    student_id VARCHAR(100),
    instructor_id VARCHAR(100)
);

ALTER TABLE Schedule ADD CONSTRAINT PK_Schedule PRIMARY KEY (lesson_type);


CREATE TABLE booking(
    type_of_lesson VARCHAR(500) NOT NULL,
    booking_time TIME(6) NOT NULL,
    booking_date DATE NOT NULL,
    skilllevel VARCHAR(500) NOT NULL,
    type_of_instrument VARCHAR(500),
    student_id VARCHAR(100) ,
    instructor_id VARCHAR(100)
);


CREATE TABLE ensemble (
    skill_level VARCHAR(500) NOT NULL,
    instructor_id VARCHAR(100) NOT NULL,
    instrument VARCHAR(500) NOT NULL,
    max_seats INT,
    min_seats INT,
    nr_of_students INT,
    lesson_time TIME(6) NOT NULL,
    lesson_date DATE NOT NULL,
    type_of_lesson VARCHAR(500)
);



CREATE TABLE group_lesson (
    skill_level VARCHAR(500) NOT NULL,
    instructor_id VARCHAR(500) NOT NULL,
    instrument VARCHAR(500) NOT NULL,
    nr_of_students VARCHAR(100),
    lesson_time TIME(6) NOT NULL,
    lesson_date DATE NOT NULL,
    type_of_lesson VARCHAR(500)
);



CREATE TABLE individual_lesson (
    skill_level VARCHAR(50) NOT NULL,
    instructor_id VARCHAR(500) NOT NULL,
    instrument VARCHAR(500) NOT NULL,
    lesson_time TIME(6) NOT NULL,
    lesson_date DATE NOT NULL,
    type_of_lesson VARCHAR(500)
);



