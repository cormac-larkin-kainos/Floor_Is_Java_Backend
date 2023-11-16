package org.kainos.ea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.ResponsibilityService;
import org.kainos.ea.cli.Responsibility;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.ResponsibilityDao;
import org.kainos.ea.exception.FailedToGetResponsibilitiesException;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ResponsibilityServiceUnitTest {

    ResponsibilityDao responsibilityDao = Mockito.mock(ResponsibilityDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);

    ResponsibilityService responsibilityService = new ResponsibilityService(responsibilityDao, databaseConnector);
    Connection connection;

    @Test
    void getAllResponsibilities_shouldReturnListOfResponsibilities_whenDaoReturnsListOfResponsibilities()
            throws SQLException, FailedToGetResponsibilitiesException {

        Responsibility sampleResponsibility1 = new Responsibility(1, "Interacting with customers");
        Responsibility sampleResponsibility2 = new Responsibility(2, "Leading teams");
        Responsibility sampleResponsibility3 = new Responsibility(3, "Mentoring those around you");

        List<Responsibility> expectedResult = Arrays.asList(
                sampleResponsibility1, sampleResponsibility2, sampleResponsibility3);

        Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
        Mockito.when(responsibilityDao.getAllResponsibilities(connection)).thenReturn(expectedResult);

        List<Responsibility> result = responsibilityService.getAllResponsibilities();

        assertEquals(result, expectedResult);
    }

    @Test
    void getAllResponsibilities_shouldThrowFailedToGetResponsibilitiesException_whenDaoThrowsSQLException()
            throws SQLException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
        Mockito.when(responsibilityDao.getAllResponsibilities(connection)).thenThrow(SQLException.class);

        assertThrows(FailedToGetResponsibilitiesException.class,
                () -> responsibilityService.getAllResponsibilities());
    }

    @Test
    void getAllResponsibilities_shouldThrowFailedToGetResponsibilitiesException_whenDatabaseConnectorThrowsSQLException()
            throws SQLException {
        Mockito.when(databaseConnector.getConnection()).thenThrow(SQLException.class);

        assertThrows(FailedToGetResponsibilitiesException.class,
                () -> responsibilityService.getAllResponsibilities());
    }

}

