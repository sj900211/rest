package run.freshr.controller;

import static run.freshr.DataRunner.boardIdList;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import run.freshr.annotation.DocsClass;
import run.freshr.annotation.DocsMethod;
import run.freshr.common.config.URIConfig;
import run.freshr.common.extension.TestExtension;
import run.freshr.domain.community.BoardDocs;
import org.springframework.restdocs.payload.PayloadDocumentation;
import run.freshr.domain.common.dto.request.AttachSortRequest;
import run.freshr.domain.common.dto.request.IdRequest;
import run.freshr.domain.community.dto.request.BoardCreateRequest;
import run.freshr.domain.community.dto.request.BoardUpdateRequest;
import run.freshr.domain.community.vo.CommunitySearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DocsClass(name = "community", description = "커뮤니티 관리")
public class CommunityControllerTest extends TestExtension {

  @Test
  @DisplayName("게시글 등록")
  @DocsMethod(displayName = "게시글 등록", requestFields = true, responseFields = true)
  public void createBoard() throws Exception {
    setSignedUser();

    apply();

    POST_BODY(
        URIConfig.uriCommunityBoard,
        BoardCreateRequest
            .builder()
            .title("input title")
            .content("input content")
            .attachList(Arrays.asList(
                AttachSortRequest
                    .builder()
                    .attach(IdRequest
                        .builder()
                        .id(testService.createAttach("test001.png", "temp"))
                        .build())
                    .sort(0)
                    .build(),
                AttachSortRequest
                    .builder()
                    .attach(IdRequest
                        .builder()
                        .id(testService.createAttach("test002.png", "temp"))
                        .build())
                    .sort(1)
                    .build(),
                AttachSortRequest
                    .builder()
                    .attach(IdRequest
                        .builder()
                        .id(testService.createAttach("test003.png", "temp"))
                        .build())
                    .sort(2)
                    .build()
            ))
            .build()
    ).andDo(print())
        .andDo(docs(
            requestFields(BoardDocs.Request.createBoard()),
            responseFields(BoardDocs.Response.createBoard())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("게시글 정보 조회 - Page")
  @DocsMethod(displayName = "게시글 정보 조회 - Page", requestParameters = true, responseFields = true)
  public void getBoardPage() throws Exception {
    setAnonymous();

    apply();

    CommunitySearch search = new CommunitySearch();

    search.setPage(2);
    search.setCpp(5);

    GET_PARAM(URIConfig.uriCommunityBoard, search)
        .andDo(print())
        .andDo(docs(
            requestParameters(BoardDocs.Request.getBoardPage()),
            responseFields(BoardDocs.Response.getBoardPage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("게시글 정보 조회")
  @DocsMethod(displayName = "게시글 정보 조회", pathParameters = true, responseFields = true)
  public void getBoard() throws Exception {
    setAnonymous();

    apply();

    GET(URIConfig.uriCommunityBoardId, boardIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(BoardDocs.Request.getBoard()),
            responseFields(BoardDocs.Response.getBoard())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("게시글 정보 수정")
  @DocsMethod(displayName = "게시글 정보 수정", pathParameters = true, requestFields = true)
  public void updateBoard() throws Exception {
    setSignedUser();

    apply();

    PUT_BODY(
        URIConfig.uriCommunityBoardId,
        BoardUpdateRequest
            .builder()
            .title("input title")
            .content("input content")
            .attachList(Arrays.asList(
                AttachSortRequest
                    .builder()
                    .attach(IdRequest
                        .builder()
                        .id(testService.createAttach("test001.png", "temp"))
                        .build())
                    .sort(0)
                    .build(),
                AttachSortRequest
                    .builder()
                    .attach(IdRequest
                        .builder()
                        .id(testService.createAttach("test002.png", "temp"))
                        .build())
                    .sort(1)
                    .build(),
                AttachSortRequest
                    .builder()
                    .attach(IdRequest
                        .builder()
                        .id(testService.createAttach("test003.png", "temp"))
                        .build())
                    .sort(2)
                    .build()
            ))
            .build(),
        boardIdList.get(0)
    ).andDo(print())
        .andDo(docs(
            pathParameters(BoardDocs.Request.updateBoardPath()),
            requestFields(BoardDocs.Request.updateBoard())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("게시글 삭제 처리")
  @DocsMethod(displayName = "게시글 삭제 처리", pathParameters = true)
  public void removeBoard() throws Exception {
    setSignedUser();

    apply();

    DELETE(URIConfig.uriCommunityBoardId, boardIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(BoardDocs.Request.removeBoard())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("게시글 첨부파일 정보 조회 - List")
  @DocsMethod(displayName = "게시글 첨부파일 정보 조회 - List", pathParameters = true, responseFields = true)
  public void getBoardAttachMappingList() throws Exception {
    setAnonymous();

    apply();

    GET(URIConfig.uriCommunityBoardIdAttach, boardIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(BoardDocs.Request.getBoardAttachMappingList()),
            responseFields(BoardDocs.Response.getBoardAttachMappingList())
        ))
        .andExpect(status().isOk());
  }

}
