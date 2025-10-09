package br.com.alura.AluraFake.task.service;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.course.CourseDTO;
import br.com.alura.AluraFake.task.dto.TaskDTO;
import br.com.alura.AluraFake.task.enums.Type;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.repository.TaskRepository;
import br.com.alura.AluraFake.task.service.helper.TaskServiceHelper;
import br.com.alura.AluraFake.util.service.MapperServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final TaskServiceHelper taskServiceHelper;

    @Override
    public TaskDTO.Response.OpenText newOpenTextExercise(TaskDTO.Request.OpenText body) {
        Course course = taskServiceHelper.getCourseForNewTask(body.getCourseId());
        List<Task> tasks = taskRepository.findByCourseOrderByOrderAsc(course);

        Task newTask = MapperServiceUtil.convertObject(body, Task.class);
        newTask.setCourse(course);
        newTask.setType(Type.OPEN_TEXT);

        if(!tasks.isEmpty()){
            taskServiceHelper.validateIdenticalStatements(tasks, body.getStatement());
            tasks = taskServiceHelper.reorderTasks(newTask, tasks);
        }

        List<Task> savedTasks = taskRepository.saveAll(tasks);

        CourseDTO.Response.Course courseResponse = MapperServiceUtil
                .convertObject(course, CourseDTO.Response.Course.class);
        List<TaskDTO.Response.Task> tasksResponse = MapperServiceUtil
                .convertObjects(savedTasks, TaskDTO.Response.Task.class);

        return new TaskDTO.Response.OpenText(courseResponse, tasksResponse);
    }
}
