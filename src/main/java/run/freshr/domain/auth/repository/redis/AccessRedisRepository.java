package run.freshr.domain.auth.repository.redis;

import org.springframework.data.repository.CrudRepository;
import run.freshr.domain.auth.redis.AccessRedis;

public interface AccessRedisRepository extends CrudRepository<AccessRedis, String> {

}
