package run.freshr;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The Class Servlet initializer.
 *
 * @author [류성재]
 * @implNote Web Application Context 구현체
 * @since 2021. 2. 25. 오후 5:22:19
 */
public class ServletInitializer extends SpringBootServletInitializer {

  /**
   * Configure spring application builder.
   *
   * @param application the application
   * @return the spring application builder
   * @author [류성재]
   * @implNote
   * @since 2021. 2. 25. 오후 5:22:19
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

}
