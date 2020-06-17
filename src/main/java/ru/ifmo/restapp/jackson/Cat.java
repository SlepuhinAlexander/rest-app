package ru.ifmo.restapp.jackson;


import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
/* В списке котов, в категории кота Мурзика вместо строки Мурзика остаётся только его ID */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
/*
 * В сериализацию не попадут поля со значениями по умолчанию.
 * Можно указать для отдельных полей.
 * */
public class Cat extends Identify {
    @JsonProperty(value = "catNameInJson", defaultValue = "Барсик")
    private String name;
    private int age;
    private Color color;
    private Category category;
    private List<Habit> habits = new ArrayList<>();
    /*
     * LocalDateTime не может десериализоваться при настройках по умолчанию: у него нет конструктора без аргументов.
     * Для исправления нужно аннотацией @JsonSerialize / @JsonDeserialize указать класс-(де)сериализатор, который
     * должен преобразовывать объект в / из строки.
     * Класс может быть реализован самостоятельно.
     * Для DateTimeAPI в библиотеке jackson есть готовые (де)сериализаторы.
     * */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime birth;

    @JsonIgnore // полностью исключает поле из сериализации. При десериализации получит значение по умолчанию.
    private String additionalInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
    }

    public LocalDateTime getBirth() {
        return birth;
    }

    public void setBirth(LocalDateTime birth) {
        this.birth = birth;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public static class Habit {
        private String name;
        private String description;
        private boolean good;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isGood() {
            return good;
        }

        public void setGood(boolean good) {
            this.good = good;
        }
    }
}
