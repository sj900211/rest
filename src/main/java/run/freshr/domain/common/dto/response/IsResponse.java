package run.freshr.domain.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Is response.
 *
 * @author [류성재]
 * @implNote 논리 데이터 값만을 가지고 있는 전역에서 공통으로 사용되는 반환 객체
 * @since 2021. 3. 16. 오후 2:36:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsResponse {

  /**
   * 논리 데이터
   */
  private Boolean is;

}
