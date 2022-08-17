package uz.jl.springbootfeatures.utils;

import io.jsonwebtoken.*;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import uz.jl.springbootfeatures.configs.security.UserDetails;
import uz.jl.springbootfeatures.enums.TokenType;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.*;
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
        return getToken((UserDetails) authentication.getPrincipal(), tokenType);
    }

    public String getToken(@NonNull final UserDetails userDetails, @NonNull final TokenType tokenType) {
        if (TokenType.ACCESS.equals(tokenType)) {
            Set<String> roles = new HashSet<>();
            Set<String> permissions = new HashSet<>();
            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                String authorityValue = authority.getAuthority();
                if (authorityValue.startsWith("ROLE_"))
                    roles.add(authority.getAuthority());
                else permissions.add(authorityValue);
            }
            return jwt(userDetails.getUsername(),
                    tokenType.getSecret(),
                    tokenType.getAmountToAdd(),
                    tokenType.getUnit(),
                    Map.of("roles", roles, "permissions", permissions)
            );
        }
        return jwt(userDetails.getUsername(),
                tokenType.getSecret(),
                tokenType.getAmountToAdd(),
                tokenType.getUnit()
        );
    }

    public boolean isTokenValid(String authToken) {
        String subject = getSubject(authToken);
        Date expiration = getClaim(authToken, Claims::getExpiration);
        return (Objects.nonNull(subject) && !isTokenExpired(expiration));
    }

    public String getSubject(String authToken) {
        return getClaim(authToken, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> func) {
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
        return this.jwtBuilder(subject, secret, amountToAdd, unit)
                .compact();
    }

    private String jwt(@NonNull final String subject,
                       @NonNull final String secret,
                       int amountToAdd,
                       TemporalUnit unit,
                       Map<String, Object> claims) {
        return this.jwtBuilder(subject, secret, amountToAdd, unit)
                .addClaims(claims)
                .compact();
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
