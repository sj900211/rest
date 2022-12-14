package run.freshr.domain.common.repository.jpa;

import static run.freshr.domain.blog.entity.QPost.post;
import static run.freshr.domain.blog.functional.BlogFunctional.checkPermission;
import static run.freshr.domain.common.entity.QHashtag.hashtag;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.common.dto.response.HashtagCountResponse;
import run.freshr.domain.common.dto.response.QHashtagCountResponse;

@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<HashtagCountResponse> getList(Role role) {
    return queryFactory
        .select(new QHashtagCountResponse(
            hashtag.id,
            JPAExpressions
                .select(post.count())
                .from(post)
                .where(
                    hashtag.postList.any().post.eq(post),
                    checkPermission(role)
                )
        ))
        .from(hashtag)
        .orderBy(hashtag.id.asc())
        .fetch();
  }

}
