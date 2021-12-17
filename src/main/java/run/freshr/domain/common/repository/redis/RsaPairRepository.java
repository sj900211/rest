package run.freshr.domain.common.repository.redis;

import org.springframework.data.repository.CrudRepository;
import run.freshr.domain.common.redis.RsaPair;

public interface RsaPairRepository extends CrudRepository<RsaPair, String> {

}
