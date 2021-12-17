package run.freshr.domain.auth.redis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import run.freshr.domain.auth.enumeration.Role;

@Slf4j
@RedisHash("CACHE_ACCESS_TOKEN")
@Getter
public class AccessRedis {

  @Id
  private final String id;

  private final Long signId;

  private final Role role;

  private AccessRedis(String id, Long signId, Role role) {
    log.info("AccessRedis.Constructor");

    this.id = id;
    this.signId = signId;
    this.role = role;
  }

  public static AccessRedis createRedis(String id, Long signId, Role role) {
    log.info("AccessRedis.createRedis");

    return new AccessRedis(id, signId, role);
  }

}
