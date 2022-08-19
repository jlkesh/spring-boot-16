package uz.jl.springbootfeatures.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * @author "Elmurodov Javohir"
 * @since 19/08/22/10:41 (Friday)
 * spring-boot-features/IntelliJ IDEA
 */


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ApiErrorResponse {
    private String friendlyMessage;
    private String developerMessage;
    @Builder.Default
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
    private String requestPath;
}
