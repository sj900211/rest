package run.freshr.domain.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Id response.
 *
 * @author [류성재]
 * @implNote ID 값만을 가지고 있는 전역에서 공통으로 사용되는 반환 객체
 * @since 2021. 3. 16. 오후 2:35:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdResponse {

  /**
   * 일련 번호
   */
  private Long id;

}
