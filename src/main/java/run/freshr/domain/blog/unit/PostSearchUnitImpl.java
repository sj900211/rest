package run.freshr.domain.blog.unit;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.elasticsearch.PostSearch;
import run.freshr.domain.blog.repository.elasticsearch.PostSearchRepository;
import run.freshr.domain.blog.vo.BlogSearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostSearchUnitImpl implements PostSearchUnit {

  private final PostSearchRepository repository;

  @Override
  @Transactional
  public void save(PostSearch document) {
    log.info("PostSearchUnitImpl.save");

    repository.save(document);
  }

  @Override
  public Boolean exists(Long id) {
    log.info("PostSearchUnitImpl.exists");

    return repository.existsById(id);
  }

  @Override
  public PostSearch get(Long id) {
    log.info("PostSearchUnitImpl.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    log.info("PostSearchUnitImpl.delete");

    repository.deleteById(id);
  }

  @Override
  public Page<PostSearch> getPage(BlogSearch search, Long signedId, Role role) {
    log.info("PostSearchUnitImpl.getPage");

    return repository.searchPage(search, signedId, role);
  }

}
