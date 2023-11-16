package org.kainos.ea.api;
import org.kainos.ea.auth.JwtGenerator;
import org.kainos.ea.auth.JwtValidator;
import org.kainos.ea.cli.Login;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.exception.FailedToLoginException;
import org.kainos.ea.exception.FailedToGenerateTokenException;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class AuthService {
    private final AuthDao authDao;

    public AuthService(AuthDao authDao) {
        this.authDao = authDao;
    }

    public String login(Login login) throws FailedToLoginException, FailedToGenerateTokenException {

        if(authDao.validLogin(login)) {

            // Generate a new JWT and insert the username into the payload
            try {
                JwtGenerator jwtGenerator = new JwtGenerator();
                String token = jwtGenerator.generateJwt(login.getUsername());

                try {
                    if (new JwtValidator().validateToken(token)) {
                        System.err.println("VALID TOKEN");
                    } else {
                        System.err.println("TOKEN INVALID");
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }


                return token;
            } catch (NoSuchAlgorithmException e) {
                throw new FailedToGenerateTokenException();
            }
        }
        throw new FailedToLoginException();
    }
}
