package uz.jl.springbootfeatures.services.auth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.jl.springbootfeatures.domains.AuthRole;
import uz.jl.springbootfeatures.dtos.auth.AuthRoleCreateDTO;
import uz.jl.springbootfeatures.dtos.auth.AuthRoleDTO;
import uz.jl.springbootfeatures.exceptions.GenericNotFoundException;
import uz.jl.springbootfeatures.mappers.AuthRoleMapper;
import uz.jl.springbootfeatures.repository.auth.AuthRoleRepository;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/15:48 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@Service
@RequiredArgsConstructor
public class AuthRoleService {

    private final AuthRoleRepository authRoleRepository;
    private final AuthRoleMapper authRoleMapper;

    public List<AuthRoleDTO> getAll() {
        List<AuthRole> authRoles = authRoleRepository.findAll();
        return authRoleMapper.toDTO(authRoles);
    }

    public AuthRoleDTO get(@NonNull Long id) {
        // TODO: 19/08/22 standardize status codes
        Supplier<GenericNotFoundException> notFoundException = () -> new GenericNotFoundException("Role not found", 404);
        AuthRole authRole = authRoleRepository.findById(id).orElseThrow(notFoundException);
        return authRoleMapper.toDTO(authRole);
    }

    public void create(AuthRoleCreateDTO dto) {
        AuthRole authRole = authRoleMapper.fromCreateDTO(dto);
        authRoleRepository.save(authRole);
    }

    public void delete(@NonNull Long id) {
        authRoleRepository.deleteById(id);
    }
}
