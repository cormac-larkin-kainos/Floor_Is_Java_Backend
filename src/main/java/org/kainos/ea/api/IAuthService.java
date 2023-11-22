package org.kainos.ea.api;

import org.kainos.ea.cli.Login;
import org.kainos.ea.exception.AuthenticationException;

/**
 * Describes an authentication service
 */
public interface IAuthService {
    /**
     * Attempts to login a user a generates a token
     * @param login the credentials to attempt to login
     * @return String representing the token
     * @throws AuthenticationException thrown on error accessing an auth source
     */
    String login (Login login) throws AuthenticationException;

    /**
     * Attempts to validate a token issued by the service.
     * @param token the token to validate
     * @return true if valid, false if not
     * @throws AuthenticationException Thrown if there was an error validating the token
     */
    boolean validate(String token) throws AuthenticationException;
}
