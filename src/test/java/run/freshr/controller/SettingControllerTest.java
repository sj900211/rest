package run.freshr.controller;

import static run.freshr.DataRunner.managerIdList;
import static run.freshr.util.CryptoUtil.encodeBase64;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import run.freshr.annotation.DocsClass;
import run.freshr.annotation.DocsMethod;
import run.freshr.common.config.URIConfig;
import run.freshr.common.extension.TestExtension;
import run.freshr.domain.auth.enumeration.ManagerPrivilege;
import run.freshr.domain.setting.ManagerDocs;
import org.springframework.restdocs.payload.PayloadDocumentation;
import run.freshr.domain.auth.dto.request.ManagerCreateRequest;
import run.freshr.domain.auth.dto.request.ManagerUpdateRequest;
import run.freshr.domain.setting.vo.SettingSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DocsClass(name = "setting", description = "설정 관리")
public class SettingControllerTest extends TestExtension {

  @Test
  @DisplayName("관리자 계정 등록")
  @DocsMethod(displayName = "관리자 계정 등록", requestFields = true, responseFields = true)
  public void createManager() throws Exception {
    setSignedSuper();

    apply();

    POST_BODY(
        URIConfig.uriSettingManager,
        ManagerCreateRequest
            .builder()
            .privilege(ManagerPrivilege.MANAGER)
            .username(encodeBase64("input username"))
            .password(encodeBase64("input password"))
            .name(encodeBase64("input name"))
            .build()
    ).andDo(print())
        .andDo(docs(
            requestFields(ManagerDocs.Request.createManager()),
            responseFields(ManagerDocs.Response.createManager())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("관리자 계정 정보 조회 - Page")
  @DocsMethod(displayName = "관리자 계정 정보 조회 - Page", requestParameters = true, responseFields = true)
  public void getManagerPage() throws Exception {
    setSignedSuper();

    apply();

    SettingSearch search = new SettingSearch();

    search.setPage(2);
    search.setCpp(5);

    GET_PARAM(URIConfig.uriSettingManager, search)
        .andDo(print())
        .andDo(docs(
            requestParameters(ManagerDocs.Request.getManagerPage()),
            responseFields(ManagerDocs.Response.getManagerPage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("관리자 계정 정보 조회")
  @DocsMethod(displayName = "관리자 계정 정보 조회", pathParameters = true, responseFields = true)
  public void getManager() throws Exception {
    setSignedSuper();

    apply();

    GET(URIConfig.uriSettingManagerId, managerIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(ManagerDocs.Request.getManager()),
            responseFields(ManagerDocs.Response.getManager())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("관리자 계정 정보 수정")
  @DocsMethod(displayName = "관리자 계정 정보 수정", pathParameters = true, requestFields = true)
  public void updateManager() throws Exception {
    setSignedSuper();

    apply();

    PUT_BODY(
        URIConfig.uriSettingManagerId,
        ManagerUpdateRequest
            .builder()
            .privilege(ManagerPrivilege.MANAGER)
            .name(encodeBase64("input name"))
            .build(),
        managerIdList.get(0)
    ).andDo(print())
        .andDo(docs(
            pathParameters(ManagerDocs.Request.updateManagerPath()),
            requestFields(ManagerDocs.Request.updateManager())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("탈퇴 처리")
  @DocsMethod(displayName = "탈퇴 처리", pathParameters = true)
  public void removeManager() throws Exception {
    setSignedSuper();

    apply();

    DELETE(URIConfig.uriSettingManagerId, managerIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(ManagerDocs.Request.removeManager())))
        .andExpect(status().isOk());
  }

}
