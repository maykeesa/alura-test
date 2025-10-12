package br.com.alura.AluraFake.task.service;

import br.com.alura.AluraFake.task.dto.TaskDTO;
import br.com.alura.AluraFake.task.enums.Type;
import br.com.alura.AluraFake.task.service.helper.TaskServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskServiceHelper taskServiceHelper;

    @Override
    public TaskDTO.Response.Tasks newOpenText(TaskDTO.Request.OpenText body) {
        return taskServiceHelper.createNewTask(body, Type.OPEN_TEXT);
    }

    @Override
    public TaskDTO.Response.Tasks newSingleChoice(TaskDTO.Request.SingleChoice body) {
        return taskServiceHelper.createNewTask(body, Type.SINGLE_CHOICE);
    }

    @Override
    public TaskDTO.Response.Tasks newMultipleChoice(TaskDTO.Request.MultipleChoice body) {
        return taskServiceHelper.createNewTask(body, Type.MULTIPLE_CHOICE);
    }
}
