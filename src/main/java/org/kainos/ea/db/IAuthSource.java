package org.kainos.ea.db;

import org.kainos.ea.cli.AuthenticationException;
import org.kainos.ea.cli.HashedPassword;

/**
 * Represents a source of authentication within the system
 */
public interface IAuthSource {

    /**
     * Gets the hashed password stored for the user given
     * @param username the username to search
     * @return HashedPassword instance
     * @throws AuthenticationException thrown on database error
     */
    HashedPassword getPasswordForUser(String username) throws AuthenticationException;


}
