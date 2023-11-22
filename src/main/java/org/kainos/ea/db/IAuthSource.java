package org.kainos.ea.db;

import io.dropwizard.auth.Auth;
import org.kainos.ea.cli.UserRole;
import org.kainos.ea.exception.AuthenticationException;
import org.kainos.ea.cli.HashedPassword;

/**
 * Represents a source of authentication within the system
 */
public interface IAuthSource {

    /**
     * Gets the hashed password stored for the user given.
     * @param username the username to search
     * @return HashedPassword instance
     * @throws AuthenticationException thrown on database error
     */
    HashedPassword getPasswordForUser(String username) throws AuthenticationException;

    /**
     * Gets the role associated with a username.
     * @return a user role enum describing the users role
     * @throws AuthenticationException thrown if there was an error fetching the role
     */
    UserRole getRoleForUser(String username) throws AuthenticationException;
}
