package run.freshr.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Sign in response.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 로그인 처리 후 반환 객체
 * @since 2021. 3. 16. 오후 2:13:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {

  /**
   * The Access token
   */
  private String accessToken;
  /**
   * The Refresh token
   */
  private String refreshToken;

}
