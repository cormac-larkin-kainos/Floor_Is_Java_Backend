package org.kainos.ea.api;

import javassist.NotFoundException;
import org.kainos.ea.cli.Responsibility;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.ResponsibilityDao;
import org.kainos.ea.exception.FailedToGetResponsibilitiesException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ResponsibilityService {

    private final ResponsibilityDao responsibilityDao;
    private final DatabaseConnector databaseConnector;

    public ResponsibilityService(ResponsibilityDao responsibilityDao, DatabaseConnector databaseConnector) {
        this.responsibilityDao = responsibilityDao;
        this.databaseConnector = databaseConnector;
    }

    public List<Responsibility> getResponsibilitiesForJob(int jobId) throws FailedToGetResponsibilitiesException, NotFoundException {
        try {
            // Check if jobId exists in the database before calling the dao
            if (!responsibilityDao.doesJobExist(databaseConnector.getConnection(), jobId)) {
                throw new NotFoundException("Job with ID" + jobId + "not found.");
            }

            // If job exists, proceed to get responsibilities
            return responsibilityDao.getResponsibilitiesForJob(databaseConnector.getConnection(), jobId);
        } catch (SQLException e) {
            throw new FailedToGetResponsibilitiesException();
        }
    }
}
