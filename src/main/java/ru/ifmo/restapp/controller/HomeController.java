package ru.ifmo.restapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.restapp.jackson.Cat;
import ru.ifmo.restapp.jackson.Category;
import ru.ifmo.restapp.jackson.Color;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
public class HomeController {

    @GetMapping("/")
    public @ResponseBody String getStringData(){
        Category category = new Category();
        category.setId(10);
        category.setName("Кошки");
        category.setDescription("Кошки с родословной");

        Cat murzik = new Cat(); // даный объект будет передан для преобразования в json строку
        murzik.setId(11);
        murzik.setName("Мурзик");
        murzik.setAge(0);
        murzik.setColor(Color.WHITE);
        murzik.setBirth(LocalDateTime.now().minus(1, ChronoUnit.YEARS));
        murzik.setAdditionalInfo("Кот со всеми прививками");

        Cat.Habit habit = new Cat.Habit();
        habit.setName("Ладит с детьми");
        habit.setDescription("Отличная привычка");
        habit.setGood(true);
        murzik.getHabits().add(habit);

        // у объектов установлены взаимные ссылки
        murzik.setCategory(category);
//        category.getCats().add(murzik);



        Cat vasilii = new Cat(); // даный объект не будет передан для преобразования в json строку
        vasilii.setId(12);
        vasilii.setName("Василий");
        vasilii.setCategory(category);
//        category.getCats().add(vasilii);

        return "текст";

    }
}
