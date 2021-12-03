package run.freshr.domain.auth.redis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import run.freshr.domain.auth.enumeration.Role;

@Slf4j
@RedisHash("CACHE_ACCESS_TOKEN")
@Getter
public class AuthAccess {

  @Id
  private final String id;

  private final Long signId;

  private final Role role;

  private AuthAccess(String id, Long signId, Role role) {
    log.info("AuthAccess.Constructor");

    this.id = id;
    this.signId = signId;
    this.role = role;
  }

  public static AuthAccess createRedis(String id, Long signId, Role role) {
    log.info("AuthAccess.createRedis");

    return new AuthAccess(id, signId, role);
  }

}
