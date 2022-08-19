package uz.jl.springbootfeatures.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.jl.springbootfeatures.dtos.auth.AuthRoleCreateDTO;
import uz.jl.springbootfeatures.dtos.auth.AuthRoleDTO;
import uz.jl.springbootfeatures.response.ApiResponse;
import uz.jl.springbootfeatures.services.auth.AuthRoleService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/15:47 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/role")
public class AuthRoleController {

    private final AuthRoleService authRoleService;

    @GetMapping
    @PreAuthorize("hasAuthority(T(uz.jl.springbootfeatures.enums.Permissions).ROLE_READ)")
    public ApiResponse<List<AuthRoleDTO>> getAll() {
        return new ApiResponse<>(authRoleService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(uz.jl.springbootfeatures.enums.Permissions).ROLE_READ)")
    public ApiResponse<AuthRoleDTO> get(@PathVariable Long id) {
        return new ApiResponse<>(authRoleService.get(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(uz.jl.springbootfeatures.enums.Permissions).ROLE_CREATE)")
    public ApiResponse<Void> create(@Valid @RequestBody AuthRoleCreateDTO dto) {
        authRoleService.create(dto);
        // TODO: 19/08/22 standardize status codes
        return new ApiResponse<>(201);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(uz.jl.springbootfeatures.enums.Permissions).ROLE_DELETE)")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        authRoleService.delete(id);
        // TODO: 19/08/22 standardize status codes
        return new ApiResponse<>(204);
    }


}
