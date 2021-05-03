package run.freshr.service;

import static java.time.Duration.between;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static run.freshr.common.util.RestUtil.error;
import static run.freshr.common.util.RestUtil.ok;
import static run.freshr.util.CryptoUtil.decryptBase64;
import static run.freshr.util.MapperUtil.map;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import run.freshr.common.model.ResponseModel;
import run.freshr.common.security.SecurityUtil;
import run.freshr.common.util.RestUtil;
import run.freshr.domain.auth.entity.Sign;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AuthAccess;
import run.freshr.domain.auth.redis.AuthRefresh;
import run.freshr.domain.auth.service.AuthAccessService;
import run.freshr.domain.auth.service.AuthRefreshService;
import run.freshr.domain.auth.service.SignService;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignPasswordRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;
import run.freshr.domain.auth.dto.response.AccountResponse;
import run.freshr.domain.auth.dto.response.ManagerResponse;
import run.freshr.domain.auth.dto.response.RefreshTokenResponse;
import run.freshr.domain.auth.dto.response.SignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class Auth service.
 *
 * @author [류성재]
 * @implNote Auth 비즈니스 서비스
 * @since 2021. 3. 2. 오후 12:09:09
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

  /**
   * The Manager service
   */
  private final SignService signService;

  /**
   * The Auth access service
   */
  private final AuthAccessService authAccessService;
  /**
   * The Auth refresh service
   */
  private final AuthRefreshService authRefreshService;

  /**
   * The Password encoder
   */
  private final PasswordEncoder passwordEncoder;

  /**
   * Sign in response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 로그인
   * @since 2021. 3. 2. 오후 12:09:09
   */
  @Transactional
  public ResponseEntity<?> signIn(SignInRequest dto) {
    String username = decryptBase64(dto.getUsername());

    if (!signService.exists(username)) {
      return error(RestUtil.getExceptions().getEntityNotFound());
    }

    Sign entity = signService.get(username);

    if (entity.getDelFlag()) {
      return error(RestUtil.getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(RestUtil.getExceptions().getUnAuthenticated());
    }

    if (!passwordEncoder.matches(decryptBase64(dto.getPassword()), entity.getPassword())) {
      return error(RestUtil.getExceptions().getUnAuthenticated());
    }

    entity.updateSignDt();

    Long id = entity.getId();

    // 토큰 발급
    String accessToken = SecurityUtil.createAccessToken(id);
    String refreshToken = SecurityUtil.createRefreshToken(id);

    // 토큰 등록
    authAccessService.save(AuthAccess.createRedis(accessToken, id, entity.getPrivilege().getRole()));
    authRefreshService.save(AuthRefresh.createRedis(refreshToken, authAccessService.get(accessToken)));

    SignInResponse response = SignInResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();

    return ok(response);
  }

  /**
   * Sign out response entity.
   *
   * @param request the request
   * @return the response entity
   * @author [류성재]
   * @implNote 로그아웃
   * @since 2021. 3. 2. 오후 12:09:09
   */
  @Transactional
  public ResponseEntity<?> signOut(HttpServletRequest request) {
    String jwt = ofNullable(request.getHeader("Authorization")).orElse("");
    String token = jwt.replace("Bearer ", "");

    if (authAccessService.exists(token)) {
      authRefreshService.delete(authAccessService.get(token));
    }

    authAccessService.delete(token);

    return RestUtil.ok();
  }

  /**
   * Update password response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 로그인한 계정 비밀번호 변경
   * @since 2021. 3. 2. 오후 12:09:09
   */
  @Transactional
  public ResponseEntity<?> updatePassword(SignPasswordRequest dto) {
    Sign entity = signService.get(RestUtil.getSignedId());

    if (!passwordEncoder.matches(decryptBase64(dto.getOriginPassword()), entity.getPassword())) {
      return error(RestUtil.getExceptions().getUnAuthenticated());
    }

    entity.updatePassword(passwordEncoder.encode(decryptBase64(dto.getPassword())));

    return RestUtil.ok();
  }

  /**
   * Refresh token response entity.
   *
   * @param request the request
   * @return the response entity
   * @author [류성재]
   * @implNote Access 토큰 갱신
   * @since 2021. 3. 2. 오후 12:09:09
   */
  @Transactional
  public ResponseEntity<?> refreshToken(HttpServletRequest request) {
    String jwt = ofNullable(request.getHeader("Authorization")).orElse("");

    // Authorization 값이 없을 때
    if (jwt.equals("")) {
      return error(RestUtil.getExceptions().getUnAuthenticated());
    }

    // Bearer 문자 제거 -> Token 값만 추출
    String refreshToken = jwt.replace("Bearer ", "");

    // Refresh Token 이 메모리에 있는지 체크
    if (!authRefreshService.exists(refreshToken)) {
      return error(RestUtil.getExceptions().getUnAuthenticated());
    }

    AuthRefresh refresh = authRefreshService.getDetail(refreshToken); // Refresh Token 상세 조회
    LocalDateTime updateDt = refresh.getUpdateDt(); // Access Token 갱신 날짜 시간 조회
    AuthAccess access = authAccessService.get(refresh.getAccess().getId()); // Access Token 상세 조회
    String accessToken = access.getId(); // Access Token 조회
    Long id = access.getSignId(); // 계정 일련 번호 조회
    Role role = access.getRole(); // 계정 권한 조회

    // Access Token 이 발급된지 4 시간이 넘었는지 확인. 60 일이 넘었다면 로그아웃 처리
    long limit = 60L * 60L * 4L; // 4 시간

    if (between(updateDt, now()).getSeconds() > limit) {
      authAccessService.delete(accessToken);
      authRefreshService.delete(refreshToken);

      return error(RestUtil.getExceptions().getUnAuthenticated());
    }

    // 새로운 Access Token 발급
    String newAccessToken = SecurityUtil.createAccessToken(id);

    authAccessService.delete(accessToken);
    authAccessService.save(AuthAccess.createRedis(newAccessToken, id, role));

    refresh.updateRedis(authAccessService.get(newAccessToken));
    authRefreshService.save(refresh);

    // 계정 최근 접속 날짜 시간 갱신
    signService.get(id).updateSignDt();

    RefreshTokenResponse response = RefreshTokenResponse
        .builder()
        .accessToken(newAccessToken)
        .build();

    return ok(response);
  }

  /**
   * Gets info.
   *
   * @return the info
   * @author [류성재]
   * @implNote 로그인한 계정 정보 조회
   * @since 2021. 3. 2. 오후 12:09:09
   */
  public ResponseEntity<?> getInfo() {
    ResponseModel.ResponseModelBuilder builder = ResponseModel.builder();

    if (RestUtil.checkUser()) {
      builder.data(map(RestUtil.getSignedAccount(), AccountResponse.class));
    } else {
      builder.data(map(RestUtil.getSignedManager(), ManagerResponse.class));
    }

    return RestUtil.ok(builder.build());
  }

  /**
   * Update info response entity.
   *
   * @param dto the dto
   * @return the response entity
   * @author [류성재]
   * @implNote 로그인한 계정 정보 수정
   * @since 2021. 3. 2. 오후 12:09:09
   */
  @Transactional
  public ResponseEntity<?> updateInfo(SignUpdateRequest dto) {
    if (RestUtil.checkUser()) {
      RestUtil.getSignedAccount().updateEntity(decryptBase64(dto.getName()));
    } else {
      RestUtil.getSignedManager().updateSelf(decryptBase64(dto.getName()));
    }

    return RestUtil.ok();
  }

  /**
   * Remove info response entity.
   *
   * @return the response entity
   * @author [류성재]
   * @implNote 로그인한 계정 탈퇴 처리
   * @since 2021. 3. 2. 오후 12:09:09
   */
  @Transactional
  public ResponseEntity<?> removeInfo() {
    Long id = RestUtil.getSignedId();

    if (RestUtil.checkUser()) {
      RestUtil.getSignedAccount().removeEntity();
    } else {
      RestUtil.getSignedManager().removeEntity();
    }

    authRefreshService.delete(authAccessService.get(id));
    authAccessService.delete(id);

    return RestUtil.ok();
  }

}
