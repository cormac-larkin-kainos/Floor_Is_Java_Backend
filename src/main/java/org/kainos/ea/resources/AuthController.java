package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.api.IAuthService;
import org.kainos.ea.cli.Authorised;
import org.kainos.ea.cli.UserRole;
import org.kainos.ea.exception.AuthenticationException;
import org.kainos.ea.cli.Login;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/api")
@Api(
    value = "Floor is Java Auth API"
)
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
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
