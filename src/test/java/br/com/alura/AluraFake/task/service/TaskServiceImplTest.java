package br.com.alura.AluraFake.task.service;

import br.com.alura.AluraFake.task.dto.TaskDTO;
import br.com.alura.AluraFake.task.enums.Type;
import br.com.alura.AluraFake.task.service.helper.TaskServiceHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskServiceHelper taskServiceHelper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    @DisplayName("You should create an open text task.")
    void newOpenTextExercise() {
        TaskDTO.Request.OpenText openText = mock(TaskDTO.Request.OpenText.class);

        when(taskServiceHelper.createNewTask(openText, Type.OPEN_TEXT))
                .thenReturn(new TaskDTO.Response.Tasks());

        taskService.newOpenText(openText);

        verify(taskServiceHelper, times(1)).createNewTask(any(), any());
    }

    @Test
    @DisplayName("You should create an single choice task.")
    void newSingleChoice() {
        TaskDTO.Request.SingleChoice singleChoice = mock(TaskDTO.Request.SingleChoice.class);

        when(taskServiceHelper.createNewTask(singleChoice, Type.SINGLE_CHOICE))
                .thenReturn(new TaskDTO.Response.Tasks());

        taskService.newSingleChoice(singleChoice);

        verify(taskServiceHelper, times(1)).createNewTask(any(), any());
    }

    @Test
    @DisplayName("You should create an multiple choice task.")
    void newMultipleChoice() {
        TaskDTO.Request.MultipleChoice multipleChoice = mock(TaskDTO.Request.MultipleChoice.class);

        when(taskServiceHelper.createNewTask(multipleChoice, Type.MULTIPLE_CHOICE))
                .thenReturn(new TaskDTO.Response.Tasks());

        taskService.newMultipleChoice(multipleChoice);

        verify(taskServiceHelper, times(1)).createNewTask(any(), any());
    }
}