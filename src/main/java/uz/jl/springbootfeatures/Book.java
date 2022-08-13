package uz.jl.springbootfeatures;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author "Elmurodov Javohir"
 * @since 13/08/22/12:37 (Saturday)
 * spring-boot-features/IntelliJ IDEA
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book implements Serializable {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private String author;
    private String genre;
    private LocalDateTime createdAt;
}
