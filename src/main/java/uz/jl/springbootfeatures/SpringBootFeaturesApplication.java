package uz.jl.springbootfeatures;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringBootFeaturesApplication implements CommandLineRunner {

    private final TaskRepo taskRepo;

    public SpringBootFeaturesApplication(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFeaturesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        taskRepo.saveAll(List.of(
                new Task("Read A book"),
                new Task("Watch movie"),
                new Task("Watch cartoon")
        ));
    }
}

@RestController
@RequiredArgsConstructor
class TaskController {
    final TaskRepo taskRepo;

    @GetMapping
    public List<Task> getAll() {
        return taskRepo.findAll();
    }

    @GetMapping("/projection")
    public List<TaskDto> getAllDTO() {
        return taskRepo.findAllDTO();
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class TaskDto {
    private Integer id;
    private String title;
}

interface TaskRepo extends JpaRepository<Task, Integer> {

    @Query("""
            select new uz.jl.springbootfeatures.TaskDto(t.id, t.title) from Task t
            """)
    List<TaskDto> findAllDTO();
}

@Data
@Entity
@NoArgsConstructor
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Task(String title) {
        this.title = title;
    }
}