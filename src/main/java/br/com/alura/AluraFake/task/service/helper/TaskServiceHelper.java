package br.com.alura.AluraFake.task.service.helper;

import br.com.alura.AluraFake.config.exception.exceptions.ServiceException;
import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.model.Task;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceHelper {

    private final CourseRepository courseRepository;

    public Course getCourseForNewTask(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id %s not found."
                        .formatted(courseId)));

        if (!course.getStatus().equals(Status.BUILDING)) {
            throw new ServiceException("Cannot add a new task, course is not in BUILDING status.");
        }

        return course;
    }

    public List<Task> reorderTasks(Task newTask, List<Task> tasks) {
        Integer orderNewTask = newTask.getOrder();
        int size = tasks.size();

        if ((size + 1) < orderNewTask) {
            throw new ServiceException("Order exceeds the maximum allowed position (%d).".formatted(size + 1));
        }

        return setNewPositionTasks(tasks, newTask, orderNewTask);
    }

    public void validateIdenticalStatements(List<Task> tasks, String statementNewTask){
        tasks.forEach(task -> {
            if(task.getStatement().equals(statementNewTask))
                throw new ServiceException("There is already a task with the same statement.");
        });
    }

    private List<Task> setNewPositionTasks(List<Task> tasks, Task newTask, Integer orderNewTask){
        tasks.forEach(t -> {{
            if(t.getOrder() >= orderNewTask){
                t.setOrder(t.getOrder() + 1);
            }
        }});

        setPositionNewTask(tasks, newTask, orderNewTask);

        return tasks;
    }

    private void setPositionNewTask(List<Task> tasks, Task newTask, Integer orderNewTask){
        if((tasks.size() + 1) == orderNewTask){
            tasks.add(newTask);
        }

        tasks.add(orderNewTask - 1, newTask);
    }
}
