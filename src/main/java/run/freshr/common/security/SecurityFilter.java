package run.freshr.common.security;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static java.util.Optional.ofNullable;
import static run.freshr.enumeration.StatusEnum.EXPIRED_JWT;
import static run.freshr.util.BeanUtil.getBean;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;
import static org.springframework.util.StringUtils.hasLength;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import run.freshr.common.model.ResponseModel;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AuthAccess;
import run.freshr.domain.auth.service.AuthAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Security Filter
 *
 * @author [류성재]
 * @implNote Security Filter
 * @since 2020 -08-10 @author 류성재
 */
@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  /**
   * The Auth access service
   */
  private final AuthAccessService authAccessService;

  /**
   * The Object mapper
   */
  private final ObjectMapper objectMapper;

  /**
   * Instantiates a new Security filter.
   *
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 2. 25. 오후 5:10:44
   */
  public SecurityFilter() {
    this.authAccessService = getBean(AuthAccessService.class);
    this.objectMapper = getBean(ObjectMapper.class);
  }

  /**
   * Do filter internal.
   *
   * @param request     the request
   * @param response    the response
   * @param filterChain the filter chain
   * @author [류성재]
   * @implNote 필터 로직
   * @since 2021. 2. 25. 오후 5:10:44
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) {
    log.debug("**** SECURITY FILTER START");

    try {
      String header = request.getHeader(SecurityUtil.AUTHORIZATION_STRING); // Request Header Authorization 값 조회
      String jwt = ofNullable(header).orElse(null); // Bearer 토큰 조회
      Long id = 0L; // 게스트 권한으로 설정
      Role role = Role.ROLE_ANONYMOUS; // 게스트 권한으로 설정

      if (hasLength(jwt)) { // Bearer 토큰이 있는 경우
        String accessToken = jwt.replace(SecurityUtil.BEARER_PREFIX, ""); // Access 토큰 조회

        if (!authAccessService.exists(accessToken)) { // 발급한 토큰인지 체크
          throw new ExpiredJwtException(null, null, "error validate token");
        }

        if (SecurityUtil.checkExpiration(accessToken)) { // 만료되었는지 체크
          throw new ExpiredJwtException(null, null, "error validate token");
        }

        AuthAccess access = authAccessService.get(accessToken); // 데이터 조회
        id = access.getSignId(); // 일련 번호 조회
        role = access.getRole(); // 권한 조회
      }

      SecurityUtil.signedId.set(id); // 조회된 계정의 일련 번호 설정
      SecurityUtil.signedRole.set(role); // 조회된 계정의 권한 설정

      /*
       * 위에서 설정한 데이터로 일회용 로그인 설정
       * 쓰레드에 설정한 데이터와 여기서 설정한 데이터 모두
       * 이번 요청에서 사용된 후 사라진다.
       */
      SecurityContextHolder
          .getContext()
          .setAuthentication(new UsernamePasswordAuthenticationToken(
              role.getUsername(),
              "{noop}",
              createAuthorityList(role.getKey())
          ));

      log.debug("**** Role: " + SecurityUtil.signedRole.get().name());
      log.debug("**** Id: " + SecurityUtil.signedId.get());

      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) { // 발급한 토큰이 아니거나 만료된 토큰 처리
      log.error("**** JwtExpiredException ****");
      log.error("**** error message : " + e.getMessage());
      log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

      response.setStatus(BAD_REQUEST.value()); //  Response Status 설정
      response.setContentType(APPLICATION_JSON_VALUE); // 반환 컨텐츠 유형 설정

      try {
        response.getWriter().write(objectMapper.writeValueAsString(
            ResponseModel
                .builder()
                .name(UPPER_CAMEL.to(LOWER_HYPHEN, EXPIRED_JWT.name()))
                .message(EXPIRED_JWT.getMessage())
                .build()
        ));
        response.getWriter().flush();
        response.getWriter().close();
      } catch (IOException ex) { // 에러 처리
        log.error("**** IOException || JSONException ****");
        log.error("**** error message : " + e.getMessage());
        log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

        ex.printStackTrace();
      }
    } catch (Exception e) { // 발급한 토큰이 아니거나 만료된 토큰이 아닌 에러 처리
      log.error("**** Exception ****");
      log.error("**** error message : " + e.getMessage());
      log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

      if (response.getStatus() == OK.value()) {
        response.setStatus(INTERNAL_SERVER_ERROR.value());
      }
    } finally {
      log.debug("**** SECURITY FILTER FINISH");
    }
  }

}
