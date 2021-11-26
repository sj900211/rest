package run.freshr.domain.common.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import run.freshr.annotation.Unit;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The Class Attach unit.
 *
 * @author [류성재]
 * @implNote 공통 관리 > 첨부파일 관리 Service
 * @since 2020 -08-10 @author 류성재
 */
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachUnit {

  /**
   * The Repository
   */
  private final AttachRepository repository;

  /**
   * Create long.
   *
   * @param entity the entity
   * @return the long
   * @author [류성재]
   * @implNote 생성
   * @since 2021. 3. 16. 오후 2:42:08
   */
  @Transactional
  public Long create(Attach entity) {
    return repository.save(entity).getId();
  }

  /**
   * Create list.
   *
   * @param entities the entities
   * @return the list
   * @author [류성재]
   * @implNote 생성 - List
   * @since 2021. 3. 16. 오후 2:42:08
   */
  @Transactional
  public List<Long> create(List<Attach> entities) {
    return repository.saveAll(entities).stream().map(Attach::getId).collect(Collectors.toList());
  }

  /**
   * Exists boolean.
   *
   * @param id the id
   * @return the boolean
   * @author [류성재]
   * @implNote Data 있는지 확인 by Id
   * @since 2021. 3. 16. 오후 2:42:08
   */
  public Boolean exists(Long id) {
    return repository.existsByIdAndDelFlagFalseAndUseFlagTrue(id);
  }

  /**
   * Get attach.
   *
   * @param id the id
   * @return the attach
   * @author [류성재]
   * @implNote Data 조회
   * @since 2021. 3. 16. 오후 2:42:08
   */
  public Attach get(Long id) {
    return repository.findByIdAndDelFlagFalseAndUseFlagTrue(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  /**
   * Gets list.
   *
   * @return the list
   * @author [류성재]
   * @implNote Data 조회 - List
   * @since 2021. 3. 16. 오후 2:42:09
   */
  public List<Attach> getList() {
    return repository.findAll();
  }

}
