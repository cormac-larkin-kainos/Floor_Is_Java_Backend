package org.kainos.ea.api;

import org.kainos.ea.cli.Job;
import org.kainos.ea.cli.JobRequest;
import org.kainos.ea.exception.ProjectException;
import org.kainos.ea.core.JobValidator;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobDao;
import org.kainos.ea.exception.FailedToGetJobsException;
import java.sql.SQLException;
import java.util.List;

public class JobService {

    private final JobDao jobDao;
    private final DatabaseConnector databaseConnector;
    private final JobValidator jobValidator;

    public JobService(JobDao jobDao, DatabaseConnector databaseConnector, JobValidator jobValidator) {
        this.jobDao = jobDao;
        this.databaseConnector = databaseConnector;
        this.jobValidator = jobValidator;
    }

    public List<Job> getAllJobs() throws FailedToGetJobsException {

        try {
            return jobDao.getAllJobs(databaseConnector.getConnection());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetJobsException();
        }

    }

    public int createJobRole(JobRequest job) throws SQLException, ProjectException {
        String validation = jobValidator.isValid(job);

        if (validation != null){
            throw new ProjectException();
        }
        int id = jobDao.createJobRole(job, databaseConnector.getConnection());

        if (id<1){
            throw new ProjectException();
        }
        return id;
    }


}
