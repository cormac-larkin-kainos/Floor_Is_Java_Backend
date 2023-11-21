package org.kainos.ea.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import org.kainos.ea.api.IAuthService;
import org.kainos.ea.cli.Authorised;
import org.kainos.ea.cli.UserRole;
import org.kainos.ea.exception.AuthenticationException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;

/**
 * A filter to allow for checking authorised roles on endpoints.
 */
@Authorised
public class AuthorisationFilter implements ContainerRequestFilter {

    /**
     * The auth service to use for tokens.
     */
    private final IAuthService authService;

    /**
     * Creates a new authorisation filter.
     * @param authService the auth service to use for tokens
     */
    public AuthorisationFilter(final IAuthService authService) {
        this.authService = authService;
    }

    /**
     * Used to get the role annotation for the endpoint.
     */
    private @Inject ResourceInfo resourceInfo;


    private String getTokenForHeader(String header){
        String[] split = header.split(" ");

        if(split.length < 2) {
            return null;
        }

        return split[1];
    }

    /**
     * Filters an authorised endpoints access by role.
     * @param containerRequestContext the request context
     */
    @Override
    public void filter(final ContainerRequestContext containerRequestContext) {
        Method method = resourceInfo.getResourceMethod();
        Authorised annotation = method.getAnnotation(Authorised.class);

        if (annotation == null) {
            return;
        }

        UserRole[] endpointRoles = annotation.value();

        String authorisationHeader = containerRequestContext.getHeaderString("Authorization");
        if (authorisationHeader == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
            return;
        }

        String encodedJWT = getTokenForHeader(authorisationHeader);
        if (encodedJWT == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
            return;
        }

        try {
            if (!this.authService.validate(encodedJWT)) {
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            Claim tokenRoleString = JWT.decode(encodedJWT).getClaim("role");
            if (tokenRoleString == null){
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            UserRole tokenRole = UserRole.valueOf(tokenRoleString.asString());

            for (UserRole endpointRole : endpointRoles) {
                if (endpointRole == tokenRole) {
                    return;
                }
            }

            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        } catch (AuthenticationException e) {
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
        } catch (Exception e) {
            containerRequestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }
}
