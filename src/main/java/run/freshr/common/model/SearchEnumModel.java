package run.freshr.common.model;

import com.querydsl.core.BooleanBuilder;
import run.freshr.mapper.EnumModel;

public interface SearchEnumModel extends EnumModel {

  BooleanBuilder search(String word);

}
