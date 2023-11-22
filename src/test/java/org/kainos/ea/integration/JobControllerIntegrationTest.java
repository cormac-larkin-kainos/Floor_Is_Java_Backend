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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(DropwizardExtensionsSupport.class)
public class JobControllerIntegrationTest {

    static final DropwizardAppExtension<FloorIsJavaConfiguration> APP = new DropwizardAppExtension<>(
            FloorIsJavaApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );

    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    JobService jobService = Mockito.mock(JobService.class);
    JobController jobController = new JobController(jobService);

    @Test
    void getAllJobs_ShouldReturnListOfJobs() {
        Response response = APP.client().target("http://localhost:8080/api/jobs")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        assertEquals(200, response.getStatus());
        Assertions.assertNotNull(response.getEntity());

        // Check that the all jobs in the response contain the correct properties
        List<Job> allJobs = response.readEntity(List.class);

        Assertions.assertFalse(allJobs.isEmpty());

    }

    @Test
    void createJobRole_ShouldCreateJobSuccessfully() throws SQLException, ProjectException {
        // Assuming you have a JobRequest instance set up
        JobRequest jobRequest = new JobRequest("Sample Job", 1, "Job spec", "https://example.com", 2);

        // Mock the behavior of the JobService to return a job ID
        int expectedJobId = 1;
        Mockito.when(jobService.createJobRole(jobRequest)).thenReturn(expectedJobId);

        // Invoke the controller method
        Response response = jobController.createJobRole(jobRequest);

        // Assert that the response status is 200
        assertEquals(200, response.getStatus());

        // Check the response entity for the expected job ID
        int jobIdFromResponse = (int) response.getEntity();
        assertTrue(jobIdFromResponse > 0);
    }

    @Test
    void createJobRole_ShouldFailWithInvalidData() throws SQLException, ProjectException {

        JobRequest jobRequest = new JobRequest("", 0, "", "", -1);
        int expectedStatusCode = 400;

        Mockito.doThrow(ProjectException.class).when(jobService).createJobRole(jobRequest);

        Response response = jobController.createJobRole(jobRequest);

        assertEquals(response.getStatus(), expectedStatusCode);
    }

}
