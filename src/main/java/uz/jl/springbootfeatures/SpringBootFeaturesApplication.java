package uz.jl.springbootfeatures;

import com.github.javafaker.Faker;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootApplication
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
@Slf4j
@RequiredArgsConstructor
class BookService {
    private final BookRepository bookRepository;
//    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<Book> getAll() {
        log.info("Get All Method called");
        return bookRepository.findAll();
    }


    public Book getOne(String id) {
        log.info("Getting One Book by id : {} ", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found by id : %s".formatted(id)));
    }

    public void delete(String id) {
        log.info("Deleting a book by id : {} ", id);
        bookRepository.deleteById(id);
    }

    public Book update(BookUpdateDTO dto) {
        Book book = getOne(dto.getId());
        log.info("Updating Book => {}\nwith => {} ", book, dto);
        if (dto.getName() != null)
            book.setName(dto.getName());
        if (dto.getAuthor() != null)
            book.setAuthor(dto.getAuthor());
        if (dto.getGenre() != null)
            book.setGenre(dto.getGenre());
        return bookRepository.save(book);
    }

    public void create(Book book) {
        log.info("Creating Book with => {} ", book);
        bookRepository.save(book);
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

@Plugin(
        name = "TelegramBotAppender",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
class TelegramBotAppender extends AbstractAppender {


    protected TelegramBotAppender(String name, Filter filter) {
        super(name, filter, PatternLayout.newBuilder().build(), true, null);
    }


    @PluginFactory
    public static TelegramBotAppender createAppender(@PluginAttribute("name") String name, @PluginElement("Filter") Filter filter) {
        return new TelegramBotAppender(name, filter);
    }

    @Override
    public void append(LogEvent event) {
        System.out.println("------------------------------------------------------------");
        System.out.println(event.toString());
        System.out.println("------------------------------------------------------------");
    }
}

