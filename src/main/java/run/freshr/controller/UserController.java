package run.freshr.controller;

import static run.freshr.common.config.URIConfig.uriUser;
import static run.freshr.common.config.URIConfig.uriUserId;
import static run.freshr.common.util.RestUtil.error;

import javax.validation.Valid;
import run.freshr.common.util.RestUtil;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.user.validator.UserValidator;
import run.freshr.domain.user.vo.UserSearch;
import run.freshr.domain.auth.dto.request.AccountCreateRequest;
import run.freshr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class User controller.
 *
 * @author [류성재]
 * @implNote 사용자 관리
 * @since 2021. 3. 16. 오후 12:21:13
 */
@RestController
@RequiredArgsConstructor
public class UserController {

  /**
   * The Service
   */
  private final UserService service;

  /**
   * The Validator
   */
  private final UserValidator validator;

  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|

  /**
   * Create account response entity.
   *
   * @param dto           the dto
   * @param bindingResult the binding result
   * @return the response entity
   * @author [류성재]
   * @implNote 사용자 계정 등록
   * @since 2021. 3. 16. 오후 12:21:14
   */
  @Secured(Role.Secured.ANONYMOUS)
  @PostMapping(uriUser)
  public ResponseEntity<?> createAccount(@RequestBody @Valid AccountCreateRequest dto,
      BindingResult bindingResult) {
    validator.createAccount(dto, bindingResult);

    if (bindingResult.hasErrors()) {
      return RestUtil.error(bindingResult);
    }

    return service.createAccount(dto);
  }

  /**
   * Gets account page.
   *
   * @param search the search
   * @return the account page
   * @author [류성재]
   * @implNote 사용자 계정 정보 조회 - Page
   * @since 2021. 3. 16. 오후 12:21:14
   */
  @Secured({Role.Secured.MANAGER, Role.Secured.USER, Role.Secured.ANONYMOUS})
  @GetMapping(uriUser)
  public ResponseEntity<?> getAccountPage(UserSearch search) {
    return service.getAccountPage(search);
  }

  /**
   * Gets account.
   *
   * @param id the id
   * @return the account
   * @author [류성재]
   * @implNote 사용자 계정 정보 조회
   * @since 2021. 3. 16. 오후 12:21:14
   */
  @Secured({Role.Secured.MANAGER, Role.Secured.USER, Role.Secured.ANONYMOUS})
  @GetMapping(uriUserId)
  public ResponseEntity<?> getAccount(@PathVariable Long id) {
    return service.getAccount(id);
  }

  /**
   * Remove account response entity.
   *
   * @param id the id
   * @return the response entity
   * @author [류성재]
   * @implNote 사용자 계정 탈퇴 처리
   * @since 2021. 3. 16. 오후 12:21:14
   */
  @Secured(Role.Secured.MANAGER)
  @DeleteMapping(uriUserId)
  public ResponseEntity<?> removeAccount(@PathVariable Long id) {
    return service.removeAccount(id);
  }

}
