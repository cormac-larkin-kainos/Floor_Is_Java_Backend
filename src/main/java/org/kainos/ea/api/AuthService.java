package org.kainos.ea.api;

import org.kainos.ea.auth.JwtGenerator;
import org.kainos.ea.auth.JwtValidator;
import org.kainos.ea.cli.AuthenticationException;
import org.kainos.ea.cli.HashedPassword;
import org.kainos.ea.cli.Login;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.IAuthSource;
import org.kainos.ea.exception.FailedToLoginException;
import org.kainos.ea.exception.FailedToGenerateTokenException;
import org.kainos.ea.exception.InvalidCredentialsException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Arrays;

public class AuthService {

    private final IAuthSource authDao;
    private final JwtGenerator jwtGenerator;

    private static final int KEY_LENGTH = 512;
    private static final String HASH_ALG = "PBKDF2WithHmacSHA512";

    /**
     * Creates a new instance of the auth service
     * @param authDao the auth source to use
     * @param jwtGenerator the generator for JWTs to use
     */
    public AuthService(IAuthSource authDao, JwtGenerator jwtGenerator) {
        this.authDao = authDao;
        this.jwtGenerator = jwtGenerator;
    }

    /**
     * Re-generates a password hash given the passed parameters.
     * @param password the password to hash.
     * @param salt the salt used in the hash.
     * @param iterations the number of iterations used in the hash.
     * @return byte[] representing the hash.
     * @throws AuthenticationException throw if hashing fails
     */
    private byte[] getPasswordHash(
            final String password,
            final byte[] salt,
            final int iterations)
            throws AuthenticationException {

        try {
            final char[] passwordChars = password.toCharArray();
            KeySpec spec = new PBEKeySpec(passwordChars, salt, iterations, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALG);
            return factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    /**
     * Attempts to log in a user
     * @param login the credentials to use
     * @return String representing the token
     * @throws AuthenticationException thrown if something goes wrong with the database
     */
    public String login(Login login) throws AuthenticationException {
        HashedPassword hashedPassword =
                authDao.getPasswordForUser(login.getUsername());

        if (hashedPassword == null) {
            return null;
        }

        //re hash the provided password and add the existing salt
        byte[] credentialHash = getPasswordHash(login.getPassword(), hashedPassword.getPasswordSalt(), hashedPassword.getIterations());
        byte[] passwordHash = hashedPassword.getPasswordHash();

        if (!Arrays.equals(credentialHash, passwordHash)) {
            return null;
        }

        return jwtGenerator.generateJwt(login.getUsername());
    }
}
