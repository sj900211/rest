package run.freshr.domain.mapping.unit;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.mapping.embedded.PostHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.PostHashtagMapping;
import run.freshr.domain.mapping.repository.PostHashtagMappingRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostHashtagMappingUnitImpl implements PostHashtagMappingUnit {

  private final PostHashtagMappingRepository repository;

  @Override
  @Transactional
  public PostHashtagMappingEmbeddedId create(PostHashtagMapping entity) {
    log.info("PostHashtagMappingUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(PostHashtagMappingEmbeddedId id) {
    log.info("PostHashtagMappingUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public PostHashtagMapping get(PostHashtagMappingEmbeddedId id) {
    log.info("PostHashtagMappingUnit.get");

    return repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public void delete(PostHashtagMappingEmbeddedId id) {
    log.info("PostHashtagMappingUnit.delete");

    repository.deleteById(id);

  }

  @Override
  public List<PostHashtagMapping> getList(Post entity) {
    log.info("PostHashtagMappingUnit.getList");

    return repository.findAllByPost(entity);
  }

  @Override
  public List<PostHashtagMapping> getList(Hashtag entity) {
    log.info("PostHashtagMappingUnit.getList");

    return repository.findAllByHashtag(entity);
  }

  @Override
  public void delete(Post entity) {
    log.info("PostHashtagMappingUnit.delete");

    repository.deleteAllByPost(entity);
  }

  @Override
  public void delete(Hashtag entity) {
    log.info("PostHashtagMappingUnit.delete");

    repository.deleteAllByHashtag(entity);
  }

}
