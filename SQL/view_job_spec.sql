ALTER TABLE job
ADD job_spec_summary TEXT, job_url varchar(255);

UPDATE job
SET job_spec_summary =
    CASE
        WHEN job_title = 'Senior Software Engineer' THEN 'Designs and develops complex software solutions, mentors junior engineers, and collaborates with cross-functional teams to deliver high-quality software products.'
        WHEN job_title = 'Dynamics Lead Engineer' THEN 'Leads the development and implementation of Microsoft Dynamics solutions, collaborates with stakeholders to gather requirements, and ensures the successful delivery of Dynamics projects.'
        WHEN job_title = 'Lead Software Engineer' THEN 'Manages a team of software engineers, sets technical direction, and oversees the end-to-end software development process, ensuring code quality and project deadlines are met.'
        WHEN job_title = 'Test Engineer' THEN 'Designs and executes test plans, identifies and reports software defects, and collaborates with development teams to ensure the delivery of reliable and high-quality software products.'
        WHEN job_title = 'Innovation Lead' THEN 'Drives innovation initiatives, identifies emerging technologies, and leads cross-functional teams to develop and implement innovative solutions to business challenges.'
        WHEN job_title = 'Technology Leader' THEN 'Provides strategic leadership in technology, assesses and implements new technologies, and ensures alignment between technology solutions and business goals.'
        WHEN job_title = 'Technical Architect' THEN 'Designs and oversees the implementation of technical solutions, ensures architectural integrity, and provides guidance to development teams on best practices and design patterns.'
        WHEN job_title = 'Software Engineer' THEN 'Develops, tests, and maintains software applications, collaborates with cross-functional teams, and follows best practices to deliver efficient and reliable software solutions.'
    END;

UPDATE job
SET job_url =
    CASE
         WHEN job_title = 'Senior Software Engineer' THEN 'https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Senior%20Software%20Engieneer%20%28Senior%20Associate%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1'
         WHEN job_title = 'Dynamics Lead Engineer' THEN 'https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20Profile%20%2D%20Dynamics%20365%20PP%20Solution%20Architect%20%28M%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1'
         WHEN job_title = 'Lead Software Engineer' THEN 'https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Lead%20Software%20Engineer%20%28Consultant%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1'
         WHEN job_title = 'Test Engineer' THEN 'https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%20Test%20Engineer%20%28Associate%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering'
         WHEN job_title = 'Innovation Lead' THEN 'https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Innovation%20Lead%20%28Consultant%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1'
         WHEN job_title = 'Technology Leader' THEN 'https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20Profile%20%2D%20Technology%20Leader%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1'
         WHEN job_title = 'Technical Architect' THEN 'https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20Profile%20%2D%20Technical%20Architect%20%28Consultant%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1'
         WHEN job_title = 'Software Engineer' THEN 'https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Software%20Engineer%20%28Associate%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1'

