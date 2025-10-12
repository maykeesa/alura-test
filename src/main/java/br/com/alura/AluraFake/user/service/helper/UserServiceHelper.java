package br.com.alura.AluraFake.user.service.helper;

import br.com.alura.AluraFake.course.dto.CourseDTO;
import br.com.alura.AluraFake.course.enums.Status;
import br.com.alura.AluraFake.course.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {

    public void setNumberTasksCourse(List<Course> coursesByInstructor, List<CourseDTO.Response.Course> courses){
        List<Integer> taskCounts = getTaskCountsPerCourse(coursesByInstructor);

        for (int i = 0; i < courses.size(); i++) {
            courses.get(i).setNumberTasksCourse(taskCounts.get(i));
        }
    }

    public Integer getNumberPublishedCourses(List<CourseDTO.Response.Course> courses){
        return courses.stream()
                .filter(course -> course.getStatus().equals(Status.PUBLISHED))
                .toList().size();
    }

    private List<Integer> getTaskCountsPerCourse(List<Course> courses) {
        return courses.stream()
                .map(course -> course.getTasks() != null ? course.getTasks().size() : 0)
                .toList();
    }
}
