package uz.jl.springbootfeatures.utils.jwt;

import io.jsonwebtoken.*;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/12:03 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
@Component
public class JWTUtils {
    public static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;

    public boolean isTokenValid(String token, String secret) {
        String subject = getSubject(token, secret);
        Date expiration = getClaim(token, Claims::getExpiration, secret);
        return (Objects.nonNull(subject) && !isTokenExpired(expiration));
    }

    public String getSubject(String token, String secret) {
        return getClaim(token, Claims::getSubject, secret);
    }

    public <T> T getClaim(String token, Function<Claims, T> func, String secret) {
        Jws<Claims> claimsJws = jwtClaims(token, secret);
        Claims claims = claimsJws.getBody();
        return func.apply(claims);
    }

    public String jwt(@NonNull final String subject,
                      @NonNull final String secret,
                      int amountToAdd, TemporalUnit unit) {
        return jwtBuilder(subject, secret, amountToAdd, unit).compact();
    }


    private boolean isTokenExpired(Date expiration) {
        Instant now = Instant.now(Clock.systemDefaultZone());
        return now.isAfter(expiration.toInstant());
    }

    private Jws<Claims> jwtClaims(@NonNull final String token, @NonNull final String secret) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private JwtBuilder jwtBuilder(@NonNull final String subject,
                                  @NonNull final String secret,
                                  int amountToAdd,
                                  TemporalUnit unit) {
        Instant now = Instant.now(Clock.systemDefaultZone());
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(amountToAdd, unit)))
                .signWith(algorithm, secret);
    }

}