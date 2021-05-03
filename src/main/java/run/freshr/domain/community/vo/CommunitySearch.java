package run.freshr.domain.community.vo;

import run.freshr.common.extension.SearchExtension;
import run.freshr.annotation.SearchClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Class Community search.
 *
 * @author [류성재]
 * @implNote 커뮤니티 관리 검색 객체
 * @since 2020 -08-10 @author 류성재
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SearchClass
public class CommunitySearch extends SearchExtension {

}
