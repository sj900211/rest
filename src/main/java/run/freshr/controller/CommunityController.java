package run.freshr.controller;

import javax.validation.Valid;
import run.freshr.common.config.URIConfig;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.community.dto.request.BoardCreateRequest;
import run.freshr.domain.community.dto.request.BoardUpdateRequest;
import run.freshr.domain.community.vo.CommunitySearch;
import run.freshr.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class Community controller.
 *
 * @author [류성재]
 * @implNote 커뮤니티 관리
 * @since 2021. 3. 16. 오후 12:17:08
 */
@RestController
@RequiredArgsConstructor
public class CommunityController {

  /**
   * The Service
   */
  private final CommunityService service;

  // .______     ______        ___      .______       _______
  // |   _  \   /  __  \      /   \     |   _  \     |       \
  // |  |_)  | |  |  |  |    /  ^  \    |  |_)  |    |  .--.  |
  // |   _  <  |  |  |  |   /  /_\  \   |      /     |  |  |  |
  // |  |_)  | |  `--'  |  /  _____  \  |  |\  \----.|  '--'  |
  // |______/   \______/  /__/     \__\ | _| `._____||_______/

  /**
   * Create board response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 게시글 등록
   * @since 2021. 3. 16. 오후 12:17:08
   */
  @Secured(Role.Secured.USER)
  @PostMapping(URIConfig.uriCommunityBoard)
  public ResponseEntity<?> createBoard(@RequestBody @Valid BoardCreateRequest dto) {
    return service.createBoard(dto);
  }

  /**
   * Gets board page.
   *
   * @param search the search
   * @return the board page
   * @author [류성재]
   * @implNote 게시글 정보 조회 - Page
   * @since 2021. 3. 16. 오후 12:17:08
   */
  @Secured({Role.Secured.USER, Role.Secured.MANAGER, Role.Secured.ANONYMOUS})
  @GetMapping(URIConfig.uriCommunityBoard)
  public ResponseEntity<?> getBoardPage(CommunitySearch search) {
    return service.getBoardPage(search);
  }

  /**
   * Gets board.
   *
   * @param id the id
   * @return the board
   * @author [류성재]
   * @implNote 게시글 정보 조회
   * @since 2021. 3. 16. 오후 12:17:08
   */
  @Secured({Role.Secured.USER, Role.Secured.MANAGER, Role.Secured.ANONYMOUS})
  @GetMapping(URIConfig.uriCommunityBoardId)
  public ResponseEntity<?> getBoard(@PathVariable Long id) {
    return service.getBoard(id);
  }

  /**
   * Update board response entity.
   *
   * @param id  the id
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 게시글 정보 수정
   * @since 2021. 3. 16. 오후 12:17:08
   */
  @Secured(Role.Secured.USER)
  @PutMapping(URIConfig.uriCommunityBoardId)
  public ResponseEntity<?> updateBoard(@PathVariable Long id,
      @RequestBody @Valid BoardUpdateRequest dto) {
    return service.updateBoard(id, dto);
  }

  /**
   * Remove board response entity.
   *
   * @param id the id
   * @return the response entity
   * @author [류성재]
   * @implNote 게시글 삭제 처리
   * @since 2021. 3. 16. 오후 12:17:08
   */
  @Secured({Role.Secured.USER, Role.Secured.MANAGER})
  @DeleteMapping(URIConfig.uriCommunityBoardId)
  public ResponseEntity<?> removeBoard(@PathVariable Long id) {
    return service.removeBoard(id);
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
   * @implNote 게시글 첨부파일 정보 조회 - List
   * @since 2021. 3. 16. 오후 12:17:08
   */
  @Secured({Role.Secured.USER, Role.Secured.MANAGER, Role.Secured.ANONYMOUS})
  @GetMapping(URIConfig.uriCommunityBoardIdAttach)
  public ResponseEntity<?> getBoardAttachMappingList(@PathVariable Long id) {
    return service.getBoardAttachMappingList(id);
  }

}
