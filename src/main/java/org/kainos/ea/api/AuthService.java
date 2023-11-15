package org.kainos.ea.api;
import org.kainos.ea.cli.Login;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.exception.FailedToLoginException;
import org.kainos.ea.exception.FailedToGenerateTokenException;

import java.sql.SQLException;

public class AuthService {
    private final AuthDao authDao;

    public AuthService(AuthDao authDao) {
        this.authDao = authDao;
    }

    public String login(Login login) throws FailedToLoginException, FailedToGenerateTokenException {

        if(authDao.validLogin(login)) {
                return authDao.generateToken(login.getUsername());
        }
        throw new FailedToLoginException();
    }
}
