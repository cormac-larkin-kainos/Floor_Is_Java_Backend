package org.kainos.ea.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class JwtValidatorTest {
    private static JwtGenerator generator;
    private static JwtValidator validator;

    @BeforeAll
    static void setup() {
        try {
            generator = new JwtGenerator();
            validator = new JwtValidator();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Test
    void checkValidTokenPassesValidation() {
        String jwt = generator.generateJwt("test");
        Assertions.assertTrue(validator.validateToken(jwt));
    }

    @Test
    void checkInvalidSignedTokenFailsValidation() throws NoSuchAlgorithmException {
        JWTCreator.Builder tokenBuilder = JWT.create()
                .withIssuer("https://www.floor-is-java.com") //placeholder url
                .withSubject("test")
                .withClaim("jti", UUID.randomUUID().toString())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(28800))) // Token expires after 8 hours
                .withIssuedAt(Date.from(Instant.now()));
        KeyPair keypair  = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        String sign = tokenBuilder.sign(Algorithm.RSA256(((RSAPublicKey) keypair.getPublic()), ((RSAPrivateKey)  keypair.getPrivate())));


        Assertions.assertThrows(InvalidParameterException.class, () -> {
            validator.validateToken(sign);
        });
    }
}
