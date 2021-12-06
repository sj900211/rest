package run.freshr.domain.common.repository;

import static run.freshr.domain.blog.entity.QPost.post;
import static run.freshr.domain.common.entity.QHashtag.hashtag;
import static run.freshr.domain.common.enumeration.Permission.PASS;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.common.dto.response.HashtagListResponse;
import run.freshr.domain.common.dto.response.QHashtagListResponse;

@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<HashtagListResponse> getList(Role role) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    switch (role) {
      case ROLE_MANAGER:
        booleanBuilder.and(hashtag.postList.any().post.managerGrant.eq(PASS));
        break;
      case ROLE_LEADER:
        booleanBuilder.and(hashtag.postList.any().post.leaderGrant.eq(PASS));
        break;
      case ROLE_COACH:
        booleanBuilder.and(hashtag.postList.any().post.coachGrant.eq(PASS));
        break;
      case ROLE_USER:
        booleanBuilder.and(hashtag.postList.any().post.userGrant.eq(PASS));
        break;
      case ROLE_ANONYMOUS:
        booleanBuilder.and(hashtag.postList.any().post.anonymousGrant.eq(PASS));
        break;
      default:
    }

    return queryFactory
        .select(new QHashtagListResponse(
            hashtag.id,
            JPAExpressions
                .select(post.count())
                .from(post)
                .where(hashtag.postList.any().post.eq(post), booleanBuilder)
        ))
        .from(hashtag)
        .orderBy(hashtag.id.asc())
        .fetch();
  }

}
