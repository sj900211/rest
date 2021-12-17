package run.freshr.domain.auth.unit;

import static java.util.Objects.isNull;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;
import run.freshr.domain.auth.repository.redis.RefreshRedisRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshRedisUnitImpl implements RefreshRedisUnit {

  private final RefreshRedisRepository repository;

  @Override
  @Transactional
  public void save(RefreshRedis redis) {
    log.info("RefreshRedisUnit.save");

    repository.save(redis);
  }

  @Override
  public Boolean exists(String id) {
    log.info("RefreshRedisUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public RefreshRedis get(String id) {
    log.info("RefreshRedisUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(String id) {
    log.info("RefreshRedisUnit.delete");

    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void delete(AccessRedis access) {
    log.info("RefreshRedisUnit.delete");

    Iterable<RefreshRedis> refreshIterable = repository.findAll();

    refreshIterable.forEach(refresh -> {
      if (!isNull(refresh) && !isNull(refresh.getAccess())
          && refresh.getAccess().getId().equals(access.getId())) {
        repository.delete(refresh);
      }
    });
  }

}
