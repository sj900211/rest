package run.freshr.domain.blog.unit;

import org.springframework.data.domain.Page;
import run.freshr.common.extension.UnitDefaultExtension;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.vo.BlogSearch;

public interface PostUnit extends UnitDefaultExtension<Post, Long> {

  Page<Post> getPage(BlogSearch search, Role role);

}
