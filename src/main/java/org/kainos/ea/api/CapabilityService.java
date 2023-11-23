package org.kainos.ea.api;

import org.kainos.ea.cli.Capability;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.FailedToGetCapabilitiesException;

import java.sql.SQLException;
import java.util.List;

public class CapabilityService {

    private final CapabilityDao capabilityDao;
    private final DatabaseConnector databaseConnector;

    public CapabilityService(CapabilityDao capabilityDao, DatabaseConnector databaseConnector) {
        this.capabilityDao = capabilityDao;
        this.databaseConnector = databaseConnector;
    }

    public List<Capability> getAllCapabilities() throws FailedToGetCapabilitiesException {

        try {
            return capabilityDao.getAllCapabilities(databaseConnector.getConnection());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetCapabilitiesException();
        }

    }

}
