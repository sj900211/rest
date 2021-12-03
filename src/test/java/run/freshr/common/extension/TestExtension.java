package run.freshr.common.extension;

import static java.util.List.of;
import static java.util.Objects.isNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.DataRunner.coachId;
import static run.freshr.DataRunner.leaderId;
import static run.freshr.DataRunner.managerId;
import static run.freshr.DataRunner.superId;
import static run.freshr.DataRunner.userId;
import static run.freshr.common.security.SecurityUtil.signedId;
import static run.freshr.common.security.SecurityUtil.signedRole;
import static run.freshr.domain.auth.enumeration.Role.ROLE_ANONYMOUS;
import static run.freshr.domain.auth.enumeration.Role.ROLE_COACH;
import static run.freshr.domain.auth.enumeration.Role.ROLE_LEADER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_MANAGER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_SUPER;
import static run.freshr.domain.auth.enumeration.Role.ROLE_USER;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.service.TestService;

@Slf4j
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@TestInstance(PER_CLASS)
public class TestExtension {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  protected TestService testService;
  @Autowired
  private EntityManager entityManager;
  private MockMvc mockMvc;

  private RestDocumentationResultHandler document;

  @BeforeEach
  public void beforeEach(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    log.info("TestExtension.beforeEach");

    this.document = document(
        "{class-name}/{method-name}", // 문서 경로 설정
        preprocessRequest( // Request 설정
            modifyUris()
                .scheme("http")
                .host("localhost")
                .port(8900), // 문서에 노출되는 도메인 설정
            prettyPrint() // 정리해서 출력
        ),
        preprocessResponse(prettyPrint()) // Response 설정. 정리해서 출력
    );

    this.mockMvc = MockMvcBuilders // MockMvc 공통 설정. 문서 출력 설정
        .webAppContextSetup(webApplicationContext)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(document)
        .build();
  }

  protected void apply() {
    log.info("TestExtension.apply");

    entityManager.flush(); // 영속성 컨텍스트 내용을 데이터베이스에 반영
    entityManager.clear(); // 영속성 컨텍스트 초기화
  }

  private MockHttpServletRequestBuilder setHeader(
      MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    log.info("TestExtension.setHeader");

    return mockHttpServletRequestBuilder
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON);
  }

  private MockHttpServletRequestBuilder setMultipart(
      MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    log.info("TestExtension.setMultipart");

    return mockHttpServletRequestBuilder
        .contentType(MULTIPART_FORM_DATA)
        .accept(APPLICATION_JSON);
  }

  public ResultActions POST(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.POST");

    return mockMvc.perform(setHeader(post(uri, pathVariables)));
  }

  public ResultActions POST_TOKEN(String uri, String token, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.POST_TOKEN");

    return mockMvc
        .perform(setHeader(post(uri, pathVariables))
            .header("Authorization", "Bearer " + token));
  }

  public <T> ResultActions POST_BODY(String uri, T content, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.POST_BODY");

    return mockMvc.perform(
        setHeader(post(uri, pathVariables))
            .content(objectMapper.writeValueAsString(content)));
  }

  public ResultActions POST_MULTIPART(String uri, String directory,
      MockMultipartFile mockMultipartFile,
      Object... pathVariables) throws Exception {
    log.info("TestExtension.POST_MULTIPART");

    MockMultipartHttpServletRequestBuilder file = fileUpload(uri, pathVariables)
        .file(mockMultipartFile);

    if (hasLength(directory)) {
      file.param("directory", directory);
    }

    return mockMvc.perform(setMultipart(file));
  }

  public ResultActions GET(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.GET");

    return mockMvc.perform(setHeader(get(uri, pathVariables)));
  }

  public <T extends SearchExtension> ResultActions GET_PARAM(String uri, T search,
      Object... pathVariables) throws Exception {
    log.info("TestExtension.GET_PARAM");

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(uri, pathVariables);

    if (!isNull(search)) {
      Arrays.stream(search.getClass().getDeclaredFields()).forEach(field -> {
        try {
          field.setAccessible(true);

          if (!isNull(field.get(search))) {
            if (!field.getType().equals(List.class)) {
              mockHttpServletRequestBuilder.param(field.getName(), field.get(search).toString());
            } else {
              String valueString = field.get(search).toString();

              valueString = valueString.substring(1, valueString.length() - 1);

              List<String> valueList = of(valueString.split(", "));
              int max = valueList.size();

              for (int i = 0; i < max; i++) {
                mockHttpServletRequestBuilder
                    .param(field.getName() + "[" + i + "]", valueList.get(i));
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });

      Arrays.stream(search.getClass().getSuperclass().getDeclaredFields()).forEach(field -> {
        try {
          field.setAccessible(true);

          if (!isNull(field.get(search))) {
            if (!field.getType().equals(List.class)) {
              mockHttpServletRequestBuilder.param(field.getName(), field.get(search).toString());
            } else {
              String valueString = field.get(search).toString();

              valueString = valueString.substring(1, valueString.length() - 1);

              List<String> valueList = of(valueString.split(", "));
              int max = valueList.size();

              for (int i = 0; i < max; i++) {
                mockHttpServletRequestBuilder
                    .param(field.getName() + "[" + i + "]", valueList.get(i));
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    }

    return mockMvc.perform(setHeader(mockHttpServletRequestBuilder));
  }

  public ResultActions PUT(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.PUT");

    return mockMvc.perform(setHeader(put(uri, pathVariables)));
  }

  public <T> ResultActions PUT_BODY(String uri, T content, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.PUT_BODY");

    return mockMvc.perform(
        setHeader(put(uri, pathVariables)).content(objectMapper.writeValueAsString(content)));
  }

  public ResultActions DELETE(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.DELETE");

    return mockMvc.perform(setHeader(delete(uri, pathVariables)));
  }

  protected RestDocumentationResultHandler docs(Snippet... snippets) {
    log.info("TestExtension.docs");

    return document.document(snippets);
  }

  protected RestDocumentationResultHandler docsPopup(Snippet... snippets) {
    log.info("TestExtension.docsPopup");

    this.document = document(
        "{class-name}/{method-name}/popup", // 문서 경로 설정
        preprocessRequest( // Request 설정
            modifyUris()
                .scheme("http")
                .host("localhost"), // 문서에 노출되는 도메인 설정
            prettyPrint() // 정리해서 출력
        ),
        preprocessResponse(prettyPrint()) // Response 설정. 정리해서 출력
    );

    return document.document(snippets);
  }


  private void authentication(Long id, Role role) {
    log.info("TestExtension.authentication");

    removeSigned(); // 로그아웃 처리

    if (!role.equals(ROLE_ANONYMOUS)) { // 게스트 권한이 아닐 경우
      testService.createAuth(id, role); // 토큰 발급 및 등록
    }

    signedRole.set(role); // 로그인한 계정 권한 설정
    signedId.set(id); // 로그인한 계정 일련 번호 설정

    SecurityContextHolder // 일회용 로그인 설정
        .getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(
            role.getUsername(),
            "{noop}",
            AuthorityUtils.createAuthorityList(role.getKey())
        ));
  }

  protected void setSignedSuper() {
    log.info("TestExtension.setSignedSuper");

    authentication(superId, ROLE_SUPER);
  }

  protected void setSignedManager() {
    log.info("TestExtension.setSignedManager");

    authentication(managerId, ROLE_MANAGER);
  }

  protected void setSignedLeader() {
    log.info("TestExtension.setSignedLeader");

    authentication(leaderId, ROLE_LEADER);
  }

  protected void setSignedCoach() {
    log.info("TestExtension.setSignedCoach");

    authentication(coachId, ROLE_COACH);
  }

  protected void setSignedUser() {
    log.info("TestExtension.setSignedUser");

    authentication(userId, ROLE_USER);
  }

  protected void setAnonymous() {
    log.info("TestExtension.setAnonymous");

    authentication(0L, ROLE_ANONYMOUS);
  }

  protected Long getSignedId() {
    log.info("TestExtension.getSignedId");

    return signedId.get();
  }

  protected Role getSignedRole() {
    log.info("TestExtension.getSignedRole");

    return signedRole.get();
  }

  protected void removeSigned() {
    log.info("TestExtension.removeSigned");

    signedRole.remove();
    signedId.remove();
  }

}
