package run.freshr.domain.auth.redis;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

@Slf4j
@RedisHash("CACHE_REFRESH_TOKEN")
@Getter
public class AuthRefresh {

  @Id
  private final String id;

  @Reference
  private AuthAccess access;

  private LocalDateTime updateDt;

  private AuthRefresh(String id, AuthAccess access) {
    log.info("AuthRefresh.Constructor");

    this.id = id;
    this.access = access;
    this.updateDt = LocalDateTime.now();
  }

  public static AuthRefresh createRedis(String id, AuthAccess access) {
    log.info("AuthRefresh.createRedis");

    return new AuthRefresh(id, access);
  }

  public void updateRedis(AuthAccess access) {
    log.info("AuthRefresh.updateRedis");

    this.access = access;
    this.updateDt = LocalDateTime.now();
  }

}
