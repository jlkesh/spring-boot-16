package uz.jl.springbootfeatures.domains.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/09:38 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "auth_permission")
public class AuthPermission implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String name;

    @Override
    public String getAuthority() {
        return this.code;
    }
}