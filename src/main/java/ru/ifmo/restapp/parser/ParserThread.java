package ru.ifmo.restapp.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.restapp.repository.CourseRepository;

@Component
@Scope("prototype") // позволит создавать несколько объектов данного
// класса (по каждому запросу - создание объекта)
public class ParserThread implements Runnable {
    private String link;
    private CourseRepository courseRepository;

    // при создании объекта ParserThread спринг установит значение
    // только для свойства courseRepository (благодаря аннотации @Autowired)
    // значение свойства link останется null (значение по умолчанию)
    @Autowired
    public ParserThread(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public CourseRepository getCourseRepository() {
        return courseRepository;
    }

    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void run() {

    }
}
