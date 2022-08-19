package uz.jl.springbootfeatures.exceptions;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/15:57 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */
public class GenericNotFoundException extends GenericRuntimeException {
    public GenericNotFoundException(String message, Integer statusCode) {
        super(message, statusCode);
    }
}
