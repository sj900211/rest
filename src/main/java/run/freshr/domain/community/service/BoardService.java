package run.freshr.domain.community.service;

import javax.persistence.EntityNotFoundException;
import run.freshr.domain.community.vo.CommunitySearch;
import run.freshr.domain.community.entity.Board;
import run.freshr.domain.community.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Board service.
 *
 * @author [류성재]
 * @implNote 커뮤니티 관리 > 게시글 관리 Service
 * @since 2020 -08-10 @author 류성재
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

  /**
   * The Repository
   */
  private final BoardRepository repository;

  /**
   * Create long.
   *
   * @param entity the entity
   * @return the long
   * @author [류성재]
   * @implNote 생성
   * @since 2021. 3. 16. 오후 2:46:51
   */
  @Transactional
  public Long create(Board entity) {
    return repository.save(entity).getId();
  }

  /**
   * Exists boolean.
   *
   * @param id the id
   * @return the boolean
   * @author [류성재]
   * @implNote Data 있는지 확인 by ID
   * @since 2021. 3. 16. 오후 2:46:51
   */
  public Boolean exists(Long id) {
    return repository.existsById(id);
  }

  /**
   * Get board.
   *
   * @param id the id
   * @return the board
   * @author [류성재]
   * @implNote Data 조회 by ID
   * @since 2021. 3. 16. 오후 2:46:51
   */
  public Board get(Long id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * Gets page.
   *
   * @param search the search
   * @return the page
   * @author [류성재]
   * @implNote Data 조회 - Page
   * @since 2021. 3. 16. 오후 2:46:51
   */
  public Page<Board> getPage(CommunitySearch search) {
    return repository.findByDelFlagIsFalseAndUseFlagIsTrueOrderByIdDesc(PageRequest.of(
        search.getPage(),
        search.getCpp()
    ));
  }

}
