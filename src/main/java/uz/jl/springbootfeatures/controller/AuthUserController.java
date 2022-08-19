package uz.jl.springbootfeatures.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jl.springbootfeatures.domains.AuthUser;
import uz.jl.springbootfeatures.response.ApiErrorResponse;
import uz.jl.springbootfeatures.response.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Random;


/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/10:50 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserController {

    Environment environment;


    @GetMapping(value = "/login", produces = "application/json")
    public ApiResponse<AuthUser> login() {
        return new ApiResponse<AuthUser>(AuthUser.builder()
                .username("John")
                .password("123")
                .lastLoginTime(LocalDateTime.now())
                .email("john.lgd65@gmail.com")
                .build());
    }

    @PostMapping("/register")
    public ApiResponse<AuthUser> register(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();

        int i = new Random().nextInt(2);
        if (i == 1)
            return new ApiResponse<>(ApiErrorResponse
                    .builder()
                    .friendlyMessage("Uzr bro omadiz kelmadi")
//                    .developerMessage("Endi *****************************************")
                    .requestPath(requestURL)
                    .build(),
                    HttpStatus.BAD_REQUEST
            );
        return new ApiResponse<>(AuthUser.builder()
                .username("John")
                .password("123")
                .lastLoginTime(LocalDateTime.now())
                .email("john.lgd65@gmail.com")
                .build());
    }

    @GetMapping("/me")
    public AuthUser me() {
        return null;
    }
}
