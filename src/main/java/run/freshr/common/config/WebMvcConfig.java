package run.freshr.common.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The Class Web mvc config.
 *
 * @author [류성재]
 * @implNote Web Mvc 설정
 * @since 2021. 2. 24. 오후 5:36:09
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  /**
   * Add cors mappings.
   *
   * @param registry the registry
   * @author [류성재]
   * @implNote Cross Domain 설정
   * @since 2021. 2. 24. 오후 5:36:09
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods(GET.name(), POST.name(), PUT.name(), DELETE.name(), OPTIONS.name());
  }

}
