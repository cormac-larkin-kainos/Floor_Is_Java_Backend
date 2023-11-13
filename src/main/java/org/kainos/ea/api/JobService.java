package org.kainos.ea.api;

import org.kainos.ea.cli.Job;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobDao;
import org.kainos.ea.exception.FailedToGetJobsException;
import java.sql.SQLException;
import java.util.List;

public class JobService {

    private final JobDao jobDao;
    private final DatabaseConnector databaseConnector;

    public JobService(JobDao jobDao, DatabaseConnector databaseConnector) {
        this.jobDao = jobDao;
        this.databaseConnector = databaseConnector;
    }

    public List<Job> getAllJobs() throws FailedToGetJobsException {

        try {
            return jobDao.getAllJobs(databaseConnector.getConnection());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetJobsException();
        }

    }


}
