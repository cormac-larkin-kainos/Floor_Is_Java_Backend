package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.JobService;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobDao;
import org.kainos.ea.exception.FailedToGetJobsException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Floor is Java Job API")
@Path("/api")
public class JobController {

    private final JobService jobService;

    public JobController() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        jobService = new JobService(new JobDao(), databaseConnector);
    }

    public JobController(JobService jobService) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        this.jobService = jobService;
    }

    @GET
    @Path("/jobs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllJobs() {

        try {
            return Response.ok(jobService.getAllJobs()).build();
        } catch (FailedToGetJobsException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }

    }

}
