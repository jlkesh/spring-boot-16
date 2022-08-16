package uz.jl.springbootfeatures.utils;

import io.jsonwebtoken.*;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import uz.jl.springbootfeatures.configs.security.UserDetails;

import java.util.Date;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/09:30 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */

@Component
public class JwtUtils {

    private final String jwtSecret = "secret-123ntTimeMillisntTimeMillis";

    public static final Supplier<SignatureAlgorithm> algorithm = () -> SignatureAlgorithm.HS512;

    private final int jwtExpirationMs = 120_000;

    public String generateJwtToken(Authentication authentication) {
        return generateJwtToken((UserDetails) authentication.getPrincipal());
    }

    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(algorithm.get(), jwtSecret)
                .setIssuer("path")
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.err.println(("Invalid JWT signature: {}" + e.getMessage()));
        } catch (MalformedJwtException e) {
            System.err.println(("Invalid JWT token: {}" + e.getMessage()));
        } catch (ExpiredJwtException e) {
            System.err.println(("JWT token is expired: {}" + e.getMessage()));
        } catch (UnsupportedJwtException e) {
            System.err.println(("JWT token is unsupported: {}" + e.getMessage()));
        } catch (IllegalArgumentException e) {
            System.err.println(("JWT claims string is empty: {}" + e.getMessage()));
        }
        return false;
    }

}
