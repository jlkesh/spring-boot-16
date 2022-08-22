package uz.jl.springbootfeatures.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.jl.springbootfeatures.domains.ActivationCode;

import java.util.Optional;

/**
 * @author "Elmurodov Javohir"
 * @since 22/08/22/11:23 (Monday)
 * spring-boot-features/IntelliJ IDEA
 */
public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {
    Optional<ActivationCode> findByActivationLink(String link);
}
