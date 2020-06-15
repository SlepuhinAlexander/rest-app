import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

// спринг создает объекты (бины) классов со следующими аннотациями
@Component // аннотация для любого компонента фреймворка.
// value позволяет задать имя бина
// ---------------------------------
@Configuration // аннотация для конфиг класса.
// Если в main классе укзана аннотация @ConfigurationPropertiesScan("имя пакета"), то спринг будет искать
// конфиг классы с аннотациями @Configuration только в указанном пакете
@PropertySource("classpath:file_name.properties") // значение полей для созданных объектов ищет
// в file_name.properties файле
@ConfigurationProperties(prefix = "prefix.in.file") // в файле ищет строки, которые начинаются с prefix
// ---------------------------------
@Controller // аннотация указывает, что данный класс является контроллером
// (чья задача - реакция на запросы). Способен передавать данные в html и возвращать html страницы
@RestController // аннотация указывает, что данный класс является контроллером
// (чья задача - реакция на запросы). Не способен возвращать html файлы и работать с Model.
// Обычно возвращает данные в формате json, лтбо xml.
// ----------------------------------
@Service // аннотация указывает, что класс является прослойкой бизнесс логики приложения
// (используется для обработки, формирования данных и тп)
// ----------------------------------
@EnableScheduling // аннотация указывает, что класс содержит метод, который будет вызываться по расписанию
public class TstClass {

    @Bean // метод будет вызван автоматически, будет создан и сохранен объект бин того типа,
    //который указан, как тип возвращаемого значения  (Animal в данном случае)
    // как правило @Bean методы определяются в @Configuration классах
    public Animal getObj(){
        return new Cat();
    }

    @Scheduled // метод должен вызываться по расписанию (при этом класс должен быть аннотирован @EnableScheduling)
    // fixedRate=N - метод будет вызываться каждые N млс. Метод не будет вызван, если предыдущий метод не завершил работу.
    // Для поддержки асинхронности необходимо добавить аннотацию @Async
    // fixedDelay=N - метод будет вызван через N млс после того, как закончит выполнение предыдущий вызов метода
    // initialDelay=N  - первый раз метод будет вызван через N млс
    // cron=
    // https://www.baeldung.com/cron-expressions
    // http://www.cronmaker.com/;jsessionid=node0s837k8idprnvh5lzzxpydfnv205811.node0?0
    // @Scheduled(fixedDelayString = "${fixed.millis}") указание из properties файла
    public void someAction() {
        System.out.println("someAction");
    }
}
