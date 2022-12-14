package run.freshr.common.security;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
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
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.unit.AccessRedisUnitImpl;

@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  private final AccessRedisUnitImpl authAccessUnit;

  private final ObjectMapper objectMapper;

  public SecurityFilter() {
    this.authAccessUnit = getBean(AccessRedisUnitImpl.class);
    this.objectMapper = getBean(ObjectMapper.class);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) {
    log.debug("**** SECURITY FILTER START");

    try {
      // Request Header Authorization ??? ??????
      String header = request.getHeader(AUTHORIZATION_STRING);
      String jwt = hasLength(header) ? header : null; // Bearer ?????? ??????
      Long id = 0L; // ????????? ???????????? ??????
      Role role = ROLE_ANONYMOUS; // ????????? ???????????? ??????

      if (hasLength(jwt)) { // Bearer ????????? ?????? ??????
        String accessToken = jwt.replace(BEARER_PREFIX, ""); // Access ?????? ??????

        if (!authAccessUnit.exists(accessToken)) { // ????????? ???????????? ??????
          throw new ExpiredJwtException(null, null, "error validate token");
        }

        if (SecurityUtil.checkExpiration(accessToken)) { // ?????????????????? ??????
          throw new ExpiredJwtException(null, null, "error validate token");
        }

        AccessRedis access = authAccessUnit.get(accessToken); // ????????? ??????

        id = access.getSignId(); // ?????? ?????? ??????
        role = access.getRole(); // ?????? ??????
      }

      signedId.set(id); // ????????? ????????? ?????? ?????? ??????
      signedRole.set(role); // ????????? ????????? ?????? ??????

      /*
       * ????????? ????????? ???????????? ????????? ????????? ??????
       * ???????????? ????????? ???????????? ????????? ????????? ????????? ??????
       * ?????? ???????????? ????????? ??? ????????????.
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
    } catch (ExpiredJwtException e) { // ????????? ????????? ???????????? ????????? ?????? ??????
      log.error("**** JwtExpiredException ****");
      log.error("**** error message : " + e.getMessage());
      log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

      response.setStatus(BAD_REQUEST.value()); //  Response Status ??????
      response.setContentType(APPLICATION_JSON_VALUE); // ?????? ????????? ?????? ??????

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
      } catch (IOException ex) { // ?????? ??????
        log.error("**** IOException || JSONException ****");
        log.error("**** error message : " + e.getMessage());
        log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

        ex.printStackTrace();
      }
    } catch (Exception e) { // ????????? ????????? ???????????? ????????? ????????? ?????? ?????? ??????
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
