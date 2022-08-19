package uz.jl.springbootfeatures.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.jl.springbootfeatures.exceptions.GenericNotFoundException;
import uz.jl.springbootfeatures.response.ApiErrorResponse;
import uz.jl.springbootfeatures.response.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/17:14 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(GenericNotFoundException.class)
    public ApiResponse<ApiErrorResponse> handle404(GenericNotFoundException e, HttpServletRequest request) {
        return new ApiResponse<>(ApiErrorResponse.builder()
                .friendlyMessage(e.getMessage())
                .developerMessage(Arrays.toString(e.getStackTrace()))
                .requestPath(request.getRequestURL().toString())
                .build(),
                e.getStatusCode());
    }

}
