CREATE TABLE job (
    job_id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    job_title VARCHAR(255)
);

INSERT INTO job (job_title)
        VALUES ('Senior Software Engineer'),
        ('Dynamics Lead Engineer'),
        ('Lead Software Engineer'),
        ('Test Engineer'),
        ('Innovation Lead'),
        ('Technology Leader'),
        ('Technical Architect'),
        ('Software Engineer');