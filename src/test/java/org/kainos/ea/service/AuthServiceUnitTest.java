package org.kainos.ea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.cli.Login;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.exception.FailedToLoginException;
import org.kainos.ea.exception.FailedToGenerateTokenException;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest {

    AuthDao authDao = Mockito.mock(AuthDao.class);
    AuthService authService = new AuthService(authDao);

    @Test
    void login_ShouldGenerateToken_WhenLoggedInWithValidCredentials() throws SQLException, FailedToGenerateTokenException, FailedToLoginException {
        //set an instance of a login
        Login testLogin = new Login("admin","admin");
        //set an instance of generating a token
        Mockito.when(authDao.validLogin(testLogin)).thenReturn(true);
        String token = "tokenString";
        Mockito.when(authDao.generateToken(testLogin.getUsername())).thenReturn(token);
        assertEquals(token, authService.login(testLogin));

    }

    @Test
    void login_ShouldThrowFailedToLogin_WhenInvalidCredentialsEntered() throws SQLException, FailedToGenerateTokenException, FailedToLoginException {
        //set an instance of a login
        Login testLogin = new Login("diane","admin");
        Mockito.when(authDao.validLogin(testLogin)).thenReturn(false);


        assertThrows(FailedToLoginException.class, () -> authService.login(testLogin));

    }

    @Test
    void login_shouldThrowFailedToGenerateTokenException_whenDaoThrowsSqlException() throws SQLException, FailedToGenerateTokenException, FailedToLoginException {
        //set an instance of a login
        Login testLogin = new Login("admin","admin");

        Mockito.when(authDao.validLogin(testLogin)).thenReturn(true);
        Mockito.when(authDao.generateToken(testLogin.getUsername())).thenThrow(SQLException.class);

        assertThrows(FailedToGenerateTokenException.class, () -> authService.login(testLogin));

    }
}
