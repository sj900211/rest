package run.freshr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableElasticsearchAuditing
@EnableScheduling
@EnableCaching
@EnableJpaRepositories("**.**.domain.**.repository.jpa")
@EnableElasticsearchRepositories("**.**.domain.**.repository.elasticsearch")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
