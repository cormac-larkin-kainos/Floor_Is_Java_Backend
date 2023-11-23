package org.kainos.ea.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.api.CapabilityService;
import org.kainos.ea.api.JobBandService;
import org.kainos.ea.cli.Capability;
import org.kainos.ea.cli.JobBand;
import org.kainos.ea.exception.FailedToGetCapabilitiesException;
import org.kainos.ea.exception.FailedToGetJobBandsException;
import org.kainos.ea.resources.CapabilityController;
import org.kainos.ea.resources.JobBandController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class JobBandControllerUnitTest {

    JobBandService jobBandService = Mockito.mock(JobBandService.class);

    @Test
    void getAllJobBands_shouldReturn200StatusCodeAndListOfJobBands_whenServiceReturnsListOfJobBands() throws FailedToGetJobBandsException, SQLException {

        // Create sample jobs and add them to a List

        JobBand jobBand1 = new JobBand(1, "Trainee");
        JobBand jobBand2 = new JobBand(2, "Associate");
        JobBand jobBand3 = new JobBand(3, "Consultant");

        List<JobBand> expectedResponseBody = Arrays.asList(jobBand1, jobBand2, jobBand3);

        Mockito.when(jobBandService.getAllJobBands()).thenReturn(expectedResponseBody);
        JobBandController jobBandController = new JobBandController(jobBandService);

        Response response = jobBandController.getAllJobBands();

        Assertions.assertEquals(response.getStatus(),200);
        Assertions.assertEquals(response.getEntity(), expectedResponseBody);
    }

    @Test
    void getAllJobBands_shouldReturn500StatusCodeAndErrorMessage_whenServiceThrowsFailedToGetJobBandsException() throws FailedToGetJobBandsException, SQLException {

        Mockito.when(jobBandService.getAllJobBands()).thenThrow(FailedToGetJobBandsException.class);
        JobBandController jobBandController = new JobBandController(jobBandService);

        Response response = jobBandController.getAllJobBands();

        Assertions.assertEquals(response.getStatus(), 500);
        Assertions.assertEquals(response.getEntity(), "A database error occurred, failed to get job bands");
    }

    @Test
    void getAllJobBands_shouldReturn500StatusCodeAndErrorMessage_whenServiceThrowsFailedToGetJobBandsException2() throws FailedToGetJobBandsException, SQLException {

        Mockito.when(jobBandService.getAllJobBands()).thenThrow(SQLException.class);
        JobBandController jobBandController = new JobBandController(jobBandService);

        Response response = jobBandController.getAllJobBands();

        Assertions.assertEquals(response.getStatus(), 500);
    }


}
