package run.freshr.domain.mapping.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import run.freshr.annotation.Unit;
import run.freshr.domain.community.entity.Board;
import run.freshr.domain.mapping.entity.BoardAttachMapping;
import run.freshr.domain.mapping.repository.BoardAttachMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Board attach mapping unit.
 *
 * @author [류성재]
 * @implNote 연관 관계 관리 > 게시글 첨부 파일 관리 Service
 * @since 2020 -08-10 @author 류성재
 */
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardAttachMappingUnit {

  /**
   * The Repository
   */
  private final BoardAttachMappingRepository repository;

  /**
   * Create long.
   *
   * @param entity the entity
   * @return the long
   * @author [류성재]
   * @implNote 생성
   * @since 2021. 3. 16. 오후 2:49:39
   */
  @Transactional
  public Long create(BoardAttachMapping entity) {
    return repository.save(entity).getId();
  }

  /**
   * Create list.
   *
   * @param entities the entities
   * @return the list
   * @author [류성재]
   * @implNote 생성 - List
   * @since 2021. 3. 16. 오후 2:49:39
   */
  @Transactional
  public List<Long> create(List<BoardAttachMapping> entities) {
    return repository.saveAll(entities).stream().map(BoardAttachMapping::getId)
        .collect(Collectors.toList());
  }

  /**
   * Get board attach mapping.
   *
   * @param id the id
   * @return the board attach mapping
   * @author [류성재]
   * @implNote Data 조회 by ID
   * @since 2021. 3. 16. 오후 2:49:39
   */
  public BoardAttachMapping get(Long id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * Gets list.
   *
   * @param entity the entity
   * @return the list
   * @author [류성재]
   * @implNote Data 조회 by Board - List
   * @since 2021. 3. 16. 오후 2:49:39
   */
  public List<BoardAttachMapping> getList(Board entity) {
    return repository.findAllByBoardAndDelFlagIsFalseAndUseFlagIsTrue(entity);
  }

  /**
   * Delete.
   *
   * @param id the id
   * @author [류성재]
   * @implNote 삭제 by ID
   * @since 2021. 3. 16. 오후 2:49:39
   */
  @Transactional
  public void delete(Long id) {
    repository.deleteById(id);
  }

  /**
   * Delete.
   *
   * @param entity the entity
   * @author [류성재]
   * @implNote 물리 삭제 by Board
   * @since 2021. 3. 16. 오후 2:49:39
   */
  @Transactional
  public void delete(Board entity) {
    repository.deleteByBoard(entity);
  }

  /**
   * Remove.
   *
   * @param entity the entity
   * @author [류성재]
   * @implNote 물리 삭제 by Board
   * @since 2021. 3. 16. 오후 2:49:39
   */
  @Transactional
  public void remove(Board entity) {
    repository.removeByBoard(entity);
  }

}
