package run.freshr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import run.freshr.common.config.URIConfig;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.service.AuthService;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignPasswordRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class Auth controller.
 *
 * @author [류성재]
 * @implNote 권한 관리
 * @since 2021. 3. 16. 오후 12:12:39
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

  /**
   * The Service
   */
  private final AuthService service;

  /**
   * Refresh token response entity.
   *
   * @param request the request
   * @return the response entity
   * @author [류성재]
   * @implNote 토큰 갱신
   * @since 2021. 3. 16. 오후 12:12:39
   */
  @PostMapping(URIConfig.uriAuthToken)
  public ResponseEntity<?> refreshToken(HttpServletRequest request) {
    return service.refreshToken(request);
  }

  /**
   * Sign in response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 로그인
   * @since 2021. 3. 16. 오후 12:12:39
   */
  @Secured(Role.Secured.ANONYMOUS)
  @PostMapping(URIConfig.uriAuthSignIn)
  public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest dto) {
    return service.signIn(dto);
  }

  /**
   * Sign out response entity.
   *
   * @param request the request
   * @return the response entity
   * @author [류성재]
   * @implNote 로그아웃
   * @since 2021. 3. 16. 오후 12:12:39
   */
  @Secured({Role.Secured.SUPER, Role.Secured.MANAGER, Role.Secured.USER})
  @PostMapping(URIConfig.uriAuthSignOut)
  public ResponseEntity<?> signOut(HttpServletRequest request) {
    return service.signOut(request);
  }

  /**
   * Gets info.
   *
   * @return the info
   * @author [류성재]
   * @implNote 로그인한 계정 자신의 정보를 조회
   * @since 2021. 3. 16. 오후 12:12:39
   */
  @Secured({Role.Secured.SUPER, Role.Secured.MANAGER, Role.Secured.USER})
  @GetMapping(URIConfig.uriAuthInfo)
  public ResponseEntity<?> getInfo() {
    return service.getInfo();
  }

  /**
   * Update info response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 로그인한 계정 자신의 정보를 수정
   * @since 2021. 3. 16. 오후 12:12:39
   */
  @Secured({Role.Secured.SUPER, Role.Secured.MANAGER, Role.Secured.USER})
  @PutMapping(URIConfig.uriAuthInfo)
  public ResponseEntity<?> updateInfo(@RequestBody @Valid SignUpdateRequest dto) {
    return service.updateInfo(dto);
  }

  /**
   * Update password response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 로그인한 계정 자신의 비밀번호를 변경
   * @since 2021. 3. 16. 오후 12:12:39
   */
  @Secured({Role.Secured.SUPER, Role.Secured.MANAGER, Role.Secured.USER})
  @PutMapping(URIConfig.uriAuthPassword)
  public ResponseEntity<?> updatePassword(@RequestBody @Valid SignPasswordRequest dto) {
    return service.updatePassword(dto);
  }

  /**
   * Remove info response entity.
   *
   * @return the response entity
   * @author [류성재]
   * @implNote 로그인한 계정 자신을 탈퇴
   * @since 2021. 3. 16. 오후 12:12:39
   */
  @Secured({Role.Secured.SUPER, Role.Secured.MANAGER, Role.Secured.USER})
  @DeleteMapping(URIConfig.uriAuthInfo)
  public ResponseEntity<?> removeInfo() {
    return service.removeInfo();
  }

}
