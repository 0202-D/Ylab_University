package io.ylab.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.lang.Assert;
import io.ylab.dto.user.UserDtoRs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class JwtProvider {
    private static final String SECRET_KEY = "mckkjnnhT53=pcntUUtwb34Zzhhenfn";
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    //сгенерировать токен JWT (Access Token)
    public String generateAccessJwtToken(UserDtoRs userPrincipal) {
        return Jwts.builder()
                .setSubject((userPrincipal.getUserName()))
                .setId(String.valueOf(userPrincipal.getUserId()))
                .setExpiration(Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(
                        SignatureAlgorithm.HS512,
                        setSigningKey(SECRET_KEY)
                )
                .compact();
    }
    public byte[] setSigningKey(String base64EncodedKeyBytes) {
        Assert.hasText(base64EncodedKeyBytes, "signing key cannot be null or empty.");
        return TextCodec.BASE64.decode(base64EncodedKeyBytes);
    }
    public boolean validateJwtToken(HttpServletRequest req) {
        String authorizationHeader = req.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
                return true;
            } catch (SignatureException e) {
                logger.error("Invalid signature -> Message: {}", e);
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT token -> Message: {}", e);
            } catch (UnsupportedJwtException e) {
                logger.error("Unsupported JWT token -> Message: {}", e);
            } catch (IllegalArgumentException e) {
                logger.error("JWT claims string is empty -> Message: {}", e);
            }
        }

        return false;
    }
}
