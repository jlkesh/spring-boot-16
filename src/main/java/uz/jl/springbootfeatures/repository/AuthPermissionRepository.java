package uz.jl.springbootfeatures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.jl.springbootfeatures.domains.auth.AuthPermission;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/10:13 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */
public interface AuthPermissionRepository extends JpaRepository<AuthPermission, Long> {
}
