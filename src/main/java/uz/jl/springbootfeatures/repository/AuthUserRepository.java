package uz.jl.springbootfeatures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.jl.springbootfeatures.domains.auth.AuthUser;

import java.util.Optional;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/10:12 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);
}
