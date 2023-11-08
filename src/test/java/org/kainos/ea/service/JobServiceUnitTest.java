package org.kainos.ea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.JobService;
import org.kainos.ea.cli.Job;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobDao;
import org.kainos.ea.exception.FailedToGetJobsException;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class JobServiceUnitTest {

    JobDao jobDao = Mockito.mock(JobDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);

    JobService jobService = new JobService(jobDao, databaseConnector);
    Connection connection;

    @Test
    void getAllJobs_shouldReturnListOfJobs_whenDaoReturnsListOfJobs() throws SQLException, FailedToGetJobsException {

        // Create sample jobs and add them to a List
        Job sampleJob1 = new Job(1, "Software Engineer");
        Job sampleJob2 = new Job(2, "Platforms Engineer");
        Job sampleJob3 = new Job(3, "Accountant");

        List<Job> expectedResult = Arrays.asList(sampleJob1, sampleJob2, sampleJob3);

        // Test that the same list is returned from both the JobService and JobDao
        Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
        Mockito.when(jobDao.getAllJobs(connection)).thenReturn(expectedResult);

        List<Job> result = jobService.getAllJobs();

        assertEquals(result, expectedResult);

    }

    @Test
    void getAllJobs_shouldThrowFailedToGetJobsException_whenDaoThrowsSQLException() throws SQLException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
        Mockito.when(jobDao.getAllJobs(connection)).thenThrow(SQLException.class);

        assertThrows(FailedToGetJobsException.class,
                () -> jobService.getAllJobs());
    }

    @Test
    void getAllJobs_shouldThrowFailedToGetJobsException_whenDatabaseConnectorThrowsSQLException() throws SQLException {
        Mockito.when(databaseConnector.getConnection()).thenThrow(SQLException.class);

        assertThrows(FailedToGetJobsException.class,
                () -> jobService.getAllJobs());
    }

}
