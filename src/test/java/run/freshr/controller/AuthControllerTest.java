package run.freshr.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.freshr.DataRunner.accountIdList;
import static run.freshr.DataRunner.managerIdList;
import static run.freshr.util.CryptoUtil.encryptBase64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import run.freshr.annotation.DocsClass;
import run.freshr.annotation.DocsMethod;
import run.freshr.common.config.URIConfig;
import run.freshr.common.extension.TestExtension;
import run.freshr.common.security.SecurityUtil;
import run.freshr.common.util.SignUtil;
import run.freshr.domain.auth.SignDocs;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignPasswordRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;
import run.freshr.domain.auth.enumeration.Role;

@DocsClass(name = "auth", description = "권한 관리")
public class AuthControllerTest extends TestExtension {

  @Test
  @DisplayName("Access 토큰 갱신")
  @DocsMethod(displayName = "Access 토큰 갱신", responseFields = true)
  public void refreshToken() throws Exception {
    Long id = accountIdList.get(0);

    String accessToken = SecurityUtil.createJwt(id.toString(), 0, null);
    String refreshToken = SecurityUtil.createRefreshToken(id);

    testService.createAccess(accessToken, id, Role.ROLE_SUPER);
    testService.createRefresh(refreshToken, accessToken);

    apply();

    POST_TOKEN(URIConfig.uriAuthToken, refreshToken)
        .andDo(print())
        .andDo(docs(responseFields(SignDocs.Response.refreshToken())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인")
  @DocsMethod(displayName = "로그인", requestFields = true, responseFields = true)
  public void signIn() throws Exception {
    setAnonymous();

    apply();

    POST_BODY(
        URIConfig.uriAuthSignIn,
        SignInRequest
            .builder()
            .username(encryptBase64(testService.getManager(managerIdList.get(0)).getUsername()))
            .password(encryptBase64("1234"))
            .build()
    ).andDo(print())
        .andDo(docs(
            requestFields(SignDocs.Request.signIn()),
            responseFields(SignDocs.Response.signIn())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그아웃")
  @DocsMethod(displayName = "로그아웃")
  public void signOut() throws Exception {
    setSignedUser();

    apply();

    POST_TOKEN(URIConfig.uriAuthSignOut, SignUtil.signedAccess.get())
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인한 계정 비밀번호 변경")
  @DocsMethod(displayName = "로그인한 계정 비밀번호 변경", requestFields = true)
  public void updatePassword() throws Exception {
    setSignedUser();

    apply();

    PUT_BODY(
        URIConfig.uriAuthPassword,
        SignPasswordRequest
            .builder()
            .originPassword(encryptBase64("1234"))
            .password(encryptBase64("input password"))
            .build()
    ).andDo(print())
        .andDo(docs(requestFields(SignDocs.Request.updatePassword())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인한 계정 정보 조회 - 관리자")
  @DocsMethod(displayName = "로그인한 계정 정보 조회 - 관리자", responseFields = true)
  public void getInfoByManager() throws Exception {
    setSignedManager();

    apply();

    GET(URIConfig.uriAuthInfo)
        .andDo(print())
        .andDo(docs(responseFields(SignDocs.Response.getInfoByManager())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인한 계정 정보 조회 - 사용자")
  @DocsMethod(displayName = "로그인한 계정 정보 조회 - 사용자", responseFields = true)
  public void getInfoByAccount() throws Exception {
    setSignedUser();

    apply();

    GET(URIConfig.uriAuthInfo)
        .andDo(print())
        .andDo(docs(responseFields(SignDocs.Response.getInfoByAccount())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인한 계정 정보 수정 - 관리자")
  @DocsMethod(displayName = "로그인한 계정 정보 수정 - 관리자", requestFields = true)
  public void updateInfoByManager() throws Exception {
    setSignedManager();

    apply();

    PUT_BODY(
        URIConfig.uriAuthInfo,
        SignUpdateRequest
            .builder()
            .name(encryptBase64("input name"))
            .build()
    ).andDo(print())
        .andDo(docs(requestFields(SignDocs.Request.updateInfoByManager())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인한 계정 정보 수정 - 사용자")
  @DocsMethod(displayName = "로그인한 계정 정보 수정 - 사용자", requestFields = true)
  public void updateInfoByAccount() throws Exception {
    setSignedManager();

    apply();

    PUT_BODY(
        URIConfig.uriAuthInfo,
        SignUpdateRequest
            .builder()
            .name(encryptBase64("input name"))
            .build()
    ).andDo(print())
        .andDo(docs(requestFields(SignDocs.Request.updateInfoByAccount())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인한 계정 탈퇴 처리")
  @DocsMethod(displayName = "로그인한 계정 탈퇴 처리")
  public void removeInfo() throws Exception {
    setSignedUser();

    apply();

    DELETE(URIConfig.uriAuthInfo)
        .andDo(print())
        .andExpect(status().isOk());
  }

}