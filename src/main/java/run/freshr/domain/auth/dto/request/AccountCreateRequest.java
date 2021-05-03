package run.freshr.domain.auth.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Account create request.
 *
 * @author [류성재]
 * @implNote 사용자 관리 > 계정 등록 요청 객체
 * @since 2021. 3. 16. 오후 12:22:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {

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
  /**
   * 이름
   */
  @NotEmpty
  private String name;

}
