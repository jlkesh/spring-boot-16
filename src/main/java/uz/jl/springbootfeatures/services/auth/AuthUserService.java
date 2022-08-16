package uz.jl.springbootfeatures.services.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.jl.springbootfeatures.configs.security.UserDetails;
import uz.jl.springbootfeatures.domains.auth.AuthUser;
import uz.jl.springbootfeatures.dto.auth.request.LoginRequest;
import uz.jl.springbootfeatures.dto.auth.response.JwtResponse;
import uz.jl.springbootfeatures.repository.AuthUserRepository;
import uz.jl.springbootfeatures.utils.JwtUtils;

import java.util.function.Supplier;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/10:12 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {
    private final AuthUserRepository authUserRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> exception = () ->
                new UsernameNotFoundException("Bad credentials");
        AuthUser authUser = authUserRepository.findByUsername(username).orElseThrow(exception);
        return new UserDetails(authUser);
    }

    public JwtResponse login(LoginRequest request) {
        UserDetails userDetails = loadUserByUsername(request.username());
        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        String jwtToken = jwtUtils.generateJwtToken(userDetails);
        return new JwtResponse(jwtToken, "Bearer");
    }
}
