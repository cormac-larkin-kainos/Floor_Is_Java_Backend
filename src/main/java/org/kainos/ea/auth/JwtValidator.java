package org.kainos.ea.auth;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.List;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtValidator {

    // The public and private keys used by the JWT generator (we need these to validate JWTs)
    RSAPublicKey publicKey = (RSAPublicKey) JwtGenerator.KEYPAIR.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) JwtGenerator.KEYPAIR.getPrivate();

    // The list of allowed issuers
    private static final List<String> allowedIssuers = Collections.singletonList("https://www.floor-is-java.com");

    /**
     * Validate a JWT token
     * @param token
     * @return decoded token
     */
    public boolean validateToken(String token) {
        try {
            final DecodedJWT jwt = JWT.decode(token);

            // Check that the token came from an allowed issuer
            if (!allowedIssuers.contains(jwt.getIssuer())) {
                System.err.println("Unknown Token Issuer: " + jwt.getIssuer());
                throw new InvalidParameterException("Unknown Token Issuer " +  jwt.getIssuer());
            }

            // Validate the JWT using the public/private keys from the JWTGenerator
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(jwt.getIssuer())
                    .build();

            verifier.verify(token);
            return true;

        } catch (Exception e) {
            System.err.println("Failed to validate JWT");
            throw new InvalidParameterException("JWT validation failed: " + e.getMessage());
        }
    }
}