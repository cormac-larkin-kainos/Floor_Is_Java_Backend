package org.kainos.ea.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.api.CapabilityService;
import org.kainos.ea.cli.Capability;
import org.kainos.ea.exception.FailedToGetCapabilitiesException;
import org.kainos.ea.resources.CapabilityController;
import org.mockito.Mockito;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

public class CapabilityControllerUnitTest {


    CapabilityService capabilityService = Mockito.mock(CapabilityService.class);

    @Test
    void getAllJobs_shouldReturn200StatusCodeAndListOfCapabilities_whenServiceReturnsListOfCapabilities() throws FailedToGetCapabilitiesException {

        // Create sample jobs and add them to a List

        Capability sampleCapability1 = new Capability(1, "Engineering");
        Capability sampleCapability2 = new Capability(2, "Platforms");
        Capability sampleCapability3 = new Capability(3, "Data & AI");



        List<Capability> expectedResponseBody = Arrays.asList(sampleCapability1, sampleCapability2, sampleCapability3);

        Mockito.when(capabilityService.getAllCapabilities()).thenReturn(expectedResponseBody);
        CapabilityController capabilityController = new CapabilityController(capabilityService);

        Response response = capabilityController.getAllCapabilities();

        Assertions.assertEquals(response.getStatus(),200);
        Assertions.assertEquals(response.getEntity(), expectedResponseBody);
    }

    @Test
    void getAllCapabilities_shouldReturn500StatusCodeAndErrorMessage_whenServiceThrowsFailedToGetCapabilitiesException() throws FailedToGetCapabilitiesException {

        Mockito.when(capabilityService.getAllCapabilities()).thenThrow(FailedToGetCapabilitiesException.class);
        CapabilityController capabilityController = new CapabilityController(capabilityService);

        Response response = capabilityController.getAllCapabilities();

        Assertions.assertEquals(response.getStatus(), 500);
        Assertions.assertEquals(response.getEntity(), "A database error occurred, failed to get capabilities");
    }



}
