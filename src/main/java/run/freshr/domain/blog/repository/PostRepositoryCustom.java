package run.freshr.domain.blog.repository;

import org.springframework.data.domain.Page;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.vo.BlogSearch;

public interface PostRepositoryCustom {

  Page<Post> getPage(BlogSearch search, Role role);

}
