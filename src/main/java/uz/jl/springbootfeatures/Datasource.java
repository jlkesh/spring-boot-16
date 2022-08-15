package uz.jl.springbootfeatures;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author "Elmurodov Javohir"
 * @since 15/08/22/12:15 (Monday)
 * spring-boot-features/IntelliJ IDEA
 */

@Component
//@Profile(value = "dev, test")
@ConditionalOnProperty(value = "log", name = "log", havingValue = "on")
public class Datasource {

}
