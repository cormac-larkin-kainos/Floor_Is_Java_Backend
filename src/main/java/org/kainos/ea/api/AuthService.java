package org.kainos.ea.api;

import org.kainos.ea.auth.JwtGenerator;
import org.kainos.ea.auth.JwtValidator;
import org.kainos.ea.cli.Login;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.exception.FailedToLoginException;
import org.kainos.ea.exception.FailedToGenerateTokenException;
import org.kainos.ea.exception.InvalidCredentialsException;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class AuthService {

    private final AuthDao authDao;
    private final JwtGenerator jwtGenerator;

    public AuthService(AuthDao authDao, JwtGenerator jwtGenerator) {
        this.authDao = authDao;
        this.jwtGenerator = jwtGenerator;
    }

    public String login(Login login) throws FailedToLoginException, InvalidCredentialsException, FailedToGenerateTokenException {

        try {
            if (authDao.validLogin(login)) {
                return jwtGenerator.generateJwt(login.getUsername());
            }
            throw new InvalidCredentialsException();
        } catch (SQLException e) {
            throw new FailedToLoginException();
        }
    }
}
