package run.freshr.domain.auth.redis;

import run.freshr.domain.auth.enumeration.Role;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * The Class Auth access.
 *
 * @author [류성재]
 * @implNote Access 토큰 관리
 * @since 2021. 3. 16. 오후 2:21:55
 */
@RedisHash("CACHE_ACCESS_TOKEN")
@Getter
public class AuthAccess {

  /**
   * Access Token
   */
  @Id
  private final String id;

  /**
   * 로그인 계정 일련 번호
   */
  private final Long signId;

  /**
   * 권한
   */
  private final Role role;

  /**
   * Instantiates a new Auth access.
   *
   * @param id     the id
   * @param signId the sign id
   * @param role   the role
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:21:55
   */
  private AuthAccess(String id, Long signId, Role role) {
    this.id = id;
    this.signId = signId;
    this.role = role;
  }

  /**
   * Create redis auth access.
   *
   * @param id     the id
   * @param signId the sign id
   * @param role   the role
   * @return the auth access
   * @author [류성재]
   * @implNote 생성 메서드
   * @since 2021. 3. 16. 오후 2:21:55
   */
  public static AuthAccess createRedis(String id, Long signId, Role role) {
    return new AuthAccess(id, signId, role);
  }

}
