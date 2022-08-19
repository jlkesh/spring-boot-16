package uz.jl.springbootfeatures;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition
public class SpringBootFeaturesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }


    CommandLineRunner runner() {
        return (args) -> {
        };
    }

}