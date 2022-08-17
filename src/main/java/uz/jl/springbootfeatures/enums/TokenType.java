package uz.jl.springbootfeatures.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @author "Elmurodov Javohir"
 * @since 17/08/22/11:49 (Wednesday)
 * spring-boot-features/IntelliJ IDEA
 */
@Getter
@AllArgsConstructor
public enum TokenType {
    ACCESS("U0RGVyQ0MzUzZnNkRyUkXiQlXjxERkhHPE9ZVUslJF4+SkhGR0pUUllVJV4=", 2, ChronoUnit.MINUTES),
    REFRESH("c2RGJF4lJiVeKERGR05NU0RGR0xFRktHREZMT0pUIylUIyQlXiMkJV9eJCVe", 10, ChronoUnit.DAYS);
    private final String secret;
    private final int amountToAdd;
    private final TemporalUnit unit;
}
