package run.freshr.domain.common.dto.send;

import static lombok.AccessLevel.PROTECTED;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class Email attach send.
 *
 * @author [류성재]
 * @implNote 이메일 첨부파일 객체
 * @since 2021. 3. 16. 오후 2:37:13
 */
@Data
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class EmailAttachSend {

  /**
   * 노출 이름
   */
  private String display;
  /**
   * 물리 이름
   */
  private String physical;

}
