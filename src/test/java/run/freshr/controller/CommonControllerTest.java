package run.freshr.controller;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static run.freshr.DataRunner.attachIdList;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import run.freshr.annotation.DocsClass;
import run.freshr.annotation.DocsMethod;
import run.freshr.common.config.URIConfig;
import run.freshr.common.extension.TestExtension;
import run.freshr.domain.common.AttachDocs;
import run.freshr.domain.common.EnumDocs;
import run.freshr.domain.common.enumeration.Gender;
import org.springframework.restdocs.request.RequestDocumentation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

@DocsClass(name = "common", description = "공통 관리")
public class CommonControllerTest extends TestExtension {

//    @Test
//    @DisplayName("개발 DB 더미 데이터 생성")
//    public void dummy() {
//        System.out.println("Insert dummy");
//    }

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
    GET(URIConfig.uriCommonEnum)
        .andDo(print())
        .andDo(docsPopup(EnumDocs.Response.getEnumList(testService.getEnumAll())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("열거형 Data 조회 - One To Many")
  @DocsMethod(displayName = "열거형 Data 조회 - One To Many", pathParameters = true)
  public void getEnum() throws Exception {
    GET(URIConfig.uriCommonEnumPick, UPPER_CAMEL.to(LOWER_HYPHEN, Gender.class.getSimpleName()).toLowerCase())
        .andDo(print())
        .andDo(docs(pathParameters(EnumDocs.Request.getEnum())))
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
  @DocsMethod(displayName = "파일 업로드", requestParts = true, requestParameters = true, responseFields = true)
  public void createAttach() throws Exception {
    setAnonymous();

    apply();

    POST_MULTIPART(
        URIConfig.uriCommonAttach,
        "temp",
        new MockMultipartFile("files", "test.png", "image/png", "EMOTION".getBytes())
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
    setAnonymous();

    apply();

    GET(URIConfig.uriCommonAttachExist, attachIdList.get(0))
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
    setAnonymous();

    apply();

    GET(URIConfig.uriCommonAttachId, attachIdList.get(0))
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
    setAnonymous();

    apply();

    DELETE(URIConfig.uriCommonAttachId, attachIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(AttachDocs.Request.removeAttach())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 다운로드")
  @DocsMethod(displayName = "파일 다운로드", pathParameters = true)
  public void getAttachDownload() throws Exception {
    setAnonymous();

    apply();

    GET(URIConfig.uriCommonAttachIdDownload, attachIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(AttachDocs.Request.getAttach())))
        .andExpect(status().isOk());
  }

  //  _______  _______   __  .___________.  ______   .______
  // |   ____||       \ |  | |           | /  __  \  |   _  \
  // |  |__   |  .--.  ||  | `---|  |----`|  |  |  | |  |_)  |
  // |   __|  |  |  |  ||  |     |  |     |  |  |  | |      /
  // |  |____ |  '--'  ||  |     |  |     |  `--'  | |  |\  \----.
  // |_______||_______/ |__|     |__|      \______/  | _| `._____|

  @Test
  @DisplayName("CK 에디터 파일 업로드")
  @DocsMethod(displayName = "CK 에디터 파일 업로드", requestParts = true)
  public void createAttachForEditorCk() throws Exception {
    POST_MULTIPART(
        URIConfig.uriCommonEditorCK,
        null,
        new MockMultipartFile("upload", "test.png", "image/png", "NEXT CULTURE".getBytes())
    ).andDo(print())
        .andDo(docs(requestParts(AttachDocs.Request.createAttachForEditorCK())))
        .andExpect(status().isOk());
  }

}
