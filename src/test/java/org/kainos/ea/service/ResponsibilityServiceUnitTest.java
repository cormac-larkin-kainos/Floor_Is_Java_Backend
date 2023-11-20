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
    public void getResponsibilitiesForJob_shouldReturnListOfResponsibilities_whenDaoReturnsListOfResponsibilities() throws FailedToGetResponsibilitiesException, SQLException {

        //sample jobId
        int jobId = 1;
        //sample responsibilities that align with sample jobId
        Responsibility resSample1 = new Responsibility(1, "Developing high quality solutions which delight our customers and impact the lives of users worldwide");
        Responsibility resSample2 = new Responsibility(2, "Making sound, reasoned decisions");
        Responsibility resSample3 = new Responsibility(3, "Learning about new technologies and approaches");
        Responsibility resSample4 = new Responsibility(4, "Mentoring those around you");

        //add sample responsibilities to a list
        List<Responsibility> expectedResult = Arrays.asList(resSample1, resSample2, resSample3, resSample4);

        //get resDao result
        Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
        Mockito.when(responsibilityDao.doesJobExist(connection, jobId)).thenReturn(true);
        Mockito.when(responsibilityDao.getResponsibilitiesForJob(connection, jobId)).thenReturn(expectedResult);

        //get resService result
        List<Responsibility> result = responsibilityService.getResponsibilitiesForJob(jobId);

        //test that the same list is returned from ResDao and ResService
        assertEquals(expectedResult, result);
    }

    @Test
    public void getResponsibilitiesForJob_shouldThrowFailedToGetResponsibilitiesException_whenDaoThrowsSQLException() throws SQLException {
        // sample job id
        int jobId = 1;

        // simulation of job not found in the database
        Mockito.when(responsibilityDao.doesJobExist(connection, jobId)).thenReturn(false);

        assertThrows(FailedToGetResponsibilitiesException.class,
                () -> responsibilityService.getResponsibilitiesForJob(jobId));
    }


    @Test
    public void getResponsibilitiesForJob_shouldThrowFailedToGetResponsibilitiesException_whenDatabaseConnectorThrowsSQLException() throws SQLException {
        // sample job id
        int jobId = 1;

        Mockito.when(databaseConnector.getConnection()).thenThrow(SQLException.class);

        assertThrows(FailedToGetResponsibilitiesException.class,
                () -> responsibilityService.getResponsibilitiesForJob(jobId));
    }

}

