package org.kainos.ea.resources;

import org.kainos.ea.db.DatabaseConnector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

@Path("/api")
public class TestController {

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testConnection() {

        try {
            Connection conn = DatabaseConnector.getConnection();
            if(conn != null) {
                return Response.ok().entity("Database connection successful").build();
            }
        } catch (SQLException e) {
            return Response.serverError().entity("Database connection failed").build();
        }

        return Response.serverError().entity("Database connection failed").build();
    }

}
