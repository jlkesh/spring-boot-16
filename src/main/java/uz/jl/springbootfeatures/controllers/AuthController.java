package uz.jl.springbootfeatures.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.jl.springbootfeatures.dto.auth.request.LoginRequest;
import uz.jl.springbootfeatures.dto.auth.response.JwtResponse;
import uz.jl.springbootfeatures.services.auth.AuthUserService;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/10:45 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = {"http://kun.uz", "http://qalampir.uz"})
public class AuthController {
    private final AuthUserService authUserService;

    @PostMapping("/access/token")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authUserService.login(request));
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authUserService.refreshToken(request));
    }

    @GetMapping("/test")
    public boolean test() {
        return true;
    }
}
