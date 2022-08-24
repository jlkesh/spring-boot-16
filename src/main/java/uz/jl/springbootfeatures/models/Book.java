package uz.jl.springbootfeatures.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * @author "Elmurodov Javohir"
 * @since 24/08/22/10:35 (Wednesday)
 * spring-boot-features/IntelliJ IDEA
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @JsonProperty(value = "PageCount")
    private Integer page;
    private String name;

    //@JsonIgnore
    private String secretId = UUID.randomUUID().toString();

    public Book(Integer page, String name) {
        this.page = page;
        this.name = name;
        this.secretId = UUID.randomUUID().toString();
    }
}
