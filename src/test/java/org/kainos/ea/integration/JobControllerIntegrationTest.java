package org.kainos.ea.integration;

import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.FloorIsJavaApplication;
import org.kainos.ea.FloorIsJavaConfiguration;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.JobService;
import org.kainos.ea.cli.Job;
import org.kainos.ea.cli.JobRequest;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.ProjectException;
import org.kainos.ea.resources.JobController;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class JobControllerIntegrationTest {

    static final DropwizardAppExtension<FloorIsJavaConfiguration> APP = new DropwizardAppExtension<>(
            FloorIsJavaApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    @Test
    void getAllJobs_ShouldReturnListOfJobs() {
        Response response = APP.client().target("http://localhost:8080/api/jobs")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());

        // Check that the all jobs in the response contain the correct properties
        List<Job> allJobs = response.readEntity(List.class);

        Assertions.assertFalse(allJobs.isEmpty());

    }

    @Test
    void createJob_ShouldReturnCreatedStatus() {
        // Create a new job object or use a predefined job object
        JobRequest newJob = new JobRequest("newJob",1,"A new job","job@newjob.com",2);

        // Send a POST request to create a job
        Response response = APP.client().target("http://localhost:8080/api/jobs")
                .request()
                .post(Entity.json(newJob)); // Send the job as JSON in the request body

        assertEquals(200, response.getStatus());
    }

    @Test
    void createJob_WithInvalidData_ShouldReturnBadRequest() {
        // Create a new job object with invalid data or missing required fields
        JobRequest invalidJob = new JobRequest("", 0, "A new job", "job@newjob.com", 2);

        // Send a POST request to create a job with invalid data
        Response response = APP.client().target("http://localhost:8080/api/jobs")
                .request()
                .post(Entity.json(invalidJob)); // Send the invalid job as JSON in the request body

        // Assert that the HTTP response status code is 400 (Bad Request) or appropriate error code
        assertEquals(400, response.getStatus());
    }
}
