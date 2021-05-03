package run.freshr.common.extension;

import static java.util.Objects.isNull;
import static run.freshr.DataRunner.accountIdList;
import static run.freshr.DataRunner.managerIdList;
import static run.freshr.DataRunner.superManagerId;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import run.freshr.common.security.SecurityUtil;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.service.TestService;
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

/**
 * The Class Test extension.
 *
 * @author [류성재]
 * @implNote Test 클래스 공통 설정 및 공통 기능 정의
 * @since 2021. 2. 25. 오후 5:53:36
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestExtension {

  /**
   * The Object mapper
   */
  @Autowired
  protected ObjectMapper objectMapper;
  /**
   * The Test service
   */
  @Autowired
  protected TestService testService;
  /**
   * The Entity manager
   */
  @Autowired
  private EntityManager entityManager;
  /**
   * The Mock mvc
   */
  private MockMvc mockMvc;

  /**
   * The Document
   */
  private RestDocumentationResultHandler document;

  /**
   * Before each.
   *
   * @param webApplicationContext the web application context
   * @param restDocumentation     the rest documentation
   * @author [류성재]
   * @implNote 이 클래스를 상속받는 모든 클래스의 기본 설정
   * @since 2021. 2. 25. 오후 5:53:36
   */
  @BeforeEach
  public void beforeEach(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.document = document(
        "{class-name}/{method-name}", // 문서 경로 설정
        preprocessRequest( // Request 설정
            modifyUris()
                .scheme("https")
                .host("rest.freshr.kr"), // 문서에 노출되는 도메인 설정
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

  /**
   * Apply.
   *
   * @author [류성재]
   * @implNote 영속성 컨텍스트 반영 및 초기화
   * @since 2021. 2. 25. 오후 5:53:36
   */
  protected void apply() {
    entityManager.flush(); // 영속성 컨텍스트 내용을 데이터베이스에 반영
    entityManager.clear(); // 영속성 컨텍스트 초기화
  }

  /**
   * Sets header.
   *
   * @param mockHttpServletRequestBuilder the mock http servlet request builder
   * @return the header
   * @author [류성재]
   * @implNote Request Header 설정 - application/json
   * @since 2021. 2. 25. 오후 5:53:36
   */
  private MockHttpServletRequestBuilder setHeader(
      MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    return mockHttpServletRequestBuilder
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON);
  }

  /**
   * Sets multipart.
   *
   * @param mockHttpServletRequestBuilder the mock http servlet request builder
   * @return the multipart
   * @author [류성재]
   * @implNote Request Header 설정 - multipart/form-data
   * @since 2021. 2. 25. 오후 5:53:36
   */
  private MockHttpServletRequestBuilder setMultipart(
      MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    return mockHttpServletRequestBuilder
        .contentType(MULTIPART_FORM_DATA)
        .accept(APPLICATION_JSON);
  }

  /**
   * Post result actions.
   *
   * @param uri           the uri
   * @param pathVariables the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote POST 통신 Path Parameter 를 설정할 수 있다.
   * @since 2021. 2. 25. 오후 5:53:36
   */
  public ResultActions POST(String uri, Object... pathVariables) throws Exception {
    return mockMvc.perform(setHeader(post(uri, pathVariables)));
  }

  /**
   * Post token result actions.
   *
   * @param uri           the uri
   * @param token         the token
   * @param pathVariables the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote POST 통신 Path Parameter 를 설정할 수 있다. Authorization 설정을 할 수 있다.
   * @since 2021. 2. 25. 오후 5:53:36
   */
  public ResultActions POST_TOKEN(String uri, String token, Object... pathVariables)
      throws Exception {
    return mockMvc
        .perform(setHeader(post(uri, pathVariables))
            .header("Authorization", "Bearer " + token));
  }

  /**
   * Post body result actions.
   *
   * @param uri           the uri
   * @param content       the content
   * @param pathVariables the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote POST 통신 Path Parameter 를 설정할 수 있다. Request Body 를 설정할 수 있다.
   * @since 2021. 2. 25. 오후 5:53:36
   */
  public <T> ResultActions POST_BODY(String uri, T content, Object... pathVariables)
      throws Exception {
    return mockMvc.perform(
        setHeader(post(uri, pathVariables))
            .content(objectMapper.writeValueAsString(content)));
  }

  /**
   * Post multipart result actions.
   *
   * @param uri               the uri
   * @param directory         the directory
   * @param mockMultipartFile the mock multipart file
   * @param pathVariables     the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote POST 통신 Path Parameter 를 설정할 수 있다. Request Parameter 를 설정할 수 있다. Multipart 파일을 설정할 수
   * 있다.
   * @since 2021. 2. 25. 오후 5:53:36
   */
  public ResultActions POST_MULTIPART(String uri, String directory, MockMultipartFile mockMultipartFile,
      Object... pathVariables) throws Exception {
    MockMultipartHttpServletRequestBuilder file = fileUpload(uri, pathVariables)
        .file(mockMultipartFile);

    if (hasLength(directory)) {
      file.param("directory", directory);
    }

    return mockMvc.perform(setMultipart(file));
  }

  /**
   * Get result actions.
   *
   * @param uri           the uri
   * @param pathVariables the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote GET 통신 Path Parameter 를 설정할 수 있다.
   * @since 2021. 2. 25. 오후 5:53:36
   */
  public ResultActions GET(String uri, Object... pathVariables) throws Exception {
    return mockMvc.perform(setHeader(get(uri, pathVariables)));
  }

  /**
   * Get param result actions.
   *
   * @param <T>           the type parameter
   * @param uri           the uri
   * @param search        the search
   * @param pathVariables the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote GET 통신 Path Parameter 를 설정할 수 있다. Request Parameter 를 VO 객체로 설정할 수 있다.
   * @since 2021. 2. 25. 오후 5:53:37
   */
  public <T extends SearchExtension> ResultActions GET_PARAM(String uri, T search,
      Object... pathVariables) throws Exception {
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

              List<String> valueList = Arrays.asList(valueString.split(", "));
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

              List<String> valueList = Arrays.asList(valueString.split(", "));
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

  /**
   * Get body result actions.
   *
   * @param uri           the uri
   * @param content       the content
   * @param pathVariables the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote GET 통신 Path Parameter 를 설정할 수 있다. Request Body 를 설정할 수 있다.
   * @since 2021. 2. 25. 오후 5:53:37
   */
  public ResultActions GET_BODY(String uri, String content, Object... pathVariables)
      throws Exception {
    return mockMvc.perform(setHeader(get(uri, pathVariables)).content(content));
  }

  /**
   * Put result actions.
   *
   * @param uri           the uri
   * @param pathVariables the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote PUT 통신 Path Parameter 를 설정할 수 있다.
   * @since 2021. 2. 25. 오후 5:53:37
   */
  public ResultActions PUT(String uri, Object... pathVariables) throws Exception {
    return mockMvc.perform(setHeader(put(uri, pathVariables)));
  }

  /**
   * Put body result actions.
   *
   * @param uri           the uri
   * @param content       the content
   * @param pathVariables the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote PUT 통신 Path Parameter 를 설정할 수 있다. Request Body 를 설정할 수 있다.
   * @since 2021. 2. 25. 오후 5:53:37
   */
  public <T> ResultActions PUT_BODY(String uri, T content, Object... pathVariables)
      throws Exception {
    return mockMvc.perform(
        setHeader(put(uri, pathVariables)).content(objectMapper.writeValueAsString(content)));
  }

  /**
   * Delete result actions.
   *
   * @param uri           the uri
   * @param pathVariables the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote DELETE 통신 Path Parameter 를 설정할 수 있다.
   * @since 2021. 2. 25. 오후 5:53:37
   */
  public ResultActions DELETE(String uri, Object... pathVariables) throws Exception {
    return mockMvc.perform(setHeader(delete(uri, pathVariables)));
  }

  /**
   * Delete body result actions.
   *
   * @param uri           the uri
   * @param content       the content
   * @param pathVariables the path variables
   * @return the result actions
   * @throws Exception the exception
   * @author [류성재]
   * @implNote DELETE 통신 Path Parameter 를 설정할 수 있다. Request Body 를 설정할 수 있다.
   * @since 2021. 2. 25. 오후 5:53:37
   */
  public ResultActions DELETE_BODY(String uri, String content, Object... pathVariables)
      throws Exception {
    return mockMvc.perform(setHeader(delete(uri, pathVariables)).content(content));
  }

  /**
   * Document 작성
   *
   * @param snippets the snippets
   * @return the rest documentation result handler
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 3:11:00
   */
  protected RestDocumentationResultHandler docs(Snippet... snippets) {
    return document.document(snippets);
  }

  /**
   * Popup Document 작성
   *
   * @param snippets the snippets
   * @return the rest documentation result handler
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 3:11:00
   */
  protected RestDocumentationResultHandler docsPopup(Snippet... snippets) {
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


  /**
   * Authentication.
   *
   * @param id   the id
   * @param role the role
   * @author [류성재]
   * @implNote 로그인 처리
   * @since 2021. 2. 25. 오후 5:53:37
   */
  private void authentication(Long id, Role role) {
    removeSigned(); // 로그아웃 처리

    if (!role.equals(Role.ROLE_ANONYMOUS)) { // 게스트 권한이 아닐 경우
      testService.createAuth(id, role); // 토큰 발급 및 등록
    }

    SecurityUtil.signedRole.set(role); // 로그인한 계정 권한 설정
    SecurityUtil.signedId.set(id); // 로그인한 계정 일련 번호 설정

    SecurityContextHolder // 일회용 로그인 설정
        .getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(
            role.getUsername(),
            "{noop}",
            AuthorityUtils.createAuthorityList(role.getKey())
        ));
  }

  /**
   * Sets anonymous.
   *
   * @author [류성재]
   * @implNote 게스트로 로그인 처리
   * @since 2021. 2. 25. 오후 5:53:37
   */
  protected void setAnonymous() {
    authentication(0L, Role.ROLE_ANONYMOUS);
  }

  /**
   * 수퍼 관리자 권한 설정
   *
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 3:11:00
   */
  protected void setSignedSuper() {
    authentication(superManagerId, Role.ROLE_SUPER);
  }

  /**
   * 관리자 권한 설정
   *
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 3:11:00
   */
  protected void setSignedManager() {
    authentication(managerIdList.get(0), Role.ROLE_MANAGER);
  }

  /**
   * 사용자 권한 설정
   *
   * @author [류성재]
   * @implNote
   * @since 2021. 3. 16. 오후 3:11:00
   */
  protected void setSignedUser() {
    authentication(accountIdList.get(0), Role.ROLE_USER);
  }

  /**
   * Gets signed id.
   *
   * @return the signed id
   * @author [류성재]
   * @implNote 로그인한 계정의 일련 번호 조회
   * @since 2021. 2. 25. 오후 5:53:37
   */
  protected Long getSignedId() {
    return SecurityUtil.signedId.get();
  }

  /**
   * Gets signed role.
   *
   * @return the signed role
   * @author [류성재]
   * @implNote 로그인한 계정의 권한 조회
   * @since 2021. 2. 25. 오후 5:53:37
   */
  protected Role getSignedRole() {
    return SecurityUtil.signedRole.get();
  }

  /**
   * Remove signed.
   *
   * @author [류성재]
   * @implNote 로그아웃 처리
   * @since 2021. 2. 25. 오후 5:53:37
   */
  protected void removeSigned() {
    SecurityUtil.signedRole.remove();
    SecurityUtil.signedId.remove();
  }

}
