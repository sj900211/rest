package run.freshr.domain.blog.unit;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.repository.PostRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostUnitImpl implements PostUnit {

  private final PostRepository repository;

  @Override
  @Transactional
  public Long create(Post entity) {
    log.info("PostUnitImpl.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long id) {
    log.info("PostUnitImpl.exists");

    return repository.existsById(id);
  }

  @Override
  public Post get(Long id) {
    log.info("PostUnitImpl.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    log.info("PostUnitImpl.delete");

    repository.deleteById(id);
  }

}
