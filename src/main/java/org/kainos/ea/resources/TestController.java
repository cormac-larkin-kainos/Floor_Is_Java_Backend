package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.db.DatabaseConnector;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * TestController is responsible for handling test endpoints
 * related to database connections.
 */
@Api("test endpoint Floor Is Java API")
@Path("/api")
public final class TestController {
    /**
     * Handle the HTTP GET request to "/api/test".
     */
    private final DatabaseConnector databaseConnector = new DatabaseConnector();

    /**
     * GET request.
     * @return server response.
     */
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testConnection() {

        try {
            Connection conn = databaseConnector.getConnection();
            if (conn != null) {
                return Response.ok()
                        .entity("Database connection successful")
                        .build();
            }
        } catch (SQLException e) {
            return Response.serverError()
                    .entity("Database connection failed")
                    .build();
        }

        return Response.serverError()
                .entity("Database connection failed")
                .build();
    }

}
