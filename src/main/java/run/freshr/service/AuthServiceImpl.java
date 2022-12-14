package run.freshr.service;

import static java.time.Duration.between;
import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static run.freshr.common.util.RestUtil.error;
import static run.freshr.common.util.RestUtil.getExceptions;
import static run.freshr.common.util.RestUtil.getSignedAccount;
import static run.freshr.common.util.RestUtil.getSignedId;
import static run.freshr.common.util.RestUtil.ok;
import static run.freshr.util.CryptoUtil.decryptRsa;
import static run.freshr.util.MapperUtil.map;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.model.ResponseModel;
import run.freshr.common.security.SecurityUtil;
import run.freshr.domain.auth.dto.request.SignChangePasswordRequest;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;
import run.freshr.domain.auth.dto.response.AccountResponse;
import run.freshr.domain.auth.dto.response.RefreshTokenResponse;
import run.freshr.domain.auth.dto.response.SignInResponse;
import run.freshr.domain.auth.entity.Account;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;
import run.freshr.domain.auth.unit.AccountUnitImpl;
import run.freshr.domain.auth.unit.AccessRedisUnitImpl;
import run.freshr.domain.auth.unit.RefreshRedisUnitImpl;
import run.freshr.domain.common.redis.RsaPair;
import run.freshr.domain.common.unit.AttachUnitImpl;
import run.freshr.domain.common.unit.RsaPairUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

  private final AccountUnitImpl accountUnit;
  private final AttachUnitImpl attachUnit;

  private final AccessRedisUnitImpl authAccessUnit;
  private final RefreshRedisUnitImpl authRefreshUnit;
  private final RsaPairUnit rsaPairUnit;

  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public ResponseEntity<?> signIn(SignInRequest dto) {
    log.info("AuthService.signIn");

    String encodePublicKey = dto.getRsa();

    if (!rsaPairUnit.checkRsa(encodePublicKey)) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getEncodePrivateKey();
    String username = decryptRsa(dto.getUsername(), encodePrivateKey);

    if (!accountUnit.exists(username)) {
      return error(getExceptions().getEntityNotFound());
    }

    Account entity = accountUnit.get(username);

    if (entity.getDelFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    if (!entity.getUseFlag()) {
      return error(getExceptions().getUnAuthenticated());
    }

    if (!passwordEncoder
        .matches(decryptRsa(dto.getPassword(), encodePrivateKey), entity.getPassword())) {
      return error(getExceptions().getUnAuthenticated());
    }

    entity.updateSignDt();

    Long id = entity.getId();

    // ?????? ??????
    String accessToken = SecurityUtil.createAccessToken(id);
    String refreshToken = SecurityUtil.createRefreshToken(id);

    // ?????? ??????
    authAccessUnit.save(AccessRedis.createRedis(accessToken, id, entity.getPrivilege().getRole()));
    authRefreshUnit.save(RefreshRedis.createRedis(refreshToken, authAccessUnit.get(accessToken)));

    SignInResponse response = SignInResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();

    return ok(response);
  }

  @Override
  @Transactional
  public ResponseEntity<?> signOut(HttpServletRequest request) {
    log.info("AuthService.signOut");

    String jwt = ofNullable(request.getHeader("Authorization")).orElse("");
    String token = jwt.replace("Bearer ", "");

    if (authAccessUnit.exists(token)) {
      authRefreshUnit.delete(authAccessUnit.get(token));
    }

    authAccessUnit.delete(token);

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> updatePassword(SignChangePasswordRequest dto) {
    log.info("AuthService.updatePassword");

    String encodePublicKey = dto.getRsa();

    if (!rsaPairUnit.checkRsa(encodePublicKey)) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getEncodePrivateKey();

    Account entity = accountUnit.get(getSignedId());

    if (!passwordEncoder
        .matches(decryptRsa(dto.getOriginPassword(), encodePrivateKey), entity.getPassword())) {
      return error(getExceptions().getUnAuthenticated());
    }

    entity.updatePassword(passwordEncoder.encode(decryptRsa(dto.getPassword(), encodePrivateKey)));

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> refreshToken(HttpServletRequest request) {
    log.info("AuthService.refreshToken");

    String jwt = ofNullable(request.getHeader("Authorization")).orElse("");

    // Authorization ?????? ?????? ???
    if (jwt.equals("")) {
      return error(getExceptions().getUnAuthenticated());
    }

    // Bearer ?????? ?????? -> Token ?????? ??????
    String refreshToken = jwt.replace("Bearer ", "");

    // Refresh Token ??? ???????????? ????????? ??????
    if (!authRefreshUnit.exists(refreshToken)) {
      return error(getExceptions().getUnAuthenticated());
    }

    RefreshRedis refresh = authRefreshUnit.get(refreshToken); // Refresh Token ?????? ??????
    LocalDateTime updateDt = refresh.getUpdateDt(); // Access Token ?????? ?????? ?????? ??????
    AccessRedis access = authAccessUnit.get(refresh.getAccess().getId()); // Access Token ?????? ??????
    String accessToken = access.getId(); // Access Token ??????
    Long id = access.getSignId(); // ?????? ?????? ?????? ??????
    Role role = access.getRole(); // ?????? ?????? ??????

    // Access Token ??? ???????????? 4 ????????? ???????????? ??????. 60 ?????? ???????????? ???????????? ??????
    long limit = 60L * 60L * 4L; // 4 ??????

    if (between(updateDt, now()).getSeconds() > limit) {
      authAccessUnit.delete(accessToken);
      authRefreshUnit.delete(refreshToken);

      return error(getExceptions().getUnAuthenticated());
    }

    // ????????? Access Token ??????
    String newAccessToken = SecurityUtil.createAccessToken(id);

    authAccessUnit.delete(accessToken);
    authAccessUnit.save(AccessRedis.createRedis(newAccessToken, id, role));

    refresh.updateRedis(authAccessUnit.get(newAccessToken));
    authRefreshUnit.save(refresh);

    // ?????? ?????? ?????? ?????? ?????? ??????
    accountUnit.get(id).updateSignDt();

    RefreshTokenResponse response = RefreshTokenResponse
        .builder()
        .accessToken(newAccessToken)
        .build();

    return ok(response);
  }

  @Override
  public ResponseEntity<?> getInfo() {
    log.info("AuthService.getInfo");

    return ok(ResponseModel
        .builder()
        .data(map(getSignedAccount(), AccountResponse.class))
        .build());
  }

  @Override
  @Transactional
  public ResponseEntity<?> updateInfo(SignUpdateRequest dto) {
    log.info("AuthService.updateInfo");

    String encodePublicKey = dto.getRsa();

    if (!rsaPairUnit.checkRsa(encodePublicKey)) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getEncodePrivateKey();

    getSignedAccount()
        .updateEntity(
            decryptRsa(dto.getName(), encodePrivateKey),
            dto.getIntroduce(),
            !isNull(dto.getProfile()) && !isNull(dto.getProfile().getId())
                ? attachUnit.get(dto.getProfile().getId())
                : null
        );

    return ok();
  }

  @Override
  @Transactional
  public ResponseEntity<?> removeInfo() {
    log.info("AuthService.removeInfo");

    Long id = getSignedId();

    getSignedAccount().removeEntity();

    authRefreshUnit.delete(authAccessUnit.get(id));
    authAccessUnit.delete(id);

    return ok();
  }

}
