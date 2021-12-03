package run.freshr.domain.auth.repository;

import org.springframework.data.repository.CrudRepository;
import run.freshr.domain.auth.redis.AuthAccess;

public interface AuthAccessRepository extends CrudRepository<AuthAccess, String> {

}
