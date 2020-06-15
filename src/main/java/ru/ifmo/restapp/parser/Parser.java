package ru.ifmo.restapp.parser;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling // класс содержит метод, который будет вызываться
// по расписанию
@Component
public class Parser {

    @Scheduled // метод должен вызывать по расписанию
    public void start(){

    }
}
