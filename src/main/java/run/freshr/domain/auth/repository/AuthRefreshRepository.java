package run.freshr.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import run.freshr.domain.auth.redis.AuthRefresh;

public interface AuthRefreshRepository extends CrudRepository<AuthRefresh, String> {

}
