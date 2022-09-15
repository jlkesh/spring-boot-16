package uz.jl.springbootfeatures;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@OpenAPIDefinition
public class SpringBootFeaturesApplication {


    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Book> redisTemplate() {
        RedisTemplate<String, Book> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }


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
        return repository.getAll();
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        log.info("CREATE BOOK api called");
        return repository.save(book);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable String id) {
        log.info("CREATE BOOK api called");
        return repository.delete(id);
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


@Repository
class BookRepository {

    public static final String BOOK_KEY = "books";
    final RedisTemplate<String, Book> redisTemplate;
    final HashOperations<String, String, Book> opsForHash;


    BookRepository(RedisTemplate<String, Book> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.opsForHash = redisTemplate.opsForHash();
    }


    public Book save(@NonNull Book book) {
        book.setId(UUID.randomUUID().toString());
        opsForHash.put(BOOK_KEY, book.getId(), book);
        return book;
    }

    public List<Book> getAll() {
        return opsForHash.entries(BOOK_KEY).values().stream().toList();
    }

    public Long delete(@NonNull String id) {
        return opsForHash.delete(BOOK_KEY, id);
    }


}


