package org.kainos.ea.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kainos.ea.cli.UserRole;

public class JwtGeneratorTest {
    private static JwtGenerator generator;

    @BeforeAll
    static void setup() {
        try {
           generator = new JwtGenerator();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Test
    void checkTokenGeneratedWithUserNamePassedIn(){
        String jwt = generator.generateJwt("test", UserRole.User);
        String subject = JWT.decode(jwt).getSubject();

        Assertions.assertEquals(subject,"test");
    }

    @Test
    void checkTokenNotGeneratedIfNullUserPassed() {
        String jwt = generator.generateJwt(null,UserRole.User);
        Assertions.assertNull(jwt);
    }

    @Test
    void checkTokenGivenWithCorrectIssuer() {
        String jwt = generator.generateJwt("Test",UserRole.User);
        DecodedJWT token = JWT.decode(jwt);
        Assertions.assertEquals(token.getIssuer(),"https://www.floor-is-java.com");
    }
}
