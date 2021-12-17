package run.freshr.domain.auth.repository.redis;

import org.springframework.data.repository.CrudRepository;
import run.freshr.domain.auth.redis.RefreshRedis;

public interface RefreshRedisRepository extends CrudRepository<RefreshRedis, String> {

}
