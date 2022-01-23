-- query 1) 
--showing the number of lesson given per month during a specified year. 
--It  also retrieves the specific number of individual and group lessons
--,as well as ensembles

SELECT date_trunc('month', booking_date) AS time, count(type_of_lesson) AS count FROM booking
GROUP BY date_trunc('month', booking_date) ORDER BY time;

SELECT date_trunc('month', lesson_date) AS time, count(ensemble) AS count FROM ensemble
GROUP BY date_trunc('month', lesson_date) ORDER BY time;

SELECT date_trunc('month', lesson_date) AS time, count(group_lesson) AS count FROM group_lesson
GROUP BY date_trunc('month', lesson_date) ORDER BY time;

SELECT date_trunc('month', lesson_date) AS time, count(individual_lesson) AS count FROM individual_lesson
GROUP BY date_trunc('month', lesson_date) ORDER BY time;

--query2)
-- Same as above, but retrieve the average number of lessons
--per month during the entire year

SELECT 'AVG OF ENSEMBLES LESSEON';
SELECT DATE_TRUNC('month',booking_date) AS time, 
ROUND((CAST (COUNT(skilllevel)AS DECIMAL)/12)::DECIMAL,2)
AS AVG FROM booking GROUP BY DATE_TRUNC('month', booking_date) ORDER BY time;

-- query 3)
-- List all instructor who have given more than a specific number of lessons
-- during the current month. 

WITH time_report AS (       
SELECT instructor_id, count (instructor_id)                                                                                                                                                                                 
FROM booking WHERE date_trunc('month', booking_date)=date_trunc('month',current_date)       
group by instructor_id order by count DESC    )
SELECT * FROM time_report WHERE count > 2;

--query 4)

SELECT * FROM ( SELECT max_seats, nr_of_students,
CASE
WHEN max_seats=nr_of_students THEN 'FULLY BOOKED'
WHEN 2 = max_seats-nr_of_students THEN 'FEW SEATS LEFT'
WHEN 1 = max_seats-nr_of_students THEN 'ONE SEAT LEFT'
ELSE 'JOIN'
END AS is_available FROM ensemble)
ensemble
WHERE EXTRACT('week' FROM lesson_date)=EXTRACT('week' FROM current_date + interval '1 week');




