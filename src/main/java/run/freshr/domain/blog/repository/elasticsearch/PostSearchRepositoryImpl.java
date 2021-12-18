package run.freshr.domain.blog.repository.elasticsearch;

import static org.elasticsearch.search.sort.SortOrder.DESC;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.domain.auth.enumeration.Role.ROLE_ANONYMOUS;
import static run.freshr.domain.blog.entity.QPost.post;

import com.querydsl.core.types.Path;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.blog.elasticsearch.PostSearch;
import run.freshr.domain.blog.enumeration.PostPageKeys;
import run.freshr.domain.blog.vo.BlogSearch;

@RequiredArgsConstructor
public class PostSearchRepositoryImpl implements PostSearchRepositoryCustom {

  private final ElasticsearchOperations elasticsearchOperations;

  @Override
  public Page<PostSearch> searchPage(BlogSearch search, Long signedId, Role role) {
    PageRequest pageRequest = of(search.getPage() - 1, search.getSize());
    BoolQueryBuilder compareBuilder = QueryBuilders.boolQuery();
    BoolQueryBuilder grantCompare = QueryBuilders.boolQuery();

    if (!role.equals(ROLE_ANONYMOUS)) {
      grantCompare.should(QueryBuilders.matchQuery(field(post.creator.id), signedId));
    }

    switch (role) {
      case ROLE_MANAGER:
        grantCompare.should(QueryBuilders.matchQuery("managerGrant", true));
        break;

      case ROLE_LEADER:
        grantCompare.should(QueryBuilders.matchQuery("leaderGrant", true));
        break;

      case ROLE_COACH:
        grantCompare.should(QueryBuilders.matchQuery("coachGrant", true));
        break;

      case ROLE_USER:
        grantCompare.should(QueryBuilders.matchQuery("userGrant", true));
        break;

      case ROLE_ANONYMOUS:
        grantCompare.should(QueryBuilders.matchQuery("anonymousGrant", true));
        break;
    }

    compareBuilder.must(grantCompare);

    String word = search.getWord();

    if (hasLength(word)) {
      BoolQueryBuilder keywordCompare = QueryBuilders.boolQuery();
      PostPageKeys.find(search.getKey())
          .getPaths()
          .forEach(item -> keywordCompare
              .should(QueryBuilders.wildcardQuery(field(item), "*" + word + "*")));

      compareBuilder.must(keywordCompare);
    }

    FieldSortBuilder sort = SortBuilders.fieldSort(field(post.id)).order(DESC);

    NativeSearchQuery query = new NativeSearchQueryBuilder()
        .withQuery(compareBuilder)
        .withSort(sort)
        .build();

    long totalElements = elasticsearchOperations.count(query, PostSearch.class);

    query.setPageable(pageRequest);

    List<PostSearch> list = elasticsearchOperations.search(query, PostSearch.class)
        .stream()
        .map(SearchHit::getContent)
        .collect(Collectors.toList());

    return new PageImpl<>(list, pageRequest, totalElements);
  }

  private String field(Path<?> path) {
    String qPath = path.toString();
    int qDotPoint = qPath.indexOf(".") + 1;
    String target = qPath.substring(0, qDotPoint);
    return qPath.replace(target, "").replace(")", "");
  }

}
