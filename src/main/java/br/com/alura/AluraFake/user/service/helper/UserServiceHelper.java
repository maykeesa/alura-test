package br.com.alura.AluraFake.user.service.helper;

import br.com.alura.AluraFake.config.exception.exceptions.ServiceException;
import br.com.alura.AluraFake.course.dto.CourseDTO;
import br.com.alura.AluraFake.course.enums.Status;
import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.user.model.User;
import br.com.alura.AluraFake.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {

    private final UserRepository userRepository;

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

    public User findValidUserByEmail(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(("Invalid email or password.")));

        if(Objects.nonNull(password) && !user.getPassword().equals(password)){
            throw new ServiceException("Invalid email or password.");
        }

        return user;
    }

    private List<Integer> getTaskCountsPerCourse(List<Course> courses) {
        return courses.stream()
                .map(course -> course.getTasks() != null ? course.getTasks().size() : 0)
                .toList();
    }
}
