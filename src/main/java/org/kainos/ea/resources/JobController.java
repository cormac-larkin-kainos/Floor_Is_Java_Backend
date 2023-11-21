package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.JobService;
import org.kainos.ea.cli.JobRequest;
import org.kainos.ea.core.JobValidator;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobDao;
import org.kainos.ea.exception.FailedToGetJobsException;
import org.kainos.ea.exception.ProjectException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Floor is Java Job API")
@Path("/api")
public class JobController {
    /**
     * instantiate JobService class.
     */
    private final JobService jobService;
    /**
     * instantiate DatabaseConnector class.
     */
    private final DatabaseConnector databaseConnector;

    private final JobValidator jobValidator = new JobValidator();


    public JobController(JobService jobService) {
        databaseConnector = new DatabaseConnector();
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

    @POST
    @Path("/jobs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createJobRole(JobRequest job){
        try{
            return Response.ok(jobService.createJobRole(job)).build();
        } catch (ProjectException e){
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
