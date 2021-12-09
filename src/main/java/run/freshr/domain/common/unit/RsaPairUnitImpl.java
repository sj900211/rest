package run.freshr.domain.common.unit;

import static run.freshr.common.util.RestUtil.getConfig;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.common.redis.RsaPair;
import run.freshr.domain.common.repository.RsaPairRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RsaPairUnitImpl implements RsaPairUnit {

  private final RsaPairRepository repository;

  @Override
  @Transactional
  public void save(RsaPair redis) {
    log.info("RsaPairUnit.save");

    repository.save(redis);
  }

  @Override
  public Boolean exists(String id) {
    log.info("RsaPairUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public RsaPair get(String id) {
    log.info("RsaPairUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(String id) {
    log.info("RsaPairUnit.delete");

    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void delete(Iterable<RsaPair> list) {
    log.info("RsaPairUnit.delete");

    repository.deleteAll(list);
  }

  @Override
  public Iterable<RsaPair> getList() {
    log.info("RsaPairUnit.getList");

    return repository.findAll();
  }

  @Override
  @Transactional
  public Boolean checkRsa(String encodePublicKey) {
    log.info("RsaPairUnit.checkRsa");

    Optional<RsaPair> optional = repository.findById(encodePublicKey);

    if (optional.isEmpty()) {
      return false;
    }

    RsaPair redis = optional.get();

    if (redis.getCreateDt()
        .plusSeconds(getConfig().getRsaLimit())
        .isBefore(LocalDateTime.now())) {
      repository.deleteById(encodePublicKey);

      return false;
    }

    return true;
  }

}
