package uz.jl.springbootfeatures;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jl.springbootfeatures.models.Book;

import java.util.List;

/**
 * @author "Elmurodov Javohir"
 * @since 24/08/22/10:53 (Wednesday)
 * spring-boot-features/IntelliJ IDEA
 */

@RestController
public class JacksonTestController {

    @GetMapping("/non_null")
    public List<Book> getObjects() {
        return List.of(new Book(null, "Mehrobdan Chayon"), new Book(null, null));
    }

}