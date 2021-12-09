package run.freshr.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.freshr.DataRunner.hashtagList;
import static run.freshr.DataRunner.postList;
import static run.freshr.common.config.URIConfig.uriBlogPost;
import static run.freshr.common.config.URIConfig.uriBlogPostId;
import static run.freshr.common.util.ThreadUtil.threadPublicKey;
import static run.freshr.util.CryptoUtil.encryptRsa;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import run.freshr.annotation.DocsClass;
import run.freshr.annotation.DocsMethod;
import run.freshr.common.extension.TestExtension;
import run.freshr.domain.blog.PostDocs;
import run.freshr.domain.blog.dto.request.PostCreateRequest;
import run.freshr.domain.blog.dto.request.PostUpdateRequest;
import run.freshr.domain.blog.vo.BlogSearch;
import run.freshr.domain.common.dto.request.IdRequest;
import run.freshr.domain.mapping.dto.request.PostHashtagMappingForPostCreateRequest;

@Slf4j
@DocsClass(name = "blog", description = "블로그 관리")
public class BlogControllerTest extends TestExtension {

  @Test
  @DisplayName("포스팅 조회 - Page")
  @DocsMethod(displayName = "포스팅 조회 - Page", requestParameters = true, responseFields = true)
  public void getPostPage() throws Exception {
    log.info("BlogControllerTest.getPostPage");

    setAnonymous();

    apply();

    BlogSearch search = new BlogSearch();

    search.setPage(1);
    search.setSize(10);

    GET_PARAM(uriBlogPost, search)
        .andDo(print())
        .andDo(docs(
            requestParameters(PostDocs.Request.getPostPage()),
            responseFields(PostDocs.Response.getPostPage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 조회")
  @DocsMethod(displayName = "포스팅 조회", pathParameters = true, responseFields = true)
  public void getPost() throws Exception {
    log.info("BlogControllerTest.getPost");

    setAnonymous();

    apply();

    GET(uriBlogPostId, postList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(PostDocs.Request.getPost()),
            responseFields(PostDocs.Response.getPost())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 등록")
  @DocsMethod(displayName = "포스팅 등록", requestFields = true, responseFields = true)
  public void createPost() throws Exception {
    log.info("BlogControllerTest.createPost");

    setSignedCoach();
    setRsa();

    apply();

    POST_BODY(
        uriBlogPost,
        PostCreateRequest
            .builder()
            .rsa(threadPublicKey.get())
            .title(encryptRsa("TITLE", threadPublicKey.get()))
            .contents(encryptRsa("# CONTENTS" + "\r\n"
                + "``` java" + "\r\n"
                + "private void handler() {" + "\r\n"
                + "    System.out.println(\"HELLO WORLD\");" + "\r\n"
                + "}" + "\r\n"
                + "```", threadPublicKey.get()))
            .managerGrant(true)
            .leaderGrant(true)
            .coachGrant(true)
            .userGrant(true)
            .anonymousGrant(false)
            .hashtagList(List.of(
                PostHashtagMappingForPostCreateRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder().id(hashtagList.get(0)).build())
                    .build(),
                PostHashtagMappingForPostCreateRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder().id(hashtagList.get(1)).build())
                    .build()
            ))
            .build()
    ).andDo(print())
        .andDo(docs(
            requestFields(PostDocs.Request.createPost()),
            responseFields(PostDocs.Response.createPost())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 수정")
  @DocsMethod(displayName = "포스팅 수정", pathParameters = true, requestFields = true)
  public void updatePost() throws Exception {
    log.info("BlogControllerTest.updatePost");

    setSignedSuper();
    setRsa();

    apply();

    PUT_BODY(
        uriBlogPostId,
        PostUpdateRequest
            .builder()
            .rsa(threadPublicKey.get())
            .title(encryptRsa("TITLE", threadPublicKey.get()))
            .contents(encryptRsa("# CONTENTS" + "\r\n"
                + "``` java" + "\r\n"
                + "private void handler() {" + "\r\n"
                + "    System.out.println(\"HELLO WORLD\");" + "\r\n"
                + "}" + "\r\n"
                + "```", threadPublicKey.get()))
            .managerGrant(true)
            .leaderGrant(true)
            .coachGrant(true)
            .userGrant(true)
            .anonymousGrant(false)
            .hashtagList(List.of(
                PostHashtagMappingForPostCreateRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder().id(hashtagList.get(0)).build())
                    .build(),
                PostHashtagMappingForPostCreateRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder().id(hashtagList.get(1)).build())
                    .build()
            ))
            .build(),
        postList.get(0)
    ).andDo(print())
        .andDo(docs(
            pathParameters(PostDocs.Request.updatePostPath()),
            requestFields(PostDocs.Request.updatePost())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 삭제")
  @DocsMethod(displayName = "포스팅 삭제", pathParameters = true)
  public void removePost() throws Exception {
    log.info("BlogControllerTest.removePost");

    setSignedSuper();

    apply();

    GET(uriBlogPostId, postList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(PostDocs.Request.removePost())))
        .andExpect(status().isOk());
  }

}