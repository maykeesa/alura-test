package br.com.alura.AluraFake.task.service.helper;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.task.dto.TaskDTO;
import br.com.alura.AluraFake.task.enums.Type;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.repository.TaskRepository;
import br.com.alura.AluraFake.user.Role;
import br.com.alura.AluraFake.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceHelperTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private TaskServiceHelper taskServiceHelper;

    @Test
    @DisplayName("You should successfully create an open text task.")
    public void createOpenTextNewTask() {
        TaskDTO.Request.OpenText body = requestOpenText();

        when(courseRepository.findById(body.getCourseId())).thenReturn(Optional.of(getCourse()));
        when(taskRepository.findByCourseOrderByOrderAsc(any())).thenReturn(getTasks());
        when(taskRepository.saveAll(any())).thenAnswer(inv -> inv.getArgument(0));

        TaskDTO.Response.Tasks response = taskServiceHelper.createNewTask(body, Type.OPEN_TEXT);

        assertNotNull(response);
        assertNotNull(response.getTasks());
        assertFalse(response.getTasks().isEmpty());
        verify(taskRepository, times(1)).saveAll(any());
    }

    @Test
    @DisplayName("Should successfully create a single choice task.")
    public void createSingleChoiceNewTask() {
        TaskDTO.Request.SingleChoice body = requestSingleChoice();

        when(courseRepository.findById(body.getCourseId())).thenReturn(Optional.of(getCourse()));
        when(taskRepository.findByCourseOrderByOrderAsc(any())).thenReturn(new ArrayList<>());
        when(taskRepository.saveAll(any())).thenAnswer(inv -> inv.getArgument(0));

        TaskDTO.Response.Tasks response = taskServiceHelper.createNewTask(body, Type.SINGLE_CHOICE);

        assertNotNull(response);
        assertNotNull(response.getTasks());
        assertFalse(response.getTasks().isEmpty());
        verify(taskRepository, times(1)).saveAll(any());
    }

    @Test
    @DisplayName("Should successfully create a multiple choice task.")
    public void createMultipleChoiceNewTask() {
        TaskDTO.Request.MultipleChoice body = requestMultipleChoice();

        when(courseRepository.findById(body.getCourseId())).thenReturn(Optional.of(getCourse()));
        when(taskRepository.findByCourseOrderByOrderAsc(any())).thenReturn(new ArrayList<>());
        when(taskRepository.saveAll(any())).thenAnswer(inv -> inv.getArgument(0));

        TaskDTO.Response.Tasks response = taskServiceHelper.createNewTask(body, Type.MULTIPLE_CHOICE);

        assertNotNull(response);
        assertNotNull(response.getTasks());
        assertFalse(response.getTasks().isEmpty());
        verify(taskRepository, times(1)).saveAll(any());
    }

    public static TaskDTO.Request.OpenText requestOpenText(){
        TaskDTO.Request.OpenText body = new TaskDTO.Request.OpenText();
        body.setCourseId(1L);
        body.setStatement("What is POO? And what are its pillars?");
        body.setOrder(1);

        return body;
    }

    public static TaskDTO.Request.SingleChoice requestSingleChoice(){
        TaskDTO.Request.SingleChoice body = new TaskDTO.Request.SingleChoice();
        body.setCourseId(1L);
        body.setStatement("What is the best language?");
        body.setOrder(1);

        TaskDTO.Request.Options first = new TaskDTO.Request.Options();
        first.setOption("Java");
        first.setIsCorrect(false);

        TaskDTO.Request.Options second = new TaskDTO.Request.Options();
        second.setOption("The one who pays the bills");
        second.setIsCorrect(true);

        TaskDTO.Request.Options third = new TaskDTO.Request.Options();
        third.setOption("Python");
        third.setIsCorrect(false);

        List<TaskDTO.Request.Options> options = List.of(first, second, third);
        body.setOptions(options);

        return body;
    }

    public static TaskDTO.Request.MultipleChoice requestMultipleChoice(){
        TaskDTO.Request.MultipleChoice body = new TaskDTO.Request.MultipleChoice();
        body.setCourseId(1L);
        body.setStatement("What is the best language?");
        body.setOrder(1);

        TaskDTO.Request.Options first = new TaskDTO.Request.Options();
        first.setOption("Java");
        first.setIsCorrect(true);

        TaskDTO.Request.Options second = new TaskDTO.Request.Options();
        second.setOption("The one who pays the bills");
        second.setIsCorrect(true);

        TaskDTO.Request.Options third = new TaskDTO.Request.Options();
        third.setOption("Python");
        third.setIsCorrect(false);

        List<TaskDTO.Request.Options> options = List.of(first, second, third);
        body.setOptions(options);

        return body;
    }

    public static Course getCourse(){
        User user = getUser();

        Course course = new Course();
        course.setId(1L);
        course.setStatus(Status.BUILDING);
        course.setInstructor(user);
        course.setTitle("Introduction to Java");
        course.setDescription("Learn the basics of Java programming, syntax, and object-oriented concepts.");
        course.setPublishedAt(null);
        course.setCreatedAt(LocalDateTime.now());

        return course;
    }

    public static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setRole(Role.INSTRUCTOR);
        user.setPassword("password123");
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public static ArrayList<Task> getTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        Course course = getCourse();

        Task task1 = new Task();
        task1.setId(1L);
        task1.setStatement("What is Object-Oriented Programming?");
        task1.setOrder(1);
        task1.setType(Type.OPEN_TEXT);
        task1.setCourse(course);
        task1.setCreatedAt(LocalDateTime.now());
        task1.setUpdatedAt(LocalDateTime.now());

        Task task2 = new Task();
        task2.setId(2L);
        task2.setStatement("Explain the concept of inheritance in OOP.");
        task2.setOrder(2);
        task2.setType(Type.OPEN_TEXT);
        task2.setCourse(course);
        task2.setCreatedAt(LocalDateTime.now());
        task2.setUpdatedAt(LocalDateTime.now());

        tasks.add(task1);
        tasks.add(task2);

        return tasks;
    }
}