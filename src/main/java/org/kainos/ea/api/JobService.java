package org.kainos.ea.api;

import org.kainos.ea.cli.Job;
import org.kainos.ea.db.JobDao;
import org.kainos.ea.exception.FailedToGetJobsException;

import java.sql.SQLException;
import java.util.List;

public class JobService {

    private final JobDao jobDao = new JobDao();

    public List<Job> getAllJobs() throws FailedToGetJobsException {

        try {
            return jobDao.getAllJobs();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetJobsException();
        }

    }

}
