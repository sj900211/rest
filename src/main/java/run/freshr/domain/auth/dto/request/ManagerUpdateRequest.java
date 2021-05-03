package run.freshr.domain.auth.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import run.freshr.domain.auth.enumeration.ManagerPrivilege;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Manager update request.
 *
 * @author [류성재]
 * @implNote 시스템 관리 > 관리자 계정 수정 요청 객체
 * @since 2021. 3. 16. 오후 12:23:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerUpdateRequest {

  /**
   * 권한
   */
  @NotNull
  private ManagerPrivilege privilege;
  /**
   * 이름
   */
  @NotEmpty
  private String name;

}
