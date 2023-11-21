package org.kainos.ea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.CapabilityService;
import org.kainos.ea.cli.Capability;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.FailedToGetCapabilitiesException;
import org.kainos.ea.exception.FailedToGetJobsException;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CapabilityServiceUnitTest {


    CapabilityDao capabilityDao = Mockito.mock(CapabilityDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);

    CapabilityService capabilityService = new CapabilityService(capabilityDao, databaseConnector);
    Connection connection;

    @Test
    void getAllCapabilities_shouldReturnListOfCapabilities_whenDaoReturnsListOfCapabilities() throws SQLException, FailedToGetCapabilitiesException {

        // Create sample capabiltiies and add them to a List
        Capability sampleCapability1 = new Capability(1, "Engineering");
        Capability sampleCapability2 = new Capability(2, "Platforms");
        Capability sampleCapability3 = new Capability(3, "Data & AI");

        List<Capability> expectedResult = Arrays.asList(sampleCapability1, sampleCapability2, sampleCapability3);

        // Test that the same list is returned from both the CapabilityService and CapabilityDao
        Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
        Mockito.when(capabilityDao.getAllCapabilities(connection)).thenReturn(expectedResult);

        List<Capability> result = capabilityService.getAllCapabilities();

        assertEquals(result, expectedResult);

    }

    @Test
    void getAllCapabilities_shouldThrowFailedToGetCapabilitiesException_whenDaoThrowsSQLException() throws SQLException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
        Mockito.when(capabilityDao.getAllCapabilities(connection)).thenThrow(SQLException.class);

        assertThrows(FailedToGetCapabilitiesException.class,
                () -> capabilityService.getAllCapabilities());
    }

    @Test
    void getAllCapabilities_shouldThrowFailedToGetCapabilitiesException_whenDatabaseConnectorThrowsSQLException() throws SQLException {
        Mockito.when(databaseConnector.getConnection()).thenThrow(SQLException.class);

        assertThrows(FailedToGetCapabilitiesException.class,
                () -> capabilityService.getAllCapabilities());
    }

}
