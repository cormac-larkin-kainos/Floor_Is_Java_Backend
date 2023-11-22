package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.CapabilityService;
import org.kainos.ea.cli.Authorised;
import org.kainos.ea.cli.UserRole;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.FailedToGetCapabilitiesException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Floor is Java Capability API")
@Path("/api")
public class CapabilityController {

    /**
     * instantiate CapabilityService class.
     */
    private final CapabilityService capabilityService;
    /**
     * instantiate DatabaseConnector class.
     */
    private final DatabaseConnector databaseConnector;

    public CapabilityController() {
        databaseConnector = new DatabaseConnector();
        capabilityService = new CapabilityService(new CapabilityDao(), databaseConnector);
    }

    public CapabilityController(CapabilityService capabilityService) {
        databaseConnector = new DatabaseConnector();
        this.capabilityService = capabilityService;
    }

    @GET
    @Path("/capabilities")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorised({UserRole.Admin, UserRole.User})
    public Response getAllCapabilities() {

        try {
            return Response.ok(capabilityService.getAllCapabilities()).build();
        } catch (FailedToGetCapabilitiesException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }

    }

}
