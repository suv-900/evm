package com.project.evm.services;

import java.util.Date;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

public class TokenService {
    // private final static Logger log = LoggerFactory.getLogger(TokenService.class);
    private final static String serverSecret = "host-service";
    private final static long EXPIRATION_TIME = 864_000_000; //10days
    
    private static Algorithm algorithm = null;
    private static JWTVerifier verifier = null;

    static{
        algorithm = Algorithm.HMAC256(serverSecret.getBytes());
        verifier = JWT.require(algorithm).build();
    }

    public String generateToken(String username)throws JWTCreationException,Exception{
        return JWT.create()
            .withIssuer("evm-host")
            .withClaim("username",username)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(algorithm);
    }

    public String extractUsername(String token)throws JWTVerificationException,TokenExpiredException,AlgorithmMismatchException{
        return verifier.verify(token)
            .getClaim("username")
            .toString();
    }
}
