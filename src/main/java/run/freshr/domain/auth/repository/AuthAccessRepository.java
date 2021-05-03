package run.freshr.domain.auth.repository;

import run.freshr.domain.auth.redis.AuthAccess;
import org.springframework.data.repository.CrudRepository;

/**
 * The Interface Auth access repository.
 *
 * @author [류성재]
 * @implNote Access 토큰 Repository
 * @since 2021. 3. 16. 오후 2:26:38
 */
public interface AuthAccessRepository extends CrudRepository<AuthAccess, String> {

}
