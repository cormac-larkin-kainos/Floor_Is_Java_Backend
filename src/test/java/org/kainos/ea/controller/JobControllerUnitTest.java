package org.kainos.ea.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.api.JobService;
import org.kainos.ea.cli.Job;
import org.kainos.ea.exception.FailedToGetJobsException;
import org.kainos.ea.resources.JobController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

public class JobControllerUnitTest {

    JobService jobService = Mockito.mock(JobService.class);

    @Test
    void getAllJobs_shouldReturn200StatusCodeAndListOfJobs_whenServiceReturnsListOfJobs() throws FailedToGetJobsException {

        // Create sample jobs and add them to a List
        Job sampleJob1 = new Job(1, "Software Engineer");
        Job sampleJob2 = new Job(2, "Platforms Engineer");
        Job sampleJob3 = new Job(3, "Accountant");

        List<Job> expectedResponseBody = Arrays.asList(sampleJob1, sampleJob2, sampleJob3);

        Mockito.when(jobService.getAllJobs()).thenReturn(expectedResponseBody);
        JobController jobController = new JobController(jobService);

        Response response = jobController.getAllJobs();

        Assertions.assertEquals(response.getStatus(),200);
        Assertions.assertEquals(response.getEntity(), expectedResponseBody);
    }

    @Test
    void getAllJobs_shouldReturn500StatusCodeAndErrorMessage_whenServiceThrowsFailedToGetJobsException() throws FailedToGetJobsException {

        Mockito.when(jobService.getAllJobs()).thenThrow(FailedToGetJobsException.class);
        JobController jobController = new JobController(jobService);

        Response response = jobController.getAllJobs();

        Assertions.assertEquals(response.getStatus(), 500);
        Assertions.assertEquals(response.getEntity(), "A database error occurred, failed to get jobs");




    }

}
