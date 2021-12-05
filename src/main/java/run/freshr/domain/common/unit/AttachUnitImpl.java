package run.freshr.domain.common.unit;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.repository.AttachRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachUnitImpl implements AttachUnit {

  private final AttachRepository repository;

  @Override
  @Transactional
  public Long create(Attach entity) {
    log.info("AttachUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  @Transactional
  public List<Long> create(List<Attach> entities) {
    log.info("AttachUnit.create");

    return repository.saveAll(entities).stream().map(Attach::getId).collect(Collectors.toList());
  }

  @Override
  public Boolean exists(Long id) {
    log.info("AttachUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Attach get(Long id) {
    log.info("AttachUnit.get");

    return repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public List<Attach> getList() {
    log.info("AttachUnit.getList");

    return repository.findAll();
  }

  @Override
  public void delete(Long id) {
    log.info("AttachUnit.delete");

    repository.deleteById(id);

  }

}
