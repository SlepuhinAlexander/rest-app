package ru.ifmo.restapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.restapp.entity.Course;
import ru.ifmo.restapp.repository.CourseRepository;

import java.util.Optional;

/*
 * Если сервер возвращает не HTML-страницы, а данные то нужно указать, что контроллер работает с REST-архитектурой.
 * Для этого контроллер размечаем @RestController
 * */
@RestController
public class CourseController {
    private final CourseRepository repository;

    @Autowired
    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    /*
     * По умолчанию @RestController ориентирован на работу с типами данных Iterable<T> и Optional<T>
     *
     * Если методы возвращают Iterable<T> или Optional<T>, то спринг сам автоматически преобразует возвращённое
     * значение к JSON-строке и возвращает её клиенту.
     * Никаких дополнительных требований от программиста не требуется.
     * */
    /*
     * Для маппинга по запросам к серверу вместо @RequestMapping можно использовать
     * @GetMapping - полуение данных
     * @PostMapping - добавление новых данных
     * @PutMapping - обновление имеющихся данных
     * @DeleteMapping - удаление данных
     * и т.д.
     * */
    @GetMapping(value = "/courses")
    public Iterable<Course> findAll() {
        return repository.findAll();
    }

    // "/course/1" | "/course/13" "/course/2"
    @GetMapping(value = "/courses/{id}")
    public Optional<Course> findById(@PathVariable int id) {
        return repository.findById(id);
    }

    // Apache HttpClient
    /* Популярная библиотека для реализации HTTP-клиента: генерации HTTP-запросов на некоторый сервер */

    /*
     * Предположим, клиент хочет получить от сервера набор байт. Например, картинку.
     * Наличие @ResponseBody означает, что спринг не должен преобразовывать ответ в JSON-строку, а передать тот тип
     * данных что возвращает метод в чистом виде. Обычно - массив байт.
     * */
    @GetMapping(value = "/img")
    public @ResponseBody
    byte[] getImage() {
        byte[] bytes = new byte[30]; // преобразование изображения в массив байт (некоторым образом)
        // и передача в тело ответа.
        return bytes;
    }

    /*
     * Аналогично, если сервер решает самостоятельно формировать JSON строку или передавать какую-то другую строку,
     * нужна аннотация @ResponseBody
     * */
    @GetMapping(value = "/string")
    public @ResponseBody
    String getString() {
        String string = "Формирование строки";
        return string;
    }
}
