package run.freshr.domain.common.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Id request.
 *
 * @author [류성재]
 * @implNote ID 값만을 가지고 있는 전역에서 공통으로 사용되는 요청 객체
 * @since 2021. 3. 16. 오후 2:33:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdRequest {

  /**
   * 일련 번호
   */
  @NotNull
  private Long id;

}
