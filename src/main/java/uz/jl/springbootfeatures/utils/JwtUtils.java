package uz.jl.springbootfeatures.utils;

import io.jsonwebtoken.*;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${security.jwt.secret}")
    private String jwtSecret;


    @Value("${security.jwt.expiry.in.seconds}")
    private int jwtExpiration;

    public static final Supplier<SignatureAlgorithm> algorithm = () -> SignatureAlgorithm.HS512;


    public String generateJwtAccessToken(Authentication authentication) {
        return generateJwtAccessToken((UserDetails) authentication.getPrincipal());
    }

    public String generateJwtAccessToken(UserDetails userDetails) {
        return getJwtBuilder(userDetails)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000L))
                .compact();
    }


    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateJwtRefreshToken(UserDetails userDetails) {
        return getJwtBuilder(userDetails)
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 86_400_000))
                .compact();
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

    private JwtBuilder getJwtBuilder(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .signWith(algorithm.get(), jwtSecret);
    }

}
