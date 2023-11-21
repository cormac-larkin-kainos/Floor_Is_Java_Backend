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
import org.kainos.ea.cli.UserRole;

public class JwtGenerator {

    public static KeyPair KEYPAIR;

    public JwtGenerator() {
        try {
            KEYPAIR = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch(NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Generates a JWT token for a specified user.
     *
     * @param username the user to generate the token for
     * @param role the token to generate the role for
     * @return The JWT token as a String
     */
    public String generateJwt(String username, UserRole role) {

        Builder tokenBuilder = JWT.create()
                .withIssuer("https://www.floor-is-java.com") //placeholder url
                .withSubject(username)
                .withClaim("jti", UUID.randomUUID().toString())
                .withClaim("role",role.toString())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(28800))) // Token expires after 8 hours
                .withIssuedAt(Date.from(Instant.now()));

        return tokenBuilder.sign(Algorithm.RSA256(((RSAPublicKey) KEYPAIR.getPublic()), ((RSAPrivateKey)  KEYPAIR.getPrivate())));
    }

}
