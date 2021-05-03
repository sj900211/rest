package run.freshr.domain.common.repository;

import java.util.Optional;
import run.freshr.domain.common.entity.Attach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Attach repository.
 *
 * @author [류성재]
 * @implNote 공통 관리 > 첨부파일 관리 Repository
 * @since 2020 -08-10 @author 류성재
 */
@Repository
public interface AttachRepository extends JpaRepository<Attach, Long> {

  /**
   * Exists by id and del flag false and use flag true boolean.
   *
   * @param id the id
   * @return the boolean
   * @author [류성재]
   * @implNote Data 있는지 확인 by Id
   * @apiNote 기본 조회 조건: 삭제 여부가 FALSE 이면서 사용 여부가 TRUE
   * @since 2020 -08-10 @author 류성재
   */
  Boolean existsByIdAndDelFlagFalseAndUseFlagTrue(Long id);

  /**
   * Find by id and del flag false and use flag true optional.
   *
   * @param id the id
   * @return the optional
   * @author [류성재]
   * @implNote Data 조회 by Id
   * @apiNote 기본 조회 조건: 삭제 여부가 FALSE 이면서 사용 여부가 TRUE
   * @since 2020 -08-10 @author 류성재
   */
  Optional<Attach> findByIdAndDelFlagFalseAndUseFlagTrue(Long id);

}
