package uz.jl.springbootfeatures.utils.jwt;

import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/12:26 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
public interface TokenService {
    String generateToken(UserDetails userDetails);

    boolean isValid(String token);

    default String getSubject(String token) {
        return null;
    }
}
