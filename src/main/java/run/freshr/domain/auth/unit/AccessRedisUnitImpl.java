package run.freshr.domain.auth.unit;

import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.repository.redis.AccessRedisRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessRedisUnitImpl implements AccessRedisUnit {

  private final AccessRedisRepository repository;

  @Override
  @Transactional
  public void save(AccessRedis redis) {
    log.info("AccessRedisUnit.save");

    repository.save(redis);
  }

  @Override
  public Boolean exists(String id) {
    log.info("AccessRedisUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Boolean exists(Long signId) {
    log.info("AccessRedisUnit.exists");

    Iterable<AccessRedis> accesses = repository.findAll();
    Optional<AccessRedis> authAccess = StreamSupport.stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId().equals(signId))
        .findFirst();

    return authAccess.isPresent();
  }

  @Override
  public AccessRedis get(String id) {
    log.info("AccessRedisUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public AccessRedis get(Long signId) {
    log.info("AccessRedisUnit.get");

    Iterable<AccessRedis> accesses = repository.findAll();
    Optional<AccessRedis> authAccess = StreamSupport.stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId().equals(signId))
        .findFirst();

    return authAccess.orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Iterable<AccessRedis> getList() {
    log.info("AccessRedisUnit.getList");

    return repository.findAll();
  }

  @Override
  @Transactional
  public void delete(String id) {
    log.info("AccessRedisUnit.delete");

    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void delete(Long signId) {
    log.info("AccessRedisUnit.delete");

    Iterable<AccessRedis> accesses = repository.findAll();
    Optional<AccessRedis> authAccess = StreamSupport.stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId().equals(signId))
        .findFirst();

    authAccess.ifPresent(repository::delete);
  }

}
