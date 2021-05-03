package run.freshr.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class Custom config.
 *
 * @author [류성재]
 * @implNote Custom 설정
 * @since 2021. 2. 25. 오후 4:43:36
 */
@Configuration
@EnableConfigurationProperties
@Data
public class CustomConfig {

  /**
   * The Email from
   */
  @Value("${freshr.email.from}")
  private String emailFrom;

}
