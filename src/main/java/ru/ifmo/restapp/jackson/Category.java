package ru.ifmo.restapp.jackson;


import java.util.ArrayList;
import java.util.List;

public class Category extends Identify {

    private String name;

    private String description;

//    private List<Cat> cats = new ArrayList<>();

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

//    public List<Cat> getCats() {
//        return cats;
//    }

//    public void setCats(List<Cat> cats) {
//        this.cats = cats;
//    }
}
