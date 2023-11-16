package org.kainos.ea.controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.cli.Login;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.exception.FailedToGenerateTokenException;
import org.kainos.ea.exception.FailedToLoginException;
import org.kainos.ea.exception.InvalidCredentialsException;
import org.kainos.ea.resources.AuthController;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
public class AuthControllerUnitTest {
    AuthService authService = Mockito.mock(AuthService.class);

    @Test
    void login_shouldReturn200StatusCodeAndGenerateToken_whenServiceReturnsValidLogin() throws FailedToGenerateTokenException, FailedToLoginException, InvalidCredentialsException {
        Login testLogin = new Login("admin","admin");

        String expectedResponseBody = "tokenString";
        Mockito.when(authService.login(testLogin)).thenReturn(expectedResponseBody);
        //pass in what you want to mock out
        AuthController authController = new AuthController(authService);

        Response response = authController.login(testLogin);

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(response.getEntity(), expectedResponseBody);
    }

    @Test
    void login_shouldReturn401StatusCode_whenServiceThrowsInvalidCredentialsException() throws FailedToGenerateTokenException, FailedToLoginException, InvalidCredentialsException {
        Login testLogin = new Login("invalidName","admin");

        Mockito.when(authService.login(testLogin)).thenThrow(InvalidCredentialsException.class);

        AuthController authController = new AuthController(authService);

        Response response = authController.login(testLogin);

        Assertions.assertEquals(401, response.getStatus());
        Assertions.assertEquals(response.getEntity(), "Login failed: invalid credentials");
    }

    @Test
    void login_shouldReturn500StatusCode_whenServiceThrowsFailedToLoginException() throws InvalidCredentialsException, FailedToLoginException, FailedToGenerateTokenException {
        Login testLogin = new Login("admin","admin");

        Mockito.when(authService.login(testLogin)).thenThrow(FailedToLoginException.class);

        AuthController authController = new AuthController(authService);

        Response response = authController.login(testLogin);

        Assertions.assertEquals(500, response.getStatus());
    }
}
