package run.freshr.domain.auth.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Sign update request.
 *
 * @author [류성재]
 * @implNote 권한 관리 > 계정 정보 수정 요청 객체
 * @since 2021. 3. 16. 오후 12:25:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpdateRequest {

  /**
   * 이름
   */
  @NotEmpty
  private String name;

}
