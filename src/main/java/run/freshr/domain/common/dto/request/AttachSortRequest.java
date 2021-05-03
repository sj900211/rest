package run.freshr.domain.common.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Attach sort request.
 *
 * @author [류성재]
 * @implNote IdRequest 와 SORT 값만을 가지고 있는 전역에서 공통으로 사용되는 요청 객체
 * @since 2020 -02-21 @author 류성재
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachSortRequest {

  /**
   * 일련 번호
   */
  @NotEmpty
  private IdRequest attach;
  /**
   * 정렬 순서
   */
  @NotEmpty
  private Integer sort;

}
