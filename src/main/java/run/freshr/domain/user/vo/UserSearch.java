package run.freshr.domain.user.vo;

import run.freshr.annotation.SearchClass;
import run.freshr.common.extension.SearchExtension;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Class User search.
 *
 * @author [류성재]
 * @implNote 사용자 관리 검색 객체
 * @since 2020 -08-10 @author 류성재
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SearchClass
public class UserSearch extends SearchExtension {

}
