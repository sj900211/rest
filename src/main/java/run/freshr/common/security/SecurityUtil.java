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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import run.freshr.domain.auth.enumeration.Role;

@Slf4j
@Component
public class SecurityUtil {

  public static final String JWT_VARIABLE = "EMOTION"; // JWT 암호화 키 값
  public static final String JWT_SHA = encryptSha256(JWT_VARIABLE);
  public static final byte[] JWT_BYTE = encodeBase64(JWT_SHA).getBytes(UTF_8); // JWT 암호화 키 Byte
  public static final Key JWT_KEY = new SecretKeySpec(JWT_BYTE, HS512.getJcaName()); // Key 생성
  public static final Integer EXPIRATION_ACCESS = 1000 * 60 * 15; // Access Token 만료 시간
  public static final String AUTHORIZATION_STRING = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";
  public static ThreadLocal<Long> signedId = new ThreadLocal<>(); // 요청한 토큰의 계정 일련 번호
  public static ThreadLocal<Role> signedRole = new ThreadLocal<>(); // 요청한 토큰의 계정 권한

  public static String createAccessToken(final Long seq) {
    return createJwt(seq.toString(), EXPIRATION_ACCESS, null);
  }

  public static String createRefreshToken(final Long seq) {
    return createJwt(seq.toString(), null, null);
  }

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

  public static Claims get(final String jwt) throws JwtException {
    return Jwts
        .parserBuilder()
        .setSigningKey(JWT_KEY)
        .build()
        .parseClaimsJws(jwt)
        .getBody();
  }

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
