package run.freshr.domain.auth.service;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.repository.AccountRepository;
import run.freshr.domain.user.vo.UserSearch;

/**
 * The Class Account unit.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 사용자 계정 관리 Service
 * @since 2020 -08-10 @author 류성재
 */
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountUnit {

  /**
   * The Repository
   */
  private final AccountRepository repository;

  /**
   * Create long.
   *
   * @param entity the entity
   * @return the long
   * @author [류성재]
   * @implNote 생성
   * @since 2021. 3. 16. 오후 2:28:11
   */
  @Transactional
  public Long create(Account entity) {
    return repository.save(entity).getId();
  }

  /**
   * Exists boolean.
   *
   * @param id the id
   * @return the boolean
   * @author [류성재]
   * @implNote Data 있는지 확인 by ID
   * @since 2021. 3. 16. 오후 2:28:11
   */
  public Boolean exists(Long id) {
    return repository.existsById(id);
  }

  /**
   * Get account.
   *
   * @param id the id
   * @return the account
   * @author [류성재]
   * @implNote Data 조회 by ID
   * @since 2021. 3. 16. 오후 2:28:11
   */
  public Account get(Long id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * Get account.
   *
   * @param username the username
   * @return the account
   * @author [류성재]
   * @implNote Data 조회 by Username
   * @since 2021. 3. 16. 오후 2:28:11
   */
  public Account get(String username) {
    return repository.findByUsername(username);
  }

  /**
   * Gets page.
   *
   * @param search the search
   * @return the page
   * @author [류성재]
   * @implNote Data 조회 - Page
   * @since 2021. 3. 16. 오후 2:28:11
   */
  public Page<Account> getPage(UserSearch search) {
    return repository.getPage(search);
  }

}
