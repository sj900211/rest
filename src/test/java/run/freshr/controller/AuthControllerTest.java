package run.freshr.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.freshr.DataRunner.managerId;
import static run.freshr.common.config.URIConfig.uriAuthInfo;
import static run.freshr.common.config.URIConfig.uriAuthPassword;
import static run.freshr.common.config.URIConfig.uriAuthSignIn;
import static run.freshr.common.config.URIConfig.uriAuthSignOut;
import static run.freshr.common.config.URIConfig.uriAuthToken;
import static run.freshr.common.util.SignUtil.signedAccess;
import static run.freshr.domain.auth.enumeration.Role.ROLE_SUPER;
import static run.freshr.util.CryptoUtil.encodeBase64;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import run.freshr.annotation.DocsClass;
import run.freshr.annotation.DocsMethod;
import run.freshr.common.extension.TestExtension;
import run.freshr.common.security.SecurityUtil;
import run.freshr.domain.auth.AccountDocs;
import run.freshr.domain.auth.dto.request.SignChangePasswordRequest;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;

@Slf4j
@DocsClass(name = "auth", description = "권한 관리")
public class AuthControllerTest extends TestExtension {

  @Test
  @DisplayName("Access 토큰 갱신")
  @DocsMethod(displayName = "Access 토큰 갱신", responseFields = true)
  public void refreshToken() throws Exception {
    log.info("AuthControllerTest.refreshToken");

    Long id = managerId;

    String accessToken = SecurityUtil.createJwt(id.toString(), 0, null);
    String refreshToken = SecurityUtil.createRefreshToken(id);

    testService.createAccess(accessToken, id, ROLE_SUPER);
    testService.createRefresh(refreshToken, accessToken);

    apply();

    POST_TOKEN(uriAuthToken, refreshToken)
        .andDo(print())
        .andDo(docs(responseFields(AccountDocs.Response.refreshToken())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인")
  @DocsMethod(displayName = "로그인", requestFields = true, responseFields = true)
  public void signIn() throws Exception {
    log.info("AuthControllerTest.signIn");

    setAnonymous();

    apply();

    POST_BODY(
        uriAuthSignIn,
        SignInRequest
            .builder()
            .username(encodeBase64(testService.getAccount(managerId).getUsername()))
            .password(encodeBase64("1234"))
            .build()
    ).andDo(print())
        .andDo(docs(
            requestFields(AccountDocs.Request.signIn()),
            responseFields(AccountDocs.Response.signIn())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그아웃")
  @DocsMethod(displayName = "로그아웃")
  public void signOut() throws Exception {
    log.info("AuthControllerTest.signOut");

    setSignedUser();

    apply();

    POST_TOKEN(uriAuthSignOut, signedAccess.get())
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인한 계정 비밀번호 변경")
  @DocsMethod(displayName = "로그인한 계정 비밀번호 변경", requestFields = true)
  public void updatePassword() throws Exception {
    log.info("AuthControllerTest.updatePassword");

    setSignedUser();

    apply();

    PUT_BODY(
        uriAuthPassword,
        SignChangePasswordRequest
            .builder()
            .originPassword(encodeBase64("1234"))
            .password(encodeBase64("input password"))
            .build()
    ).andDo(print())
        .andDo(docs(requestFields(AccountDocs.Request.updatePassword())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인한 계정 정보 조회")
  @DocsMethod(displayName = "로그인한 계정 정보 조회", responseFields = true)
  public void getInfo() throws Exception {
    log.info("AuthControllerTest.getInfo");

    setSignedUser();

    apply();

    GET(uriAuthInfo)
        .andDo(print())
        .andDo(docs(responseFields(AccountDocs.Response.getInfo())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인한 계정 정보 수정")
  @DocsMethod(displayName = "로그인한 계정 정보 수정", requestFields = true)
  public void updateInfo() throws Exception {
    log.info("AuthControllerTest.updateInfo");

    setSignedManager();

    apply();

    PUT_BODY(
        uriAuthInfo,
        SignUpdateRequest
            .builder()
            .name(encodeBase64("input name"))
            .build()
    ).andDo(print())
        .andDo(docs(requestFields(AccountDocs.Request.updateInfo())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인한 계정 탈퇴 처리")
  @DocsMethod(displayName = "로그인한 계정 탈퇴 처리")
  public void removeInfo() throws Exception {
    log.info("AuthControllerTest.removeInfo");

    setSignedUser();

    apply();

    DELETE(uriAuthInfo)
        .andDo(print())
        .andExpect(status().isOk());
  }

}