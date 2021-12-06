package run.freshr.domain.auth.unit;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.repository.AccountRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountUnitImpl implements AccountUnit {

  private final AccountRepository repository;

  @Override
  @Transactional
  public Long create(Account entity) {
    log.info("AccountUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long id) {
    log.info("AccountUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Boolean exists(String username) {
    log.info("AccountUnit.exists");

    return repository.existsByUsername(username);
  }

  @Override
  public Account get(Long id) {
    log.info("AccountUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Account get(String username) {
    log.info("AccountUnit.get");

    return repository.findByUsername(username);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    log.info("AccountUnit.delete");

    repository.deleteById(id);
  }

}
