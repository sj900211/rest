package run.freshr.domain.auth.repository;

import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.user.vo.UserSearch;
import org.springframework.data.domain.Page;

/**
 * The Interface Account repository custom.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 사용자 계정 관리 Repository Custom
 * @since 2020 -08-10 @author 류성재
 */
public interface AccountRepositoryCustom {

  /**
   * Gets page.
   *
   * @param search the search
   * @return the page
   * @author [류성재]
   * @implNote Data 조회 - Page
   * @since 2021. 3. 16. 오후 2:25:33
   */
  Page<Account> getPage(UserSearch search);

}
