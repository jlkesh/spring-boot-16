package uz.jl.springbootfeatures.services;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.jl.springbootfeatures.config.security.UserDetails;
import uz.jl.springbootfeatures.domains.AuthUser;
import uz.jl.springbootfeatures.dtos.JwtResponse;
import uz.jl.springbootfeatures.dtos.LoginRequest;
import uz.jl.springbootfeatures.dtos.RefreshTokenRequest;
import uz.jl.springbootfeatures.dtos.UserRegisterDTO;
import uz.jl.springbootfeatures.dtos.auth.AuthUserDTO;
import uz.jl.springbootfeatures.exceptions.GenericInvalidTokenException;
import uz.jl.springbootfeatures.mappers.AuthUserMapper;
import uz.jl.springbootfeatures.repository.AuthUserRepository;
import uz.jl.springbootfeatures.services.mail.MailService;
import uz.jl.springbootfeatures.utils.jwt.TokenService;

import java.util.function.Supplier;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/12:07 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@Service
public class AuthUserService implements UserDetailsService {
    private final AuthenticationManager authenticationManager;
    private final AuthUserRepository authUserRepository;
    private final TokenService accessTokenService;
    private final TokenService refreshTokenService;
    private final AuthUserMapper authUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public AuthUserService(@Lazy AuthenticationManager authenticationManager,
                           AuthUserRepository authUserRepository,
                           @Qualifier("accessTokenService") TokenService accessTokenService,
                           @Qualifier("refreshTokenService") TokenService refreshTokenService,
                           AuthUserMapper authUserMapper,
                           PasswordEncoder passwordEncoder, MailService mailService) {
        this.authenticationManager = authenticationManager;
        this.authUserRepository = authUserRepository;
        this.accessTokenService = accessTokenService;
        this.refreshTokenService = refreshTokenService;
        this.authUserMapper = authUserMapper;

        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
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
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = accessTokenService.generateToken(userDetails);
        String refreshToken = refreshTokenService.generateToken(userDetails);
        return new JwtResponse(accessToken, refreshToken, "Bearer");
    }

    public JwtResponse refreshToken(@NonNull RefreshTokenRequest request) {
        String token = request.token();
        if (accessTokenService.isValid(token)) {
            throw new GenericInvalidTokenException("Refresh Token invalid", 401);
        }
        String subject = accessTokenService.getSubject(token);
        UserDetails userDetails = loadUserByUsername(subject);
        String accessToken = accessTokenService.generateToken(userDetails);
        return new JwtResponse(accessToken, request.token(), "Bearer");
    }

    public AuthUser register(UserRegisterDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        AuthUser authUser = authUserMapper.fromRegisterDTO(dto);
        authUserRepository.save(authUser);
        AuthUserDTO authUserDTO = authUserMapper.toDTO(authUser);
        mailService.sendEmail(authUserDTO,);

    }
}
