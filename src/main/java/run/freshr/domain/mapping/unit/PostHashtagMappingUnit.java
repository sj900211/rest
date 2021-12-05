package run.freshr.domain.mapping.unit;

import java.util.List;
import run.freshr.common.extension.UnitDefaultExtension;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.common.entity.Hashtag;
import run.freshr.domain.mapping.embedded.PostHashtagMappingEmbeddedId;
import run.freshr.domain.mapping.entity.PostHashtagMapping;

public interface PostHashtagMappingUnit
    extends UnitDefaultExtension<PostHashtagMapping, PostHashtagMappingEmbeddedId> {

  List<PostHashtagMapping> getList(Post entity);

  List<PostHashtagMapping> getList(Hashtag entity);

  void delete(Post entity);

  void delete(Hashtag entity);

}
