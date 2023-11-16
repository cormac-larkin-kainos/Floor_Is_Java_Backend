package org.kainos.ea.api;

import org.kainos.ea.cli.Responsibility;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.ResponsibilityDao;
import org.kainos.ea.exception.FailedToGetResponsibilitiesException;

import java.sql.SQLException;
import java.util.List;

public class ResponsibilityService {

    private final ResponsibilityDao responsibilityDao;
    private final DatabaseConnector databaseConnector;

    public ResponsibilityService(ResponsibilityDao responsibilityDao, DatabaseConnector databaseConnector) {
        this.responsibilityDao = responsibilityDao;
        this.databaseConnector = databaseConnector;
    }

    public List<Responsibility> getAllResponsibilities() throws FailedToGetResponsibilitiesException {
        try {
            return responsibilityDao.getAllResponsibilities(databaseConnector.getConnection());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetResponsibilitiesException();
        }
    }

    public List<Responsibility> getResponsibilitiesForJob(int jobId) throws FailedToGetResponsibilitiesException {
        try {
            return responsibilityDao.getResponsibilitiesForJob(databaseConnector.getConnection(), jobId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetResponsibilitiesException();
        }
    }
}
