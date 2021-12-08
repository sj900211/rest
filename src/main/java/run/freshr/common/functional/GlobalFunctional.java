package run.freshr.common.functional;

import static java.lang.Byte.parseByte;
import static java.lang.Integer.toBinaryString;
import static org.springframework.data.domain.PageRequest.of;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import run.freshr.common.extension.SearchExtension;
import run.freshr.common.model.SearchEnumModel;

@Slf4j
public class GlobalFunctional {

  public static byte getBinaryAnd(Integer x, Integer y) {
    log.info("GlobalFunctional.getBinaryAnd");

    final GetBinaryAndFunctional GET_BINARY_AND_FUNCTIONAL = (functionalX, functionalY) ->
        parseByte(toBinaryString(functionalX & functionalY));

    return GET_BINARY_AND_FUNCTIONAL.get(x, y);
  }

  public static BooleanBuilder searchKeyword(String word,
      List<StringPath> paths) {
    log.info("GlobalFunctional.searchKeyword");

    final SearchKeywordFunctional SEARCH_KEYWORD_FUNCTIONAL =
        (functionalWord, functionalPaths) -> {
          BooleanBuilder booleanBuilder = new BooleanBuilder();

          functionalPaths.forEach(path ->
              booleanBuilder.or(path.containsIgnoreCase(functionalWord)));

          return booleanBuilder;
        };

    return SEARCH_KEYWORD_FUNCTIONAL.search(word, paths);
  }

  public static <E extends SearchEnumModel> BooleanBuilder searchEnum(String word, E enumeration) {
    log.info("GlobalFunctional.searchEnum");

    final SearchEnumFunctional<E> SEARCH_ENUM_FUNCTIONAL =
        (functionalWord, functionalEnumeration) -> functionalEnumeration.search(functionalWord);

    return SEARCH_ENUM_FUNCTIONAL.search(word, enumeration);
  }

  public static <E, S extends SearchExtension> Page<E> paging(JPAQuery<E> query, S search) {
    log.info("GlobalFunctional.paging");

    final PagingFunctional<E, S> PAGING_FUNCTIONAL = (functionalQuery, functionalSearch) -> {
      PageRequest pageRequest = of(functionalSearch.getPage() - 1, functionalSearch.getSize());

      functionalQuery.offset(pageRequest.getOffset())
          .limit(pageRequest.getPageSize());

      QueryResults<E> result = functionalQuery.fetchResults();

      return new PageImpl<>(result.getResults(), pageRequest, result.getTotal());
    };

    return PAGING_FUNCTIONAL.paging(query, search);
  }

}
