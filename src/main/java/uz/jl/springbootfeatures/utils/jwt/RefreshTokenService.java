package uz.jl.springbootfeatures.utils.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/15:11 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@Service
public class RefreshTokenService implements TokenService {

    private final JWTUtils jwtUtils;
    private final String secret;
    private final Integer amountToAdd;
    private final TemporalUnit timeUnit;

    public RefreshTokenService(@Lazy JWTUtils jwtUtils,
                               @Value("${jwt.refresh.token.secret}") String secret,
                               @Value("${jwt.refresh.token.expiry.adding.amount}") Integer amountToAdd,
                               @Value("${jwt.refresh.token.expiry.time.unit}") String timeUnitName) {
        this.jwtUtils = jwtUtils;
        this.secret = secret;
        this.amountToAdd = amountToAdd;
        this.timeUnit = ChronoUnit.valueOf(timeUnitName);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return jwtUtils.jwt(
                userDetails.getUsername(),
                secret,
                amountToAdd,
                timeUnit);
    }

    @Override
    public boolean isValid(String token) {
        return jwtUtils.isTokenValid(token, secret);
    }
}
