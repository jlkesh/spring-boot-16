package uz.jl.springbootfeatures;

import com.github.javafaker.Faker;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.*;
import org.springdoc.api.annotations.ParameterObject;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties({
        OpenApiConfigurer.class
})
public class SpringBootFeaturesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

    //    @Bean
    CommandLineRunner runner(BookRepository bookRepository) {
        return (args) -> {
            com.github.javafaker.Book book = new Faker().book();
            List<Book> books = IntStream.rangeClosed(1, 1000).mapToObj(i -> Book.builder()
                    .name(book.title())
                    .author(book.author())
                    .genre(book.genre())
                    .build()).toList();
            bookRepository.saveAll(books);
        };
    }

}


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Book {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private String author;
    private String genre;
    private LocalDateTime createdAt;
}


@Service
@RequiredArgsConstructor
class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @SneakyThrows
    @Cacheable(cacheNames = "book", key = "#id")
    public Book getOne(String id) {
        // TODO: 13/08/22 some logic here that takes around 3 4 seconds
        TimeUnit.SECONDS.sleep(2);
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found by id : %s".formatted(id)));
    }

    @CacheEvict(cacheNames = "book", key = "#id")
    public void delete(String id) {
        bookRepository.deleteById(id);
    }

    @CachePut(cacheNames = "book", key = "#dto.id")
    public Book update(BookUpdateDTO dto) {
        Book book = getOne(dto.getId());
        if (dto.getName() != null)
            book.setName(dto.getName());
        if (dto.getAuthor() != null)
            book.setAuthor(dto.getAuthor());
        if (dto.getGenre() != null)
            book.setGenre(dto.getGenre());
        return bookRepository.save(book);
    }

    public void create(Book book) {
        bookRepository.save(book);
    }

}


interface BookRepository extends JpaRepository<Book, String> {
}


@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "All book operations will be done by this controller")
class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    public List<Book> books(@ParameterObject BookCriteria bookCriteria) {
        return bookService.getAll();
    }

    @PostMapping
    @RouterOperation(
            operation = @Operation(operationId = "create a book",
                    summary = "This api for creating book",
                    tags = {
                            "Create a book"
                    },
                    parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "dto", description = "BookCreateDTO")
                    },
                    responses = {
                            @ApiResponse(responseCode = "400", description = "Bad request"),
                            @ApiResponse(responseCode = "201", description = "Created"),
                            @ApiResponse(responseCode = "404", description = "Path not found")
                    }))
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Book book) {
        bookService.create(book);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book getOne(@PathVariable String id) {
        return bookService.getOne(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody BookUpdateDTO dto) {
        bookService.update(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        bookService.delete(id);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class BookUpdateDTO {
    private String id;
    private String name;
    private String author;
    private String genre;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class BookCriteria {
    private boolean deleted;
    private String name;
    private String author;
    private Genre genre;

    public static enum Genre {
        ROMANCE, DRAMA, SCI_FI
    }
}


@Configuration
@ConfigurationProperties(prefix = "api.documentation")
@OpenAPIDefinition
@Data
class OpenApiConfigurer {

    private Info info;
    private Licence licence;
    private External external;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(getInfo().license(licence())).externalDocs(externalDocumentation());
    }

    private io.swagger.v3.oas.models.info.Info getInfo() {
        return new io.swagger.v3.oas.models.info.Info()
                .title(info.getTitle())
                .description(info.getDescription())
                .version(info.getVersion());
    }

    private io.swagger.v3.oas.models.info.License licence() {
        return new io.swagger.v3.oas.models.info.License()
                .name(licence.getName())
                .url(licence.getUrl());
    }

    private io.swagger.v3.oas.models.ExternalDocumentation externalDocumentation() {
        return new io.swagger.v3.oas.models.ExternalDocumentation()
                .description(external.getDescription())
                .url(external.getUrl());
    }

    @Getter
    @Setter
    public static class Info {
        private String title;
        private String description;
        private String version;
    }

    @Getter
    @Setter
    public static class Licence {
        private String name;
        private String url;

    }

    @Getter
    @Setter
    public static class External {
        private String description;
        private String url;
    }

}
