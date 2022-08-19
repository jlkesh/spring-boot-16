package uz.jl.springbootfeatures.mappers;

import org.mapstruct.Mapper;
import uz.jl.springbootfeatures.domains.AuthUser;
import uz.jl.springbootfeatures.dtos.UserRegisterDTO;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/15:28 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    AuthUser fromRegisterDTO(UserRegisterDTO dto);
}
