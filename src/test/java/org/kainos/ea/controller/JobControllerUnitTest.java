package org.kainos.ea.controller;

import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.api.JobService;
import org.kainos.ea.cli.Job;
import org.kainos.ea.cli.JobRequest;
import org.kainos.ea.exception.FailedToGetJobsException;
import org.kainos.ea.exception.FailedtoDeleteException;
import org.kainos.ea.exception.ProjectException;
import org.kainos.ea.resources.JobController;
import org.mockito.Mockito;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobControllerUnitTest {

    JobService jobService = Mockito.mock(JobService.class);
    JobRequest jobRequest = new JobRequest(
            "Test",
            1,
            "This is a test",
            "https://google.com",
            1);
    JobController jobController = new JobController(jobService);

    private final Job sampleJob1 = new Job(
            1,
            "Software Engineer",
            "Engineering",
            "Develops, tests, and maintains software applications, collaborates with cross-functional teams, and follows best practices to deliver efficient and reliable software solutions.",
            "https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Software%20Engineer%20%28Associate%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1",
            "Trainee"
    );

    @Test
    void getAllJobs_shouldReturn200StatusCodeAndListOfJobs_whenServiceReturnsListOfJobs() throws FailedToGetJobsException {

        // Create sample jobs and add them to a List
        Job sampleJob1 = new Job(1, "Software Engineer", "Engineering", "Develops, tests, and maintains software applications, collaborates with cross-functional teams, and follows best practices to deliver efficient and reliable software solutions.", "https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Software%20Engineer%20%28Associate%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1", "Trainee");
        Job sampleJob2 = new Job(2, "Test Engineer", "Quality Assurance", "Designs and executes test plans, identifies and reports software defects, and collaborates with development teams to ensure the delivery of reliable and high-quality software products.", "https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%20Test%20Engineer%20%28Associate%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering", "Trainee");
        Job sampleJob3 = new Job(3, "Senior Software Engineer", "Platforms", "Designs and develops complex software solutions, mentors junior engineers, and collaborates with cross-functional teams to deliver high-quality software products.", "https://kainossoftwareltd.sharepoint.com/people/Job%20Specifications/Forms/AllItems.aspx?id=%2Fpeople%2FJob%20Specifications%2FEngineering%2FJob%20profile%20%2D%20Senior%20Software%20Engieneer%20%28Senior%20Associate%29%2Epdf&parent=%2Fpeople%2FJob%20Specifications%2FEngineering&p=true&ga=1", "Senior Associate");

        List<Job> expectedResponseBody = Arrays.asList(sampleJob1, sampleJob2, sampleJob3);

        Mockito.when(jobService.getAllJobs()).thenReturn(expectedResponseBody);
        JobController jobController = new JobController(jobService);

        Response response = jobController.getAllJobs();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(expectedResponseBody, response.getEntity());

    }

    @Test
    void getAllJobs_shouldReturn500StatusCodeAndErrorMessage_whenServiceThrowsFailedToGetJobsException() throws FailedToGetJobsException {

        Mockito.when(jobService.getAllJobs()).thenThrow(FailedToGetJobsException.class);
        JobController jobController = new JobController(jobService);

        Response response = jobController.getAllJobs();

        assertEquals(response.getStatus(), 500);
        assertEquals(response.getEntity(), "A database error occurred, failed to get jobs");
    }


    @Test
    void createJobRole_shouldReturn200Response_whenJobRoleServiceDoesNotThrowException() throws ProjectException, SQLException {
        int expectedId = 1;
        int expectedStatusCode = 200;

        Mockito.when(jobService.createJobRole(jobRequest)).thenReturn(expectedId);

        Response response = jobController.createJobRole(jobRequest);

       assertEquals(response.getStatus(), expectedStatusCode);
        assertEquals(response.getEntity(), expectedId);
    }

    @Test
    void createJobRole_shouldReturn500Response_whenJobRoleServiceThrowsActionFailedException() throws ProjectException, SQLException {
        int expectedStatusCode = 500;

        Mockito.doThrow(SQLException.class).when(jobService).createJobRole(jobRequest);

        Response response = jobController.createJobRole(jobRequest);

        assertEquals(response.getStatus(), expectedStatusCode);
    }


    @Test
    void deleteJob_shouldReturn404_whenJobIdNotFound() throws FailedtoDeleteException, NotFoundException {
        Mockito.doThrow(NotFoundException.class).when(jobService).delete(-1);
        JobController jobController = new JobController(jobService);

        Response response = jobController.deleteJob("-1");

        Assertions.assertEquals(404,response.getStatus());
    }

    @Test
    void deleteJob_shouldReturn500_whenFailedToDeleteExceptionThrown() throws FailedtoDeleteException, NotFoundException {
        Mockito.doThrow(FailedtoDeleteException.class).when(jobService).delete(-1);
        JobController jobController = new JobController(jobService);

        Response response = jobController.deleteJob("-1");

        Assertions.assertEquals(500,response.getStatus());
    }

    @Test
    void deleteJob_shouldReturn400_whenBadIdPassed() {
        JobController jobController = new JobController(jobService);

        Response response = jobController.deleteJob("This is a bad id");
        Assertions.assertEquals(400,response.getStatus());
    }

    @Test
    void deleteJob_shouldReturn400_whenNoPassed() {
        JobController jobController = new JobController(jobService);

        Response response = jobController.deleteJob(null);
        Assertions.assertEquals(400,response.getStatus());
    }

    @Test
    void deleteJob_shouldReturn200_whenValidJobIdPassed() throws FailedtoDeleteException, NotFoundException {
        Mockito.doNothing().when(jobService).delete(1);
        JobController jobController = new JobController(jobService);
        Response response = jobController.deleteJob("1");

        Assertions.assertEquals(200,response.getStatus());
    }

    @Test
    void getJobById_shouldReturn200andJob_whenValidIdPassed() throws FailedToGetJobsException {
        Mockito.when(jobService.getById(1)).thenReturn(sampleJob1);
        JobController controller = new JobController(jobService);
        Response response = controller.getJob("1");
        Assertions.assertEquals(200,response.getStatus());
    }

    @Test
    void getJobById_shouldReturn404_whenNonExistantIdPassed() throws FailedToGetJobsException, NotFoundException {
        Mockito.when(jobService.getById(-1)).thenReturn(null);
        JobController controller = new JobController(jobService);
        Response response = controller.getJob("-1");
        Assertions.assertEquals(404,response.getStatus());
    }

    @Test
    void getJobById_shouldReturn400_whenInvalidId() {
        JobController controller = new JobController(jobService);
        Response response = controller.getJob("This is invalid");
        Assertions.assertEquals(400,response.getStatus());
    }

}
