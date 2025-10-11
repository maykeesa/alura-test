package br.com.alura.AluraFake.task.service;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.dto.TaskDTO;
import br.com.alura.AluraFake.task.enums.Type;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.task.repository.TaskRepository;
import br.com.alura.AluraFake.task.service.helper.TaskServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final TaskServiceHelper taskServiceHelper;

    @Override
    public TaskDTO.Response.Tasks newOpenTextExercise(TaskDTO.Request.OpenText body) {
        Course course = taskServiceHelper.getCourseForNewTask(body.getCourseId());
        List<Task> tasks = taskRepository.findByCourseOrderByOrderAsc(course);
        Task newTask = taskServiceHelper.createNewTask(body, course, Type.OPEN_TEXT);

        taskServiceHelper.validateOrderDesired(tasks.size(), newTask.getOrder());

        if(!tasks.isEmpty()){
            taskServiceHelper.validateIdenticalStatements(tasks, body.getStatement());
            tasks = taskServiceHelper.setNewPositionTasks(tasks, newTask, newTask.getOrder());
        }else{
            tasks.add(newTask);
        }

        List<Task> savedTasks = taskRepository.saveAll(tasks);
        return taskServiceHelper.createResponseTask(course, savedTasks);
    }

    @Override
    public TaskDTO.Response.Tasks newSingleChoice(TaskDTO.Request.Choice body) {
        Course course = taskServiceHelper.getCourseForNewTask(body.getCourseId());
        List<Task> tasks = taskRepository.findByCourseOrderByOrderAsc(course);
        Task newTask = taskServiceHelper.createNewTask(body, course, Type.SINGLE_CHOICE);

        taskServiceHelper.validateOrderDesired(tasks.size(), newTask.getOrder());
        taskServiceHelper.validateQuantitiesCorrectAnswer(newTask.getOptions(), 1);
        taskServiceHelper.validateIdenticalOptionsAndStatement(newTask);

        if(!tasks.isEmpty()){
            taskServiceHelper.validateIdenticalStatements(tasks, body.getStatement());
            tasks = taskServiceHelper.setNewPositionTasks(tasks, newTask, newTask.getOrder());
        }else{
            tasks.add(newTask);
        }

        List<Task> savedTasks = taskRepository.saveAll(tasks);
        return taskServiceHelper.createResponseTask(course, savedTasks);
    }

    @Override
    public TaskDTO.Response.Tasks newMultipleChoice(TaskDTO.Request.Choice body) {
        return null;
    }
}
