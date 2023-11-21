package org.kainos.ea.resources;

import io.swagger.annotations.*;
import org.kainos.ea.api.JobService;
import org.kainos.ea.cli.Authorised;
import org.kainos.ea.cli.UserRole;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobDao;
import org.kainos.ea.exception.FailedToGetJobsException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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


    public JobController() {
        databaseConnector = new DatabaseConnector();
        jobService = new JobService(new JobDao(), databaseConnector);
    }

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
}
