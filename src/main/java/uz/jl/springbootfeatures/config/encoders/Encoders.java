package uz.jl.springbootfeatures.config.encoders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/12:18 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@Configuration
public class Encoders {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
