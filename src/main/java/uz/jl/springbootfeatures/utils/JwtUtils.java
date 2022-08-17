package uz.jl.springbootfeatures.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import uz.jl.springbootfeatures.configs.security.UserDetails;
import uz.jl.springbootfeatures.enums.TokenType;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/09:30 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */

@Component
public class JwtUtils {


    public static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;


    public String getToken(@NonNull final Authentication authentication, @NonNull final TokenType tokenType) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwt(userDetails.getUsername(), tokenType.getSecret(), tokenType.getAmountToAdd(), tokenType.getUnit());
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(TokenType.ACCESS.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String authToken) {
        String subject = getClaim(authToken, Claims::getSubject);
        Date expiration = getClaim(authToken, Claims::getExpiration);
        return (Objects.nonNull(subject) && !isTokenExpired(expiration));
    }

    private <T> T getClaim(String token, Function<Claims, T> func) {
        Jws<Claims> claimsJws = jwtClaims(token);
        Claims claims = claimsJws.getBody();
        return func.apply(claims);
    }

    private boolean isTokenExpired(Date expiration) {
        Instant now = Instant.now(Clock.systemDefaultZone());
        return now.isAfter(expiration.toInstant());
    }

    private Jws<Claims> jwtClaims(String authToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(TokenType.ACCESS.getSecret())
                    .parseClaimsJws(authToken);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    private String jwt(@NonNull final String subject,
                       @NonNull final String secret,
                       int amountToAdd, TemporalUnit unit) {
        Instant now = Instant.now(Clock.systemDefaultZone());
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(amountToAdd, unit)))
                .signWith(algorithm, secret)
                .compact();
    }

}
