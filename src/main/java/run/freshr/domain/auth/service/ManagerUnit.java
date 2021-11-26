package run.freshr.domain.auth.service;

import javax.persistence.EntityNotFoundException;
import run.freshr.annotation.Unit;
import run.freshr.domain.auth.entity.Manager;
import run.freshr.domain.auth.repository.ManagerRepository;
import run.freshr.domain.setting.vo.SettingSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Manager unit.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 관리자 계정 관리 Service
 * @since 2020 -08-10 @author 류성재
 */
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerUnit {

  /**
   * The Repository
   */
  private final ManagerRepository repository;

  /**
   * Create long.
   *
   * @param entity the entity
   * @return the long
   * @author [류성재]
   * @implNote 생성
   * @since 2021. 3. 16. 오후 2:30:44
   */
  @Transactional
  public Long create(Manager entity) {
    return repository.save(entity).getId();
  }

  /**
   * Exists boolean.
   *
   * @param id the id
   * @return the boolean
   * @author [류성재]
   * @implNote Data 있는지 확인 by ID
   * @since 2021. 3. 16. 오후 2:30:44
   */
  public Boolean exists(Long id) {
    return repository.existsById(id);
  }

  /**
   * Get manager.
   *
   * @param id the id
   * @return the manager
   * @author [류성재]
   * @implNote Data 조회 by ID
   * @since 2021. 3. 16. 오후 2:30:44
   */
  public Manager get(Long id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * Get manager.
   *
   * @param username the username
   * @return the manager
   * @author [류성재]
   * @implNote Data 조회 by Username
   * @since 2021. 3. 16. 오후 2:30:44
   */
  public Manager get(String username) {
    return repository.findByUsername(username);
  }

  /**
   * Gets page.
   *
   * @param search the search
   * @return the page
   * @author [류성재]
   * @implNote Data 조회 - Page
   * @since 2021. 3. 16. 오후 2:30:44
   */
  public Page<Manager> getPage(SettingSearch search) {
    return repository.findByDelFlagIsFalseAndUseFlagIsTrueOrderByIdDesc(PageRequest.of(
        search.getPage(),
        search.getCpp()
    ));
  }

}
