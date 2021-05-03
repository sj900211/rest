package run.freshr;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * The Class App runner.
 *
 * @author [류성재]
 * @implNote Application 구동 완료 클래스
 * @since 2021. 2. 25. 오후 5:23:27
 */
@Component
public class AppRunner implements ApplicationRunner {

  /**
   * Run.
   *
   * @param args the args
   * @author [류성재]
   * @implNote 멋
   * @since 2021. 2. 25. 오후 5:23:27
   */
  @Override
  public void run(ApplicationArguments args) {
    System.out.println("------------------------------------------------------------");
    System.out.println(".___  ___.   ______   .__   __.   _______  __       _______ ");
    System.out.println("|   \\/   |  /  __  \\  |  \\ |  |  /  _____||  |     |   ____|");
    System.out.println("|  \\  /  | |  |  |  | |   \\|  | |  |  __  |  |     |  |__   ");
    System.out.println("|  |\\/|  | |  |  |  | |  . `  | |  | |_ | |  |     |   __|  ");
    System.out.println("|  |  |  | |  `--'  | |  |\\   | |  |__| | |  `----.|  |____ ");
    System.out.println("|__|  |__|  \\______/  |__| \\__|  \\______| |_______||_______|");
    System.out.println(".___  ___.   ______   .__   __.   _______  __       _______ ");
    System.out.println("|   \\/   |  /  __  \\  |  \\ |  |  /  _____||  |     |   ____|");
    System.out.println("|  \\  /  | |  |  |  | |   \\|  | |  |  __  |  |     |  |__   ");
    System.out.println("|  |\\/|  | |  |  |  | |  . `  | |  | |_ | |  |     |   __|  ");
    System.out.println("|  |  |  | |  `--'  | |  |\\   | |  |__| | |  `----.|  |____ ");
    System.out.println("|__|  |__|  \\______/  |__| \\__|  \\______| |_______||_______|");
    System.out.println("------------------------------------------------------------");
  }

}
