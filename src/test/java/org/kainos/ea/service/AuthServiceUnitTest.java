package org.kainos.ea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.auth.JwtGenerator;
import org.kainos.ea.cli.Login;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.exception.FailedToLoginException;
import org.kainos.ea.exception.FailedToGenerateTokenException;
import org.kainos.ea.exception.InvalidCredentialsException;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest {

    AuthDao authDao = Mockito.mock(AuthDao.class);
    JwtGenerator jwtGenerator = Mockito.mock(JwtGenerator.class);

    AuthService authService = new AuthService(authDao, jwtGenerator);

    @Test
    void login_ShouldGenerateToken_WhenLoggedInWithValidCredentials() throws SQLException, FailedToGenerateTokenException, FailedToLoginException, InvalidCredentialsException {

        Login testLogin = new Login("admin","admin");

        Mockito.when(authDao.validLogin(testLogin)).thenReturn(true);

        // Check that a JWT is returned from the authService after a successful login
        String jwt = "This is a sample JWT";
        Mockito.when(jwtGenerator.generateJwt(testLogin.getUsername())).thenReturn(jwt);
        assertEquals(jwt, authService.login(testLogin));
    }

    @Test
    void login_ShouldThrowInvalidCredentialsException_WhenInvalidCredentialsEntered() throws SQLException {

        Login testLogin = new Login("diane","admin");
        Mockito.when(authDao.validLogin(testLogin)).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.login(testLogin));
    }

    @Test
    void login_shouldThrowFailedToLoginException_whenDaoThrowsSqlException() throws SQLException {

        Login testLogin = new Login("admin","admin");

        Mockito.when(authDao.validLogin(testLogin)).thenThrow(SQLException.class);

        assertThrows(FailedToLoginException.class, () -> authService.login(testLogin));
    }
}
