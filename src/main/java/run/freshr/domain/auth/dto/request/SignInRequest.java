package run.freshr.domain.auth.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Sign in request.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 로그인 요청 객체
 * @since 2021. 3. 16. 오후 12:24:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

  /**
   * 고유 아이디
   */
  @NotEmpty
  private String username;
  /**
   * 비밀번호
   */
  @NotEmpty
  private String password;

}
