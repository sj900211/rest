package run.freshr.domain.community.dto.response;

import run.freshr.common.extension.ResponseExtension;
import run.freshr.domain.auth.dto.response.AuditResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Class Board response.
 *
 * @author [류성재]
 * @implNote 커뮤니티 관리 > 게시글 반환 객체
 * @since 2020 -08-10 @author 류성재
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse extends ResponseExtension {

  /**
   * 제목
   */
  private String title;
  /**
   * 내용
   */
  private String content;
  /**
   * 조회수
   */
  private Integer hit;
  /**
   * 작성자 정보
   */
  private AuditResponse creator;

}
