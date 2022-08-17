package uz.jl.springbootfeatures.services.auth;

import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.jl.springbootfeatures.configs.security.UserDetails;
import uz.jl.springbootfeatures.domains.auth.AuthUser;
import uz.jl.springbootfeatures.dto.auth.request.LoginRequest;
import uz.jl.springbootfeatures.dto.auth.request.RefreshTokenRequest;
import uz.jl.springbootfeatures.dto.auth.response.JwtResponse;
import uz.jl.springbootfeatures.enums.TokenType;
import uz.jl.springbootfeatures.repository.AuthUserRepository;
import uz.jl.springbootfeatures.utils.JwtUtils;

import java.util.function.Supplier;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/10:12 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */

@Service
public class AuthUserService implements UserDetailsService {
    private final AuthenticationManager authenticationManager;
    private final AuthUserRepository authUserRepository;
    private final JwtUtils jwtUtils;

    public AuthUserService(@Lazy AuthenticationManager authenticationManager, AuthUserRepository authUserRepository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.authUserRepository = authUserRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> exception = () ->
                new UsernameNotFoundException("Bad credentials");
        AuthUser authUser = authUserRepository.findByUsername(username).orElseThrow(exception);
        return new UserDetails(authUser);
    }

    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        String accessToken = jwtUtils.getToken(authentication, TokenType.ACCESS);
        String refreshToken = jwtUtils.getToken(authentication, TokenType.REFRESH);
        return new JwtResponse(accessToken, refreshToken, "Bearer");
    }

    public JwtResponse refreshToken(@NonNull RefreshTokenRequest request) {
        String token = request.token();
        if (jwtUtils.isTokenValid(token)) {
            throw new RuntimeException("Token invalid");
        }
        String subject = jwtUtils.getSubject(token);
        UserDetails userDetails = loadUserByUsername(subject);
        String accessToken = jwtUtils.getToken(userDetails, TokenType.ACCESS);
        return new JwtResponse(accessToken, request.token(), "Bearer");
    }
}
