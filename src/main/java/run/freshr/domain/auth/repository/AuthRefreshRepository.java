package run.freshr.domain.auth.repository;

import run.freshr.domain.auth.redis.AuthRefresh;
import org.springframework.data.repository.CrudRepository;

/**
 * The Interface Auth refresh repository.
 *
 * @author [류성재]
 * @implNote Refresh 토큰 Repository
 * @since 2021. 3. 16. 오후 2:26:45
 */
public interface AuthRefreshRepository extends CrudRepository<AuthRefresh, String> {

}
