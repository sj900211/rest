package run.freshr.domain.common.redis;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Slf4j
@RedisHash("CACHE_ACCESS_TOKEN")
@Getter
public class RsaPair {

  @Id
  private final String encodePublicKey;

  private final String encodePrivateKey;

  private final LocalDateTime createDt;

  private RsaPair(String encodePublicKey, String encodePrivateKey, LocalDateTime createDt) {
    log.info("RsaPair.Constructor");

    this.encodePublicKey = encodePublicKey;
    this.encodePrivateKey = encodePrivateKey;
    this.createDt = createDt;
  }

  public static RsaPair createRedis(String encodePublicKey, String encodePrivateKey,
      LocalDateTime createDt) {
    log.info("RsaPair.createRedis");

    return new RsaPair(encodePublicKey, encodePrivateKey, createDt);
  }

}
