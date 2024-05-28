package com.berk2s.ds.api.infrastructure.security.jwt;

import com.berk2s.ds.api.application.auth.TokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.security.SecureRandom;

@RequiredArgsConstructor
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    private static final String SECRET;
    private static final JWSHeader JWT_HEADER;
    private static final long TOKEN_EXPIRATION_TIME;

    static {
        SECRET = generateSecretKey();
        JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
        TOKEN_EXPIRATION_TIME = 3600000;
    }

    @Override
    public String generateToken(String username) {
        try {
            JWSSigner signer = new MACSigner(SECRET);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .expirationTime(new Date(new Date().getTime() + TOKEN_EXPIRATION_TIME))
                    .issueTime(new Date())
                    .notBeforeTime(new Date())
                    .build();

            SignedJWT signedJWT = new SignedJWT(JWT_HEADER, claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    @Override
    public JWTClaimsSet parseAndValidate(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET);

            if (signedJWT.verify(verifier)) {
                return signedJWT.getJWTClaimsSet();
            } else {
                throw new InvalidJWT("jwt.invalid");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing or validating token", e);
        }
    }

    @Override
    public boolean validate(String token) {
        try {
            JWTClaimsSet claimsSet = parseAndValidate(token);
            Date expirationTime = claimsSet.getExpirationTime();
            return expirationTime.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private static String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
