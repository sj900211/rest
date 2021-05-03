package run.freshr.domain.auth.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Sign password request.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 비밀번호 변경 요청 객체
 * @since 2021. 3. 16. 오후 12:25:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignPasswordRequest {

  /**
   * 현재 비밀번호
   */
  @NotEmpty
  private String originPassword;
  /**
   * 변경할 비밀번호
   */
  @NotEmpty
  private String password;

}
