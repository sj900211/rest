package run.freshr.domain.mapping.unit;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.mapping.embedded.AccountHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.AccountHashtagMapping;
import run.freshr.domain.mapping.repository.AccountHashtagMappingRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountHashtagMappingUnitImpl implements AccountHashtagMappingUnit {

  private final AccountHashtagMappingRepository repository;

  @Override
  @Transactional
  public AccountHashtagMappingEmbeddedId create(AccountHashtagMapping entity) {
    log.info("AccountHashtagMappingUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(AccountHashtagMappingEmbeddedId id) {
    log.info("AccountHashtagMappingUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public AccountHashtagMapping get(AccountHashtagMappingEmbeddedId id) {
    log.info("AccountHashtagMappingUnit.get");

    return repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(AccountHashtagMappingEmbeddedId id) {
    log.info("AccountHashtagMappingUnit.delete");

    repository.deleteById(id);

  }

  @Override
  public List<AccountHashtagMapping> getList(Account entity) {
    log.info("AccountHashtagMappingUnit.getList");

    return repository.findAllByAccount(entity);
  }

  @Override
  public List<AccountHashtagMapping> getList(Hashtag entity) {
    log.info("AccountHashtagMappingUnit.getList");

    return repository.findAllByHashtag(entity);
  }

  @Override
  @Transactional
  public void delete(Account entity) {
    log.info("AccountHashtagMappingUnit.delete");

    repository.deleteAllByAccount(entity);
  }

  @Override
  @Transactional
  public void delete(Hashtag entity) {
    log.info("AccountHashtagMappingUnit.delete");

    repository.deleteAllByHashtag(entity);
  }

}
