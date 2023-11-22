package org.kainos.ea.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.FloorIsJavaApplication;
import org.kainos.ea.FloorIsJavaConfiguration;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.cli.Login;
import javax.ws.rs.client.Entity;
import org.kainos.ea.cli.Job;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class JobControllerIntegrationTest {

    static final DropwizardAppExtension<FloorIsJavaConfiguration> APP = new DropwizardAppExtension<>(
            FloorIsJavaApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    private static final String VALID_USERNAME = System.getenv("TEST_USERNAME");
    private static final String VALID_PASSWORD = System.getenv("TEST_PASSWORD");

    private String getJWT() {
        if(VALID_USERNAME == null || VALID_PASSWORD == null){
            throw new IllegalArgumentException("Test credential environment variables not set!");
        }
        Login credentials = new Login(VALID_USERNAME,VALID_PASSWORD);
        Response response = APP.client().target("http://localhost:8080/api/login").request().post(Entity.json(credentials));

        return response.readEntity(String.class);
    }

    @Test
    void getAllJobs_ShouldReturnListOfJobs() {
        Response response = APP.client().target("http://localhost:8080/api/jobs")
                .request()
                .header("Authorization","Bearer " + getJWT())
                .accept(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertNotNull(response.getEntity());

        // Check that the all jobs in the response contain the correct properties
        List<Job> allJobs = response.readEntity(new GenericType<List<Job>>() {});

        for (Job job : allJobs) {
            int jobID = job.getJobID();
            Assertions.assertTrue(jobID > 0);

            Assertions.assertNotNull(job.getTitle());
            Assertions.assertNotNull(job.getJobURL());
            Assertions.assertNotNull(job.getJobSpec());
            Assertions.assertNotNull(job.getCapability());
            Assertions.assertNotNull(job.getJobBand());
        }

    }

}
