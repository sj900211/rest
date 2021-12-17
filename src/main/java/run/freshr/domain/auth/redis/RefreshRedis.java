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
public class RefreshRedis {

  @Id
  private final String id;

  @Reference
  private AccessRedis access;

  private LocalDateTime updateDt;

  private RefreshRedis(String id, AccessRedis access) {
    log.info("RefreshRedis.Constructor");

    this.id = id;
    this.access = access;
    this.updateDt = LocalDateTime.now();
  }

  public static RefreshRedis createRedis(String id, AccessRedis access) {
    log.info("RefreshRedis.createRedis");

    return new RefreshRedis(id, access);
  }

  public void updateRedis(AccessRedis access) {
    log.info("RefreshRedis.updateRedis");

    this.access = access;
    this.updateDt = LocalDateTime.now();
  }

}
