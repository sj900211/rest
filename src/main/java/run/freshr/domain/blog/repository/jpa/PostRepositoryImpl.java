package run.freshr.domain.blog.repository.jpa;

import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.functional.GlobalFunctional.paging;
import static run.freshr.common.functional.GlobalFunctional.searchEnum;
import static run.freshr.domain.blog.entity.QPost.post;
import static run.freshr.domain.blog.functional.BlogFunctional.checkPermission;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.enumeration.PostPageKeys;
import run.freshr.domain.blog.vo.BlogSearch;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Post> getPage(BlogSearch search, Role role) {
    JPAQuery<Post> query = queryFactory.selectFrom(post)
        .where(post.delFlag.isFalse(), post.useFlag.isTrue(), checkPermission(role));

    String word = search.getWord();

    if (hasLength(word)) {
      query.where(searchEnum(word, PostPageKeys.find(search.getKey())));
    }

    return paging(query, search);
  }

}
