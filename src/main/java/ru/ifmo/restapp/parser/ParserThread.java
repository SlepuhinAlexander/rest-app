package ru.ifmo.restapp.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.restapp.entity.Course;
import ru.ifmo.restapp.repository.CourseRepository;

import java.io.IOException;

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
        try {
            Document document = Jsoup.connect(link).get();
            Course course = new Course();
            course.setTitle(document.title());
            /*
             * Метод title() вернёт содержимое атрибута title в документе (HTML-страницы).
             * Т.е. то, что будет указано в названии вкладки в браузере
             * */

            /*
             * Длительность курса находится в элементе, отмеченном классом "dot__str1", который находится рядом с
             * элементом, отмеченном классом "dot__title" со значением "Длительность"
             *
             * Строимость курса находится в элементе, отмеченном классом "dot__str1", который находится рядом с
             * элементом, отмеченном классом "dot__title" со значением "Стоимость"
             * */
            // Получаем все элементы с классом "dot__title"
            Elements dotTitles = document.select(".dot__title");
            // Перебираем их все и проверяем значение на равенство с "Длительность" и "Стоимость"
            for (Element element : dotTitles) {
                if ("Длительность".equals(element.text())) {
                    // получаем следующий элемент с тем же родителем
                    course.setDuration(element.nextElementSibling().text());
                } else if ("Стоимость".equals(element.text())) {
                    course.setPrice(element.nextElementSibling().text());
                }
//                System.out.println(Thread.currentThread().getName() + " : " + link);
//                System.out.println(course);
                courseRepository.save(course);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
