package ru.ifmo.restapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.restapp.jackson.Cat;
import ru.ifmo.restapp.jackson.Category;
import ru.ifmo.restapp.jackson.Color;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class HomeController {

    @GetMapping("/")
    public @ResponseBody
    String getStringData() {
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
        category.getCats().add(murzik);


        Cat vasilii = new Cat(); // даный объект не будет передан для преобразования в json строку
        vasilii.setId(12);
        vasilii.setName("Василий");
        vasilii.setCategory(category);
        category.getCats().add(vasilii);

        ObjectMapper mapperToJson = new ObjectMapper();
        String jsonCat = "один объект Cat";
        String jsonCats = "список объектов Cat";

        try {
            // преобразование объекта в JSON-строку.
            jsonCat = mapperToJson.writeValueAsString(murzik);
            // преобразование списка объектов
            jsonCats = mapperToJson.writeValueAsString(Arrays.asList(murzik, murzik, murzik));
            System.out.println("--- РЕЗУЛЬТАТ ПРЕОБРАЗОВАНИЯ К JSON ---");
            System.out.println(jsonCat);
            System.out.println(jsonCats);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /*
         * По умолчанию, включаются все поля класса
         * Название полей в JSONе равно названию полей в классе.
         * */

        // преобразование из JSON к объекту / к списку объектов.
        ObjectMapper mapperToObject = new ObjectMapper();
        Cat catFromJson = null;
        Cat[] catsFromJson = null;
        ArrayList<Cat> catsListFromJson = null;

        try {
            // из JSON к объекту Cat
            catFromJson = mapperToObject.readValue(jsonCat, Cat.class);
            /*
             * Передаём источник, из которого нужно собрать объект. В данном случае строку
             * И класс объекта, который нужно собрать.
             *
             * С настройками по умолчанию, класс (и все используемые им классы) должны иметь конструкторы по умолчанию.
             * */

            // из JSON к массиву Cat[]
            catsFromJson = mapperToObject.readValue(jsonCats, Cat[].class);

            // из JSON к коллекции (например, ArrayList)
            /*
             * Для преобразования к коллекции нужно определить тип коллекции, которую нужно создать.
             * */
            CollectionType type = mapperToJson.getTypeFactory()
                    .constructCollectionType(ArrayList.class, Cat.class);
            catsListFromJson = mapperToJson.readValue(jsonCats, type);
            // альтернативный вариант:
/*
            catsListFromJson = mapperToJson.readValue(jsonCats, new TypeReference<ArrayList<Cat>>() {
            });
*/

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("--- РЕЗУЛЬТАТ ПРЕОБРАЗОВАНИЯ ИЗ JSON К ОБЪЕКТУ Cat ---");
        System.out.println(catFromJson == null ? "" : catFromJson.getName());
        System.out.println("--- РЕЗУЛЬТАТ ПРЕОБРАЗОВАНИЯ ИЗ JSON К МАССИВУ Cat[] ---");
        for (Cat cat : catsFromJson == null ? new Cat[0] : catsFromJson) {
            System.out.println(cat.getName() + " : ");
            for (Cat.Habit catHabit : cat.getHabits()) {
                System.out.println(catHabit.getName());
            }
        }
        System.out.println("--- РЕЗУЛЬТАТ ПРЕОБРАЗОВАНИЯ ИЗ JSON К МАССИВУ ArrayList<Cat> ---");
        if (catsListFromJson != null) {
            catsListFromJson.forEach(System.out::println);
        }

        return "текст";

    }
}
