package run.freshr.domain.auth.unit;

import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.redis.AuthAccess;
import run.freshr.domain.auth.repository.AuthAccessRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthAccessUnitImpl implements AuthAccessUnit {

  private final AuthAccessRepository repository;

  @Override
  @Transactional
  public void save(AuthAccess redis) {
    log.info("AuthAccessUnit.save");

    repository.save(redis);
  }

  @Override
  public Boolean exists(String id) {
    log.info("AuthAccessUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Boolean exists(Long signId) {
    log.info("AuthAccessUnit.exists");

    Iterable<AuthAccess> accesses = repository.findAll();
    Optional<AuthAccess> authAccess = StreamSupport.stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId().equals(signId))
        .findFirst();

    return authAccess.isPresent();
  }

  @Override
  public AuthAccess get(String id) {
    log.info("AuthAccessUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public AuthAccess get(Long signId) {
    log.info("AuthAccessUnit.get");

    Iterable<AuthAccess> accesses = repository.findAll();
    Optional<AuthAccess> authAccess = StreamSupport.stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId().equals(signId))
        .findFirst();

    return authAccess.orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Iterable<AuthAccess> getList() {
    log.info("AuthAccessUnit.getList");

    return repository.findAll();
  }

  @Override
  @Transactional
  public void delete(String id) {
    log.info("AuthAccessUnit.delete");

    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void delete(Long signId) {
    log.info("AuthAccessUnit.delete");

    Iterable<AuthAccess> accesses = repository.findAll();
    Optional<AuthAccess> authAccess = StreamSupport.stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId().equals(signId))
        .findFirst();

    authAccess.ifPresent(repository::delete);
  }

}
