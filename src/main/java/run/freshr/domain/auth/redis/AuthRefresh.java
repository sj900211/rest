package run.freshr.domain.auth.redis;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

/**
 * The Class Auth refresh.
 *
 * @author [류성재]
 * @implNote Refresh 토큰 관리
 * @since 2020 -08-10 @author 류성재
 */
@RedisHash("CACHE_REFRESH_TOKEN")
@Getter
public class AuthRefresh {

  /**
   * Refresh Token
   */
  @Id
  private final String id;

  /**
   * Access 정보
   */
  @Reference
  private AuthAccess access;

  /**
   * 마지막 수정 날짜 시간
   */
  private LocalDateTime updateDt;

  /**
   * Instantiates a new Auth refresh.
   *
   * @param id     the id
   * @param access the access
   * @author [류성재]
   * @implNote 생성자
   * @since 2021. 3. 16. 오후 2:22:34
   */
  private AuthRefresh(String id, AuthAccess access) {
    this.id = id;
    this.access = access;
    this.updateDt = LocalDateTime.now();
  }

  /**
   * Create redis auth refresh.
   *
   * @param id     the id
   * @param access the access
   * @return the auth refresh
   * @author [류성재]
   * @implNote 생성 메서드
   * @since 2021. 3. 16. 오후 2:22:34
   */
  public static AuthRefresh createRedis(String id, AuthAccess access) {
    return new AuthRefresh(id, access);
  }

  /**
   * Update redis.
   *
   * @param access the access
   * @author [류성재]
   * @implNote 정보 수정
   * @since 2021. 3. 16. 오후 2:22:35
   */
  public void updateRedis(AuthAccess access) {
    this.access = access;
    this.updateDt = LocalDateTime.now();
  }

}
