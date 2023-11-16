package org.kainos.ea.auth;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtGenerator {

    public static final KeyPair KEYPAIR;

    static {
        try {
            KEYPAIR = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public JwtGenerator() throws NoSuchAlgorithmException {
    }

    /**
     * Generates a JWT token for a specified user
     *
     * @param username
     * @return The JWT token as a String
     */
    public String generateJwt(String username) {

        Builder tokenBuilder = JWT.create()
                .withIssuer("https://www.floor-is-java.com") //placeholder url
                .withSubject(username)
                .withClaim("jti", UUID.randomUUID().toString())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(1800))) // Token expires after 30 minutes
                .withIssuedAt(Date.from(Instant.now()));

        return tokenBuilder.sign(Algorithm.RSA256(((RSAPublicKey) KEYPAIR.getPublic()), ((RSAPrivateKey)  KEYPAIR.getPrivate())));
    }

}
