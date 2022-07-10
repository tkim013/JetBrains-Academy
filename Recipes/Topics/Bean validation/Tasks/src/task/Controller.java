package task;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.util.*;
import java.util.concurrent.*;

@RestController
public class Controller {
    final List<Task> tempDb = new CopyOnWriteArrayList<>();

    @PostMapping("/tasks")
    public void addTask(@Valid @RequestBody Task task) {
        tempDb.add(task);
    }
}

class Task {
    @NotBlank
    @Size(min = 1, max = 50)
    String name;
    @NotBlank
    @Size(min = 1, max = 200)
    String description;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
