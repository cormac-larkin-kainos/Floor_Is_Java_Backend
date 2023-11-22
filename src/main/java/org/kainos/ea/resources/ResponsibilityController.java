package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import javassist.NotFoundException;
import org.kainos.ea.api.ResponsibilityService;
import org.kainos.ea.cli.Authorised;
import org.kainos.ea.cli.UserRole;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.ResponsibilityDao;
import org.kainos.ea.cli.Responsibility;
import org.kainos.ea.exception.FailedToGetResponsibilitiesException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Floor is Java Responsibility API")
@Path("/api")
public class ResponsibilityController {

    private final ResponsibilityService responsibilityService;
    private ResponsibilityDao responsibilityDao;

    public ResponsibilityController() {
        responsibilityService = new ResponsibilityService(new ResponsibilityDao(), new DatabaseConnector());
    }

    public ResponsibilityController(ResponsibilityService responsibilityService) {
        this.responsibilityService = responsibilityService;
    }

    @GET
    @Path("/jobs/{jobId}/responsibilities")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorised({UserRole.User, UserRole.Admin})
    public Response getResponsibilitiesForJob(@PathParam("jobId") int jobId) {
        try {
                //find responsibilities
            List<Responsibility> responsibilities = responsibilityService.getResponsibilitiesForJob(jobId);
                //return 404 if no responsibilities found
            if (responsibilities.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Job with ID " + jobId + " not found").build();
            }
                //otherwise return status 200 okay
            return Response.ok(responsibilities).build();
        } catch (FailedToGetResponsibilitiesException e) {
                //return status 500 if any internal server errors
            return Response.serverError().entity(e.getMessage()).build();
        } catch(NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}



