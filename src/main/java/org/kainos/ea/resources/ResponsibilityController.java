package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.ResponsibilityService;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.ResponsibilityDao;
import org.kainos.ea.cli.Responsibility;
import org.kainos.ea.exception.FailedToGetResponsibilitiesException;
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
    private final DatabaseConnector databaseConnector;

    public ResponsibilityController() {
        databaseConnector = new DatabaseConnector();
        responsibilityService = new ResponsibilityService(new ResponsibilityDao(), databaseConnector);
    }

    public ResponsibilityController(ResponsibilityService responsibilityService) {
        databaseConnector = new DatabaseConnector();
        this.responsibilityService = responsibilityService;
    }

    @GET
    @Path("/responsibilities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllResponsibilities() {
        try {
            List<Responsibility> responsibilities = responsibilityService.getAllResponsibilities();
            return Response.ok(responsibilities).build();
        } catch (FailedToGetResponsibilitiesException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/jobs/{jobId}/responsibilities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResponsibilitiesForJob(@PathParam("jobId") int jobId) {
        try {
            List<Responsibility> responsibilities = responsibilityService.getResponsibilitiesForJob(jobId);
            return Response.ok(responsibilities).build();
        } catch (FailedToGetResponsibilitiesException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}



