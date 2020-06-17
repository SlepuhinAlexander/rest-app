package ru.ifmo.restapp.jackson;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
/*
 * В сериализации не будут участвовать поля со значением null.
 * Можно указать не для класса, а для отдельных полей.
 * */
//@JsonPropertyOrder(value = {"name", "description", "..."}) // использовать указанный порядок свойств
@JsonPropertyOrder(alphabetic = true) // использовать алфавитный порядок свойств
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC,
        getterVisibility = JsonAutoDetect.Visibility.ANY,
        setterVisibility = JsonAutoDetect.Visibility.ANY)
/*
 * позволяет отфильтровать какие свойства нужно сериализовать. Можно фильтровать по:
 * - полям: fieldVisibility
 * - геттерам: getterVisibility
 * - сеттерам: setterVisibility
 * */
public class Category extends Identify {

    private String name;

    private String description;

    private List<Cat> cats = new ArrayList<>();

/*
    public Category(@JsonProperty(defaultValue = "default") String name,
                    @JsonProperty(defaultValue = "default") String description,
                    @JsonProperty(defaultValue = "null") List<Cat> cats) {
        this.name = name;
        this.description = description;
        this.cats = cats;
    }
*/

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

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }
}
