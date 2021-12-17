package run.freshr.domain.blog.unit;

import org.springframework.data.domain.Page;
import run.freshr.common.extension.UnitDocumentDefaultExtension;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.elasticsearch.PostSearch;
import run.freshr.domain.blog.vo.BlogSearch;

public interface PostSearchUnit extends UnitDocumentDefaultExtension<PostSearch, Long> {

  Page<PostSearch> getPage(BlogSearch search, Long signedId, Role role);

}
