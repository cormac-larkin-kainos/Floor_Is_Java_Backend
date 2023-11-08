package org.kainos.ea.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.cli.Job;
import org.kainos.ea.floorIsJavaApplication;
import org.kainos.ea.floorIsJavaConfiguration;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class JobControllerIntegrationTest {

    static final DropwizardAppExtension<floorIsJavaConfiguration> APP = new DropwizardAppExtension<>(
            floorIsJavaApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getAllJobs_ShouldReturnListOfJobs() {
        Response response = APP.client().target("http://localhost:8080/api/jobs")
                .request()
                .get();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertNotNull(response.readEntity(List.class));
    }

}
