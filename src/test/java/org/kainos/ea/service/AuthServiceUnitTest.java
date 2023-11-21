package org.kainos.ea.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.AuthService;
import org.kainos.ea.auth.JwtGenerator;
import org.kainos.ea.auth.JwtValidator;
import org.kainos.ea.cli.UserRole;
import org.kainos.ea.exception.AuthenticationException;
import org.kainos.ea.cli.HashedPassword;
import org.kainos.ea.cli.Login;
import org.kainos.ea.db.AuthDao;
import org.kainos.ea.db.IAuthSource;
import org.kainos.ea.exception.FailedToLoginException;
import org.kainos.ea.exception.FailedToGenerateTokenException;
import org.kainos.ea.exception.InvalidCredentialsException;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest {

    private static final String HASH_ALG = "PBKDF2WithHmacSHA512";
    private static final int KEY_LENGTH = 512;

    IAuthSource authDao = Mockito.mock(AuthDao.class);
    JwtGenerator jwtGenerator = Mockito.mock(JwtGenerator.class);
    JwtValidator jwtValidator = Mockito.mock(JwtValidator.class);

    AuthService authService = new AuthService(authDao, jwtGenerator,jwtValidator);

    private static String VALID_USERNAME = System.getenv("TEST_VALID_USERNAME");
    private static String VALID_PASSWORD = System.getenv("TEST_VALID_PASSWORD");

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    private static HashedPassword generateHashForPassword(String password) {
        try {
            char[] passwordChars = password.toCharArray();
            byte[] salt = generateSalt();
            int iterations = 3000;
            KeySpec spec = new PBEKeySpec(passwordChars, salt, iterations, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(HASH_ALG);

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return new HashedPassword(hash,iterations,salt);
        } catch(Exception e){
            return null;
        }
    }

    @Test
    void login_ShouldGenerateToken_WhenLoggedInWithValidCredentials() throws SQLException, FailedToGenerateTokenException, FailedToLoginException, InvalidCredentialsException, AuthenticationException {

        Login testLogin = new Login(VALID_USERNAME,VALID_PASSWORD);

        HashedPassword hashedPassword = generateHashForPassword(testLogin.getPassword());

        Mockito.when(authDao.getPasswordForUser(testLogin.getUsername())).thenReturn(hashedPassword);

        // Check that a JWT is returned from the authService after a successful login
        String jwt = "TEST.TEST.TEST";
        Mockito.when(jwtGenerator.generateJwt(testLogin.getUsername(), UserRole.User)).thenReturn(jwt);
        Mockito.when(authDao.getRoleForUser(testLogin.getUsername())).thenReturn(UserRole.User);
        assertEquals(jwt, authService.login(testLogin));
    }

    @Test
    void login_ShouldReturnNull_WhenInvalidCredentialsEntered() throws AuthenticationException {

        Login testLogin = new Login("diane","admin");
        Mockito.when(authDao.getPasswordForUser(testLogin.getUsername())).thenReturn(null);

        assertNull(authService.login(testLogin));
    }

    @Test
    void login_shouldThrowAuthenticationException_whenDAOThrowsAuthenticationException() throws AuthenticationException {
        Login testLogin = new Login(VALID_USERNAME,VALID_PASSWORD);
        Mockito.when(authDao.getPasswordForUser(testLogin.getUsername())).thenThrow(AuthenticationException.class);
        assertThrows(AuthenticationException.class, () -> authService.login(testLogin));
    }
}
