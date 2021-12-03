package run.freshr;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppRunner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) {
    log.info("-------------------------------------------------------------------");
    log.info("_______ .______       _______     _______. __    __  .______");
    log.info("|   ____||   _  \\     |   ____|   /       ||  |  |  | |   _  \\");
    log.info("|  |__   |  |_)  |    |  |__     |   (----`|  |__|  | |  |_)  |");
    log.info("|   __|  |      /     |   __|     \\   \\    |   __   | |      /");
    log.info("|  |     |  |\\  \\----.|  |____.----)   |   |  |  |  | |  |\\  \\----.");
    log.info("|__|     | _| `._____||_______|_______/    |__|  |__| | _| `._____|");
    log.info("-------------------------------------------------------------------");
  }

}
