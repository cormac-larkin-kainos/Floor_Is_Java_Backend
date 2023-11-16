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
    void getAllResponsibilities_shouldReturn200StatusCodeAndListOfResponsibilities_whenServiceReturnsListOfResponsibilities() throws FailedToGetResponsibilitiesException {

        Responsibility sampleResponsibility1 = new Responsibility(1, "Interacting with customers");
        Responsibility sampleResponsibility2 = new Responsibility(2, "Leading teams");
        Responsibility sampleResponsibility3 = new Responsibility(3, "Mentoring those around you");

        List<Responsibility> expectedResponseBody = Arrays.asList(
                sampleResponsibility1, sampleResponsibility2, sampleResponsibility3);

        Mockito.when(responsibilityService.getAllResponsibilities()).thenReturn(expectedResponseBody);
        ResponsibilityController responsibilityController = new ResponsibilityController(responsibilityService);

        Response response = responsibilityController.getAllResponsibilities();

        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertEquals(response.getEntity(), expectedResponseBody);
    }

    @Test
    void getAllResponsibilities_shouldReturn500StatusCodeAndErrorMessage_whenServiceThrowsFailedToGetResponsibilitiesException() throws FailedToGetResponsibilitiesException {

        Mockito.when(responsibilityService.getAllResponsibilities()).thenThrow(FailedToGetResponsibilitiesException.class);
        ResponsibilityController responsibilityController = new ResponsibilityController(responsibilityService);

        Response response = responsibilityController.getAllResponsibilities();

        Assertions.assertEquals(response.getStatus(), 500);
        Assertions.assertEquals(response.getEntity(), "A database error occurred, failed to get responsibilities");

    }

}


