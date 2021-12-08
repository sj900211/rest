package run.freshr.common.functional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;

@FunctionalInterface
public interface SearchKeywordFunctional {

  BooleanBuilder search(String word, List<StringPath> paths);

}
