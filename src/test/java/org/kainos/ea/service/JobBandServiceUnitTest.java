package org.kainos.ea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.JobBandService;
import org.kainos.ea.cli.JobBand;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobBandDao;
import org.kainos.ea.exception.FailedToGetJobBandsException;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JobBandServiceUnitTest {

    JobBandDao jobBandDao = Mockito.mock(JobBandDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);

    JobBandService jobBandService = new JobBandService(jobBandDao, databaseConnector);
    Connection connection;

    @Test
    void getAllJobBands_shouldReturnListOfJobBands_whenDaoReturnsListOfJobBands() throws SQLException, FailedToGetJobBandsException {

        JobBand jobBand1 = new JobBand(1, "Trainee");
        JobBand jobBand2 = new JobBand(2, "Associate");
        JobBand jobBand3 = new JobBand(3, "Consultant");

        List<JobBand> expectedResult = Arrays.asList(jobBand1, jobBand2, jobBand3);

        // Test that the same list is returned from both the CapabilityService and CapabilityDao
        Mockito.when(databaseConnector.getConnection()).thenReturn(connection);
        Mockito.when(jobBandDao.getAllJobBands(connection)).thenReturn(expectedResult);

        List<JobBand> result = jobBandService.getAllJobBands();

        assertEquals(result, expectedResult);

    }


}
