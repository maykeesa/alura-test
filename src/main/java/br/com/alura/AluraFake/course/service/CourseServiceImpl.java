package br.com.alura.AluraFake.course.service;

import br.com.alura.AluraFake.config.exception.exceptions.ServiceException;
import br.com.alura.AluraFake.course.dto.CourseDTO;
import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.user.User;
import br.com.alura.AluraFake.user.UserRepository;
import br.com.alura.AluraFake.util.service.MapperServiceUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public List<CourseDTO.Response.Course> getAll() {
        return MapperServiceUtil.convertObjects(courseRepository.findAll(), CourseDTO.Response.Course.class);
    }

    @Override
    public CourseDTO.Response.Course create(CourseDTO.Request.Register newCourse) {
        Optional<User> possibleAuthor = userRepository
                .findByEmail(newCourse.getEmailInstructor())
                .filter(User::isInstructor);

        if(possibleAuthor.isEmpty()) {
            throw new ServiceException("The user is not an instructor.");
        }

        Course course = new Course(newCourse.getTitle(), newCourse.getDescription(), possibleAuthor.get());

        return MapperServiceUtil.convertObject(courseRepository.save(course), CourseDTO.Response.Course.class);
    }

    @Override
    public CourseDTO.Response.Course publish(Long id) {
        return null;
    }
}
