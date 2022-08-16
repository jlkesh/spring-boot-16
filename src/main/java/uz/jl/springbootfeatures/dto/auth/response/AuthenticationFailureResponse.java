package uz.jl.springbootfeatures.dto.auth.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * @author "Elmurodov Javohir"
 * @since 16/08/22/11:14 (Tuesday)
 * spring-boot-features/IntelliJ IDEA
 */
@Data
@Builder
public class AuthenticationFailureResponse {
    private final String friendlyMessage;
    private final String developerMessage;
    private final String path;
    @Builder.Default
    private final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemDefaultZone()));

}
