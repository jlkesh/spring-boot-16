package uz.jl.springbootfeatures;

import com.github.javafaker.Faker;
import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
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
public class SpringBootFeaturesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(20);
        eventMulticaster.setTaskExecutor(taskExecutor);
        return eventMulticaster;
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
    private final ApplicationEventPublisher publisher;


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
        publisher.publishEvent(new BookCreateEvent(book.getId(), book.getName(), book.getAuthor()));
    }


}


interface BookRepository extends JpaRepository<Book, String> {
}


@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
class BookController {

    private final BookService bookService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> books() {
        return bookService.getAll();
    }

    @PostMapping
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


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
class BookCreateEvent {
    private String id;
    private String title;
    private String author;
}


@Component
class BookEventHandler {

    @EventListener
    @SneakyThrows
    public void createBookListener(BookCreateEvent bookCreateEvent) {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("CREATING..............." + bookCreateEvent);
    }

}