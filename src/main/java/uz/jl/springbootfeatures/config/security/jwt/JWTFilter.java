package uz.jl.springbootfeatures.config.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.jl.springbootfeatures.services.AuthUserService;
import uz.jl.springbootfeatures.utils.JWTUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/12:01 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;
    private final AuthUserService authUserService;

    public JWTFilter(JWTUtils jwtUtils, AuthUserService authUserService) {
        this.jwtUtils = jwtUtils;
        this.authUserService = authUserService;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (secureUrl.apply(request.getRequestURI())) {
            try {
                String token = parseJwt(request);
                if (jwtUtils.isTokenValid(token)) {
                    String username = jwtUtils.getSubject(token);
                    final List<String> authorityValues = new ArrayList<>();
                    authorityValues.addAll(jwtUtils.getClaim(token, claims -> claims.get("roles", ArrayList.class)));
                    authorityValues.addAll(jwtUtils.getClaim(token, claims -> claims.get("permissions", ArrayList.class)));
                    List<SimpleGrantedAuthority> authorities = authorityValues.stream().map(SimpleGrantedAuthority::new).toList();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username, null, authorities);
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
            !List.of("/auth/login", "/auth/refresh").contains(path);

}

