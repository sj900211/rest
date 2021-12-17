package run.freshr.domain.blog.unit;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.repository.jpa.PostRepository;
import run.freshr.domain.blog.vo.BlogSearch;

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

  @Override
  public Page<Post> getPage(BlogSearch search, Role role) {
    log.info("PostUnitImpl.getPage");

    return repository.getPage(search, role);
  }
}
