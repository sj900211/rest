package run.freshr.domain.common.unit;

import run.freshr.common.extension.UnitRedisDefaultExtension;
import run.freshr.domain.common.redis.RsaPair;

public interface RsaPairUnit extends UnitRedisDefaultExtension<RsaPair, String> {

  Iterable<RsaPair> getList();

  void delete(Iterable<RsaPair> list);

  Boolean checkRsa(String encodePublicKey);

}
