package run.freshr.domain.auth.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import run.freshr.domain.auth.enumeration.ManagerPrivilege;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Manager create request.
 *
 * @author [류성재]
 * @implNote 시스템 관리 > 관리자 계정 등록 요청 객체
 * @since 2021. 3. 16. 오후 12:23:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerCreateRequest {

  /**
   * 권한
   */
  @NotNull
  private ManagerPrivilege privilege;
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
