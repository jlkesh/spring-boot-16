package uz.jl.springbootfeatures.domains;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/10:50 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int loginTryCount;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDateTime lastLoginTime;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.NOT_ACTIVE;

    public enum Status {
        ACTIVE, NOT_ACTIVE, BLOCKED;
    }
}