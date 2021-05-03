package run.freshr.domain.auth.vo;

import run.freshr.annotation.SearchClass;
import run.freshr.common.extension.SearchExtension;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Class Auth search.
 *
 * @author [류성재]
 * @implNote 권한 관리 검색 객체
 * @since 2021. 3. 16. 오후 2:32:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SearchClass
public class AuthSearch extends SearchExtension {

}
