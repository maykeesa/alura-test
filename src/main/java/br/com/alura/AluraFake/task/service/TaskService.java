package br.com.alura.AluraFake.task.service;

import br.com.alura.AluraFake.task.dto.TaskDTO;

public interface TaskService {

    TaskDTO.Response.Tasks newOpenTextExercise(TaskDTO.Request.OpenText body);

    TaskDTO.Response.Tasks newSingleChoice(TaskDTO.Request.Choice body);

    TaskDTO.Response.Tasks newMultipleChoice(TaskDTO.Request.Choice body);
}
