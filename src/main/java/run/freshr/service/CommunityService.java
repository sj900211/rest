package run.freshr.service;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static run.freshr.common.util.RestUtil.error;
import static run.freshr.common.util.RestUtil.ok;

import java.util.List;
import java.util.stream.Collectors;
import run.freshr.common.util.RestUtil;
import run.freshr.domain.common.service.AttachUnit;
import run.freshr.domain.common.dto.request.AttachSortRequest;
import run.freshr.domain.common.dto.response.IdResponse;
import run.freshr.domain.community.dto.request.BoardCreateRequest;
import run.freshr.domain.community.dto.request.BoardUpdateRequest;
import run.freshr.domain.community.dto.response.BoardResponse;
import run.freshr.domain.community.entity.Board;
import run.freshr.domain.community.service.BoardUnit;
import run.freshr.domain.community.vo.CommunitySearch;
import run.freshr.domain.mapping.dto.response.BoardAttachMappingResponse;
import run.freshr.domain.mapping.entity.BoardAttachMapping;
import run.freshr.domain.mapping.service.BoardAttachMappingUnit;
import run.freshr.util.MapperUtil;
import run.freshr.util.XssUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Community service.
 *
 * @author [류성재]
 * @implNote 커뮤니티 관리 Service
 * @since 2020 -08-10 @author 류성재
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

  /**
   * The Board service
   */
  private final BoardUnit boardUnit;
  /**
   * The Board attach mapping service
   */
  private final BoardAttachMappingUnit boardAttachMappingUnit;
  /**
   * The Attach service
   */
  private final AttachUnit attachUnit;

  // .______     ______        ___      .______       _______
  // |   _  \   /  __  \      /   \     |   _  \     |       \
  // |  |_)  | |  |  |  |    /  ^  \    |  |_)  |    |  .--.  |
  // |   _  <  |  |  |  |   /  /_\  \   |      /     |  |  |  |
  // |  |_)  | |  `--'  |  /  _____  \  |  |\  \----.|  '--'  |
  // |______/   \______/  /__/     \__\ | _| `._____||_______/

  /**
   * Gets board page.
   *
   * @param search the search
   * @return the board page
   * @author [류성재]
   * @implNote 게시글 조회 - Page
   * @since 2021. 3. 16. 오후 3:07:24
   */
  public ResponseEntity<?> getBoardPage(CommunitySearch search) {
    Page<BoardResponse> page = boardUnit
        .getPage(search)
        .map(board -> MapperUtil.map(board, BoardResponse.class));

    return RestUtil.ok(page);
  }

  /**
   * Gets board.
   *
   * @param id the id
   * @return the board
   * @author [류성재]
   * @implNote 게시글 조회
   * @since 2021. 3. 16. 오후 3:07:24
   */
  @Transactional
  public ResponseEntity<?> getBoard(Long id) {
    Board entity = boardUnit.get(id);

    entity.addHit();

    BoardResponse data = MapperUtil.map(entity, BoardResponse.class);

    return ok(data);
  }

  /**
   * Create board response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 게시글 생성
   * @since 2021. 3. 16. 오후 3:07:24
   */
  @Transactional
  public ResponseEntity<?> createBoard(BoardCreateRequest dto) {
    long id = boardUnit.create(Board.createEntity(
        dto.getTitle(),
        XssUtil.xssBasicIgnoreImg(dto.getContent()),
        RestUtil.getSignedAccount()
    ));
    Board entity = boardUnit.get(id);

    List<AttachSortRequest> attachList = dto.getAttachList();

    if (!isNull(attachList)) {
      boardAttachMappingUnit.create(attachList
          .stream()
          .map(item -> BoardAttachMapping.createEntity(
              entity,
              attachUnit.get(item.getAttach().getId()),
              ofNullable(item.getSort()).orElse(0)
          )).collect(Collectors.toList()));
    }

    return ok(IdResponse.builder().id(id).build());
  }

  /**
   * Update board response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 게시글 수정
   * @since 2021. 3. 16. 오후 3:07:24
   */
  @Transactional
  public ResponseEntity<?> updateBoard(Long id, BoardUpdateRequest dto) {
    Board entity = boardUnit.get(id);

    if (!entity.owner(RestUtil.getSignedAccount())) {
      return error(RestUtil.getExceptions().getAccessDenied());
    }

    entity.updateEntity(
        dto.getTitle(),
        XssUtil.xssBasicIgnoreImg(dto.getContent()),
        RestUtil.getSignedAccount()
    );

    boardAttachMappingUnit.remove(entity);

    List<AttachSortRequest> attachList = dto.getAttachList();

    if (!isNull(attachList)) {
      boardAttachMappingUnit.create(attachList
          .stream()
          .map(item -> BoardAttachMapping.createEntity(
              entity,
              attachUnit.get(item.getAttach().getId()),
              ofNullable(item.getSort()).orElse(0)
          )).collect(Collectors.toList()));
    }

    return RestUtil.ok();
  }

  /**
   * Remove board response entity.
   *
   * @param id the id
   * @return the response entity
   * @author [류성재]
   * @implNote 게시글 삭제
   * @since 2021. 3. 16. 오후 3:07:24
   */
  @Transactional
  public ResponseEntity<?> removeBoard(Long id) {
    Board entity = boardUnit.get(id);

    entity.removeEntity();

    boardAttachMappingUnit.remove(entity);

    return RestUtil.ok();
  }

  // .______     ______        ___      .______       _______
  // |   _  \   /  __  \      /   \     |   _  \     |       \
  // |  |_)  | |  |  |  |    /  ^  \    |  |_)  |    |  .--.  |
  // |   _  <  |  |  |  |   /  /_\  \   |      /     |  |  |  |
  // |  |_)  | |  `--'  |  /  _____  \  |  |\  \----.|  '--'  |
  // |______/   \______/  /__/     \__\ | _| `._____||_______/
  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  // .___  ___.      ___      .______   .______    __  .__   __.   _______
  // |   \/   |     /   \     |   _  \  |   _  \  |  | |  \ |  |  /  _____|
  // |  \  /  |    /  ^  \    |  |_)  | |  |_)  | |  | |   \|  | |  |  __
  // |  |\/|  |   /  /_\  \   |   ___/  |   ___/  |  | |  . `  | |  | |_ |
  // |  |  |  |  /  _____  \  |  |      |  |      |  | |  |\   | |  |__| |
  // |__|  |__| /__/     \__\ | _|      | _|      |__| |__| \__|  \______|

  /**
   * Gets board attach mapping list.
   *
   * @param id the id
   * @return the board attach mapping list
   * @author [류성재]
   * @implNote 게시글 첨부 파일 조회 - List
   * @since 2021. 3. 16. 오후 3:07:24
   */
  @Transactional
  public ResponseEntity<?> getBoardAttachMappingList(Long id) {
    Board entity = boardUnit.get(id);
    List<BoardAttachMapping> entityList = boardAttachMappingUnit.getList(entity);
    List<BoardAttachMappingResponse> list = MapperUtil
        .map(entityList, BoardAttachMappingResponse.class);

    return RestUtil.ok(list);
  }

}
