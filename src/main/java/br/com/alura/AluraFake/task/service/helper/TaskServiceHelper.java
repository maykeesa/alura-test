package br.com.alura.AluraFake.task.service.helper;

import br.com.alura.AluraFake.config.exception.exceptions.ServiceException;
import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseDTO;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.dto.TaskDTO;
import br.com.alura.AluraFake.task.enums.Type;
import br.com.alura.AluraFake.task.model.Option;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.util.service.MapperServiceUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskServiceHelper {

    private final CourseRepository courseRepository;

    public Task createNewTask(TaskDTO.Request.Task body, Course course, Type typeTask) {
        Task newTask = MapperServiceUtil.convertObject(body, Task.class);
        newTask.setCourse(course);
        newTask.setType(typeTask);

        if (!Type.OPEN_TEXT.equals(typeTask)) {
            newTask.getOptions().forEach(option -> option.setTask(newTask));
        }

        return newTask;
    }

    public TaskDTO.Response.Tasks createResponseTask(Course course, List<Task> tasks) {
        CourseDTO.Response.Course courseResponse = MapperServiceUtil
                .convertObject(course, CourseDTO.Response.Course.class);
        List<TaskDTO.Response.Task> tasksResponse = MapperServiceUtil
                .convertObjects(tasks, TaskDTO.Response.Task.class);

        return new TaskDTO.Response.Tasks(courseResponse, tasksResponse);
    }

    public Course getCourseForNewTask(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id %s not found."
                        .formatted(courseId)));

        if (!course.getStatus().equals(Status.BUILDING)) {
            throw new ServiceException("Cannot add a new task, course is not in BUILDING status.");
        }

        return course;
    }

    public void validateIdenticalStatements(List<Task> tasks, String statementNewTask) {
        tasks.forEach(task -> {
            if (task.getStatement().equals(statementNewTask))
                throw new ServiceException("There is already a task with the same statement.");
        });
    }

    public void validateOrderDesired(Integer sizeTasks, Integer orderNewTask) {
        if ((sizeTasks + 1) < orderNewTask) {
            throw new ServiceException("Order exceeds the maximum allowed position (%d)."
                    .formatted(sizeTasks + 1));
        }
    }

    public void validateQuantitiesCorrectAnswer(List<Option> options, Integer numberCorrectAnswers) {
        int amountTruth = 0;

        for(Option option: options){
            if(option.getIsCorrect())
                amountTruth++;

            if(amountTruth > numberCorrectAnswers)
                throw new ServiceException("There can be no more than one correct option in the task.");
        }

        if(amountTruth == 0)
            throw new ServiceException("The task must have a correct alternative.");
    }

    public void validateIdenticalOptionsAndStatement(Task newTask) {
        Set<String> allOption = new HashSet<>();

        newTask.getOptions().forEach(option -> {
            if (!allOption.add(option.getOption()))
                throw new ServiceException("There is at least one duplicate option for this: %s"
                        .formatted(option.getOption()));

            if(newTask.getStatement().equals(option.getOption()))
                throw new ServiceException("The alternatives cannot be the same as the statement.");
        });
    }

    public List<Task> setNewPositionTasks(List<Task> tasks, Task newTask, Integer orderNewTask) {
        tasks.forEach(t -> {
            {
                if (t.getOrder() >= orderNewTask) {
                    t.setOrder(t.getOrder() + 1);
                }
            }
        });

        setPositionNewTask(tasks, newTask, orderNewTask);

        return tasks;
    }

    private void setPositionNewTask(List<Task> tasks, Task newTask, Integer orderNewTask) {
        if ((tasks.size() + 1) == orderNewTask) {
            tasks.add(newTask);
        }

        tasks.add(orderNewTask - 1, newTask);
    }
}
