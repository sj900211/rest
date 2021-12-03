package run.freshr.domain.auth.unit;

import run.freshr.common.extension.UnitRedisDefaultExtension;
import run.freshr.domain.auth.redis.AuthAccess;

public interface AuthAccessUnit extends UnitRedisDefaultExtension<AuthAccess, String> {

  Boolean exists(Long signId);

  AuthAccess get(Long signId);

  Iterable<AuthAccess> getList();

  void delete(Long signId);

}
