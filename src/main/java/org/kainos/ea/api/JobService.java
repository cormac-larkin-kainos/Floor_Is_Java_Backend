package org.kainos.ea.api;

import javassist.NotFoundException;
import org.kainos.ea.cli.Job;
import org.kainos.ea.cli.JobRequest;
import org.kainos.ea.exception.ProjectException;
import org.kainos.ea.core.JobValidator;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobDao;
import org.kainos.ea.exception.FailedToGetJobsException;
import org.kainos.ea.exception.FailedtoDeleteException;

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

        if (validation != null) {
            throw new ProjectException("Invalid data for job creation");
        }
        int id = jobDao.createJobRole(job, databaseConnector.getConnection());

        if (id < 1) {
            throw new ProjectException("Job creation failed");
        }
        return id;
    }

    public Job getById(int id) throws FailedToGetJobsException {
        try {
            return jobDao.getJobById(databaseConnector.getConnection(),id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetJobsException();
        }
    }

    public void delete(int id) throws FailedtoDeleteException, NotFoundException {
        try {

            Job job = jobDao.getJobById(databaseConnector.getConnection(), id);
            if (job == null) {
                System.err.println("Attempted to delete job by id " + id + " but it doesn't exist!");
                throw new NotFoundException("Could not find job by id " + id);
            }

            jobDao.deleteJob(databaseConnector.getConnection(), id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedtoDeleteException("Failed to delete job!");
        }
    }
}
