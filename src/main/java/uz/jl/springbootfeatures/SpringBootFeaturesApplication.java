package uz.jl.springbootfeatures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;


/**
 * docs for @see <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#scheduling">docs</a>
 */

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SpringBootFeaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

//    @Scheduled(initialDelay = 3, fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
//    public void timeIs() {
//        System.out.printf("Time is %s%n", LocalDateTime.now());
//    }

//    @Scheduled( cron = "* */10 8-10 * FRI *")
//    public void cronExample() {
//        System.out.printf("Cron Tap : %s%n", LocalDateTime.now());
//    }

    @Scheduled( cron = "10-30/1 * * * JAN-MAY MON-FRI")
    public void cronExample() {
        System.out.printf("Cron Tap : %s%n", LocalDateTime.now());
    }
    @Scheduled( cron = "@annually")
    public void everyYear() {
        System.out.printf("Cron Tap : %s%n", LocalDateTime.now());
    }


}
