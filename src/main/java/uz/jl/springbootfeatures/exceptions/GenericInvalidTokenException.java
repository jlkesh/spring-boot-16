package uz.jl.springbootfeatures.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/15:21 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */

@Getter
public class GenericInvalidTokenException extends GenericNotFoundException {
    public GenericInvalidTokenException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
