package org.kainos.ea.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.api.ResponsibilityService;
import org.kainos.ea.cli.Responsibility;
import org.kainos.ea.exception.FailedToGetResponsibilitiesException;
import org.kainos.ea.resources.ResponsibilityController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

public class ResponsibilityControllerUnitTest {

    ResponsibilityService responsibilityService = Mockito.mock(ResponsibilityService.class);

    @Test
    void getResponsibilitiesForJob_shouldReturn200StatusCodeAndListOfResponsibilities_whenServiceReturnsListOfJobResponsibilities() throws FailedToGetResponsibilitiesException {

        //sample jobId
        int jobId = 1;
        //sample responsibilities that align with sample jobId
        Responsibility resSample1 = new Responsibility(1, "Developing high quality solutions which delight our customers and impact the lives of users worldwide");
        Responsibility resSample2 = new Responsibility(2, "Making sound, reasoned decisions");
        Responsibility resSample3 = new Responsibility(3, "Learning about new technologies and approaches");
        Responsibility resSample4 = new Responsibility(4, "Mentoring those around you");

        //add sample responsibilities to a list
        List<Responsibility> expectedResponseBody = Arrays.asList(resSample1, resSample2, resSample3, resSample4);

        Mockito.when(responsibilityService.getResponsibilitiesForJob(jobId)).thenReturn(expectedResponseBody);
        ResponsibilityController responsibilityController = new ResponsibilityController(responsibilityService);

        Response response = responsibilityController.getResponsibilitiesForJob(jobId);

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertEquals(response.getEntity(), expectedResponseBody);
    }

    @Test
    void getResponsibilitiesForJob_shouldReturn500StatusCodeAndErrorMessage_whenServiceThrowsFailedToGetResponsibilitiesException() throws FailedToGetResponsibilitiesException {

        //sample jobId
        int jobId = 1;

        Mockito.when(responsibilityService.getResponsibilitiesForJob(jobId)).thenThrow(FailedToGetResponsibilitiesException.class);
        ResponsibilityController responsibilityController = new ResponsibilityController(responsibilityService);

        Response response = responsibilityController.getResponsibilitiesForJob(jobId);

        Assertions.assertEquals(response.getStatus(), 500);
        Assertions.assertEquals(response.getEntity(), "A database error occurred, failed to get responsibilities");

    }
}
