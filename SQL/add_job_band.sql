USE floor_is_java_CiaraS;

CREATE TABLE job_band (
    job_band_id TINYINT UNSIGNED NOT NULL PRIMARY KEY,
    job_band_name VARCHAR(50)
);

INSERT INTO job_band (job_band_id, job_band_name)
VALUES
    (1, 'Apprentice'),
    (2, 'Trainee'),
    (3, 'Associate'),
    (4, 'Senior Associate'),
    (5, 'Consultant'),
    (6, 'Manager'),
    (7, 'Principal'),
    (8, 'Leadership Community');


ALTER TABLE job
ADD COLUMN job_band_id TINYINT UNSIGNED NOT NULL;

UPDATE job
SET job_band_id =
    CASE
        WHEN job_title = 'Senior Software Engineer' THEN 4
        WHEN job_title = 'Dynamics Lead Engineer' THEN 6
        WHEN job_title = 'Lead Software Engineer' THEN 5
        WHEN job_title = 'Test Engineer' THEN 2
        WHEN job_title = 'Innovation Lead' THEN 5
        WHEN job_title = 'Technology Leader' THEN 8
        WHEN job_title = 'Technical Architect' THEN 5
        WHEN job_title = 'Software Engineer' THEN 2
		END
		WHERE job_id >0;


ALTER TABLE job
ADD FOREIGN KEY (job_band_id) REFERENCES job_band(job_band_id);



