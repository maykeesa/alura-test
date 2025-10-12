package br.com.alura.AluraFake.course.service.helper;

import br.com.alura.AluraFake.config.exception.exceptions.ServiceException;
import br.com.alura.AluraFake.course.enums.Status;
import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.task.model.Task;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseServiceHelper {

    private final CourseRepository courseRepository;

    public Course getCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id %s not found."
                        .formatted(courseId)));

        if (!course.getStatus().equals(Status.BUILDING)) {
            throw new ServiceException("You can only publish courses that have the status BUILDING.");
        }

        return course;
    }

    public void validateSequentialOrder(List<Task> tasks) {
        for (int index = 0; index < tasks.size(); index++) {
            int expectedOrder = index + 1;
            int actualOrder = tasks.get(index).getOrder();

            if (expectedOrder != actualOrder) {
                throw new ServiceException(
                        "Tasks must have sequential orders starting from 1. Expected %d but found %d."
                                .formatted(expectedOrder, actualOrder));
            }
        }
    }

    public void validateTypesTasksContained(List<Task> tasks) {
        boolean hasAllThreeTypes = tasks.stream()
                .map(Task::getType)
                .distinct().count() == 3;

        if (!hasAllThreeTypes) {
            throw new ServiceException("The course must contain at least one task of each type.");
        }
    }
}
