package ru.ifmo.restapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ifmo.restapp.entity.Course;

public interface CourseRepository
        extends CrudRepository<Course, Integer> {
}
