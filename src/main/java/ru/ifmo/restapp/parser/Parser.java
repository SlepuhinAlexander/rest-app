package ru.ifmo.restapp.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.ifmo.restapp.config.ParserConfig;

import java.io.IOException;

@EnableScheduling // класс содержит метод, который будет вызываться по расписанию
@Component
public class Parser {
    // Нужно получить ссылку для парсинга, которую ParserConfig прочитал из application.properties.
    // Для этого храним ссылку на объект конфига.
    private final ParserConfig config;

    // Также нужен пул потоков, чтобы брать из него потоки и передавать задачи на исполнение.
    private final TaskExecutor executor;

    // ApplicationContext нужен для того чтобы получить интересующий Bean-объект из тех объектов, которые создал и
    // сохранил спринг
    private final ApplicationContext context;

    @Autowired
    public Parser(ParserConfig config, @Qualifier("executor") TaskExecutor executor, ApplicationContext context) {
        /*
         * ParserConfig создан, потому что его класс размечен @Configuration и спринг его создаст и сохранит.
         * TaskExecutor создан, потому что создающий его метод размечен @Bean и спринг его создаст и сохранит.
         * ApplicationContext в принципе есть в контексте спринга всегда, спринг его создаёт в начале своей работы.
         * */
        this.config = config;
        this.executor = executor;
        this.context = context;
    }

    @Scheduled(fixedRate = 3600000) // метод должен вызываться по расписанию
    /*
     * Варианты запуска по расписанию:
     * - fixedRate = ### (в ms) -- метод будет вызван каждые N милисекунд. Задержка берётся от начала предыдущего
     *      запуска.
     *      Если метод не успеет отработать за указанные период - новый запуск не будет произведён, параллельных
     *      запусков не будет, программа будет дожидаться завершения выполнения метода и только потом запустит новый
     *      (уже без задержки).
     *      Если это поведение не нужно и нужно строгое расписание запуска (с возможными накладками нескольких
     *      выполнений друг на друга), то нужно указать дополнительную аннотацию @Async
     * - fixedDelay = ### (в ms) -- метод будет вызван через N миллисекунд после окончания предыдущего выполнения.
     * - initialDelay = ### (в ms) -- задержка в миллисекундах перед ПЕРВЫМ вызовом метода.
     * - cron = *** (строка) -- возможность задать расписание запуска в виде cron-job строки.
     * */
    /*
     * Использование аннотации @Scheduled создаст ещё один TaskExecutor пул потоков для работы по расписанию.
     * Это приведёт к конфликту при запуске приложения: у нас будет 2 бина с типом TaskExecutor, и спринг не может
     * выбрать какой именно
     *
     * Для исправления ситуации нужно при создании объекта указать аннотацию @Qualifier (с уникальным именем для
     * объекта) и при использовании - тоже указать эту аннотацию.
     *
     * Альтернативно, вместо идентификатора @Qualifier можно указать аннотацию @Primary
     * Она означает, что если у спринга будет конфликт какой бин выбрать - он выберет @Primary бин.
     * */
    public void start() {
        System.out.println("Start");


        try {
            // считываем документ
            Document document = Jsoup.connect(config.getLink()).get();
            /*
             * Указываем что нужно соединиться, передаём URL из конфига.
             * Считываем документ целиком.
             * */

            /*
             * Каждый курс находится в контейнере с классом course-item
             * Нам нужно получить такой элемент и получить из него ссылку на курс.
             * */
            Elements elements = document.select(".course-item a");
            /*
             * Выбираем из документа все элементы типа a = ссылка, с CSS-классом courseItem
             * */

            // перебор коллекции, которая состоит тэгов <a href="link"></a>
            for (Element element : elements) {
                // получить тред для задачи распарсить ссылку
                ParserThread parserThread = context.getBean(ParserThread.class);
                /*
                 * Получаем из контекста спринг, указав интересующий класс.
                 * */

                // получить из элемента объект типа URL (на самом деле стринг): брать из атрибута href
                parserThread.setLink(element.absUrl("href"));

                // передать задачу в пул потоков на выполнение
                executor.execute(parserThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
