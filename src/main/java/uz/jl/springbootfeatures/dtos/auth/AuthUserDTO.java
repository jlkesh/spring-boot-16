package uz.jl.springbootfeatures.dtos.auth;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author "Elmurodov Javohir"
 * @since 22/08/22/10:48 (Monday)
 * spring-boot-features/IntelliJ IDEA
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUserDTO {
    private Long id;
    private String username;
    private int loginTryCount;
    private String email;
    private String status;
    private LocalDateTime lastLoginTime;
}
