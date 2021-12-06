package run.freshr.domain.common.unit;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.common.dto.response.HashtagListResponse;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.common.repository.HashtagRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagUnitImpl implements HashtagUnit {

  private final HashtagRepository repository;

  @Override
  @Transactional
  public String create(Hashtag entity) {
    log.info("HashtagUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(String id) {
    log.info("HashtagUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Hashtag get(String id) {
    log.info("HashtagUnit.get");

    return repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(String id) {
    log.info("HashtagUnit.delete");

    repository.deleteById(id);
  }

  @Override
  public List<HashtagListResponse> getList(Role role) {
    log.info("HashtagUnit.getList");

    return repository.getList(role);
  }

}
