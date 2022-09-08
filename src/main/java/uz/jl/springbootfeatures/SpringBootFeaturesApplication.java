package uz.jl.springbootfeatures;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringBootFeaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

    //    @Bean
    public CommandLineRunner runner(UserRepo userRepo, TaskRepo taskRepo) {
        return (args) -> {

            Users user1 = Users.builder().username("john").password("123").build();
            Users user2 = Users.builder().username("muhammad").password("123").build();
            userRepo.save(user1);
            userRepo.save(user2);
            Task task1 = Task.builder().title("Read A book").users(user1).build();
            Task task2 = Task.builder().title("The Lord of the rings").users(user1).build();

            Task task3 = Task.builder().title("Dream About Future").users(user2).build();
            Task task4 = Task.builder().title("Watch movie(Game od thrones)").users(user2).build();
            taskRepo.saveAll(List.of(task1, task2, task3, task4));
        };
    }

}

@RestController
@RequiredArgsConstructor
class ApiController {
    final UserRepo userRepo;
    final TaskRepo taskRepo;
    final UserMapper userMapper;


    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userRepo.findAllUsersWithUserDTO();
    }

    @GetMapping("/userst")
    public List<UserDTO> getAllUserst() {
        List<Users> all = userRepo.findAll();
        System.out.println("all requesting");
        return userMapper.toDto(all);
//        return all;
    }

//    private List<UserDTO> toDto(List<Users> users) {
//        return users.stream().map(user -> new UserDTO(user.getId(), user.getUsername())).toList();
//    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Task> tasks;
}

interface UserRepo extends JpaRepository<Users, Integer> {

    @Query("select id, username from Users")
    List<UserDTO> findAllUsersWithUserDTO();
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
}

interface TaskRepo extends JpaRepository<Task, Integer> {
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class UserDTO {
    private Integer id;
    private String username;
    private List<Task> tasks;
}

@Mapper(componentModel = "spring")
interface UserMapper {
    UserDTO toDto(Users users);

    @Mapping(source = "tasks", target = "tasks", ignore = true)
    List<UserDTO> toDto(List<Users> users);
}