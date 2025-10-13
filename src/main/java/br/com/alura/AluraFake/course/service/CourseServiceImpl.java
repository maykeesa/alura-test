package br.com.alura.AluraFake.course.service;

import br.com.alura.AluraFake.config.exception.exceptions.ServiceException;
import br.com.alura.AluraFake.course.dto.CourseDTO;
import br.com.alura.AluraFake.course.enums.Status;
import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.course.service.helper.CourseServiceHelper;
import br.com.alura.AluraFake.task.model.Task;
import br.com.alura.AluraFake.user.model.User;
import br.com.alura.AluraFake.user.repository.UserRepository;
import br.com.alura.AluraFake.util.service.MapperServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseServiceHelper courseServiceHelper;

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public List<CourseDTO.Response.Course> findAll() {
        return MapperServiceUtil.convertObjects(courseRepository.findAll(), CourseDTO.Response.Course.class);
    }

    @Override
    public CourseDTO.Response.Course create(CourseDTO.Request.Register body) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> possibleAuthor = userRepository
                .findByEmail(auth.getPrincipal().toString())
                .filter(User::isInstructor);

        if(possibleAuthor.isEmpty()) {
            throw new ServiceException("The user is not an instructor.");
        }

        Course course = new Course(body.getTitle(), body.getDescription(), possibleAuthor.get());

        return MapperServiceUtil.convertObject(courseRepository.save(course), CourseDTO.Response.Course.class);
    }

    @Override
    public CourseDTO.Response.Course publish(Long id) {
        Course course = courseServiceHelper.getCourse(id);
        List<Task> tasks = course.getTasks().stream()
                .sorted(Comparator.comparing(Task::getOrder))
                .toList();

        if(tasks.isEmpty()){
            throw new ServiceException("To publish a course, you must add assignments.");
        }

        courseServiceHelper.validateSequentialOrder(tasks);
        courseServiceHelper.validateTypesTasksContained(tasks);

        course.setStatus(Status.PUBLISHED);
        course.setPublishedAt(LocalDateTime.now());
        Course courseSaved = courseRepository.save(course);

        return MapperServiceUtil.convertObject(courseSaved, CourseDTO.Response.Course.class);
    }
}
