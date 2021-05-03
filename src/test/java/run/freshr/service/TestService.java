package run.freshr.service;

import java.util.List;
import java.util.Map;
import run.freshr.common.security.SecurityUtil;
import run.freshr.common.util.SignUtil;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.entity.Manager;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AuthAccess;
import run.freshr.domain.auth.redis.AuthRefresh;
import run.freshr.domain.auth.service.AccountService;
import run.freshr.domain.auth.service.AuthAccessService;
import run.freshr.domain.auth.service.AuthRefreshService;
import run.freshr.domain.auth.service.ManagerService;
import run.freshr.domain.common.entity.Attach;
import run.freshr.domain.common.service.AttachService;
import run.freshr.domain.community.entity.Board;
import run.freshr.domain.community.service.BoardService;
import run.freshr.domain.mapping.entity.BoardAttachMapping;
import run.freshr.domain.mapping.service.BoardAttachMappingService;
import run.freshr.mapper.EnumMapper;
import run.freshr.mapper.EnumValue;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The Class Test service.
 *
 * @author [류성재]
 * @implNote Test 서비스
 * @since 2021. 2. 25. 오후 5:40:13
 */
@Service
@RequiredArgsConstructor
public class TestService {

  /**
   * The Password encoder
   */
  private final PasswordEncoder passwordEncoder;

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|
  /**
   * The Enum mapper
   */
  private final EnumMapper enumMapper;
  /**
   * The Auth access service
   */
  private final AuthAccessService authAccessService;

  //      ___      __    __  .___________. __    __
  //     /   \    |  |  |  | |           ||  |  |  |
  //    /  ^  \   |  |  |  | `---|  |----`|  |__|  |
  //   /  /_\  \  |  |  |  |     |  |     |   __   |
  //  /  _____  \ |  `--'  |     |  |     |  |  |  |
  // /__/     \__\ \______/      |__|     |__|  |__|
  /**
   * The Auth refresh service
   */
  private final AuthRefreshService authRefreshService;
  // .___  ___.      ___      .__   __.      ___       _______  _______ .______
  // |   \/   |     /   \     |  \ |  |     /   \     /  _____||   ____||   _  \
  // |  \  /  |    /  ^  \    |   \|  |    /  ^  \   |  |  __  |  |__   |  |_)  |
  // |  |\/|  |   /  /_\  \   |  . `  |   /  /_\  \  |  | |_ | |   __|  |      /
  // |  |  |  |  /  _____  \  |  |\   |  /  _____  \ |  |__| | |  |____ |  |\  \----.
  // |__|  |__| /__/     \__\ |__| \__| /__/     \__\ \______| |_______|| _| `._____|
  private final ManagerService managerService;
  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  private final AccountService accountService;
  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  private final AttachService attachService;
  // .______     ______        ___      .______       _______
  // |   _  \   /  __  \      /   \     |   _  \     |       \
  // |  |_)  | |  |  |  |    /  ^  \    |  |_)  |    |  .--.  |
  // |   _  <  |  |  |  |   /  /_\  \   |      /     |  |  |  |
  // |  |_)  | |  `--'  |  /  _____  \  |  |\  \----.|  '--'  |
  // |______/   \______/  /__/     \__\ | _| `._____||_______/
  private final BoardService boardService;
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
  private final BoardAttachMappingService boardAttachMappingService;

  /**
   * Gets enum all.
   *
   * @return the enum all
   * @author [류성재]
   * @implNote Enum 데이터 전체 조회
   * @since 2021. 2. 25. 오후 5:40:13
   */
  public Map<String, List<EnumValue>> getEnumAll() {
    return enumMapper.getAll();
  }

  /**
   * Create auth.
   *
   * @param id   the id
   * @param role the role
   * @author [류성재]
   * @implNote 로그인 처리
   * @since 2021. 2. 25. 오후 5:40:13
   */
  public void createAuth(Long id, Role role) {
    SignUtil.signedAccess.remove();
    SignUtil.signedRefresh.remove();

    // 토큰 발급
    String accessToken = SecurityUtil.createAccessToken(id);
    String refreshToken = SecurityUtil.createRefreshToken(id);

    SignUtil.signedAccess.set(accessToken);
    SignUtil.signedRefresh.set(refreshToken);

    // 토큰 등록
    authAccessService.save(AuthAccess.createRedis(accessToken, id, role));
    authRefreshService
        .save(AuthRefresh.createRedis(refreshToken, authAccessService.get(accessToken)));
  }

  /**
   * Create access.
   *
   * @param accessToken the access token
   * @param id          the id
   * @param role        the role
   * @author [류성재]
   * @implNote Access 토큰 생성
   * @since 2021. 2. 25. 오후 5:40:13
   */
  public void createAccess(String accessToken, Long id, Role role) {
    authAccessService.save(AuthAccess.createRedis(accessToken, id, role));
  }

  /**
   * Gets access.
   *
   * @param id the id
   * @return the access
   * @author [류성재]
   * @implNote Access 토큰 조회
   * @since 2021. 2. 25. 오후 5:40:13
   */
  public AuthAccess getAccess(String id) {
    return authAccessService.get(id);
  }

  /**
   * Create refresh.
   *
   * @param refreshToken the refresh token
   * @param access       the access
   * @author [류성재]
   * @implNote Refresh 토큰 생성
   * @since 2021. 2. 25. 오후 5:40:13
   */
  public void createRefresh(String refreshToken, String access) {
    authRefreshService.save(AuthRefresh.createRedis(refreshToken, getAccess(access)));
  }

  /**
   * Create super long.
   *
   * @param username the username
   * @param name     the name
   * @return the long
   * @author [류성재]
   * @implNote 수퍼 관리자 계정 생성
   * @since 2021. 3. 16. 오후 3:25:09
   */
  public long createSuper(String username, String name) {
    return managerService
        .create(Manager.createSuper(username, passwordEncoder.encode("1234"), name));
  }

  /**
   * Create manager long.
   *
   * @param username the username
   * @param name     the name
   * @return the long
   * @author [류성재]
   * @implNote 일반 관리자 계정 생성
   * @since 2021. 3. 16. 오후 3:25:09
   */
  public long createManager(String username, String name) {
    return managerService
        .create(Manager.createManager(username, passwordEncoder.encode("1234"), name));
  }

  /**
   * Gets manager.
   *
   * @param id the id
   * @return the manager
   * @author [류성재]
   * @implNote 관리자 계정 조회
   * @since 2021. 3. 16. 오후 3:25:09
   */
  public Manager getManager(long id) {
    return managerService.get(id);
  }

  /**
   * Create account long.
   *
   * @param username the username
   * @param name     the name
   * @return the long
   * @author [류성재]
   * @implNote 사용자 계정 생성
   * @since 2021. 3. 16. 오후 3:25:09
   */
  public long createAccount(String username, String name) {
    return accountService
        .create(Account.createEntity(username, passwordEncoder.encode("1234"), name));
  }

  /**
   * Gets account.
   *
   * @param id the id
   * @return the account
   * @author [류성재]
   * @implNote 사용자 계정 조회
   * @since 2021. 3. 16. 오후 3:25:09
   */
  public Account getAccount(long id) {
    return accountService.get(id);
  }

  public long createAttach(String filename, String path) {
    return attachService.create(Attach.createEntity(
        "image/png",
        filename + ".png",
        path + "/" + filename + ".png",
        2048L,
        "alt",
        "title"
    ));
  }

  public Attach getAttach(long id) {
    return attachService.get(id);
  }

  public long createBoard(String title, String content, Account account) {
    return boardService.create(Board.createEntity(
        title,
        content,
        account
    ));
  }

  public Board getBoard(long id) {
    return boardService.get(id);
  }

  public long createBoardAttachMapping(Board board, Attach attach, int sort) {
    return boardAttachMappingService.create(BoardAttachMapping.createEntity(
        board,
        attach,
        sort
    ));
  }

  public BoardAttachMapping getBoardAttachMapping(long id) {
    return boardAttachMappingService.get(id);
  }

  public List<BoardAttachMapping> getBoardAttachMappingAll(Board board) {
    return boardAttachMappingService.getList(board);
  }

}
