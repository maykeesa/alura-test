package br.com.alura.AluraFake.task.service.helper;

import br.com.alura.AluraFake.config.exception.exceptions.ServiceException;
import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.dto.CourseDTO;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.course.enums.Status;
import br.com.alura.AluraFake.task.dto.TaskDTO;
import br.com.alura.AluraFake.task.enums.Type;
import br.com.alura.AluraFake.task.model.Option;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.repository.TaskRepository;
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
    private final TaskRepository taskRepository;

    public TaskDTO.Response.Tasks createNewTask(TaskDTO.Request.Task body, Type type) {
        Course course = getCourseForNewTask(body.getCourseId());
        List<Task> tasks = taskRepository.findByCourseOrderByOrderAsc(course);
        Task newTask = createTask(body, course, type);

        validateOrderDesired(tasks.size(), newTask.getOrder());

        if (type.equals(Type.SINGLE_CHOICE) || type.equals(Type.MULTIPLE_CHOICE)) {
            validateQuantitiesCorrectAnswer(newTask.getOptions(), type);
            validateIdenticalOptionsAndStatement(newTask);
        }

        if (!tasks.isEmpty()) {
            tasks.forEach(task -> {
                validateIdenticalStatements(task, newTask.getStatement());
                setNewPositionTasks(task, newTask);
            });

            tasks.add(newTask.getOrder() - 1, newTask);
        } else {
            tasks.add(newTask);
        }

        List<Task> savedTasks = taskRepository.saveAll(tasks);
        return createResponseTask(course, savedTasks);
    }

    private Task createTask(TaskDTO.Request.Task body, Course course, Type typeTask) {
        Task task = MapperServiceUtil.convertObject(body, Task.class);
        task.setCourse(course);
        task.setType(typeTask);

        if (!Type.OPEN_TEXT.equals(typeTask)) {
            task.getOptions().forEach(option -> option.setTask(task));
        }

        return task;
    }

    private TaskDTO.Response.Tasks createResponseTask(Course course, List<Task> tasks) {
        CourseDTO.Response.Course courseResponse = MapperServiceUtil
                .convertObject(course, CourseDTO.Response.Course.class);
        List<TaskDTO.Response.Task> tasksResponse = MapperServiceUtil
                .convertObjects(tasks, TaskDTO.Response.Task.class);

        return new TaskDTO.Response.Tasks(courseResponse, tasksResponse);
    }

    private Course getCourseForNewTask(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id %s not found."
                        .formatted(courseId)));

        if (!course.getStatus().equals(Status.BUILDING)) {
            throw new ServiceException("Cannot add a new task, course is not in BUILDING status.");
        }

        return course;
    }

    private void validateIdenticalStatements(Task task, String statementNewTask) {
        if (task.getStatement().equals(statementNewTask))
            throw new ServiceException("There is already a task with the same statement.");
    }

    private void validateOrderDesired(Integer sizeTasks, Integer orderNewTask) {
        if ((sizeTasks + 1) < orderNewTask) {
            throw new ServiceException("Order exceeds the maximum allowed position (%d)."
                    .formatted(sizeTasks + 1));
        }
    }

    private void validateQuantitiesCorrectAnswer(List<Option> options, Type type) {
        int amountTruth = 0;
        int numberCorrectAnswers = type.equals(Type.SINGLE_CHOICE) ? 1 : 2;

        for (Option option : options) {
            if (option.getIsCorrect())
                amountTruth++;
        }

        if (amountTruth == 0)
            throw new ServiceException("The task must have at least one correct alternative.");

        if (type.equals(Type.SINGLE_CHOICE) && amountTruth > numberCorrectAnswers) {
            throw new ServiceException("There should only be one correct option.");
        }

        if (type.equals(Type.MULTIPLE_CHOICE) && amountTruth < numberCorrectAnswers) {
            throw new ServiceException("There must be at least two correct options.");
        }
    }

    private void validateIdenticalOptionsAndStatement(Task newTask) {
        Set<String> allOption = new HashSet<>();

        newTask.getOptions().forEach(option -> {
            if (!allOption.add(option.getOption()))
                throw new ServiceException("There is at least one duplicate option for this: %s"
                        .formatted(option.getOption()));

            if (newTask.getStatement().equals(option.getOption()))
                throw new ServiceException("The alternatives cannot be the same as the statement.");
        });
    }

    private void setNewPositionTasks(Task task, Task newTask) {
        if (task.getOrder() >= newTask.getOrder()) {
            task.setOrder(task.getOrder() + 1);
        }
    }
}
