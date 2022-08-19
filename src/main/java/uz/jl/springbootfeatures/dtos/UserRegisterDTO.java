package uz.jl.springbootfeatures.dtos;

import lombok.*;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/15:23 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO {
    private String username;
    private String password;
    private String email;
}
