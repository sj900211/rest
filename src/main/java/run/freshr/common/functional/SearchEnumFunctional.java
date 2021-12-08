package run.freshr.common.functional;

import com.querydsl.core.BooleanBuilder;
import run.freshr.common.model.SearchEnumModel;

@FunctionalInterface
public interface SearchEnumFunctional<E extends SearchEnumModel> {

  BooleanBuilder search(String word, E enumeration);

}
