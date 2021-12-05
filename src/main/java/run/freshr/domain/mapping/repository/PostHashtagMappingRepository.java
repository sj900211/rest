package run.freshr.domain.mapping.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.mapping.embedded.PostHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.PostHashtagMapping;

public interface PostHashtagMappingRepository
    extends JpaRepository<PostHashtagMapping, PostHashtagMappingEmbeddedId> {

  List<PostHashtagMapping> findAllByPost(Post entity);

  List<PostHashtagMapping> findAllByHashtag(Hashtag entity);

  void deleteAllByPost(Post entity);

  void deleteAllByHashtag(Hashtag entity);
}
