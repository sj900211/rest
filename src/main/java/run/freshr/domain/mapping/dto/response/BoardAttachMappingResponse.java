package run.freshr.domain.mapping.dto.response;

import run.freshr.domain.common.dto.response.AttachResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Board attach mapping response.
 *
 * @author [류성재]
 * @implNote 연관 관계 관리 > 게시글 첨부 파일 반환 객체
 * @since 2020 -08-10 @author 류성재
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardAttachMappingResponse {

  /**
   * 첨부 파일
   */
  private AttachResponse attach;
  /**
   * 정렬 순서
   */
  private Integer sort;

}
