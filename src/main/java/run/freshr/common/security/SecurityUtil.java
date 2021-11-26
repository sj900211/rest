package run.freshr.common.security;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static run.freshr.util.CryptoUtil.encodeBase64;
import static run.freshr.util.CryptoUtil.encryptSha256;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import javax.crypto.spec.SecretKeySpec;
import run.freshr.domain.auth.enumeration.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The Class Security util.
 *
 * @author [류성재]
 * @implNote Security 유틸
 * @since 2021. 2. 25. 오전 10:32:19
 */
@Slf4j
@Component
public class SecurityUtil {

  public static final String JWT_VARIABLE = "EMOTION"; // JWT 암호화 키 값
  /**
   * SHA256 으로 암호화 HS512 로 암호화할 때 짧은 SALT 값은 오류가 발생하기 때문에 암호화를 진행해서 긴 문자를 생성
   */
  public static final String JWT_SHA = encryptSha256(JWT_VARIABLE);
  public static final byte[] JWT_BYTE = encodeBase64(JWT_SHA).getBytes(UTF_8); // JWT 암호화 키 Byte
  public static final Key JWT_KEY = new SecretKeySpec(JWT_BYTE, HS512.getJcaName()); // Key 생성
  public static final Integer EXPIRATION_ACCESS = 1000 * 60 * 15; // Access Token 만료 시간
  public static final String AUTHORIZATION_STRING = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";
  public static ThreadLocal<Long> signedId = new ThreadLocal<>(); // 요청한 토큰의 계정 일련 번호
  public static ThreadLocal<Role> signedRole = new ThreadLocal<>(); // 요청한 토큰의 계정 권한

  /**
   * Create access token string.
   *
   * @param seq the seq
   * @return the string
   * @author [류성재]
   * @implNote Access Token 생성
   * @since 2021. 2. 25. 오전 10:32:19
   */
  public static String createAccessToken(final Long seq) {
    return createJwt(seq.toString(), EXPIRATION_ACCESS, null);
  }

  /**
   * Create refresh token string.
   *
   * @param seq the seq
   * @return the string
   * @author [류성재]
   * @implNote Refresh Token 생성
   * @since 2021. 2. 25. 오전 10:32:19
   */
  public static String createRefreshToken(final Long seq) {
    return createJwt(seq.toString(), null, null);
  }

  /**
   * Create jwt string.
   *
   * @param subject    the subject
   * @param expiration the expiration
   * @param claim      the claim
   * @return the string
   * @author [류성재]
   * @implNote JWT 생성
   * @since 2021. 2. 25. 오전 10:32:19
   */
  public static String createJwt(final String subject, final Integer expiration,
      final HashMap<String, Object> claim) {
    JwtBuilder jwtBuilder = Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setSubject(subject)
        .setIssuedAt(new Date())
        .signWith(JWT_KEY);

    if (!isNull(claim)) { // 토큰 Body 설정
      jwtBuilder.setClaims(claim);
    }

    if (!isNull(expiration)) { // 만료 시간 설정
      jwtBuilder.setExpiration(new Date(new Date().getTime() + expiration));
    }

    return jwtBuilder.compact();
  }

  /**
   * Get claims.
   *
   * @param jwt the jwt
   * @return the claims
   * @throws JwtException the jwt exception
   * @author [류성재]
   * @implNote JWT 복호화
   * @since 2021. 2. 25. 오전 10:32:19
   */
  public static Claims get(final String jwt) throws JwtException {
    return Jwts
        .parser()
        .setSigningKey(JWT_KEY)
        .parseClaimsJws(jwt)
        .getBody();
  }

  /**
   * Check expiration boolean.
   *
   * @param jwt the jwt
   * @return the boolean
   * @throws JwtException the jwt exception
   * @author [류성재]
   * @implNote JWT 만료 체크
   * @since 2021. 2. 25. 오전 10:32:19
   */
  public static boolean checkExpiration(final String jwt) throws JwtException {
    boolean flag = false;

    try {
      get(jwt);
    } catch (ExpiredJwtException e) {
      flag = true;
    }

    return flag;
  }

}
