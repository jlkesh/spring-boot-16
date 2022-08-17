package uz.jl.springbootfeatures.configs.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.jl.springbootfeatures.services.auth.AuthUserService;
import uz.jl.springbootfeatures.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/09:29 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final AuthUserService authUserService;

    public JwtFilter(JwtUtils jwtUtils, AuthUserService authUserService) {
        this.jwtUtils = jwtUtils;
        this.authUserService = authUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (secureUrl.apply(request.getRequestURI())) {
            try {
                String token = parseJwt(request);
                if (jwtUtils.isTokenValid(token)) {
                    String username = jwtUtils.getSubject(token);
                    UserDetails userDetails = authUserService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private final static Function<String, Boolean> secureUrl = (path) ->
            !List.of("/access/token", "/refresh/token").contains(path);

}
