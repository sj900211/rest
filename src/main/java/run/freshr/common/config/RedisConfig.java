package run.freshr.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * The Class Redis config.
 *
 * @author [류성재]
 * @implNote 로컬에서 테스트를 위한 Redis 서버를 실행하는 클래스 test Profile 에서만 작동
 * @since 2021. 2. 24. 오후 5:41:45
 */
@Profile({"dev", "staging", "prod"})
@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

  private final RedisProperties redisProperties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate() {
    RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();

    redisTemplate.setConnectionFactory(redisConnectionFactory());

    return redisTemplate;
  }


}
