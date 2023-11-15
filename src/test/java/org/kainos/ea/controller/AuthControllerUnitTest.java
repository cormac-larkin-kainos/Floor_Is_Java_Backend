package org.kainos.ea.controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.cli.Login;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.exception.FailedToGenerateTokenException;
import org.kainos.ea.exception.FailedToLoginException;
import org.kainos.ea.resources.AuthController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
public class AuthControllerUnitTest {
    AuthService authService = Mockito.mock(AuthService.class);

    @Test
    void login_shouldReturn200StatusCodeAndGenerateToken_whenServiceReturnsValidLogin() throws FailedToGenerateTokenException, FailedToLoginException {
        Login testLogin = new Login("admin","admin");

        String expectedResponseBody = "tokenString";
        Mockito.when(authService.login(testLogin)).thenReturn(expectedResponseBody);
        //pass in what you want to mock out
        AuthController authController = new AuthController(authService);

        Response response = authController.login(testLogin);

        Assertions.assertEquals(response.getStatus(),200);
        Assertions.assertEquals(response.getEntity(), expectedResponseBody);
    }

    @Test
    void login_shouldReturn401StatusCodeAndThrowFailedToLogin_whenServiceReturnsInvalidLogin() throws FailedToGenerateTokenException, FailedToLoginException {
        Login testLogin = new Login("invalidName","admin");

        Mockito.when(authService.login(testLogin)).thenThrow(FailedToLoginException.class);

        AuthController authController = new AuthController(authService);

        Response response = authController.login(testLogin);

        Assertions.assertEquals(response.getStatus(),401);
        Assertions.assertEquals(response.getEntity(), "Failed to Login");
    }
}
