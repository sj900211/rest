package run.freshr.controller;

import static run.freshr.DataRunner.accountIdList;
import static run.freshr.util.CryptoUtil.encryptBase64;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import run.freshr.common.config.URIConfig;
import run.freshr.domain.user.vo.UserSearch;
import run.freshr.common.extension.TestExtension;
import run.freshr.domain.auth.dto.request.AccountCreateRequest;
import run.freshr.domain.user.AccountDocs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserControllerTest extends TestExtension {

  @Test
  @DisplayName("사용자 계정 등록")
  public void createAccount() throws Exception {
    setAnonymous();

    apply();

    POST_BODY(
        URIConfig.uriUser,
        AccountCreateRequest
            .builder()
            .username(encryptBase64("input username"))
            .password(encryptBase64("input password"))
            .name(encryptBase64("input name"))
            .build()
    ).andDo(print())
        .andDo(docs(
            requestFields(AccountDocs.Request.createAccount()),
            responseFields(AccountDocs.Response.createAccount())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 계정 정보 조회 - Page")
  public void getAccountPage() throws Exception {
    setAnonymous();

    apply();

    UserSearch search = new UserSearch();

    search.setPage(2);
    search.setCpp(5);

    GET_PARAM(URIConfig.uriUser, search)
        .andDo(print())
        .andDo(docs(
            requestParameters(AccountDocs.Request.getAccountPage()),
            responseFields(AccountDocs.Response.getAccountPage())
        ))
        .andDo(docsPopup(AccountDocs.Docs.getAccountPage()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 계정 정보 조회")
  public void getAccount() throws Exception {
    setAnonymous();

    apply();

    GET(URIConfig.uriUserId, accountIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(AccountDocs.Request.getAccount()),
            responseFields(AccountDocs.Response.getAccount())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 계정 탈퇴 처리")
  public void removeAccount() throws Exception {
    setSignedManager();

    apply();

    DELETE(URIConfig.uriUserId, accountIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(AccountDocs.Request.removeAccount())))
        .andExpect(status().isOk());
  }

}
