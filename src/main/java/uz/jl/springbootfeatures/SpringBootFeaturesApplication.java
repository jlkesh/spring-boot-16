package uz.jl.springbootfeatures;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@SpringBootApplication
@OpenAPIDefinition
public class SpringBootFeaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

}

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
class Person {
    private Long id;
    private String firstName;
    private String secondName;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dateOfBirth;
    private String profession;
}

@Getter
@Setter
@Builder
class PersonEntityModel extends EntityModel<Person> {
    private final Person person;

    public PersonEntityModel(Person person) {
        this.person = person;
//        add(Link.of("/person/" + person.getId(), "getOnePerson"));
        add(linkTo(PersonController.class).withRel("people"));
        add(linkTo(methodOn(PersonController.class).getAll()).withRel("all-person"));
        add(linkTo(methodOn(PersonController.class).getPersonById(person.getId())).withSelfRel());
    }
}

@RestController
@RequestMapping("/person")
class PersonController {

    List<Person> people = new ArrayList<>() {{
        add(Person.builder()
                .id(1L)
                .firstName("Aslonbek")
                .secondName("Hazratov")
                .profession("Developer JAva backend")
                .dateOfBirth(LocalDateTime.of(1995, 11, 30, 0, 0, 0))
                .build());
        add(Person.builder()
                .id(2L)
                .firstName("Shoxruh")
                .secondName("Berdimurodov")
                .profession("no idea")
                .dateOfBirth(LocalDateTime.of(2001, 8, 28, 0, 0, 0))
                .build());
    }};

    @GetMapping("/{id}")
    public ResponseEntity<PersonEntityModel> getPersonById(@PathVariable long id) {
        Person neededPerson = people.stream().filter(person -> person.getId().equals(id)).findFirst().get();
        return ResponseEntity.ok(new PersonEntityModel(neededPerson));
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<Person>> getAll() {
        CollectionModel<Person> collectionModel = CollectionModel.of(people);
        collectionModel.add(linkTo(methodOn(PersonController.class).getAll()).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }

}