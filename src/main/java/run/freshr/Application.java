package run.freshr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Class Application.
 *
 * @author [류성재]
 * @implNote Application
 * @since 2021. 2. 25. 오후 5:28:18
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class Application {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @author [류성재]
   * @implNote main
   * @since 2021. 2. 25. 오후 5:28:18
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
