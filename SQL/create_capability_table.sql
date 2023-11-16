USE floor_is_java_CiaraS;

-- Create a capability table
CREATE TABLE capability (
	capability_id TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255)
);

-- Add sample data to the capability table
INSERT INTO capability (capability_id, `name`) VALUES
(1, 'Engineering'),
(2, 'Platforms'),
(3, 'Data & AI'),
(4, 'Quality Assurance'),
(5, 'Cyber Security');

-- Add the 'capability_id' column to the 'job' table
ALTER TABLE job
ADD COLUMN capability_id TINYINT UNSIGNED NOT NULL;

-- Add capability IDs to the existing rows in the table
UPDATE job
SET capability_id =
    CASE
        WHEN job_title = 'Senior Software Engineer' THEN 1
        WHEN job_title = 'Dynamics Lead Engineer' THEN 1
        WHEN job_title = 'Lead Software Engineer' THEN 1
        WHEN job_title = 'Test Engineer' THEN 4
        WHEN job_title = 'Innovation Lead' THEN 2
        WHEN job_title = 'Technology Leader' THEN 5
        WHEN job_title = 'Technical Architect' THEN 4
        WHEN job_title = 'Software Engineer' THEN 1
    END;

-- Add the foreign key constraint to link the 'job' and 'capability' tables
ALTER TABLE job
ADD CONSTRAINT fk_job_capability
FOREIGN KEY (capability_id)
REFERENCES capability(capability_id);

