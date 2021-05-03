package run.freshr.domain.auth.repository;

import run.freshr.domain.auth.entity.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Sign repository.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 계정 관리 Repository
 * @since 2021. 3. 16. 오후 2:27:27
 */
@Repository
public interface SignRepository extends JpaRepository<Sign, Long> {

  /**
   * Exists by username boolean.
   *
   * @param username the username
   * @return the boolean
   * @author [류성재]
   * @implNote Data 있는지 확인 by Username
   * @since 2021. 3. 16. 오후 2:27:27
   */
  Boolean existsByUsername(String username);

  /**
   * Find by username sign.
   *
   * @param username the username
   * @return the sign
   * @author [류성재]
   * @implNote Data 조회 by Username
   * @since 2021. 3. 16. 오후 2:27:27
   */
  Sign findByUsername(String username);

}
