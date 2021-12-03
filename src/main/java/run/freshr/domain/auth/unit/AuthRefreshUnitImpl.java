package run.freshr.domain.auth.unit;

import static java.util.Objects.isNull;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.redis.AuthAccess;
import run.freshr.domain.auth.redis.AuthRefresh;
import run.freshr.domain.auth.repository.AuthRefreshRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthRefreshUnitImpl implements AuthRefreshUnit {

  private final AuthRefreshRepository repository;

  @Override
  @Transactional
  public void save(AuthRefresh redis) {
    log.info("AuthRefreshUnit.save");

    repository.save(redis);
  }

  @Override
  public Boolean exists(String id) {
    log.info("AuthRefreshUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public AuthRefresh get(String id) {
    log.info("AuthRefreshUnit.getDetail");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(String id) {
    log.info("AuthRefreshUnit.delete");

    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void delete(AuthAccess access) {
    log.info("AuthRefreshUnit.delete");

    Iterable<AuthRefresh> refreshIterable = repository.findAll();

    refreshIterable.forEach(refresh -> {
      if (!isNull(refresh) && !isNull(refresh.getAccess())
          && refresh.getAccess().getId().equals(access.getId())) {
        repository.delete(refresh);
      }
    });
  }

}
