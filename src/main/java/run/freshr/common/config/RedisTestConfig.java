package run.freshr.common.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

/**
 * The Class Redis config.
 *
 * @author [류성재]
 * @implNote 로컬에서 테스트를 위한 Redis 서버를 실행하는 클래스 test Profile 에서만 작동
 * @since 2021. 2. 24. 오후 5:41:45
 */
@Profile("test")
@Configuration
public class RedisTestConfig {

  /**
   * The Redis server
   */
  private final RedisServer redisServer;

  /**
   * Instantiates a new Redis config.
   *
   * @param redisPort the redis port
   * @author [류성재]
   * @implNote Redis 서버를 application.yml 에 있는 정보로 설정
   * @since 2021. 2. 24. 오후 5:41:45
   */
  public RedisTestConfig(@Value("${spring.redis.port}") int redisPort) {
    redisServer = new RedisServer(redisPort);
  }

  /**
   * Start.
   *
   * @author [류성재]
   * @implNote Redis 서버 시작
   * @since 2021. 2. 24. 오후 5:41:45
   */
  @PostConstruct
  public void start() {
    redisServer.start();
  }

  /**
   * Stop.
   *
   * @author [류성재]
   * @implNote Redis 서버 종료
   * @since 2021. 2. 24. 오후 5:41:45
   */
  @PreDestroy
  public void stop() {
    redisServer.stop();
  }

}
