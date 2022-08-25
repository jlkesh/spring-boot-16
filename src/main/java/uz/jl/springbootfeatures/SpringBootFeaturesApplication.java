package uz.jl.springbootfeatures;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@RestController
@OpenAPIDefinition
public class SpringBootFeaturesApplication {
    private final static List<Book> books = new ArrayList<>() {{
        add(Book.builder().name("Men Va Ozim").description("Resume").build());
        add(Book.builder().name("Shaytanat").description("91-yil*****").build());
    }};

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

    @GetMapping(value = {"", "/book"},
            produces = {"application/json", "application/xml"}
    )
    public List<Book> books() {
        return books;
    }

    @PostMapping(value = {"", "/book"},
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {"application/json", "application/xml"}
    )
    public Book create(@RequestBody BookCreateDTO dto) {
        Book book = Book.builder().name(dto.getName()).description(dto.getDescription()).build();
        books.add(book);
        return book;
    }

}


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Book {
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private String description;
}

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class BookCreateDTO {
    private String name;
    private String description;
}
