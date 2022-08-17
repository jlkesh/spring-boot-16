package uz.jl.springbootfeatures.configs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import uz.jl.springbootfeatures.dto.auth.response.AuthenticationFailureResponse;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/11:08 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */


@Component
@RequiredArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        ServletOutputStream outputStream = response.getOutputStream();
        AuthenticationFailureResponse failureResponse = AuthenticationFailureResponse.builder()
                .friendlyMessage(authException.getMessage())
                .developerMessage(authException.getMessage())
                .path(request.getRequestURI())
                .build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        objectMapper.writeValue(outputStream, failureResponse);

    }
}
