package uz.jl.springbootfeatures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class SpringBootFeaturesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

}