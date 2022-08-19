package uz.jl.springbootfeatures.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/12:26 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@Service
public class AccessTokenGenerator implements TokenGenerator {

    private final JWTUtils jwtUtils;

    @Value("${:12}")
    private Integer amountToAdd;
    private final TemporalUnit timeUnit = ChronoUnit.MINUTES;

    public AccessTokenGenerator(@Lazy JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            String authorityValue = authority.getAuthority();
            if (authorityValue.startsWith("ROLE_"))
                roles.add(authority.getAuthority());
            else permissions.add(authorityValue);
        }
        return jwtUtils.jwt(
                userDetails.getUsername(),
                amountToAdd,
                timeUnit,
                Map.of("roles", roles, "permissions", permissions)
        );
    }
}
