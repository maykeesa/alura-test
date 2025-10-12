package br.com.alura.AluraFake.user.service;

import br.com.alura.AluraFake.config.exception.exceptions.ServiceException;
import br.com.alura.AluraFake.course.dto.CourseDTO;
import br.com.alura.AluraFake.course.model.Course;
import br.com.alura.AluraFake.course.repository.CourseRepository;
import br.com.alura.AluraFake.user.dto.UserDTO;
import br.com.alura.AluraFake.user.model.User;
import br.com.alura.AluraFake.user.repository.UserRepository;
import br.com.alura.AluraFake.user.service.helper.UserServiceHelper;
import br.com.alura.AluraFake.util.service.MapperServiceUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserServiceHelper userServiceHelper;

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<UserDTO.Response.User> findAll() {
        List<User> users = userRepository.findAll();

        return MapperServiceUtil.convertObjects(users, UserDTO.Response.User.class);
    }

    @Override
    public UserDTO.Response.Instructor findInstructorById(Long id) {
        User instructor = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No instructor with that ID was found."));

        if(!instructor.isInstructor()){
            throw new ServiceException("The user is not an instructor.");
        }

        List<Course> coursesByInstructor = courseRepository.findByInstructor(instructor);
        List<CourseDTO.Response.Course> courses = MapperServiceUtil
                .convertObjects(coursesByInstructor, CourseDTO.Response.Course.class);
        UserDTO.Response.Instructor response = MapperServiceUtil
                .convertObject(instructor, UserDTO.Response.Instructor.class);

        userServiceHelper.setNumberTasksCourse(coursesByInstructor, courses);

        response.setNumberCoursesPublished(userServiceHelper.getNumberPublishedCourses(courses));
        response.setCourses(courses);

        return response;
    }

    @Override
    public UserDTO.Response.User create(UserDTO.Request.Register body) {
        if(userRepository.existsByEmail(body.getEmail())) {
            throw new ServiceException("Email already registered in the system.");
        }

        User userSaved = userRepository.save(MapperServiceUtil.convertObject(body, User.class));
        return MapperServiceUtil.convertObject(userSaved, UserDTO.Response.User.class);
    }
}
