package uz.jl.springbootfeatures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.jl.springbootfeatures.domains.AuthUser;

import java.util.Optional;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/12:09 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);
}
