package run.freshr.controller;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.freshr.DataRunner.attachId;
import static run.freshr.DataRunner.hashtagList;
import static run.freshr.common.config.URIConfig.uriCommonAttach;
import static run.freshr.common.config.URIConfig.uriCommonAttachExist;
import static run.freshr.common.config.URIConfig.uriCommonAttachId;
import static run.freshr.common.config.URIConfig.uriCommonAttachIdDownload;
import static run.freshr.common.config.URIConfig.uriCommonCrypto;
import static run.freshr.common.config.URIConfig.uriCommonEnum;
import static run.freshr.common.config.URIConfig.uriCommonEnumPick;
import static run.freshr.common.config.URIConfig.uriCommonHashtag;
import static run.freshr.common.config.URIConfig.uriCommonHashtagAll;
import static run.freshr.common.config.URIConfig.uriCommonHashtagId;
import static run.freshr.common.util.RestUtil.buildId;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import run.freshr.annotation.DocsClass;
import run.freshr.annotation.DocsMethod;
import run.freshr.common.extension.TestExtension;
import run.freshr.domain.common.AttachDocs;
import run.freshr.domain.common.CryptoDocs;
import run.freshr.domain.common.EnumDocs;
import run.freshr.domain.common.HashtagDocs;
import run.freshr.domain.common.enumeration.Gender;

@Slf4j
@DocsClass(name = "common", description = "공통 관리")
public class CommonControllerTest extends TestExtension {

  @Test
  @DisplayName("개발 DB 더미 데이터 생성")
  public void dummy() {
    log.info("CommonControllerTest.dummy");
  }

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|

  @Test
  @DisplayName("열거형 Data 조회 - All")
  @DocsMethod(displayName = "열거형 Data 조회 - All")
  public void getEnumList() throws Exception {
    log.info("CommonControllerTest.getEnumList");

    setAnonymous();

    GET(uriCommonEnum)
        .andDo(print())
        .andDo(docsPopup(EnumDocs.Response.getEnumList(testService.getEnumAll())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("열거형 Data 조회 - One To Many")
  @DocsMethod(displayName = "열거형 Data 조회 - One To Many", pathParameters = true)
  public void getEnum() throws Exception {
    log.info("CommonControllerTest.getEnum");

    setAnonymous();

    GET(uriCommonEnumPick,
        UPPER_CAMEL.to(LOWER_HYPHEN, Gender.class.getSimpleName()).toLowerCase())
        .andDo(print())
        .andDo(docs(pathParameters(EnumDocs.Request.getEnum())))
        .andExpect(status().isOk());
  }

  //  _______ .__   __.   ______ .______     ____    ____ .______   .___________.
  // |   ____||  \ |  |  /      ||   _  \    \   \  /   / |   _  \  |           |
  // |  |__   |   \|  | |  ,----'|  |_)  |    \   \/   /  |  |_)  | `---|  |----`
  // |   __|  |  . `  | |  |     |      /      \_    _/   |   ___/      |  |
  // |  |____ |  |\   | |  `----.|  |\  \----.   |  |     |  |          |  |
  // |_______||__| \__|  \______|| _| `._____|   |__|     | _|          |__|

  @Test
  @DisplayName("RSA 공개키 조회")
  @DocsMethod(displayName = "RSA 공개키 조회")
  public void getPublicKey() throws Exception {
    log.info("CommonControllerTest.getPublicKey");

    setAnonymous();

    GET(uriCommonCrypto)
        .andDo(print())
        .andDo(docs(responseFields(CryptoDocs.Response.getPublicKey())))
        .andExpect(status().isOk());
  }

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|

  @Test
  @DisplayName("파일 업로드")
  @DocsMethod(displayName = "파일 업로드",
      requestParts = true, requestParameters = true, responseFields = true)
  public void createAttach() throws Exception {
    log.info("CommonControllerTest.createAttach");

    setSignedUser();

    apply();

    POST_MULTIPART(
        uriCommonAttach,
        "temp",
        new MockMultipartFile("files", "test.png", "image/png",
            "EMOTION".getBytes())
    ).andDo(print())
        .andDo(docs(
            requestParts(AttachDocs.Request.createAttachFile()),
            requestParameters(AttachDocs.Request.createAttach()),
            responseFields(AttachDocs.Response.createAttach())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 존재 여부 확인")
  @DocsMethod(displayName = "파일 존재 여부 확인", pathParameters = true, responseFields = true)
  public void existAttach() throws Exception {
    log.info("CommonControllerTest.existAttach");

    setAnonymous();

    apply();

    GET(uriCommonAttachExist, attachId)
        .andDo(print())
        .andDo(docs(
            pathParameters(AttachDocs.Request.existAttach()),
            responseFields(AttachDocs.Response.existAttach())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 상세 조회")
  @DocsMethod(displayName = "파일 상세 조회", pathParameters = true, responseFields = true)
  public void getAttach() throws Exception {
    log.info("CommonControllerTest.getAttach");

    setAnonymous();

    apply();

    GET(uriCommonAttachId, attachId)
        .andDo(print())
        .andDo(docs(
            pathParameters(AttachDocs.Request.getAttach()),
            responseFields(AttachDocs.Response.getAttach())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 삭제")
  @DocsMethod(displayName = "파일 삭제", pathParameters = true)
  public void removeAttach() throws Exception {
    log.info("CommonControllerTest.removeAttach");

    setSignedUser();

    apply();

    DELETE(uriCommonAttachId, attachId)
        .andDo(print())
        .andDo(docs(pathParameters(AttachDocs.Request.removeAttach())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 다운로드")
  @DocsMethod(displayName = "파일 다운로드", pathParameters = true)
  public void getAttachDownload() throws Exception {
    log.info("CommonControllerTest.getAttachDownload");

    setAnonymous();

    apply();

    GET(uriCommonAttachIdDownload, attachId)
        .andDo(print())
        .andDo(docs(pathParameters(AttachDocs.Request.getAttach())))
        .andExpect(status().isOk());
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|

  @Test
  @DisplayName("해시태그 조회 - 전체")
  @DocsMethod(displayName = "해시태그 조회 - 전체", responseFields = true)
  public void getHashtagAll() throws Exception {
    log.info("CommonControllerTest.getHashtagAll");

    setAnonymous();

    apply();

    GET(uriCommonHashtagAll)
        .andDo(print())
        .andDo(docs(responseFields(HashtagDocs.Response.getHashtagAll())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("해시태그 조회 - List")
  @DocsMethod(displayName = "해시태그 조회 - List", responseFields = true)
  public void getHashtagList() throws Exception {
    log.info("CommonControllerTest.getHashtagList");

    setAnonymous();

    apply();

    GET(uriCommonHashtag)
        .andDo(print())
        .andDo(docs(responseFields(HashtagDocs.Response.getHashtagList())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("해시태그 등록")
  @DocsMethod(displayName = "해시태그 등록", requestFields = true)
  public void createHashtag() throws Exception {
    log.info("CommonControllerTest.createHashtag");

    setSignedManager();

    apply();

    POST_BODY(uriCommonHashtag, buildId("test"))
        .andDo(print())
        .andDo(docs(requestFields(HashtagDocs.Request.createHashtag())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("해시태그 삭제")
  @DocsMethod(displayName = "해시태그 삭제", pathParameters = true)
  public void deleteHashtag() throws Exception {
    log.info("CommonControllerTest.deleteHashtag");

    setSignedManager();

    apply();

    DELETE(uriCommonHashtagId, hashtagList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(HashtagDocs.Request.deleteHashtag())))
        .andExpect(status().isOk());
  }

}
