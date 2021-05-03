package run.freshr.domain.common.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Id sort request.
 *
 * @author [류성재]
 * @implNote ID 와 SORT 값만을 가지고 있는 전역에서 공통으로 사용되는 요청 객체
 * @since 2021. 3. 16. 오후 2:34:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdSortRequest {

  /**
   * 일련 번호
   */
  @NotNull
  private Long id;
  /**
   * 정렬 순서
   */
  @NotNull
  @Size(min = 1)
  private Integer sort;

}
