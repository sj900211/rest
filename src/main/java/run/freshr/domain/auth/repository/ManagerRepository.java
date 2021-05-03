package run.freshr.domain.auth.repository;

import run.freshr.domain.auth.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface Manager repository.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 관리자 계정 관리 Repository
 * @since 2020 -08-10 @author 류성재
 */
public interface ManagerRepository extends JpaRepository<Manager, Long> {

  /**
   * Find by username manager.
   *
   * @param username the username
   * @return the manager
   * @author [류성재]
   * @implNote Data 조회 by Username
   * @since 2021. 3. 16. 오후 2:27:05
   */
  Manager findByUsername(String username);

  /**
   * Find by del flag is false and use flag is true order by id desc page.
   *
   * @param pageable the pageable
   * @return the page
   * @author [류성재]
   * @implNote Data 조회 - Page
   * @since 2021. 3. 16. 오후 2:27:05
   */
  Page<Manager> findByDelFlagIsFalseAndUseFlagIsTrueOrderByIdDesc(Pageable pageable);

}
