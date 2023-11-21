package org.kainos.ea.resources;

import io.dropwizard.auth.Auth;
import io.swagger.annotations.Api;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.JobService;
import org.kainos.ea.cli.AuthenticationException;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.cli.Login;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobDao;
import org.kainos.ea.exception.FailedToGenerateTokenException;
import org.kainos.ea.exception.FailedToLoginException;
import org.kainos.ea.exception.InvalidCredentialsException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("floorIsJava Authentication API")
@Path("/api")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)

    public Response login(Login login) {

        try {
            String token = authService.login(login);
            if (token == null){
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            return Response.ok().entity(token).build();
        } catch (AuthenticationException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Could not login user").build();
        }
    }

}
