package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.JobService;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.exception.FailedToGetJobsException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

@Api("test endpoint Floor Is Java API")
@Path("/api")
public class TestController {

    private final DatabaseConnector databaseConnector = new DatabaseConnector();

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testConnection() {

        try {
            Connection conn = databaseConnector.getConnection();
            if(conn != null) {
                return Response.ok().entity("Database connection successful").build();
            }
        } catch (SQLException e) {
            return Response.serverError().entity("Database connection failed").build();
        }

        return Response.serverError().entity("Database connection failed").build();
    }

}
