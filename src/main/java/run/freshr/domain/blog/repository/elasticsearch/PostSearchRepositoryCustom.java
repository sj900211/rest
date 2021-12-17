package run.freshr.domain.blog.repository.elasticsearch;

import org.springframework.data.domain.Page;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.elasticsearch.PostSearch;
import run.freshr.domain.blog.vo.BlogSearch;

public interface PostSearchRepositoryCustom {

  Page<PostSearch> searchPage(BlogSearch search, Long signedId, Role role);

}
