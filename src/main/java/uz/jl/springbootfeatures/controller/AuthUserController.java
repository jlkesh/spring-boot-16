package uz.jl.springbootfeatures.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.jl.springbootfeatures.domains.AuthUser;
import uz.jl.springbootfeatures.dtos.JwtResponse;
import uz.jl.springbootfeatures.dtos.LoginRequest;
import uz.jl.springbootfeatures.response.ApiResponse;
import uz.jl.springbootfeatures.services.AuthUserService;


/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/10:50 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final AuthUserService authUserService;


    @GetMapping(value = "/login", produces = "application/json")
    public ApiResponse<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ApiResponse<>(authUserService.login(loginRequest));
    }

    @PostMapping("/register")
    public ApiResponse<AuthUser> register() {
        return new ApiResponse<>(authUserService.register(new Object()));
    }

    @GetMapping("/me")
    public AuthUser me() {
        return null;
    }
}
