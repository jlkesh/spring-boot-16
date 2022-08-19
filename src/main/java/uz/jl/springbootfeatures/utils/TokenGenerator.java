package uz.jl.springbootfeatures.utils;

import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/12:26 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
public interface TokenGenerator {
    String generateToken(UserDetails userDetails);
}
