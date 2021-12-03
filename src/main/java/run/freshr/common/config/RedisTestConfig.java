package run.freshr.common.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

@Profile("test")
@Configuration
public class RedisTestConfig {

  private final RedisServer redisServer;

  public RedisTestConfig(@Value("${spring.redis.port}") int redisPort) {
    redisServer = new RedisServer(redisPort);
  }

  @PostConstruct
  public void start() {
    redisServer.start();
  }

  @PreDestroy
  public void stop() {
    redisServer.stop();
  }

}
