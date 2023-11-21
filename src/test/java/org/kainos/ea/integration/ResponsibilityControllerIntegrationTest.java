package org.kainos.ea.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.FloorIsJavaApplication;
import org.kainos.ea.FloorIsJavaConfiguration;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.cli.Responsibility;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ResponsibilityControllerIntegrationTest {

    static final DropwizardAppExtension<FloorIsJavaConfiguration> APP = new DropwizardAppExtension<>(
            FloorIsJavaApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getResponsibilitiesForJob_ShouldReturnListOfResponsibilitiesForJob() {
        int jobId = 1;

        Response response = APP.client().target("http://localhost:8080/api/jobs/" + jobId + "/responsibilities")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        // Check HTTP status code
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<Responsibility> responsibilities = response.readEntity(List.class);

        Assertions.assertFalse(responsibilities.isEmpty());
    }


    @Test
    void getResponsibilitiesForJob_ShouldReturn404ForNonExistingJob() {
        int nonExistingJobId = -1; // Assuming -1 is not a valid job ID

        Response response = APP.client().target("http://localhost:8080/api/jobs/" + nonExistingJobId + "/responsibilities")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        // Check HTTP status code
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}





