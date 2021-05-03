package run.freshr.domain.auth.repository;

import run.freshr.domain.auth.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface Account repository.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 사용자 계정 관리 Repository
 * @since 2020 -08-10 @author 류성재
 */
public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

  /**
   * Find by username account.
   *
   * @param username the username
   * @return the account
   * @author [류성재]
   * @implNote Data 조회 by Username - Single
   * @since 2021. 3. 16. 오후 2:25:04
   */
  Account findByUsername(String username);

}
