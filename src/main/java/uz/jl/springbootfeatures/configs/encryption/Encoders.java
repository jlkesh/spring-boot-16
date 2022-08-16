package uz.jl.springbootfeatures.configs.encryption;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/09:29 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */

@Configuration
public class Encoders {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
