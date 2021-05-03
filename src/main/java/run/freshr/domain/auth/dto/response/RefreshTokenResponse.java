package run.freshr.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Refresh token response.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 토큰 갱신 요청 객체
 * @since 2021. 3. 16. 오후 12:27:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponse {

  /**
   * Access 토큰
   */
  private String accessToken;

}
