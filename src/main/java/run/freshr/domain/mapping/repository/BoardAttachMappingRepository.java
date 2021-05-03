package run.freshr.domain.mapping.repository;

import java.util.List;
import run.freshr.domain.community.entity.Board;
import run.freshr.domain.mapping.entity.BoardAttachMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The Interface Board attach mapping repository.
 *
 * @author [류성재]
 * @implNote 연관 관계 관리 > 게시글 첨부 파일 관리 Repository
 * @since 2020 -08-10 @author 류성재
 */
@Repository
public interface BoardAttachMappingRepository extends JpaRepository<BoardAttachMapping, Long> {

  /**
   * Find all by board and del flag is false and use flag is true list.
   *
   * @param entity the entity
   * @return the list
   * @author [류성재]
   * @implNote Data 조회 by Board - List
   * @since 2021. 3. 16. 오후 2:48:55
   */
  List<BoardAttachMapping> findAllByBoardAndDelFlagIsFalseAndUseFlagIsTrue(Board entity);

  /**
   * Delete by board.
   *
   * @param entity the entity
   * @author [류성재]
   * @implNote 물리 삭제 by Board
   * @since 2021. 3. 16. 오후 2:48:55
   */
  void deleteByBoard(Board entity);

  /**
   * Remove by board.
   *
   * @param entity the entity
   * @author [류성재]
   * @implNote 논리 삭제 by Board
   * @since 2021. 3. 16. 오후 2:48:55
   */
  @Modifying
  @Query("update BoardAttachMapping T set T.delFlag = true, T.useFlag = false where T.board = :entity")
  void removeByBoard(Board entity);

}
