package uz.jl.springbootfeatures.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jl.springbootfeatures.domains.AuthUser;
import uz.jl.springbootfeatures.response.ApiResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/11:03 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public ApiResponse<List<AuthUser>> getAll() {
        return new ApiResponse<List<AuthUser>>(List.of(AuthUser.builder()
                .username("John")
                .password("123")
                .lastLoginTime(LocalDateTime.now())
                .email("john.lgd65@gmail.com")
                .build()), 2);
    }
}
