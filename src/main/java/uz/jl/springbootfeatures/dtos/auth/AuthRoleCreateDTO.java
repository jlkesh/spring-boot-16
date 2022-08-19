package uz.jl.springbootfeatures.dtos.auth;

import lombok.Builder;
import lombok.Data;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/16:34 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
@Data
@Builder
public class AuthRoleCreateDTO {
    private final String code;
    private final String name;
}
