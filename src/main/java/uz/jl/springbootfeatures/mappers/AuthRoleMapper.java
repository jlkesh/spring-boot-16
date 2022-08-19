package uz.jl.springbootfeatures.mappers;

import org.mapstruct.Mapper;
import uz.jl.springbootfeatures.domains.AuthRole;
import uz.jl.springbootfeatures.dtos.auth.AuthRoleCreateDTO;
import uz.jl.springbootfeatures.dtos.auth.AuthRoleDTO;

import java.util.List;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/15:52 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
@Mapper(componentModel = "spring")
public interface AuthRoleMapper {
    AuthRoleDTO toDTO(AuthRole entity);

    List<AuthRoleDTO> toDTO(List<AuthRole> entities);

    AuthRole fromCreateDTO(AuthRoleCreateDTO dto);
}
