package org.kainos.ea.resources;

import io.swagger.annotations.*;
import javassist.NotFoundException;
import org.kainos.ea.api.JobService;
import org.kainos.ea.cli.JobRequest;
import org.kainos.ea.core.JobValidator;
import org.kainos.ea.cli.Authorised;
import org.kainos.ea.cli.Job;
import org.kainos.ea.cli.UserRole;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.FailedToGetJobsException;
import org.kainos.ea.exception.ProjectException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.kainos.ea.exception.FailedtoDeleteException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@SwaggerDefinition(
        securityDefinition = @SecurityDefinition(
                apiKeyAuthDefinitions = {
                        @ApiKeyAuthDefinition(
                                key = "basicAuth",
                                name = "Authorization",
                                in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER)
                }
        )
)
@Path("/api")
@Api(
    value = "Floor is Java Job API",
    authorizations = {@Authorization(value = "basicAuth")}
)
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
    @Authorised({UserRole.User,UserRole.Admin})
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
    @Authorised({UserRole.Admin})
    public Response createJobRole(JobRequest job) {
        try {
            int createdJobId = jobService.createJobRole(job);
            return Response.ok(createdJobId).build();
        } catch (ProjectException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Job creation failed").build();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Database error").build();
        }
    }

    @GET
    @Path("/job/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorised({UserRole.User,UserRole.Admin})
    public Response getJob(@PathParam("id") String id) {
        try {
            Job job = jobService.getById(Integer.parseInt(id));
            if(job == null){
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok().entity(job).build();
        } catch (FailedToGetJobsException e) {
            return Response.serverError().build();
        } catch (NumberFormatException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @DELETE
    @Path("/jobs/{id}")
    @Authorised({UserRole.Admin})
    public Response deleteJob(@PathParam("id") String id) {
        try {
            int convertedId = Integer.parseInt(id);
            jobService.delete(convertedId);
            return Response.ok().build();
        } catch (FailedtoDeleteException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (NumberFormatException e){
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
