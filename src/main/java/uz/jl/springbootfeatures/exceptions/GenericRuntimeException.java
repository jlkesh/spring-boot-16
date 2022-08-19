package uz.jl.springbootfeatures.exceptions;

import lombok.Getter;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/15:58 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
@Getter
public class GenericRuntimeException extends RuntimeException {
    protected final Integer statusCode;

    public GenericRuntimeException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
