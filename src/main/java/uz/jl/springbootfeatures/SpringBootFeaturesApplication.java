package uz.jl.springbootfeatures;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
@OpenAPIDefinition
@EnableConfigurationProperties({
        AppConfig.class
})
@RequiredArgsConstructor
public class SpringBootFeaturesApplication {
    private final AppConfig appConfig;

//    @Value("${application.name}")
//    private String profileName;
//
//    @Value("${programming.languages}")
//    private List<String> progLangs;

    @Value("#{${users}}")
    private Map<String, String> users;

    @Value("#{'Hello world'.split(' ')}")
    private List<String> words;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

    @GetMapping
    public String message() {
        return words + "Profile is -> " + "profileName" + ";" +
                "progLangs" + ";" + appConfig.getJobs() + ";" +
                users;
    }

}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Job {
    private Integer id;
    private String title;
    private String description;
}

@Configuration
@ConfigurationProperties(prefix = "app")
class AppConfig {
    private List<Job> jobs;

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}