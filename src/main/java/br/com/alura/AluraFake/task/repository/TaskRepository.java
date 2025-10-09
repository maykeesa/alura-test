package br.com.alura.AluraFake.task.repository;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByCourseOrderByOrderAsc(Course course);
}
