package run.freshr.domain.auth.unit;

import run.freshr.common.extension.UnitRedisDefaultExtension;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;

public interface RefreshRedisUnit extends UnitRedisDefaultExtension<RefreshRedis, String> {

  void delete(AccessRedis access);

}
