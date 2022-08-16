package uz.jl.springbootfeatures;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.jl.springbootfeatures.domains.auth.AuthPermission;
import uz.jl.springbootfeatures.domains.auth.AuthRole;
import uz.jl.springbootfeatures.domains.auth.AuthUser;
import uz.jl.springbootfeatures.repository.AuthUserRepository;

import java.util.List;

@SpringBootApplication
public class SpringBootFeaturesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }


//    @Bean
    CommandLineRunner runner(PasswordEncoder passwordEncoder, AuthUserRepository repository) {
        return (args) -> {
            AuthRole managerRole = AuthRole.builder()
                    .code("MANAGER")
                    .name("Manager")
                    .build();

            AuthRole adminRole = AuthRole.builder()
                    .code("ADMIN")
                    .name("Admin")
                    .permissions(List.of(AuthPermission.builder().code("CREATE_MANAGER").name("Create Manager").build()))
                    .build();

            AuthRole employeeRole = AuthRole.builder()
                    .code("EMPLOYEE")
                    .name("Employee")
                    .build();

            AuthUser admin = AuthUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("123"))
                    .roles(List.of(adminRole, managerRole, employeeRole))
                    .build();

            AuthUser manager = AuthUser.builder()
                    .username("manager")
                    .password(passwordEncoder.encode("123"))
                    .roles(List.of(managerRole, employeeRole))
                    .build();

            AuthUser user = AuthUser.builder()
                    .username("user")
                    .password(passwordEncoder.encode("123"))
                    .roles(List.of(employeeRole))
                    .build();
            repository.saveAll(List.of(admin, manager, user));
        };
    }

}
