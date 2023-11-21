package org.kainos.ea.controller;

import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.api.ResponsibilityService;
import org.kainos.ea.cli.Responsibility;
import org.kainos.ea.exception.FailedToGetResponsibilitiesException;
import org.kainos.ea.resources.ResponsibilityController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResponsibilityControllerUnitTest {

    ResponsibilityService responsibilityService = Mockito.mock(ResponsibilityService.class);
    ResponsibilityController responsibilityController = new ResponsibilityController(responsibilityService);

    @Test
    void getResponsibilitiesForJob_shouldReturn200StatusCodeAndListOfResponsibilities_whenServiceReturnsListOfJobResponsibilities() throws FailedToGetResponsibilitiesException, SQLException, NotFoundException {

        //sample jobId
        int jobId = 1;
        //sample responsibilities that align with sample jobId
        Responsibility resSample1 = new Responsibility(1, "Developing high quality solutions which delight our customers and impact the lives of users worldwide");
        Responsibility resSample2 = new Responsibility(2, "Making sound, reasoned decisions");
        Responsibility resSample3 = new Responsibility(3, "Learning about new technologies and approaches");
        Responsibility resSample4 = new Responsibility(4, "Mentoring those around you");

        //add sample responsibilities to a list
        List<Responsibility> expectedResponseBody = Arrays.asList(resSample1, resSample2, resSample3, resSample4);

            Mockito.when(responsibilityService.doesJobExist(jobId)).thenReturn(true);

            Mockito.when(responsibilityService.getResponsibilitiesForJob(jobId)).thenReturn(expectedResponseBody);

            Response response = responsibilityController.getResponsibilitiesForJob(jobId);

            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertEquals(expectedResponseBody, response.getEntity());

    }

    @Test
    void getResponsibilitiesForJob_shouldReturn500StatusCodeAndErrorMessage_whenServiceThrowsFailedToGetResponsibilitiesException() throws FailedToGetResponsibilitiesException, SQLException, NotFoundException {

        //sample jobId
        int jobId = 1;

        Mockito.when(responsibilityService.doesJobExist(jobId)).thenReturn(true);

        Mockito.when(responsibilityService.getResponsibilitiesForJob(jobId)).thenThrow(FailedToGetResponsibilitiesException.class);

        Response response = responsibilityController.getResponsibilitiesForJob(jobId);

        Assertions.assertEquals(500, response.getStatus());
        Assertions.assertEquals("A database error occurred, failed to get responsibilities", response.getEntity());

    }

    @Test
    void getResponsibilitiesForJob_shouldReturn404Status_whenJobDoesNotExist() throws SQLException {
        //sample jobId
        int jobId = 1;

        Mockito.when(responsibilityService.doesJobExist(jobId)).thenReturn(false);

        Response response = responsibilityController.getResponsibilitiesForJob(jobId);

        Assertions.assertEquals(404, response.getStatus());
        Assertions.assertEquals("Job with ID " + jobId + " not found", response.getEntity());
    }

    @Test
    void getResponsibilitiesForJob_shouldReturn404Status_whenResponsibilitiesAreEmpty() throws SQLException, FailedToGetResponsibilitiesException, NotFoundException {
        //sample jobId
        int jobId = 1;

        Mockito.when(responsibilityService.doesJobExist(jobId)).thenReturn(true);
        Mockito.when(responsibilityService.getResponsibilitiesForJob(jobId)).thenReturn(new ArrayList<>());

        Response response = responsibilityController.getResponsibilitiesForJob(jobId);

        Assertions.assertEquals(404, response.getStatus());
        Assertions.assertEquals("Job with ID " + jobId + " not found", response.getEntity());
    }
}
