package run.freshr.common.security;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static java.util.Optional.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.security.SecurityUtil.AUTHORIZATION_STRING;
import static run.freshr.common.security.SecurityUtil.BEARER_PREFIX;
import static run.freshr.common.security.SecurityUtil.signedId;
import static run.freshr.common.security.SecurityUtil.signedRole;
import static run.freshr.domain.auth.enumeration.Role.ROLE_ANONYMOUS;
import static run.freshr.enumeration.StatusEnum.EXPIRED_JWT;
import static run.freshr.util.BeanUtil.getBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import run.freshr.common.model.ResponseModel;
import run.freshr.domain.auth.enumeration.Role;
import run.freshr.domain.auth.redis.AuthAccess;
import run.freshr.domain.auth.unit.AuthAccessUnitImpl;

@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  private final AuthAccessUnitImpl authAccessUnit;

  private final ObjectMapper objectMapper;

  public SecurityFilter() {
    this.authAccessUnit = getBean(AuthAccessUnitImpl.class);
    this.objectMapper = getBean(ObjectMapper.class);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) {
    log.debug("**** SECURITY FILTER START");

    try {
      // Request Header Authorization 값 조회
      String header = request.getHeader(AUTHORIZATION_STRING);
      String jwt = of(header).orElse(null); // Bearer 토큰 조회
      Long id = 0L; // 게스트 권한으로 설정
      Role role = ROLE_ANONYMOUS; // 게스트 권한으로 설정

      if (hasLength(jwt)) { // Bearer 토큰이 있는 경우
        String accessToken = jwt.replace(BEARER_PREFIX, ""); // Access 토큰 조회

        if (!authAccessUnit.exists(accessToken)) { // 발급한 토큰인지 체크
          throw new ExpiredJwtException(null, null, "error validate token");
        }

        if (SecurityUtil.checkExpiration(accessToken)) { // 만료되었는지 체크
          throw new ExpiredJwtException(null, null, "error validate token");
        }

        AuthAccess access = authAccessUnit.get(accessToken); // 데이터 조회

        id = access.getSignId(); // 일련 번호 조회
        role = access.getRole(); // 권한 조회
      }

      signedId.set(id); // 조회된 계정의 일련 번호 설정
      signedRole.set(role); // 조회된 계정의 권한 설정

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

      log.debug("**** Role: " + signedRole.get().name());
      log.debug("**** Id: " + signedId.get());

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
