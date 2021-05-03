package run.freshr.domain.community.repository;

import run.freshr.domain.community.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface Board repository.
 *
 * @author [류성재]
 * @implNote 커뮤니티 관리 > 게시글 관리 Repository
 * @since 2020 -08-10 @author 류성재
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

  /**
   * Find by del flag is false and use flag is true order by id desc page.
   *
   * @param pageable the pageable
   * @return the page
   * @author [류성재]
   * @implNote Data 조회 - Page
   * @since 2021. 3. 16. 오후 2:46:23
   */
  Page<Board> findByDelFlagIsFalseAndUseFlagIsTrueOrderByIdDesc(Pageable pageable);

}
