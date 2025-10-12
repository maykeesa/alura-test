package br.com.alura.AluraFake.task.service;

import br.com.alura.AluraFake.task.dto.TaskDTO;

public interface TaskService {

    TaskDTO.Response.Tasks newOpenText(TaskDTO.Request.OpenText body);

    TaskDTO.Response.Tasks newSingleChoice(TaskDTO.Request.SingleChoice body);

    TaskDTO.Response.Tasks newMultipleChoice(TaskDTO.Request.MultipleChoice body);
}
