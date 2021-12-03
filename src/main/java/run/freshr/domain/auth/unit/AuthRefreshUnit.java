package run.freshr.domain.auth.unit;

import run.freshr.common.extension.UnitRedisDefaultExtension;
import run.freshr.domain.auth.redis.AuthAccess;
import run.freshr.domain.auth.redis.AuthRefresh;

public interface AuthRefreshUnit extends UnitRedisDefaultExtension<AuthRefresh, String> {

  void delete(AuthAccess access);

}
