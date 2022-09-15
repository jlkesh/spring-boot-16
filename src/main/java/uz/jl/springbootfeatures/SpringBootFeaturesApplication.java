package uz.jl.springbootfeatures;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

@SpringBootApplication
@OpenAPIDefinition
@EnableCaching
public class SpringBootFeaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

}


@RestController
@RequiredArgsConstructor
@Slf4j
class BookController {
    final BookRepository repository;

    @GetMapping
    public List<Book> getAll() {
        log.info("Get All BOOKS api called");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(cacheNames = "book", key = "#id")
    public Book getAll(@PathVariable String id) {
        log.info("GET ONE BOOK api called");
        Supplier<RuntimeException> supplier = () -> new RuntimeException("Book not found");
        return repository.findById(id).orElseThrow(supplier);
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        log.info("CREATE BOOK api called");
        return repository.save(book);
    }

}


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Book implements Serializable {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    private String id;

    private String title;

    private String author;
}


interface BookRepository extends JpaRepository<Book, String> {
}