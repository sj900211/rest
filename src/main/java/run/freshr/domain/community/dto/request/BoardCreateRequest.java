package run.freshr.domain.community.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import run.freshr.domain.common.dto.request.AttachSortRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Board create request.
 *
 * @author [류성재]
 * @implNote 커뮤니티 관리 > 게시글 등록 요청 객체
 * @since 2020 -08-10 @author 류성재
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequest {

  /**
   * 제목
   */
  @NotEmpty
  private String title;
  /**
   * 내용
   */
  @NotEmpty
  private String content;
  /**
   * 첨부 파일
   */
  private List<AttachSortRequest> attachList;

}
